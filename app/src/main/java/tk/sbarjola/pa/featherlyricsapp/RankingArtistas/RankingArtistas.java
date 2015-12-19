package tk.sbarjola.pa.featherlyricsapp.RankingArtistas;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import tk.sbarjola.pa.featherlyricsapp.R;


public class RankingArtistas extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artistas, contenedor, false);
        return view;
    }
}
