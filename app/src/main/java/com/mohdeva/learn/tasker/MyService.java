package com.mohdeva.learn.tasker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.logging.Handler;


public class MyService extends Service {
    double longitude;
    double latitude;
    GPSTracker gps;
    long i = 0;
    static int x = 1; //Seconds
    public static final long INTERVAL= x * 1000;//variable for execute services every x seconds
    private Handler mHandler= new Handler(); // run on another Thread to avoid crash
    private Timer mTimer=null; // timer handling


    public MyService() {}
    @Override public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "onCreate in Service was Called", Toast.LENGTH_SHORT).show();

        // cancel if service is  already existed
        if(mTimer!=null)
            mTimer.cancel();
        else
            mTimer=new Timer(); // recreate new timer
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(),0,INTERVAL);// schedule task

    }
    @Override public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }
    @Override public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();//cancel the timer
        Toast.makeText(this, "onDestroy in Service was Called", Toast.LENGTH_SHORT).show();
    }

    //Inner Class for Timer
    private class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // display toast at every interval
                    Toast.makeText(getApplicationContext(), "Notify "+i, Toast.LENGTH_SHORT).show();
                    i++;
                }
            });
        }
    }
}