package cct.github.droid.android_mvc_sample.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cct.github.droid.android_mvc_sample.model.postItemEntity;

/*

This class is currently a combination of a repository layer and
a database layer; I need to seperate this out in a later version
so that we can fully test and disconnect these 2 layers

 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Shared instance of this helper class
    private  static DatabaseHandler mInstance = null;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "testDb";

    private Context mCxt;

    public static DatabaseHandler getmInstance(Context ctx) {
        if (mInstance == null)
            mInstance = new DatabaseHandler(ctx.getApplicationContext());
        return mInstance;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_POSTITEM_TABLE = "CREATE TABLE POSTITEM (ID INTEGER PRIMARY KEY, USERID VARCHAR(56), TITLE  VARCHAR(256), BODY TEXT)";
        db.execSQL(CREATE_POSTITEM_TABLE);

        System.out.println("DB - Creating tables...");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 1) {
            try {
                String UPDATE_POSTITEM = "ALTER TABLE POSTITEM ADD COLUMN IMG BLOB";
                db.execSQL(UPDATE_POSTITEM);
            } catch (Exception e) {}
        }

    }

    /**
     * Operations for PostItem Object
     * */
    public List<postItemEntity> postItem_getAll() {

        List<postItemEntity> ret = new ArrayList<>();
        String selectQuery = "SELECT * FROM POSTITEM ORDER BY ID";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                postItemEntity e = new postItemEntity();
                e.id = cursor.getInt(cursor.getColumnIndex("ID"));
                e.userId = cursor.getInt(cursor.getColumnIndex("USERID"));
                e.title = cursor.getString(cursor.getColumnIndex("TITLE"));
                e.body = cursor.getString(cursor.getColumnIndex("BODY"));
                ret.add(e);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return ret;
    }
    public postItemEntity postItem_Get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query("POSTITEM", new String[]{"USERID", "TITLE", "BODY"}, "ID" + "=?",
                new String[]{Integer.toString(id)}, null, null, null, null);

        postItemEntity ret = null;
        if (cursor != null){
            if (cursor.moveToFirst()){
                ret.id = cursor.getInt(cursor.getColumnIndex("ID"));
                ret.userId = cursor.getInt(cursor.getColumnIndex("USERID"));
                ret.title = cursor.getString(cursor.getColumnIndex("TITLE"));
                ret.body = cursor.getString(cursor.getColumnIndex("BODY"));
            }
        }
        cursor.close();
        return ret;
    }
    public void postItem_Add(postItemEntity e) {

        if (!postItem_Exists(e)) {
            //does not exist, lets add it to the database
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            //populate our database entity
            values.put("ID", e.id);
            values.put("USERID", e.userId);
            values.put("TITLE", e.title);
            values.put("BODY", e.body);
            // Inserting Row
            db.insert("POSTITEM", null, values);
        } else {
            //already exists.. lets update the values in the database
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            //populate our database entity
            values.put("USERID", e.userId);
            values.put("TITLE", e.title);
            values.put("BODY", e.body);
            // Inserting Row
            db.insert("POSTITEM", null, values);
            db.update("POSTITEM", values, "ID = ?",
                    new String[]{Integer.toString(e.id)});
        }

    }
    public void postItem_Remove(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("POSTITEM", "ID = ?",
                new String[]{Integer.toString(id)});
    }
    private Boolean postItem_Exists(postItemEntity e) {

        int cnt = 0;
        String countQuery = "SELECT * FROM POSTITEM WHERE USERID = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, new String[]{Integer.toString(e.userId)});
        cnt = cursor.getCount();
        cursor.close();

        if (cnt > 0){
            return true;
        } else {
            return false;
        }
    }
    public void postItem_ClearAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("POSTITEM", null, null);
    }



    private boolean doesTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}