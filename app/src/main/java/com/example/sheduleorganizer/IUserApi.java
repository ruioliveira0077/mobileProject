package com.example.sheduleorganizer;

import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUserApi {

    @FormUrlEncoded
    @POST("user/createUser")
    Call<ResponseBody> createUser(@Field("username") String title,
                                  @Field("password") String body,
                                  @Field("email") String email);

    @GET("getCourses/courses")
    Call<List<Courses>> getCoursesByIdUser(@Query("user_id")Long user_id);

    @FormUrlEncoded
    @POST("getCourses/createCourse")
    Call<ResponseBody> createCourse(@Field("user_id") long user_id,
                                  @Field("title") String title);

    @GET("getCourses/courseByTitle")
    Call<List<Courses>> courseByTitle(@Query("user_id")Long user_id,@Query("title")String title );

    @DELETE("getCourses/deleteCourse/{course_id}")
    Call<ResponseBody> deleteCourse(@Path("course_id") long course_id);

    @GET("getSubjects/subjectByCourse")
    Call<List<Subjects>> subjectByCourse(@Query("course_id")Long course_id);

    @FormUrlEncoded
    @POST("getSubjects/createSubject")
    Call<ResponseBody> createSubject(@Field("course_id") long user_id,
                                  @Field("title") String title);

    @DELETE("getSubjects/deleteSubject/{id}")
    Call<ResponseBody> deleteSubject(@Path("id") long id);

    @GET("getClasses/classesByDate")
    Call<List<Classes>> getClassesByDate(@Query("date") String date, @Query("user_id") Long user_id);

    @GET("getClasses/classesByDateAndSubject")
    Call<List<Classes>> getClassesByDateSubjectId(@Query("subject_id") Long subject_id, @Query("date") String date, @Query("user_id") Long user_id);

    @DELETE("getClasses/deleteClass/{id}")
    Call<ResponseBody> deleteClass(@Path("id") long id);

    @GET("getSubjects/subjectByTitle")
    Call<List<Subjects>> subjectByTitle(@Query("title")String title );

    @GET("getRooms/roomByTitle")
    Call<List<Rooms>> roomByTitle(@Query("title")String title );

    @GET("getRooms/allRooms")
    Call<List<Rooms>> allRooms();

    @POST("user/login")
    Call<ResponseBody> login(@Query("email") String email, @Query("password") String password);

    @FormUrlEncoded
    @POST("getClasses/createClass")
    Call<ResponseBody> createClass(@Field("subject_id") long subject_id,
                                   @Field("room_id") long room_id,
                                   @Field("date") String date,
                                   @Field("dateToCompare") String dateToCompare,
                                     @Field("duration") int duration);
    @FormUrlEncoded
    @PUT("getCourses/editCourse")
    Call<ResponseBody> editCourse(@Field("course_id") long course_id,
                                    @Field("title") String title);

    @FormUrlEncoded
    @PUT("getSubjects/editSubject")
    Call<ResponseBody> editSubject(@Field("subject_id") long subject_id,
                                     @Field("title") String title);

}
