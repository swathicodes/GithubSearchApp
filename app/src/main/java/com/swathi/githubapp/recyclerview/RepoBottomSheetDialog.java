package com.swathi.githubapp.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.swathi.githubapp.R;

public class RepoBottomSheetDialog extends BottomSheetDialogFragment {

    private TextView tvLastUpdated;
    private TextView tvStars;
    private TextView tvForks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_slide_dialog, container, false);
        tvLastUpdated = v.findViewById(R.id.lastUpdated);
        tvStars = v.findViewById(R.id.tvStars);
        tvForks =v.findViewById(R.id.tvForks);
            if (getArguments() != null){
            String lastUpdated = getArguments().getString("last_updated");
            String stars = getArguments().getString("stars");
            String folks = getArguments().getString("forks");
            tvLastUpdated.setText(lastUpdated);
            tvStars.setText(stars);
            tvForks.setText(folks);
        }


        return v;
    }


}
