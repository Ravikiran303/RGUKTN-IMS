package com.phenix.haroz.rgukt_n;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class tech_login extends AppCompatActivity{
    private EditText inputEmail, inputPassword;
    private FirebaseDatabase auth;
    private DatabaseReference databaseReference;
    TechLogCredentials techLogCredentials;
    private ProgressBar progressBar;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private TextView contact;
    static private String log_Tech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_login);

        auth = FirebaseDatabase.getInstance();
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        databaseReference = auth.getReference("TechLogCredentials");


       progressDialog = new ProgressDialog(this);
       progressDialog.setMessage("Loading...");

        contact = (TextView)findViewById(R.id.tvcontact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tech_login.this,Contact.class);
                startActivity(intent);
            }
        });


        btnLogin = (Button)findViewById(R.id.email_sign_in_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String encoded = EncodeString(email);
                if(haveNetworkConnection() == true) {
                    signIn(encoded, password);
                }else{
                    Toast.makeText(tech_login.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                }
           }

        });
    }
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }
    public void signIn(final String username,final String pass){

        final Pattern EMAIL_ADDRESS = Pattern.compile(
                "[a-zA-Z0-9]{1,256}" +
                        "\\@" +
                        "[rR]{1}[gG]{1}[uU]{1}[kK]{1}[tT]{1}[nN]{1}" +
                        "\\." +
                        "[aA]{1}[cC]{1}" +
                        "\\." +
                        "[iI]{1}[nN]{1}");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(EMAIL_ADDRESS.matcher(username).matches())) {

                    if (dataSnapshot.child(username).exists()) {

                        if (TextUtils.isEmpty(username)) {
                            Toast.makeText(getApplicationContext(), "Enter Your ID!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(pass)) {
                            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        progressDialog.show();

                        if (username.contains("NUZC")) {
                            techLogCredentials = dataSnapshot.child(DecodeString(username)).getValue(TechLogCredentials.class);

                            if (techLogCredentials.getPassword().equals(pass)) {
                                progressDialog.dismiss();
//                            log_Tech = dataSnapshot.child(username).toString();
//                            Log.d("Logged Tech",log_Tech);
                                Intent intent = new Intent(tech_login.this, Tech_car.class);
                                //intent.putExtra("Tech",username);
                                startActivity(intent);

                                finish();
                            } else {
                                Toast.makeText(tech_login.this, "Enter Valid Username,Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (username.contains("NUZE")) {
                            techLogCredentials = dataSnapshot.child(username).getValue(TechLogCredentials.class);
                            if (techLogCredentials.getPassword().equals(pass)) {
                                progressDialog.dismiss();
                                Intent intent = new Intent(tech_login.this, Tech_services.class);
                                //intent.putExtra("Tech",username);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(tech_login.this, "Enter Valid Username,Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (username.contains("NUZP")) {
                            techLogCredentials = dataSnapshot.child(username).getValue(TechLogCredentials.class);
                            //progressBar.setVisibility(View.GONE);
                            if (techLogCredentials.getPassword().equals(pass)) {
                                progressDialog.dismiss();
                                Intent intent = new Intent(tech_login.this, Tech_plum.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(tech_login.this, "Enter Valid Username,Password", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {

                        Toast.makeText(tech_login.this, "No Such User Found..Try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(tech_login.this, "You are not a Technician", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(tech_login.this, Technician.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}
