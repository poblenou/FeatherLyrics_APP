package tk.sbarjola.pa.featherlyricsapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by 46465442z on 14/01/16.
 */
public class MusicBroadcastReceiver extends BroadcastReceiver {

    public MusicBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String playingArtist = intent.getStringExtra("artist");    // Sacamos el artista del intent
        String playingTrack = intent.getStringExtra("track");      // sacamos la pista

        // Preferencias personalizadas
        SharedPreferences preferencias =  context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

        if (preferencias.getBoolean("toastNotificacion", true)){
            Toast.makeText(context, playingTrack + " - " + playingArtist, Toast.LENGTH_SHORT).show(); // Y lanzamos la toast
        }
    }
}