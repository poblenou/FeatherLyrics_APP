package tk.sbarjola.pa.featherlyricsapp.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import tk.sbarjola.pa.featherlyricsapp.R;
import tk.sbarjola.pa.featherlyricsapp.provider.music.MusicColumns;

public class Home extends Fragment{

    MusicListAdapter myListAdapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    //Aixo fa que mostri el menu. Com n'hi han fragments no grafics cal especificar-ho
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, contenedor, false);

        listView = (ListView) view.findViewById(R.id.home_listCanciones);

        // Creamos y definimos el ListAdapter
        myListAdapter = new MusicListAdapter(
                getContext(),
                R.layout.fragment_home,
                null,
                new String[]{MusicColumns.TITLE, MusicColumns.BAND},
                new int[]{R.id.music_list_adapter_songName, R.id.music_list_adapter_artistName},
                0);

        listView.setAdapter(myListAdapter); //Acoplem el adaptador

       //  getLoaderManager().initLoader(0, null, this); // Creamos un loaderManager

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {   //Listener para el list
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){ //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.noticias_fragment_menu, menu);
    }
}