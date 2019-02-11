package com.example.dell.clone;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AdapterForAddToCart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<AddToCartModalClass> list;
    Context context;
    AddToCart_Listener addToCart_listener;


    public AdapterForAddToCart(ArrayList<AddToCartModalClass> dobj, Context context, AddToCart_Listener con) {
        list = dobj;
        this.context = context;
        addToCart_listener = con;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < (list.size())) {
            return R.layout.data_for_add_to_cart;
        } else {
            return R.layout.buy_for_add_to_cart;
        }
    }

    @Override
    public int getItemCount() {

        return list.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Log.e("value of i is ", "" + viewType);
        if (viewType == R.layout.data_for_add_to_cart) {
            View view = LayoutInflater.from(context).inflate(R.layout.data_for_add_to_cart, viewGroup, false);
            return new DataViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.buy_for_add_to_cart, viewGroup, false);
            return new BuyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewholder, final int i) {
        if (viewholder instanceof DataViewHolder) {
            final AddToCartModalClass obj = list.get(i);
            ((DataViewHolder)viewholder).count.setText(Integer.toString(obj.count));
            ((DataViewHolder)viewholder).price.setText(Integer.toString(obj.price));
            ((DataViewHolder)viewholder).pubname.setText(obj.pubName);
            ((DataViewHolder)viewholder).title.setText(obj.title);
            Picasso.get().load(obj.image).into(((DataViewHolder)viewholder).image);

            ((DataViewHolder)viewholder).inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart_listener.increment(list.get(i), ((DataViewHolder)viewholder).count);
                }
            });
            ((DataViewHolder)viewholder).dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart_listener.decrement(list.get(i), ((DataViewHolder)viewholder).count);
                }
            });
        }

        if(viewholder instanceof BuyViewHolder){

            // fetching old value for total price
            SharedPreferences sharedPreferences = context.getSharedPreferences("DummyDatabaseTotal", MODE_PRIVATE);
            int  total = sharedPreferences.getInt("Total", 0);

            // setting initital value from lastly stored
            ((BuyViewHolder)viewholder).totalPrice.setText(""+total);

            ((BuyViewHolder)viewholder).buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Buy successfully", Toast.LENGTH_SHORT);
                }
            });
        }
    }


    public class DataViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pubname;
        TextView price;
        TextView count;
        ImageView image;
        Button inc;
        Button dec;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.add_to_cart_title);
            pubname = itemView.findViewById(R.id.add_to_cart_pub_name);
            price = itemView.findViewById(R.id.add_to_cart_price);
            image = itemView.findViewById(R.id.add_to_cart_image);
            count = itemView.findViewById(R.id.add_to_cart_count);
            inc = itemView.findViewById(R.id.add_to_cart_add);
            dec = itemView.findViewById(R.id.add_to_cart_remove);
        }
    }

    public class BuyViewHolder extends RecyclerView.ViewHolder {
        TextView totalPrice;
        Button buy;

        public BuyViewHolder(@NonNull View itemView) {
            super(itemView);
            totalPrice = itemView.findViewById(R.id.total_price);
            buy = itemView.findViewById(R.id.buy_button_for_add_to_cart);
        }
    }

}

