package com.example.rojsa.laboratorysecondvacanciesapp.data;

import com.example.rojsa.laboratorysecondvacanciesapp.BuildConfig;
import com.example.rojsa.laboratorysecondvacanciesapp.utils.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rojsa on 18.04.2018.
 */

public class ConnectInternet {
    private static RequestInterface service = null;

    public static RequestInterface initRetrofit(){
        if(service == null) {
            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build()
                    .create(RequestInterface.class);
        }
        return service;
    }
    private static OkHttpClient getOkHttpClient(){
        return new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/json;version=1");

                        return chain.proceed(builder.build());
                    }
                })
                .build();
    }
}
