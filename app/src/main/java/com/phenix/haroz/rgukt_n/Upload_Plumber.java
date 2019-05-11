package com.phenix.haroz.rgukt_n;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Upload_Plumber extends AppCompatActivity {

    private TextView ServiceType;
    private EditText mRoom;
    private Button Upload_Button;
    private DatabaseReference databasePlumber;
    private ProgressBar progressBar;
    private Button imageButton;
    private ImageView imageView;
    private static final int Gallary_intent = 1;
    UploadImagePlumber uploadImagePlumber;
    private StorageReference imagePath;
    private Uri uri;
    private Spinner mFloor,spinner;
    private StorageReference Storage;
    private ProgressDialog progressDialog;
    private StorageTask mUploadTask;
    Login login;
    private String logmail;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private ExpandableListView expandableListView;
    private ExpandableListAdapterPlum listAdapter;
    private HashMap<String, List<String>> listDataChild;
    private List<String> listDataHeader;
    private Button mBlockName;private String s;
    static int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__plumber);

        databasePlumber = FirebaseDatabase.getInstance().getReference("PlumbingWork");
        Storage = FirebaseStorage.getInstance().getReference("PlumbingWork");

        progressBar = (ProgressBar)findViewById(R.id.login_progress);
        ServiceType = (TextView)findViewById(R.id.text);
        spinner = (Spinner)findViewById(R.id.spinner);
        mBlockName = (Button) findViewById(R.id.selectBlock);
        //mBlockNo = (Spinner)findViewById(R.id.blockNo);
        mFloor = (Spinner) findViewById(R.id.spinner4);
        mRoom = (EditText)findViewById(R.id.Room_no);
        imageView = (ImageView)findViewById(R.id.img);

        login = new Login();
        logmail = login.store;
        Log.d("logged mail::",logmail);

        expandableListView = (ExpandableListView) findViewById(R.id.expandable_listview);
        //spinner = (Spinner) findViewById(R.id.spinner2);

        mBlockName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c++;
                if (c%2 == 1){
                    expandableListView.setVisibility(View.VISIBLE);
                }else {
                    expandableListView.setVisibility(View.GONE);
                }
            }
        });
        repairListData();
        listAdapter = new ExpandableListAdapterPlum(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(listAdapter);

        // Listview on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                s = (listDataHeader.get(groupPosition)
                        + " : "
                        + listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition)).toString();
                mBlockName.setText(s);
                expandableListView.setVisibility(View.GONE);
                return false;
            }
        });

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = dateFormat.format(calendar.getTime());
        Log.d("Date and Time : ",date);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");


        imageButton = (Button) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,Gallary_intent);
            }
        });

        Upload_Button = (Button) findViewById(R.id.upload_button);
        Upload_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask!=null && mUploadTask.isInProgress())
                {
                    //Toast.makeText(Upload_Plumber.this, "Upload in Process", Toast.LENGTH_SHORT).show();
                }else {
                    addPlumber();
                }
            }
        });
    }
    private void addPlumber(){
        final String Service = ServiceType.getText().toString();
        final String ComponentName = spinner.getSelectedItem().toString();
        final String Block = mBlockName.getText().toString();
//        final String BlockName = mBlockName.getSelectedItem().toString();
//        final String BlockNo = mBlockNo.getSelectedItem().toString().toUpperCase();
        final String Floor = mFloor.getSelectedItem().toString();
        final String Room = mRoom.getText().toString().trim().toUpperCase();
        //final String Block = BlockName+" "+BlockNo;

        //progressDialog.show();
        if (Floor.equals("--Select Floor--")) {
            Toast.makeText(getApplicationContext(), "Please select Floor..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ComponentName.equals("--Select Component--")) {
            Toast.makeText(getApplicationContext(), "Please select Component..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Room)) {
            Toast.makeText(getApplicationContext(), "Enter Room No", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(s)&& Block.equals("--Select Block--")) {
            Toast.makeText(getApplicationContext(), "Please select Block..", Toast.LENGTH_SHORT).show();
            return;
        }
        if(uri!=null && !ComponentName.equals("--Select Component--") && !Floor.equals("--Select Floor--")){
            imagePath = Storage.child(System.currentTimeMillis()+"."+getFileExtension(uri));

            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //here you can choose quality factor in third parameter(ex. i choosen 25)
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] fileInBytes = baos.toByteArray();

            mUploadTask = imagePath.putBytes(fileInBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //progressDialog.show();
                            progressBar.setProgress(0);

                        }
                    },5000);
                    Toast.makeText(Upload_Plumber.this, "Problem has been posted successfully. Status will be updated soon.", Toast.LENGTH_LONG).show();
                    String id = databasePlumber.push().getKey();
                    uploadImagePlumber = new UploadImagePlumber(id,Service, taskSnapshot.getDownloadUrl().toString(), ComponentName.toUpperCase(), Block, Floor, Room,date,logmail);
                    databasePlumber.child(id).setValue(uploadImagePlumber);
                    Intent intent = new Intent(Upload_Plumber.this,Services.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Upload_Plumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double process = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int)process);
                    Toast.makeText(Upload_Plumber.this, "Uploading..Wait", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(this, "Please select image..", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallary_intent && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            uri = data.getData();
            Picasso.with(this).load(uri).into(imageView);
            imageView.setVisibility(View.VISIBLE);

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void repairListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        listDataHeader.add("A");
        listDataHeader.add("K");
        listDataHeader.add("I");
        listDataHeader.add("M");
        listDataHeader.add("N");
        listDataHeader.add("O");
        listDataHeader.add("P");
        listDataHeader.add("Q");
        listDataHeader.add("H");
        listDataHeader.add("L");
        listDataHeader.add("S");

        // Adding child data
        List<String> A = new ArrayList<String>();
        A.add("2");
        A.add("3");
        A.add("4");
        List<String> K = new ArrayList<String>();
        K.add("2");
        K.add("3");
        K.add("4");
        List<String> I = new ArrayList<String>();
        I.add("1");
        I.add("2");
        I.add("3");
        List<String> M = new ArrayList<String>();
        M.add("1");
        M.add("2");
        List<String> N = new ArrayList<String>();
        N.add("1");
        N.add("2");
        N.add("3");
        N.add("4");
        List<String> O = new ArrayList<String>();
        O.add("1");
        O.add("2");
        O.add("3");
        O.add("4");
        List<String> P = new ArrayList<String>();
        P.add("1");
        P.add("2");
        P.add("3");
        List<String> Q = new ArrayList<String>();
        Q.add("1");
        Q.add("2");
        Q.add("3");
        Q.add("4");
        List<String> DINING = new ArrayList<String>();
        DINING.add("1");
        DINING.add("2");
        DINING.add("3");
        DINING.add("4");
        DINING.add("5");
        DINING.add("6");
        DINING.add("7");
        List<String> L = new ArrayList<String>();
        L.add("1");
        L.add("2");
        L.add("3");
        List<String> S = new ArrayList<String>();
        S.add("1");
        S.add("2");
        S.add("3");
        listDataChild.put(listDataHeader.get(0), A); // Header, Child data
        listDataChild.put(listDataHeader.get(1), K);
        listDataChild.put(listDataHeader.get(2), I);
        listDataChild.put(listDataHeader.get(3), M);
        listDataChild.put(listDataHeader.get(4), N);
        listDataChild.put(listDataHeader.get(5), O);
        listDataChild.put(listDataHeader.get(6), P);
        listDataChild.put(listDataHeader.get(7), Q);
        listDataChild.put(listDataHeader.get(8), DINING);
        listDataChild.put(listDataHeader.get(9), L);
        listDataChild.put(listDataHeader.get(10), S);

    }

}