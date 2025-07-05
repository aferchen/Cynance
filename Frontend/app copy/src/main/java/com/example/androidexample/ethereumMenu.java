package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ethereumMenu extends AppCompatActivity {

    Button buyEther;
    private TextView priceTextView;
    private double etherPrice = 0.0;
    private ImageButton backButton;
    int ID;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_ethereum);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("ID");
            user = extras.getString("Username");
        }

        buyEther = findViewById(R.id.button8);
        priceTextView = findViewById(R.id.textView);
        backButton = findViewById(R.id.profileBackButton4);

        // Create a request queue for Volley
        RequestQueue queue = Volley.newRequestQueue(this);

        // URL for the API request
        String url = "http://coms-3090-001.class.las.iastate.edu:8080/api/cryptos";

        // Create a JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Loop through the array of cryptos
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject crypto = response.getJSONObject(i);
                                String name = crypto.getString("name");
                                if (name.equalsIgnoreCase("ethereum")) {
                                    etherPrice = crypto.getDouble("price");
                                    break;
                                }
                            }

                            // Log the price for debugging
                            Log.d("Ethereum Price", "Price: " + etherPrice);

                            // Update the TextView with the Ethereum price
                            if (etherPrice > 0) {
                                priceTextView.setText("$" + etherPrice);
                            } else {
                                priceTextView.setText("Ethereum price not found");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ethereumMenu.this, "Failed to parse data", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ethereumMenu", "Error: " + error.getMessage());
                        Toast.makeText(ethereumMenu.this, "Failed to get data from server", Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add the request to the queue
        queue.add(jsonArrayRequest);

        // Set up the button to display the fetched price when clicked
        buyEther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etherPrice > 0) {
                    // Display the correct etherPrice
                    Toast.makeText(ethereumMenu.this, "You have successfully bought $" + etherPrice + " worth of Ethereum", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ethereumMenu.this, "Ethereum price is not available", Toast.LENGTH_LONG).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ethereumMenu.this, cryptoPage.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Username", user);
                startActivity(intent);
            }
        });
    }
}
