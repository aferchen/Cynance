package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class currencyConverter extends AppCompatActivity {

    private Button convert;
    private EditText amount;
    private TextView printFin;
    private double result;
    private String currentCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_converter);
        Spinner mySpinner = findViewById(R.id.mySpinner);
        convert = findViewById(R.id.button17);
        amount = findViewById(R.id.editText2);
        //printFin = findViewById(R.id.textView14);



        String[] options = {"Select a Currency", "Euro", "Yen", "Sterling", "Rupee", "CAD", "Peso"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        //can i add time delay to calculating??
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String usd = amount.getText().toString();
                double userUSD;
                if(position == 1){ //Euro
                    userUSD = Double.parseDouble(usd);
                    result = userUSD * 0.9;
                    String value = Double.toString(result);
                    currentCurrency = "Euro";
                } else if(position == 2){ //Yen
                    userUSD = Double.parseDouble(usd);
                    result = userUSD * 150;
                    String value = Double.toString(result);
                    currentCurrency = "Yen";

                } else if(position==3){ //sterling
                    userUSD = Double.parseDouble(usd);
                    result = userUSD * 0.8;
                    currentCurrency = "Sterling";
                } else if(position==4){ //rupee
                    userUSD = Double.parseDouble(usd);
                    result = userUSD*83;
                    currentCurrency = "Rupee";
                } else if(position==5){ //CAD
                    userUSD = Double.parseDouble(usd);
                    result = userUSD*1.26;
                    currentCurrency = "CAD";
                } else if(position==6){ //peso
                    userUSD = Double.parseDouble(usd);
                    result = userUSD*18;
                    currentCurrency = "Peso";
                } else{
                    //do nothing
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(currencyConverter.this, "Please select a Currency", Toast.LENGTH_SHORT).show();
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(currencyConverter.this, "Calculating...", Toast.LENGTH_SHORT).show();
                int temp = (int)result;
//                printFin = findViewById(R.id.textView14);  // Replace with your actual TextView ID
//                printFin.setText(String.valueOf(temp));

                if(currentCurrency.equals("Euro")){
                    //add euro symbol €
                    printFin = findViewById(R.id.textView14);  // Replace with your actual TextView ID
                    printFin.setText(String.valueOf(temp) + "€");
                } else if(currentCurrency.equals("Yen")){
                    //add yen symbol
                    printFin = findViewById(R.id.textView14);  // Replace with your actual TextView ID
                    printFin.setText(String.valueOf(temp) + "¥");
                } else if(currentCurrency.equals("Sterling")){
                    //add pound symbol
                    printFin = findViewById(R.id.textView14);  // Replace with your actual TextView ID
                    printFin.setText(String.valueOf(temp) + "£");
                } else if(currentCurrency.equals("Rupee")){
                    //add rupee symbol
                    printFin = findViewById(R.id.textView14);  // Replace with your actual TextView ID
                    printFin.setText(String.valueOf(temp) + "₹");
                } else if(currentCurrency.equals("CAD")){
                    //add CAD symbol
                    printFin = findViewById(R.id.textView14);  // Replace with your actual TextView ID
                    printFin.setText(String.valueOf(temp) + "$");
                } else if(currentCurrency.equals("Peso")){
                    //add peso symbol
                    printFin = findViewById(R.id.textView14);  // Replace with your actual TextView ID
                    printFin.setText(String.valueOf(temp) + "₱");
                } else{
                    //do nothing

                }


            }
        });


    }

}
