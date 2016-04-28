package tk.sbarjola.pa.featherlyricsapp.Identificacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import tk.sbarjola.pa.featherlyricsapp.Discografia.Vagalume.Item;
import tk.sbarjola.pa.featherlyricsapp.R;

/**
 * Created by 46465442z on 28/04/16.
 */
public class userArtistsAdapter extends ArrayAdapter<Item> {

    public String BASE_URL =  "http://www.vagalume.com.br/";

    public userArtistsAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Creamos el objeto en la posición correspondiente
        Item item = getItem(position);

        // Comprobamos si la view ya se ha usado antes, si no, la inflamos (es una buena practica y ahorramos recursos)
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_artists_adapter, parent, false);
        }

        // Asociamos cada variable a su elemento del layout
        TextView titular = (TextView) convertView.findViewById(R.id.user_artists_artistName);
        ImageView imagenArtista = (ImageView) convertView.findViewById(R.id.user_artists_artistImage);

        // Incorporamos los objetos al layout
        String urlImagen = BASE_URL + item.getCover();
        titular.setText(item.getDesc());
        Picasso.with(getContext()).load(urlImagen).fit().centerCrop().into(imagenArtista);

        return convertView; //Devolvemos la view ya rellena
    }


}
