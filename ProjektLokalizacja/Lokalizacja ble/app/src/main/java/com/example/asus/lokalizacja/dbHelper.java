package com.example.asus.lokalizacja;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//Do SQLite - juz nie uzywane
public class dbHelper extends SQLiteOpenHelper {

    public dbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS urzadzenia(name VARCHAR,addres VARCHAR,localization VARCHAR);");
        }catch (Exception e)
        {
            System.out.print("Error create table: "+e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.print("Usuniecie starej tabeli wer. "+i+" i stworzenie nowej wer."+i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS urzadzenia;");
        onCreate(sqLiteDatabase);
    }

}

