package tk.sbarjola.pa.featherlyricsapp.Firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sergi on 27/03/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Artista {

    // String
    private String artistas; // Generos que escucha el usuario

    // Constructores

    public Artista(){}

    // Getters

    public String getArtistas() {
        return artistas;
    }

    //  Setters

    public void setArtistas(String artistas) {
        this.artistas = artistas;
    }
}
