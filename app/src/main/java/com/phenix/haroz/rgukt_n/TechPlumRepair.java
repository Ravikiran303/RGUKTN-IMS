package com.phenix.haroz.rgukt_n;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class TechPlumRepair extends AppCompatActivity{
    private ExpandableListView expandableListView;
    private ExpandableListAdapterPlum listAdapter;
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
    private DatabaseReference databasePlumber;
    TechUploadPlum techUploadPlum;
    private String status;
    private String s;
    private Spinner spinnerT,spinnerP;
    private String filter,filter1,filter2,filter3;
    private String filter4,filter5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_plum_repair);

        databasePlumber = FirebaseDatabase.getInstance().getReference("TechWork");

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
        //spinner = (Spinner) findViewById(R.id.spinner2);

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
                uploadTechPlum();


            }
        });
//        btpending = (Button)findViewById(R.id.pending);
//        btpending.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                status = btpending.getText().toString();
//                Log.d("Button pending = ",status);
//                uploadTechPlum();
//                //Toast.makeText(TechPlumRepair.this, "Attended Successfully..", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    private void uploadTechPlum(){
        final String type=spinnerT.getSelectedItem().toString().toUpperCase();
        final String pos=spinnerP.getSelectedItem().toString();
        final String bcom = component.getText().toString();
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
            techUploadPlum = new TechUploadPlum(filter, filter1, filter2, filter3, date, status, filter4, pos, type, s,filter5);
            String id = databasePlumber.push().getKey();
            databasePlumber.child(id).setValue(techUploadPlum);
            try {
                Intent intent = new Intent(TechPlumRepair.this,Tech_plum.class);
                startActivity(intent);
                //Tech_plum tech_plum = new Tech_plum();
                Toast.makeText(TechPlumRepair.this, "Attended Successfully..", Toast.LENGTH_SHORT).show();
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
        listDataHeader.add("TP");
        listDataHeader.add("WB");
        listDataHeader.add("UN");
        listDataHeader.add("LV");
        listDataHeader.add("WC");

        // Adding child data
        List<String> top = new ArrayList<String>();
        top.add("SP");
        top.add("TH");
        top.add("EN");

        List<String> washbasin = new ArrayList<String>();
        washbasin.add("WP");
        washbasin.add("RB");
        washbasin.add("HT");
        washbasin.add("CG");
        washbasin.add("PC");
        washbasin.add("AC");
        washbasin.add("RW");
        washbasin.add("SP");
        washbasin.add("NT");

        List<String> URINALS = new ArrayList<String>();
        URINALS.add("WP");
        URINALS.add("SC");
        URINALS.add("PW");
        URINALS.add("PK");
        URINALS.add("CP");

        List<String> WATERCLOSET = new ArrayList<String>();
        WATERCLOSET.add("WP");
        WATERCLOSET.add("CP");
        WATERCLOSET.add("ST");

        List<String> WATERCOOLER = new ArrayList<String>();
        WATERCOOLER.add("FV");
        WATERCOOLER.add("CP");
        WATERCOOLER.add("PT");
        WATERCOOLER.add("AC");

        listDataChild.put(listDataHeader.get(0), top); // Header, Child data
        listDataChild.put(listDataHeader.get(1), washbasin);
        listDataChild.put(listDataHeader.get(2), URINALS);
        listDataChild.put(listDataHeader.get(3), WATERCLOSET);
        listDataChild.put(listDataHeader.get(4), WATERCOOLER);
    }

//    @Override
//    public void senddata(String success) {
//
//    }
}
