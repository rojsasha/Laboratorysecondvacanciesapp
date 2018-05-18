package com.example.rojsa.laboratorysecondvacanciesapp.data;

import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RequestInterface {
    @FormUrlEncoded
    @POST(Constants.ENDPOINT)
    Call<List<VacanciesModel>> getAllVacancies(@Field("login") String login,
                                               @Field("f") String f,
                                               @Field("limit") String limit,
                                               @Field("page") String page);

    @FormUrlEncoded
    @POST(Constants.ENDPOINT)
    Call<List<VacanciesModel>> getSearchVacancies(@Field("login") String login,
                                                  @Field("f") String f,
                                                  @Field("limit") String limit,
                                                  @Field("page") String page,
                                                  @Field("salary") String salary,
                                                  @Field("term") String term);
}
