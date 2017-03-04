package com.mohdeva.learn.tasker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button b1, b2, b3, b4;
    private StringBuilder curPattern = new StringBuilder(100);
    private String savPattern = new String();
    private String textFromFileString;
    private int countKnock = 0;
    private static final String TAG = MainActivity.class.getName();
    private static final String FILENAME = "pat.txt";
    private TextView err;
    private Intent i, j;
    Vibrator vibrator;
    //First Run ?
    final String PREFS_NAME = "TaskerFirstRun";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);



        //displays the content in full screen mode
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //int flag, int mask
        setContentView(R.layout.lock);

        //Check First Time Run
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("Tasker_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            Toast.makeText(this, "First Run Success !!!!", Toast.LENGTH_SHORT).show();

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("Tasker_first_time", false).commit();
        }

        err = (TextView) findViewById(R.id.error);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //setpassword to file as for testing
        String textToSaveString = "1234";

        writeToFile(textToSaveString);

        //get password from file
        textFromFileString = readFromFile();

        b1 = (Button) findViewById(R.id.one);
        b2 = (Button) findViewById(R.id.two);
        b3 = (Button) findViewById(R.id.three);
        b4 = (Button) findViewById(R.id.four);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        //starting service
        ServiceStart();


    }
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.one:
                {
                    curPattern.append("1");
                    countKnock++;
                    if (countKnock == 4) {
                        if (curPattern.toString().equals(textFromFileString)) {
                            Intent i = new Intent(MainActivity.this, Todo.class);
                            startActivity(i);
                            finish();

                        } else {
                            err.setText("Wrong Password");
                            countKnock = countKnock % 4;
                            curPattern.setLength(0);
                            vibrator.vibrate(400);
                        }
                    }
                    break;
                }
            case R.id.two:
                {
                    curPattern.append("2");
                    countKnock++;
                    if (countKnock == 4) {
                        if (curPattern.toString().equals(textFromFileString)) {
                            Intent i = new Intent(MainActivity.this, Todo.class);
                            startActivity(i);
                            finish();
                        } else {
                            err.setText("Wrong Password");
                            countKnock = countKnock % 4;
                            curPattern.setLength(0);
                            vibrator.vibrate(400);
                        }
                    }
                    break;
                }
            case R.id.three:
                {
                    curPattern.append("3");
                    countKnock++;
                    if (countKnock == 4) {
                        if (curPattern.toString().equals(textFromFileString)) {
                            Intent i = new Intent(MainActivity.this, Todo.class);
                            startActivity(i);
                            finish();
                        } else {
                            err.setText("Wrong Password");
                            countKnock = countKnock % 4;
                            curPattern.setLength(0);
                            vibrator.vibrate(400);
                        }
                    }
                    break;
                }
            case R.id.four:
                {
                    curPattern.append("4");
                    countKnock++;
                    if (countKnock == 4) {
                        if (curPattern.toString().equals(textFromFileString)) {
                            Intent i = new Intent(MainActivity.this, Todo.class);
                            startActivity(i);
                            finish();
                        } else {
                            err.setText("Wrong Password");
                            countKnock = countKnock % 4;
                            curPattern.setLength(0);
                            vibrator.vibrate(400);
                        }
                    }
                    break;
                }
        }
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }

    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput(FILENAME);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }


    //Starting Service
    //Method to Start the Service
    public void ServiceStart() {
        Toast.makeText(this, "Service Initiated", Toast.LENGTH_SHORT).show();
        Intent j = new Intent(this, MyService.class);
        startService(j);
    }
}
