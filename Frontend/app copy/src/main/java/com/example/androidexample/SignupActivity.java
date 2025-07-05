package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private EditText confirmEditText;   // define confirm edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable
    private EditText firstNameText;     // define first name edittext variable
    private EditText lastNameText;      // define last name edittext variable
    private CheckBox adminCheckBox;     // define checkbox for admin privileges

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.signup_username_edt);
        passwordEditText = findViewById(R.id.signup_password_edt);
        confirmEditText = findViewById(R.id.signup_confirm_edt);
        loginButton = findViewById(R.id.signup_login_btn);
        signupButton = findViewById(R.id.signup_signup_btn);
        firstNameText = findViewById(R.id.signup_last_edt3);
        lastNameText = findViewById(R.id.signup_last_edt);
        adminCheckBox = findViewById(R.id.admin_privileges_checkbox);

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when login button is pressed, switch to Login Activity */
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirm = confirmEditText.getText().toString();
                String first = firstNameText.getText().toString();
                String last = lastNameText.getText().toString();

                boolean isAdmin = adminCheckBox.isChecked();

                if (!isPasswordValid(password)) {
                    Toast.makeText(getApplicationContext(), "Password must be at least 4 characters long and contain at least one special character.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (password.equals(confirm)) {
                    Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_LONG).show();
                    // Check if the username already exists before proceeding
                    checkIfUsernameExists(username, new UsernameCheckCallback() {
                        @Override
                        public void onResult(boolean exists) {
                            if (exists) {
                                Toast.makeText(getApplicationContext(), "Username already exists. Please choose another.", Toast.LENGTH_LONG).show();
                            } else {
                                sendSignupData(first, last, username, password, isAdmin);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendSignupData(String firstName, String lastName, String username, String password, boolean isAdmin) {
        //String url = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/signUpInformation";
        String url = "http://coms-3090-001.class.las.iastate.edu:8080/api/users/signup";

        // Create a JSON object with the signup data
        JSONObject signupData = new JSONObject();
        try {
            signupData.put("firstName", firstName);
            signupData.put("lastName", lastName);
            signupData.put("username", username);
            signupData.put("password", password);
            signupData.put("isAdmin", isAdmin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, signupData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null && response.length() > 0) {
                            Toast.makeText(getApplicationContext(), "Signup successful!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No response received from server.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Signup failed: " + error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void checkIfUsernameExists(String username, final UsernameCheckCallback callback) {
        //String url = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/helloName";  // Replace with actual URL
        String url = "http://coms-3090-001.class.las.iastate.edu:8080/api/users";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Use JsonArrayRequest to handle the array response
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        boolean usernameExists = false;
                        try {
                            // Iterate through the array to find the username
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject user = response.getJSONObject(i);
                                if (user.getString("username").equals(username)) {
                                    usernameExists = true;
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        callback.onResult(usernameExists);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error fetching user data: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        callback.onResult(false);  // Proceed as if username doesn't exist if there's an error
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }





    private boolean isPasswordValid(String password) {
        return password.length() >= 4 && containsSpecial(password); // Password must be at least 4 characters long
    }

    private boolean containsSpecial(String password) {
        for (char c : password.toCharArray()) {
            if ("!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?".indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }

    // Callback interface for username existence check
    interface UsernameCheckCallback {
        void onResult(boolean exists);
    }
}
