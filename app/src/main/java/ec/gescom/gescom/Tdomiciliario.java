package ec.gescom.gescom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Tdomiciliario extends SQLiteOpenHelper
{

    public Tdomiciliario(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Directorio(id integer primary key autoincrement, CARTERA text, CALIF_DE_RIESGO text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Directorio");
    }

    public void insert(String cartera, String calif_de_riesgo)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("CARTERA", cartera);
        contentValues.put("CALIF_DE_RIESGO", calif_de_riesgo);


        getWritableDatabase().insert("Directorio",null, contentValues);
    }

   public List<Object[]> getALL()
    {
            List<Object[]> listaTdomiciliario=new ArrayList<>();
        Cursor cursor=getReadableDatabase().rawQuery("select * from Directorio",null);
        while (cursor.moveToNext())
        {
            listaTdomiciliario.add(new Object[]{cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)});
        }

        return listaTdomiciliario;


    }

    public void elminarDatos(SQLiteDatabase db)
    {
        ///Cursor cursor=getReadableDatabase().rawQuery("delete from tdomiciliario",null);
        db.execSQL("delete from Directorio");

    }
}
