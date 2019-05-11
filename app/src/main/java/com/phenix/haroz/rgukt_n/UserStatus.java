package com.phenix.haroz.rgukt_n;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserStatus extends AppCompatActivity {
    private ListView listViewStatus;
    private DatabaseReference database;
    ArrayList<String> arrayListT;
    ArrayAdapter<String> arrayAdapterT;
    TechUploadPlum techUploadPlumT;
    Login login;
    private String logmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);

        listViewStatus = (ListView)findViewById(R.id.listViewStatus);
        database = FirebaseDatabase.getInstance().getReference("TechWork");
        arrayListT = new ArrayList<>();
        arrayAdapterT = new ArrayAdapter<String>(this,R.layout.user_list,R.id.thing,arrayListT);
        techUploadPlumT = new TechUploadPlum();

        login = new Login();
        logmail = login.store;
        Log.d("logged mail::",logmail);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    techUploadPlumT = ds.getValue(TechUploadPlum.class);
                    if (techUploadPlumT.getUpMail().toString().equals(logmail)) {
                        try {
                            if (techUploadPlumT != null) {
                                arrayListT.add("U-D : " + techUploadPlumT.getUpdate().trim().toString() + " | " + "S-D : " + techUploadPlumT.getAttenddate().toString()  + " | " + techUploadPlumT.getStatus().toString()+" | "+techUploadPlumT.getSer_type().toString());
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }
                listViewStatus.setAdapter(arrayAdapterT);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
