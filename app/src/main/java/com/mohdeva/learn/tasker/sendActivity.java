package com.mohdeva.learn.tasker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class sendActivity extends AppCompatActivity {

    TextView taskname, taskDetails;
    ImageView imageView;
    String Taskdetails = new String();
    String nameString=new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        //Data from prev Intent
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nameString= null;
            } else {
                nameString= extras.getString("Data");
            }
        } else {
            nameString= (String) savedInstanceState.getSerializable("Data");
        }

        taskname = (TextView)findViewById(R.id.taskName);
        taskDetails = (TextView) findViewById(R.id.taskDetails);
        imageView=(ImageView) findViewById(R.id.imageView);

        //Get Task Name
        String text2Qr = "Task Name Goes Here";
        StringBuilder toQr = new StringBuilder();
        toQr.append(text2Qr);
        //Get Database details and append to string with separartor -> ##
        int iterator;
        for(iterator=0;iterator<10;iterator++){
            toQr.append("##");
            toQr.append(String.valueOf(iterator));
        }

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(toQr.toString(), BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}
