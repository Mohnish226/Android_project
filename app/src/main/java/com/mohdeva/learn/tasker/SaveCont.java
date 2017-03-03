package com.mohdeva.learn.tasker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SaveCont extends AppCompatActivity {

    private String nameString;
    private String Name, Cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loc);
        //Recieve data from intent
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nameString= null;
            } else {
                nameString= extras.getString("Data");
            }
        }
        else {
            nameString= (String) savedInstanceState.getSerializable("Data");
        }

        //Splitting name and Number
        String[] data = nameString.split("::");
        Name = data[0];
        Cont = data[1];
        Toast.makeText(SaveCont.this,Name +" $$ "+Cont, Toast.LENGTH_SHORT).show();
    }

}
