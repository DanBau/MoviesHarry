package com.example.moviesapi.Interfaces;

import com.example.moviesapi.Models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImovieApi {

    @GET("/")
    Call<List<Movie>> find(@Query("t") String q);
    //Call<List<Movie>> find(@Query("apikey") String k, @Query("t") String q);

}
