package com.example.mini_project_phone_auth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public ArrayList<Seller> myData;
    public RecyclerViewClickListener listener;


    public ListAdapter(ArrayList<Seller> myDataSet, RecyclerViewClickListener listener){
        this.myData=myDataSet;
        this.listener=listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        v.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClicked(myData.get(vh.getAdapterPosition()));
                    }
                }

        );

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item.setText(myData.get(position).getItemName());
        holder.sellerName.setText(myData.get(position).getSellerName());
        holder.price.setText(String.valueOf(myData.get(position).getSellerPrice()));

    }

    @Override
    public int getItemCount()
    {
        return myData.size();
    }


}

class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView item;
    public TextView sellerName;
    public TextView price;


    public MyViewHolder(View v) {
        super(v);
        item=(TextView)v.findViewById(R.id.itemToDisplay);
        sellerName=(TextView)v.findViewById(R.id.sellerName);
        price=(TextView)v.findViewById(R.id.priceToDisplay);


    }



}

 interface RecyclerViewClickListener{

     void onItemClicked(Seller seller);

}
