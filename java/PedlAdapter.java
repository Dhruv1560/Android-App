package com.example.dhruvpatel.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class PedlAdapter extends RecyclerView.Adapter<PedlAdapter.ImageViewHolder> {

    Context context;
    List<UploadPedlDetail> detail_pedl;
    OnItemClickListener mlistener;

    public PedlAdapter(Context context, List<UploadPedlDetail> detail_pedl) {
        this.context = context;
        this.detail_pedl = detail_pedl;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v_pedl=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_pedl_fetch,viewGroup,false);
        return new ImageViewHolder(v_pedl);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int i) {
        UploadPedlDetail currentdetail = detail_pedl.get(i);
        holder.pedlname.setText(currentdetail.getpedlname());
        holder.pedlcc.setText(currentdetail.getpedlcc());
        holder.pedlyear.setText(currentdetail.getpedlyear());
        holder.pedlprice.setText(currentdetail.getpedlprice());
        Picasso.with(context).load(currentdetail.getImageUrl()).into(holder.pedlimage);

    }

    @Override
    public int getItemCount() {
        return detail_pedl.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            ,View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener {

        public TextView pedlname,pedlcc,pedlyear,pedlprice;
        public ImageView pedlimage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            pedlname=itemView.findViewById(R.id.card_pedl_name);
            pedlcc=itemView.findViewById(R.id.card_pedl_cc);
            pedlyear=itemView.findViewById(R.id.card_pedl_year);
            pedlprice=itemView.findViewById(R.id.card_pedl_price);
            pedlimage=itemView.findViewById(R.id.card_pedl_image);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mlistener!=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                     mlistener.OnItemClick(position);
                }
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem dowhatever = contextMenu.add(Menu.NONE,1,1,"Do whatever");
            MenuItem delete = contextMenu.add(Menu.NONE,2,2,"Delete");

            dowhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mlistener!=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){

                    switch (menuItem.getItemId()){

                        case 1: mlistener.OnWhateverClick(position);
                             return true;
                        case 2: mlistener.OnDeleteClick(position);
                            return true;


                    }

                }
            }
            return false;
        }
    }
    public  interface OnItemClickListener{
        void OnItemClick(int position);
        void OnWhateverClick(int position);
        void OnDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;

    }
}
