package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.R;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model.OWRIDEModel;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model.OWRIDEViewModel;

public class OWRIDEStatusFragment extends Fragment {

    private LiveData<OWRIDEModel> latestHistory;
    private Observer<OWRIDEModel> observer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.owride_status_fragment, container, false);

        OWRIDEOpenGLFragment fragment = new OWRIDEOpenGLFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.owride_opengl, fragment).commit();

        // Mengambil instansi OWRIDEViewModel dengan Factory pattern
        OWRIDEViewModel mOWRIDEViewModel = new ViewModelProvider(getActivity()).get(OWRIDEViewModel.class);
        latestHistory = mOWRIDEViewModel.getLatestHistory();

        observer = new Observer<OWRIDEModel>() {
            @Override
            public void onChanged(OWRIDEModel history) {
                ((TextView) view.findViewById(R.id.from)).setText(history.getFrom());
                ((TextView) view.findViewById(R.id.to)).setText(history.getTo());
                ((TextView) view.findViewById(R.id.status)).setText(convertStatus(history.getStatus()));
            }
        };
        latestHistory.observe(getActivity(), observer);

        // Pembatalan order
        View cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to cancel?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                OWRIDEActivity activity = (OWRIDEActivity) getActivity();
                                activity.cancel();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((OWRIDEActivity) getActivity()).fetchService();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        latestHistory.removeObserver(observer);
    }

    String convertStatus(int status){
        switch (status){
            case 0:
                return getString(R.string.status_owride_1);
            case 1:
                return getString(R.string.status_owride_2);
            case 2:
                return getString(R.string.status_owride_3);
        }
        return "Cancelled";
    }

}