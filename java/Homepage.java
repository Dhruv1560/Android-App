package com.example.dhruvpatel.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reff;
    TextView data_fname,data_number,data_lname;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ImageView four = (ImageView)findViewById(R.id.homepage_car);
        ImageView two = (ImageView)findViewById(R.id.homepage_pedl);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();




        retrieveData();



                /*signout.setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View view) {

                           Logout();
                           }
                   });*/

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c= new Intent(Homepage.this,DatePickerCar.class);
                startActivity(c);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d = new Intent(Homepage.this,DatePickerPedl.class);
                startActivity(d);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        data_fname=(TextView)header.findViewById(R.id.nav_fname);
        data_number=(TextView)header.findViewById(R.id.nav_number);
        //data_lname=(TextView)header.findViewById(R.id.nav_lname);
        //name = (TextView)header.findViewById(R.id.username);
        //name.setText(personName);

    }
    public void retrieveData(){

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();

        reff = FirebaseDatabase.getInstance().getReference("Register").child(userID);


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String d_fname= dataSnapshot.child("fname").getValue().toString();
                String d_lname= dataSnapshot.child("lname").getValue().toString();
                String d_number=dataSnapshot.child("mobileno").getValue().toString();

                String username = d_fname+" "+d_lname;

                Toast.makeText(Homepage.this, "Welcome "+username, Toast.LENGTH_SHORT).show();
                //Toast.makeText(Homepage.this, ""+d_number, Toast.LENGTH_SHORT).show();
                data_fname.setText(username);
                //data_lname.setText(d_lname);
                data_number.setText(d_number);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Homepage.this, "Error in fetch", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void Logout(){

        firebaseAuth.signOut();
        finish();
        Intent a = new Intent(Homepage.this,MainActivity.class);
        startActivity(a);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id  = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logoutMenu) {
            Logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_changecity) {
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {
            Intent c =new Intent(Homepage.this,SliderActivity.class);
            startActivity(c);

        } else if (id == R.id.nav_aboutus) {
            startActivity(new Intent(Homepage.this,AboutUs.class));

        } else if (id == R.id.nav_terms) {
            Intent g=new Intent(Homepage.this,Privacy.class);
            startActivity(g);


        } else if (id == R.id.nav_share) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            String sharebody="Application not hosted";
            String sharesub = "link Coming soon";
            share.putExtra(Intent.EXTRA_SUBJECT,sharebody);
            share.putExtra(Intent.EXTRA_TEXT,sharesub);
            startActivity(Intent.createChooser(share,"Share Using"));

        } else if (id == R.id.nav_helpandsupport) {
            startActivity(new Intent(Homepage.this,AboutUs.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
