package tk.sbarjola.pa.featherlyricsapp.Fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;
import tk.sbarjola.pa.featherlyricsapp.Noticias.News;
import tk.sbarjola.pa.featherlyricsapp.Noticias.NoticiasAdapter;
import tk.sbarjola.pa.featherlyricsapp.R;


public class Home extends Fragment{

    // Datos de la API
    private String BaseURL = "http://api.vagalume.com.br/";  //Principio de la URL que usará retrofit
    private String apiKey = "754f223018be007a45003e3b87877bac";     // Key de Vagalume. Máximo 100.000 peticiones /dia

    private List<Mu> resultadosLetras;
    public String artist;
    public String track;
    public String textoCancion;
    public String letraCancion;

    // Variables y Adapters
    private servicioLetrasRetrofit servicioLetras;   // Interfaz para las peliculas populares
    private ArrayList<News> items; ///ArrayList amb els items
    private ListView listaNoticias; //ListView on mostrarem els items
    NoticiasAdapter myListAdapter;  //Adaptador per al listView

    // Declaramos el retrofit como variable global para poder reutilizarlo si es necesario
    private Retrofit retrofit = new Retrofit.Builder()  //Retrofit
            .baseUrl(BaseURL)                           //Primera parte de la url
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, contenedor, false);

        ImageView featherIcon = (ImageView) view.findViewById(R.id.featherIcon);    //Asignme el id
        featherIcon.setImageResource(R.drawable.feather_icon);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){ //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.noticias_fragment_menu, menu);
    }

    public void setLetra(String artist, String track) {

        this.artist = artist;
        this.track = track;

        textoCancion = track + " - " + artist;       // Creamos el texto de la cancion

        Toast.makeText(getContext(), textoCancion, Toast.LENGTH_SHORT).show(); // Y lanzamos la toast

        Snackbar.make(this.getView().findViewById(R.id.content_frame), textoCancion, Snackbar.LENGTH_LONG).setAction("Action", null).show();

        DescargarLetras descargarLetras = new DescargarLetras();  // Instanciams nuestro asyncTask para descargar en segundo plano las noticias
        descargarLetras.execute();    // Y lo ejecutamos*/
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
                LyricsList resultado = response.body();
                resultadosLetras = resultado.getMus();
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        letraCancion = resultadosLetras.get(0).getText();
        TextView textCancion = (TextView) getView().findViewById(R.id.songName);
        textCancion.setText(letraCancion);
        textCancion.setSelected(true);    // Es necesario para hacer el texto scrollable
    }

    public interface servicioLetrasRetrofit{ //Interficie per a la llista de popular
        @GET("search.php")
        Call<LyricsList> letras(
                @Query("art") String artista,
                @Query("mus") String songName,
                @Query("apikey") String api
        );
    }
}