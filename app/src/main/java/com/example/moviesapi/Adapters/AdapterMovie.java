package com.example.moviesapi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.moviesapi.Models.Movie;
import com.example.moviesapi.R;

import java.util.ArrayList;

public class AdapterMovie extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Movie> movies;



    public AdapterMovie(Context context, int layout, ArrayList<Movie> movies) {
        this.context = context;
        this.layout = layout;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return this.movies.size();
    }

    @Override
    public Object getItem(int position) {
        return this.movies.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // Copiamos la vista que vamos a inflar
        View v = convertView;

        if (v == null) {
            // Usamos la clase LayoutInflater que se obtiene de un método de la misma clase pasándole u contexto
            // Inflamos la vista que nos hallegado con el layout personalizado
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            // Le indicamos el Layout que hemos creado antes
            v = layoutInflater.inflate(this.layout, null);
        }

        // Nos traemos el valor de la posición
        String titulo = movies.get(position).getTitulo();
        String estreno = String.valueOf(movies.get(position).getEstreno());
        String valoracion = String.valueOf(movies.get(position).getValoracion());

        // Nos falta rellenar el textView del Layout
        // aquí no tenemos el FindViewById, para referenciarlo tenemos que usar la vista que hemos creado
        TextView txtTitulo = (TextView) v.findViewById(R.id.mv_txtTitulo);
        txtTitulo.setText(titulo);
        TextView txtExtreno = (TextView) v.findViewById(R.id.mv_txtEstreno);
        txtExtreno.setText(estreno);
        TextView txtValoracion = (TextView) v.findViewById(R.id.mv_txtValoracion);
        txtValoracion.setText(valoracion);

        // Devolvemos la vista inflada y modificada para terminar
        return v;

    }
}
