package tk.sbarjola.pa.featherlyricsapp.Canciones;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import at.markushi.ui.CircleButton;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;
import tk.sbarjola.pa.featherlyricsapp.MainActivity;
import tk.sbarjola.pa.featherlyricsapp.R;

public class Canciones extends Fragment{

    // Datos de la API
    private String BaseURL = "http://api.vagalume.com.br/";         //Principio de la URL que usará retrofit
    private String apiKey = "754f223018be007a45003e3b87877bac";     // Key de Vagalume. Máximo 100.000 peticiones /dia
    private List<Mu> resultadosLetras = null;      // List con el resultado de las letras obtenidas

    // Variables del fragment
    String playingArtist = "no artist";            // Nombre del artista de la canción en reproducción
    String playingTrack = "no track";              // Nombre de la pista en reproducción
    String discographyArtist = "no artist";        // Nombre del artista seleccionado en discografia
    String discographyTrack = "no track";          // Nombre de la pista seleccionada en discografia

    // Artista y pista que vamos a mostrar
    String artist = "no artist";
    String track = "no track";

    String cancionMostrada = "reproduccion";       // Que canción estamos mostrando en este momento
    String letraCancion;                           // String en el que guardaremos la letra de la canción

    // Variables y Adapters
    private servicioLetrasRetrofit servicioLetras;  // Interfaz para las peliculas populares

    // Declaramos el retrofit como variable global para poder reutilizarlo si es necesario
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_canciones, contenedor, false);

        ProgressBar progress = (ProgressBar) view.findViewById(R.id.progressAnimation);   // Animacion de cargando
        CircleButton button = (CircleButton) view.findViewById(R.id.canciones_circleButton);   // Nuestro circle button

        extraerInfoMusica();
        progress.setVisibility(View.GONE);

        if(!discographyArtist.equals("no artist")){
            artist = discographyArtist;
            track = discographyTrack;
            cancionMostrada = "busqueda";
        }
        else{
            artist = playingArtist;
            track = playingTrack;
            cancionMostrada = "reproduccion";
        }

        // Que descargue letras
        DescargarLetras descargarLetras = new DescargarLetras();  // Instanciams nuestro asyncTask para descargar en segundo plano la letra
        descargarLetras.execute();                                // Y lo ejecutamos

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Extrae el artista y pista del MainActivity

                if (!playingArtist.equals("no artist") && !discographyArtist.equals("no artist")) {    // Comprueba si hay algún artista en reproducción

                    ProgressBar progress = (ProgressBar) getView().findViewById(R.id.progressAnimation);   // Animacion de cargando

                    if (cancionMostrada.equals("reproduccion") && !discographyArtist.equals("no artist") && !discographyTrack.equals("no track")) {
                        artist = discographyArtist;
                        track = discographyTrack;
                        cancionMostrada = "busqueda";
                        progress.setVisibility(View.VISIBLE);
                    } else if (cancionMostrada.equals("busqueda") && !playingTrack.equals("no track")) {
                        artist = playingArtist;
                        track = playingTrack;
                        cancionMostrada = "reproduccion";
                        progress.setVisibility(View.VISIBLE);
                    }

                    DescargarLetras descargarLetras = new DescargarLetras();  // Instanciams nuestro asyncTask para descargar en segundo plano la letra
                    descargarLetras.execute();                                // Y lo ejecutamos
                } else {
                    Toast.makeText(getContext(), "No se ha detectado ningun artista", Toast.LENGTH_SHORT).show(); // Mostramos un toast
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){ //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.dashboard, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                discographyTrack = query.split("-")[0];   // Cojemos la primer aparte de la busqueda que será la canción
                discographyArtist = query.split("-")[1];  // Y la segunda que será el grupo

                track = discographyTrack;
                artist = discographyArtist;

                // Descargamos la información de las canciones
                DescargarLetras descargarLetras = new DescargarLetras();  // Instanciams nuestro asyncTask para descargar en segundo plano la letra
                descargarLetras.execute();                                // Y lo ejecutamos

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        inflater.inflate(R.menu.noticias_fragment_menu, menu);
    }


    class DescargarLetras extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            descargarLetra();
            return null;
        }
    }

    public void descargarLetra(){

        servicioLetras = retrofit.create(servicioLetrasRetrofit.class);

        Call<LyricsList> llamada = (Call<LyricsList>) servicioLetras.letras(artist, track, apiKey);

        llamada.enqueue(new Callback<LyricsList>() {
            @Override
            public void onResponse(Response<LyricsList> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    LyricsList resultado = response.body();

                    resultadosLetras = resultado.getMus();

                    if (resultadosLetras.size() == 0) {   // Si no hemos conseguido nada mostramos que la letra no está disponible
                        letraCancion = "Letra no disponible";
                    } else {
                        letraCancion = resultadosLetras.get(0).getText();

                        // Ajustamos correctamente el nombre de pista y el del artista
                        track = resultadosLetras.get(0).getName();
                        artist = resultado.getArt().getName();

                        TextView textCancion = (TextView) getView().findViewById(R.id.canciones_letraCancion);
                        TextView tituloCancion = (TextView) getView().findViewById(R.id.canciones_instrucciones);
                        ProgressBar progress = (ProgressBar) getView().findViewById(R.id.progressAnimation);   // Animacion de cargando

                        progress.setVisibility(View.GONE);  // Una vez descargado todo ocultamos la animación
                        tituloCancion.setText(track + "\n" + artist);   // Asignamos el titulo de la canción y el grupo a su textView
                        textCancion.setText(letraCancion);  // Asigamos la letra de la canción a su textView

                        ScrollView scrollLetra = (ScrollView) getView().findViewById(R.id.canciones_scrollView);
                        scrollLetra.fullScroll(ScrollView.FOCUS_UP);    // Cada vez que pone el texto de una canción, mueve el scrollView al principio
                    }
                } else {
                    try {
                        Log.e(null, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public interface servicioLetrasRetrofit{ //Interficie para descargar las letras
        @GET("search.php")
        Call<LyricsList> letras(
                @Query("art") String artista,
                @Query("mus") String songName,
                @Query("apikey") String api
        );
    }

    public void setSong(String artist, String track) {

        // Les damos a las variables globales el valor de la que hemos recibido para pasarsela a retrofit
        this.playingArtist = artist;
        this.playingTrack = track;
        this.artist = artist;
        this.track = track;

        if(cancionMostrada.equals("reproduccion")) {

            TextView textCancion = (TextView) getView().findViewById(R.id.canciones_letraCancion);
            textCancion.setText("");
            ProgressBar progress = (ProgressBar) getView().findViewById(R.id.progressAnimation);   // Animacion de cargando
            progress.setVisibility(View.VISIBLE);

            // Descargamos la información de las canciones
            DescargarLetras descargarLetras = new DescargarLetras();  // Instanciams nuestro asyncTask para descargar en segundo plano la letra
            descargarLetras.execute();                                // Y lo ejecutamos
        }
    }

    public void extraerInfoMusica(){

        // Sacamos los datos de la cancion en reproduccion
        playingArtist = ((MainActivity)getActivity()).getPlayingArtist();
        playingTrack = ((MainActivity)getActivity()).getPlayingTrack();

        // Y de la cancion buscada
        discographyArtist = ((MainActivity)getActivity()).getDiscographyArtist();
        discographyTrack = ((MainActivity)getActivity()).getDiscographyTrack();

        ((MainActivity)getActivity()).setSearchedArtist("no artist");
        ((MainActivity)getActivity()).setSearchedTrack("no track");
    }
}