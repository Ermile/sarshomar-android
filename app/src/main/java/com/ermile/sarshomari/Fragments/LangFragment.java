package com.ermile.sarshomari.Fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ermile.sarshomari.Activities.introActivity;
import com.ermile.sarshomari.R;
import com.github.paolorotolo.appintro.AppIntro;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LangFragment extends Fragment {




    public LangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_lang, container, false);

        TextView lang_fa = (TextView) vi.findViewById(R.id.fap_lang);
        lang_fa.setTypeface(Typeface.
                createFromAsset(this.getContext().getAssets(), "fonts/IRANSans.ttf"));



        RelativeLayout usa = (RelativeLayout) vi.findViewById(R.id._us_);
        RelativeLayout iran = (RelativeLayout) vi.findViewById(R.id._fa_);
        final ImageView done_us = (ImageView) vi.findViewById(R.id.set_lang_us);
        final ImageView done_ir = (ImageView) vi.findViewById(R.id.set_lang_fa);

        if (getActivity().getSharedPreferences("language", MODE_PRIVATE).getString("language","Fa").equals("Fa")){

            done_ir.setVisibility(View.VISIBLE);
            done_us.setVisibility(View.INVISIBLE);


        }else {
            done_us.setVisibility(View.VISIBLE);
            done_ir.setVisibility(View.INVISIBLE);
        }


        usa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getContext().getSharedPreferences("language", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("language", "En");
                editor.apply();
                done_us.setVisibility(View.VISIBLE);
                done_ir.setVisibility(View.INVISIBLE);
                getActivity().recreate();




            }
        });

        iran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getContext().getSharedPreferences("language", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("language", "Fa");
                editor.apply();
                done_ir.setVisibility(View.VISIBLE);
                done_us.setVisibility(View.INVISIBLE);
                getActivity().recreate();




            }
        });


        // Inflate the layout for this fragment
        return vi;




    }









}
