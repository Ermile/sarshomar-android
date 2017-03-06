package com.ermile.sarshomari.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.ermile.sarshomari.AppController;
import com.ermile.sarshomari.Classes.CheckBoxGroupView;
import com.ermile.sarshomari.Classes.Utility;
import com.ermile.sarshomari.MainActivity;
import com.ermile.sarshomari.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.ermile.sarshomari.R.id.age_from_edittext;
import static com.ermile.sarshomari.R.id.age_to_edittext;
import static com.ermile.sarshomari.R.id.cbGroup;
import static com.ermile.sarshomari.R.id.cbGroupmarr;
import static com.ermile.sarshomari.R.id.hide_results_switch;
import static com.ermile.sarshomari.R.id.new_poll_description_EditText;
import static com.ermile.sarshomari.R.id.new_poll_tags_EditText;
import static com.ermile.sarshomari.R.id.new_poll_title_EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class newPollFragment extends Fragment {

    ArrayList<String> OptionsArray = new ArrayList<String>();
    ArrayList<String> CorrectOptionsArray = new ArrayList<String>();
    public String userChoosenTask;
    ImageView thumbView;
    TextView filenametext;
    public String chosen_poll_type = "single";
    public RequestQueue mRequestQueue;
    private static final String TIME_PATTERN = "HH:mm";


    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    TextView datetimetxt;
    TextView datetimetxt_end;
    MaterialSpinner spinner;
    Boolean poll_hide;
    String image_path="null";
    JSONArray answers_j_array;



    public newPollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View vi = inflater.inflate(R.layout.fragment_new_poll, container, false);
        thumbView = (ImageView) vi.findViewById(R.id.new_poll_thumbnail);
        filenametext = (TextView) vi.findViewById(R.id.new_poll_file_name);
        datetimetxt = (TextView) vi.findViewById(R.id.new_poll_schedule_txt);
        datetimetxt_end = (TextView) vi.findViewById(R.id.new_poll_schedule_txt_end);
        answers_j_array = new JSONArray();
        //HttpRequest Catching queue
        AppController mApp = ((AppController) getActivity().getApplicationContext());
        mRequestQueue = mApp.getmRequestQueue();

        spinner = (MaterialSpinner) vi.findViewById(R.id.spinner_type);
        spinner.setItems(getResources().getStringArray(R.array.poll_type_array));
        spinner.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));

        vi.findViewById(R.id.single_choose_poll).setVisibility(View.VISIBLE);


        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                if (item.equals(getResources().getString(R.string.singlechoice))){

                    chosen_poll_type = "single";

                    vi.findViewById(R.id.file_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.range_poll).setVisibility(View.GONE);


                    if (vi.findViewById(R.id.single_choose_poll).getVisibility() == View.GONE){
                        vi.findViewById(R.id.single_choose_poll).setVisibility(View.VISIBLE);

                        YoYo.with(Techniques.FadeIn)
                                .duration(350)
                                .playOn(vi);
                    }
                }

                if (item.equals(getResources().getString(R.string.multiplechoice))){
                    chosen_poll_type = "multiple";

                    vi.findViewById(R.id.file_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.range_poll).setVisibility(View.GONE);



                    if (vi.findViewById(R.id.single_choose_poll).getVisibility() == View.GONE){
                        vi.findViewById(R.id.single_choose_poll).setVisibility(View.VISIBLE);

                        YoYo.with(Techniques.FadeIn)
                                .duration(350)
                                .playOn(vi);
                    }

                }

                if (item.equals(getResources().getString(R.string.notify))){
                    chosen_poll_type = "notify";

                    vi.findViewById(R.id.single_choose_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.file_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.range_poll).setVisibility(View.GONE);

                    YoYo.with(Techniques.FadeIn)
                            .duration(350)
                            .playOn(vi);



                }

                if (item.equals(getResources().getString(R.string.score))){
                    chosen_poll_type = "score";

                    vi.findViewById(R.id.single_choose_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.file_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.range_poll).setVisibility(View.GONE);

                    YoYo.with(Techniques.FadeIn)
                            .duration(350)
                            .playOn(vi);



                }

                if (item.equals(getResources().getString(R.string.descriptive))){
                    chosen_poll_type = "describe";

                    vi.findViewById(R.id.single_choose_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.file_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.range_poll).setVisibility(View.GONE);

                    YoYo.with(Techniques.FadeIn)
                            .duration(350)
                            .playOn(vi);


                }

                if (item.equals(getResources().getString(R.string.file))){
                    chosen_poll_type = "file";

                    vi.findViewById(R.id.single_choose_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.range_poll).setVisibility(View.GONE);

                    if (vi.findViewById(R.id.file_poll).getVisibility() == View.GONE){
                        vi.findViewById(R.id.file_poll).setVisibility(View.VISIBLE);

                        YoYo.with(Techniques.FadeIn)
                                .duration(350)
                                .playOn(vi);
                    }



                }

                if (item.equals(getResources().getString(R.string.rangebar))){
                    chosen_poll_type = "range";

                    vi.findViewById(R.id.single_choose_poll).setVisibility(View.GONE);
                    vi.findViewById(R.id.file_poll).setVisibility(View.GONE);

                    if (vi.findViewById(R.id.range_poll).getVisibility() == View.GONE){
                        vi.findViewById(R.id.range_poll).setVisibility(View.VISIBLE);

                        YoYo.with(Techniques.FadeIn)
                                .duration(350)
                                .playOn(vi);
                    }


                }
            }
        });

        setUpNewPoll(vi);

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        Switch hide_switch = (Switch) vi.findViewById(hide_results_switch);

        hide_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    poll_hide = true;
                }else {
                    poll_hide = false;
                }
            }
        });






        return vi;


    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////new poll functions////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setUpNewPoll(final View vi) {
        final EditText new_option_input = (EditText) vi.findViewById(R.id.new_poll_new_option_EditText);
        ImageButton add_option_button = (ImageButton) vi.findViewById(R.id.add_option_button);
        final ListView new_options_list = (ListView) vi.findViewById(R.id.options_ListView);
        Button upload_img_btn = (Button) vi.findViewById(R.id.upload_image_btn);
        Button upload_file_btn = (Button) vi.findViewById(R.id.upload_file_btn);
        Button submit_poll_btn = (Button) vi.findViewById(R.id.submit_poll);
        Button date_btn = (Button) vi.findViewById(R.id.pick_date_time_btn);
        Button date_btn_end = (Button) vi.findViewById(R.id.pick_date_time_btn_end);


        final TimePickerDialog.OnTimeSetListener timelisten_start = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                update();
            }
        };

        final DatePickerDialog.OnDateSetListener datelisten_start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                update();
                TimePickerDialog.newInstance(timelisten_start, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");

            }
        };


        final TimePickerDialog.OnTimeSetListener timelisten_end = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                update_end();
            }
        };


        final DatePickerDialog.OnDateSetListener datelisten_end = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                update_end();
                TimePickerDialog.newInstance(timelisten_end, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
            }
        };





        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(datelisten_start, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getActivity().getFragmentManager(), "datePicker");

            }
        });

        date_btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(datelisten_end, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getActivity().getFragmentManager(), "datePicker");

            }
        });



        final String new_option_txt = new_option_input.getText().toString();

        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, OptionsArray);
        new_options_list.setAdapter(adapter);

        final CheckBoxGroupView cbGroup = (CheckBoxGroupView) vi.findViewById(R.id.cbGroup);

        for (int i = 0; i < 6; i++) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setTag("nice_dicks");
            cb.setText("علمی");
            cb.setTypeface(Typeface.
                    createFromAsset(getActivity().getAssets(), "fonts/IRANSans.ttf"));
            cbGroup.put(cb);
            cbGroup.getCheckedIds().toString();

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("onStart:", cbGroup.getCheckedIds().toString());
                }
            });
        }


            add_option_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (new_option_input.getText().toString().equals(null) || new_option_input.getText().toString().equals("")) {

                        Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.new_option_error), Toast.LENGTH_LONG).show();

                    } else {
                        OptionsArray.add(new_option_input.getText().toString());
                        adapter.notifyDataSetChanged();
                        new_option_input.setText("");
                        Log.d("onClickarray: ",OptionsArray.toString()+" "+OptionsArray.size());
                    }
                }
            });

            new_options_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               final int pos, long id) {
                    // TODO Auto-generated method stub

                    new AlertDialog.Builder(getActivity())
                            .setMessage(getResources().getString(R.string.wanttoremove))
                            .setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    OptionsArray.remove(pos);
                                    adapter.notifyDataSetChanged();
                                }
                            }).setNegativeButton(getResources().getString(R.string.cancel), null).show();


                    return true;
                }
            });

            new_options_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    try {
                        for (int ctr = 0; ctr <= OptionsArray.size(); ctr++) {
                            if (i == ctr) {
                                if (new_options_list.getChildAt(ctr).getTag() != null) {
                                    new_options_list.getChildAt(ctr).setBackgroundColor(Color.WHITE);
                                    new_options_list.getChildAt(ctr).setTag(null);
                                    CorrectOptionsArray.remove(i);
                                } else {
                                    new_options_list.getChildAt(ctr).setBackgroundColor(Color.parseColor("#9CCC65"));
                                    new_options_list.getChildAt(ctr).setTag("selected");
                                    CorrectOptionsArray.add(new_options_list.getItemAtPosition(i).toString());
                                }


                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.v("Selected item", new_options_list.getItemAtPosition(i).toString());
                }


            });


            upload_img_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });

            upload_file_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectFile();
                }
            });





            submit_poll_btn.setOnClickListener(new View.OnClickListener() {


                EditText title_input = (EditText) vi.findViewById(new_poll_title_EditText);
                EditText desc_input = (EditText) vi.findViewById(new_poll_description_EditText);
                EditText tags_input = (EditText) vi.findViewById(new_poll_tags_EditText);
                EditText age_from = (EditText) vi.findViewById(age_from_edittext);
                EditText age_to = (EditText) vi.findViewById(age_to_edittext);


                CheckBoxGroupView cbGroupDegree = (CheckBoxGroupView) vi.findViewById(R.id.cbGroupGrade);
                CheckBoxGroupView cbGroupEmploy = (CheckBoxGroupView) vi.findViewById(R.id.cbGroupemploy);
                CheckBoxGroupView cbGroupMarriage = (CheckBoxGroupView) vi.findViewById(R.id.cbGroupmarr);
                CheckBoxGroupView cbGroupgender = (CheckBoxGroupView) vi.findViewById(R.id.cbGroupsex);
                JSONObject from = new JSONObject();
                JSONObject schedule_obj = new JSONObject();
                JSONArray degree_arr = new JSONArray();
                JSONArray employ_arr = new JSONArray();
                JSONArray marriage_arr = new JSONArray();
                JSONArray gender_arr = new JSONArray();
                JSONArray age_range = new JSONArray();


                /////////////////////////////POLL INFO TO SEND////////////////////////////////////
                String poll_title = title_input.getText().toString();
                String poll_desc = desc_input.getText().toString();
                String poll_type = chosen_poll_type;
                String age_range_str = age_from.getText().toString()+"-"+age_to.getText().toString();
                ArrayList<String> options_array = OptionsArray;
                ArrayList<String> answers_array = CorrectOptionsArray;
                List<Object> group_degree = cbGroupDegree.getCheckedIds();
                List<Object> group_employ = cbGroupEmploy.getCheckedIds();
                List<Object> group_marriage = cbGroupMarriage.getCheckedIds();
                List<Object> group_gender = cbGroupgender.getCheckedIds();
















                String token_guest = getActivity().getSharedPreferences("guest_token", MODE_PRIVATE).getString("gtkn", null);

                @Override
                public void onClick(View v) {
                    JSONObject jsonBody = new JSONObject();
                    try {
                        for (int i=0; i < OptionsArray.size(); i++){

                            JSONObject jsonBody2 = new JSONObject();
                            String title = OptionsArray.get(i);
                            jsonBody2.put("title",title);
                            jsonBody2.put("type","select");
                            answers_j_array.put(jsonBody2);


                        }

                        if (group_degree.size() > 0) {

                            for (int i = 0; i < group_degree.size(); i++) {

                                if (group_degree.size() > 0) {
                                    String title = group_degree.get(i).toString();
                                    degree_arr.put(title);
                                }

                            }
                        }

                        if (group_employ.size() > 0) {

                            for (int i = 0; i < group_employ.size(); i++) {

                                if (group_employ.size() > 0) {
                                    String title = group_employ.get(i).toString();
                                    employ_arr.put(title);
                                }

                            }
                        }

                        if (group_marriage.size() > 0) {

                            for (int i = 0; i < group_marriage.size(); i++) {

                                if (group_marriage.size() > 0) {
                                    String title = group_marriage.get(i).toString();
                                    marriage_arr.put(title);
                                }

                            }
                        }

                        if (group_gender.size() > 0) {

                            for (int i = 0; i < group_gender.size(); i++) {

                                if (group_gender.size() > 0) {
                                    String title = group_gender.get(i).toString();
                                    gender_arr.put(title);
                                }

                            }
                        }

                        from.put("degree",degree_arr);
                        from.put("employmentstatus",employ_arr);
                        from.put("marrital",marriage_arr);
                        from.put("gender",gender_arr);
                        age_range.put(age_range_str);
                        from.put("range",age_range);

                        if(datetimetxt.getText() != null) {
                            schedule_obj.put("start", datetimetxt.getText());
                        }
                        if(datetimetxt_end.getText() != null) {
                            schedule_obj.put("end", datetimetxt_end.getText());
                        }




                        jsonBody.put("title", poll_title);
                        jsonBody.put("description", poll_desc);
                        jsonBody.put("summary", poll_desc);
                        //jsonBody.put("type", poll_type);
                        //jsonBody.put("cat", poll_type);
                        jsonBody.put("hidden_result", poll_hide);
                        jsonBody.put("answers",answers_j_array);
                        jsonBody.put("from",from);
                        jsonBody.put("schedule",schedule_obj);




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    /*
                    File final_file = new File(image_path);

                    try {
                        MultipartEntity entity = new MultipartEntity();

                        FileBody fileBody = new FileBody(final_file); // image should be
                        // a String
                        entity.addPart("file", fileBody);

                        entity.addPart("title",new StringBody(poll_title));
                        entity.addPart("description",new StringBody(poll_desc));
                        entity.addPart("summary",new StringBody(poll_desc));
                        //jsonBody.put("type", poll_type);
                        //jsonBody.put("cat", poll_type);
                        entity.addPart("hidden_result",poll_hide);
                        jsonBody.put("answers",answers_j_array);
                        jsonBody.put("from",from);
                        jsonBody.put("schedule",schedule_obj);


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    */

                    final MultipartEntity entity = new MultipartEntity();
                    File final_file = new File(image_path);
                    FileBody fileBody = new FileBody(final_file); // image should be
                    // a String
                    entity.addPart("file", fileBody);



                    //String user_token = getActivity().getSharedPreferences("user_token", MODE_PRIVATE).getString("utkn",null);

                    if (token_guest != null) {

                        String url ="https://sarshomar.com/api/v1/poll/";
                        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                                url, jsonBody,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // response
                                        Log.d("Response", response.toString());
                                        Toast.makeText(getActivity().getApplicationContext(),"نظرسنجی با موفقیت ارسال شد",Toast.LENGTH_LONG);
                                        getActivity().recreate();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error
                                        Log.d("Error.Response", error.toString());
                                    }
                                }
                        )


                        {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<>();

                                headers.put("api_token", token_guest);

                                return headers;
                            }

                            @Override
                            public byte[] getBody() {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                try {



                                    entity.writeTo(bos);
                                    String entityContentAsString = new String(bos.toByteArray());
                                    Log.e("volley", entityContentAsString);
                                } catch (IOException e) {
                                    VolleyLog.e("IOException writing to ByteArrayOutputStream");
                                }
                                return bos.toByteArray();
                            }

                            @Override
                            public String getBodyContentType() {
                                return entity.getContentType().getValue();
                            }

                        };

                        mRequestQueue.add(postRequest);
                    }
                }
            });




        }






/////////////////////////////////////////////////Image Upload Process /////////////////////////////////////////////////////////////////////
    private void selectImage() {
        final CharSequence[] items = { getResources().getString(R.string.takephoto), getResources().getString(R.string.choosefromgallery),
                getResources().getString(R.string.cancel) };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.addphoto));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(getActivity());

                if (items[item].equals(getResources().getString(R.string.takephoto))) {
                    userChoosenTask=getResources().getString(R.string.takephoto);
                    if(result)
                        cameraIntent();

                } else if (items[item].equals(getResources().getString(R.string.choosefromgallery))) {
                    userChoosenTask=getResources().getString(R.string.choosefromgallery);
                    if(result)
                        galleryIntent();

                } else if (items[item].equals(getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectFile(){
        Intent intent = new Intent();
        intent.setType("file/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2)
                onSelectFromGalleryResult(data);
            else if (requestCode == 1)
                onCaptureImageResult(data);
            else if (requestCode == 3)
                onSelectFile(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), data.getData());
                image_path = data.getData().getPath();
                Log.d("selected poll image:",image_path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        thumbView.setImageBitmap(bm);
    }



    private void onSelectFile(Intent data) {

        Log.d("onSelectFile: ","duck");


        if (data != null) {
            try {
                Uri selectedFileUri = data.getData();

                String filepath = selectedFileUri.getPath();
                filenametext.setText(filepath);
                Log.d("onSelectFile: ",filepath);

            } catch (Exception e){
                e.printStackTrace();

            }
        }



    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),2);
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 95, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        thumbView.setImageBitmap(thumbnail);
    }




    public void update(){

        datetimetxt.setText(dateFormat.format(calendar.getTime())+ " "+ timeFormat.format(calendar.getTime()));
    }

    public void update_end(){

        datetimetxt_end.setText(dateFormat.format(calendar.getTime())+ " "+ timeFormat.format(calendar.getTime()));
    }


}
