package com.example.dhruvpatel.login;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CarActivityFetch extends AppCompatActivity implements CarAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    List<UploadCarDetail> details;
    public FirebaseStorage storage;
    private ValueEventListener mDBlistener;
    CarAdapter carAdapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_fetch);

        recyclerView=findViewById(R.id.recycler_view_car);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference=FirebaseDatabase.getInstance().getReference("Car Detail");
        storage=FirebaseStorage.getInstance();

        details = new ArrayList<>();

        carAdapter = new CarAdapter(CarActivityFetch.this,details);
        recyclerView.setAdapter(carAdapter);

        carAdapter.setOnItemClickListener(CarActivityFetch.this);


        mDBlistener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                details.clear();
                for(DataSnapshot datasnap: dataSnapshot.getChildren()){

                    UploadCarDetail uploaded_data = datasnap.getValue(UploadCarDetail.class);
                    uploaded_data.setKey(datasnap.getKey());
                    details.add(uploaded_data);
                }

                carAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CarActivityFetch.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal click at position"+position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onwhateverClick(int position) {

        Toast.makeText(this, "Whatever click at position"+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDeleteClick(int position) {

        UploadCarDetail selected_item= details.get(position);
        final String selectkey =  selected_item.getKey();

        StorageReference deleteReference = storage.getReferenceFromUrl(selected_item.getImageurl());
        deleteReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(selectkey).removeValue();
                Toast.makeText(CarActivityFetch.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mDBlistener);
    }
}
