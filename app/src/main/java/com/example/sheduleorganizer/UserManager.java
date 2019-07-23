package com.example.sheduleorganizer;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserManager {

    private static IUserApi service;
    private static UserManager userManager;

    private UserManager() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.69:8080/sheduleOrganizer/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        service = retrofit.create(IUserApi.class);
    }

    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public void createUser(String username, String password, String email, Callback<ResponseBody> callback) {
        Call<ResponseBody> userCall = service.createUser(username,password,email);
        userCall.enqueue(callback);
    }


    public void getCoursesByIdUser(Long  user_id, Callback<List<Courses>> callback){
        Call<List<Courses>> userCall = service.getCoursesByIdUser(user_id);
        userCall.enqueue(callback);
    }

    public void createCourse(long user_id, String title, Callback<ResponseBody> callback) {
        Call<ResponseBody> userCall = service.createUser(user_id,title);
        userCall.enqueue(callback);
    }

    public void getCourseByTitle(long user_id, String title, Callback<List<Courses>> callback) {
        Call<List<Courses>> userCall = service.courseByTitle(user_id,title);
        userCall.enqueue(callback);
    }

    public void getSubjectsByCourse(long course_id, Callback<List<Subjects>> callback) {
        Call<List<Subjects>> userCall = service.subjectByCourse(course_id);
        userCall.enqueue(callback);
    }

    public void createSubject(long course_id, String title, Callback<ResponseBody> callback) {
        Call<ResponseBody> userCall =  service.createSubject(course_id, title);
        userCall.enqueue(callback);
    }

    public void deleteCourse(long course_id, Callback<ResponseBody> callback) {
        Call<ResponseBody> userCall =  service.deleteCourse(course_id);
        userCall.enqueue(callback);
    }

    public void deleteSubject(long id, Callback<ResponseBody> callback) {
        Call<ResponseBody> userCall =  service.deleteSubject(id);
        userCall.enqueue(callback);
    }

    public void getClassesByDateAndSubjectId(Long subject_id, String date, Long user_id, Callback<List<Classes>> callback) {
        Call<List<Classes>> userCall = service.getClassesByDateSubjectId(subject_id, date, user_id);
        userCall.enqueue(callback);
    }

    public void getClassesByDate(String date, Long user_id, Callback<List<Classes>> callback) {
        Call<List<Classes>> userCall = service.getClassesByDate(date, user_id);
        userCall.enqueue(callback);
    }

    public void deleteClass(long id, Callback<ResponseBody> callback) {
        Call<ResponseBody> userCall =  service.deleteClass(id);
        userCall.enqueue(callback);
    }


    public void getsubjectByTitle(String title, Callback<List<Subjects>> callback) {
        Call<List<Subjects>> userCall = service.subjectByTitle(title);
        userCall.enqueue(callback);
    }

    public void allRooms(Callback<List<Rooms>> callback) {
        Call<List<Rooms>> userCall = service.allRooms();
        userCall.enqueue(callback);
    }

    public void getRoomByTitle(String title, Callback<List<Rooms>> callback) {
        Call<List<Rooms>> userCall = service.roomByTitle(title);
        userCall.enqueue(callback);
    }

    public void login(String email, String password, Callback<ResponseBody> callback) {
        Call<ResponseBody> userCall = service.login(email, password);
        userCall.enqueue(callback);
    }

    public void createClass(long subject_id, long room_id, String date, String dateToCompare, int duration, Callback<ResponseBody> callback) {
        Call<ResponseBody> userCall = service.createClass(subject_id,room_id, date, dateToCompare, duration);
        userCall.enqueue(callback);
    }
}
