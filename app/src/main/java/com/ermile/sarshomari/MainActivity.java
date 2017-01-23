package com.ermile.sarshomari;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ermile.sarshomari.Activities.HelpActivity;
import com.ermile.sarshomari.Activities.ProfileActivity;
import com.ermile.sarshomari.Activities.SettingsActivity;
import com.ermile.sarshomari.Fragments.PollFragment;
import com.ermile.sarshomari.Fragments.SearchFragment;
import com.ermile.sarshomari.Fragments.newPollFragment;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    public Menu menu;
    static {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        ////////////////////////////////set toolbar title and icon//////////////////////////////////////////

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                Typeface titleFont = Typeface.
                        createFromAsset(this.getAssets(), "fonts/IRANSans.ttf");
                tv.setText(getResources().getString(R.string.app_name));
                tv.setTypeface(titleFont);
                break;

            }
        }




        //diable arrow in mainpage->>>>  toolbar.setNavigationIcon(R.drawable.ic_arrow_forward_black_24dp);





        /////////////////////////////////////////Handle Bottom Navigation Tab selection here//////////////////////////////////////////////////

        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (tabId == R.id.tab_polls) {

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new PollFragment());

                    ft.commit();
                    YoYo.with(Techniques.FadeIn)
                            .duration(500)
                            .playOn(findViewById(R.id.container));




                }

                if (tabId == R.id.new_poll) {

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new newPollFragment());

                    ft.commit();
                    YoYo.with(Techniques.FadeIn)
                            .duration(500)
                            .playOn(findViewById(R.id.container));







                }


                if (tabId == R.id.tab_search) {

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new SearchFragment());

                    ft.commit();
                    YoYo.with(Techniques.FadeIn)
                            .duration(500)
                            .playOn(findViewById(R.id.container));









                }
            }
        });













    }

    @Override
    public void onResume(){
        super.onResume();
        setTitleGravityBasedOnLocale();

    }

    @Override
    public void onStart(){
        super.onStart();
        setTitleGravityBasedOnLocale();

    }

    @Override
    public void onRestart(){
        super.onRestart();

    }





    @Override
    public void onBackPressed() {


            new AlertDialog.Builder(this)
                    .setMessage(getResources().getString(R.string.exit))
                    .setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            exitAppMethod();
                        }
                    }).setNegativeButton(getResources().getString(R.string.cancel), null).show();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    ///////////////////////////////////////////////setup main menu////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i2settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i2settings);
            finish();

        }

        if (id == R.id.action_help) {
            Intent i2help = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(i2help);
        }

        if (id == R.id.action_profile) {
            Intent i2profile = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(i2profile);
        }



        return super.onOptionsItemSelected(item);
    }









    //custom functions



    public void exitAppMethod(){

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    public void setTitleGravityBasedOnLocale(){

        TextView title_toolbar = (TextView) findViewById(R.id.tv);

        if (getSharedPreferences("language", MODE_PRIVATE).getString("language",null).equals("fa")){
            title_toolbar.setGravity(Gravity.RIGHT);
        }else if (getSharedPreferences("language", MODE_PRIVATE).getString("language",null).equals("en")){
            title_toolbar.setGravity(Gravity.LEFT);
        }
    }





}
