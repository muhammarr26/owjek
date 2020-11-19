package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.R;

public class OWRIDEActivity extends AppCompatActivity {

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

    public void confirm(){
        OWRIDEStatusFragment status = new OWRIDEStatusFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, status);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }
}