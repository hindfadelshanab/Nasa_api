package com.example.nasa;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    String BASE_URL="https://api.nasa.gov/";
    private Context context;
    private ApiInterface apiInterface;
    public NetworkUtils(Context context){
        this.context=context;
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttp=new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).client(okHttp)
                .addConverterFactory(GsonConverterFactory.create() ).build();
        apiInterface=retrofit.create(ApiInterface.class);
    }

    public ApiInterface getApiInterface(){
        return apiInterface;
    }
}

