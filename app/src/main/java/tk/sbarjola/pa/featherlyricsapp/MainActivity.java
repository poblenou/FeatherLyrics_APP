package tk.sbarjola.pa.featherlyricsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import tk.sbarjola.pa.featherlyricsapp.Canciones.Canciones;
import tk.sbarjola.pa.featherlyricsapp.Discografia.Discografia;
import tk.sbarjola.pa.featherlyricsapp.Noticias.Noticias;
import tk.sbarjola.pa.featherlyricsapp.RankingArtistas.RankingArtistas;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Datos de la API
    private String BaseURL = "http://api.vagalume.com.br/";  //Principio de la URL que usará retrofit
    private String apiKey = "754f223018be007a45003e3b87877bac";     // Key de Vagalume. Máximo 100.000 peticiones /dia

    private DrawerLayout drawer;
    private NavigationView navigationView;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajustamos la tooblar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Ajustamos el drawer de nuestro activity
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Ajustamos el navigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Hacemos que autmaticamente arranque en el fragmento "Home"
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new Home(), "home")
                .commit();

        // Para controlar la musica que se Android este reproduciendo

        IntentFilter iF = new IntentFilter();   // Intent filter que usaremos para recibir informacion de los reproductores de audio

        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.htc.music.metachanged");
        iF.addAction("fm.last.android.metachanged");
        iF.addAction("com.sec.android.app.music.metachanged");
        iF.addAction("com.nullsoft.winamp.metachanged");
        iF.addAction("com.amazon.mp3.metachanged");
        iF.addAction("com.miui.player.metachanged");
        iF.addAction("com.real.IMP.metachanged");
        iF.addAction("com.sonyericsson.music.metachanged");
        iF.addAction("com.rdio.android.metachanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        iF.addAction("com.andrew.apollo.metachanged");
        iF.addAction("com.spotify.mobile.android.metadatachanged");
        iF.addAction("com.spotify.music.metadatachanged");

        registerReceiver(mReceiver, iF);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Canciones canciones = (Canciones) getSupportFragmentManager().findFragmentByTag("canciones");

            String artist = intent.getStringExtra("artist");    // Sacamos el artista del intent
            String track = intent.getStringExtra("track");      // sacamos la pista

            Toast.makeText(context, track + " - " + artist, Toast.LENGTH_SHORT).show(); // Y lanzamos la toast

            Snackbar.make(findViewById(R.id.content_frame), track + " - " + artist, Snackbar.LENGTH_LONG).setAction("Action", null).show();

            if (canciones != null) {
                canciones.setSong(artist, track);
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        boolean transaccion = false; // Cuadno el boleano sea tre, se cambiará a otro fragment

        String tag = null;

        // Asignamos las acciones a cada menuItem del drawer
        if (id == R.id.nav_home) {
            fragment = new Home();
            transaccion = true;
        }
        else if (id == R.id.nav_canciones) {
            fragment = new Canciones();
            transaccion = true;
            tag = "canciones";
        }else if (id == R.id.nav_discografia) {
            fragment = new Discografia();
            transaccion = true;
        } else if (id == R.id.nav_noticias) {
            fragment = new Noticias();
            transaccion = true;
        } else if (id == R.id.nav_rankingArtistas) {
            fragment = new RankingArtistas();
            transaccion = true;
        } else if (id == R.id.nav_salir) {
            finish();   // La última opción es para cerrar la APP
        }

        if(transaccion){    // Si el boleano es true llamamos al nuevo fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment, tag)
                    .commit();

            item.setChecked(true);  // Y lo ponemos como marcado.
            getSupportActionBar().setTitle(item.getTitle());
        }

        drawer.closeDrawers();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        if (item.getItemId() == R.id.action_settings) {
            // Intent i = new Intent(this, SettingsActivity.class);    // intent para ir a la activity de settings
            // startActivity(i);                                       // hacemos el intent
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
