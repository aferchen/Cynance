package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.PopupWindow;

public class goalsList  extends AppCompatActivity {
    //VARIABLES
    String URL = "http://coms-3090-001.class.las.iastate.edu:8080/api";
    String username;
    int id;
    boolean isAdmin;
    Button GoalPageButton;
    BottomNavigationView bottomNavigationView;
    TextView g1, g1d, g2, g2d, g3, g3d, g4, g4d, g5, g5d;
    String TODAYSDATE = "2024-11-26";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goals_list);
        Volley.newRequestQueue(goalsList.this).getCache().clear();


        GoalPageButton = findViewById(R.id.changeGoals);
        bottomNavigationView = findViewById(R.id.bottomNavigationViewGoals);
        bottomNavigationView.setSelectedItemId(R.id.goalsBarButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("ID");
            username = extras.getString("Username");
            isAdmin = extras.getBoolean("ADMIN");
        }

        GetRequest(URL + "/users/" + id);


        GoalPageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(goalsList.this, goalsPage.class);
                intent.putExtra("ID", id);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.cryptoBarButton){
                    Intent intent = new Intent(goalsList.this, cryptoPage.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                } else if (itemId == R.id.homeBarButton) {
                    Intent intent = new Intent(goalsList.this, Balance_FinancePage.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                } else if(itemId == R.id.graphsButton) {
                    Intent intent = new Intent(goalsList.this, graphs.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                }else if(itemId == R.id.financialAdvisorButton) {
                    Intent intent = new Intent(goalsList.this, financialAdvisor.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                }
                return false;
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
                            if (response.has("goals")) {
                                JSONArray arrayResponse = response.getJSONArray("goals");
                                // Extract the student information and status from the JSON response
                                int len = arrayResponse.length();
                                String deadline = "";
                                for(int i = 1; i <= len; i++){
                                    JSONObject goal = arrayResponse.getJSONObject(i - 1);
                                    switch (i){
                                        case 1:
                                            findViewById(R.id.noGoalPresent).setVisibility(View.INVISIBLE);
                                            g1 = findViewById(R.id.Goal1);
                                            g1d = findViewById(R.id.Goal1Date);
                                            g1.setVisibility(View.VISIBLE);
                                            g1d.setVisibility(View.VISIBLE);
                                            g1.setText(goal.getString("title"));
                                            deadline = goal.getString("deadline");
                                            g1d.setText(deadline);
                                            if(compareDates(deadline, TODAYSDATE) < 0){
                                                g1d.setBackgroundColor(getResources().getColor(R.color.goalBad));
                                            }
                                            else{
                                                g1d.setBackgroundColor(getResources().getColor(R.color.goalGood));
                                            }
                                            String goal1Desc = goal.getString("description");
                                            g1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    showPopup(v, goal1Desc);
                                                }
                                            });
                                            break;
                                        case 2:
                                            g2 = findViewById(R.id.Goal2);
                                            g2d = findViewById(R.id.Goal2Date);
                                            g2.setVisibility(View.VISIBLE);
                                            g2d.setVisibility(View.VISIBLE);
                                            g2.setText(goal.getString("title"));
                                            deadline = goal.getString("deadline");
                                            g2d.setText(deadline);
                                            if(compareDates(deadline, TODAYSDATE) < 0){
                                                g2d.setBackgroundColor(getResources().getColor(R.color.goalBad));
                                            }
                                            else{
                                                g2d.setBackgroundColor(getResources().getColor(R.color.goalGood));
                                            }
                                            String goal2Desc = goal.getString("description");
                                            g2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    showPopup(v, goal2Desc);
                                                }
                                            });
                                            break;
                                        case 3:
                                            g3 = findViewById(R.id.Goal3);
                                            g3d = findViewById(R.id.Goal3Date);
                                            g3.setVisibility(View.VISIBLE);
                                            g3d.setVisibility(View.VISIBLE);
                                            g3.setText(goal.getString("title"));
                                            deadline = goal.getString("deadline");
                                            g3d.setText(deadline);
                                            if(compareDates(deadline, TODAYSDATE) < 0){
                                                g3d.setBackgroundColor(getResources().getColor(R.color.goalBad));
                                            }
                                            else{
                                                g3d.setBackgroundColor(getResources().getColor(R.color.goalGood));
                                            }
                                            String goal3Desc = goal.getString("description");
                                            g3.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    showPopup(v, goal3Desc);
                                                }
                                            });
                                            break;
                                        case 4:
                                            g4 = findViewById(R.id.Goal4);
                                            g4d = findViewById(R.id.Goal4Date);
                                            g4.setVisibility(View.VISIBLE);
                                            g4d.setVisibility(View.VISIBLE);
                                            g4.setText(goal.getString("title"));
                                            deadline = goal.getString("deadline");
                                            g4d.setText(deadline);
                                            if(compareDates(deadline, TODAYSDATE) < 0){
                                                g4d.setBackgroundColor(getResources().getColor(R.color.goalBad));
                                            }
                                            else{
                                                g4d.setBackgroundColor(getResources().getColor(R.color.goalGood));
                                            }
                                            String goal4Desc = goal.getString("description");
                                            g4.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    showPopup(v, goal4Desc);
                                                }
                                            });
                                            break;
                                        case 5:
                                            g5 = findViewById(R.id.Goal5);
                                            g5d = findViewById(R.id.Goal5Date);
                                            g5.setVisibility(View.VISIBLE);
                                            g5d.setVisibility(View.VISIBLE);
                                            g5.setText(goal.getString("title"));
                                            deadline = goal.getString("deadline");
                                            g5d.setText(deadline);
                                            if(compareDates(deadline, TODAYSDATE) < 0){
                                                g5d.setBackgroundColor(getResources().getColor(R.color.goalBad));
                                            }
                                            else{
                                                g5d.setBackgroundColor(getResources().getColor(R.color.goalGood));
                                            }
                                            String goal5Desc = goal.getString("description");
                                            g5.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    showPopup(v, goal5Desc);
                                                }
                                            });
                                            break;
                                    }
                                }
                                if(len == 0){
                                    findViewById(R.id.noGoalPresent).setVisibility(View.VISIBLE);
                                    findViewById(R.id.Goal1).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.Goal1Date).setVisibility(View.INVISIBLE);
                                }

                            } else {
                                // Handle the case when the student doesn't exist (i.e., 404 Not Found)
                                Toast.makeText(goalsList.this, "error getting goals or no goals for this user", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(goalsList.this, "Volley Error", Toast.LENGTH_LONG).show();
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
    public static int compareDates(String date1, String date2) {
        // Define the date format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the dates
            Date parsedDate1 = formatter.parse(date1);
            Date parsedDate2 = formatter.parse(date2); //2024-11-24

            // Compare the dates
            return parsedDate1.compareTo(parsedDate2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Return 0 if there was an error parsing
        }
    }
    private void showPopup(View anchorView, String description) {
        // Inflate your custom layout for the popup
        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

        // Create the PopupWindowp
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // Set the description to a TextView in the popup
        TextView descriptionTextView = popupView.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(description);

        // Show the PopupWindow
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchorView);
    }
}
