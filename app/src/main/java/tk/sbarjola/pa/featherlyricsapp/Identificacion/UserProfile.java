package tk.sbarjola.pa.featherlyricsapp.Identificacion;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.HashSet;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import tk.sbarjola.pa.featherlyricsapp.Firebase.Artista;
import tk.sbarjola.pa.featherlyricsapp.Firebase.FirebaseConfig;
import tk.sbarjola.pa.featherlyricsapp.Firebase.Usuario;
import tk.sbarjola.pa.featherlyricsapp.MainActivity;
import tk.sbarjola.pa.featherlyricsapp.R;

/**
 * Created by 46465442z on 27/04/16.
 */
public class UserProfile extends Fragment {

    FirebaseConfig config;                       // Configuración de firebase
    private Firebase referenciaListaUsuarios;    // Apunta a la lista de usuarios
    Usuario userToShow;
    String userUID = "";
    ImageView imageUser;
    TextView userName;
    TextView userDescription;
    ListView historial;

    // Adapter para la lista de artistas
    ArrayAdapter<String> arrayAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);    //Definimos el fragment

        config = (FirebaseConfig) getActivity().getApplication();

        imageUser = (ImageView) view.findViewById(R.id.userProfile_userPicture);
        userName = (TextView) view.findViewById(R.id.userProfile_userName);
        userDescription = (TextView) view.findViewById(R.id.userProfile_userInfo);
        historial = (ListView) view.findViewById(R.id.userProfile_listHistory);

        referenciaListaUsuarios = config.getReferenciaListaUsuarios();

        arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.profile_user_history_adapter,R.id.profileUser_history_adapter_songName);

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
                                    arrayAdapter.add(grupo.getArtistas().toString());
                                }

                                historial.setAdapter(arrayAdapter); //Acoplem el adaptador
                                setListViewHeightBasedOnChildren(historial);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
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
                TextView textViewAuxiliar = (TextView) view.findViewById(R.id.profileUser_history_adapter_songName);
                String text = textViewAuxiliar.getText().toString();

                // Cortamos el nombre de la pista y el artista
                String artist = text.split("-")[0];

                // Y lo mandamos al fragment de canciones
                ((MainActivity) getActivity()).setSearchedArtist(artist);
                ((MainActivity) getActivity()).abrirArtistas();

            }
        });


        return view;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        // Calculamos caunto hay que desplegar el GridView para poder mostrarlo todo dentro del ScrollView

        int alturaTotal = 0;
        int items = arrayAdapter.getCount();
        int filas = 0;

        View listItem = arrayAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        alturaTotal = listItem.getMeasuredHeight();

        float x = 1;

        x = items;
        filas = (int) (x + 1);
        alturaTotal *= filas;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = alturaTotal;
        listView.setLayoutParams(params);

    }
}
