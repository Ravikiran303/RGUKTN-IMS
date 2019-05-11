package com.phenix.haroz.rgukt_n;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AdminServices extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    private Button plumber,carpentar,electrical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

        plumber = (Button)findViewById(R.id.button2);
        carpentar = (Button)findViewById(R.id.button3);
        electrical = (Button)findViewById(R.id.button4);

//        plumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminServices.this,AdminPlumber.class);
//                startActivity(intent);
//
//            }
//        });
//        carpentar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminServices.this,AdminCarpentar.class);
//                startActivity(intent);
//
//            }
//        });
//        electrical.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminServices.this,AdminElectrical.class);
//                startActivity(intent);
//            }
//        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adminsignout, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.admin_signout:
                Intent i = new Intent(AdminServices.this, AdminLogin.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
