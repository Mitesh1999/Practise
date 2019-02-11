package com.example.dell.clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button login;
    TextView text;
    SharedPreferences LoginData;
    String api = "https://minder.000webhostapp.com/lib/v1/Login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text = findViewById(R.id.register);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        login = findViewById(R.id.login);

        LoginData = getSharedPreferences("LoginData",MODE_PRIVATE);

        // if already log in then no directly go inside
        if(LoginData.getString("email",null)!=null){
            Intent i = new Intent(Login.this,drawer.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
         //       i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidate())
                {
                    getData();
                }
            }
        });

    }

    private boolean isValidate() {
        boolean isValid = true;
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (email.isEmpty()) {
            isValid = false;
            mEmail.setError("Please Enter Email Address");
        }
        if (password.isEmpty()) {
            isValid = false;
            mPassword.setError("Please Enter Password");
        }
        return isValid;
    }

    void getData()
    {
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Logining ");

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                        progressDialog.dismiss();
                        JSONObject obj = new JSONObject(response);
                        String error = obj.getString("error");
                        String id = obj.getString("id");
                        if(error.equals("false"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully Login ",Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = LoginData.edit();
                            editor.putString("email",email);
                            editor.putString("password",password);
                            editor.apply();

                            Intent i = new Intent(Login.this,drawer.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext()," Login failure",Toast.LENGTH_SHORT).show();
                        }

                    }catch (JSONException e) {
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
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
