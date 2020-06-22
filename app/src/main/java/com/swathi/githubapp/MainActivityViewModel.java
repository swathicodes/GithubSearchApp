package com.swathi.githubapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.swathi.githubapp.models.UserDetails;
import com.swathi.githubapp.models.UserRepos;
import com.swathi.githubapp.network.GithubRepository;

public class MainActivityViewModel extends ViewModel {

    MutableLiveData<UserDetails> userDetailsLiveData = new MutableLiveData<>();
    MutableLiveData<UserRepos> userReposLiveData = new MutableLiveData<>();

    void fetchUserDetails(String userId){
        GithubRepository.getInstance().getUserDetails(userId,userDetailsLiveData);
    }

    void fetchUserRepos(String userId){
        GithubRepository.getInstance().getUserRepos(userId,userReposLiveData);
    }

}
