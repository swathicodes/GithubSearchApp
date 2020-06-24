package com.swathi.githubapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.swathi.githubapp.models.UserDetails;
import com.swathi.githubapp.models.UserRepos;
import com.swathi.githubapp.network.GithubRepository;

public class MainActivityViewModel extends ViewModel {

    LiveData<UserDetails> userDetailsLiveData = new MutableLiveData<>();
    LiveData<UserRepos> userReposLiveData = new MutableLiveData<>();

    void fetchUserDetails(String userId){
        GithubRepository.getInstance().getUserDetails(userId,(MutableLiveData<UserDetails>) userDetailsLiveData);
    }

    void fetchUserRepos(String userId){
        GithubRepository.getInstance().getUserRepos(userId,(MutableLiveData<UserRepos>)userReposLiveData);
    }

}
