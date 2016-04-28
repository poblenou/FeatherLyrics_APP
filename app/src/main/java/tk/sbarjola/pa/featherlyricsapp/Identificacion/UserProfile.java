package tk.sbarjola.pa.featherlyricsapp.Identificacion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import tk.sbarjola.pa.featherlyricsapp.Firebase.Artista;
import tk.sbarjola.pa.featherlyricsapp.Firebase.FirebaseConfig;
import tk.sbarjola.pa.featherlyricsapp.Firebase.Usuario;
import tk.sbarjola.pa.featherlyricsapp.MainActivity;
import tk.sbarjola.pa.featherlyricsapp.R;

/**
 * Created by 46465442z on 27/04/16.
 */
public class UserProfile extends Fragment {

    FirebaseConfig config;                                      // Configuración de firebase
    private Firebase referenciaListaUsuarios;                   // Apunta a la lista de usuarios
    Usuario userToShow;
    String userUID = "";
    ImageView imageUser;
    TextView userName;
    TextView userDescription;
    GridView historial;

    // Adapter para la lista de artistas
    private userArtistsAdapter myGridAdapter;
    List<String> items = new ArrayList<String>();
    Set<String> collectionArtistas = new HashSet<String>();

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);    //Definimos el fragment

        config = (FirebaseConfig) getActivity().getApplication();

        imageUser = (ImageView) view.findViewById(R.id.userProfile_userPicture);
        userName = (TextView) view.findViewById(R.id.userProfile_userName);
        userDescription = (TextView) view.findViewById(R.id.userProfile_userInfo);
        historial = (GridView) view.findViewById(R.id.userProfile_listHistory);

        referenciaListaUsuarios = config.getReferenciaListaUsuarios();

        // Seccion del grid y los albumes
        myGridAdapter = new userArtistsAdapter(container.getContext(), 0, items);  // Definimos nuestro adaptador

        // Primero extraemos el del main activity
        userUID = ((MainActivity) getActivity()).getOpenedProfile();

        // Si no se ha buscado ninguno, el nuestro por defecto
        if(userUID.equals("no profile") || userUID.equals("") || userUID == null){
            userUID = config.getUserUID();
        }

        // Descargamos la lista de usuarios
        referenciaListaUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = userSnapshot.getValue(Usuario.class);

                    if (usuario.getUID().equals(userUID)) {

                        userToShow = usuario;
                        userName.setText(usuario.getNombre() + " - (" + usuario.getEdad() + ")");
                        userDescription.setText(usuario.getDescripcion());

                        final Firebase referenciaMusicaContacto = new Firebase(config.getReferenciaListaUsuarios().toString() + "/" + usuario.getKey() + "/Artistas");

                        // Descargamos la lista de usuarios
                        referenciaMusicaContacto.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    Artista grupo = userSnapshot.getValue(Artista.class);
                                    collectionArtistas.add(grupo.getArtistas().toString());
                                    items.add(grupo.getArtistas().toString());
                                }

                                // Limpiamos los duplicados
                                Set<String> hs = new LinkedHashSet<>(items);
                                hs.addAll(items);
                                items.clear();
                                items.addAll(hs);

                                // Setteamos el adapter
                                historial.setAdapter(myGridAdapter);
                                setGridViewHeightBasedOnChildren(historial, 2);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {}
                        });
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        historial.setOnItemClickListener(new AdapterView.OnItemClickListener() { //Listener para el list

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Extraemos el artista del listView
                TextView textViewAuxiliar = (TextView) view.findViewById(R.id.user_artists_artistName);
                String text = textViewAuxiliar.getText().toString();

                // Cortamos el nombre de la pista y el artista
                String artist = text.split("-")[0];

                // Y lo mandamos al fragment de canciones
                ((MainActivity) getActivity()).setDiscographyStart(artist);
                ((MainActivity) getActivity()).abrirArtistas();

            }
        });

        return view;
    }

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columnas) {

        // Calculamos caunto hay que desplegar el GridView para poder mostrarlo todo dentro del ScrollView

        try {

            int alturaTotal = 0;
            int items = myGridAdapter.getCount();
            int filas = 0;

            View listItem = myGridAdapter.getView(0, null, gridView);
            listItem.measure(0, 0);
            alturaTotal = listItem.getMeasuredHeight();

            float x = 1;

            if (items > columnas) {
                x = items / columnas;
                filas = (int) (x + 1);
                alturaTotal *= filas;
            }

            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = alturaTotal;
            gridView.setLayoutParams(params);

        } catch (IndexOutOfBoundsException e){}
    }
}
