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

public class Tech_services extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private DatabaseReference databaseElect;
    private RecyclerView recyclerViewE;
    private FirebaseRecyclerAdapter<UploadImageElect, EleViewHolder> mFirebaseElect;
    private FirebaseRecyclerOptions persons;
    private AutoCompleteTextView textView;
    private String technician;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_services);

        recyclerViewE = findViewById(R.id.listView);
        recyclerViewE.setHasFixedSize(true);
        recyclerViewE.setLayoutManager(new LinearLayoutManager(this));

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        technician = bundle.getString("Tech").toString().toUpperCase();

        //textView = (AutoCompleteTextView)findViewById(R.id.searchbar);
        electrician();
    }
    private void electrician(){

        databaseElect = FirebaseDatabase.getInstance().getReference("ElectricalWork");

        Query personsE = databaseElect.orderByChild("blockname");
        persons = new FirebaseRecyclerOptions.Builder<UploadImageElect>().setQuery(personsE, UploadImageElect.class).build();
        mFirebaseElect = new FirebaseRecyclerAdapter<UploadImageElect, EleViewHolder>(persons) {
            @NonNull
            @Override
            public EleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater. from(parent.getContext()).inflate(R.layout.tech_info_e, parent, false);
                return new EleViewHolder(itemView);
            }
            protected void onBindViewHolder(@NonNull EleViewHolder holder, final int posi, @NonNull final UploadImageElect elect) {
        holder.eleComponent.setText(elect.getEleComponent());
        holder.Block.setText("B -"+elect.getBlockname());
        holder.Floor.setText("F -"+elect.getFloorno());
        holder.Room.setText("R -"+elect.getRoomno());
        holder.up_Date.setText(elect.getDate_Time());
        holder.mail.setText(elect.getMail());
        Picasso.with(Tech_services.this).load(elect.getEurl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.img);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tech_services.this);
                builder.setMessage("Are you sure you want to delete?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int Item = posi;
                                mFirebaseElect.getRef(Item).removeValue();
                                mFirebaseElect.notifyItemRemoved(Item);
                                mFirebaseElect.notifyDataSetChanged();
                                recyclerViewE.invalidate();
                                finish();
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
                return false;
            }
        });

        holder.repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tech_services.this);
                builder.setMessage("Do you want to Check this Work ?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Intent intent = new Intent(Tech_services.this,TechEleRepair.class);
                                    intent.putExtra("Block",elect.getBlockname());
                                    intent.putExtra("Floor",elect.getFloorno());
                                    intent.putExtra("Room",elect.getRoomno());
                                    intent.putExtra("Up_time",elect.getDate_Time());
                                    intent.putExtra("Lmail",elect.getMail());
                                    intent.putExtra("type",elect.getServiceType());
                                    startActivity(intent);
                                    int Item_po = posi;
                                    mFirebaseElect.getRef(Item_po).removeValue();
                                    mFirebaseElect.notifyItemRemoved(Item_po);
                                    mFirebaseElect.notifyDataSetChanged();
                                    mFirebaseElect.notifyDataSetChanged();
                                    recyclerViewE.invalidate();
                                }catch (Error error){
                                    error.printStackTrace();
                                }

                                //recyclerViewE.invalidate();
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

        holder.pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tech_services.this);
                builder.setMessage("Do you want to Attend this data ?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Tech_services.this,PendingElec.class);
                                intent.putExtra("Block",elect.getBlockname());
                                intent.putExtra("Floor",elect.getFloorno());
                                intent.putExtra("Room",elect.getRoomno());
                                intent.putExtra("Up_time",elect.getDate_Time());
                                intent.putExtra("Lmail",elect.getMail());
                                intent.putExtra("type",elect.getServiceType());
                                startActivity(intent);
//                                int Item = posi;
//                                mFirebaseElect.getRef(Item).removeValue();
//                                mFirebaseElect.notifyItemRemoved(Item);
//                                mFirebaseElect.notifyDataSetChanged();
//                                recyclerViewE.invalidate();
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
        recyclerViewE.setAdapter(mFirebaseElect);
    }@Override
    public void onStart() {
        super.onStart();
        mFirebaseElect.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseElect.stopListening();
    }
//    private void doTheAutoRefresh() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Write code for your refresh logic
//                electrician();
//            }
//        }, 5000);
//    }
    private class EleViewHolder extends RecyclerView.ViewHolder{
            private final TextView eleComponent;
            private final TextView Block;
            private final TextView Floor;
            private final TextView Room;
            private final Button repair;
            private final Button pending;
            private final TextView up_Date;
            private final TextView mail;
            private final ImageView img;

            public EleViewHolder(final View itemView) {
                super(itemView);

                eleComponent = (TextView)itemView.findViewById(R.id.component);
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
                Intent i = new Intent(Tech_services.this, tech_login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
