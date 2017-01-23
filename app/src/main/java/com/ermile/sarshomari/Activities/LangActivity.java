package com.ermile.sarshomari.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ermile.sarshomari.MainActivity;
import com.ermile.sarshomari.R;

import static java.security.AccessController.getContext;

public class LangActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);



if (getSharedPreferences("language", MODE_PRIVATE).getString("language",null) != null){

    Intent i2main = new Intent(LangActivity.this, MainActivity.class);
    startActivity(i2main);
    finish();

}else {

    TextView lang_fa = (TextView) findViewById(R.id.fap_lang);
    lang_fa.setTypeface(Typeface.
            createFromAsset(this.getAssets(), "fonts/IRANSans.ttf"));

    RelativeLayout usa = (RelativeLayout) findViewById(R.id._us_);
    RelativeLayout iran = (RelativeLayout) findViewById(R.id._fa_);


    usa.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences pref = getSharedPreferences("language", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("language", "en");
            editor.apply();
            Intent i2intro = new Intent(LangActivity.this, introActivity.class);
            startActivity(i2intro);
            finish();


        }
    });

    iran.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences pref = getSharedPreferences("language", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("language", "fa");
            editor.apply();
            Intent i2intro = new Intent(LangActivity.this, introActivity.class);
            startActivity(i2intro);
            finish();


        }
    });


}




    }
}
