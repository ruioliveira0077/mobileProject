package com.example.sheduleorganizer;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sheduleorganizer.MainActivity.EXTRA_MESSAGE;

public class CreateClass extends AppCompatActivity {


    private Spinner dropdownCourses;
    private Spinner dropdownSubjects;
    private Spinner dropdownRooms;
    private Spinner dropdownHours;
    private Spinner dropdownDuration;
    private Adapter dropdownAdapterCourses;
    private Adapter dropdownAdapterSubjects;
    private Adapter dropdownAdapterRooms;
    private Adapter dropdownAdapterHours;
    private Adapter dropdownAdapterDuration;
    private ArrayList<String> roomsList = new ArrayList<String>();
    private ArrayList<String> hoursList = new ArrayList<String>();
    private ArrayList<String> coursesList = new ArrayList<String>();
    private ArrayList<String> durationList = new ArrayList<String>();
    private ArrayList<String> subjectsList = new ArrayList<String>();
    private ArrayList<Classes> classesList = new ArrayList<Classes>();
    private Long room_id ;
    private Long subject_id ;
    private  String date;
    private int duration;
    private static UserManager userManager;
    private Button addClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        Intent i = getIntent();
        String type = i.getStringExtra("type");
        final String dateSelected = i.getStringExtra("date");

        userManager = userManager.getInstance();
        SharedPreferences shared = getSharedPreferences("info", MODE_PRIVATE);
        final String userId = shared.getString("id", "DEFAULT");

        //// get type and create by type
        if(Integer.parseInt(type) == 0)
        {

            dropdownCourses = findViewById(R.id.selectCourse);
            dropdownSubjects = findViewById(R.id.selectSubject);
            dropdownRooms = findViewById(R.id.selectRooms);
            dropdownHours = findViewById(R.id.selectHours);
            dropdownDuration = findViewById(R.id.selectDuration);

            dropdownAdapterCourses = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, coursesList);
            dropdownCourses.setAdapter((ArrayAdapter)dropdownAdapterCourses);

            dropdownAdapterSubjects = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, subjectsList);
            dropdownSubjects.setAdapter((ArrayAdapter)dropdownAdapterSubjects);

            dropdownAdapterRooms = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, roomsList);
            dropdownRooms.setAdapter((ArrayAdapter)dropdownAdapterRooms);

            dropdownAdapterHours = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hoursList);
            dropdownHours.setAdapter((ArrayAdapter)dropdownAdapterHours);

            dropdownAdapterDuration = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, durationList);
            dropdownDuration.setAdapter((ArrayAdapter)dropdownAdapterDuration);

            userManager.getCoursesByIdUser(Long.parseLong(userId), new Callback<List<Courses>>() {
                @Override
                public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                    Log.w(" => ", new Gson().toJson(response));
                    if (response.code() == 200) {
                        Log.d("status", "200");

                        for (int i = 0; i < response.body().size(); i++) {
                            coursesList.add(response.body().get(i).getTitle());
                        }
                        ((ArrayAdapter) dropdownAdapterCourses).notifyDataSetChanged();

                    } else {
                        Log.d("status", "Failed");
                    }
                }

                @Override
                public void onFailure(Call<List<Courses>> call, Throwable t) {
                    Toast.makeText(CreateClass.this,
                            "Error is " + t.getMessage()
                            , Toast.LENGTH_LONG).show();
                }
            });

            dropdownCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    subjectsList.clear();
                    classesList.clear();
                    hoursList.clear();
                    durationList.clear();

                    userManager.getCourseByTitle(Long.parseLong(userId),String.valueOf(parent.getItemAtPosition(position)), new Callback<List<Courses>>() {
                        @Override
                        public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                            Log.w(" => ", new Gson().toJson(response));
                            if (response.code() == 200) {
                                Log.d("status", "200");

                                //course_id= response.body().get(0).getId();

                                userManager.getSubjectsByCourse(response.body().get(0).getId(), new Callback<List<Subjects>>() {
                                    @Override
                                    public void onResponse(Call<List<Subjects>> call, Response<List<Subjects>> response) {
                                        Log.w(" => ", new Gson().toJson(response));
                                        if (response.code() == 200) {
                                            Log.d("statusSubjects", "200");

                                            for (int i = 0; i < response.body().size(); i++) {
                                                subjectsList.add(response.body().get(i).getTitle());
                                            }
                                            ((ArrayAdapter) dropdownAdapterSubjects).notifyDataSetChanged();

                                        } else {
                                            Log.d("status", "Failed");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Subjects>> call, Throwable t) {
                                        Log.d("eroos", t.getMessage());
                                        Toast.makeText(CreateClass.this,
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
                            Toast.makeText(CreateClass.this,
                                    "Error is " + t.getMessage()
                                    , Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


        dropdownSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                classesList.clear();
                hoursList.clear();
                durationList.clear();
                userManager.getsubjectByTitle(String.valueOf(parent.getItemAtPosition(position)), new Callback<List<Subjects>>() {
                    @Override
                    public void onResponse(Call<List<Subjects>> call, Response<List<Subjects>> response) {
                        Log.w(" => ", new Gson().toJson(response));
                        if (response.code() == 200) {
                            Log.d("status", "200");

                            subject_id= response.body().get(0).getId();

                            userManager.getClassesByDateAndSubjectId(subject_id,dateSelected,Long.parseLong(userId), new Callback<List<Classes>>() {
                                @Override
                                public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                                    Log.w(" => ", new Gson().toJson(response));
                                    if (response.code() == 200) {
                                        Log.d("statusSubjects", "200");

                                        //////////////////DRPoDOWN FOR Hours//////////////////////////////////////////////////
                                        for (Integer j=9; j<23; j++)
                                        {
                                            hoursList.add(""+j+":00");
                                            ((ArrayAdapter) dropdownAdapterHours).notifyDataSetChanged();
                                        }

                                        for (int i = response.body().size()-1; i >= 0 ; i--) {
                                            classesList.add(response.body().get(i));
                                            String dateHour = response.body().get(i).getDate();

                                            String[] parts = dateHour.split("T");
                                            String[] parts2 = parts[1].split(":");

                                            int duration = response.body().get(i).getDuration();

                                            int inicio = Integer.parseInt(parts2[0]);
                                            int fim = inicio +duration;
                                            Log.d("inicio ", ""+inicio);
                                            Log.d("fim ", ""+fim);
                                            for(int h=0;h<hoursList.size();h++)
                                            {
                                                for(int k=inicio;k<=fim; k++)
                                                {
                                                    String[] hoursSplit = hoursList.get(h).split(":");
                                                    Log.d("Horas ", ""+k);
                                                    Log.d("Horas ", ""+hoursSplit[0]);

                                                    if(k == Integer.parseInt(hoursSplit[0]))
                                                    {
                                                        hoursList.remove(h);
                                                        ((ArrayAdapter) dropdownAdapterHours).notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        }

                                    } else {
                                        Log.d("status", "Failed");
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Classes>> call, Throwable t) {
                                    Log.d("eroos", t.getMessage());
                                    Toast.makeText(CreateClass.this,
                                            "Error is " + t.getMessage()
                                            , Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Log.d("status", "Failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Subjects>> call, Throwable t) {
                        Log.d("eroos", t.getMessage());
                        Toast.makeText(CreateClass.this,
                                "Error is " + t.getMessage()
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //////////////////DRPoDOWN FOR ROOMS//////////////////////////////////////////////////
        userManager.allRooms( new Callback<List<Rooms>>() {
            @Override
            public void onResponse(Call<List<Rooms>> call, Response<List<Rooms>> response) {
                Log.w(" => ", new Gson().toJson(response));
                if (response.code() == 200) {
                    Log.d("status", "200");
                    for (int i = 0; i < response.body().size(); i++) {
                        roomsList.add(response.body().get(i).getName());
                    }


                } else {
                    Log.d("status", "Failed");
                }
                ((ArrayAdapter) dropdownAdapterRooms).notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Rooms>> call, Throwable t) {
                Log.d("eroos", t.getMessage());
                Toast.makeText(CreateClass.this,
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

        dropdownHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                durationList.clear();

                String selectedHour = hoursList.get(position);
                String [] parts= selectedHour.split(":");
                date = dateSelected+" "+parts[0]+":00:00";
                if(Integer.parseInt(parts[0])==22){
                    durationList.add("1 Hour");
                }
                else
                {
                    for(int i=1; i<=4;i++ ){
                        String nextHour = hoursList.get(position+i);
                        String [] parts2= nextHour.split(":");
                        if (Integer.parseInt(parts2[0])==(Integer.parseInt(parts[0])+i)){
                            durationList.add(i+" Hour");
                            ((ArrayAdapter) dropdownAdapterDuration).notifyDataSetChanged();
                        }else
                        {
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dropdownRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userManager.getRoomByTitle(String.valueOf(parent.getItemAtPosition(position)), new Callback<List<Rooms>>() {
                    @Override
                    public void onResponse(Call<List<Rooms>> call, Response<List<Rooms>> response) {
                        Log.w(" => ", new Gson().toJson(response));
                        if (response.code() == 200) {
                            Log.d("status", "200");

                            room_id= response.body().get(0).getId();

                        } else {
                            Log.d("status", "Failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Rooms>> call, Throwable t) {
                        Log.d("eroos", t.getMessage());
                        Toast.makeText(CreateClass.this,
                                "Error is " + t.getMessage()
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dropdownDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String durationString= String.valueOf(parent.getItemAtPosition(position));
               String[] durationSplit = durationString.split(" ");
               duration = Integer.parseInt(durationSplit[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addClass= findViewById(R.id.addClass);

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userManager.createClass(subject_id, room_id, date, dateSelected, duration, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.w(" => ",new Gson().toJson(response));
                        if (response.code() == 200) {
                            Log.d("status", "200");


                            Intent i = new Intent(CreateClass.this, DayGrid.class);
                            i.putExtra("date",dateSelected);
                            startActivity(i);


                        } else {
                            Log.d("status", "Failed");
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(CreateClass.this,
                                "Error is " + t.getMessage()
                                , Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////Navigation Bar ///////////////////////////////////////////////////////////////////
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

                        Intent i = new Intent(CreateClass.this, Login.class);
                        i.putExtra(EXTRA_MESSAGE, "1");
                        startActivity(i);
                        return true;
                    case R.id.menu:
                        Intent is = new Intent(CreateClass.this, Menu.class);
                        startActivity(is);
                        return true;
                }
                return false;
            }
        });

    }
}
