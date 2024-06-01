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

public class DatabaseDateTimeCar extends AppCompatActivity {

    TextView pickup_date,dropoff_date,pickup_time,dropoff_time,location,service;
    Button confirm_car;
    public String UploadID;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,fetchdata;
    public  String username,fname,lname,car_pickup_date,car_dropoff_date,car_pickup_time,car_dropoff_time,car_location,car_servicetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_date_time_car);

        firebaseAuth = FirebaseAuth.getInstance();

        RetrieveData();

        //databaseReference = FirebaseDatabase.getInstance().getReference("Booking of car");


        confirm_car=findViewById(R.id.database_confirm_car);
        pickup_date = findViewById(R.id.database_pickupdate_car);
        dropoff_date = findViewById(R.id.database_dropoffdate_car);
        pickup_time= findViewById(R.id.database_pickuptime_car);
        dropoff_time = findViewById(R.id.database_dropofftime_car);
        location=findViewById(R.id.database_location_car);
        service=findViewById(R.id.database_service_car);

        Bundle b =getIntent().getExtras();

        pickup_date.setText(b.getString("pickup-date"));
        dropoff_date.setText(b.getString("dropoff-date"));
        pickup_time.setText(b.getString("pickup-time"));
        dropoff_time.setText(b.getString("dropoff-time"));
        location.setText(b.getString("location"));
        service.setText(b.getString("type"));

        car_pickup_date=b.getString("pickup-date");
        car_pickup_time=b.getString("pickup-time");
        car_dropoff_date=b.getString("dropoff-date");
        car_dropoff_time=b.getString("dropoff-time");
        car_location=b.getString("location");
        car_servicetype=b.getString("type");



        confirm_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TransferFile();
                //startActivity(new Intent(DatabaseDateTimeCar.this,MainActivityFour.class));
                Toast.makeText(DatabaseDateTimeCar.this, "Select Your Car", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DatabaseDateTimeCar.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void TransferFile() {

        //BookingCar bookingCar = new BookingCar(username,car_pickup_date,car_pickup_time,car_dropoff_date,car_dropoff_time,car_location,car_servicetype);
        //UploadID = databaseReference.push().getKey();
        //databaseReference.child(UploadID).setValue(bookingCar);
        Intent carname_intent= new Intent(DatabaseDateTimeCar.this,MainActivityFour.class);
        Bundle carname_b = new Bundle();
        //carname_b.putString("carnameID",UploadID);
        carname_b.putString("username",username);
        carname_b.putString("pick_date",car_pickup_date);
        carname_b.putString("pick_time",car_pickup_time);
        carname_b.putString("drop_time",car_dropoff_time);
        carname_b.putString("drop_date",car_dropoff_date);
        carname_b.putString("carlocation",car_location);
        carname_b.putString("servicetype",car_servicetype);
        carname_intent.putExtras(carname_b);
        startActivity(carname_intent);



    }
}
