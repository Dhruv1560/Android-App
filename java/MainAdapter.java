package com.example.dhruvpatel.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ImageViewHolder> {

    public Context context;
    public List<MainList> mainList;


    public MainAdapter(MainActivityFour mainActivityFour,List<MainList> mainList){
        this.context=mainActivityFour;
        this.mainList=mainList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cutom_main_four,viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int i) {

        final MainList currentdetail = mainList.get(i);
        holder.carname.setText(currentdetail.getcarname());
        holder.carseater.setText(currentdetail.getcarseater());
        holder.cartype.setText(currentdetail.getcartype());
        holder.caryear.setText(currentdetail.getcaryear());
        holder.carprice.setText(currentdetail.getcarprice());
        Picasso.with(context)
                .load(currentdetail.getImageurl())
                .fit()
                .into(holder.image);

        holder.book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(context instanceof MainActivityFour){
                   ((MainActivityFour)context).generateCheckSum(currentdetail.getcarprice());
                   ((MainActivityFour)context).UploadCarName(currentdetail.getcarname());
               }
               else Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
                //PaytmPayment pm = new PaytmPayment();
                //pm.generateCheckSum(car_price);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder  {

        public TextView carname,carseater,cartype,caryear,carprice;
        public ImageView image;
        public Button book_btn;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            carname=itemView.findViewById(R.id.custom_main_name_four);
            carseater=itemView.findViewById(R.id.custom_main_seater_four);
            cartype=itemView.findViewById(R.id.custom_main_type_four);
            caryear=itemView.findViewById(R.id.custom_main_year_four);
            carprice=itemView.findViewById(R.id.custom_main_price_four);
            image=itemView.findViewById(R.id.custom_main_image_four);

            book_btn=itemView.findViewById(R.id.custom_main_button_book_four);
            //book_btn.setOnClickListener(this);



        }


        /*@Override
        public void onClick(View itemView) {
            z=getAdapterPosition();

            car_price=mainList.get(z).getcarprice();
            car_name=mainList.get(z).getcarname();


            PaytmPayment pm = new PaytmPayment();
            pm.generateCheckSum(car_price);
            //Toast.makeText(context, "Confirmation Of "+car_name+" Getting Soon",Toast.LENGTH_SHORT).show();
        }*/
    }


}


/*class MainAdapter extends BaseAdapter {

    Context context;
    List<UploadCarDetail> mainList;
    LayoutInflater layoutInflater;

    public MainAdapter(MainActivityFour mainActivityFour, List<UploadCarDetail> mainList) {
              this.context=mainActivityFour;
              this.mainList=mainList;
              layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mainList.size();
    }

    @Override
    public Object getItem(int i) {
        return mainList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.cutom_main_four,null);
        ImageView iv= view.findViewById(R.id.custom_main_image_four);
        TextView tv1 = view.findViewById(R.id.custom_main_name_four);
        TextView tv2 = view.findViewById(R.id.custom_main_seater_four);
        TextView tv3 = view.findViewById(R.id.custom_main_year_four);
        TextView tv4 = view.findViewById(R.id.custom_main_type_four);
        TextView tv5 = view.findViewById(R.id.custom_main_price_four);

        //iv.setImageResource(mainList.get(i).getImageurl());
        Picasso.with(context).load(mainList.get(i).getImageurl()).into(iv);
        tv1.setText(mainList.get(i).getcarname());
        tv2.setText(mainList.get(i).getcarseater());
        tv3.setText(mainList.get(i).getcaryear());
        tv4.setText(mainList.get(i).getcartype());
        tv5.setText(mainList.get(i).getcarprice());

        return view ;
    }
}*/
