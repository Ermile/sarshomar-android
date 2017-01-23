package com.ermile.sarshomari.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ermile.sarshomari.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {
    EditText email_input;
    EditText pass;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



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






        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                Typeface titleFont = Typeface.
                        createFromAsset(this.getAssets(), "fonts/IRANSans.ttf");
                tv.setText(getResources().getString(R.string.profile));
                tv.setTypeface(titleFont);
                break;

            }
        }





        EditText name = (EditText) findViewById(R.id.name);
        Button login=(Button) findViewById(R.id.btn_login);
        Button join=(Button) findViewById(R.id.btn_signup);
        Button forget=(Button) findViewById(R.id.btn_forget);


        Typeface medium_sans = Typeface.createFromAsset(getAssets(), "fonts/IRANSans.ttf");

        email_input = (EditText) findViewById(R.id.emailinput);
        pass = (EditText) findViewById(R.id.pass);


        email_input.setTypeface(medium_sans);
        pass.setTypeface(medium_sans);
        forget.setTypeface(medium_sans);
        join.setTypeface(medium_sans);
        login.setTypeface(medium_sans);
        name.setTypeface(medium_sans);
        name.setVisibility(View.GONE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
