package com.example.dndsorcerapp.DatabaseTools;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SpellViewModel extends AndroidViewModel {

    private SpellRepository spellRepository;
    private LiveData<List<SpellEntity>> allSpells;

    public SpellViewModel(@NonNull Application application) {
        super(application);
        spellRepository = new SpellRepository(application);
        allSpells = spellRepository.getAllSpells();
    }

    public void insert(SpellEntity spellEntity) {
        spellRepository.insert(spellEntity);
    }

    public void update(SpellEntity spellEntity) {
        spellRepository.update(spellEntity);
    }

    public void delete(SpellEntity spellEntity) {
        spellRepository.delete(spellEntity);
    }

    public void deleteAllSpells() {
        spellRepository.deleteAllSpells();
    }

    public LiveData<List<SpellEntity>> getAllSpells() {
        return allSpells;
    }
}
