package com.phenix.haroz.rgukt_n;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private TextView tvLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignUp = (Button) findViewById(R.id.email_sign_in_button);
        tvLogin = (TextView) findViewById(R.id.tvsignin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, Login.class);
                startActivity(intent);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

//               final Pattern emailPattern = Pattern.compile("[a-zA-Z0-9\\.]{1,256}" +
//                       "\\@" +
//                       "[rR]{1}[gG]{1}[uU]{1}[kK]{1}[tT]{1}[nN]{1}" +
//                       "\\." +
//                       "[aA]{1}[cC]{1}" +
//                       "\\." +
//                       "[iI]{1}[nN]{1}+");
                final Pattern EMAIL_ADDRESS = Pattern.compile(
                        "[a-zA-Z0-9]{1,256}" +
                                "\\@" +
                                "[rR]{1}[gG]{1}[uU]{1}[kK]{1}[tT]{1}[nN]{1}" +
                                "\\." +
                                "[aA]{1}[cC]{1}" +
                                "\\." +
                                "[iI]{1}[nN]{1}");

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }//progressBar.setVisibility(View.VISIBLE);
                progressDialog.show();
                Log.d("Internet = ", String.valueOf(haveNetworkConnection()));
                if(haveNetworkConnection() == true) {
                    if (EMAIL_ADDRESS.matcher(email).matches()) {
                        auth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                Log.d("checking==", email);
                                boolean check = !task.getResult().getProviders().isEmpty();
                                if (check) {
                                    Toast.makeText(SignupActivity.this, "Email Already Registered..", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignupActivity.this, Login.class));
                                    finish();
                                } else {
                                    auth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    //Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                                    //progressBar.setVisibility(View.GONE);
                                                    progressDialog.dismiss();
                                                    if (!task.isSuccessful()) {
                                                        Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                                                Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        //startActivity(new Intent(SignupActivity.this, Login.class));
                                                        sendVerificationEmail();
                                                        //finish();
                                                    }
                                                }
                                            });

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof FirebaseNetworkException) {
                                    Toast.makeText(SignupActivity.this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(SignupActivity.this, "Mail format should be N130303@rguktn.ac.in", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent

                            Toast.makeText(SignupActivity.this, "Verify your Email address..", Toast.LENGTH_LONG).show();
                            // after email is sent just logout the user and finish this activity
                            //FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignupActivity.this, Login.class));
                            finish();
                        } else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseNetworkException) {
                    Toast.makeText(SignupActivity.this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
                }
            }
            // Toast.makeText(SignupActivity.this, "Not present", Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
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
