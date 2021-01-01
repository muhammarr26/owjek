package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.ui.main;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.R;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.OWRIDEActivity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainFragment extends Fragment {

    private boolean isConnected;
    private ConnectivityManager connMgr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        View owride_btn = view.findViewById(R.id.ow_ride);

        connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        isConnected = networkInfo != null && networkInfo.isConnected();
        getActivity().registerReceiver(connReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        owride_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected) return;
                if(!checkLocationPermission()) return;
                Intent intent = new Intent(getActivity(), OWRIDEActivity.class);
                getActivity().unregisterReceiver(connReceiver);
                startActivity(intent);
            }
        });

        return view;

    }

    BroadcastReceiver connReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                boolean currConnection = networkInfo != null && networkInfo.isConnected();
                if(isConnected != currConnection) {
                    isConnected = currConnection;
                    Snackbar.make(getActivity().findViewById(R.id.ow_ride),
                            String.format("Connection %s", isConnected ? "Established" : "Disconnected"),
                            Snackbar.LENGTH_LONG
                    ).show();
                }
            }
        };

    private boolean checkLocationPermission() {
        int coarseCheck = ContextCompat.checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION);
        if (coarseCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION}, 200);
            return false;
        } else {
            return true;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0) {

                    boolean accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (accepted) {
                        Snackbar.make(getActivity().findViewById(R.id.ow_ride), "Permission Granted, now you can use this service.", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(getActivity().findViewById(R.id.ow_ride), "Location permission is needed for driver to know your location.", Snackbar.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("Location permission is needed for driver to know your location.",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_COARSE_LOCATION}, 200);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }
}