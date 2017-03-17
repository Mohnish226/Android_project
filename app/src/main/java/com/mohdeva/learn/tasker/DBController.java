package com.mohdeva.learn.tasker;

/**
 * Created by Mohnish_Devadiga on 17/03/17.
 */
import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper{
    private static final String LOGCAT = null;

    public DBController(Context applicationcontext) {
        super(applicationcontext, "androidsqlite.db", null, 1);
        Log.d(LOGCAT,"Created");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE tasks ( taskId INTEGER PRIMARY KEY, taskName TEXT)";
        database.execSQL(query);
        Log.d(LOGCAT,"tasks Created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS tasks";
        database.execSQL(query);
        onCreate(database);
    }

    public void insertTask(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("taskName", queryValues.get("taskName"));
        database.insert("tasks", null, values);
        database.close();
    }

    public int updateTask(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("taskName", queryValues.get("taskName"));
        return database.update("tasks", values, "taskId" + " = ?", new String[] { queryValues.get("taskId") });
        //String updateQuery = "Update  words set txtWord='"+word+"' where txtWord='"+ oldWord +"'";
        //Log.d(LOGCAT,updateQuery);
        //database.rawQuery(updateQuery, null);
        //return database.update("words", values, "txtWord  = ?", new String[] { word });
    }

    public void deleteTask(String id) {
        Log.d(LOGCAT,"delete");
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM  tasks where taskName= '"+ id +"'";
        Log.d("query",deleteQuery);
        database.execSQL(deleteQuery);
    }

    public void getId(String taskName){
        Log.d(LOGCAT,"id");
        SQLiteDatabase database = this.getWritableDatabase();
        String idQuery = "Select taskId where taskName='"+taskName+"'";
        Log.d("query",idQuery);
        database.execSQL(idQuery);
    }

    public ArrayList<HashMap<String, String>> getAllTasks() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM tasks";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("taskId", cursor.getString(0));
                map.put("taskName", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        // return contact list
        return wordList;
    }

    public HashMap<String, String> getTaskInfo(String id) {
        HashMap<String, String> wordList = new HashMap<String, String>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM tasks where taskId='"+id+"'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                //HashMap<String, String> map = new HashMap<String, String>();
                wordList.put("taskName", cursor.getString(1));
                //wordList.add(map);
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}
