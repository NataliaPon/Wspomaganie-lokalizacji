package com.example.asus.lokalizacja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Obsluga lokalnej bazy danych SQLite - juz nie uzywane
 */

public class dbConnection {

    static final String dbName = "bazaBLE.db";
    static final int dbWer = 1;
    public static SQLiteDatabase db;
    private final Context context;
    private static dbHelper dbHelp;

    public  dbConnection(Context con)
    {
        context = con;
        dbHelp = new dbHelper(context, dbName, null, dbWer);
    }
    public  dbConnection open() throws SQLException
    {
        db = dbHelp.getWritableDatabase();        return this;
    }

    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public String insertRecord(String name, String addr, String local)
    {
        try{
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("name", name);
            newValues.put("addres", addr);
            newValues.put("localization", local);

            db = dbHelp.getWritableDatabase();
            long result=db.insert("urzadzenia", null, newValues);
            System.out.print(result);

        }catch (Exception e){
            System.out.print("Error insert record: "+e);
        }
        return "ok";
    }

    public void deleteRecord(String addr)
    {
        String where="addres=?";
        db.delete("urzadzenia", where, new String[]{addr}) ;

    }

    public void updateRecord (String name, String addr, String local)
    {
        ContentValues updatedValues = new ContentValues();

        updatedValues.put("name", name);
        updatedValues.put("localization", local);

        String where="addres=?";
        db.update("urzadzenia",updatedValues, where, new String[]{addr}) ;
    }

    public Cursor getRecord(String addr)
    {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        Cursor cursor =  database.rawQuery( "select * from urzadzenia where addres='"+addr+"'", null );
        return cursor;
    }

}
