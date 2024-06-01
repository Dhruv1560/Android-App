package com.example.dhruvpatel.login;

import android.content.Context;
import android.net.Uri;
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

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ImageViewHolder> {

    public Context context;
    public List<UploadCarDetail> details;
    public OnItemClickListener mlistener;

    public CarAdapter(Context context,List<UploadCarDetail> details){
        this.context=context;
        this.details=details;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_car_fetch,viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int i) {

        UploadCarDetail currentdetail = details.get(i);
        holder.carname.setText(currentdetail.getcarname());
        holder.carseater.setText(currentdetail.getcarseater());
        holder.cartype.setText(currentdetail.getcartype());
        holder.caryear.setText(currentdetail.getcaryear());
        holder.carprice.setText(currentdetail.getcarprice());
        Picasso.with(context)
                .load(currentdetail.getImageurl())
                .centerCrop()
                .fit()
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return details.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener {

        public TextView carname,carseater,cartype,caryear,carprice;
        public ImageView image;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            carname=itemView.findViewById(R.id.card_car_name);
            carseater=itemView.findViewById(R.id.card_car_seater);
            cartype=itemView.findViewById(R.id.card_car_type);
            caryear=itemView.findViewById(R.id.card_car_year);
            carprice=itemView.findViewById(R.id.card_car_price);
            image=itemView.findViewById(R.id.card_car_image);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

            if(mlistener!=null){
                int position= getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                       mlistener.onItemClick(position);
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
                int position= getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){

                    switch (menuItem.getItemId()){
                        case 1: mlistener.onwhateverClick(position);
                        return true;


                        case 2: mlistener.OnDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onwhateverClick(int position);
        void OnDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }
}
