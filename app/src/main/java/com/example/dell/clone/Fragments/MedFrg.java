package com.example.dell.clone.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dell.clone.ActivityTransfer;
import com.example.dell.clone.Books;
import com.example.dell.clone.CustomAdapter;
import com.example.dell.clone.MySingleton;
import com.example.dell.clone.R;
import com.example.dell.clone.temp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MedFrg extends Fragment implements ActivityTransfer{
    ArrayList<Books> list;
    CustomAdapter adp;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.medical,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<Books>();

        recyclerView = view.findViewById(R.id.recycler2);

/*        list.add(new Books("https://cdn.pixabay.com/photo/2013/07/04/11/04/google-images-143148_960_720.png","C++   1000"));
        list.add(new Books("https://cdn.pixabay.com/photo/2013/07/04/11/04/google-images-143148_960_720.png","DATABASE   20000"));
        list.add(new Books("https://cdn.pixabay.com/photo/2013/07/04/11/04/google-images-143148_960_720.png","Compiler Design   30000"));
        list.add(new Books("https://cdn.pixabay.com/photo/2013/07/04/11/04/google-images-143148_960_720.png","Machine Learning   4000"));
        list.add(new Books("https://cdn.pixabay.com/photo/2013/07/04/11/04/google-images-143148_960_720.png","Ai   5000"));
        list.add(new Books("https://cdn.pixabay.com/photo/2013/07/04/11/04/google-images-143148_960_720.png","Java   1000"));
        list.add(new Books("https://cdn.pixabay.com/photo/2013/07/04/11/04/google-images-143148_960_720.png","Digital logic design   10"));
   */

        getData();

    }


    void getData(){

        String url = "https://minder.000webhostapp.com/lib/v1/fetch_med_book_data.php";

        final ProgressDialog dialog = new ProgressDialog(getActivity());

        dialog.setMessage("Loading");

        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    dialog.dismiss();

                    String id,name,details,price,pub_name,pub_year,contact,img_url;

                    JSONObject jsonObject = new JSONObject(response) ;

                    JSONArray array = jsonObject.getJSONArray("data");

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);

                        id = object.getString("id");
                        name = object.getString("name");

                        details = object.getString("details");
                        price = object.getString("price");

                        pub_name = object.getString("pub_name");
                        pub_year = object.getString("pub_year");


                        contact = object.getString("contact");
                        img_url = object.getString("image_url");


                        list.add(new Books(Integer.parseInt(id),name,details,Integer.parseInt(price),pub_name,pub_year,Integer.parseInt(contact),img_url));

                    }


                    //set adapter

                    adp = new CustomAdapter(list , getActivity(), MedFrg.this);

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(adp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }


    public void helpToTransfer(Books obj) {

        Intent intent = new Intent(getActivity(), temp.class);
        intent.putExtra("image",obj.getImage());
        intent.putExtra("name",obj.getName());
        intent.putExtra("id",obj.getId());
        intent.putExtra("details",obj.getDetails());
        intent.putExtra("price",obj.getPrice());
        intent.putExtra("pub_year",obj.getPubYear());
        intent.putExtra("pub_name",obj.getPubName());
        intent.putExtra("contact",obj.getContect());

        startActivity(intent);
        //getActivity().finish();
    }

}
