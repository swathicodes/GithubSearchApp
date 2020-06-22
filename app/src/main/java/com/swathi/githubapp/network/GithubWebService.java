package com.swathi.githubapp.network;

import com.swathi.githubapp.models.UserDetails;
import com.swathi.githubapp.models.UserRepos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface GithubWebService {

    @GET("users/{userid}")
    Call<UserDetails> getUserDetails(
            @Path(value = "userid", encoded = true) String userId);

    @GET("users/{userid}/repos")
    Call<UserRepos> getUserRepos(
            @Path(value = "userid", encoded = true) String userId);

}
