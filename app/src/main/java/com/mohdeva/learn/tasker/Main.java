package com.mohdeva.learn.tasker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private android.support.v7.widget.AppCompatButton btnDatePicker, btnloc,btncont;
    private String nameString;
    private TextView int_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setTitle("Select an option");

        //Recieve data from intent
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
        this.setTitle(nameString);
        int_name= (TextView) findViewById(R.id.IntentName);
        int_name.setText("More Details About :\n"+nameString);

        btnDatePicker=(android.support.v7.widget.AppCompatButton)findViewById(R.id.btn_date);

        btnloc=(android.support.v7.widget.AppCompatButton)findViewById(R.id.btn_loc);
        btncont=(android.support.v7.widget.AppCompatButton)findViewById(R.id.btn_call);
        btnDatePicker.setOnClickListener(this);

        btncont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cont = new Intent(Main.this, Cont.class);
                cont.putExtra("DataCont", nameString);
                startActivity(cont);
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {
            Intent cont = new Intent(Main.this, DateAndTime.class);
            cont.putExtra("DataCont", nameString);
            startActivity(cont);
        }

    }
}
