package com.example.moviesapi.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.util.Log;

import com.example.moviesapi.Interfaces.ImovieApi;
import com.example.moviesapi.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GestorMovie {
    public static MovieDB datos;
    public static SQLiteDatabase db;


    public static ArrayList<Movie> misMovies;

    public static ArrayList<Movie> getMisMovies() {
        return misMovies;
    }

    public static void setMisMovies(ArrayList<Movie> misMovies) {
        GestorMovie.misMovies = misMovies;
    }

    public GestorMovie() {
        this.misMovies = new ArrayList<Movie>();
    }

    public String mostrarMovies() {
        db = datos.getWritableDatabase();
        try {
            if (db != null) {
                misMovies.clear();

                Cursor cursor = db.rawQuery("SELECT * FROM " + datos.getTmovie(), null);

                if (cursor.moveToFirst()) {
                    do {
                        misMovies.add(new Movie(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),cursor.getInt(3)));
                    } while (cursor.moveToNext());
                }
                db.close();
                return "";
            } else {
                db.close();
                return "Error al acceder a la base de datos";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String devolverMoviesByFilter(String titulo){
        db = datos.getWritableDatabase();
        try {
            if (db != null) {
                misMovies.clear();
                Cursor cursor = db.rawQuery("SELECT * FROM " + datos.getTmovie() + " WHERE titulo  LIKE '%"+ titulo+"%'", null);

                if (cursor.moveToFirst()) {
                    do {
                        misMovies.add(new Movie(cursor.getInt(0), cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
                    } while (cursor.moveToNext());
                }
                db.close();
                return "";
            } else {
                db.close();
                return "Error al acceder a la base de datos";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }


    public String addMovie(Movie movie) {
        db = datos.getWritableDatabase();
        if (db != null) {
            ContentValues registro = new ContentValues();

            registro.put("id", misMovies.size());
            registro.put("titulo", movie.getTitulo());
            registro.put("estreno", movie.getEstreno());
            registro.put("valoracion", movie.getValoracion());

            if (db.insert(datos.getTmovie(), null, registro) == -1) {
                db.close();
                return "Error al añadir dato";
            } else {
                Movie auxMovie = new Movie(misMovies.size(), movie.getTitulo(), movie.getEstreno(),movie.getValoracion());
                misMovies.add(auxMovie);
            }
        } else {
            db.close();
            return "Error al acceder a la base de datos";
        }

        return "";
    }

    public String addMovieUtil(ArrayList<Movie> movies) {
        for (Movie movie: movies ) {
            db = datos.getWritableDatabase();
            if (db != null) {
                ContentValues registro = new ContentValues();

                registro.put("id", misMovies.size());
                registro.put("titulo", movie.getTitulo());
                registro.put("estreno", movie.getEstreno());
                registro.put("valoracion", movie.getValoracion());

                if (db.insert(datos.getTmovie(), null, registro) == -1) {
                    db.close();
                    return "Error al añadir dato";
                } else {
                    Movie auxMovie = new Movie(misMovies.size(), movie.getTitulo(), movie.getEstreno(),movie.getValoracion());
                    misMovies.add(auxMovie);
                }
            } else {
                db.close();
                return "Error al acceder a la base de datos";
            }
        }

        return "";
    }

    public String eliminarMovie(int id) {
        db = datos.getWritableDatabase();

        if (db != null) {
            int movieBorrado = db.delete(datos.getTmovie(), "id=?", new String[]{String.valueOf(id)});
            if (movieBorrado != 1) {
                db.close();
                return "No ha sido posible eliminar el registro";
            } else {
                mostrarMovies();
                db.close();
                return "";
            }
        } else {
            db.close();
            return "Error al acceder a la base de datos";
        }
    }

    public void editMovie(Movie movie) {

        db = datos.getWritableDatabase();
        if (db != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("titulo", movie.getTitulo());
            contentValues.put("estreno", movie.getEstreno());
            contentValues.put("valoracion", movie.getValoracion());
            String id = String.valueOf(movie.getId() - 1);
            db.update(datos.getTmovie(), contentValues, "id=?", new String[]{id});
        } else {
            db.close();
        }

    }

    /***
     * Metodo que utilizo solo para modificar la valoración a la movie
     * @param movie
     */
    public void editMovieVal(Movie movie) {

        db = datos.getWritableDatabase();
        if (db != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("valoracion", movie.getValoracion());
            String id = String.valueOf(movie.getId());
            db.update(datos.getTmovie(), contentValues, "id=?", new String[]{id});
        } else {
            db.close();
        }

    }


    public void crearBd(Context context) {
        datos = new MovieDB(context);
        db = datos.getWritableDatabase();
    }

    public void cerrarBd() {
        db.close();
    }


    public void getData(){
        String sql = "http://www.omdbapi.com/?apikey=349ecbc2&t=Harry+Potter";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();

            JSONArray jsonArr = null;

            jsonArr = (JSONArray) new JSONArray(json);
            String mensaje = "";
            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                Log.d("SLIDA",jsonObject.optString("description"));
                mensaje += "DESCRIPCION "+i+" "+jsonObject.optString("description")+"\n";
            }
            //  sal.setText(mensaje);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Boolean RellenarDesdeApi(String q) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ImovieApi movieService = retrofit.create(ImovieApi.class);
        Call<List<Movie>> call = movieService.find(q);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> moviesList = response.body();
                if ( moviesList != null){
                    for (Movie mv: moviesList){
                        misMovies.add(mv);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                t.getMessage();
            }
        });

        return true;
    }

}
