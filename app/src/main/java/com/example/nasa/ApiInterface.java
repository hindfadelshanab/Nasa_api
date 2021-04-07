package com.example.nasa;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

  @GET("planetary/apod")
  Call<photoNasa> getphoto(@Query("api_key") String apiKey ,@Query("date") String date);
}
