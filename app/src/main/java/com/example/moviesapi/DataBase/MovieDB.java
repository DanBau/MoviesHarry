package com.example.moviesapi.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDB extends SQLiteOpenHelper {
    private static final int VERSION_BASEDATOS = 3;
    private String Tmovie = "Movie";

    private static final String NOMBRE_BASEDATOS = "movieDB.db";
    private static final String TABLA_MOVIE = "CREATE TABLE Movie(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT , estreno INTEGER,valoracion INTEGER)";

    public String getTmovie() {
        return Tmovie;
    }

    public void setTmovie(String tmovie) {
        Tmovie = tmovie;
    }

    /***
     * Inicializo todos los propiedades para que en el programa tenga menos intencion de error
     * @param contexto activity que se vaya a utilizar
     */
    public MovieDB(Context contexto) {
        super(contexto, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Movie");
        db.execSQL(TABLA_MOVIE);
    }
}
