package com.mohdeva.learn.tasker;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyService extends Service {
    //MediaPlayer myPlayer;
    double longitude;
    double latitude;
    GPSTracker gps;
    long i=0;
//    SSleep sleep;

    public MyService() {}
    @Override public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "onCreate in Service was Called", Toast.LENGTH_SHORT).show();
        //myPlayer = MediaPlayer.create(this, R.raw.arjun);
        // create class object
//        gps = new GPSTracker(MyService.this);
    }
    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        //myPlayer.start();

//        while(i<600000000) {
//            Toast.makeText(this, "working"+i, Toast.LENGTH_SHORT).show();
            // check if GPS enabled
//            gps = new GPSTracker(MyService.this);
//
//
//            if(gps.canGetLocation()){
//
//
//                longitude = gps.getLongitude();
//                latitude = gps .getLatitude();
//
//                Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//
//                gps.showSettingsAlert();
//            }
//            long j=0;
//            while(j<1600000000){
//                long k=0;
//                while(k<1600000000){
//                    k++;
//                }
//                j++;
//            }
//            if (i == 59999) {
//                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//                Date dateobj = new Date();
//                Toast.makeText(this, df.format(dateobj)+" "+i, Toast.LENGTH_SHORT).show();
//
//            }
//            if (i==599999999){
//                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//                Date dateobj = new Date();
//                Toast.makeText(this, df.format(dateobj)+" "+i, Toast.LENGTH_SHORT).show();
//            }
//            i++;
//        }


        return super.onStartCommand(intent, flags, startId);
    }
    @Override public void onDestroy() {
        super.onDestroy();
        //myPlayer.stop();
        Toast.makeText(this, "onDestroy in Service was Called", Toast.LENGTH_SHORT).show();
    }
}