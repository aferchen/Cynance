package com.example.androidexample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class finance_quiz extends AppCompatActivity {
    TextView question;
    TextView answer1, answer2, answer3, answer4, scoreTxt;
    String url = "http://coms-3090-001.class.las.iastate.edu:8080/api";
    String user;
    int ID;
    String Mockurl = "https://5cedebf9-5f70-44f7-811e-789d8886425c.mock.pstmn.io/quiz";
    Random rand = new Random();
    int randInt = rand.nextInt(20) + 1;
    int score = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_quiz_lay);
        //Volley.newRequestQueue(context).getCache().clear();
        Volley.newRequestQueue(finance_quiz.this).getCache().clear();

        question = findViewById(R.id.quizQuestion);
        scoreTxt = findViewById(R.id.Score);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("ID");
            user = extras.getString("Username");
        }
        GetRequest(Mockurl, randInt);
        scoreTxt.setText("Score: " + score);


    }

    private void GetRequest(String url, int rando) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject q = response.getJSONObject(i);
                                int qID = q.getInt("id");
                                if (qID == rando) {
                                    String askingQuestion = q.getString("question");
                                    Log.d("Question in question", q.toString());
                                    question.setText(askingQuestion);
                                    String actAnswer = q.getString("answer");
                                    JSONArray otherAns = q.getJSONArray("potential_answers");
                                    String ans1 = otherAns.getJSONObject(0).getString("ans1");
                                    String ans2 = otherAns.getJSONObject(1).getString("ans2");
                                    String ans3 = otherAns.getJSONObject(2).getString("ans3");
                                    String ans4 = otherAns.getJSONObject(3).getString("ans4");


                                    answer1 = findViewById(R.id.quizA1);
                                    answer2 = findViewById(R.id.quizA2);
                                    answer3 = findViewById(R.id.quizA3);
                                    answer4 = findViewById(R.id.quizA4);
                                    answer1.setText(ans1);
                                    answer2.setText(ans2);
                                    answer3.setText(ans3);
                                    answer4.setText(ans4);
                                    answer1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d("Clicked", "1");
                                            RightAnswer(url, ans1, rando);
                                        }
                                    });
                                    answer2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RightAnswer(url, ans2, rando);
                                        }
                                    });
                                    answer3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RightAnswer(url, ans3, rando);
                                        }
                                    });
                                    answer4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RightAnswer(url, ans4, rando);
                                        }
                                    });

                                    break;
                                }
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(finance_quiz.this, "Something went wrong :(", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(finance_quiz.this, "Volley Error", Toast.LENGTH_LONG).show();
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
    private void RightAnswer(String url, String clickedANS, int rando) {
        TextView CORRECT = findViewById(R.id.CorrectScreen);
        TextView INCORRECT = findViewById(R.id.IncorrectScreen);
        TextView INCORRECT2 = findViewById(R.id.IncorrectScreen2);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject q = response.getJSONObject(i);
                                int qID = q.getInt("id");
                                if (qID == rando) {
                                    String actAnswer = q.getString("answer");
                                    if(clickedANS.equals(actAnswer)){
                                        score += 100;
                                        scoreTxt.setText("Score: " + score);
                                        CORRECT.setVisibility(View.VISIBLE);
                                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                            CORRECT.setVisibility(View.INVISIBLE);
                                            randInt = rand.nextInt(20) + 1;
                                            GetRequest(Mockurl, randInt);
                                        }, 2000); // 1000 milliseconds = 1 second
                                    }
                                    else{
                                        INCORRECT.setVisibility(View.VISIBLE);
                                        INCORRECT2.setText(actAnswer);
                                        INCORRECT2.setVisibility(View.VISIBLE);
                                        score -= 50;
                                        scoreTxt.setText("Score: " + score);
                                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                            INCORRECT.setVisibility(View.INVISIBLE);
                                            INCORRECT2.setVisibility(View.INVISIBLE);
                                            randInt = rand.nextInt(20) + 1;
                                            GetRequest(Mockurl, randInt);
                                        }, 2000);
                                    }
                                    break;
                                }
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(finance_quiz.this, "Something went wrong :(", Toast.LENGTH_LONG).show();
                        }
                    }
                },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
                Toast.makeText(finance_quiz.this, "Volley Error", Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

}
