package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity  {
    public static final String EXTRA_MESSAGE ="" ;
    private Button submitButton;
    private Button loginButton;
    private EditText password;
    private EditText username;
    private EditText email;
    public static UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userManager = userManager.getInstance();

        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        submitButton = findViewById(R.id.submit);
        loginButton = findViewById(R.id.login);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                userManager.createUser(username.getText().toString(),email.getText().toString(),password.getText().toString(), new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.w(" => ",new Gson().toJson(response));
                        if (response.code() == 200) {
                            Log.d("status", "200");

                            Intent i = new Intent(MainActivity.this, Login.class);
                            i.putExtra(EXTRA_MESSAGE, "1");
                            startActivity(i);
                        } else {
                            Log.d("status", "Failed");
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                "Error is " + t.getMessage()
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                i.putExtra(EXTRA_MESSAGE, "0");
                startActivity(i);
            }
        });
    }
}
