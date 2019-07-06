package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesActivity extends AppCompatActivity {

    private ArrayList<Courses> textStrings = new ArrayList<>();
    private static IUserApi service;
    private static UserManager userManager;
    private Button menu;
    ImageView add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        userManager = userManager.getInstance();
        menu = findViewById(R.id.menu);

        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        final String userId = shared.getString("id","DEFAULT");
        Log.d("userId", userId);

        userManager.getCoursesByIdUser(Long.parseLong(userId), new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                Log.w(" => ",new Gson().toJson(response));
                if (response.code() == 200) {
                    Log.d("status", "200");

                    for(int i=0; i<response.body().size();i++){
                        textStrings.add(response.body().get(i));
                    }

                    // Recycler view Call
                    RecyclerView coursesList = (RecyclerView) findViewById(R.id.coursesList);
                    ListAdapter adpater = new ListAdapter(textStrings, CoursesActivity.this);
                    coursesList.setAdapter(adpater);
                    coursesList.setLayoutManager(new LinearLayoutManager(CoursesActivity.this));

                } else {
                    Log.d("status", "Failed");
                }
            }
            @Override
            public void onFailure(Call<List<Courses>> call, Throwable t) {
                Log.d("eroos",t.getMessage() );
                Toast.makeText(CoursesActivity.this,
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });


        // Set add Course
        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CoursesActivity.this, "Add Course", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CoursesActivity.this, GenericForm.class));
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CoursesActivity.this, Menu.class);
                startActivity(i);
            }
        });

    }
}
