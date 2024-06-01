package com.example.dhruvpatel.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityFour extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    RecyclerView recyclerViewfour;
    //List<UploadCarDetail> details;
    //ListView listView;
    public FirebaseStorage firebaseStorage;
    public  String UploadID,username,car_pickup_date,car_dropoff_date,car_pickup_time,car_dropoff_time,car_location,car_servicetype;


    DatabaseReference databaseReference,reff_carname;

    /*String[] name={"Hyundai Elantra","BMW","Toyota Fortuner","Hyundai Grand i10","Jaguar","Land Rover","Suzuki Swift 2018","Suzuki Ritz","Suzuki Swift","Tata Tiago","Honda City"};
    int[] image={R.drawable.elantra,R.drawable.bmw,R.drawable.fortuner,R.drawable.grand,R.drawable.jaguar,R.drawable.landlover,R.drawable.newswift,R.drawable.ritz,R.drawable.swift,R.drawable.tiago,R.drawable.wrv};
    String[] price={"₹450","₹450","₹499","₹499","₹499","₹550","₹550","₹550","₹400","₹400","₹400"};
    String[] seat={"5 seater","5 seater","5 seater","5 seater","5 seater","7 seater","7 seater","5 seater","5 seater","5 seater","5 seater"};
     */

    List<MainList> mainList;

    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_four);
        //listView = (ListView)findViewById(R.id.listviews);
        recyclerViewfour=findViewById(R.id.recycler_view_Four);
        recyclerViewfour.setHasFixedSize(true);
        recyclerViewfour.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("Car Detail");
        reff_carname=FirebaseDatabase.getInstance().getReference("Booking of car");
        firebaseStorage = FirebaseStorage.getInstance();

        mainList =new  ArrayList<>();


        mainAdapter =new MainAdapter(MainActivityFour.this,mainList);
        recyclerViewfour.setAdapter(mainAdapter);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mainList.clear();

                for(DataSnapshot pickdata : dataSnapshot.getChildren()){

                    MainList uploadCarDetail = pickdata.getValue(MainList.class);
                    uploadCarDetail.setKey(pickdata.getKey());
                    mainList.add(uploadCarDetail);

                }
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivityFour.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void UploadCarName(String car_name){

        Bundle carname_b =getIntent().getExtras();
        username=carname_b.getString("username");
        car_pickup_date=carname_b.getString("pick_date");
        car_pickup_time=carname_b.getString("pick_time");
        car_dropoff_time=carname_b.getString("drop_time");
        car_dropoff_date=carname_b.getString("drop_date");
        car_location=carname_b.getString("carlocation");
        car_servicetype=carname_b.getString("servicetype");
        BookingCar bookingCar = new BookingCar(username,car_pickup_date,car_pickup_time,car_dropoff_date,car_dropoff_time,car_location,car_servicetype,car_name);
        UploadID = reff_carname.push().getKey();
        reff_carname.child(UploadID).setValue(bookingCar);


    }

    public void generateCheckSum(String price) {

        //getting the tax amount first.
        String txnAmount = price;


        //creating a retrofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        Api apiService = retrofit.create(Api.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        final Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum

        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                initializePaytmPayment(response.body().getChecksumHash(), paytm);

            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {

                Toast.makeText(MainActivityFour.this, "Errrr"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void initializePaytmPayment(String checksumHash, Paytm paytm) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());


        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(MainActivityFour.this, true, true, this);

    }

    //all these overriden method is to detect the payment result accordingly
    @Override
    public void onTransactionResponse(Bundle bundle) {

        Toast.makeText(this,"OnTransaction REsponse" +bundle.toString(), Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainActivityFour.this,Homepage.class));
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, "ClientAuthFailed"+s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(this, "SomeUIError"+s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(this,"OnErrorLoad" +s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(this, "Cancel Transaction..", Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainActivityFour.this,Homepage.class));
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(this, "Ontransactioncancel "+s + bundle.toString(), Toast.LENGTH_LONG).show();
    }


}
