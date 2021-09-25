package com.example.moviesapi.Utils;

import android.content.Context;

import com.example.moviesapi.Models.Movie;

import java.util.ArrayList;
import java.util.List;

public class movieUtil {
    public static List<Movie> getMovies (final Context context) {
        return new ArrayList<Movie>() {{
            add(new Movie("Harry Potter y la piedra filosofal",2001,1)) ;
            add(new Movie("Harry Potter y la cámara secreta",2002,1)) ;
            add(new Movie("Harry Potter y el prisionero de Azcaban",2004,1));
            add(new Movie("Harry Potter y la orden del Fenix",2005,1));
            add(new Movie("Harry Potter y el misterio del principe",2007,1));
            add(new Movie("Harry Potter y las reliquias de la muerte 1",2009,1));
            add(new Movie("Harry Potter y las reliquias de la muerte 2",2011,1));
        }} ;
    }
}
