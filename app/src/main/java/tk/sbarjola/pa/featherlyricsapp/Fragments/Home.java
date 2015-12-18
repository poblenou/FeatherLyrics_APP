package tk.sbarjola.pa.featherlyricsapp.Fragments;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import tk.sbarjola.pa.featherlyricsapp.R;


public class Home extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, contenedor, false);

        ImageView featherIcon = (ImageView) view.findViewById(R.id.featherIcon);    //Asignme el id
        featherIcon.setImageResource(R.drawable.feather_icon);
        return view;
    }
}