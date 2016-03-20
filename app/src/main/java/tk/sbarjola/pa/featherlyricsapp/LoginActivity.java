package tk.sbarjola.pa.featherlyricsapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.ArrayList;

import tk.sbarjola.pa.featherlyricsapp.Firebase.FirebaseConfig;
import tk.sbarjola.pa.featherlyricsapp.Firebase.Usuario;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
