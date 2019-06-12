package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.example.sheduleorganizer.MainActivity.EXTRA_MESSAGE;

public class Login extends AppCompatActivity {

    private SharedPreferences loginPreferences;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        String userId = shared.getString("id","DEFAULT");
        Log.d("userId", userId);

        if(userId!="DEFAULT"){
            Intent i = new Intent(Login.this, Menu.class);
            i.putExtra(EXTRA_MESSAGE, "0");
            startActivity(i);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login);

        /*
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        Log.d("userID:", message);
        */

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                loginPreferences = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginPreferences.edit();
                editor.putString("id","1");
                editor.putString("username","rui");
                editor.commit();

                Intent i = new Intent(Login.this, Menu.class);
                i.putExtra(EXTRA_MESSAGE, "1");
                startActivity(i);
            }
        });
    }
}
