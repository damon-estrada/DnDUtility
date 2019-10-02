package com.example.dndsorcerapp.Fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dndsorcerapp.MainActivity;
import com.example.dndsorcerapp.MyDnDAPI;
import com.example.dndsorcerapp.R;
import com.example.dndsorcerapp.RetrofitClientCreation;
import com.example.dndsorcerapp.SpellEntity;
import com.example.dndsorcerapp.SpellViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class SpellFrontFragment extends Fragment {

    Retrofit retrofit = RetrofitClientCreation.getRetrofitCreation();

    /* Retrofit handles the lifting here */
    MyDnDAPI myDnDAPI = retrofit.create(MyDnDAPI.class);

    /* Text Views */
    TextView spellName;
    TextView spellLvl;
    TextView castingTime;
    TextView range;
    TextView components;
    TextView duration;
    TextView spellDesc;
    TextView material;
    TextView higherLvlDesc;

    View inflaterView;

    public int layoutId;

    private String spellToCreate;
    private List<SpellEntity> spells = new ArrayList<>();


    public SpellFrontFragment(int layoutId, String spellToCreate) {
        this.layoutId = layoutId;
        this.spellToCreate = spellToCreate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflaterView = inflater.inflate(R.layout.dnd_spell_card, container, false);
        //assignCorrectLayout(inflater, container, layoutId);

        System.out.println("VIEW: " + inflaterView);



        makeCall(inflaterView, spellToCreate);

        return inflaterView;
    }

    public void makeCall(View inflaterView, String spellIndex) {

        Call<Spell> call = myDnDAPI.getSpell(spellIndex);

        call.enqueue(new Callback<Spell>() {
            @Override
            public void onResponse(Call<Spell> call, Response<Spell> response) {

                Spell spell = response.body();

                buildSpellCard(spell, inflaterView);
            }

            @Override
            public void onFailure(Call<Spell> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    /**
     * BuildSpellCard() will assign all the spell card's attributes (TextViews).
     * @param spell Spell object which holds the spell attributes.
     * @param view Refering to the view it'll be placed (inflaterView).
     */
    public void buildSpellCard(Spell spell, View view) {

        StringBuilder formatter = new StringBuilder();

        spellName = view.findViewById(R.id.spellName);
        spellLvl = view.findViewById(R.id.spellLvl);
        castingTime = view.findViewById(R.id.castingTime);
        range = view.findViewById(R.id.range);
        components = view.findViewById(R.id.components);
        duration = view.findViewById(R.id.duration);
        spellDesc = view.findViewById(R.id.spellDesc);
        material = view.findViewById(R.id.material);
        higherLvlDesc = view.findViewById(R.id.higherLvlDesc);

        /* update the spell card */
        spellName.setText(spell.getName());

        /* Spell Level */
        formatter.append(formatLvl(spell.getLevel(), spell.getSchool().getName()));
        spellLvl.setText(formatter);

        formatter.setLength(0); // clear

        /* Casting time */
        castingTime.setText(spell.getCastingTime());

        /* Range */
        range.setText(spell.getRange());

        /* components */
        for (int i = 0; i < spell.getComponents().size(); i++) {
            formatter.append(spell.getComponents().get(i));
            if (i != (spell.getComponents().size()) - 1) {
                formatter.append(", ");  // do not append to the last component
            }
        }

        components.setText(formatter);

        formatter.setLength(0);

        /* Duration */
        duration.setText(spell.getDuration());

        /* Material needed */
        if ( !(spell.getMaterial() == null) ) {
            formatter.append("Material: ");
            formatter.append(spell.getMaterial());
            material.setText(formatter);
        }

        formatter.setLength(0);

        /* Spell description */
        for (int i = 0; i < spell.getDesc().size(); i++) {
            formatter.append(spell.getDesc().get(i));
            formatter.append("\n\n");
        }

        spellDesc.setText(formatter);

        formatter.setLength(0);

        /* At higher levels description */
        for (int i = 0; i < spell.getDescHigherLvl().size(); i++) {
            formatter.append(spell.getDescHigherLvl().get(i));
        }

        higherLvlDesc.setText(formatter);
    }

    /**
     * This method will append the proper tense of the word based on the spell level
     * @param lvl Spell level as a number
     * @param school What school does the spell belong to.
     * @return String with the appended tense.
     */
    public String formatLvl(String lvl, String school) {
        switch (lvl.charAt(0)) {
            case '1':
                return "1st-level " + school;
            case '2':
                return "2nd-level " + school;
            case '3':
                return "3rd-level " + school;
            default:
                return lvl + "th-level " + school;
        }
    }

    /**
     * This method is intended to chose a layout of spell card based on if it has certain traits.
     * For example, a spell with no required materials should use a layout that does not have
     * that parameter, etc.
     * @param inflater
     * @param container
     * @param layoutId
     */
    public void assignCorrectLayout(LayoutInflater inflater, ViewGroup container, int layoutId) {
        inflaterView = inflater.inflate(layoutId, container, false);
    }
}
