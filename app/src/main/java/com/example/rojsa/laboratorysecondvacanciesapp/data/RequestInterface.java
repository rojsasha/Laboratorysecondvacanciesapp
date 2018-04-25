package com.example.rojsa.laboratorysecondvacanciesapp.data;

import com.example.rojsa.laboratorysecondvacanciesapp.model.AllDayModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;



public interface RequestInterface {
    @FormUrlEncoded
    @POST(Constants.ENDPOINT)
    Call<List<AllDayModel>> getAllVacancies(@Field("login") String login,
                                           @Field("f") String f,
                                           @Field("limit") String limit,
                                           @Field("page") String page);
}
