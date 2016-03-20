package tk.sbarjola.pa.featherlyricsapp.Firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sergi on 20/03/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Usuario {

    private String UID;      // Identificador del usuario
    private String email;    // Correo del usuario
    private String password; // Contrasenya
    private String key;      // key

    public Usuario(){}

    // Getters

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUID() {
        return UID;
    }

    public String getKey() {
        return key;
    }

    // Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setKey(String key) {
        this.key = key;
    }
}