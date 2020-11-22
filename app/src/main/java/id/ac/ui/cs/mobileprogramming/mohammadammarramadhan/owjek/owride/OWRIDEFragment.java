package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.R;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model.OWRIDEModel;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model.OWRIDEViewModel;

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
                OWRIDEModel history = new OWRIDEModel(
                        ((EditText) activity.findViewById(R.id.from_input)).getText().toString(),
                        ((EditText) activity.findViewById(R.id.to_input)).getText().toString()
                );
                OWRIDEViewModel mOWRIDEViewModel = new ViewModelProvider(getActivity()).get(OWRIDEViewModel.class);
                mOWRIDEViewModel.insert(history);

                activity.confirm();
            }
        });

        return view;
    }

}