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

import java.util.Objects;

public class RepoBottomSheetDialog extends BottomSheetDialogFragment {

    private TextView tv_lastUpdated, tv_stars, tv_forks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_slide_dialog, container, false);
        tv_lastUpdated = v.findViewById(R.id.last_updated);
        tv_stars = v.findViewById(R.id.tv_stars);
        tv_forks =v.findViewById(R.id.tv_forks);
        String lastUpdated = getArguments().getString("last_updated");
        String stars = getArguments().getString("stars");
        String folks = getArguments().getString("forks");
        tv_lastUpdated.setText(lastUpdated);
        tv_stars.setText(stars);
        tv_forks.setText(folks);
        return v;
    }


}
