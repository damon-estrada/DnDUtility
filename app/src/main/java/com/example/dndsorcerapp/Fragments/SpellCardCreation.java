package com.example.dndsorcerapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dndsorcerapp.R;
import com.example.dndsorcerapp.DatabaseTools.SpellEntity;

public class SpellCardCreation extends Fragment {

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

    private SpellEntity spell;

    public SpellCardCreation (int layoutId, SpellEntity spell) {
        this.layoutId = layoutId;
        this.spell = spell;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflaterView = inflater.inflate(R.layout.dnd_spell_card, container, false);
        //assignCorrectLayout(inflater, container, layoutId);

        System.out.println("VIEW: " + inflaterView);

        spellName = inflaterView.findViewById(R.id.spellName);
        spellName = inflaterView.findViewById(R.id.spellName);
        spellLvl = inflaterView.findViewById(R.id.spellLvl);
        castingTime = inflaterView.findViewById(R.id.castingTime);
        range = inflaterView.findViewById(R.id.range);
        components = inflaterView.findViewById(R.id.components);
        duration = inflaterView.findViewById(R.id.duration);
        spellDesc = inflaterView.findViewById(R.id.spellDesc);
        material = inflaterView.findViewById(R.id.material);
        higherLvlDesc = inflaterView.findViewById(R.id.higherLvlDesc);

        /* update the spell card */
        spellName.setText(spell.getName());

        /* Spell Level */
        spellLvl.setText(spell.getLevel());

        /* Casting time */
        castingTime.setText(spell.getCastingTime());

        /* Range */
        range.setText(spell.getRange());

        /* components */
        components.setText(spell.getComponents());


        /* Duration */
        duration.setText(spell.getDuration());

        /* Material needed */
        if ( !(spell.getMaterial() == null) ) {
            material.setText(spell.getMaterial());
        }

        /* Spell description */
        spellDesc.setText(spell.getDesc());

        /* At higher levels description */
        higherLvlDesc.setText(spell.getDescHigherLvl());

        return inflaterView;
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
