package tk.sbarjola.pa.featherlyricsapp.PerfilesUsuarios.TabLayoutFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import tk.sbarjola.pa.featherlyricsapp.Firebase.FirebaseConfig;
import tk.sbarjola.pa.featherlyricsapp.Firebase.Usuario;
import tk.sbarjola.pa.featherlyricsapp.MainActivity;
import tk.sbarjola.pa.featherlyricsapp.R;

/**
 * Created by 46465442z on 10/05/16.
 */
public class EditProfile extends Fragment {

    // Firebase
    FirebaseConfig config;                                      // Configuración de firebase
    private Firebase referenciaUser;                            // Apunta al usuario

    // Instancia del usuario que mostraremos y su UID
    Usuario userToShow;
    String userUID = "";

    // Vistas en las que mostraremo la información
    ImageView imageUser;
    TextView userName;
    TextView userDescription;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);    //Definimos el fragment

        // Instancia de la configuración
        config = (FirebaseConfig) getActivity().getApplication();
        referenciaUser = config.getReferenciaUsuarioLogeado();




        // Descargamos la lista de usuarios
        referenciaUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    try {

                        Usuario usuario = userSnapshot.getValue(Usuario.class);


                    }catch (NullPointerException e){}
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

        return view;
    }
}
