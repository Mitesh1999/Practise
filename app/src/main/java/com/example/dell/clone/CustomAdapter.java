package com.example.dell.clone;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.viewholder>{
    ArrayList<Books> list;
    Context context;
    ActivityTransfer activityTransfer;

    public CustomAdapter(ArrayList<Books> dobj, Context context , ActivityTransfer con)
    {
        list = dobj;
        this.context = context ;
        activityTransfer = con;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.books,viewGroup,false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, final int i) {

        final Books obj = list.get(i);

        viewholder.tv.setText(obj.getName());

        if(!(obj.getImage().isEmpty()))
        {
            Picasso.get().load(obj.getImage()).into(viewholder.iv);
        }

        viewholder.mainLinearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               activityTransfer.helpToTransfer(obj);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv ;
        LinearLayout mainLinearLayout;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.bimage);
            tv = itemView.findViewById(R.id.bname);
            mainLinearLayout = itemView.findViewById(R.id.mainLinearLayout);
        }
    }
}
