package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
//import ua.naiksoftware.stomp.Stomp;
//import ua.naiksoftware.stomp.StompClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.java_websocket.handshake.ServerHandshake;

public class financialAdvisor extends AppCompatActivity implements WebSocketListener {

    private Button connect;
    private EditText users;

    boolean isAdmin;
    String Username;
    int id;
    @Override
    //@ServerEndpoint("/chat/{username}") JACOB NEEDS THIS
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advising_websocket);

        connect = (Button) findViewById(R.id.connection);
        users = (EditText) findViewById(R.id.name);


        connect.setOnClickListener(view -> {
           // String serverUrl = serverURL + users.getText().toString();

            //String serverUrl = "ws://coms-3090-001.class.las.iastate.edu:8080/chat/" + users.getText().toString();
            //String serverUrl = "ws://coms-3090-001.class.las.iastate.edu:8080/chat/zach";

            String serverUrl = "ws://10.0.2.2:8080/chat/" + users.getText().toString();


            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(financialAdvisor.this);

            // got to chat activity
            Intent intent = new Intent(financialAdvisor.this, chat.class);
            startActivity(intent);

        });
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.financialAdvisorButton);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Username = extras.getString("Username");
            id = extras.getInt("ID");
            isAdmin = extras.getBoolean("ADMIN");
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.cryptoBarButton){
                    Intent intent = new Intent(financialAdvisor.this, cryptoPage.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", Username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                } else if (itemId == R.id.goalsBarButton) {
                    Intent intent = new Intent(financialAdvisor.this, goalsList.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", Username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                } else if(itemId == R.id.homeBarButton) {
                    Intent intent = new Intent(financialAdvisor.this, Balance_FinancePage.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", Username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                } else if(itemId == R.id.graphsButton) {
                    Intent intent = new Intent(financialAdvisor.this, graphs.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Username", Username);
                    intent.putExtra("ADMIN", isAdmin);
                    startActivity(intent);
                }
                return false;
            }
        });

    }

    @Override
    public void onWebSocketMessage(String message) {}

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {}

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}

}



