package com.mohdeva.learn.tasker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Mohnish_Devadiga on 11/03/17.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context.getApplicationContext(), "This is my Toast message!", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(context, MyService.class);
        context.startService(myIntent);

    }


}