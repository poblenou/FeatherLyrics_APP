package tk.sbarjola.pa.featherlyricsapp.Firebase;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by sergi on 20/03/16.
 */

public class FirebaseConfig extends Application {

    // Clase que configura Firebsae

    private Firebase mainReference;              // Apunta a la raiz de firebase
    private Firebase referenciaListaUsuarios;    // Apunta a la lista de usuarios
    private Firebase referenciaUsuarioLogeado;   // Apunta al usuario loggeado

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

        // Referncias de firebase
        mainReference = new Firebase("https://feathermusic.firebaseio.com/");
        referenciaListaUsuarios = new Firebase("https://feathermusic.firebaseio.com/UsersList");
        referenciaUsuarioLogeado  = new Firebase("https://feathermusic.firebaseio.com/UsersList");

    }

    // Getters

    public Firebase getMainReference() {
        return mainReference;
    }

    public Firebase getReferenciaListaUsuarios() {
        return referenciaListaUsuarios;
    }

    public Firebase getReferenciaUsuarioLogeado() {
        return referenciaUsuarioLogeado;
    }

    // Setters

    public void setMainReference(Firebase mainReference) {
        this.mainReference = mainReference;
    }

    public void setReferenciaListaUsuarios(Firebase referenciaListaUsuarios) {
        this.referenciaListaUsuarios = referenciaListaUsuarios;
    }

    public void setReferenciaUsuarioLogeado(Firebase referenciaUsuarioLogeado) {
        this.referenciaUsuarioLogeado = referenciaUsuarioLogeado;
    }
}
