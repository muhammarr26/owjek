package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Map;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.R;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model.service.OWRIDEService;

public class OWRIDEActivity extends AppCompatActivity {
    private OWRIDEService owrideService;
    private Handler handler = new Handler();
    boolean mBound = false;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            OWRIDEService.LocalBinder binder = (OWRIDEService.LocalBinder) service;
            owrideService = binder.getService();
            mBound = true;
            if (owrideService.isRunning()) handler.postDelayed(runnable, 1);
            Log.println(Log.INFO, "OWRIDE", "service bounded");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.println(Log.INFO, "OWRIDE", "service disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Create instance of your Fragment
        Fragment fragment = new OWRIDEFragment();
        //Add Fragment instance to your Activity
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, OWRIDEService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    public void confirm() {
        OWRIDEStatusFragment status = new OWRIDEStatusFragment();

        owrideService.start();
        handler.postDelayed(runnable, 1);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, status);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Map<String, Integer> result = owrideService.getProgress();
            ((ProgressBar) findViewById(R.id.progress)).setProgress(result.get("progress"));
            ((TextView) findViewById(R.id.estimated)).setText(
                    getString(R.string.estimated, result.get("estimated"))
            );
            handler.postDelayed(this, 1);
        }
    };

    @Override
    protected void onStop() {
        Log.println(Log.INFO, "OWRIDE", "unbind service");
        super.onStop();
        unbindService(connection);
        mBound = false;
        handler.removeCallbacks(runnable);

    }
}