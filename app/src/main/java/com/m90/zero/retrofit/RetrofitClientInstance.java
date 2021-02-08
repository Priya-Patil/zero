package com.m90.zero.retrofit;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.m90.zero.productdetail.model.ProductDescribeAddheadResponse;
import com.m90.zero.productdetail.model.ProductDescribeResponse;
import com.m90.zero.utils.Utilities;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;

    Context context;
    static String accesstoken ;

   //public static final String BASE_URL = "http://z.eurekatalents.in/api/";
   public static final String BASE_URL = "http://192.168.0.12:8000/api/";

    //public static final String BASE_URL_IMG = "http://z.eurekatalents.in/";
    public static final String BASE_URL_IMG = "http://192.168.0.12:8000/";

    public RetrofitClientInstance(Context context, String accesstoken) {
        this.context = context;
        this.accesstoken = accesstoken;
    }

    public static Retrofit getRetrofitInstanceServer() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



}
