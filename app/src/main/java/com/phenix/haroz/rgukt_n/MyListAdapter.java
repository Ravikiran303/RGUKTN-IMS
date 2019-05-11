//package com.phenix.haroz.rgukt_n;
//
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class MyListAdapter  extends ArrayAdapter<TechUploadPlum> {
//    private List<TechUploadPlum> list;
//    private Context mContext;
//    private int resource;
//
//
//
//    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        //we need to get the view of the xml for our list item
//        //And for this we need a layoutinflater
//        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//
//        //getting the view
//        View view = layoutInflater.inflate(resource, null, false);
//        TextView Compo = (TextView) view.findViewById(R.id.thing);
//        //block = (TextView)view.findViewById(R.id.bfr);
//        TextView update = (TextView)view.findViewById(R.id.u_date);
//        TextView soldate = (TextView)view.findViewById(R.id.S_date);
//        TextView status = (TextView)view.findViewById(R.id.status_s);
//
//        //getting the hero of the specified position
//        TechUploadPlum hero = list.get(position);
//
//        //adding values to the list item
//        Compo.setText(hero.getComp());
//        update.setText(hero.getUpdate());
//        soldate.setText(hero.getAttenddate());
//        status.setText(hero.getStatus());
//
//        return view;
//    }
//}
//
//
//
//// extends RecyclerView.Adapter<MyListAdapter.MyView> {
////private List<TechUploadPlum> list;
////private Context context;
////
////public MyListAdapter(Context mContext,List<TechUploadPlum> mlist) {
////        this.list = mlist;
////        this.context = mContext;
////        }
////
////@NonNull
////@Override
////public MyListAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(context).inflate(R.layout.admin_info, parent, false);
////        return new MyView(view);
////        }
////
////@Override
////public void onBindViewHolder(@NonNull MyListAdapter.MyView holder, int position) {
////        TechUploadPlum techUploadPlum = list.get(position);
////        holder.Compo.setText(techUploadPlum.getPcomp());
////        holder.block.setText(techUploadPlum.getBlock());
////        holder.floor.setText(techUploadPlum.getFloor());
////        holder.room.setText(techUploadPlum.getRoom());
////        holder.status.setText(techUploadPlum.getStatus());
////        }
////
////@Override
////public int getItemCount() {
////        return list.size();
////        }
////
////public class MyView extends RecyclerView.ViewHolder {
////    private TextView Compo, block, floor, room, status;
////
////    public MyView(View itemView) {
////        super(itemView);
////        Compo = (TextView) itemView.findViewById(R.id.compo);
////        block = (TextView) itemView.findViewById(R.id.block);
////        floor = (TextView) itemView.findViewById(R.id.floor);
////        room = (TextView) itemView.findViewById(R.id.room);
////        status = (TextView) itemView.findViewById(R.id.status);
////        }
////    }
////}
//
//
//
//
//
//
//
