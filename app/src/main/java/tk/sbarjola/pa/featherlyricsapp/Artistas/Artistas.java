package tk.sbarjola.pa.featherlyricsapp.Artistas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tk.sbarjola.pa.featherlyricsapp.R;

public class Artistas extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artistas, contenedor, false);
        return view;
    }
}