package com.example.favmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.favmovies.modelo.Pelicula;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;




//import es.uniovi.eii.sdm.modelo.Pelicula;
//import es.uniovi.eii.sdm.util.Conexion;


public class ShowMovie extends AppCompatActivity {

    private Pelicula pelicula;

    CollapsingToolbarLayout toolBarLayout;
    ImageView imagenFondo;
    TextView categoria;
    TextView estreno;
    TextView duracion;
    TextView argumento;
    ImageView caratula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        //Recepción datos como activity secundaria
        Intent intentPeli= getIntent();
        pelicula= intentPeli.getParcelableExtra(MainRecyclerActivity.PELICULA_SELECCIONADA);

        // Gestión barra de la app
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        imagenFondo= (ImageView)findViewById(R.id.imagenFondo);

        // Gestión de los controles que contienen los datos de la película
        categoria= (TextView)findViewById(R.id.categoria);
        estreno= (TextView)findViewById(R.id.estreno);
        duracion= (TextView)findViewById(R.id.duracion);
        argumento= (TextView)findViewById(R.id.argumento);
        caratula= (ImageView)findViewById(R.id.caratula);

        if (pelicula!=null) //apertura en modo consulta
            mostrarDatos(pelicula);

        // Gestión del FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                verTrailer(pelicula.getUrlTrailer());
            }
        });
    }

    // Carga los datos que tenemos en la instancia en los componentes de la activity para mostrarlos
    public void mostrarDatos(Pelicula pelicula){
        if (!pelicula.getTitulo().isEmpty()) { //apertura en modo consulta
            //Actualizar componentes con valores de la pelicula específica
            String fecha= pelicula.getFecha();
            toolBarLayout.setTitle(pelicula.getTitulo()+" ("+fecha.substring(fecha.lastIndexOf('/') + 1)+")");
            // Imagen de fondo
            Picasso.get()
                    .load(pelicula.getUrlFondo()).into(imagenFondo);

            categoria.setText(pelicula.getCategoria().getNombre());
            estreno.setText(pelicula.getFecha());
            duracion.setText(pelicula.getDuracion());
            argumento.setText(pelicula.getArgumento());

            // Imagen de la carátula
            Picasso.get()
                    .load(pelicula.getUrlCaratula()).into(caratula);
        }
    }





    /**
     * Abre una activity con YouTube y muestra el vídeo indicado en el parámetro
     * @param urlTrailer url con el vídeo que se quiere visualizar
     */
    private void verTrailer(String urlTrailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlTrailer)));
    }


}