package com.phenix.haroz.rgukt_n;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TechCarRepair extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private ExpandableListAdapterCar listAdapter;
    private Button repair,replace,component;
    private List<String> listDataHeader;
    private LinearLayout linearLayout;
    private HashMap<String, List<String>> listDataChild;
    private Spinner spinner;
    static int c=0;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private TextView tvblock,tvroom,tvfloor;
    private Button btcompleted,btpending;
    private DatabaseReference databaseCar;
    private TechUploadPlum techUploadCar;
    private String status;
    private String s;
    private Spinner spinnerT,spinnerP;
    private String filter,filter1,filter2,filter3,filter4,filter5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_car_repair);

        databaseCar = FirebaseDatabase.getInstance().getReference("TechWork");

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = dateFormat.format(calendar.getTime());
        Log.d("Date and Time : ",date);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        filter = bundle.getString("Block").toString().toUpperCase();
        filter1 = bundle.getString("Floor").toString().toUpperCase();
        filter2 = bundle.getString("Room").toString().toUpperCase();
        filter3 = bundle.getString("Up_time").toString();
        filter4 = bundle.getString("Lmail").toString();
        filter5 = bundle.getString("type").toString();

        tvblock = (TextView)findViewById(R.id.tvblock);
        tvfloor = (TextView)findViewById(R.id.tvfloor);
        tvroom = (TextView)findViewById(R.id.tvroom);
        tvblock.setText("Block - "+filter);
        tvfloor.setText("Floor - "+filter1);
        tvroom.setText("Room - "+filter2);
        spinnerT = (Spinner)findViewById(R.id.repSpinner);
        spinnerP = (Spinner)findViewById(R.id.spinnerPos);

        expandableListView = (ExpandableListView) findViewById(R.id.expandable_listview);
        linearLayout = (LinearLayout)findViewById(R.id.repairLayout);

        component = (Button) findViewById(R.id.button);
        spinner = (Spinner) findViewById(R.id.spinner2);

        component.setOnClickListener(new View.OnClickListener() {
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
        listAdapter = new ExpandableListAdapterCar(this, listDataHeader, listDataChild);
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
                component.setText(s);
                expandableListView.setVisibility(View.GONE);
                return false;
            }
        });
        btcompleted = (Button) findViewById(R.id.completed);
        btcompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = btcompleted.getText().toString();
                uploadTechCar();
                //Toast.makeText(TechCarRepair.this, "Completed Successfully..", Toast.LENGTH_SHORT).show();

            }
        });
//        btpending = (Button)findViewById(R.id.pending);
//        btpending.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                status = btpending.getText().toString();
//                Log.d("Button pending = ",status);
//                uploadTechCar();
//            }
//        });

    }
    private void uploadTechCar(){
        String type=spinnerT.getSelectedItem().toString().toUpperCase();
        String pos=spinnerP.getSelectedItem().toString();
        String bcom = component.getText().toString();
        if (type.equals("--CHOOSE TYPE--")) {
            Toast.makeText(getApplicationContext(), "Please select ServiceType..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pos.equals("--Select Position--")) {
            Toast.makeText(getApplicationContext(), "Please select Position..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(s)&& bcom.equals("--select Component--")) {
            Toast.makeText(getApplicationContext(), "Please select Component..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!type.equals("--CHOOSE TYPE--") && !pos.equals("--Select Position--")) {
            techUploadCar = new TechUploadPlum(filter, filter1, filter2, filter3, date, status, filter4, pos, type, s,filter5);
            String id = databaseCar.push().getKey();
            databaseCar.child(id).setValue(techUploadCar);
            try {
                Intent intent = new Intent(this,Tech_car.class);
                startActivity(intent);
                Toast.makeText(TechCarRepair.this, "Attended Successfully..", Toast.LENGTH_SHORT).show();
                finish();
            }catch (Error e){
                e.printStackTrace();
            }

        }
    }

    private void repairListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        listDataHeader.add("DR");
        listDataHeader.add("WD");
        listDataHeader.add("CB");
        listDataHeader.add("DK");

        // Adding child data
        List<String> DR = new ArrayList<String>();
        DR.add("FP");
        DR.add("ST");
        DR.add("FX");
        DR.add("TB");
        DR.add("HB");
        DR.add("AD");
        DR.add("LK");
        DR.add("PL");

        List<String> WD = new ArrayList<String>();
        WD.add("FM");
        WD.add("HB");
        WD.add("TB");
        WD.add("GB");

        List<String> CB = new ArrayList<String>();
        CB.add("HB");
        CB.add("DM");
        CB.add("DL");

        List<String>  DK= new ArrayList<String>();
        DK.add("TM");

        listDataChild.put(listDataHeader.get(0),DR ); // Header, Child data
        listDataChild.put(listDataHeader.get(1), WD);
        listDataChild.put(listDataHeader.get(2), CB);
        listDataChild.put(listDataHeader.get(3), DK);
    }

}
