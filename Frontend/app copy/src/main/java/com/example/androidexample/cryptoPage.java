package com.example.androidexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class cryptoPage extends AppCompatActivity {


    private Button bitcoinButton;
    private Button ethereumButton;
    private Button dogecoinButton;
    private Button solanaButton;
    private Button liteCoinButton;
    private Button aaveButton;

    private int id;

    private Button admin;

    private String username;

    private boolean isAdmin;
    private boolean adminVis;

    private BottomNavigationView bottomNavigationView;

    private ImageButton backButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_page);
        bitcoinButton = findViewById(R.id.button);
        ethereumButton = findViewById(R.id.button2);
        dogecoinButton = findViewById(R.id.button3);
        solanaButton = findViewById(R.id.button4);
        liteCoinButton = findViewById(R.id.button5);
        aaveButton = findViewById(R.id.button6);
        admin = findViewById(R.id.adminButton);
        bottomNavigationView = findViewById(R.id.bottomNavigationViewCrypto);
        bottomNavigationView.setSelectedItemId(R.id.cryptoBarButton);

        checkIfAdmin();



        /* extract data passed into this activity from another activity */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("Username");
            id = extras.getInt("ID");
            isAdmin = extras.getBoolean("ADMIN");
        }

        bitcoinButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(cryptoPage.this, bitcoinMenu.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", username);
                startActivity(intent);  // go to SignupActivity
            }
        });


        ethereumButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(cryptoPage.this, ethereumMenu.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", username);
                startActivity(intent);  // go to SignupActivity
            }
        });

        dogecoinButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(cryptoPage.this, dogecoinMenu.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", username);
                startActivity(intent);  // go to SignupActivity
            }
        });

        solanaButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(cryptoPage.this, solanaMenu.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", username);
                startActivity(intent);  // go to SignupActivity
            }
        });


        liteCoinButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(cryptoPage.this, litecoinMenu.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", username);
                startActivity(intent);  // go to SignupActivity
            }
        });

        aaveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(cryptoPage.this, aaveMenu.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", username);
                startActivity(intent);  // go to SignupActivity
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cryptoPage.this, Admin.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.homeBarButton){
                    Intent intent = new Intent(cryptoPage.this, Balance_FinancePage.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                } else if (itemId == R.id.goalsBarButton) {
                    Intent intent = new Intent(cryptoPage.this, goalsPage.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                }else if(itemId == R.id.financialAdvisorButton){
                    Intent intent = new Intent(cryptoPage.this, financialAdvisor.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);

                } else if(itemId == R.id.graphsButton) {
                    Intent intent = new Intent(cryptoPage.this, graphs.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                }
                return false;
            }
        });
    }


    private void checkIfAdmin() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //String url = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/isAdmin";
        String url = "http://coms-3090-001.class.las.iastate.edu:8080/api/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject user = response.getJSONObject(i);
                            if (user.getString("username").equals(username)) {  // Check if username matches
                                adminVis = user.getString("role").equalsIgnoreCase("ADMIN");
                                if (adminVis || isAdmin) {
                                    admin.setVisibility(View.VISIBLE);
                                } else {
                                    admin.setVisibility(View.GONE);
                                }
                                break; // Exit the loop once the user is found
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Failed to check admin status: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        requestQueue.add(jsonArrayRequest); // Add the request to the queue

    }


}




