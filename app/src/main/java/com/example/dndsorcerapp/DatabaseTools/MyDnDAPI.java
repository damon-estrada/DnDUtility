package com.example.dndsorcerapp.DatabaseTools;

import com.example.dndsorcerapp.Responses.SpellResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyDnDAPI {

    /********************************
     * All spells based on category *
     ********************************/
    @GET("spells/")
    Call<List<SpellEntity>> getAllSpellsList(); // Get data from the server

    @GET("spells/{level}")
    Call<List<SpellEntity>> getAllSpellsWithLevel(@Path("level") String spellLevel);

    @GET("spells/{school}")
    Call<List<SpellEntity>> getAllSpellsWithSchool(@Path("school") String spellSchool);

    @GET("spells/{class}")
    Call<List<SpellEntity>> getAllSpellsWithClass(@Path("class") String spellClass);

    @GET("spells/{concentration}")
    Call<List<SpellEntity>> getAllSpellsWithConcentration(@Path("concentration") String concentration);

    @GET("spells/{range}")
    Call<List<SpellEntity>> getAllSpellsWithRange(@Path("range") String spellRange);

    /**************************
     * Single instance spells *
     **************************/
    @GET("spells/{name}")
    Call<SpellEntity> getSpellName(@Path("name") String spellName);

    /***************************
     * Various filtered spells *
     ***************************/
    @GET("spells/")
    Call<SpellResponse> getUIFilteredSpells(@Query("level") String lvl,
                                            @Query("level_int") String lvlInt,
                                            @Query("school") String spellSchool,
                                            @Query("duration") String duration,
                                            @Query("components") String components,
                                            @Query("concentration") String concentration,
                                            @Query("casting_time") String castingTime,
                                            @Query("dnd_class") String spellClass,
                                            @Query("document__slug") String docSlug);
}
