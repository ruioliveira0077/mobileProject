package com.example.sheduleorganizer;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<ResponseBody> createUser(@Field("user_id") long user_id,
                                  @Field("title") String title);
}
