package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.R;

public class OWRIDEFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.owride_fragment, container, false);

        View confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OWRIDEActivity activity = (OWRIDEActivity) getActivity();
                activity.confirm();
            }
        });

        return view;
    }

}