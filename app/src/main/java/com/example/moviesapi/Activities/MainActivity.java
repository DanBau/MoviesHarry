package com.example.moviesapi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.moviesapi.Adapters.AdapterMovie;
import com.example.moviesapi.DataBase.GestorMovie;
import com.example.moviesapi.Models.Movie;
import com.example.moviesapi.R;
import com.example.moviesapi.Utils.movieUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvMovies;
    public static AdapterMovie adapterMovie;
    public static GestorMovie gestorMovie = new GestorMovie();
    private ImageButton btnAdd, btnSearch;
    private EditText edSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMovies = (ListView) findViewById(R.id.ly_movie);
        gestorMovie.crearBd(this);
        adapterMovie = new AdapterMovie(this, R.layout.lymovie,(ArrayList<Movie>) gestorMovie.getMisMovies());
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        edSearch = (EditText) findViewById(R.id.edSearch);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);

        //gestorMovie.getData();
        //gestorMovie.RellenarDesdeApi("Harry+Potter");


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtBuscado = edSearch.getText().toString();
                gestorMovie.devolverMoviesByFilter(txtBuscado);
                adapterMovie.notifyDataSetChanged();
                edSearch.setText("");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAction = new Intent(view.getContext(), AddMovieActivity.class);
                startActivity(intentAction);
            }
        });
    }

    public void abrirWeb(String t){
        String url = "http://www.omdbapi.com/?apikey=349ecbc2&t=";
        Uri uri = Uri.parse(url+t);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

    }

    public void actualizarLv() {
        lvMovies.setAdapter(adapterMovie);
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abrirWeb(gestorMovie.misMovies.get(position).getTitulo());
            }
        });
        gestorMovie.mostrarMovies();

        if (gestorMovie.misMovies.size() == 0){
            gestorMovie.addMovieUtil( (ArrayList<Movie>) movieUtil.getMovies(this));
            gestorMovie.mostrarMovies();
        }
        adapterMovie.notifyDataSetChanged();
        registerForContextMenu(lvMovies);
        if (gestorMovie.misMovies.size() != 0) {
            //txtInfo.setText("");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        // Antes de inflarlo, añadimos un título al menú contextual
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(gestorMovie.misMovies.get(info.position).getTitulo());
        inflater.inflate(R.menu.context_movie_menu, menu);
    }

    // Manejamos eventos click del menú contextual
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete_item:
                // Boramos y notificamos los cambios
                // Borramos un elemento, el indicado porla posición
                gestorMovie.eliminarMovie(gestorMovie.misMovies.get(info.position).getId());
                actualizarLv();

                // Informamos al adaptador del cambio
                return true;
            case R.id.edit_item:
                Intent intentAction = new Intent(this, AddMovieActivity.class);
                intentAction.putExtra("movie", gestorMovie.misMovies.get(info.position));
                startActivity(intentAction);
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onResume() {
        super.onResume();
        gestorMovie.crearBd(this);
        actualizarLv();
    }

    public void onPause() {
        super.onPause();
        gestorMovie.cerrarBd();
    }
}