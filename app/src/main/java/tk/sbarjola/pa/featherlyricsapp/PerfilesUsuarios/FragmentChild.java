package tk.sbarjola.pa.featherlyricsapp.PerfilesUsuarios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tk.sbarjola.pa.featherlyricsapp.R;

/**
 * Created by 46465442z on 29/04/16.
 */
public class FragmentChild extends Fragment {

    String childname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Bundle bundle = getArguments();
        childname = bundle.getString("data");
        setEvents();
        return view;
    }



    private void setEvents() {

    }
}