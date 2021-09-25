package com.example.moviesapi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapi.Models.Movie;
import com.example.moviesapi.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PrimitiveIterator;

public class AddMovieActivity extends AppCompatActivity {

    private TextView tvAccion;
    private EditText txtTitulo, txtEstreno, txtValoracion;
    private Button btnAdd;
    private Movie movieActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        tvAccion = (TextView) findViewById(R.id.textAccion);
        txtTitulo = (EditText) findViewById(R.id.ed_titulo);
        txtEstreno = (EditText) findViewById(R.id.ed_estreno);
        txtValoracion = (EditText) findViewById(R.id.ed_Valoracion);
        btnAdd = (Button) findViewById(R.id.btn_addMv);
        if (getIntent().getExtras() != null){
            Bundle bundle_mv = getIntent().getExtras();
            movieActual = (Movie) bundle_mv.get("movie");
            rellenarDatos();
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Movie auxMovie = comprobarDatos();
                if (auxMovie == null){
                    clearMovie();
                    return;
                }
                if (movieActual == null){
                    MainActivity.gestorMovie.addMovie(auxMovie);
                } else {
                    MainActivity.gestorMovie.editMovie(auxMovie);
                }
                clearMovie();
                Intent intentAction = new Intent(view.getContext(), MainActivity.class);
                startActivity(intentAction);
            }
        });
    }

    public void rellenarDatos(){
        txtTitulo.setText(movieActual.getTitulo());
        txtEstreno.setText( String.valueOf(movieActual.getEstreno()));
        txtValoracion.setText( String.valueOf(movieActual.getValoracion()));
        tvAccion.setText("Editar Pelicula");
        btnAdd.setText("Editar");

    }

    public void clearMovie(){
        txtTitulo.setText("");
        txtEstreno.setText("");
        txtValoracion.setText("");
    }

    public Movie comprobarDatos(){
        String t = txtTitulo.getText().toString();
        String e = txtEstreno.getText().toString();
        String v = txtValoracion.getText().toString();



        if (t.isEmpty() || e.isEmpty() || v.isEmpty()){
            Toast.makeText(this, "Campo vacio", Toast.LENGTH_LONG).show();
            txtTitulo.requestFocus();
            return null;
        }

        int es = Integer.valueOf(txtEstreno.getText().toString());
        int va = Integer.valueOf(txtValoracion.getText().toString());

        Date date = new Date();
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String currentYear = getYearFormat.format(date);
        System.out.println(currentYear);

        if (es > Integer.valueOf(currentYear)){

            Toast.makeText(this, "Estreno no valido", Toast.LENGTH_LONG).show();
            txtTitulo.requestFocus();
            return null;
        }

        if (va < 1 || va > 5){
            Toast.makeText(this, "La valoración tiene que estar entre 1 - 5", Toast.LENGTH_LONG).show();
            txtTitulo.requestFocus();
            return null;
        }

        int id = MainActivity.gestorMovie.misMovies.size();


        Movie aux = new Movie(id,t,es,va);
        if (MainActivity.gestorMovie.misMovies.contains(aux)){
            Toast.makeText(this, "Esta pelicula ya existe", Toast.LENGTH_LONG).show();
            return null;
        }
        return aux;

    }


}