package tk.sbarjola.pa.featherlyricsapp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
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
    private String BaseURL = "http://api.vagalume.com.br/";         //Principio de la URL que usará retrofit
    private String apiKey = "754f223018be007a45003e3b87877bac";     // Key de Vagalume. Máximo 100.000 peticiones /dia

    private List<Mu> resultadosLetras;      // List con el resultado de las letras obtenidas
    public String artist = "Marea";         // Nombre del artista
    public String track = "Perro Verde";    // Nombre de la pista

    public String letraCancion;

    // Variables y Adapters
    private servicioLetrasRetrofit servicioLetras;  // Interfaz para las peliculas populares
    private ArrayList<News> items;                  ///ArrayList amb els items
    private ListView listaNoticias;                 //ListView on mostrarem els items
    NoticiasAdapter myListAdapter;                  //Adaptador per al listView

    // Declaramos el retrofit como variable global para poder reutilizarlo si es necesario
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, contenedor, false);

        CircleButton button = (CircleButton) view.findViewById(R.id.circleButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargarInfo();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){ //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.noticias_fragment_menu, menu);
    }

    public void setSong(String artist, String track) {
        // Les damos a las variables globales el valor de la que hemos recibido para pasarsela a retrofit
        this.artist = artist;
        this.track = track;

        descargarInfo();
    }

    public void descargarInfo(){
        DescargarLetras descargarLetras = new DescargarLetras();  // Instanciams nuestro asyncTask para descargar en segundo plano la letra
        descargarLetras.execute();                                // Y lo ejecutamos
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
                if (response.isSuccess()){
                    LyricsList resultado = response.body();

                    resultadosLetras = resultado.getMus();

                    if(resultadosLetras.get(0) == null){
                        letraCancion = "Letra no disponible";
                    }
                    else{
                        letraCancion = resultadosLetras.get(0).getText();
                    }

                    TextView textCancion = (TextView) getView().findViewById(R.id.lyricsTextView);
                    TextView tituloCancion = (TextView) getView().findViewById(R.id.discografia_nombreArtista);

                    textCancion.setText(letraCancion);
                    textCancion.setSelected(true);    // Es necesario para hacer el texto scrollable


                    tituloCancion.setText(track + "\n" + artist);
                }
                else {
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
}