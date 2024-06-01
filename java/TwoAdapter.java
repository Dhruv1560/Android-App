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

public class TwoAdapter extends RecyclerView.Adapter<TwoAdapter.ImageviewHolder> {

    public Context context;
    public List<TwoList> twoLists;


    public TwoAdapter(MainActivityTwo mainActivityTwo, List<TwoList> twoLists) {
        this.context = mainActivityTwo;
        this.twoLists = twoLists;
    }

    @NonNull
    @Override
    public ImageviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v_two=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_main_two,viewGroup,false);
        return new ImageviewHolder(v_two);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageviewHolder holder, int i) {
        final TwoList currentdetailtwo = twoLists.get(i);
        holder.pedlname.setText(currentdetailtwo.getpedlname());
        holder.pedlyear.setText(currentdetailtwo.getpedlyear());
        holder.pedlcc.setText(currentdetailtwo.getpedlcc());
        holder.pedlprice.setText(currentdetailtwo.getpedlprice());
        Picasso.with(context).load(currentdetailtwo.getImageurl()).fit().into(holder.pedlimage);
        holder.book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof MainActivityTwo){
                    ((MainActivityTwo)context).generateCheckSum(currentdetailtwo.getpedlprice());
                    ((MainActivityTwo)context).UploadPedlName(currentdetailtwo.getpedlname());
                }
                else Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return twoLists.size();
    }

    public class ImageviewHolder extends RecyclerView.ViewHolder {

        public TextView pedlname,pedlyear,pedlcc,pedlprice;
        public ImageView pedlimage;
        public Button book_btn;

        public ImageviewHolder(@NonNull View itemView) {
            super(itemView);


            pedlname=itemView.findViewById(R.id.custom_main_name_two);
            pedlyear=itemView.findViewById(R.id.custom_main_year_two);
            pedlcc=itemView.findViewById(R.id.custom_main_cc_two);
            pedlprice=itemView.findViewById(R.id.custom_main_price_two);
            pedlimage=itemView.findViewById(R.id.custom_main_image_two);
            book_btn=itemView.findViewById(R.id.custom_main_button_book_two);


        }
    }

}

/*class TwoAdapter extends BaseAdapter {

    Context context;
    ArrayList<TwoList> twoList;
    LayoutInflater layoutInflater;

    public TwoAdapter(MainActivityTwo mainActivityTwo, ArrayList<TwoList> twoList) {
        this.context=mainActivityTwo;
        this.twoList=twoList;
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return twoList.size();
    }

    @Override
    public Object getItem(int i) {
        return twoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.custom_two,null);
        ImageView iv= view.findViewById(R.id.custom_main_iv_two);
        TextView tv1 = view.findViewById(R.id.custom_main_name_two );
        TextView tv2 = view.findViewById(R.id.custom_main_cc_two);
        TextView tv3 = view.findViewById(R.id.custom_main_price_two);

        iv.setImageResource(twoList.get(i).getImage());
        tv1.setText(twoList.get(i).getName());
        tv2.setText(twoList.get(i).getCc());
        tv3.setText(twoList.get(i).getPrice());

        return view ;
    }
}*/

