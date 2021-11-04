package com.intellidigest.hackathon_pitch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemSelectedListener, TextWatcher, AdapterView.OnItemClickListener {

    EditText username, password;
    AutoCompleteTextView crop;
    Spinner fertilizer, pest, pesticide;
    Button login, submit;
    RecyclerView recyclerView;

    //API URL
    String url = "https://stfc-api-wrggqo5tiq-lz.a.run.app";

    //AUTOFILL ARRAY
    JSONArray CROPS_ARRAY = new JSONArray();
    ArrayList<String> CROP_NAMES = new ArrayList<>();
    ArrayAdapter<String> cropAdapter;
    String currentCropId;

    ArrayList<String> FERTILIZER_ARRAY = new ArrayList<>();
    ArrayAdapter<String> fertilizerAdapter;

    JSONArray PEST_ARRAY = new JSONArray();
    ArrayList<String> PEST_NAMES = new ArrayList<>();
    ArrayAdapter<String> pestAdapter;

    ArrayList<String> PESTICIDE_ARRAY = new ArrayList<>();
    ArrayAdapter<String> pesticideAdapter;

    public enum api_call_type
    {
        SUGGESTION,
        FERTILIZERS,
        DISEASES,
        PESTICIDES
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(MainActivity.this);
        username.setOnFocusChangeListener(MainActivity.this);
        password.setOnFocusChangeListener(MainActivity.this);
        */

        //Default Spinner/Combobox Values
        FERTILIZER_ARRAY.add(0,"Please Select a Fertilizer.");
        PEST_NAMES.add(0,"Please Select a Disease.");
        PESTICIDE_ARRAY.add(0,"Please Select a Pesticide.");

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        //Crop Field
        String[] crop_names = getResources().getStringArray(R.array.crop_names);
        cropAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, crop_names);
        crop = findViewById(R.id.crop);
        crop.setOnFocusChangeListener(this);
        crop.setOnItemClickListener(this);
        crop.setAdapter(cropAdapter);
        crop.addTextChangedListener(this);

        //Fertilizer Spinner/Combobox
        fertilizerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, FERTILIZER_ARRAY);
        fertilizer = findViewById(R.id.fertilizer_spinner);
        fertilizer.setAdapter(fertilizerAdapter);

        //Pest Spinner/Combobox
        pestAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, PEST_NAMES);
        pest = findViewById(R.id.pest_spinner);
        pest.setOnItemSelectedListener(this);
        pest.setAdapter(pestAdapter);

        //Pesticide Spinner/Combobox
        pesticideAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, PESTICIDE_ARRAY);
        pesticide = findViewById(R.id.pesticide_spinner);
        pesticide.setAdapter(pesticideAdapter);

        //Recycler of Suggestions [HARDCODED]
        RecyclerItem cornTest = new RecyclerItem("Paddy (Dhan)", "Swarna Shreya", "0.21", R.drawable.paddy);
        RecyclerItem riceTest = new RecyclerItem("Cotton (Kapas)", "Common","0.53", R.drawable.cotton);
        RecyclerItem cerealTest = new RecyclerItem("Maize (Makka)","Hybrid New", "0.32", R.drawable.maize);
        List<RecyclerItem> test = new ArrayList<>();
        //Add items to be added.
        test.add(cornTest); test.add(riceTest); test.add(cerealTest);

        CustomAdapter customAdapter = new CustomAdapter(test, getApplicationContext());
        recyclerView = findViewById(R.id.crop_suggestion_recycler);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    private void executeGet(String url, final api_call_type type) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (type)
                {
                    case SUGGESTION:
                        cropAutoFill(response);
                        break;
                    case FERTILIZERS:
                        getFertilizers(response);
                        break;
                    case DISEASES:
                        getDiseases(response);
                        break;
                    case PESTICIDES:
                        getPesticides(response);
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error Response
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void cropAutoFill(String json){
        try
        {
            CROPS_ARRAY = new JSONArray(json);
            /*CROP_NAMES.clear();
            for (int i=0; i<CROPS_ARRAY.length(); i++)
            {
                JSONObject crop = CROPS_ARRAY.getJSONObject(i);
                String name = crop.getString("crop_name");
                if (!CROP_NAMES.contains(crop)){
                    CROP_NAMES.add(name);
                }
            }

            cropAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_1line, CROP_NAMES);
            crop.setAdapter(cropAdapter);*/
            try
            {
                for (int i=0; i < CROPS_ARRAY.length(); i++)
                {
                    JSONObject cropData = CROPS_ARRAY.getJSONObject(i);
                    String name = cropData.getString("crop_name");
                    String cropId = cropData.getString("crop_id");
                    if (crop.getText().toString().equals(name))
                    {
                        currentCropId = cropId;
                        executeGet(url + "/crop/" + cropId + "/fertilizers", api_call_type.FERTILIZERS);
                        executeGet(url + "/crop/" + cropId + "/diseases", api_call_type.DISEASES);
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void getFertilizers(String json){
        try
        {
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject fertilizer = jsonArray.getJSONObject(i);
                String name = fertilizer.getString("fertilizer_name");
                if (!FERTILIZER_ARRAY.contains(name))
                {
                    FERTILIZER_ARRAY.add(name);
                }
            }
            fertilizerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, FERTILIZER_ARRAY);
            fertilizer.setAdapter(fertilizerAdapter);
            Log.e("Fertilizers", FERTILIZER_ARRAY.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void getDiseases(String json){
        try
        {
            PEST_ARRAY = new JSONArray(json);
            for (int i=0; i<PEST_ARRAY.length(); i++)
            {
                JSONObject crop = PEST_ARRAY.getJSONObject(i);
                String name = crop.getString("disease_name");
                if (!PEST_NAMES.contains(crop)){
                    PEST_NAMES.add(name);
                }
            }
            pestAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, PEST_NAMES);
            pest.setAdapter(pestAdapter);
            Log.e("Diseases", PEST_ARRAY.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void getPesticides(String json){
        try
        {
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject pesticide = jsonArray.getJSONObject(i);
                String name = pesticide.getString("pesticide_name");
                if (!PESTICIDE_ARRAY.contains(name))
                {
                    PESTICIDE_ARRAY.add(name);
                }
            }
            pesticideAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, PESTICIDE_ARRAY);
            pesticide.setAdapter(pesticideAdapter);
            Log.e("Fertilizers", PESTICIDE_ARRAY.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    //Submit button
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.submit:
                if (crop.getText().toString() != ""
                        && fertilizer.getItemAtPosition(fertilizer.getSelectedItemPosition()).toString() != "Please Select a Fertilizer."
                        && pesticide.getItemAtPosition(pesticide.getSelectedItemPosition()).toString() != "Please Select a Pesticide."){
                    PopUpClass pop = new PopUpClass();
                    pop.showPopupWindow(v, crop.getText().toString(),
                            fertilizer.getItemAtPosition(fertilizer.getSelectedItemPosition()).toString(),
                            pesticide.getItemAtPosition(pesticide.getSelectedItemPosition()).toString());
                }
                break;
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Touched elsewhere but textbox
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            hideKeyboard(v);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try
        {
            for (int i=0; i < PEST_ARRAY.length(); i++)
            {
                JSONObject cropData = PEST_ARRAY.getJSONObject(i);
                String name = cropData.getString("disease_name");
                String diseaseId = cropData.getString("disease_id");
                if (pest.getSelectedItem().toString().equals(name))
                {
                    executeGet(url + "/crop/" + currentCropId + "/disease/" + diseaseId + "/pesticides", api_call_type.PESTICIDES);
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InputMethodManager imm = (InputMethodManager) getSystemService(view.getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

        //Clear Spinner/Comboboxes
        FERTILIZER_ARRAY.subList(1, FERTILIZER_ARRAY.size()).clear();
        PEST_NAMES.subList(1, PEST_NAMES.size()).clear();
        PESTICIDE_ARRAY.subList(1, PESTICIDE_ARRAY.size()).clear();
        executeGet(url + "/crop/name/" + parent.getItemAtPosition(position).toString(), api_call_type.SUGGESTION);
    }

    //AutoFill CALL API AFTER CERTAIN NUMBER OF LETTERS TYPED
    @Override
    public void afterTextChanged(Editable s) {
        /*
        if (s.length() >= 3 && !CROP_NAMES.contains(s.toString())) {
            //CALL API TO GET CROP SUGGESTIONS
            executeGet(url + "/crop/name/" + s.toString(), api_call_type.SUGGESTION);
        }*/
    }

    //region Unused Methods
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    //endregion

}
