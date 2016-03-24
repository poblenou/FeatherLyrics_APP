package tk.sbarjola.pa.featherlyricsapp.Identificacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.firebase.client.Firebase;

import tk.sbarjola.pa.featherlyricsapp.Firebase.FirebaseConfig;
import tk.sbarjola.pa.featherlyricsapp.MainActivity;
import tk.sbarjola.pa.featherlyricsapp.R;

public class LoginActivity extends AppCompatActivity {

    // Referencias a Firebase
    FirebaseConfig config;
    private Firebase mainReference;              // Apunta a la raiz de firebase
    private Firebase referenciaListaUsuarios;    // Apunta a la lista de usuarios

    //EditText
    EditText loginEmail;        // EditText que contiene el email
    EditText loginPassword;     // EditText que contiene la contrasenya
    Button buttonLogin;         // Boton para loggearse
    Button buttonRegister;      // Boton para registarse en la aplicación
    TextView info;              // Info del intento de login

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
        buttonRegister = (Button) this.findViewById(R.id.login_ButtonRegister);
        info = (TextView) this.findViewById(R.id.login_info);

        // On click para el botón de login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity
                Intent registerLogin = new Intent().setClass(LoginActivity.this, MainActivity.class);
                startActivity(registerLogin);
            }
        });

        // On click para el botón de regisrarse
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity
                Intent registerIntent = new Intent().setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }
}
