package com.reddyz.newz;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.prefs.PreferenceChangeListener;

public class SettingsActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NewsSettingsPreferenceFragment
            extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_preference_menu);

            Preference sortByPref = findPreference(getString(R.string.settings_sort_by_key));
            bindPreferenceSummaryToValue(sortByPref);
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String value = sharedPrefs.getString(preference.getKey(),"");
            onPreferenceChange(preference, value);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String prefValue= newValue.toString();
            if( preference instanceof ListPreference) {
                ListPreference listPref = (ListPreference) preference;
                int index = listPref.findIndexOfValue(prefValue);
                if(index >= 0) {
                    CharSequence [] labels = listPref.getEntries();
                    preference.setSummary(labels[index]);
                }
            } else {
                preference.setSummary(prefValue);
            }
            return true;
        }
    }
}
