package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profileActivity extends AppCompatActivity {
    ImageButton back, editButton;
    Button changePass, delete, signOut;
    TextView username, DOB, email, userID, phone, name, password;
    String user;
    int ID;
    Boolean HasPhone, HasEmail, HasDOB;
    private String url = "http://coms-3090-001.class.las.iastate.edu:8080/api";

    private static final String Delete_URL = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/DeleteUser/success";
    private static final String URL_STRING_REQ = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/helloName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Volley.newRequestQueue(context).getCache().clear();
        Volley.newRequestQueue(profileActivity.this).getCache().clear();

    back = findViewById(R.id.profileBackButton);
    name = findViewById(R.id.profileUser);
    signOut = findViewById(R.id.signOutButton);
    //makeStringReq();
    email = findViewById(R.id.profileBackendEmail);
    userID = findViewById(R.id.profileBackendID);
    phone = findViewById(R.id.profileBackendPhone);
    username = findViewById(R.id.profileBackendUser);
    password = findViewById(R.id.profileBackendPass);
    delete = findViewById(R.id.deleteAccountButton);
    changePass = findViewById(R.id.changePasswordButton);
    DOB = findViewById(R.id.profileBackendDOB);
    editButton = findViewById(R.id.editProfileButton);

    Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("ID");
            user = extras.getString("Username");
        }
    GetRequest(url + "/users/" + ID);


    back.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        Intent intent = new Intent(profileActivity.this, Balance_FinancePage.class);
        intent.putExtra("ID", ID);
        intent.putExtra("Username", user);
        startActivity(intent);
    }
    });


    signOut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(profileActivity.this, MainActivity.class);
            startActivity(intent);
        }
    });

    // Button to send DELETE request
    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //String id = inputId.getText().toString();
            if (ID == 0) {
                //textGetResponse.setText("Please enter a valid Student ID");
                Toast.makeText(profileActivity.this, "Error deleting account, please try again", Toast.LENGTH_LONG).show();
            } else {
                // Send DELETE request
                sendDeleteRequest(url + "/users/" + ID);
                Intent intent = new Intent(profileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    });
    editButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(profileActivity.this, profileEdit.class);
            intent.putExtra("ID", ID);
            intent.putExtra("Username", user);
            intent.putExtra("HasEmail", HasEmail);
            intent.putExtra("HasPhone", HasPhone);
            intent.putExtra("HasDOB", HasDOB);
            startActivity(intent);
        }
    });


    }
    private void sendDeleteRequest(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DELETE RESPONSE", response.toString());

                        try {
                            // Extract the "message" and "status" from the JSON response
                            //String message = response.getString("message");
                            String status = response.getString("status");
                            Toast.makeText(profileActivity.this, "Delete accepted", Toast.LENGTH_LONG).show();
                            //Toast.makeText(profileActivity.this, message + status, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(profileActivity.this, "Error parsing response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DELETE ERROR", error.toString());
                error.printStackTrace();
                Toast.makeText(profileActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
    private void GetRequest(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString());
                        try {
                            // Check if the student exists or not
                            if (response.has("id")) {
                                name.setText(response.getString("username"));
                                //email.setText(response.getString("email"));
                                userID.setText(response.getString("id"));
                                username.setText(response.getString("username"));
                                password.setText(response.getString("password"));
                                if(response.getString("phone").equals("null")){
                                    HasPhone = false;
                                }
                                else{
                                    HasPhone = true;
                                    phone.setText(response.getString("phone"));
                                }
                                if(response.getString("email").equals("null")){
                                    HasEmail = false;
                                }
                                else{
                                    HasEmail = true;
                                    email.setText(response.getString("email"));
                                }
                                if(response.getString("DOB").equals("null")){
                                    HasDOB = false;
                                }
                                else{
                                    HasDOB = true;
                                    DOB.setText(response.getString("email"));
                                }
                                }
                                else {
                                // Handle the case when the student doesn't exist (i.e., 404 Not Found)
                                Toast.makeText(profileActivity.this, "error getting user", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(profileActivity.this, "Volley Error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("param1", "value1");
                params.put("param2", "value2");
                return params;
            }
        };
        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
