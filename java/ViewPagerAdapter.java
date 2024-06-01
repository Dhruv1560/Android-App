package com.example.dhruvpatel.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;


    LayoutInflater layoutInflater;
    int[] image={R.drawable.elantra,R.drawable.bmw,R.drawable.fortuner,R.drawable.jaguar,R.drawable.landlover,R.drawable.swift};
    String[] name={"Elantra","Bmw","Fortuner","Jaguar","Landrover","Swift"};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout_slider,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv);
        TextView textView =(TextView)view.findViewById(R.id.tv);
        textView.setText(name[position]);
        imageView.setImageResource(image[position]);

        

        ViewPager vp = (ViewPager)container;
        vp.addView(view,0);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager)container;
        View view = (View)object;
        vp.removeView(view);
    }
}
