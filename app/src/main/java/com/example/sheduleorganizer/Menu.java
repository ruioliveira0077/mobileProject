package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static com.example.sheduleorganizer.MainActivity.EXTRA_MESSAGE;

public class Menu extends AppCompatActivity {

    private SharedPreferences loginPreferences;
    private Button getCourses;
    private Button getDisciplines;
    private Button myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.signOut:
                        loginPreferences = getSharedPreferences("info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginPreferences.edit();
                        editor.putString("id",null);
                        editor.putString("username",null);
                        editor.commit();

                        Intent i = new Intent(Menu.this, Login.class);
                        i.putExtra(EXTRA_MESSAGE, "1");
                        startActivity(i);
                        return true;
                    case R.id.menu:
                        return true;
                }
                return false;
            }
        });

        getCourses = findViewById(R.id.myCourses);
        getDisciplines = findViewById(R.id.myDisciplines);
        myCalendar = findViewById(R.id.Calendar);

        getCourses.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, CoursesActivity.class);
                i.putExtra(EXTRA_MESSAGE, "1");
                startActivity(i);
            }
        });

        getDisciplines.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, DisciplinesActivity.class);
                i.putExtra(EXTRA_MESSAGE, "1");
                startActivity(i);
            }
        });

        myCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, MyCalendarActivity.class);
                i.putExtra(EXTRA_MESSAGE, "1");
                startActivity(i);
            }
        });

    }
}
