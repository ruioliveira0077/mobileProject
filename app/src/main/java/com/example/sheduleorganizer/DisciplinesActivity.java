package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sheduleorganizer.MainActivity.EXTRA_MESSAGE;

public class DisciplinesActivity<course_id> extends AppCompatActivity{

    private static UserManager userManager;
    private ArrayList<String> textStrings = new ArrayList<String>();
    private List<Subjects> recyclerViewStrings = new ArrayList<>();
    private ImageView add;
    private Spinner dropdown;
    private Adapter dropdownAdapter;
    private Long course_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplines);

        userManager = userManager.getInstance();
        add = findViewById(R.id.add);
        dropdown = findViewById(R.id.selectCourse);

        dropdownAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, textStrings);
        dropdown.setAdapter((ArrayAdapter)dropdownAdapter);

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
                        textStrings.add(response.body().get(i).getTitle());
                    }
                    ((ArrayAdapter) dropdownAdapter).notifyDataSetChanged();

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

        // Recycler view Call
        RecyclerView subjectsList = (RecyclerView) findViewById(R.id.subjectsList);
        final ListAdapterSubjects adpater = new ListAdapterSubjects((ArrayList<Subjects>) recyclerViewStrings, DisciplinesActivity.this);
        subjectsList.setAdapter(adpater);
        subjectsList.setLayoutManager(new LinearLayoutManager(DisciplinesActivity.this));

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recyclerViewStrings.clear();

                userManager.getCourseByTitle(Long.parseLong(userId),String.valueOf(parent.getItemAtPosition(position)), new Callback<List<Courses>>() {
                    @Override
                    public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                        Log.w(" => ", new Gson().toJson(response));
                        if (response.code() == 200) {
                            Log.d("status", "200");

                            course_id= response.body().get(0).getId();

                            userManager.getSubjectsByCourse(response.body().get(0).getId(), new Callback<List<Subjects>>() {
                                @Override
                                public void onResponse(Call<List<Subjects>> call, Response<List<Subjects>> response) {
                                    Log.w(" => ", new Gson().toJson(response));
                                    if (response.code() == 200) {
                                        Log.d("statusSubjects", "200");

                                        for (int i = 0; i < response.body().size(); i++) {
                                            recyclerViewStrings.add(response.body().get(i));
                                        }
                                        adpater.notifyDataSetChanged();

                                    } else {
                                        Log.d("status", "Failed");
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Subjects>> call, Throwable t) {
                                    Log.d("eroos", t.getMessage());
                                    Toast.makeText(DisciplinesActivity.this,
                                            "Error is " + t.getMessage()
                                            , Toast.LENGTH_LONG).show();
                                }
                            });
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(course_id!=0){
                    Intent i = new Intent(DisciplinesActivity.this, CreateSubjectForm.class);
                    i.putExtra("course_id",Long.toString(course_id));
                    startActivity(i);
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.signOut:
                        SharedPreferences loginPreferences = getSharedPreferences("info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginPreferences.edit();
                        editor.putString("id",null);
                        editor.putString("username",null);
                        editor.commit();

                        Intent i = new Intent(DisciplinesActivity.this, Login.class);
                        i.putExtra(EXTRA_MESSAGE, "1");
                        startActivity(i);
                        return true;
                    case R.id.menu:
                        Intent is = new Intent(DisciplinesActivity.this, Menu.class);
                        startActivity(is);
                        return true;
                }
                return false;
            }
        });
    }
}