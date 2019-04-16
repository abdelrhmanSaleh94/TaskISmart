package com.example.abdelrahmansaleh.taskismart.data.rest;

import com.example.abdelrahmansaleh.taskismart.data.model.getHubRepos.GetHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("repos")
    Call<List<GetHubRepo>> GET_HUB_REPO_CALL();
}
