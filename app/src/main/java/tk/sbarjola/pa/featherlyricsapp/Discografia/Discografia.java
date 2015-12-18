package tk.sbarjola.pa.featherlyricsapp.Discografia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.sbarjola.pa.featherlyricsapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Discografia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Discografia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Discografia extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artistas, contenedor, false);
        return view;
    }
}
