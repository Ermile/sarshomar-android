package com.ermile.sarshomari.Fragments;


import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.ermile.sarshomari.Classes.Utility;
import com.ermile.sarshomari.MainActivity;
import com.ermile.sarshomari.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



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



    public newPollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View vi = inflater.inflate(R.layout.fragment_new_poll, container, false);
        thumbView = (ImageView) vi.findViewById(R.id.new_poll_thumbnail);
        filenametext = (TextView) vi.findViewById(R.id.new_poll_file_name);



        MaterialSpinner spinner = (MaterialSpinner) vi.findViewById(R.id.spinner_type);
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




        return vi;
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////new poll functions////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setUpNewPoll(View vi){
        final EditText new_option_input = (EditText) vi.findViewById(R.id.new_poll_new_option_EditText);
        ImageButton add_option_button = (ImageButton) vi.findViewById(R.id.add_option_button);
        final ListView  new_options_list = (ListView) vi.findViewById(R.id.options_ListView);
        Button upload_img_btn = (Button) vi.findViewById(R.id.upload_image_btn);
        Button upload_file_btn = (Button) vi.findViewById(R.id.upload_file_btn);

        final String new_option_txt = new_option_input.getText().toString();

       final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, OptionsArray);
        new_options_list.setAdapter(adapter);



        add_option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new_option_input.getText().toString().equals(null) || new_option_input.getText().toString().equals("")){

                    Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.new_option_error),Toast.LENGTH_LONG).show();

                }else {
                        OptionsArray.add(new_option_input.getText().toString());
                        adapter.notifyDataSetChanged();
                        new_option_input.setText("");
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
                try{
                    for (int ctr=0;ctr<=OptionsArray.size();ctr++){
                        if(i==ctr){
                            if (new_options_list.getChildAt(ctr).getTag() != null){
                                new_options_list.getChildAt(ctr).setBackgroundColor(Color.WHITE);
                                new_options_list.getChildAt(ctr).setTag(null);
                                CorrectOptionsArray.remove(i);
                            }else{
                                new_options_list.getChildAt(ctr).setBackgroundColor(Color.parseColor("#9CCC65"));
                                new_options_list.getChildAt(ctr).setTag("selected");
                                CorrectOptionsArray.add(new_options_list.getItemAtPosition(i).toString());
                            }


                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Log.v("Selected item",new_options_list.getItemAtPosition(i).toString());
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






}
