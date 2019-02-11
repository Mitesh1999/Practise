package com.example.dell.clone;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddToCart extends AppCompatActivity implements AddToCart_Listener {
    ArrayList<AddToCartModalClass> list;
    AdapterForAddToCart adapterForAddToCart;
    RecyclerView recyclerView;
    Boolean dataAlreadyExist = false;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        String img = getIntent().getStringExtra("MyImage");
        String title = getIntent().getStringExtra("MyTitle");
        String pubname = getIntent().getStringExtra("MyPubname");
        int price = getIntent().getIntExtra("MyPrice", 0);

        recyclerView = findViewById(R.id.recyclerview_add_to_cart);

        // fetching old value for total price
        SharedPreferences sharedPreferences = getSharedPreferences("DummyDatabase", MODE_PRIVATE);
        loadArray();

        if (!list.isEmpty()) {
            dataAlreadyExist = false;
            for (AddToCartModalClass dobj : list) {
                if (title.equals(dobj.title)) {
                    dataAlreadyExist = true;
                    break;
                }
            }
            if (!dataAlreadyExist) {
                list.add(new AddToCartModalClass(img, 0, title, pubname, price));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                storeData();
            }
        }
        else {
            list.add(new AddToCartModalClass(img, 0, title, pubname, price));
            storeData();
        }


        adapterForAddToCart = new AdapterForAddToCart(list, this, this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterForAddToCart);

    }

    public void storeData() {
        SharedPreferences sharedPreferences = getSharedPreferences("DummyDatabase", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("AddToCartList",json);
        editor.apply();
    }

    public void loadArray() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("DummyDatabase", MODE_PRIVATE);
        String json = sharedPreferences.getString("AddToCartList", null);

        Type type = new TypeToken<ArrayList<AddToCartModalClass>>(){}.getType();

        if (json == null) {
            list = new ArrayList<>();
        } else {
            list = gson.fromJson(json, type);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("DummyDatabase",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        storeData();
    }

    @Override
    public void increment(AddToCartModalClass obj, TextView count) {
        if (obj.count < 10) {
            TextView textView = findViewById(R.id.total_price);
            obj.count++;
            String x = Integer.toString(obj.count);
            count.setText(x);

            SharedPreferences sharedPreferences = getSharedPreferences("DummyDatabaseTotal", MODE_PRIVATE);
            int temp = sharedPreferences.getInt("Total", 0);
            temp = temp + obj.getPrice();
            if (textView != null)
                textView.setText(""+ temp);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Total", temp);
            editor.apply();

        }
    }

    @Override
    public void decrement(AddToCartModalClass obj, TextView count) {

        if (obj.count > 0) {
            TextView textView = findViewById(R.id.total_price);
            obj.count--;
            String x = Integer.toString(obj.count);
            count.setText(x);
            SharedPreferences sharedPreferences = getSharedPreferences("DummyDatabaseTotal", MODE_PRIVATE);
            int temp = sharedPreferences.getInt("Total", 0);
            temp = temp - obj.getPrice();
            if (textView != null)
                textView.setText("" + temp);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Total", temp);
            editor.apply();

        }
    }
}
