package com.ermile.sarshomari.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ermile.sarshomari.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    LinearLayout host;



    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView mainsearch =(SearchView) vi.findViewById(R.id.search_view);
        host =(LinearLayout) vi.findViewById(R.id.results_host);



        mainsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {





            @Override
            public boolean onQueryTextSubmit(String query) {
                View searchcard = LayoutInflater.from(getActivity()).inflate(
                        R.layout.search_card, null);
                TextView mrusult = (TextView) searchcard.findViewById(R.id.search_text);
                mrusult.setText(query);

                host.addView(searchcard);


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                return true;
            }
        });
        // Inflate the layout for this fragment
        return vi;
    }


    public void searchPolls(String stext){



           // host.removeAllViews();






    }

}
