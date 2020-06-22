package com.swathi.githubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.swathi.githubapp.models.UserDetails;
import com.swathi.githubapp.models.UserRepos;
import com.swathi.githubapp.models.UserReposItem;
import com.swathi.githubapp.recyclerview.RecyclerItemClickListener;
import com.swathi.githubapp.recyclerview.RepoAdapter;
import com.swathi.githubapp.recyclerview.RepoBottomSheetDialog;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;

    private Button searchBtn;
    private EditText useridSearchET;
    private ImageView userImage;
    private RecyclerView recyclerView;
    private RepoAdapter repoAdapter;
    private ProgressBar progressBar_loadImage, progressBar_repoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_main);

        initializeRecyclerView();
        initUI();

        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        mainActivityViewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);

        mainActivityViewModel.userDetailsLiveData.observe(this, new Observer<UserDetails>() {
            @Override
            public void onChanged(UserDetails userDetails) {
                updateUserDetails(userDetails);
            }
        });

        mainActivityViewModel.userReposLiveData.observe(this, new Observer<UserRepos>() {
            @Override
            public void onChanged(UserRepos userReposItems) {
                updateUserReposList(userReposItems);
            }
        });

        setListeners();

    }


    private void updateUserReposList(UserRepos userReposItems) {
        progressBar_repoList.setVisibility(View.GONE);
        if (userReposItems == null){
            Toast.makeText(this, "Error in fetching user Repositories", Toast.LENGTH_LONG).show();
            return;
        }
        repoAdapter.updateList(userReposItems);
    }

    private void updateUserDetails(UserDetails userDetails) {
        progressBar_loadImage.setVisibility(View.GONE);
        if (userDetails == null){
            Toast.makeText(this, "Error in fetching Avatar", Toast.LENGTH_LONG).show();
            return;
        }
        Picasso.get().load(userDetails.getAvatar_url()).into(userImage);

    }

    private void setListeners() {

        searchBtn.setOnClickListener(v -> {

            String userId = useridSearchET.getText().toString();
            if (userId.equals("") || userId.isEmpty()){
                Toast.makeText(MainActivity.this, "Please enter a valid Github user Id", Toast.LENGTH_LONG).show();
                return;
            }
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            progressBar_loadImage.setVisibility(View.VISIBLE);
            progressBar_repoList.setVisibility(View.VISIBLE);

            mainActivityViewModel.fetchUserDetails(userId);
            mainActivityViewModel.fetchUserRepos(userId);
        });
    }

    private void initUI() {
        searchBtn = findViewById(R.id.searchBtn);
        useridSearchET = findViewById(R.id.useridSearchET);
        userImage = findViewById(R.id.userImgView);
        progressBar_loadImage = findViewById(R.id.progressbar_loadImage);
        progressBar_repoList = findViewById(R.id.progressbar_repoList);
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.reposListView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        repoAdapter = new RepoAdapter(new UserRepos(), clickListener);
        recyclerView.setAdapter(repoAdapter);
    }

    private RecyclerItemClickListener clickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(UserReposItem reposItem) {
            Bundle bundle = new Bundle();
            bundle.putString("last_updated", reposItem.component4());
            bundle.putString("stars", String.valueOf(reposItem.getStargazers_count()));
            bundle.putString("forks", String.valueOf(reposItem.getForks_count()));
            RepoBottomSheetDialog bottomSheetDialog = new RepoBottomSheetDialog();
            bottomSheetDialog.show(getSupportFragmentManager(), "RepoBottomSheet");
            bottomSheetDialog.setArguments(bundle);
        }
    };
}