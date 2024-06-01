package com.example.dhruvpatel.login;

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

public class PedlActivityFetch extends AppCompatActivity implements PedlAdapter.OnItemClickListener{

    RecyclerView recyclerView;
    List<UploadPedlDetail> detail_pedl;
    public DatabaseReference databaseReference_pedl;
    public FirebaseStorage firebaseStorage;
    PedlAdapter pedlAdapter;
    private ValueEventListener mDBlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedl_fetch);

        recyclerView=findViewById(R.id.recycler_view_pedl);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference_pedl=FirebaseDatabase.getInstance().getReference("Pedl Detail");
        firebaseStorage=FirebaseStorage.getInstance();

        detail_pedl=new ArrayList<>();

        pedlAdapter = new PedlAdapter(PedlActivityFetch.this,detail_pedl);
        recyclerView.setAdapter(pedlAdapter);

        pedlAdapter.setOnItemClickListener(this);



        mDBlistener = databaseReference_pedl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detail_pedl.clear();

                for(DataSnapshot datasnap_pedl : dataSnapshot.getChildren()){

                    UploadPedlDetail uploaded_val = datasnap_pedl.getValue(UploadPedlDetail.class);
                    uploaded_val.setKey(datasnap_pedl.getKey());
                    detail_pedl.add(uploaded_val);

                }
                pedlAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PedlActivityFetch.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void OnItemClick(int position) {
        Toast.makeText(this, "Normal Click at Position"+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnWhateverClick(int position) {
        Toast.makeText(this, "Whatever click at position"+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDeleteClick(int position) {

        UploadPedlDetail selecteditem = detail_pedl.get(position);
        final String selectedkey = selecteditem.getKey();

        StorageReference deletreff = firebaseStorage.getReferenceFromUrl(selecteditem.getImageUrl());
        deletreff.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference_pedl.child(selectedkey).removeValue();
                Toast.makeText(PedlActivityFetch.this, "Item Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference_pedl.removeEventListener(mDBlistener);
    }
}
