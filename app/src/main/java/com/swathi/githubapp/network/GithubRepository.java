package com.swathi.githubapp.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.swathi.githubapp.models.UserDetails;
import com.swathi.githubapp.models.UserRepos;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubRepository {

    public void getUserDetails(String userId,MutableLiveData<UserDetails> userDetailsLiveData){

        githubWebService.getUserDetails(userId).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.body() != null){
                    userDetailsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                userDetailsLiveData.setValue(null);

            }
        });

    }

    public void getUserRepos(String userId,MutableLiveData<UserRepos> userReposMutableLiveData){

        githubWebService.getUserRepos(userId).enqueue(new Callback<UserRepos>() {
            @Override
            public void onResponse(Call<UserRepos> call, Response<UserRepos> response) {
                if (response.body() != null){
                    userReposMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserRepos> call, Throwable t) {
                userReposMutableLiveData.setValue(null);

            }
        });

    }

    // static variable single_instance of type Singleton
    private static GithubRepository single_instance = null;
    private static GithubWebService githubWebService;

    private GithubRepository() {
    }

    public static GithubRepository getInstance() {
        if (single_instance == null){
            single_instance = new GithubRepository();
            githubWebService = ApiClient.getClient().create(GithubWebService.class);
        }

        return single_instance;
    }

}
