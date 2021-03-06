package com.mohdeva.learn.tasker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.SEND_SMS;

public class Todo extends AppCompatActivity {
    private ImageButton btnSpeak;
    private EditText recSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int PERMISSION_REQUEST_CODE = 600;
    private RecyclerView lv;
    private Button btn;
    private int iterator = 0; //for tasks
    private int confirmFlag = 2;
    List<Tasks> tasksList = new ArrayList<>();
    Recycler_Adapter recycler_adapter;
    List<String> tasks_list;
    int taskid_for_edit=-1;

    DBController controller = new DBController(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent z = new Intent(Todo.this, ExpenseMgr.class);
                startActivity(z);
                return true;
            case R.id.item2:
                Intent i = new Intent(Todo.this, ChangePassword.class);
                startActivity(i);
                return true;
            case R.id.item3:
                Intent x = new Intent(Todo.this, Developers.class);
                startActivity(x);
                return true;
            case R.id.item4:
                Intent xz = new Intent(Todo.this, receiveActivity.class);
                startActivity(xz);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Default toolbe elevation
        getSupportActionBar().setElevation(0);

        recSpeak = (EditText) findViewById(R.id.TEXT);
        btnSpeak = (ImageButton) findViewById(R.id.Speech);
        recycler_adapter = new Recycler_Adapter(tasksList);
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        btn = (Button) findViewById(R.id.ADD);
        lv = (RecyclerView) findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        Log.d("debugMode", "The application stopped after this");
        lv.setLayoutManager(layoutManager);
        lv.setItemAnimator(new DefaultItemAnimator());
        lv.setAdapter(recycler_adapter);
        lv.addItemDecoration(new Recycler_Decoration(Todo.this, Recycler_Decoration.VERTICAL_LIST));

        checkPermission();
        requestPermission();
        ServiceStart();
        // Initializing a new String Array
        //to get from db
        ArrayList<HashMap<String, String>> taskList = controller.getAllTasks();

        String sum = "";
        for (HashMap<String, String> hash : taskList) {
            for (String current : hash.values()) {
                sum = sum + current + "<#>";
            }
        }
        //    \\d+ Regular Expression
        String[] tasks = new String[]{};
        if (!sum.isEmpty()) {
            tasks = sum.split("<#>\\d+<#>");
        }
        // Create a List from String Array elements
        tasks_list = new ArrayList<String>(Arrays.asList(tasks));
        for (int i = 0; i < tasks_list.size(); i++) {
            int tempid = controller.getId(tasks_list.get(i));
            String temp_type = " ", Type = " ";
            Cursor cursor = controller.gettype(tempid);
            int isdone = controller.getisdone(tempid);
            if (cursor.moveToNext()) {
                temp_type = cursor.getString(0);
            }
            if (temp_type.equals("location"))
                Type = "Location";
            else if (temp_type.equals("call_message"))
                Type = "Call/Message";
            else if (temp_type.equals("time_date"))
                Type = "Time Based";
            Tasks task = new Tasks(tasks_list.get(i), Type, tasks_list.get(i), isdone);
            tasksList.add(task);
        }
        recycler_adapter.notifyDataSetChanged();


//        // Create an ArrayAdapter from List
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, tasks_list);
//        // DataBind ListView with items from ArrayAdapter
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new Items to List
                String temp = recSpeak.getText().toString();
                if (temp != null && !temp.isEmpty()) {
//                    tasks_list.add(temp);
                    recSpeak.setText("");

                    recycler_adapter.notifyDataSetChanged();
                    //        Notifies the attached observers that the underlying
                    //        data has been changed and any View reflecting the
                    Intent selecttype = new Intent(Todo.this, Main.class);
                    selecttype.putExtra("Datacont", temp);
                    selecttype.putExtra("taskname", temp);
                    finishAffinity();
                    startActivity(selecttype);
                } else {
                    Toast.makeText(Todo.this,
                            "No Task !", Toast.LENGTH_LONG).show();
                }
                confirmFlag = 2;
            }
        });
        // React to user clicks on item
        // LongClick
//        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                //Confirm Delete
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Todo.this);
//                // Setting Dialog Title
//                alertDialog.setTitle("Confirm Delete...");
//                // Setting Dialog Message
//                alertDialog.setMessage("Are you sure you want delete this?");
//                // Setting Positive "Yes" Button
//                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int which) {
//                        // Write your code here to invoke YES event
//                        String type[]=new String[1];
//                        String item = tasks_list.get(position);
//                        int tid=controller.getId(item);
//                        Cursor cursor=controller.gettype(tid);
//                        if(cursor.moveToFirst())
//                        {
//                            type[0]= cursor.getString(0);
//                        }
////                        Toast.makeText(getApplicationContext()," "+cursor.getCount(),Toast.LENGTH_SHORT).show();
//                        controller.deleteTask(tid,type[0]);
//                        controller.deleteTask(tid,"tasks");
//                        tasks_list.remove(position);
//                        arrayAdapter.notifyDataSetChanged();
//                        Toast.makeText(Todo.this, "You Deleted : " + item, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                // Setting Negative "NO" Button
//                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //invoke NO event
//                        Toast.makeText(getApplicationContext(), "Okay ", Toast.LENGTH_SHORT).show();
//                        dialog.cancel();
//                    }
//                });
//                // Showing Alert Message
//                alertDialog.show();
//                return true;
//            }
//        });
//        short click
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                String strName =(String) arg0.getItemAtPosition(arg2);
//                int taskid=controller.getId(strName);
//                Cursor cursor=controller.gettype(taskid);
//                if(cursor.moveToFirst())
//                {
//                    String type=cursor.getString(0);
//                    if (type.equals("location"))
//                    {
//                        Intent x=new Intent(Todo.this,Saved_Location.class);
//                        x.putExtra("Datacont",strName);
//                        x.putExtra("taskid",taskid);
//                        startActivity(x);
//                    }
//                }
////                Intent i = new Intent(Todo.this,MainAfterSelected.class);
////                i.putExtra("Datacont", strName);
////                i.putExtra("taskid",taskid);
////                startActivity(i);
//            }
//        });

    }

    //SPEECH STARTS HERE
    // Showing google speech input dialog
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Your Command");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
        }
    }

    // Receiving speech input
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    recSpeak.setText(result.get(0));
                }
                break;
            }

        }
    }

    public void ServiceStart() {
        Intent j = new Intent(this, MyService.class);
        startService(j);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED&& result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, READ_CONTACTS, ACCESS_COARSE_LOCATION, INTERNET, SEND_SMS, CALL_PHONE,
                CAMERA}, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        //Toast.makeText(Todo.this, "Grant The Next Permission", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, READ_CONTACTS,ACCESS_COARSE_LOCATION,INTERNET,
                                                            SEND_SMS,CALL_PHONE,CAMERA},PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Todo.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Todo.this);
        // Setting Dialog Title
        alertDialog.setTitle("Exit");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to Exit?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke YES event
                finishAffinity();
                finish();
                System.exit(0);
            }
        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //invoke NO event
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    public class Recycler_Adapter extends RecyclerView.Adapter<com.mohdeva.learn.tasker.Todo.Recycler_Adapter.MyViewHolder> {

        private List<Tasks> tasklist;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView tname, ttype, markasdone, done;

            public MyViewHolder(View itemView) {
                super(itemView);
                done = (TextView) itemView.findViewById(R.id.done);
                tname = (TextView) itemView.findViewById(R.id.taskTitle);
                ttype = (TextView) itemView.findViewById(R.id.tasktype);
                markasdone = (TextView) itemView.findViewById(R.id.taskDate);
            }
        }

        public Recycler_Adapter(List<Tasks> taskList) {
            this.tasklist = taskList;
        }

        @Override
        public com.mohdeva.learn.tasker.Todo.Recycler_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_list_row, parent, false);
            return new com.mohdeva.learn.tasker.Todo.Recycler_Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(com.mohdeva.learn.tasker.Todo.Recycler_Adapter.MyViewHolder holder, final int position) {
            Tasks tasks = tasklist.get(position);
            if (tasks.getType().equals("Location")) {
                holder.done.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_location, 0, 0, 0);
            } else if (tasks.getType().equals("Time Based")) {
                holder.done.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_time, 0, 0, 0);
            } else {
                holder.done.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_call, 0, 0, 0);
            }

            if (tasks.getIsdone() == 0)
                holder.markasdone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_pending, 0, 0, 0);
            else
                holder.markasdone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_done, 0, 0, 0);
            holder.tname.setText(tasks.getName());
            holder.ttype.setText(tasks.getType());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    taskid_for_edit=position;
                    ViewDialog viewDialog=new ViewDialog();
                    viewDialog.showDialog(Todo.this);
                    return true;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String strName = tasks_list.get(position);
                    int taskid = controller.getId(strName);
                    Cursor cursor = controller.gettype(taskid);
                    if (cursor.moveToFirst()) {
                        String type = cursor.getString(0);
                        if (type.equals("location")) {
                            Intent x = new Intent(Todo.this, Saved_Location.class);
                            x.putExtra("Datacont", strName);
                            x.putExtra("taskid", taskid);
                            startActivity(x);
                        }
                    }
//                Intent i = new Intent(Todo.this,MainAfterSelected.class);
//                i.putExtra("Datacont", strName);
//                i.putExtra("taskid",taskid);
//                startActivity(i);

                }
            });
        }

        @Override
        public int getItemCount() {
            return tasklist.size();
        }
    }

    public class ViewDialog implements View.OnClickListener {

        Button b1, b2, b3,b4;
        Dialog dialog;

        public void showDialog(Activity activity) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog);
            b1 = (Button) dialog.findViewById(R.id.asdone);
            b2 = (Button) dialog.findViewById(R.id.delete_activity);
            b3 = (Button) dialog.findViewById(R.id.cancel);
            b4 = (Button) dialog.findViewById(R.id.send);
            b1.setOnClickListener(this);
            b2.setOnClickListener(this);
            b3.setOnClickListener(this);
            b4.setOnClickListener(this);
            dialog.show();

        }

        @Override
        public void onClick(View view) {
            if(view==b2)
            {
                String type[]=new String[1];
                String item = tasks_list.get(taskid_for_edit);
                int tid=controller.getId(item);
                Cursor cursor=controller.gettype(tid);
                if(cursor.moveToFirst())
                {
                    type[0]= cursor.getString(0);
                }
//                        Toast.makeText(getApplicationContext()," "+cursor.getCount(),Toast.LENGTH_SHORT).show();
                controller.deleteTask(tid,type[0]);
                controller.deleteTask(tid,"tasks");
                dialog.dismiss();
                tasksList.remove(taskid_for_edit);
                tasks_list.remove(taskid_for_edit);
                recycler_adapter.notifyDataSetChanged();
                Toast.makeText(Todo.this, "You Deleted : " + item, Toast.LENGTH_SHORT).show();
            }
            else if(view==b1) {
                String item = tasks_list.get(taskid_for_edit);
                int tid = controller.getId(item);
                Boolean flag = controller.updateisdone(tid);
                recycler_adapter.notifyDataSetChanged();
                dialog.dismiss();
                if (flag) {
                    Toast.makeText(Todo.this, "You Edited : " + item, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Todo.this, "An Error Occured", Toast.LENGTH_SHORT).show();
                }
                Intent refresh = new Intent(Todo.this, Todo.class);
                startActivity(refresh);//Start the same Activity
                finish();
                //For smooth transition
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
            else if(view==b3)
            {
                dialog.dismiss();
            }
            else if(view==b4)
            {
                //get task ID
                Intent shareTask = new Intent(Todo.this, sendActivity.class);
                startActivity(shareTask);

            }
        }
    }
}