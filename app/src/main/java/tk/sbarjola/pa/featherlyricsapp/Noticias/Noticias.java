package tk.sbarjola.pa.featherlyricsapp.Noticias;

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
import android.widget.ListView;
import java.util.ArrayList;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import tk.sbarjola.pa.featherlyricsapp.R;

public class Noticias extends Fragment{

    // Datos de la API
    private String BaseURL = "http://api.vagalume.com.br/";  //Principio de la URL que usará retrofit

    // Variables y Adapters
    private servicioNoticiasRetrofit servicioNoticias;   // Interfaz para las noticias
    private ArrayList<News> items;                       ///ArrayList amb els items
    private ListView listaNoticias;                      //ListView on mostrarem els items
    NoticiasAdapter myListAdapter;                       //Adaptador per al listView

    // Declaramos el retrofit como variable global para poder reutilizarlo si es necesario
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public void onStart() { //Cada vez que se abra el fragment que se descargen las noticias
        super.onStart();

        DescargarNoticias descargarNoticias = new DescargarNoticias();  // Instanciams nuestro asyncTask para descargar en segundo plano las noticias
        descargarNoticias.execute();    // Y lo ejecutamos
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
            public void onFailure(Throwable t) {
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View fragmentoLista = inflater.inflate(R.layout.fragment_noticias, container, false);    //Definimos el fragment

        items = new ArrayList<>();     //array list que contindrà les pel·licules
        listaNoticias = (ListView) fragmentoLista.findViewById(R.id.listaNoticias);    //Asignme el id
        myListAdapter = new NoticiasAdapter(container.getContext(), 0, items);  // Definim adaptador al layaout predefinit i al nostre array items
        listaNoticias.setAdapter(myListAdapter);    //Acoplem el adaptador

        listaNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //Listener para el list
            // De momento no hace nada
            }
        });

        //Afegim diverses entrades al ListView que apareixeran per defecte
        return fragmentoLista;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {

            items.clear();

            DescargarNoticias descargarNoticias = new DescargarNoticias();  // Instanciams nuestro asyncTask para descargar en segundo plano las noticias
            descargarNoticias.execute();    // Y lo ejecutamos
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){ //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.noticias_fragment_menu, menu);
    }

    public interface servicioNoticiasRetrofit{ //Interficie para descargar las noticias
        @GET("news/index.js")
        Call<NewsList> noticias();
    }

    class DescargarNoticias extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            descargarNoticias();
            return null;
        }
    }
}
