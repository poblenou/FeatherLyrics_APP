package tk.sbarjola.pa.featherlyricsapp.Identificacion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import tk.sbarjola.pa.featherlyricsapp.Firebase.FirebaseConfig;
import tk.sbarjola.pa.featherlyricsapp.R;

public class RegisterActivity extends AppCompatActivity {

    // IMPORTANTE!
    // A la hora de usar Firebase para regisrar usuarios utilizando este metodo, es necesario activar
    // esta funcionalidad dentro de nuestro proyecto Firebase:
    // -> Enable Email & Password Authentication

    // Referencias a Firebase
    FirebaseConfig config;
    private Firebase mainReference;              // Apunta a la raiz de firebase
    private Firebase referenciaListaUsuarios;    // Apunta a la lista de usuarios

    //EditText
    EditText registerEmail;     // EditText que contiene el email
    EditText registerPassword;  // EditText que contiene la contrasenya
    Button buttonRegister;      // Boton para registarse en la aplicación
    TextView info;              // Info del intento de register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Feather Lyrics - Registarse");

        // Mostramos la flecha para retroceder de activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Damos valor a nuestras variables de firebase
        config = (FirebaseConfig) getApplication();
        mainReference = config.getMainReference();
        referenciaListaUsuarios = config.getReferenciaListaUsuarios();

        // Referncias al layout
        registerEmail = (EditText) this.findViewById(R.id.register_TextEmail);
        registerPassword = (EditText) this.findViewById(R.id.register_TextPassword);
        buttonRegister = (Button) this.findViewById(R.id.register_ButtonRegister);
        info = (TextView) this.findViewById(R.id.register_info);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Extraemos los datos de los editText
                final String email = registerEmail.getText().toString();
                final String password = registerPassword.getText().toString();

                // Creamos un nuevo usuario y lo subimos. Mostarmos el resultado en nuestro TextView.
                mainReference.createUser(email, password, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        info.setVisibility(View.VISIBLE);
                        info.setText("Se ha creado el nuevo usuario correctamente");
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        info.setVisibility(View.VISIBLE);
                        info.setText("Error " + firebaseError.toString().split(":")[1]);
                    }
                });
            }
        });


    }

}
