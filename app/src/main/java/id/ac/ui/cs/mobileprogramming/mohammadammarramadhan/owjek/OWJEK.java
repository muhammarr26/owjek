package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek;

import android.app.Application;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OWJEK extends Application {
    public static final ExecutorService asyncLog = Executors.newFixedThreadPool(1);
}