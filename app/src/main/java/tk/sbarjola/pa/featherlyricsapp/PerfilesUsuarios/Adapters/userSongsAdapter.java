package tk.sbarjola.pa.featherlyricsapp.PerfilesUsuarios.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Url;
import tk.sbarjola.pa.featherlyricsapp.APIs.Spotify.ArtistSpotify;
import tk.sbarjola.pa.featherlyricsapp.R;

/**
 * Created by 46465442z on 28/04/16.
 */
public class userSongsAdapter extends ArrayAdapter<String> {

    private String BaseURL = "https://api.spotify.com/v1/";    // Principio de la URL que usará retrofit
    private servicioImagenArtistaRetrofit servicioImagen;      // Interfaz para descargar la imagen
    String artista = "";

    // Declaramos el retrofit como variable global para poder reutilizarlo si es necesario
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public userSongsAdapter(Context context, int resource, List<String> name) {
        super(context, resource, name);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Creamos el objeto en la posición correspondiente
        String item = getItem(position);
        this.artista = item.split("-")[0];

        // Comprobamos si la view ya se ha usado antes, si no, la inflamos (es una buena practica y ahorramos recursos)
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_songs_adapter, parent, false);
        }

        // Asociamos cada variable a su elemento del layout
        TextView nombreCancion = (TextView) convertView.findViewById(R.id.user_songs_songName);
        TextView nombreGrupo = (TextView) convertView.findViewById(R.id.user_songs_band);

        if(item.contains("-")){
            nombreGrupo.setText(item.split("-")[0]);
            nombreCancion.setText(item.split("-")[1]);

        }
        else{
            nombreGrupo.setText(item);
            nombreCancion.setText("Canción desconocida");
        }

        descargaArtista(convertView);

        return convertView; //Devolvemos la view ya rellena
    }

    public void descargaArtista(final View convertView){

        String URLSpotify = "https://api.spotify.com/v1/search?q=" + artista + "&type=artist";

        servicioImagen = retrofit.create(servicioImagenArtistaRetrofit.class);

        Call<ArtistSpotify> llamadaSpotify = (Call<ArtistSpotify>) servicioImagen.artistsSpotify(URLSpotify);

        llamadaSpotify.enqueue(new Callback<ArtistSpotify>() {
            @Override
            public void onResponse(Response<ArtistSpotify> response, Retrofit retrofit) {

                ArtistSpotify resultado = response.body();

                if (response.isSuccess()) {

                    if(resultado.getArtists().getItems().size() != 0){

                        try{
                            //Comprobamos si el artista tiene imagen

                            if(resultado.getArtists().getItems().get(0).getImages().size() != 0){

                                // Extraemos la URL de nuestra imagen parsendo el JSON
                                String URLimagen = resultado.getArtists().getItems().get(0).getImages().get(0).toString();
                                URLimagen = URLimagen.split(",")[1].split(",")[0].replace("url=", "").trim();

                                ImageView imagenArtista = (ImageView) convertView.findViewById(R.id.user_artists_artistImage);

                                Transformation transformation = new RoundedTransformationBuilder()
                                        .cornerRadiusDp(360)
                                        .oval(false)
                                        .build();

                                Picasso.with(getContext()).load(URLimagen).fit().centerCrop().transform(transformation).into(imagenArtista);
                            }
                        }
                        catch (NullPointerException ex){}
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {}
        });
    }

    public interface servicioImagenArtistaRetrofit{ // Interficie para descargar la imagen del artista
        @GET
        Call<ArtistSpotify> artistsSpotify(@Url String url); // Le pasamos la URL entera ya construida
    }
}
