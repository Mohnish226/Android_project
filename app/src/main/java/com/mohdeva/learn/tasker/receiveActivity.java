package com.mohdeva.learn.tasker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class receiveActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    //View Objects
    private Button buttonScan;
    private TextView taskname, taskdetails;

    //qr code scanner object
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        //taskname = (TextView) findViewById(R.id.taskName);
        //taskdetails = (TextView) findViewById(R.id.taskDetails);
        buttonScan = (Button)findViewById(R.id.buttonScan);

    }

    public void QrScanner(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        String[] fromScanner=rawResult.getText().toString().split("##");
        Toast.makeText(receiveActivity.this, fromScanner[0]+"\n"+fromScanner[1], Toast.LENGTH_SHORT).show();
        //taskname.setText(fromScanner[0]);
        for(int iterator=0;iterator<fromScanner.length;iterator++){
            //taskdetails.append(fromScanner[iterator]);
        }

        //ADD Task Here
        Toast.makeText(receiveActivity.this, "TASK Added", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        this.finish();
        return;
    }

}
