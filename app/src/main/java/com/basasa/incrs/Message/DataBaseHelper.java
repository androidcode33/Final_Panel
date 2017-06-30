package com.basasa.incrs.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.basasa.incrs.Recyclerview.Student_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by basasagerald on 4/5/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="MessageDB";
    private static final int DATABASE_VERSION = 1;
    private static final String STUDENT_TABLE = "message";
    private static final String RESPONSE = "responses";
    private static final String STU_TABLE = "CREATE TABLE IF NOT EXISTS "+STUDENT_TABLE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, messages TEXT,  Type TEXT CHECK(Type IN('O','C')) NOT NULL DEFAULT 'O', Sender TEXT CHECK(Sender IN('student','lecturer')) NOT NULL DEFAULT 'student')";
    private static final String RESPONSES = "CREATE TABLE IF NOT EXISTS "+RESPONSE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,MessageID INTEGER, Responses TEXT)";

    Context context;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(STU_TABLE);
        db.execSQL(RESPONSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESPONSE);
        // Create tables again
        onCreate(db);
    }
    public void fetch(){
        String query = "select messages from "+STUDENT_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            System.out.println("The message is :"+cursor.getString(0));
        }

    }
public void insertresponseIntoDB(int id, String response){

    // 1. get reference to writable DB
    SQLiteDatabase db2 = this.getWritableDatabase();

    // 2. create ContentValues to add key "column"/value
    ContentValues values = new ContentValues();
    values.put("MessageID", id);
    values.put("Responses", response);

    // 3. insert
    db2.insert(RESPONSE, null, values);
    // 4. close
    db2.close();


}
    /* Insert into database*/
    public void insertIntoDB(String messagesent, String  type, String sender){
        Log.d("insert", "before insert");

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("messages", messagesent);
        values.put("Type", type);
        values.put("Sender", sender);

        // 3. insert
        db.insert(STUDENT_TABLE, null, values);
        // 4. close
        db.close();
        Toast.makeText(context, "insert value", Toast.LENGTH_LONG);
        Log.i("insert into DB", "After insert");
    }
    /* Retrive  data from database */
    public List<Student_Model> getDataFromDB(){
        List<Student_Model> modelList = new ArrayList<Student_Model>();
        String query = "select messages from "+STUDENT_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Student_Model model = new Student_Model();
                model.setQuestion(cursor.getString(0));
                modelList.add(model);
            }while (cursor.moveToNext());
        }


        Log.d("message data", modelList.toString());


        return modelList;
    }


    /*delete a row from database*/

    public void deleteARow(String message){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(STUDENT_TABLE, "message" + " = ?", new String[] { message });
        db.close();
    }
}
