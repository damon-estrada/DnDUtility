/**
 * An interface that Room will populate.
 */

package com.example.dndsorcerapp.DatabaseTools;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SpellDao {

    @Insert
    void insert(SpellEntity spellEntity);

    @Update
    void update(SpellEntity spellEntity);

    @Delete
    void delete(SpellEntity spellEntity);

    @Query("DELETE FROM spell_table")
    void deleteAllSpells();

    @Query("SELECT * FROM spell_table ORDER BY priority DESC")
    LiveData<List<SpellEntity>> getAllSpells();
}
