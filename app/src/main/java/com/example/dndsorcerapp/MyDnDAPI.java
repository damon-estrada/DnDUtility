package com.example.dndsorcerapp;

import com.example.dndsorcerapp.Fragments.Spell;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyDnDAPI {

    /**********
     * Spells *
     **********/
    @GET("spells/")
    Call<List<Spell>> getAllSpellsList(); // Get data from the server

    @GET("spells/{number}/")
    Call<Spell> getSpell(@Path("number") String spellNum);

}
