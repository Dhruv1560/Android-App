package com.example.dhruvpatel.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    ImageView image;
    //TextView tx1,tx2,tx3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //getSupportActionBar().hide();

        //LogoLauncher logoLauncher= new LogoLauncher();
        //logoLauncher.start();
        image=findViewById(R.id.imageView2);
       // tx1=findViewById(R.id.textView);
        //tx2=findViewById(R.id.textView2);
        //tx3=findViewById(R.id.textView3);

        AlphaAnimation imgAnimation = new AlphaAnimation(0, 1);
        imgAnimation.setDuration(2000);
        image.setAnimation(imgAnimation);

       /* AlphaAnimation txoneAnimation = new AlphaAnimation(0, 1);
        txoneAnimation.setDuration(2000);
        tx1.setAnimation(txoneAnimation);

        AlphaAnimation txtwoAnimation = new AlphaAnimation(0, 1);
        txtwoAnimation.setDuration(2000);
        tx2.setAnimation(txtwoAnimation);

        AlphaAnimation txthreeAnimation = new AlphaAnimation(0, 1);
        txthreeAnimation.setDuration(2000);
        tx3.setAnimation(txthreeAnimation);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent f= new Intent(SplashActivity.this,MainActivity.class);
                startActivity(f);

                finish();
            }
        },2500);



    }
    /*private class LogoLauncher extends  Thread{

        Thread thread = new Thread();

        public void run(){

            try{
                sleep(2000);
                Intent f= new Intent(SplashActivity.this,MainActivity.class);
                startActivity(f);
                SplashActivity.this.finish();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }*/
}
