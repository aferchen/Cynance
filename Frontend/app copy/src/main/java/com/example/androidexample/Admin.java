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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Admin extends AppCompatActivity {

    private ImageButton back;
    private Button delete;
    private Button add;
    private Button update;
    private TextView cryptoToDelete;
    private TextView cryptoNameInput;
    private TextView cryptoPriceInput;
    private RequestQueue requestQueue;
    private Button createTipPage;
    private TextView cryptoNameUpdate;
    private TextView cryptoPriceUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_admin);

        createTipPage = findViewById(R.id.createTips);
        delete = findViewById(R.id.button13);
        update = findViewById(R.id.button14);
        add = findViewById(R.id.button15);
        back = findViewById(R.id.backAdmin);
        cryptoToDelete = findViewById(R.id.editText);
        cryptoNameInput = findViewById(R.id.cryptoNameInput); // New input for crypto name
        cryptoPriceInput = findViewById(R.id.cryptoPriceInput); // New input for crypto price

        cryptoNameUpdate = findViewById(R.id.cryptoNameUpdate);
        cryptoPriceUpdate = findViewById(R.id.cryptoPriceUpdate);

        // Initialize the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, cryptoPage.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cryptoName = cryptoToDelete.getText().toString().trim();

                if (!cryptoName.isEmpty()) {
                    deleteCrypto(cryptoName);
                } else {
                    Toast.makeText(Admin.this, "Please enter a cryptocurrency name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input for crypto name and price from the TextViews (you should already have these in your layout)
                String cryptoName = cryptoNameUpdate.getText().toString().trim();
                String cryptoPriceString = cryptoPriceUpdate.getText().toString().trim();

                // Check if the fields are not empty
                if (!cryptoName.isEmpty() && !cryptoPriceString.isEmpty()) {
                    try {
                        // Convert the price string to a double
                        double cryptoPrice = Double.parseDouble(cryptoPriceString);

                        // Call the updateCrypto method to update the cryptocurrency
                        updateCrypto(cryptoName, cryptoPrice);
                    } catch (NumberFormatException e) {
                        // Handle invalid price input
                        Toast.makeText(Admin.this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // If inputs are empty, show a message
                    Toast.makeText(Admin.this, "Please enter both name and price", Toast.LENGTH_SHORT).show();
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cryptoName = cryptoNameInput.getText().toString().trim();
                String cryptoPriceString = cryptoPriceInput.getText().toString().trim();

                if (!cryptoName.isEmpty() && !cryptoPriceString.isEmpty()) {
                    try {
                        double cryptoPrice = Double.parseDouble(cryptoPriceString);
                        addCrypto(cryptoName, cryptoPrice);  // Call addCrypto with the name and price
                    } catch (NumberFormatException e) {
                        Toast.makeText(Admin.this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Admin.this, "Please enter both name and price", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createTipPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, tipCreationAdmin.class); //--------------------------
                startActivity(intent);
            }
        });

    }


    private void deleteCrypto(String cryptoName) {
        int id = 0;
        if(cryptoName.equals("bitcoin")){
            id=8;
        } else if(cryptoName.equals("ethereum")){
            id=2;
        } else if(cryptoName.equals("dogecoin")){
            id=3;
        } else if(cryptoName.equals("solana")){
            id=4;
        } else if(cryptoName.equals("litecoin")){
            id=5;
        }else if(cryptoName.equals("aave")){
            id=6;
        }else{
            id=-1;
        }




        String url = "http://coms-3090-001.class.las.iastate.edu:8080/api/cryptos/" + id;

        // Create a DELETE request with an empty body (if required by the backend)
        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response from the server
                        Log.d("DELETE RESPONSE", response.toString());
                        Toast.makeText(Admin.this, cryptoName + " has been deleted", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the error for better debugging
                        String errorMessage = error.networkResponse != null ? new String(error.networkResponse.data) : error.toString();
                        Toast.makeText(Admin.this, "Failed to delete " + cryptoName + ": " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add the request to the queue
        requestQueue.add(deleteRequest);
    }




    // Function to add a new cryptocurrency
    private void addCrypto(String cryptoName, double cryptoPrice) {
       // String url = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/addCrypto";
            String url = "http://coms-3090-001.class.las.iastate.edu:8080/api/cryptos";
        JSONObject newCrypto = new JSONObject();
        try {
            newCrypto.put("name", cryptoName);
            newCrypto.put("price", cryptoPrice);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(Admin.this, "Failed to create cryptocurrency object", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, newCrypto,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Admin.this, cryptoName + " has been added", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Admin.this, "Failed to add " + cryptoName, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(postRequest);
    }

    private void updateCrypto(String cryptoName, double cryptoPrice) {
        int id = 0;
        if(cryptoName.equals("bitcoin")){
            id=8;
        } else if(cryptoName.equals("ethereum")){
            id=2;
        } else if(cryptoName.equals("dogecoin")){
            id=5;
        } else if(cryptoName.equals("solana")){
            id=10;

        }else if(cryptoName.equals("aave")){
            id=6;
        }else if(cryptoName.equals("testcoin")){
        id=9;
    }else{
            id=-1;
        }
        //String url = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/updateCrypto";
        String url = "http://coms-3090-001.class.las.iastate.edu:8080/api/cryptos/" +id;
        JSONObject updatedCrypto = new JSONObject();
        try {
            updatedCrypto.put("name", cryptoName);
            updatedCrypto.put("price", cryptoPrice);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(Admin.this, "Failed to create cryptocurrency object", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, updatedCrypto,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Admin.this, cryptoName + " has been updated", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Admin.this, "Failed to update " + cryptoName, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(putRequest);
    }

}
