package com.ermile.sarshomari.Fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ermile.sarshomari.Classes.CheckBoxGroupView;
import com.ermile.sarshomari.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class PollFragment extends Fragment {






    public PollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vi = inflater.inflate(R.layout.fragment_poll, container, false);

        FloatingActionButton fab = (FloatingActionButton) vi.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog results_dialog = new Dialog(getActivity());
                results_dialog.setTitle(getResources().getString(R.string.poll_results));
                results_dialog.setContentView(R.layout.my_chart_dialog_layout);
                results_dialog.show();
                setupBarChart(results_dialog);
            }
        });












        final CheckBoxGroupView cbGroup = (CheckBoxGroupView) vi.findViewById(R.id.cbGroup);

        CheckBox cb = new CheckBox(getActivity());
        cb.setTag(1);
        cb.setText("مرسدس بنز");
        cb.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));

        CheckBox cb2 = new CheckBox(getActivity());
        cb.setTag(2);
        cb2.setText("فورد");
        cb2.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));

        CheckBox cb3 = new CheckBox(getActivity());
        cb.setTag(3);
        cb3.setText("هیوندای");
        cb3.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));

        CheckBox cb4 = new CheckBox(getActivity());
        cb.setTag(4);
        cb4.setText("تویوتا");
        cb4.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));

        CheckBox cb5 = new CheckBox(getActivity());
        cb.setTag(5);
        cb5.setText("لامبورگینی");
        cb5.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));

        cbGroup.put(cb);
        cbGroup.put(cb2);
        cbGroup.put(cb3);
        cbGroup.put(cb4);
        cbGroup.put(cb5);




        /*for (int i=0; i<8; i++){
            CheckBox cb = new CheckBox(getActivity());
            cb.setTag(1);
            cb.setText("مرسدس بنز");
            cb.setTypeface( Typeface.
                    createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));
            cbGroup.put(cb);
            cbGroup.getCheckedIds().toString();

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("onStart:",cbGroup.getCheckedIds().toString());
                }
            });

        }*/


        vi.findViewById(R.id.multiple_choose_poll).setVisibility(View.VISIBLE);

        EditText edittext_explain = (EditText) vi.findViewById(R.id.explain_poll_answer_edittext);
        TextView title_explain = (TextView) vi.findViewById(R.id.answer_title);
        edittext_explain.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));
        title_explain.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));

        Button pre_btn = (Button) vi.findViewById(R.id.previous_poll_button);
        Button ok_btn = (Button) vi.findViewById(R.id.verify_poll_button);
        Button nxt_btn = (Button) vi.findViewById(R.id.next_poll_button);



        nxt_btn.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));
        ok_btn.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));
        pre_btn.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));




        return vi;

    }

    @Override
    public void onStart(){
        super.onStart();





    }



    /////////////////////////////the main function for polls system/////////////////////////////




    public void setupPieChart(Dialog results_dialog){


        PieChart pieChart = (PieChart) results_dialog.findViewById(R.id.chart);

        pieChart.setTouchEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);



        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(32, 0));
        entries.add(new Entry(43, 1));
        entries.add(new Entry(13, 2));
        entries.add(new Entry(54, 3));
        entries.add(new Entry(22, 4));


        PieDataSet dataset = new PieDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("مرسدس بنز");
        labels.add("تویوتا");
        labels.add("هیوندای");
        labels.add("لامبورگینی");
        labels.add("فورد");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setValueFormatter(new PercentFormatter());
        dataset.setValueTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));
        dataset.setValueTextColor(Color.WHITE);
        dataset.setValueTextSize(13);
        pieChart.setDescription("");

        pieChart.setData(data);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);


        pieChart.animateY(2000);

        pieChart.invalidate();






    }



    public void setupBarChart(Dialog results_dialog){


        BarChart mbarChart = (BarChart) results_dialog.findViewById(R.id.barchart);


        mbarChart.setVisibility(View.VISIBLE);

        mbarChart.setTouchEnabled(true);




        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(32, 0));
        entries.add(new BarEntry(43, 1));
        entries.add(new BarEntry(13, 2));
        entries.add(new BarEntry(54, 3));
        entries.add(new BarEntry(22, 4));


        BarDataSet dataset = new BarDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("مرسدس بنز");
        labels.add("تویوتا");
        labels.add("هیوندای");
        labels.add("لامبورگینی");
        labels.add("فورد");

        BarData data = new BarData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setValueFormatter(new PercentFormatter());
        dataset.setValueTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));
        dataset.setValueTextColor(Color.BLACK);

        dataset.setValueTextSize(13);
        mbarChart.setDescription("");

        mbarChart.setData(data);





        mbarChart.animateY(2000);

        mbarChart.invalidate();






    }

}