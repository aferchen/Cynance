package com.example.androidexample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class lineChart extends AppCompatActivity {

    //i will need an integer every time a button from these three are clicked to add more or less to the pie chart

    private Button click;
    private PieChart chart;

    //in this branch mock the amounts of graphs using a random number with different users

     int depositAmount;
     int withdrawAmount;
     int transferAmount;
     TextView deposit;
     TextView withdraw;
     TextView transfer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linechart);

        deposit = findViewById(R.id.textView22);
        withdraw = findViewById(R.id.textView23);
        transfer = findViewById(R.id.textView24);

        click = findViewById(R.id.btn_click);
        chart = findViewById(R.id.pie_chart);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToPieChart();



            }
        });


        Random rand = new Random();
        int randomID = rand.nextInt(4) + 1;

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/pieChart";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, // Request method (GET in this case)
                url,                // The URL to send the request to
                null,               // No request body for GET request
                new Response.Listener<JSONArray>() { // Listener for success
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Loop through the response and find the data for the random ID
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject user = response.getJSONObject(i);
                                int id = user.getInt("id");

                                if (id == randomID) {
                                    depositAmount = user.getInt("deposits");
                                    withdrawAmount = user.getInt("withdrawls");
                                    transferAmount = user.getInt("transfers");

                                    deposit.setText(String.valueOf(depositAmount));
                                    withdraw.setText(String.valueOf(withdrawAmount));
                                    transfer.setText(String.valueOf(transferAmount));
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(lineChart.this, "Failed to parse data", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() { // Listener for error
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        Toast.makeText(lineChart.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }







        private void addToPieChart(){
        chart.clearChart(); // Clear any existing data

        // Adding labeled slices with three different colors
        chart.addPieSlice(new PieModel("Deposit", depositAmount, Color.parseColor("#FFA726"))); // Orange
        chart.addPieSlice(new PieModel("Withdraw", withdrawAmount, Color.parseColor("#66BB6A"))); // Green
        chart.addPieSlice(new PieModel("Transfer", transferAmount, Color.parseColor("#2986F6"))); // Blue
        chart.startAnimation();
        click.setClickable(false);
    }
}
