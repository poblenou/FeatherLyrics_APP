package tk.sbarjola.pa.featherlyricsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.firebase.client.Firebase;

import tk.sbarjola.pa.featherlyricsapp.Firebase.FirebaseConfig;
import tk.sbarjola.pa.featherlyricsapp.Firebase.Usuario;

public class LoginActivity extends AppCompatActivity {

    // Referencias a Firebase
    FirebaseConfig config;
    private Firebase mainReference;              // Apunta a la raiz de firebase
    private Firebase referenciaListaUsuarios;    // Apunta a la lista de usuarios

    //EditText
    EditText loginEmail;
    EditText loginPassword;
    Button buttonLogin;
    TextView info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ajustamos la tooblar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Feather Lyrics - Login");

        // Damos valor a nuestras variables de firebase
        config = (FirebaseConfig) getApplication();
        mainReference = config.getMainReference();
        referenciaListaUsuarios = config.getReferenciaListaUsuarios();

        // Referncias al layout
        loginEmail = (EditText) this.findViewById(R.id.login_TextEmail);
        loginPassword = (EditText) this.findViewById(R.id.login_TextPassword);
        buttonLogin = (Button) this.findViewById(R.id.login_ButtonLogin);
        info = (TextView) this.findViewById(R.id.login_info);

    }
}
