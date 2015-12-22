package tk.sbarjola.pa.featherlyricsapp.Discografia;

import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import java.util.ArrayList;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Url;
import tk.sbarjola.pa.featherlyricsapp.MainActivity;
import tk.sbarjola.pa.featherlyricsapp.R;


public class Discografia extends Fragment {

    // Datos de la API
    private String BaseURL = "http://api.vagalume.com.br/";  //Principio de la URL que usará retrofit
    private final static String endURL = "/discografia/index.js";
    private String URL = "";
    private String artist = "iron-maiden";

    // Variables y Adapters
    private servicioDiscografiaRetrofit servicioDiscografia;   // Interfaz para las noticias
    private ArrayList<Item> items;
    private GridView gridDiscos;                      //ListView on mostrarem els items
    DiscografiaAdapter myGridAdapter;                       //Adaptador para el gridView

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

    @Override
    public void onStart() { //Cada vez que se abra el fragment que se descargen las noticias
        super.onStart();

        DescargarDiscografia descargarDiscografia = new DescargarDiscografia();  // Instanciams nuestro asyncTask para descargar en segundo plano las noticias
        descargarDiscografia.execute();    // Y lo ejecutamos
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discografia, container, false);

        items = new ArrayList<>();     //array list que contindrà les pel·licules
        gridDiscos = (GridView) view.findViewById(R.id.discografia_gridDiscos);    //Asignme el id
        myGridAdapter = new DiscografiaAdapter(container.getContext(), 0, items);  // Definim adaptador al layaout predefinit i al nostre array items
        gridDiscos.setAdapter(myGridAdapter);    //Acoplem el adaptador

        gridDiscos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //Listener para el list
                // De momento no hace nada
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.dashboard, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                artist = query;
                artist = artist.toLowerCase();
                artist = artist.replace(" ", "-");

                DescargarDiscografia descargarDiscografia = new DescargarDiscografia();  // Instanciams nuestro asyncTask para descargar en segundo plano las noticias
                descargarDiscografia.execute();    // Y lo ejecutamos
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });
    }

    public void descargarDiscografia(){

        URL = BaseURL + artist + endURL;

        servicioDiscografia = retrofit.create(servicioDiscografiaRetrofit.class);

        Call<ListDiscografia> llamada = (Call<ListDiscografia>) servicioDiscografia.discografia(URL);

        llamada.enqueue(new Callback<ListDiscografia>() {
            @Override
            public void onResponse(Response<ListDiscografia> response, Retrofit retrofit) {
                ListDiscografia resultado = response.body();

                Discography discografia = resultado.getDiscography();

                myGridAdapter.clear();

                TextView nombreArtista = (TextView) getView().findViewById(R.id.discografia_nombreArtista);
                nombreArtista.setText(resultado.getDiscography().getArtist().getDesc());

                for(int iterador = 0; iterador < discografia.getItem().size(); iterador++){
                    myGridAdapter.add(discografia.getItem().get(iterador));
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public interface servicioDiscografiaRetrofit{ //Interficie para descargar las discografia de un artista
        @GET
        Call<ListDiscografia> discografia(@Url String url);
    }

    class DescargarDiscografia extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            descargarDiscografia();
            return null;
        }
    }
}
