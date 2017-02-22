package com.mohdeva.learn.tasker;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cont extends AppCompatActivity {
    ListView listView;
    TextView textView;
    EditText contName;
//    List<String> name = new ArrayList<String>();
//    List<String> contactt = new ArrayList<String>();
//    private int i=0;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    Cursor c;
    ArrayList contacts;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cont);
        listView = (ListView) findViewById(R.id.idList); //Method to Start the Service
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) { //Name of Method for Calling Message
            showContacts();
        } else { //TODO
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.READ_CONTACTS
            }, PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        contName = (EditText)findViewById(R.id.contactname);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(adapter);

    }


    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showContacts() {
        c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
//        List<String> contacts = new ArrayList<String>();
        contacts = new ArrayList();
        while (c.moveToNext()) {
            String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            name.add(contactName);
//            contactt.add(phNumber);
            contacts.add("Name: " + contactName + "\n" + "PhoneNo: " + phNumber);
//            Toast.makeText(this,"COntacts: "+contacts+"-"+name[i-1]+contact[i-1],Toast.LENGTH_SHORT).show();

        }
        c.close();
    }
}
