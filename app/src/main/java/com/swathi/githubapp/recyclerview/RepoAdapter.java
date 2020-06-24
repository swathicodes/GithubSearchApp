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
        holder.repoTitle.setText(currentRepo.getName());
        holder.repoDescription.setText(currentRepo.getDescription());

        holder.itemView.setOnClickListener(v -> recyclerItemClickListener.onItemClick(currentRepo));

    }

    @Override
    public int getItemCount() {
        return userRepos.size();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder{

        private TextView repoTitle, repoDescription;

        RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            repoTitle = itemView.findViewById(R.id.tvRepoTitle);
            repoDescription = itemView.findViewById(R.id.tvRepoDesc);
        }
    }

    public void updateList(UserRepos userRepos){
          this.userRepos = userRepos;
          notifyDataSetChanged();
    }
}
