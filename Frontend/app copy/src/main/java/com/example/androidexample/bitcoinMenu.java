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

public class bitcoinMenu extends AppCompatActivity {

    Button buyBitcoin;
    private TextView priceTextView;
    private double bitcoinPrice = 0.0;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_bitcoin);

        buyBitcoin = findViewById(R.id.button7);
        priceTextView = findViewById(R.id.textView);
        backButton = findViewById(R.id.profileBackButton3);

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
                                if (name.equalsIgnoreCase("bitcoin")) {
                                    bitcoinPrice = crypto.getDouble("price");
                                    break;
                                }
                            }

                            // Update the TextView with the Bitcoin price
                            if (bitcoinPrice > 0) {
                                priceTextView.setText("$" + bitcoinPrice);
                            } else {
                                priceTextView.setText("Bitcoin price not found");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(bitcoinMenu.this, "Failed to parse data", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("bitcoinMenu", "Error: " + error.getMessage());
                        Toast.makeText(bitcoinMenu.this, "Failed to get data from server", Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add the request to the queue
        queue.add(jsonArrayRequest);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bitcoinMenu.this, cryptoPage.class);
                startActivity(intent);
            }
        });



    }
}
