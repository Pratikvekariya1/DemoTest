package com.example.demoapp.di.api;

import com.example.demoapp.data.model.UserPicture;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ListAPI {

    @GET("list")
    Single<List<UserPicture>> getList(@Query("page") int page, @Query("limit") int limit);

}
