package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.OWRIDEActivity;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.R;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        View owride_btn = view.findViewById(R.id.ow_ride);
        owride_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OWRIDEActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }

}