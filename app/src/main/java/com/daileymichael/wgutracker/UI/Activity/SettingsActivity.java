package com.daileymichael.wgutracker.UI.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daileymichael.wgutracker.R;


public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPref;

    /**
     * This method sets the contentView and toolbar & radiobutton action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = this.getSharedPreferences(SharingActivity.SHARED_PREFS_FILENAME, 0);

        RadioButton shareEmail = findViewById(R.id.share_email);
        RadioButton shareSMS = findViewById(R.id.share_sms);
        String shareFromSharedPref = sharedPref.getString(SharingActivity.SHARED_PREF_SHARE, "EMAIL");

        if (shareFromSharedPref.equals("EMAIL")) {
            shareEmail.setChecked(true);
        } else {
            shareSMS.setChecked(true);
        }
    }

    /**
     * This method checks which option is selected in the settings for sharing information
     * @param view
     */
    public void onShareClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        SharedPreferences.Editor editor = sharedPref.edit();

        switch(view.getId()) {
            case R.id.share_email:
                if (checked) {
                    editor.putString(SharingActivity.SHARED_PREF_SHARE, "EMAIL");
                } else {
                    editor.putString(SharingActivity.SHARED_PREF_SHARE, "SMS");
                }
                break;
            case R.id.share_sms:
                if (checked) {
                    editor.putString(SharingActivity.SHARED_PREF_SHARE, "SMS");
                } else {
                    editor.putString(SharingActivity.SHARED_PREF_SHARE, "EMAIL");
                }
                break;
        }
        editor.apply();
    }


}

