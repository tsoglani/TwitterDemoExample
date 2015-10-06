package com.twitter.tsoglani.mytwitterapplication;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tsoglani on 6/10/2015.
 */
public class DB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "Twitts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_MESSAGE = "message";
    private HashMap hp;
private Activity context;
    public DB(Activity context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + CONTACTS_TABLE_NAME +" (" +
                        CONTACTS_COLUMN_ID + " integer primary key, " +
                        CONTACTS_COLUMN_NAME + " text, " +
                        CONTACTS_COLUMN_MESSAGE + " text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);

    }

    public boolean save(String name, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put(CONTACTS_COLUMN_MESSAGE, message);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);

        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+CONTACTS_COLUMN_ID+" where id=" + id + "", null);

        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);

        return numRows;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

      return db.delete(CONTACTS_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});

    }

    public ArrayList<String> getMessagesAndNames() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CONTACTS_TABLE_NAME, null);
        res.moveToFirst();


        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME))+"@ @ @ @" + res.getString(res.getColumnIndex(CONTACTS_COLUMN_MESSAGE))+"@ @ @ @"+res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            res.moveToNext();

         }
        return array_list;
    }

    public int getID(String name, String message) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CONTACTS_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
         String nm=   res.getString( res.getColumnIndex(CONTACTS_COLUMN_NAME));
           String msg= res.getString(res.getColumnIndex(CONTACTS_COLUMN_MESSAGE));
            if(nm.equalsIgnoreCase("name")&&message.equalsIgnoreCase(msg)){
                return res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID));
            }
            res.moveToNext();

            }
        return -1;
    }


}
