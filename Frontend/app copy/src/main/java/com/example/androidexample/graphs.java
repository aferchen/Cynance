package com.example.androidexample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class graphs extends AppCompatActivity {

    private Button lineGraph;
    boolean isAdmin;
    String username;
    int id;

    private BottomNavigationView bottomNavigationView;
    ArrayList barArraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphs_page);

        bottomNavigationView = findViewById(R.id.bottomNavigationViewGraphs);
        bottomNavigationView.setSelectedItemId(R.id.graphsButton);

        lineGraph = findViewById(R.id.linegraph);
        BarChart barChart = findViewById(R.id.barchart);
        getData();
        BarDataSet barDataSet = new BarDataSet(barArraylist, "Your Funds");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("Username");
        id = extras.getInt("ID");

        lineGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(graphs.this, lineChart.class);
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.homeBarButton){
                    Intent intent = new Intent(graphs.this, Balance_FinancePage.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                } else if (itemId == R.id.goalsBarButton) {
                    Intent intent = new Intent(graphs.this, goalsList.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                }else if(itemId == R.id.financialAdvisorButton){
                    Intent intent = new Intent(graphs.this, financialAdvisor.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);

                }else if(itemId == R.id.cryptoBarButton){
                    Intent intent = new Intent(graphs.this, cryptoPage.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);

                }

                return false;
            }
        });

    }

    private void getData(){
        //how is the funds look like for a user after each transaction
        barArraylist = new ArrayList();
        barArraylist.add(new BarEntry(2f, 30));
        barArraylist.add(new BarEntry(3f, 20));
        barArraylist.add(new BarEntry(4f, 40));
        barArraylist.add(new BarEntry(5f, 10));
        barArraylist.add(new BarEntry(6f, 50));


    }



}
