package tk.sbarjola.pa.featherlyricsapp.Noticias;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;
import tk.sbarjola.pa.featherlyricsapp.R;

public class Noticias extends Fragment{

    // Datos de la API
    private String BaseURL = "http://api.vagalume.com.br/";  //Principio de la URL que usará retrofit
    private String apiKey = "754f223018be007a45003e3b87877bac";     // Key de Vagalume. Máximo 100.000 peticiones /dia

    // Variables y Adapters
    private servicioNoticiasRetrofit servicioNoticias;   // Interfaz para las peliculas populares
    private ArrayList<News> items; ///ArrayList amb els items
    private ListView listaNoticias; //ListView on mostrarem els items
    NoticiasAdapter myListAdapter;  //Adaptador per al listView

    // Declaramos el retrofit como variable global para poder reutilizarlo si es necesario
    private Retrofit retrofit = new Retrofit.Builder()  //Retrofit
            .baseUrl(BaseURL)                           //Primera parte de la url
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public void onStart() { //Cada vez que se abra el fragment que se descargen las noticias
        super.onStart();
        descargarNoticias();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    //Aixo fa que mostri el menu. Com n'hi han fragments no grafics cal especificar-ho
    }

    public void descargarNoticias(){

        servicioNoticias = retrofit.create(servicioNoticiasRetrofit.class);

        Call<NewsList> llamada = (Call<NewsList>) servicioNoticias.noticias();

        llamada.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Response<NewsList> response, Retrofit retrofit) {
                NewsList resultado = response.body();
                myListAdapter.clear();
                myListAdapter.addAll(resultado.getNews());
            }

            @Override
            public void onFailure(Throwable t) {}
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View fragmentoLista = inflater.inflate(R.layout.fragment_artistas, container, false);    //Definimos el fragment
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(container.getContext());   // necesario para referenciar y leer la configuración del programa

        items = new ArrayList<>();     //array list que contindrà les pel·licules
        listaNoticias = (ListView) fragmentoLista.findViewById(R.id.listaNoticias);    //Asignme el id
        myListAdapter = new NoticiasAdapter(container.getContext(), 0, items);  // Definim adaptador al layaout predefinit i al nostre array items
        listaNoticias.setAdapter(myListAdapter);    //Acoplem el adaptador

        listaNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //Listener para el list

            }
        });

        //Afegim diverses entrades al ListView que apareixeran per defecte
        return fragmentoLista;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){ //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.noticias_fragment_menu, menu);
    }

    public interface servicioNoticiasRetrofit{ //Interficie per a la llista de popular
        @GET("news/index.js")
        Call<NewsList> noticias();

    }
}
