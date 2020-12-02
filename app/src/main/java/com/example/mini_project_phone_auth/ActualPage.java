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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class ActualPage extends AppCompatActivity {

    private TextView t;
    private EditText quantity;
    private EditText price;
    private Button confirm;

    private String fruitName;
    private String phone_number;
    private String TAG = "Error";

    int flag=0;

    DatabaseReference databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference databaseReferenceItems=FirebaseDatabase.getInstance().getReference().child("Items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_page);

        fruitName = getIntent().getStringExtra("itemName");
        phone_number = getIntent().getStringExtra("phone_number");

        t = (TextView) findViewById(R.id.stockName);
        quantity = (EditText) findViewById(R.id.quantity);
        price = (EditText) findViewById(R.id.price);
        confirm = (Button) findViewById(R.id.confirm_button);


        t.setText(fruitName);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stock stock=new Stock();
                Log.i(TAG, "Before logging price");
                Log.i(TAG, price.getText().toString());
                stock.setStockName(fruitName);
                stock.setPrice(Double.parseDouble(price.getText().toString()));
                stock.setQuantity(Integer.parseInt(quantity.getText().toString()));
                int stockid=generateStockId();
                Log.i(TAG,String.valueOf(stockid));
                stock.setStockId(stockid);

                databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if(user.getPhone().equals(phone_number)){
                                stock.setUser(user);
                                break;
                            }else{
                                continue;
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

        databaseReferenceItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.i(TAG, "Inside for");
                    Stock stock1 = snapshot.getValue(Stock.class);
                    if(stock1.getUser().getPhone().equals(phone_number)) {
                        Log.i(TAG, "Inside first if");
                        Log.i(TAG, fruitName);
                        Log.i(TAG, stock1.toString());
                        if (stock1.getStockName().equals(fruitName)) {
                            Log.i(TAG, "Inside second if");

                            int newQuantity = stock1.getQuantity() + Integer.parseInt(quantity.getText().toString());
                            stock1.setQuantity(newQuantity);
                            stock1.setPrice(Double.parseDouble(price.getText().toString()));

                            HashMap<String, Object> mymap=new HashMap<>();
                            mymap.put("stockName", fruitName);
                            mymap.put("price", Double.parseDouble(price.getText().toString()));
                            mymap.put("quantity", newQuantity);
                            mymap.put("stockId", stock1.getStockId());
                            mymap.put("user", stock1.getUser());

                            databaseReferenceItems.child(String.valueOf(stock1.getStockId())).updateChildren(mymap);

                            flag=1;
                            break;
                        }
                    }
                }
                if(flag==0){
                    Log.i(TAG, "Inside else");

                    databaseReferenceItems.child(String.valueOf(stock.getStockId())).setValue(stock, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {

                                Toast.makeText(ActualPage.this, "Data could not saved", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ActualPage.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ActualPage.this, MainPage.class);
                                intent.putExtra("phone_number", phone_number);
                                startActivity(intent);
                            }
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
});


    }


    public int generateStockId(){

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return number;

    }


}