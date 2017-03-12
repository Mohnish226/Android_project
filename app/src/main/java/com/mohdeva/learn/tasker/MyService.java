package com.mohdeva.learn.tasker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;
import android.os.Handler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.logging.Handler;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MyService extends Service {
    double longitude;
    double latitude;
    GPSTracker gps;
    long i = 0;
    static int x = 10; //Seconds
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
//        Toast.makeText(this, "onCreate in Service was Called", Toast.LENGTH_SHORT).show();

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
//        Toast.makeText(this, "onDestroy in Service was Called", Toast.LENGTH_SHORT).show();
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
                    if(i==4){
                        Intent intent = new Intent(MyService.this, ConfirmCall.class);
                        //get from db
                        String name="Mohnish";
                        String contact="+917045693777";
                        callNotification(name,contact,intent);
                    }
                    if(i==1){
                        //get from db
                        String name="Mohnish";
                        String contact="+917045693777";
                        String Sms="Hello World";
                        smsNotification(name,contact,Sms);
                    }
                }
            });
        }
    }

    private void callNotification(String Name,String Content,Intent intent) {
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.todoist_blue)
                        .setContentTitle("Call Reminder")
                        .setContentText("Call "+Name);

//        Intent notificationIntent = new Intent(this, ConfirmCall.class);
        StringBuilder str=new StringBuilder();
        str.append(Name);
        str.append("::");
        str.append(Content);
        String strName=str.toString();

        intent.putExtra("Data", strName);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        Vibrator V;
        V = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        V.vibrate(100);
        //notification sound
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        manager.notify(m, builder.build());
    }

    private void smsNotification(String Name,String contact,String SMS) {
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.todoist_blue)
                        .setContentTitle("SMS Reminder")
                        .setContentText("Send SMS to "+Name);

        StringBuilder str=new StringBuilder();
        str.append(Name);
        str.append("::");
        str.append(contact);
        str.append("::");
        str.append(SMS);
        String strName=str.toString();

        Intent intent2 = new Intent(MyService.this, ConfirmSMS.class);
        intent2.putExtra("Data", strName);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent2,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        Vibrator V;
        V = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        V.vibrate(100);
        //notification sound
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        manager.notify(m, builder.build());
    }

}