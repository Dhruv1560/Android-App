package com.example.dhruvpatel.login;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class SliderActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        viewPager = (ViewPager)findViewById(R.id.viewpager);

        viewPagerAdapter = new ViewPagerAdapter(SliderActivity.this);
        viewPager.setAdapter(viewPagerAdapter);


    }
    public void OnSubscribe(View view){

        Toast.makeText(this, "You Subscribe this car", Toast.LENGTH_SHORT).show();

    }
}
