package com.example.mini_project_phone_auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ConfirmBuy extends AppCompatActivity {

    private TextView itemName;
    private TextView price;
    private EditText quantity;
    private Button calculate;
    private TextView display_cost;
    private Button buy;

    String phone_number;

    private Seller seller;

    DatabaseReference databaseReferenceItems= FirebaseDatabase.getInstance().getReference().child("Items");

    int setquan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_buy);

        itemName=(TextView) findViewById(R.id.itemNameToBuy);
        price=(TextView) findViewById(R.id.pricePerUnit);
        quantity=(EditText)findViewById(R.id.quantityToBuy);
        calculate=(Button)findViewById(R.id.calculate_price);
        buy=(Button)findViewById(R.id.buy_now);
        display_cost=(TextView)findViewById(R.id.display_total_cost);

        phone_number=getIntent().getStringExtra("phone_number");
        seller=(Seller)getIntent().getSerializableExtra("Seller");
        Log.i("Seller from ConfirmBuy", seller.toString());

        itemName.setText(seller.getItemName());
        price.setText(String.valueOf(seller.getSellerPrice()));

        calculate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quan=Integer.parseInt(quantity.getText().toString());
                        setquan=quan;

                        if(quan > seller.getQuantity()){
                            Toast.makeText(ConfirmBuy.this, String.valueOf(quan)+" not available", Toast.LENGTH_SHORT).show();
                        }
                        else if(quan <= seller.getQuantity()){
                            double total_cost=quan*seller.getSellerPrice();
                            display_cost.setText("Total Cost is Rs "+total_cost);

                        }

                    }
                }
        );

        buy.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        databaseReferenceItems.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    Stock stock=snapshot.getValue(Stock.class);
                                    if(stock.getUser().getPhone().equals(seller.getPhone())){

                                        if(stock.getStockName().equals(seller.getItemName())){

                                            int newQuantity=seller.getQuantity() - setquan;

                                            Log.i("newQuantity", String.valueOf(newQuantity));

                                            HashMap<String, Object> myMap=new HashMap<>();
                                            myMap.put("quantity", newQuantity);

                                            databaseReferenceItems.child(String.valueOf(stock.getStockId())).updateChildren(myMap, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                    if(databaseError!=null){
                                                        Toast.makeText(ConfirmBuy.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        Toast.makeText(ConfirmBuy.this, "Order Successful", Toast.LENGTH_SHORT).show();
                                                        Intent intent=new Intent(ConfirmBuy.this, MainPage.class);
                                                        intent.putExtra("phone_number", phone_number);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });

                                        }

                                    }


                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
        );



    }
}