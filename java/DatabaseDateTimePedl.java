package com.example.dhruvpatel.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseDateTimePedl extends AppCompatActivity {

    TextView pickup_date,dropoff_date,pickup_time,dropoff_time,location,service;
    Button confirm_pedl;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,fetchdata;
    public  String username,fname,lname,pedl_pickup_date,pedl_dropoff_date,pedl_pickup_time,pedl_dropoff_time,pedl_location,pedl_servicetype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_date_time_pedl);

        firebaseAuth = FirebaseAuth.getInstance();

        RetrieveData();

        //databaseReference = FirebaseDatabase.getInstance().getReference("Booking of pedl");


        confirm_pedl=findViewById(R.id.database_confirm_pedl);
        pickup_date = findViewById(R.id.database_pickupdate_pedl);
        dropoff_date = findViewById(R.id.database_dropoffdate_pedl);
        pickup_time= findViewById(R.id.database_pickuptime_pedl);
        dropoff_time = findViewById(R.id.database_dropofftime_pedl);
        location=findViewById(R.id.database_location_pedl);
        service=findViewById(R.id.database_service_pedl);

        Bundle pedl =getIntent().getExtras();


        pickup_date.setText(pedl.getString("pickup-date"));
        dropoff_date.setText(pedl.getString("dropoff-date"));
        pickup_time.setText(pedl.getString("pickup-time"));
        dropoff_time.setText(pedl.getString("dropoff-time"));
        location.setText(pedl.getString("location"));
        service.setText(pedl.getString("type"));

        pedl_pickup_date=pedl.getString("pickup-date");
        pedl_pickup_time=pedl.getString("pickup-time");
        pedl_dropoff_date=pedl.getString("dropoff-date");
        pedl_dropoff_time=pedl.getString("dropoff-time");
        pedl_location=pedl.getString("location");
        pedl_servicetype=pedl.getString("type");

        confirm_pedl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransferFile();
                //startActivity(new Intent(DatabaseDateTimePedl.this,MainActivityTwo.class));
                Toast.makeText(DatabaseDateTimePedl.this, "select your vehicle", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void RetrieveData() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();

        fetchdata=FirebaseDatabase.getInstance().getReference("Register").child(userId);

        fetchdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                fname = dataSnapshot.child("fname").getValue().toString();
                lname = dataSnapshot.child("lname").getValue().toString();
                username=fname+" "+lname;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DatabaseDateTimePedl.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void TransferFile() {

        //BookingPedl bookingPedl = new BookingPedl(username,pedl_pickup_date,pedl_pickup_time,pedl_dropoff_date,pedl_dropoff_time,pedl_location,pedl_servicetype);
        //String UploadID = databaseReference.push().getKey();
        //databaseReference.child(UploadID).setValue(bookingPedl);
        Intent pedl_intent=new Intent(DatabaseDateTimePedl.this,MainActivityTwo.class);
        Bundle pedl_b=new Bundle();
        pedl_b.putString("username",username);
        pedl_b.putString("pick_date",pedl_pickup_date);
        pedl_b.putString("pick_time",pedl_pickup_time);
        pedl_b.putString("drop_time",pedl_dropoff_time);
        pedl_b.putString("drop_date",pedl_dropoff_date);
        pedl_b.putString("pedllocation",pedl_location);
        pedl_b.putString("servicetype",pedl_servicetype);
        pedl_intent.putExtras(pedl_b);
        startActivity(pedl_intent);

    }

}
