package com.example.mini_project_phone_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuyView1 extends AppCompatActivity implements RecyclerViewClickListener {

    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter myAdapter;
    private static RecyclerView.LayoutManager layoutManager;

    String phone_number;


    private   ArrayList<Seller> myList=new ArrayList<>();
    private String TAG = "Check!!";

    DatabaseReference databaseReferenceItems = FirebaseDatabase.getInstance().getReference().child("Items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_view1);

        phone_number=getIntent().getStringExtra("phone_number");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

            databaseReferenceItems.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    showData(dataSnapshot);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }



            });

            Log.i("Printing list", myList.toString());



    }

    private void showData(DataSnapshot dataSnapshot){

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Stock stock = snapshot.getValue(Stock.class);

            Log.i(TAG, stock.toString());

            String sellerName = stock.getUser().getName();
            String itemName = stock.getStockName();
            double price = stock.getPrice();
            int quantity=stock.getQuantity();
            String phone=stock.getUser().getPhone();

            Seller seller = new Seller(sellerName, itemName, price, quantity, phone);

            Log.i(TAG, seller.toString());

            myList.add(seller);

            Log.i(TAG, myList.toString());

            myAdapter = new ListAdapter(myList, this);
            recyclerView.setAdapter(myAdapter);

        }

    }


    @Override
    public void onItemClicked(Seller seller) {

//        Toast.makeText(BuyView1.this, seller.getItemName()+" "+seller.getSellerName(), Toast.LENGTH_LONG).show();
        Intent intent=new Intent(BuyView1.this, ConfirmBuy.class);
        intent.putExtra("Seller", seller);
        intent.putExtra("phone_number", phone_number);
        Log.i("phone number", phone_number);
        Log.i("Seller", seller.toString());
        startActivity(intent);

    }
}







