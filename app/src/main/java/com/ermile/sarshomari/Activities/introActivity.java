package com.ermile.sarshomari.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Toast;

import com.ermile.sarshomari.Fragments.LangFragment;
import com.ermile.sarshomari.MainActivity;
import com.ermile.sarshomari.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class introActivity extends AppIntro {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If user has seen the intro before, go straight to main page

       /* if(getSharedPreferences("see_intro", MODE_PRIVATE).getInt("see_intro",0) == 1){

            Intent i2main = new Intent(introActivity.this, MainActivity.class);
            startActivity(i2main);
            finish();
            } */









        // Just set a title, description, background and image. AppIntro will do the rest.
        if (getSharedPreferences("language", MODE_PRIVATE).getString("language",null).equals("fa")){

            String languageToLoad  = "fa"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }






        }else {
            String languageToLoad  = "en"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
            getBaseContext().getResources().getDisplayMetrics());

        }

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.test_welcom),"fonts/IRANSans.ttf","به سرشمار خوش آمدید","fonts/IRANSans.ttf",R.drawable.lgs, Color.parseColor("#094b58")));
        addSlide(AppIntroFragment.newInstance("راهنمای استفاده","fonts/IRANSans.ttf","سرشمار بهترین نظرسنج","fonts/IRANSans.ttf",R.drawable.lgs, Color.parseColor("#4b0049")));



        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#3F51B5"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);
        setDoneText("ورود به برنامه");
        setDoneTextTypeface("fonts/IRANSans.ttf");
        setZoomAnimation();
        setGoBackLock(true);
        setSwipeLock(false);



        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        //save if the user has ever seen the intro
        SharedPreferences pref = getApplicationContext().getSharedPreferences("see_intro", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("see_intro", 1);
        editor.apply();



        Intent i2main = new Intent(introActivity.this, MainActivity.class);
        startActivity(i2main);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }




}
