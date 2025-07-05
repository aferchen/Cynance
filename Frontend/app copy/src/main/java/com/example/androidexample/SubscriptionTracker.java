package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionTracker extends AppCompatActivity {
TextView n1, s1, b1, rd1, a1, n2, s2, b2, rd2, a2, n3, s3, b3, rd3, a3, n4, s4, b4, rd4, a4, n5, s5, b5, rd5, a5, n6, s6, b6, rd6, a6;
TextView n7, s7, b7, rd7, a7, n8, s8, b8, rd8, a8, n9, s9, b9, rd9, a9, n10, s10, b10, rd10, a10, Total;
ImageButton back, add, delete;
String URL = "http://coms-3090-001.class.las.iastate.edu:8080/api/subscriptions/user/";
String user;
EditText SubName, SubBilling, SubRenDate, SubAmnt;

int ID;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_tracker);
        back = findViewById(R.id.SubBackButton);
    n1 = findViewById(R.id.SubName1);
    s1 = findViewById(R.id.SubStatus1);
    b1 = findViewById(R.id.SubBilling1);
    rd1 = findViewById(R.id.SubRenDate1);
    a1 = findViewById(R.id.SubAmt1);
    n2 = findViewById(R.id.SubName2);
    s2 = findViewById(R.id.SubStatus2);
    b2 = findViewById(R.id.SubBilling2);
    rd2 = findViewById(R.id.SubRenDate2);
    a2 = findViewById(R.id.SubAmt2);
    n3 = findViewById(R.id.SubName3);
    s3 = findViewById(R.id.SubStatus3);
    b3 = findViewById(R.id.SubBilling3);
    rd3 = findViewById(R.id.SubRenDate3);
    a3 = findViewById(R.id.SubAmt3);
    n4 = findViewById(R.id.SubName4);
    s4 = findViewById(R.id.SubStatus4);
    b4 = findViewById(R.id.SubBilling4);
    rd4 = findViewById(R.id.SubRenDate4);
    a4 = findViewById(R.id.SubAmt4);
    n5 = findViewById(R.id.SubName5);
    s5 = findViewById(R.id.SubStatus5);
    b5 = findViewById(R.id.SubBilling5);
    rd5 = findViewById(R.id.SubRenDate5);
    a5 = findViewById(R.id.SubAmt5);
    n6 = findViewById(R.id.SubName6);
    s6 = findViewById(R.id.SubStatus6);
    b6 = findViewById(R.id.SubBilling6);
    rd6 = findViewById(R.id.SubRenDate6);
    a6 = findViewById(R.id.SubAmt6);
    n7 = findViewById(R.id.SubName7);
    s7 = findViewById(R.id.SubStatus7);
    b7 = findViewById(R.id.SubBilling7);
    rd7 = findViewById(R.id.SubRenDate7);
    a7 = findViewById(R.id.SubAmt7);
    n8 = findViewById(R.id.SubName8);
    s8 = findViewById(R.id.SubStatus8);
    b8 = findViewById(R.id.SubBilling8);
    rd8 = findViewById(R.id.SubRenDate8);
    a8 = findViewById(R.id.SubAmt8);
    n9 = findViewById(R.id.SubName9);
    s9 = findViewById(R.id.SubStatus9);
    b9 = findViewById(R.id.SubBilling9);
    rd9 = findViewById(R.id.SubRenDate9);
    a9 = findViewById(R.id.SubAmt9);
    n10 = findViewById(R.id.SubName10);
    s10 = findViewById(R.id.SubStatus10);
    b10 = findViewById(R.id.SubBilling10);
    rd10 = findViewById(R.id.SubRenDate10);
    a10 = findViewById(R.id.SubAmt10);
    Total = findViewById(R.id.Total);
    SubName = findViewById(R.id.addSubName);
    SubBilling = findViewById(R.id.addSubBilling);
    SubRenDate = findViewById(R.id.addRenDate);
    SubAmnt = findViewById(R.id.addAmount);
    add = findViewById(R.id.SubAddButton);
    delete = findViewById(R.id.SubDeleteButton);


    Bundle extras = getIntent().getExtras();
    if (extras != null) {
        ID = extras.getInt("ID");
        user = extras.getString("Username");
    }
    GetRequest(URL + ID);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(SubscriptionTracker.this, Balance_FinancePage.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Username", user);
                startActivity(intent);
            }
        });
        View.OnClickListener sharedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Shared logic for all buttons
            if (v.getId() == R.id.SubStatus1) {
                if(s1.getText().equals("Active")){
                    s1.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a1.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s1.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a1.getText().toString());
                    totalTotal = totalTotal + amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s1.getText().toString(), s1);
            }
            else if  (v.getId() == R.id.SubStatus2) {
                if(s2.getText().equals("Active")){
                    s2.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a2.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s2.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a2.getText().toString());
                    totalTotal = totalTotal + amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s2.getText().toString(), s2);
            }
            else if  (v.getId() == R.id.SubStatus3) {
                if(s3.getText().equals("Active")){
                    s3.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a3.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s3.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a3.getText().toString());
                    totalTotal = totalTotal + amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s3.getText().toString(), s3);
            }
            else if  (v.getId() == R.id.SubStatus4) {
                if(s4.getText().equals("Active")){
                    s4.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a4.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s4.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a4.getText().toString());
                    totalTotal = totalTotal + amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s4.getText().toString(), s4);
            }
            else if  (v.getId() == R.id.SubStatus5) {
                if(s5.getText().equals("Active")){
                    s5.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a5.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s5.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a5.getText().toString());
                    totalTotal = totalTotal + amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s5.getText().toString(), s5);
            }
            else if  (v.getId() == R.id.SubStatus6) {
                if(s6.getText().equals("Active")){
                    s6.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a6.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s6.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a6.getText().toString());
                    totalTotal = totalTotal + amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s6.getText().toString(), s6);
            }
            else if  (v.getId() == R.id.SubStatus7) {
                if(s7.getText().equals("Active")){
                    s7.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a7.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s7.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a7.getText().toString());
                    totalTotal = totalTotal + amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s7.getText().toString(), s7);
            }
            else if  (v.getId() == R.id.SubStatus8) {
                if(s8.getText().equals("Active")){
                    s8.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a8.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s8.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a8.getText().toString());
                    totalTotal = totalTotal + amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s8.getText().toString(), s8);
            }
            else if  (v.getId() == R.id.SubStatus9) {
                if(s9.getText().equals("Active")){
                    s9.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a9.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s9.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a9.getText().toString());
                    totalTotal = totalTotal + amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s9.getText().toString(), s9);
            }
            else if  (v.getId() == R.id.SubStatus10) {
                if(s10.getText().equals("Active")){
                    s10.setText("Inactive");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a10.getText().toString());
                    totalTotal = totalTotal - amount;
                    Total.setText(String.format("%.2f", totalTotal));
                }
                else{
                    s10.setText("Active");
                    double totalTotal = Double.parseDouble(Total.getText().toString());
                    double amount = Double.parseDouble(a10.getText().toString());
                    totalTotal = totalTotal + amount;
                    
                    Total.setText(String.format("%.2f", totalTotal));
                }
                assignColor(s10.getText().toString(), s10);
            }
        }
    };

    s1.setOnClickListener(sharedClickListener);
    s2.setOnClickListener(sharedClickListener);
    s3.setOnClickListener(sharedClickListener);
    s4.setOnClickListener(sharedClickListener);
    s5.setOnClickListener(sharedClickListener);
    s6.setOnClickListener(sharedClickListener);
    s7.setOnClickListener(sharedClickListener);
    s8.setOnClickListener(sharedClickListener);
    s9.setOnClickListener(sharedClickListener);
    s10.setOnClickListener(sharedClickListener);
    add.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
            String name = SubName.getText().toString();
            String billing = SubBilling.getText().toString();
            String renewaldate = SubRenDate.getText().toString();
            String amount = SubAmnt.getText().toString();

            // Create JSON object for POST request
            JSONObject json = new JSONObject();
            try {
                json.put("name", name);
                json.put("isActive", true);
                json.put("billing", billing);
                json.put("renewalDate", renewaldate);
                json.put("amount", amount);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            PostRequest(URL+ID, json);

        }
    });
    delete.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
            //String id = inputId.getText().toString();
            if (true) {
                // Send DELETE request
                String FindSub = SubName.getText().toString();
                GetIDRequest(URL + ID, FindSub);
            } else {
                //textGetResponse.setText("Please enter a valid Student ID");
                Toast.makeText(SubscriptionTracker.this, "Error deleting account, please try again, make sure goal name is correct", Toast.LENGTH_LONG).show();
            }
        }
    });
    }





    private void GetRequest(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        try {

                                int len = response.length();
                                double total = 0;
                                for(int i = 1; i <= len; i++){
                                    JSONObject subscrition = response.getJSONObject(i - 1);
                                    switch (i){
                                        case 1:
                                            n1.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s1.setText("Active");
                                            }else {
                                                s1.setText("Inactive");
                                            }
                                            b1.setText(subscrition.getString("billing"));
                                            rd1.setText(subscrition.getString("renewalDate"));
                                            a1.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s1.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s1);
                                            break;
                                        case 2:
                                            n2.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s2.setText("Active");
                                            }else {
                                                s2.setText("Inactive");
                                            }
                                            b2.setText(subscrition.getString("billing"));
                                            rd2.setText(subscrition.getString("renewalDate"));
                                            a2.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s2.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s2);
                                            break;
                                        case 3:
                                            n3.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s3.setText("Active");
                                            }else {
                                                s3.setText("Inactive");
                                            }
                                            b3.setText(subscrition.getString("billing"));
                                            rd3.setText(subscrition.getString("renewalDate"));
                                            a3.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s3.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s3);
                                            break;
                                        case 4:
                                            n4.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s4.setText("Active");
                                            }else {
                                                s4.setText("Inactive");
                                            }
                                            b4.setText(subscrition.getString("billing"));
                                            rd4.setText(subscrition.getString("renewalDate"));
                                            a4.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s4.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s4);
                                            break;
                                        case 5:
                                            n5.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s5.setText("Active");
                                            }else {
                                                s5.setText("Inactive");
                                            }
                                            b5.setText(subscrition.getString("billing"));
                                            rd5.setText(subscrition.getString("renewalDate"));
                                            a5.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s5.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s5);
                                            break;
                                        case 6:
                                            n6.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s6.setText("Active");
                                            }else {
                                                s6.setText("Inactive");
                                            }
                                            b6.setText(subscrition.getString("billing"));
                                            rd6.setText(subscrition.getString("renewalDate"));
                                            a6.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s6.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s6);
                                            break;
                                        case 7:
                                            n7.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s7.setText("Active");
                                            }else {
                                                s7.setText("Inactive");
                                            }
                                            b7.setText(subscrition.getString("billing"));
                                            rd7.setText(subscrition.getString("renewalDate"));
                                            a7.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s7.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s7);
                                            break;
                                        case 8:
                                            n8.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s8.setText("Active");
                                            }else {
                                                s8.setText("Inactive");
                                            }
                                            b8.setText(subscrition.getString("billing"));
                                            rd8.setText(subscrition.getString("renewalDate"));
                                            a8.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s8.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s8);
                                            break;
                                        case 9:
                                            n9.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s9.setText("Active");
                                            }else {
                                                s9.setText("Inactive");
                                            }
                                            b9.setText(subscrition.getString("billing"));
                                            rd9.setText(subscrition.getString("renewalDate"));
                                            a9.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s9.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s9);
                                            break;
                                        case 10:
                                            n10.setText(subscrition.getString("name"));
                                            if(subscrition.getString("isActive").equals("true")){
                                                s10.setText("Active");
                                            }else {
                                                s10.setText("Inactive");
                                            }
                                            b10.setText(subscrition.getString("billing"));
                                            rd10.setText(subscrition.getString("renewalDate"));
                                            a10.setText(subscrition.getString("amount"));
                                            total += howMuchMonthly(subscrition.getString("billing"), subscrition.getDouble("amount"),s10.getText().toString());
                                            assignColor(subscrition.getString("isActive"), s10);
                                            break;
                                    }
                                }
                                Total.setText(String.valueOf(total));
                            }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(SubscriptionTracker.this, "Volley Error", Toast.LENGTH_LONG).show();
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }
    private double howMuchMonthly(String time, double amount, String active){
    if(active.equals("Active") || active.equals("true")) {
        if (time.equals("monthly")) {
            return amount;
        } else if (time.equals("yearly")) {
            return amount / 12;
        } else if (time.equals("weekly")) {
            return amount * 4;
        } else if (time.equals("daily")) {
            return amount * 30;
        }
        return 0;
    }
    return 0;
    }
    private void assignColor(String active, TextView ID){
    if(active.equals("Active") || active.equals("true")){
        ID.setTextColor(getResources().getColor(R.color.goalGood));
    }
    else if(active.equals("Inactive") || active.equals("false"))
        ID.setTextColor(getResources().getColor(R.color.goalBad));
    else
        ID.setTextColor(getResources().getColor(R.color.black));
    }
    private void PostRequest(String url, JSONObject jsonData) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("POST RESPONSE", response.toString());
                        Toast.makeText(SubscriptionTracker.this, "Subscription successfully added!", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("POST ERROR", error.toString());
                error.printStackTrace();
                // Error in toast
                Toast.makeText(SubscriptionTracker.this,"Error: " + error.toString(), Toast.LENGTH_LONG).show();
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
    private void DeleteRequest(String url) {
        Toast.makeText(SubscriptionTracker.this, "Goal Deleted", Toast.LENGTH_LONG).show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DELETE RESPONSE", response.toString());
                        Toast.makeText(SubscriptionTracker.this, "Goal Deleted", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DELETE ERROR", error.toString());
                error.printStackTrace();
                //Toast.makeText(SubscriptionTracker.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
    private void GetIDRequest(String url, String titleToFind) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        //testText.setText(response.toString());

                        try {
                            // Iterate through each object in the array
                            int len = response.length();
                            double total = 0;
                            for(int i = 1; i <= len; i++) {
                                JSONObject subscrition = response.getJSONObject(i - 1);
                                if (subscrition.has("name") && titleToFind.equals(subscrition.getString("name"))) {
                                    // If there's a match, return the "id" value
                                    String retVal = subscrition.getString("id");
                                    DeleteRequest("http://coms-3090-001.class.las.iastate.edu:8080/api/subscriptions/" + retVal);

                                }
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
                        //testText.setText("volley error");
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

}

