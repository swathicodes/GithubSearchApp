package com.swathi.githubapp.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.swathi.githubapp.R;
import com.swathi.githubapp.models.UserRepos;
import com.swathi.githubapp.models.UserReposItem;


public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

      private UserRepos userRepos;
      private RecyclerItemClickListener recyclerItemClickListener;

      public RepoAdapter(UserRepos userRepos, RecyclerItemClickListener recyclerItemClickListener){
          this.userRepos = userRepos;
          this.recyclerItemClickListener = recyclerItemClickListener;
      }


    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item,parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {

        final UserReposItem currentRepo = userRepos.get(position);
        holder.repo_title.setText(currentRepo.getName());
        holder.repo_description.setText(currentRepo.getDescription());

        holder.itemView.setOnClickListener(v -> recyclerItemClickListener.onItemClick(currentRepo));

    }

    @Override
    public int getItemCount() {
        return userRepos.size();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder{

        private TextView repo_title, repo_description;

        RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            repo_title = itemView.findViewById(R.id.tv_repoTitle);
            repo_description = itemView.findViewById(R.id.tv_repoDesc);
        }
    }

    public void updateList(UserRepos userRepos){
          this.userRepos = userRepos;
          notifyDataSetChanged();
    }
}
