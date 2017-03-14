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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ermile.sarshomari.AppController;
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
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PollFragment extends Fragment {

    public RequestQueue mRequestQueue;
    ArrayList<String> value_array = new ArrayList<String>();
    ArrayList<String> title_array = new ArrayList<String>();
    String poll_id;
    CheckBoxGroupView cbGroups;






    public PollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        AppController mApp = ((AppController) getActivity().getApplicationContext());
        mRequestQueue = mApp.getmRequestQueue();


        final View vi = inflater.inflate(R.layout.fragment_poll, container, false);

        cbGroups = (CheckBoxGroupView) vi.findViewById(R.id.grc);

        final FloatingActionButton fab = (FloatingActionButton) vi.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog results_dialog = new Dialog(getActivity());
                results_dialog.setTitle(getResources().getString(R.string.poll_results));
                results_dialog.setContentView(R.layout.my_chart_dialog_layout);
                results_dialog.show();
                setupPieChart(results_dialog);
            }
        });

















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


        Button pre_btn = (Button) vi.findViewById(R.id.previous_poll_button);
        Button ok_btn = (Button) vi.findViewById(R.id.verify_poll_button);
        Button nxt_btn = (Button) vi.findViewById(R.id.next_poll_button);



        nxt_btn.setTypeface( Typeface.
                createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));



        Button get_poll_btn = (Button) vi.findViewById(R.id.start_poll_btn);
        final RelativeLayout cover_layout = (RelativeLayout) vi.findViewById(R.id.cover_pre);

        get_poll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetRandomPoll(vi);
                cover_layout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.BounceInLeft).playOn(fab);
                YoYo.with(Techniques.FadeOutDown).playOn(cover_layout);
            }
        });

        Button acceptpollbtn = (Button) vi.findViewById(R.id.verify_poll_buttons);

        acceptpollbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Sendpollanswer();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });






        return vi;

    }

    @Override
    public void onStart(){
        super.onStart();





    }



    /////////////////////////////the main functions for polls system/////////////////////////////




    public void setupPieChart(Dialog results_dialog){


        PieChart pieChart = (PieChart) results_dialog.findViewById(R.id.chart);
        pieChart.setVisibility(View.VISIBLE);

        pieChart.setTouchEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.isUsePercentValuesEnabled();




        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < value_array.size(); i++){
            entries.add(new Entry(Integer.valueOf(value_array.get(i)), i));
        }



        PieDataSet dataset = new PieDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i < title_array.size(); i++){
            labels.add(title_array.get(i));
        }


        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setValueFormatter(new PercentFormatter());
        dataset.setValueTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));
        dataset.setValueTextColor(Color.WHITE);
        dataset.setValueTextSize(13);
        dataset.setDrawValues(true);
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
        entries.add(new BarEntry(32f, 0));
        entries.add(new BarEntry(43f, 1));
        entries.add(new BarEntry(13f, 2));
        entries.add(new BarEntry(54f, 3));
        entries.add(new BarEntry(22f, 4));


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
        dataset.setBarSpacePercent(10f);



        dataset.setValueTextSize(13);
        mbarChart.setDescription("");
        mbarChart.setDrawValueAboveBar(true);
        mbarChart.setData(data);
        mbarChart.setMaxVisibleValueCount(10);



        mbarChart.animateY(2000);

        mbarChart.invalidate();






    }



public void getAndSetRandomPoll(final View vie){
    final String token_guest = getActivity().getSharedPreferences("guest_token", MODE_PRIVATE).getString("gtkn",null);

    ImageView poll_image = (ImageView) vie.findViewById(R.id.single_answer_image);
    final TextView  poll_title = (TextView) vie.findViewById(R.id.multy_answer_title);
    TextView  poll_type_txt = (TextView) vie.findViewById(R.id.answer_type);

    final TextView  poll_desc = (TextView) vie.findViewById(R.id.single_answer_description);



    final String url = "https://sarshomar.com/api/v1/poll/random/";

// prepare the Request
    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response) {
                    // display response
                    try {
                        
                        JSONObject result_object = response.getJSONObject("result");

                        String p_title = result_object.getString("title");
                        String p_desc = result_object.getString("description");
                        poll_id = result_object.getString("id");
                        Log.d("onResponse_random: ",result_object.toString()
                        );

                        JSONArray options_json_array = result_object.getJSONObject("result").getJSONArray("answers");

                        poll_title.setText(p_title);
                        poll_desc.setText(p_desc);

                        for (int i = 0; i < options_json_array.length(); i++){

                            JSONObject jo = options_json_array.getJSONObject(i);
                            String answer_title = jo.getString("title");
                            String answer_key = jo.getString("key");
                            int answer_value = jo.getInt("value_reliable");

                            CheckBox cb = new CheckBox(getActivity());
                            cb.setTag(answer_key);
                            cb.setText(answer_title);
                            cb.setTypeface( Typeface.
                                    createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));
                            cbGroups.addView(cb);
                            cbGroups.put(cb);
                            value_array.add(String.valueOf(answer_value));
                            title_array.add(answer_title);




                            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    Log.d("onStart:",cbGroups.getCheckedIds().toString());
                                }

                        });
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.toString());
                    error.printStackTrace();
                }
            }
    ){
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {



            HashMap<String, String> mheaders = new HashMap<>();

/*
            String credentials = "ermile:Ermile1233";
            String auth = "Basic "
                    + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            mheaders.put("Content-Type", "application/json");
            Log.d("getHeaders: ",mheaders.toString());
            mheaders.put("Authorization", auth);
            */
            mheaders.put("api_token", token_guest);
            return mheaders;
        }
    };


// add it to the RequestQueue
    mRequestQueue.add(getRequest);
}


    public void Sendpollanswer() throws JSONException {


        String token_guest = getActivity().getSharedPreferences("guest_token", MODE_PRIVATE).getString("gtkn", null);
        String url = "http://sarshomar.com/api/v1/poll/answer/";
        RequestParams paramss = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");
        client.addHeader("Content_Type", "application/json");
        client.addHeader("api_token", token_guest);

        if (cbGroups.getCheckedIds().size() > 0){

            JSONObject answers_obj = new JSONObject();


            for(int i=0; i < cbGroups.getCheckedIds().size(); i++){
                answers_obj.put(cbGroups.getCheckedIds().get(i).toString(),true);
            }

            paramss.put("answers",answers_obj);
            paramss.put("id",poll_id);

            try {



                client.post(url,paramss, new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                        Log.d("onFail: ",responseString+throwable.toString());
                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                        Log.d("onSuccess_sendpoll: ",responseString);
                        Toast.makeText(getActivity().getApplicationContext(),responseString,Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onStart() {
                        // called before request is started
                    }


                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
            } catch (Exception e) {
                Log.d("Sendpollanswer: ","shit"+"->"+e.toString());
            }

        }

    }





}
