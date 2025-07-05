package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable
    private boolean isAdmin = false;
    private Button temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);            // link to Login activity XML

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.login_username_edt);
        passwordEditText = findViewById(R.id.login_password_edt);
        loginButton = findViewById(R.id.login_login_btn);    // link to login button in the Login activity XML
        signupButton = findViewById(R.id.login_signup_btn);  // link to signup button in the Login activity XML
        temp = findViewById(R.id.button16);


        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                String url = "http://coms-3090-001.class.las.iastate.edu:8080/api/users";
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                boolean usernameFound = false;
                                boolean passwordCorrect = false;
                                int ID = 0;

                                try {

                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject user = response.getJSONObject(i);
                                        if (user.getString("username").equals(username)) {
                                            usernameFound = true;

                                            if (user.getString("password").equals(password)) {
                                                passwordCorrect = true;

                                                if(user.getString("role").equals("ADMIN")){
                                                    isAdmin = true;
                                                }
                                            }
                                            ID = user.getInt("id");
                                            break;
                                        }
                                    }

                                    // Display appropriate messages
                                    if (!usernameFound) {
                                        Toast.makeText(LoginActivity.this, "Incorrect username", Toast.LENGTH_SHORT).show();
                                    } else if (!passwordCorrect) {
                                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Intent intent = new Intent(LoginActivity.this, Balance_FinancePage.class);
                                        intent.putExtra("Username", username);
                                        intent.putExtra("Password", password);
                                        intent.putExtra("ADMIN", isAdmin);
                                        intent.putExtra("ID", ID);

                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle errors
                                Toast.makeText(LoginActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            }






                        });

                // Add the request to the RequestQueue
                requestQueue.add(jsonArrayRequest);
            }
        });






        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);  // go to SignupActivity
            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, finance_quiz.class);
                startActivity(intent);
            }
        });





    }
}