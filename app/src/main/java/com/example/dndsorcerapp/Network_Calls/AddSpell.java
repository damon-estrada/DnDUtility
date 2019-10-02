package com.example.dndsorcerapp.Network_Calls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.dndsorcerapp.DatabaseTools.MyDnDAPI;
import com.example.dndsorcerapp.DatabaseTools.SpellEntity;
import com.example.dndsorcerapp.R;
import com.example.dndsorcerapp.Responses.SpellResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

/**
 * AddSpell Model Object.
 *
 * <P>This is where the spells are called using retrofit and eventualy being passed
 * back to the SpellsFragment fragment to process the retrieved spells from the API.
 *
 * @author Damon Estrada
 * @version 1.0
 * @since   2019-09-07
 */
public class AddSpell extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /* UI variables */
    private NumberPicker spellLvlFilter;
    private NumberPicker spellRangeFilter;
    private Spinner schoolSpinner;
    private Spinner classesSpinner;
    private Spinner concentrationSpinner;

    /* User choice options */
    private String lvl;
    private String range;
    private String school;
    private String classes;
    private String concentration;

    /* Retrofit client creation. */
    Retrofit retrofit = RetrofitClientCreation.getRetrofitCreation();

    /* Retrofit handles the lifting here with my custom interface */
    MyDnDAPI myDnDAPI = retrofit.create(MyDnDAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spell);

        /* Identify the id to each variable. */
        spellLvlFilter = findViewById(R.id.spellLvlFilter);
        spellRangeFilter = findViewById(R.id.spellRangeFilter);
        schoolSpinner = findViewById(R.id.schoolSpinner);
        classesSpinner = findViewById(R.id.classSpinner);
        concentrationSpinner = findViewById(R.id.concentrationSpinner);

        spellLvlFilter.setMinValue(0);
        spellLvlFilter.setMaxValue(10);
        spellLvlFilter.setDisplayedValues(new String[] {"All", "0", "1", "2", "3", "4",
                                                        "5", "6", "7", "8", "9"});

        spellRangeFilter.setMaxValue(0);
        spellRangeFilter.setMaxValue(11);

        /* Specify the ranges supported by spells. */
        spellRangeFilter.setDisplayedValues(new String[] {"All", "1 Mile", "500 Feet", "300 Feet",
                                                        "150 Feet", "120 Feet", "90 Feet", "30 Feet",
                                                        "10 Feet", "Touch", "Self", "Special"});
        /* Setting up the spinner adapters*/
        ArrayAdapter<CharSequence> schoolAdapter = ArrayAdapter
                .createFromResource(this, R.array.schoolNames, android.R.layout.simple_spinner_item);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolSpinner.setAdapter(schoolAdapter);
        schoolSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> classesAdapter = ArrayAdapter
                .createFromResource(this, R.array.classNames, android.R.layout.simple_spinner_item);
        classesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classesSpinner.setAdapter(classesAdapter);
        classesSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> concentrationAdapter = ArrayAdapter
                .createFromResource(this, R.array.concentration, android.R.layout.simple_spinner_item);
        classesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        concentrationSpinner.setAdapter(concentrationAdapter);
        concentrationSpinner.setOnItemSelectedListener(this);

        /* Capture values of the number pickers. */
        lvl = Integer.toString(spellLvlFilter.getValue());
        range = getRangeString(spellRangeFilter.getValue());

        spellLvlFilter.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                lvl = Integer.toString(newVal - 1);
                if (lvl.equals("All")) {
                    lvl = "";
                }
            }
        });

        spellRangeFilter.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                range = getRangeString(newVal);
                if (range.equals("All")) {
                    range = "";
                }
            }
        });

        setTitle("Add Spell");
    }

    /**
     * This is the user filter options for the spinners in which each have a listener.
     * @param parent parent
     * @param view view
     * @param position position in the adapter
     * @param id id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /* Dealing with many spinners, need to know which one has different parameters. */
        switch (parent.getId()) {
            case R.id.schoolSpinner:
                school = parent.getItemAtPosition(position).toString();
                if (school.equals("All")) {
                    school = "";  // Meaning no preference, show all from api request.
                }
                break;
            case R.id.classSpinner:
                classes = parent.getItemAtPosition(position).toString();
                if (classes.equals("All")) {
                    classes = "";
                }
                break;
            case R.id.concentrationSpinner:
                concentration = parent.getItemAtPosition(position).toString();
                if (concentration.equals("Both")) {
                    concentration = "";
                } else if (concentration.equals("Necessary")) {
                    concentration = "yes";
                } else {
                    concentration = "no";
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * Save the spell or spells depending on how retrofit returned.
     * @param spells list of spells waiting to be sent to SpellsFragment.java
     */
    private void saveSpell(ArrayList<SpellEntity> spells) {

        /* Preparing to send the spells to be created. */
        Intent spellData = new Intent();
        spellData.putParcelableArrayListExtra("spells", spells);

        setResult(RESULT_OK, spellData);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_spell_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveSpellIcon:
                makeCall(lvl, school, concentration, classes);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Based on the various filters the user applies, pass it to make api call.
     * @param spellLvl Level of spell needed
     * @param spellSchool which spell school
     * @param concentration true/false present
     * @param spellClass which class of spell
     */
    public void makeCall(String spellLvl, String spellSchool,
                         String concentration, String spellClass) {

        Call<SpellResponse> call = myDnDAPI.getUIFilteredSpells("", spellLvl, spellSchool
                            ,"", "", concentration, "" ,spellClass
                            , "");
        Log.d(TAG, "makeCall: URL --> " + call.request().url());
        call.enqueue(new Callback<SpellResponse>() {
            @Override
            public void onResponse(Call<SpellResponse> call, Response<SpellResponse> response) {

                Log.d(TAG, "onResponse: CODE --> " + response.code());

                SpellResponse spellResponse = response.body();

                saveSpell(spellResponse.getResults());
            }

            @Override
            public void onFailure(Call<SpellResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    /**
     * This method is used for the spinner to see which integer corresponds to which range value.
     * @param position Where in the spinner.
     * @return
     */
    public String getRangeString(int position) {
        switch (position) {
            case 1:
                return "1 Mile";
            case 2:
                return "500 Feet";
            case 3:
                return "300 Feet";
            case 4:
                return "150 Feet";
            case 5:
                return "120 Feet";
            case 6:
                return "90 Feet";
            case 7:
                return "30 Feet";
            case 8:
                return "10 Feet";
            case 9:
                return "Touch";
            case 10:
                return "Self";
            case 11:
                return "Special";
            case 0:             // same as default since 'All' has no parameter.
            default:
                return "";
        }
    }
}

// Array conversion: https://stackoverflow.com/questions/19127071/change-string-array-in-strings-xml-to-arraylist
// Spinner setup help from https://www.youtube.com/watch?v=on_OrrX7Nw4

