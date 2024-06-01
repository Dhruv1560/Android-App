package com.example.dhruvpatel.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ThirdActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    public Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        signout = (Button) findViewById(R.id.third_btn_logout);
        firebaseAuth = FirebaseAuth.getInstance();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Logout();
            }
        });
    }

    public void Logout(){

        firebaseAuth.signOut();
        finish();
        Intent z = new Intent(ThirdActivity.this,MainActivity.class);
        startActivity(z);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.logoutMenu: {
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);

    }
}
