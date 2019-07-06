package com.example.sheduleorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.sheduleorganizer.MainActivity.EXTRA_MESSAGE;

public class Menu extends AppCompatActivity {

    private SharedPreferences loginPreferences;
    private Button signOut;
    private Button getCourses;
    private Button getDisciplines;
    private Button myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        signOut = findViewById(R.id.signOut);
        getCourses = findViewById(R.id.myCourses);
        getDisciplines = findViewById(R.id.myDisciplines);
        myCalendar = findViewById(R.id.Calendar);


        signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                loginPreferences = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginPreferences.edit();
                editor.putString("id",null);
                editor.putString("username",null);
                editor.commit();

                Intent i = new Intent(Menu.this, Login.class);
                i.putExtra(EXTRA_MESSAGE, "1");
                startActivity(i);
            }
        });

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
