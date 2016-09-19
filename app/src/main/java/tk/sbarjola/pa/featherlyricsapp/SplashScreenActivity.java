package tk.sbarjola.pa.featherlyricsapp;

/**
 * Created by sergi on 21/12/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.IOException;


public class SplashScreenActivity extends Activity {

    SharedPreferences preferencias; // Preferencias personalizadas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferencias = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

        boolean sonido = preferencias.getBoolean("sound", true);
        boolean nuevoTono = preferencias.getBoolean("nuevoTono", false);

        if (sonido == true) {

            if(nuevoTono == true){

                try {
                    MediaPlayer mp = new MediaPlayer();
                    mp.setDataSource(preferencias.getString("rutaTono", null));
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else{
                MediaPlayer mp = MediaPlayer.create(this, R.raw.intro);
                mp.start();
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}