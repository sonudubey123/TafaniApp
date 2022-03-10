package com.sunmi.printerhelper.nextgen_sharique.retroft;


import android.content.Context;
import android.text.TextUtils;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.Url;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static String auth;

    public static Retrofit getClient(final Context mContext,int RequestNo) {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        // =========================================================
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        //.header("token", auth)
                        //.header("Accept", "application/json")
                        .method(original.method(), original.body());

                if (!TextUtils.isEmpty(auth)) {
                    requestBuilder.header("token", auth);
                    requestBuilder.addHeader("Headers", auth);
                }

                if (!TextUtils.isEmpty(auth)){
                    requestBuilder.addHeader("Authorization",auth);
                }


                Request request = requestBuilder.build();

                //   Log.e("TAG", "-------------Sharique-----request------requestBuilder------ "+new Gson().toJson(requestBuilder));
                //   Log.e("TAG", "-------------Sharique-----request------request------ "+new Gson().toJson(request));


                return chain.proceed
                        (request);
            }
        });


        httpClient.addInterceptor(interceptor); // show the log in logcat
        OkHttpClient client = httpClient.build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        retrofit.create(APIInterface.class);


        return retrofit;
    }


}
