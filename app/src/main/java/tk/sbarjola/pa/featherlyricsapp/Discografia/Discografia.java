package tk.sbarjola.pa.featherlyricsapp.Discografia;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
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
import android.widget.Toast;

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
    private String BaseURL = "http://api.vagalume.com.br/";         //Principio de la URL que usará retrofit
    private final static String endURL = "/discografia/index.js";   // Ultima parte de la url
    private String URL = "";                                        // Parte del medio que será el artista en minusculas y los espacios cambiados por guiones
    private String artist = "Iron Maiden";                          // Nombre del artista

    // Variables y Adapters
    private servicioDiscografiaRetrofit servicioDiscografia;   // Interfaz para descargar la discografia
    private ArrayList<Item> items = new ArrayList<>();;        // ArrayList que llenaremos con los albumes
    private GridView gridDiscos;                               // Grid View donde mostraremos los discos
    private ListView listCanciones;                            // List View donde mostraremos las canciones
    private DiscografiaAdapter myGridAdapter;                  // Adaptador para el gridView
    private ArrayAdapter<String> myListAdapter;                // Adaptador para el listView (con uno predefinido nos sirve)

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
        descargarDiscografia.execute();                                          // Y lo ejecutamos
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discografia, container, false);

        // Asignamos el grid y el list a sus variables
        gridDiscos = (GridView) view.findViewById(R.id.discografia_gridDiscos);
        listCanciones = (ListView) view.findViewById(R.id.discografia_listCanciones);

        // Seccion del grid y los albumes

        myGridAdapter = new DiscografiaAdapter(container.getContext(), 0, items);  // Definimos nuestro adaptador
        gridDiscos.setAdapter(myGridAdapter);                                      // Y acoplamos el adaptador

        // Sección del list y las canciones

        myListAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1);   // Definimos nuestro adaptador
        listCanciones.setAdapter(myListAdapter);                                                            // Y acoplamos el adaptador


        gridDiscos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  // En caso de pulsar sobre un album

                gridDiscos.setVisibility(View.GONE);            // Ocultamos el grid
                listCanciones.setVisibility(View.VISIBLE);      // Mostramos el list

                List<List<Disc>> disco = items.get(position).getDiscs();    // Sacamos los discos del elemento que hayamos pulsado

                // Cambioamos el titulo de la toolbar
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(items.get(position).getDesc() + " - " + artist + " - " + items.get(position).getPublished());

                // Y cargamos las canciones del album correspondiente a nuestro listView
                for (int iterador1 = 0; iterador1 < disco.size(); iterador1++) {                        // El primer for recorre los cd's del album, ya que pueden ser varios
                    for (int iterador2 = 0; iterador2 < disco.get(iterador1).size(); iterador2++) {     // El segundo las canciones
                        myListAdapter.add(disco.get(iterador1).get(iterador2).getDesc());
                    }
                }
            }
        });

        listCanciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  // En caso de pulsar sobre un album

                // Extrae el artista y pista del MainActivity
                ((MainActivity) getActivity()).setSearchedArtist(artist);
                ((MainActivity) getActivity()).setSearchedTrack(listCanciones.getItemAtPosition(position).toString());
                ((MainActivity) getActivity()).abrirCanciones();
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
                DescargarDiscografia descargarDiscografia = new DescargarDiscografia();  // Instanciams nuestro asyncTask para descargar en segundo plano la discografia
                descargarDiscografia.execute();                                          // Y lo ejecutamos
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

        artist = artist.toLowerCase();      // la busqueda del usuario la pasamos a minusculas
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
