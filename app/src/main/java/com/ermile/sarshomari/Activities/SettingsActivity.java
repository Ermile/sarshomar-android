package com.ermile.sarshomari.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.ermile.sarshomari.MainActivity;
import com.ermile.sarshomari.R;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;



public class SettingsActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction().replace(R.id.setting_content, new MyPreferenceFragment()).commit();

        setLanguage();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_forward_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





        android.support.v7.app.ActionBar actionBar = getSupportActionBar();


        //actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2settings = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(i2settings);
                finish();
            }
        });









    }

    @Override
    public void onBackPressed() {

        getBack();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);







            addPreferencesFromResource(R.xml.prefrences);

            final ListPreference langpref = (ListPreference) findPreference("languageType");


            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            langpref.setIcon(getResources().getDrawable(R.drawable.ic_translate_black_24dp));


           // String current_lang = getActivity().getSharedPreferences("language", MODE_PRIVATE).getString("language",null);




            langpref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    SharedPreferences pref = getActivity().getSharedPreferences("language", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("language", newValue.toString());
                    editor.apply();
                    getActivity().recreate();


                    return false;
                }
            });





        }



    }



    public void setLanguage(){



        if (getSharedPreferences("language", MODE_PRIVATE).getString("language",null).equals("fa")){

            String languageToLoad  = "fa"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());





        }else {
            String languageToLoad  = "en"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

        }



    }



    public void setTitleGravityBasedOnLocale(){

        TextView title_toolbar = (TextView) findViewById(R.id.tv);

        if (getSharedPreferences("language", MODE_PRIVATE).getString("language",null).equals("fa")){
            title_toolbar.setGravity(Gravity.RIGHT);
        }else if (getSharedPreferences("language", MODE_PRIVATE).getString("language",null).equals("en")){
            title_toolbar.setGravity(Gravity.LEFT);
        }
    }

public void getBack(){
    Intent i2settings = new Intent(SettingsActivity.this, MainActivity.class);
    startActivity(i2settings);
    finish();
}




}
