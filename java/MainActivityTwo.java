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

public class MainActivityTwo extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    RecyclerView recyclerViewTwo;
    public  String UploadID,username,pedl_pickup_date,pedl_dropoff_date,pedl_pickup_time,pedl_dropoff_time,pedl_location,pedl_servicetype;
    public FirebaseStorage firebaseStorage;
    public DatabaseReference databaseReference,reff_pedlname;
    List<TwoList> twoLists;
    TwoAdapter twoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        recyclerViewTwo=findViewById(R.id.recycler_view_two);
        recyclerViewTwo.setHasFixedSize(true);
        recyclerViewTwo.setLayoutManager(new LinearLayoutManager(this));

        databaseReference=FirebaseDatabase.getInstance().getReference("Pedl Detail");
        reff_pedlname=FirebaseDatabase.getInstance().getReference("Booking of pedl");
        firebaseStorage=FirebaseStorage.getInstance();

        twoLists=new ArrayList<>();

        twoAdapter=new TwoAdapter(MainActivityTwo.this,twoLists);
        recyclerViewTwo.setAdapter(twoAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                twoLists.clear();

                for(DataSnapshot pickdatatwo : dataSnapshot.getChildren()){

                    TwoList currentdetail = pickdatatwo.getValue(TwoList.class);
                    currentdetail.setkey(pickdatatwo.getKey());
                    twoLists.add(currentdetail);
                }
                twoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(MainActivityTwo.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void UploadPedlName(String pedl_name){

        Bundle pedlname_b =getIntent().getExtras();
        username=pedlname_b.getString("username");
        pedl_pickup_date=pedlname_b.getString("pick_date");
        pedl_pickup_time=pedlname_b.getString("pick_time");
        pedl_dropoff_time=pedlname_b.getString("drop_time");
        pedl_dropoff_date=pedlname_b.getString("drop_date");
        pedl_location=pedlname_b.getString("pedllocation");
        pedl_servicetype=pedlname_b.getString("servicetype");
        BookingCar bookingCar = new BookingCar(username,pedl_pickup_date,pedl_pickup_time,pedl_dropoff_date,pedl_dropoff_time,pedl_location,pedl_servicetype,pedl_name);
        UploadID = reff_pedlname.push().getKey();
        reff_pedlname.child(UploadID).setValue(bookingCar);

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

                Toast.makeText(MainActivityTwo.this, "Errrr"+t.getMessage(), Toast.LENGTH_SHORT).show();

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
        Service.startPaymentTransaction(MainActivityTwo.this, true, true, this);

    }

    //all these overriden method is to detect the payment result accordingly
    @Override
    public void onTransactionResponse(Bundle bundle) {

        Toast.makeText(this,"OnTransaction REsponse" +bundle.toString(), Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainActivityTwo.this,Homepage.class));
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
        startActivity(new Intent(MainActivityTwo.this,Homepage.class));
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(this, "Ontransactioncancel "+s + bundle.toString(), Toast.LENGTH_LONG).show();
    }

}

/*public class MainActivityTwo extends AppCompatActivity {

    ListView listView;
    String[] name={"Access","Activa 4G","Royal Enfield 300","Royal Enfield 500","IActiva","Jupiter 2015","Thunder Bird","Jupiter 2018"};
    int[] image={R.drawable.access,R.drawable.activa,R.drawable.bullet300,R.drawable.bullet500,R.drawable.iactiva,R.drawable.jupiter,R.drawable.thunder,R.drawable.newjupiter};
    String[] price={"₹450","₹450","₹499","₹499","₹499","₹550","₹150","₹120"};
    String[] cc={"110cc","110cc","300cc","500cc","100cc","110cc","500cc","110cc"};

    ArrayList<TwoList> twoList;

    TwoAdapter twoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);
        listView = (ListView)findViewById(R.id.listview2);

        twoList =new  ArrayList<TwoList>();


        for(int i=0;i<name.length;i++){

            TwoList list2= new TwoList();
            list2.setName(name[i]);
            list2.setImage(image[i]);
            list2.setCc(cc[i]);
            list2.setPrice(price[i]);
            twoList.add(list2);
        }
        twoAdapter =new TwoAdapter(MainActivityTwo.this,twoList);
        listView.setAdapter(twoAdapter);

    }

}*/
