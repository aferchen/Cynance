package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class tipCreationAdmin extends AppCompatActivity {

    private EditText quoteEditText;
    private Button createTipButton;
    private RequestQueue requestQueue;
    private int numTips = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createtip_admin);



        quoteEditText = findViewById(R.id.editText3);
        createTipButton = findViewById(R.id.button18);
        requestQueue = Volley.newRequestQueue(this);

        createTipButton.setOnClickListener(v -> addQuote());
    }

    private void addQuote() {
        String newQuote = quoteEditText.getText().toString().trim();
        if (newQuote.isEmpty()) {
            Toast.makeText(this, "Please enter a quote", Toast.LENGTH_SHORT).show();
            return;
        }


        JSONObject quoteObject = new JSONObject();
        try {
            quoteObject.put("message", newQuote);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = "http://coms-3090-001.class.las.iastate.edu:8080/api/tips";


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, quoteObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(tipCreationAdmin.this, "Quote added successfully!", Toast.LENGTH_SHORT).show();
                        quoteEditText.setText("");  // Clear the input field
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("tipCreationAdmin", "Volley Error: " + error.toString());

                        Toast.makeText(tipCreationAdmin.this, "Failed to add quote: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        requestQueue.add(postRequest);
    }

}
