package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class goalsPage  extends AppCompatActivity {
    //VARIABLES
    String URL = "http://coms-3090-001.class.las.iastate.edu:8080/api";
    String user, retVal;
    int ID;
    Button delete;
    ImageButton put, post;
    EditText goalTxt, deadlineTxt, descTxt, oldGoal;
    Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goals_page);
        Volley.newRequestQueue(goalsPage.this).getCache().clear();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("ID");
            user = extras.getString("Username");
        }
        //GetRequest(URL + "/users/" + user);


        //IDS
        delete = findViewById(R.id.deleteGoalButton);
        post = findViewById(R.id.goalAddButton);
        put = findViewById(R.id.goalUpdateButton);
        goalTxt = findViewById(R.id.addGoalText);
        deadlineTxt = findViewById(R.id.addGoalDateText);
        descTxt = findViewById(R.id.addGoalDescriptionText);
        back = findViewById(R.id.goalsBackButton);
        oldGoal = findViewById(R.id.oldGoalName);



    //BUTTONS
    // Button to send DELETE request
        delete.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
        //String id = inputId.getText().toString();
        if (true) {
            // Send DELETE request
            String GoalToFind = oldGoal.getText().toString();
            GetRequest(URL + "/goals/user/" + ID, GoalToFind, "delete");
        } else {
            //textGetResponse.setText("Please enter a valid Student ID");
            Toast.makeText(goalsPage.this, "Error deleting account, please try again, make sure goal name is correct", Toast.LENGTH_LONG).show();
        }
    }
    });
    // Button to send POST/ADD request
    post.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
            String goal = goalTxt.getText().toString();
            String deadline = deadlineTxt.getText().toString();
            String description = descTxt.getText().toString();

            // Create JSON object for POST request
            JSONObject json = new JSONObject();
            try {
                json.put("title", goal);
                json.put("description", description);
                json.put("deadline", deadline);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            PostRequest(URL+"/goals/user/"+ID, json);

        }
    });
    // Button to send PUT/UPDATE request
    put.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
            String goal = goalTxt.getText().toString();
            String deadline = deadlineTxt.getText().toString();
            String description = descTxt.getText().toString();
            String GoalToFind = oldGoal.getText().toString();
            GetRequest(URL + "/goals/user/" + ID, GoalToFind, "put");
        }
    });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Intent intent = new Intent(goalsPage.this, goalsList.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Username", user);
                startActivity(intent);
            }

        });
}




    private void GetRequest(String url, String titleToFind, String reqType) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        //testText.setText(response.toString());

                        try {
                            // Iterate through each object in the array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                // Check if the "title" key exists and matches the titleToFind
                                if (jsonObject.has("title") && titleToFind.equals(jsonObject.getString("title"))) {
                                    // If there's a match, return the "id" value
                                    retVal = jsonObject.getString("id");
                                    if(reqType.equals("put")){
                                        JSONObject json = new JSONObject();
                                        json.put("title", jsonObject.getString("title"));
                                        json.put("description", jsonObject.getString("description"));
                                        json.put("deadline", jsonObject.getString("deadline"));
                                        PutRequest(URL + "/goals/" + retVal, json);
                                    } else if (reqType.equals("delete")) {
                                        DeleteRequest(URL + "/goals/" + retVal);
                                    }

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
    private void DeleteRequest(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DELETE RESPONSE", response.toString());

                        try {
                            // Extract the "message" and "status" from the JSON response
                            String message = response.getString("message");
                            String status = response.getString("status");

                            Toast.makeText(goalsPage.this, message + status, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(goalsPage.this, "Error parsing response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DELETE ERROR", error.toString());
                error.printStackTrace();
                Toast.makeText(goalsPage.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
    private void PostRequest(String url, JSONObject jsonData) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("POST RESPONSE", response.toString());
                        Toast.makeText(goalsPage.this, "Goal successfully added!", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("POST ERROR", error.toString());
                error.printStackTrace();
                // Error in toast
                Toast.makeText(goalsPage.this,"Error: " + error.toString(), Toast.LENGTH_LONG).show();
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
    private void PutRequest(String url, JSONObject jsonData) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PUT RESPONSE", response.toString());

                        try {
                            Toast.makeText(goalsPage.this, "Goal successfully updated", Toast.LENGTH_LONG).show();
                        } //catch (JSONException e)
                        catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(goalsPage.this,"Error parsing response" , Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PUT ERROR", error.toString());
                error.printStackTrace();
                Toast.makeText(goalsPage.this,"Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
    public interface OnDataReceivedCallback {
        void onDataReceived(String data);
    }

}
