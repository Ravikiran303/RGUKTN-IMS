package com.phenix.haroz.rgukt_n;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Tech_car extends AppCompatActivity {
    private DatabaseReference databaseCarpentar;
    boolean doubleBackToExitPressedOnce = false;
    private RecyclerView recyclerViewC;
    private FirebaseRecyclerAdapter<UploadImageCarpentar, CarViewHolder> mFirebase;
    private FirebaseRecyclerOptions personsOptions;
    private Query persons;
    private AutoCompleteTextView textView;
    private String technician;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_car);

        recyclerViewC = findViewById(R.id.listView);
        recyclerViewC.setHasFixedSize(true);
        recyclerViewC.setLayoutManager(new LinearLayoutManager(this));

        carpentry();
    }
    private void carpentry(){

        databaseCarpentar = FirebaseDatabase.getInstance().getReference("CarpentryWork");
        persons = databaseCarpentar.orderByChild("blockname");

        personsOptions = new FirebaseRecyclerOptions.Builder<UploadImageCarpentar>().setQuery(persons, UploadImageCarpentar.class).build();
        mFirebase = new FirebaseRecyclerAdapter<UploadImageCarpentar,CarViewHolder>(personsOptions){
            public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater. from(parent.getContext()).inflate(R.layout.tech_info_c, parent, false);
                return new CarViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull CarViewHolder holder, final int i, @NonNull final UploadImageCarpentar carpentar) {
                //Log.d("Position - ", String.valueOf(holder.getAdapterPosition()));
                holder.carComponent.setText(carpentar.getCarComponent());
                holder.Block.setText("B-"+carpentar.getBlockname());
                holder.Floor.setText("F -"+carpentar.getFloorno());
                holder.Room.setText("R -"+carpentar.getRoomno());
                holder.up_Date.setText(carpentar.getDate_Time());
                holder.mail.setText(carpentar.getMail());
                Picasso.with(Tech_car.this).load(carpentar.getCurl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.img);

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Tech_car.this);
                        builder.setMessage("Are you sure you want to delete ?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int Items = i;
                                        mFirebase.getRef(Items).removeValue();
                                        mFirebase.notifyItemRemoved(Items);
                                        mFirebase.notifyDataSetChanged();
                                        recyclerViewC.invalidate();
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confirm");
                        dialog.show();
                        return false;
                    }
                });


                holder.repair.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Tech_car.this);
                        builder.setMessage("Do you want to Attend this Work ?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            Intent intent = new Intent(Tech_car.this,TechCarRepair.class);
                                            intent.putExtra("Block",carpentar.getBlockname());
                                            intent.putExtra("Floor",carpentar.getFloorno());
                                            intent.putExtra("Room",carpentar.getRoomno());
                                            intent.putExtra("Up_time",carpentar.getDate_Time());
                                            intent.putExtra("Lmail",carpentar.getMail());
                                            intent.putExtra("type",carpentar.getServiceType());
                                            startActivity(intent);
                                            final int selected_pos = i;
                                            mFirebase.getRef(selected_pos).removeValue();
                                            mFirebase.notifyItemRemoved(selected_pos);
                                            mFirebase.notifyDataSetChanged();
                                            recyclerViewC.invalidate();
                                        }catch (Error r){
                                            r.printStackTrace();
                                        }

                                        //recyclerViewC.invalidate();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confirm");
                        dialog.show();
                        // Log.d("Positon -- ", String.valueOf(pos));
                    }
                });
                holder.pending.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Tech_car.this);
                        builder.setMessage("Do you want to attend this data ?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Tech_car.this,PendingCar.class);
                                        intent.putExtra("Block",carpentar.getBlockname());
                                        intent.putExtra("Floor",carpentar.getFloorno());
                                        intent.putExtra("Room",carpentar.getRoomno());
                                        intent.putExtra("Up_time",carpentar.getDate_Time());
                                        intent.putExtra("Lmail",carpentar.getMail());
                                        intent.putExtra("type",carpentar.getServiceType());
                                        startActivity(intent);
//                                        int Items = i;
//                                        mFirebase.getRef(Items).removeValue();
//                                        mFirebase.notifyItemRemoved(Items);
//                                        mFirebase.notifyDataSetChanged();
//                                        recyclerViewC.invalidate();
                                        //doTheAutoRefresh();
                                        //onStart();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confirm");
                        dialog.show();
                    }
                });
            }

        };
        recyclerViewC.setAdapter(mFirebase);
    }public void onStart() {
        super.onStart();
        mFirebase.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebase.stopListening();
    }

    private class CarViewHolder extends RecyclerView.ViewHolder {
            private final TextView carComponent;
            private final TextView Block;
            private final TextView Floor;
            private final TextView Room;
            private final Button repair;
            private final Button pending;
            private final TextView up_Date;
            private final TextView mail;
            private final ImageView img;

            public CarViewHolder(final View itemView) {
                super(itemView);

                carComponent = (TextView) itemView.findViewById(R.id.component);
                Block = (TextView) itemView.findViewById(R.id.techinfo);
                Floor = (TextView) itemView.findViewById(R.id.Floor);
                Room = (TextView) itemView.findViewById(R.id.Room);
                img = (ImageView) itemView.findViewById(R.id.imageView);
                repair = (Button) itemView.findViewById(R.id.Repair);
                up_Date = (TextView) itemView.findViewById(R.id.up_date);
                mail = (TextView) itemView.findViewById(R.id.mail);
                pending = (Button) itemView.findViewById(R.id.delete);
            }
        }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.techsignout, menu);
        return super.onCreateOptionsMenu(menu);
    }
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.signout:
                Intent i = new Intent(Tech_car.this, tech_login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
