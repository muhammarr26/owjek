package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.R;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.OWRIDEActivity;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model.OWRIDEModel;
import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model.OWRIDERepository;

// https://developer.android.com/guide/components/bound-services
public class OWRIDEService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private OWRIDERepository owrideRepository;
    private CountDownTimer t;
    private long[] timer = {15000, 20000}; // pickup, transport
    private long wait = 3000;

    private boolean running = false;
    private int progress = 0;
    private int state = 0;
    private long estimated = 0;

    public OWRIDEService() {
    }

    public void start() {
        if (!isRunning()){
            t = new CountDownTimer(wait, wait) {
                public void onTick(long millisUntilFinished) {}
                public void onFinish() {
                    startTravel();
                }
            }.start();
            this.running = true;
        }
    }

    public void stop() {
        this.state = 0;
        this.running = false;
    }

    public void cancel() {
        owrideRepository.updateStatusOnLatestHistory(OWRIDEModel.CANCELLED);
        t.cancel();
        stop();
    }

    public boolean isRunning() {
        return this.running;
    }

    public Map<String, Integer> getProgress() {
        Map<String, Integer> result = new HashMap<>();
        result.put("progress", progress);
        result.put("estimated", (int) TimeUnit.MILLISECONDS.toSeconds(estimated));
        return result;
    }

    public class LocalBinder extends Binder {
        public OWRIDEService getService() {
            owrideRepository = new OWRIDERepository(getApplication());
            return OWRIDEService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /////////////////////////////////////////////////////////////////

    private void startTravel(){
        t = new CountDownTimer(timer[state], 5) {
            // Update estimasi waktu dan progress dalam persen
            public void onTick(long millisUntilFinished) { tick(millisUntilFinished); }
            public void onFinish() { finish(); }

        }.start();
    }

    private void tick(long millisUntilFinished){
        estimated = millisUntilFinished;
        progress = 100 - (int) (millisUntilFinished*100 / timer[state]);
        Log.i("Progress", String.format("State: %d, Progress: %d",state,progress));
    }

    private void finish(){
        state++;
        owrideRepository.updateStatusOnLatestHistory(state);
        switch(state){
            // Driver sampai ke lokasi pengguna
            case 1:
                sendNotification((String) getText(R.string.driver_pickup));
                // Tunggu selama 3 detik sebelum drivernya jalan ke tempat tujuan
                t = new CountDownTimer(wait, wait) {
                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {
                        startTravel();
                    }
                }.start();
                break;

            // Driver mengangkut penumpang sampai tujuan
            case 2:
                sendNotification((String) getText(R.string.arrive_at_location));
                stop();
                break;
        }
    }

    private void sendNotification(String message){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, OWRIDEActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "owride")
                .setSmallIcon(R.drawable.logo_color)
                .setContentTitle("OWRIDE")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setVibrate(new long[0])
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }
}
