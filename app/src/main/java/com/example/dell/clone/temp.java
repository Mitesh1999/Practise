package com.example.dell.clone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class temp extends AppCompatActivity {

    ImageView tiv;
    TextView tname, tdetails, tprice, tpub_year, tpub_name;
    String image , name , details , pub_name ,pub_year;
    int contact , price;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        name = getIntent().getStringExtra("name");
        pub_year = getIntent().getStringExtra("pub_year");
        image = getIntent().getStringExtra("image");
        details = getIntent().getStringExtra("details");
        price = getIntent().getIntExtra("price", 0);
        pub_name = getIntent().getStringExtra("pub_name");
        contact = getIntent().getIntExtra("contact", 0);


        tiv = findViewById(R.id.BookImage);
        tname = findViewById(R.id.name);
        tdetails = findViewById(R.id.details);
        tprice = findViewById(R.id.price);
        tpub_year = findViewById(R.id.pubYear);
        tpub_name = findViewById(R.id.pubName);

        Picasso.get().load(image).into(tiv);
        tname.setText(name);
        tprice.setText(String.valueOf(price));
        tpub_name.setText(pub_name);
        tpub_year.setText(pub_year);
        tdetails.setText(details);


        button = findViewById(R.id.AddToCart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionToAddCart();
            }
        });


    }

    public void FunctionToAddCart() {
        Intent intent = new Intent(this, AddToCart.class);
        intent.putExtra("MyImage", image);
        intent.putExtra("MyTitle", name);
        intent.putExtra("MyPubname", pub_name);
        intent.putExtra("MyPrice", price);
        startActivity(intent);
    }

}
