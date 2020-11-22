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

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.MainActivity;
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

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //Create instance of your Fragment
            if(!owrideService.isRunning()) {
                OWRIDEFragment fragment = new OWRIDEFragment();
                fragmentTransaction.replace(R.id.frame_layout, fragment, "form");
                fragmentTransaction.addToBackStack(null);
            }else{
                OWRIDEStatusFragment fragment = new OWRIDEStatusFragment();
                fragmentTransaction.replace(R.id.frame_layout, fragment, "status");
                fragmentTransaction.addToBackStack(null);
            }
            //Add Fragment instance to your Activity
            fragmentTransaction.commit();

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mBound){
            Intent intent = new Intent(this, OWRIDEService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
            startService(intent);
        }
    }

    public void confirm() {
        OWRIDEStatusFragment status = new OWRIDEStatusFragment();

        owrideService.start();

        getSupportFragmentManager().popBackStack();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, status, "status");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void cancel() {
        handler.removeCallbacks(runnable);
        owrideService.cancel();

        getSupportFragmentManager().popBackStack();
        OWRIDEFragment form = new OWRIDEFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, form, "form");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void fetchService(){
        handler.postDelayed(runnable, 1);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("status");
            if(fragment != null && fragment.isVisible()) {
                Map<String, Integer> result = owrideService.getProgress();
                ((ProgressBar) findViewById(R.id.progress)).setProgress(result.get("progress"));
                ((TextView) findViewById(R.id.estimated)).setText(
                        getString(R.string.estimated, result.get("estimated"))
                );
                handler.postDelayed(this, 1);
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        Log.println(Log.INFO, "OWRIDE", "unbind service");
        super.onStop();
        unbindService(connection);
        mBound = false;
        handler.removeCallbacks(runnable);
    }
}