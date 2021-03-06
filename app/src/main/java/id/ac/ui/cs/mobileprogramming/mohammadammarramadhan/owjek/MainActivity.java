package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.owride_status_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Create instance of your Fragment
        Fragment fragment = new MainFragment();
        //Add Fragment instance to your Activity
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}