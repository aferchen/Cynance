package com.example.androidexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import org.java_websocket.handshake.ServerHandshake;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
//import com.example.androidexample.databinding.BalanceFinanceBinding;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import java.util.Random;
import java.util.function.Consumer;

public class Balance_FinancePage extends AppCompatActivity {
    /*
    Saved Params
     */
    String Username;
    int id;
    boolean isAdmin;
    TextView user, bal;
    ImageButton profile, currencyButton, quizButton, subscriptionButton;
    BottomNavigationView bottomNavigationView;
    private WebSocketManager webSocketManager;
    TextView transact1, transact2, transact3, transact4, transact5, transact1bal, transact2bal, transact3bal, transact4bal, transact5bal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_finance);
        Random rand = new Random();
        int randomInt = rand.nextInt(20) + 1;
        // Set up WebSocketManager
        //webSocketManager = WebSocketManager.getInstance();
        //webSocketManager.setWebSocketListener(new MyWebSocketListener(this));

        // Connect to WebSocket server
        //String serverUrl = "wss://echo.websocket.org";
        //webSocketManager.connectWebSocket(serverUrl);



        /*
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/financeTips";
        String url = "";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{

                            for(int i=0;i<response.length();i++){

                                    JSONObject tip = response.getJSONObject(i);
                                    int tipID = tip.getInt("id");
                                    if (tipID == randomInt) {
                                        String tipQuote = tip.getString("message");

                                        TextView tipDisplay = findViewById(R.id.financeTip);
                                        tipDisplay.setText(tipQuote);
                                        break;
                                    }


                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Balance_FinancePage.this, "Failed to parse data", Toast.LENGTH_LONG).show();
                        }
                    }
                },

        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("balance page", "Error: " + error.getMessage());
                Toast.makeText(Balance_FinancePage.this, "Failed to get data from server", Toast.LENGTH_LONG).show();
            }
        }
        );
        queue.add(jsonArrayRequest);
        */

        /*
        View by ID's
         */
        bal = findViewById(R.id.actBalance);
        user = findViewById(R.id.userTxt);
        profile = findViewById(R.id.profileButton);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.homeBarButton);
        transact1 = findViewById(R.id.transaction1);
        transact2 = findViewById(R.id.transaction2);
        transact3 = findViewById(R.id.transaction3);
        transact4 = findViewById(R.id.transaction4);
        transact5 = findViewById(R.id.transaction5);
        transact1bal = findViewById(R.id.transaction1bal);
        transact2bal = findViewById(R.id.transaction2bal);
        transact3bal = findViewById(R.id.transaction3bal);
        transact4bal = findViewById(R.id.transaction4bal);
        transact5bal = findViewById(R.id.transaction5bal);
        currencyButton = findViewById(R.id.currencyConvTemp);
        subscriptionButton = findViewById(R.id.SubcriptionsTrackerButton);
        quizButton = findViewById(R.id.FinanceQuizButton);



        /* extract data passed into this activity from another activity */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Username = extras.getString("Username");
            isAdmin = extras.getBoolean("ADMIN");
            id = extras.getInt("ID");
        }
        user.setText(Username);

        GetRequest("http://coms-3090-001.class.las.iastate.edu:8080/api/users/username/" + Username);
        GetTransactions("http://coms-3090-001.class.las.iastate.edu:8080/api/transactions/user/" + id + "/all");

        //Toast.makeText(Balance_FinancePage.this, "ID is " + id, Toast.LENGTH_LONG).show();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = null;

                if(itemId == R.id.cryptoBarButton){
                    intent = new Intent(Balance_FinancePage.this, cryptoPage.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", Username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                } else if (itemId == R.id.goalsBarButton) {
                    intent = new Intent(Balance_FinancePage.this, goalsList.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", Username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);

                } else if(itemId == R.id.financialAdvisorButton){
                    intent = new Intent(Balance_FinancePage.this, financialAdvisor.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", Username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);

                } else if(itemId == R.id.graphsButton) {
                    intent = new Intent(Balance_FinancePage.this, graphs.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", Username);
                    intent.putExtra("ADMIN", isAdmin);

                    startActivity(intent);
                }

                return false;
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Balance_FinancePage.this, profileActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });

        currencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Balance_FinancePage.this, currencyConverter.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });
        subscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Balance_FinancePage.this, SubscriptionTracker.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Balance_FinancePage.this, finance_quiz.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });

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
                                if (!response.getString("balance").equals("null")) {
                                    bal.setText(response.getString("balance"));
                                } else {
                                    bal.setText("No recorded balance");
                                }
                                if (!response.getString("firstName").equals("null")) {
                                    user.setText(response.getString("firstName"));
                                } else {
                                    // Handle the case when the student doesn't exist (i.e., 404 Not Found)
                                    Toast.makeText(Balance_FinancePage.this, "error getting user", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Balance_FinancePage.this, "Volley Error", Toast.LENGTH_LONG).show();
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

    // Custom WebSocketListener for handling messages and updating the UI
    private class MyWebSocketListener implements WebSocketListener {
        private final Activity activity;

        public MyWebSocketListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onWebSocketOpen(ServerHandshake handshakedata) {
            Log.d("WebSocket", "Connection opened");
        }

        @Override
        public void onWebSocketMessage(String message) {
            Log.d("WebSocket", "Received: " + message);

            try {
                JSONArray response = new JSONArray(message);
                int len = response.length();
                String[] values = new String[10];
                //String trans1, trans1bal;

                for(int i = 0; i <= len; i++) {
                    JSONObject currentTransaction = response.getJSONObject(i);
                    switch (i) {
                        case 0:
                            if(len == 0){
                                transact1.setVisibility(View.INVISIBLE);
                                transact1bal.setVisibility(View.INVISIBLE);
                            }
                            break;

                        case 1:
                            transact1.setVisibility(View.VISIBLE);
                            transact1bal.setVisibility(View.VISIBLE);
                            values[0] = currentTransaction.getString("type");
                            values[1] = currentTransaction.getString("amount");
                            //trans1 = currentTransaction.getString("type");
                            //trans1bal = currentTransaction.getString("amount");
                            break;
                        case 2:
                            transact2.setVisibility(View.VISIBLE);
                            transact2bal.setVisibility(View.VISIBLE);
                            values[2] = currentTransaction.getString("type");
                            values[3] = currentTransaction.getString("amount");
                            break;
                        case 3:
                            transact3.setVisibility(View.VISIBLE);
                            transact3bal.setVisibility(View.VISIBLE);
                            values[4] = currentTransaction.getString("type");
                            values[5] = currentTransaction.getString("amount");
                            break;
                        case 4:
                            transact4.setVisibility(View.VISIBLE);
                            transact4bal.setVisibility(View.VISIBLE);
                            values[6] = currentTransaction.getString("type");
                            values[7] = currentTransaction.getString("amount");
                            break;
                        case 5:
                            transact5.setVisibility(View.VISIBLE);
                            transact5bal.setVisibility(View.VISIBLE);
                            values[8] = currentTransaction.getString("type");
                            values[9] = currentTransaction.getString("amount");
                            break;
                    }
                }
                //String trans1 = response.getString("Transaction");
                //Update on response
                activity.runOnUiThread(() -> {
                    // Find TextView and update with parsed JSON value
                    if(values != null) {
                        switch (len) {
                            default:
                            case 5:
                                transact5.setText(values[8]);
                                transact5bal.setText(values[9]);
                            case 4:
                                transact4.setText(values[6]);
                                transact4bal.setText(values[7]);
                            case 3:
                                transact3.setText(values[4]);
                                transact3bal.setText(values[5]);
                            case 2:
                                transact2.setText(values[2]);
                                transact2bal.setText(values[3]);
                            case 1:
                                transact1.setText(values[0]);
                                transact1bal.setText(values[1]);
                            case 0:

                        }
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("WebSocket", "Failed to parse JSON: " + e.getMessage());
            }
        }

        @Override
        public void onWebSocketClose(int code, String reason, boolean remote) {
            Log.d("WebSocket", "Connection closed with reason: " + reason);
        }

        @Override
        public void onWebSocketError(Exception ex) {
            Log.e("WebSocket", "Error occurred: " + ex.getMessage());
        }
    }
    private class NotificationWebsocket implements WebSocketListener {
        private final Activity activity;

        public NotificationWebsocket(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onWebSocketOpen(ServerHandshake handshakedata) {
            Log.d("WebSocket", "Connection opened");
        }

        @Override
        public void onWebSocketMessage(String message) {
            Log.d("WebSocket", "Received: " + message);
    //Update on response
                activity.runOnUiThread(() -> {
                    Toast.makeText(Balance_FinancePage.this, message, Toast.LENGTH_LONG).show();
                });
            /*try {


            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("WebSocket", "Failed to parse JSON: " + e.getMessage());
            }

             */
        }

        @Override
        public void onWebSocketClose(int code, String reason, boolean remote) {
            Log.d("WebSocket", "Connection closed with reason: " + reason);
        }

        @Override
        public void onWebSocketError(Exception ex) {
            Log.e("WebSocket", "Error occurred: " + ex.getMessage());
        }
    }
    private void GetTransactions(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        try {
                            int len = response.length();
                            String[] values = new String[10];

                            for(int i = 1; i <= len; i++) {
                                JSONObject currentTransaction = response.getJSONObject(i-1);
                                Log.d("Volley Response", "JSON" + i);
                                switch (i) {
                                    case 1:
                                        transact1.setVisibility(View.VISIBLE);
                                        transact1bal.setVisibility(View.VISIBLE);
                                        values[0] = currentTransaction.getString("type");
                                        values[1] = currentTransaction.getString("amount");
                                        //trans1 = currentTransaction.getString("type");
                                        //trans1bal = currentTransaction.getString("amount");
                                        break;
                                    case 2:
                                        transact2.setVisibility(View.VISIBLE);
                                        transact2bal.setVisibility(View.VISIBLE);
                                        values[2] = currentTransaction.getString("type");
                                        values[3] = currentTransaction.getString("amount");
                                        break;
                                    case 3:
                                        transact3.setVisibility(View.VISIBLE);
                                        transact3bal.setVisibility(View.VISIBLE);
                                        values[4] = currentTransaction.getString("type");
                                        values[5] = currentTransaction.getString("amount");
                                        break;
                                    case 4:
                                        transact4.setVisibility(View.VISIBLE);
                                        transact4bal.setVisibility(View.VISIBLE);
                                        values[6] = currentTransaction.getString("type");
                                        values[7] = currentTransaction.getString("amount");
                                        break;
                                    case 5:
                                        transact5.setVisibility(View.VISIBLE);
                                        transact5bal.setVisibility(View.VISIBLE);
                                        values[8] = currentTransaction.getString("type");
                                        values[9] = currentTransaction.getString("amount");
                                        break;
                                }
                            }
                                switch (len) {
                                    default:
                                    case 5:
                                        transact5.setText(values[8]);
                                        transact5bal.setText(values[9]);
                                    case 4:
                                        transact4.setText(values[6]);
                                        transact4bal.setText(values[7]);
                                    case 3:
                                        transact3.setText(values[4]);
                                        transact3bal.setText(values[5]);
                                    case 2:
                                        transact2.setText(values[2]);
                                        transact2bal.setText(values[3]);
                                    case 1:
                                        transact1.setText(values[0]);
                                        transact1bal.setText(values[1]);
                                    case 0:
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
                        Toast.makeText(Balance_FinancePage.this, "Volley Error", Toast.LENGTH_LONG).show();
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
