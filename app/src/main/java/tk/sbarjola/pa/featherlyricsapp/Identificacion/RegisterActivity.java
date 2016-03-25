package tk.sbarjola.pa.featherlyricsapp.Identificacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import tk.sbarjola.pa.featherlyricsapp.Firebase.FirebaseConfig;
import tk.sbarjola.pa.featherlyricsapp.R;

public class RegisterActivity extends AppCompatActivity implements LocationListener {

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

    // Localización del usuario
    private ProgressDialog dialog;      // Pantalla de localización
    Location localizacion = null;       // Localización
    private ImageView imagenUsuario;    // Foto a enviar
    boolean imagen = false;             // Booleano que determina si el mensaje tiene imagen o no

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feather Lyrics - Registarse");

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
        imagenUsuario = (ImageView) this.findViewById(R.id.register_profilePic);

        // Localizacion
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);

        // Le damos un listener, que abrirá la camara al darl encima
        imagenUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagen = true;
                Intent camara = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(camara);
            }
        });

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

    public String getRutaImagen() {
        // Con este código obtenemos la ruta de la imagen
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        return cursor.getString(column_index_data);
    }

    // Metodos locationListener
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onLocationChanged(Location location) {
        localizacion = location;
        dialog.dismiss();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderDisabled(String provider) {

    }

}
