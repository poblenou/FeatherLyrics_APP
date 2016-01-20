package tk.sbarjola.pa.featherlyricsapp.Home;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tk.sbarjola.pa.featherlyricsapp.R;
import tk.sbarjola.pa.featherlyricsapp.provider.music.MusicColumns;

/**
 * Created by sergi on 19/01/16.
 */

public class MusicListAdapter extends SimpleCursorAdapter {

    Context context;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MusicListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Creamos el objeto en la posición correspondiente
        Cursor musica = getCursor();
        musica.moveToPosition(position);

        // Comprobamos si la view ya se ha usado antes, si no, la inflamos (es una buena practica y ahorramos recursos)
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.music_list_adapter_layout, parent, false);
        }

        // Asociamos cada variable a su elemento del layout
        TextView texto = (TextView) convertView.findViewById(R.id.music_list_adapter_songName);

        texto.setText(musica.getString(musica.getColumnIndex(MusicColumns.BAND)) + " - " + musica.getString(musica.getColumnIndex(MusicColumns.TITLE)));

        return convertView; //Devolvemos la view ya rellena
    }
}