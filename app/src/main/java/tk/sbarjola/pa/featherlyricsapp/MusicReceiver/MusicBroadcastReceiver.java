package tk.sbarjola.pa.featherlyricsapp.MusicReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import tk.sbarjola.pa.featherlyricsapp.MainActivity;

/**
 * Created by 46465442z on 14/01/16.
 */
public class MusicBroadcastReceiver extends BroadcastReceiver {

    public static String playingArtist = "no artist";
    public static String playingTrack = "no track";
    static MainActivity mainVar = null;                 // Esta será la clase del MainActivity

    public MusicBroadcastReceiver() {


    }

    public static void setMainActivityHandler(MainActivity main){
        mainVar = main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        playingArtist = intent.getStringExtra("artist");    // Sacamos el artista del intent
        playingTrack = intent.getStringExtra("track");      // sacamos la pista

        // Preferencias personalizadas
        SharedPreferences preferencias =  context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

        if (preferencias.getBoolean("toastNotificacion", true)){
            Toast.makeText(context, playingTrack + " - " + playingArtist, Toast.LENGTH_SHORT).show(); // Y lanzamos la toast
        }

        /* Si está vinculado al MainActivity, es decir, si la APP se está ejecutando
         le dará un aviso de que ha habido un cambio de canción */

        if(mainVar != null){
            mainVar.extraerInfoMusica();
            mainVar.actualizarMusica();
        }
    }

    public static String getPlayingArtist() {
        return playingArtist;
    }

    public static String getPlayingTrack() {
        return playingTrack;
    }
}