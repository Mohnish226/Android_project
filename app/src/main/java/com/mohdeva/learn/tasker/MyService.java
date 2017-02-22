package com.mohdeva.learn.tasker;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
public class MyService extends Service {
    //MediaPlayer myPlayer;
    int i=0;
    public MyService() {}
    @Override public IBinder onBind(Intent intent) { // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "onCreate in Service was Called", Toast.LENGTH_SHORT).show();
        //myPlayer = MediaPlayer.create(this, R.raw.arjun);
    }
    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        //myPlayer.start();
        while(i<600){
            Toast.makeText(this, "working"+i, Toast.LENGTH_SHORT).show();
            i++;
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override public void onDestroy() {
        super.onDestroy();
        //myPlayer.stop();
        Toast.makeText(this, "onDestroy in Service was Called", Toast.LENGTH_SHORT).show();
    }
}