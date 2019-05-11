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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Tech_plum extends AppCompatActivity{
    boolean doubleBackToExitPressedOnce = false;
    private RecyclerView recyclerViewP;
    private DatabaseReference databasePlumber;
    private FirebaseRecyclerAdapter<UploadImagePlumber, ImageViewHolder> mFirebaseAdapter;
    private FirebaseRecyclerOptions personsOptions;
    private AutoCompleteTextView textView;
    //private ArrayList<UploadImagePlumber> search;
    private ImageButton imageButton;
    //private String technician;
    private final Handler handler = new Handler();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_plum);

        recyclerViewP = findViewById(R.id.listView);
        recyclerViewP.setHasFixedSize(true);
        recyclerViewP.setLayoutManager(new LinearLayoutManager(this));

        plumberService();
    }
    private void plumberService(){
        databasePlumber = FirebaseDatabase.getInstance().getReference("PlumbingWork");

        final Query personsQuery = databasePlumber.orderByChild("blockname");
        //.startAt(searchText).endAt(searchText + "\uf8ff");


        personsOptions = new FirebaseRecyclerOptions.Builder<UploadImagePlumber>().setQuery(personsQuery, UploadImagePlumber.class).build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<UploadImagePlumber,ImageViewHolder>(personsOptions){

            @NonNull
            @Override
            public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater. from(parent.getContext()).inflate(R.layout.tech_info, parent, false);
                return new ImageViewHolder(itemView);
            }
            @Override
            protected void onBindViewHolder(@NonNull ImageViewHolder holder, final int position, @NonNull final UploadImagePlumber model) {
                final int selected_pos = position;
                holder.pluComponent.setText(model.getPlumComponent());
                holder.Block.setText("B-"+model.getBlockname());
                holder.Floor.setText("F -"+model.getFloorno());
                holder.Room.setText("R -"+model.getRoomno());
                holder.up_Date.setText(model.getDate_Time());
                holder.mail.setText(model.getMail());
                Picasso.with(Tech_plum.this).load(model.getUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.img);

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Tech_plum.this);
                        builder.setMessage("Are you sure you want to delete ?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            int selected_Items = position;
                                            mFirebaseAdapter.getRef(selected_Items).removeValue();
                                            mFirebaseAdapter.notifyItemRemoved(selected_Items);
                                            mFirebaseAdapter.notifyDataSetChanged();
                                            recyclerViewP.invalidate();
                                            finish();
                                        }catch (Error e){
                                            e.printStackTrace();
                                        }

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(Tech_plum.this);
                        builder.setMessage("Do you want to Attend this Work ?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            Intent intent = new Intent(Tech_plum.this,TechPlumRepair.class);
                                            intent.putExtra("Block",model.getBlockname());
                                            intent.putExtra("Floor",model.getFloorno());
                                            intent.putExtra("Room",model.getRoomno());
                                            intent.putExtra("Up_time",model.getDate_Time());
                                            intent.putExtra("Lmail",model.getMail());
                                            intent.putExtra("type",model.getServiceType());
                                            startActivity(intent);
                                            int selected_Items = position;
                                            mFirebaseAdapter.getRef(selected_Items).removeValue();
                                            mFirebaseAdapter.notifyItemRemoved(selected_Items);
                                            mFirebaseAdapter.notifyDataSetChanged();
                                            recyclerViewP.invalidate();
                                        }catch (Error e){
                                            e.printStackTrace();
                                        }

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(Tech_plum.this);
                        builder.setMessage("Do you want to Attend this data ?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Tech_plum.this,PendingTech.class);
                                        intent.putExtra("Block",model.getBlockname());
                                        intent.putExtra("Floor",model.getFloorno());
                                        intent.putExtra("Room",model.getRoomno());
                                        intent.putExtra("Up_time",model.getDate_Time());
                                        intent.putExtra("Lmail",model.getMail());
                                        intent.putExtra("type",model.getServiceType());
                                        startActivity(intent);
//                                        int selectedItems = position;
//                                        mFirebaseAdapter.getRef(selectedItems).removeValue();
//                                        mFirebaseAdapter.notifyItemRemoved(selectedItems);
//                                        mFirebaseAdapter.notifyDataSetChanged();
//                                        recyclerViewP.invalidate();
                                        // onStart();
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

        recyclerViewP.setAdapter(mFirebaseAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
    }


    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private final TextView pluComponent;
        private final TextView Block;
        private final TextView Floor;
        private final TextView Room;
        private final Button repair;
        private final Button pending;
        private final TextView up_Date;
        private final TextView mail;
        private final ImageView img;

        public ImageViewHolder(View itemView) {
            super(itemView);

            pluComponent = (TextView)itemView.findViewById(R.id.component);
            Block = (TextView)itemView.findViewById(R.id.techinfo);
            Floor = (TextView)itemView.findViewById(R.id.Floor);
            Room = (TextView)itemView.findViewById(R.id.Room);
            img = (ImageView)itemView.findViewById(R.id.imageView);
            repair = (Button)itemView.findViewById(R.id.Repair);
            up_Date = (TextView)itemView.findViewById(R.id.up_date);
            mail = (TextView)itemView.findViewById(R.id.mail);
            pending = (Button)itemView.findViewById(R.id.delete);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.techsignout, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.signout:
                Intent i = new Intent(Tech_plum.this, tech_login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



