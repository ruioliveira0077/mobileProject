package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisciplinesActivity extends AppCompatActivity{

    private static UserManager userManager;
    private ArrayList<String> textStrings = new ArrayList<String>();
    private Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplines);
        userManager = userManager.getInstance();
        menu = findViewById(R.id.menu);

        SharedPreferences shared = getSharedPreferences("info", MODE_PRIVATE);
        final String userId = shared.getString("id", "DEFAULT");
        Log.d("userId", userId);

        userManager.getCoursesByIdUser(Long.parseLong(userId), new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                Log.w(" => ", new Gson().toJson(response));
                if (response.code() == 200) {
                    Log.d("status", "200");

                    for (int i = 0; i < response.body().size(); i++) {
                        //textStrings.add(response.body().get(i));
                        textStrings.add(response.body().get(i).getTitle());
                    }
                } else {
                    Log.d("status", "Failed");
                }
            }

            @Override
            public void onFailure(Call<List<Courses>> call, Throwable t) {
                Log.d("eroos", t.getMessage());
                Toast.makeText(DisciplinesActivity.this,
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

        Spinner dropdown = findViewById(R.id.selectCourse);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, textStrings);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("entrou", "entrou");
                Object item = parent.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(DisciplinesActivity.this, item.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DisciplinesActivity.this, Menu.class);
                startActivity(i);
            }
        });

    }
}