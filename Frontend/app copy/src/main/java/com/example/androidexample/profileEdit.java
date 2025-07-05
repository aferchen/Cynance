package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profileEdit  extends AppCompatActivity {
    //VARIABLES
    //String URL = "http://coms-3090-001.class.las.iastate.edu:8080/api";
    //String URL = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/addInfo";
    String URL = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/signUpInformation";

    String user;
    int ID;
    ImageButton backButton, addEmail, addPhone, addDOB;
    EditText phone, email, DOB;
    Boolean hasPhone, hasEmail, hasDOB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        Volley.newRequestQueue(profileEdit.this).getCache().clear();

        backButton = findViewById(R.id.profileEditBackButton);
        addEmail = findViewById(R.id.emailAddButton);
        addPhone = findViewById(R.id.phoneAddButton);
        addDOB = findViewById(R.id.DOBAddButton);
        phone = findViewById(R.id.phoneAdd);
        email = findViewById(R.id.emailAdd);
        DOB = findViewById(R.id.DOBAdd);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("ID");
            user = extras.getString("Username");
            hasEmail = extras.getBoolean("HasEmail");
            hasPhone = extras.getBoolean("HasPhone");
            hasDOB = extras.getBoolean("HasDOB");
        }





        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(profileEdit.this, profileActivity.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Username", user);
                startActivity(intent);
            }
        });
        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailName = email.getText().toString().trim();
                if (!emailName.isEmpty()) {
                    if(hasEmail){
                        updateInfo(emailName, "email", URL);
                    } else{
                        // Create JSON object for POST request
                        JSONObject json = new JSONObject();
                        try {
                            json.put("email", email);
                            json.put("birthday", DOB);
                            json.put("Phone", phone);
                            //ADD THE REST LATER
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postInfo(json, "email", URL);
                    }
                } else {
                    Toast.makeText(profileEdit.this, "Please enter an email to add", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DOBname = DOB.getText().toString().trim();
                if(!DOBname.isEmpty()){
                    if(hasDOB) {
                        updateInfo(DOBname, "Date of Birth", URL);
                    } else{
                        // Create JSON object for POST request
                        JSONObject json = new JSONObject();
                        try {
                            json.put("email", email);
                            json.put("birthday", DOB);
                            json.put("Phone", phone);
                            //ADD THE REST LATER
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postInfo(json, "birthday", URL);
                    }
                } else {
                    Toast.makeText(profileEdit.this, "Please enter an Date of Birth to add", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PhoneName = phone.getText().toString().trim();
                if(!PhoneName.isEmpty()){
                    if(hasPhone) {
                        updateInfo(PhoneName, "Phone", URL);
                    } else {
                        // Create JSON object for POST request
                        JSONObject json = new JSONObject();
                        try {
                            json.put("email", email);
                            json.put("birthday", DOB);
                            json.put("Phone", phone);
                            //ADD THE REST LATER
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postInfo(json, "phone", URL);
                    }

                } else {
                    Toast.makeText(profileEdit.this, "Please enter a phone number to add", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateInfo(String info, String type, String url) {
        JSONObject updatedInfo = new JSONObject();
        try {
            updatedInfo.put(type, info);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(profileEdit.this, "Failed to update user information", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, updatedInfo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(profileEdit.this, type + " has been updated", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(profileEdit.this, "Failed to update user " + type, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(putRequest);
    }
    private void postInfo(JSONObject jsonData, String type, String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("POST RESPONSE", response.toString());

                        try {

                            Toast.makeText(profileEdit.this,"Successfully added " + type + " to databse" , Toast.LENGTH_LONG).show();
                            response.getString("");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(profileEdit.this,"Error posting information to database" , Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("POST ERROR", error.toString());
                error.printStackTrace();
                // Error in toast
                Toast.makeText(profileEdit.this,"Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        // Add the request to the RequestQueue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}