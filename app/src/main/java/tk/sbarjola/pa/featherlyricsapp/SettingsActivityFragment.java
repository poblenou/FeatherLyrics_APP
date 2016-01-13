package tk.sbarjola.pa.featherlyricsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by sergi on 27/12/15.
 */
public class SettingsActivityFragment extends PreferenceFragment {

    SharedPreferences myPreferences;
    Switch toastSwitch;
    Switch splashSwitch;
    Switch soundSwitch;
    SharedPreferences.Editor sharedPreferencesEditor;

    public SettingsActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View settingsActivity = inflater.inflate(R.layout.fragment_settings, container, false);

        myPreferences = getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

        toastSwitch = (Switch) settingsActivity.findViewById(R.id.settings_mostrarToast);
        splashSwitch = (Switch) settingsActivity.findViewById(R.id.settings_mostarSplashScreen);
        soundSwitch = (Switch) settingsActivity.findViewById(R.id.settings_sound);

        toastSwitch.setChecked(myPreferences.getBoolean("toastNotificacion", true));
        splashSwitch.setChecked(myPreferences.getBoolean("splash", true));
        soundSwitch.setChecked(myPreferences.getBoolean("sound", true));

        toastSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    sharedPreferencesEditor = myPreferences.edit();
                    sharedPreferencesEditor.putBoolean("toastNotificacion", true);
                    sharedPreferencesEditor.commit();
                } else {
                    sharedPreferencesEditor = myPreferences.edit();
                    sharedPreferencesEditor.putBoolean("toastNotificacion", false);
                    sharedPreferencesEditor.commit();
                }
            }
        });

        splashSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    sharedPreferencesEditor = myPreferences.edit();
                    sharedPreferencesEditor.putBoolean("splash", true);
                    sharedPreferencesEditor.commit();
                } else {
                    sharedPreferencesEditor = myPreferences.edit();
                    sharedPreferencesEditor.putBoolean("splash", false);
                    sharedPreferencesEditor.commit();
                }
            }
        });

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    sharedPreferencesEditor = myPreferences.edit();
                    sharedPreferencesEditor.putBoolean("sound", true);
                    sharedPreferencesEditor.commit();
                } else {
                    sharedPreferencesEditor = myPreferences.edit();
                    sharedPreferencesEditor.putBoolean("sound", false);
                    sharedPreferencesEditor.commit();
                }
            }
        });

        return settingsActivity;
    }
}