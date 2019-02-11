package com.example.dell.clone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView againLogin;
    EditText email,name,password;
    Button register;

    String url = "https://minder.000webhostapp.com/lib/v1/RegisterUser.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         againLogin = findViewById(R.id.againLogin);
         email = findViewById(R.id.email);
         name = findViewById(R.id.name);
         password = findViewById(R.id.password);
         register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(isValidate())
                    {
                        putData();
                    }
            }
        });

        againLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

    }


    // isValidate function check for if any field id invalid or not

    boolean isValidate()
    {
        boolean ans = true;

        String str1 = name.getText().toString();
        String str2 = email.getText().toString();
        String str3 = password.getText().toString();

        if(str1.isEmpty())
        {
                ans = false;
                name.setError("Enter valid name...");
        }
        if(str2.isEmpty())
        {
            ans = false;
            email.setError("Enter valid email....");
        }
        if(str3.isEmpty())
        {
            ans = false;
            password.setError("Enter valid password...");
        }
        if(password.length()<4 || password.length()>10)
        {
            ans = false ;
            password.setError("Password must between 4 to 10 character");
        }
        return ans;
    }


    // this function put data on server as soon as user click on the register button

    void putData()
    {

        final String str1 = name.getText().toString();
        final String str2 = email.getText().toString();
        final String str3 = password.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                        JSONObject object = new JSONObject(response);

                        String error = object.getString("error");

                        String message = object.getString("message");

                        if(error.equals("false"))
                        {
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        }

                } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();

                params.put("name", str1);
                params.put("email", str2);
                params.put("password", str3);

                return params;
            }

        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
