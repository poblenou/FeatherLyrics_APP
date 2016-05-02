package tk.sbarjola.pa.featherlyricsapp.Firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by sergi on 27/03/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artista {

    // String
    private String artistas; // Generos que escucha el usuario
    private ArrayList<String> idsUsuarios = new ArrayList<>();
    private ArrayList<String> idsCanciones = new ArrayList<>();
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

    public ArrayList<String> getIdsUsuarios() {
        return idsUsuarios;
    }

    public void setIdsUsuarios(ArrayList<String> idsUsuarios) {
        this.idsUsuarios = idsUsuarios;
    }
}
