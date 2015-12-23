package tk.sbarjola.pa.featherlyricsapp.Discografia;

import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

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
    private String artist = "Iron Maiden";

    // Variables y Adapters
    private servicioDiscografiaRetrofit servicioDiscografia;   // Interfaz para las noticias
    private ArrayList<Item> items;
    private GridView gridDiscos;                        // Grid View donde mostraremos los discos
    private ListView listCanciones;                     // List View donde mostraremos los discos
    DiscografiaAdapter myGridAdapter;                   // Adaptador para el gridView
    private ArrayAdapter<String> myListAdapter;

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

        listCanciones = (ListView) view.findViewById(R.id.discografia_listCanciones);
        myListAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1);
        listCanciones.setAdapter(myListAdapter);


        gridDiscos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                gridDiscos.setVisibility(View.GONE);
                listCanciones.setVisibility(View.VISIBLE);

                List<List<Disc>> disco = items.get(position).getDiscs();

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(items.get(position).getDesc() + " - " + artist + " - " + items.get(position).getPublished());

                for (int iterador1 = 0; iterador1 < disco.size(); iterador1++) {
                    for (int iterador2 = 0; iterador2 < disco.get(iterador1).size(); iterador2++) {
                        myListAdapter.add(disco.get(iterador1).get(iterador2).getDesc());
                    }
                }
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

        artist = artist.toLowerCase();  // la busqueda del usuario la pasamos a minusculas
        artist = artist.replace(" ", "-");  // y cambiamos los espacios por guiones

        URL = BaseURL + artist + endURL;    // Y construimos la URL

        servicioDiscografia = retrofit.create(servicioDiscografiaRetrofit.class);

        Call<ListDiscografia> llamada = (Call<ListDiscografia>) servicioDiscografia.discografia(URL);

        llamada.enqueue(new Callback<ListDiscografia>() {
            @Override
            public void onResponse(Response<ListDiscografia> response, Retrofit retrofit) {
                ListDiscografia resultado = response.body();

                Discography discografia = resultado.getDiscography();

                myGridAdapter.clear();
                artist = resultado.getDiscography().getArtist().getDesc();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(artist);

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
        Call<ListDiscografia> discografia(@Url String url); // Le pasamos la URL entera ya construida
    }

    class DescargarDiscografia extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            descargarDiscografia();
            return null;
        }
    }
}
