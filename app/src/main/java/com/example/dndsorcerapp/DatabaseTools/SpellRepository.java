package com.example.dndsorcerapp.DatabaseTools;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SpellRepository {

    private SpellDao spellDao;
    private LiveData<List<SpellEntity>> allSpells;

    public SpellRepository(Application application) {
        SpellDatabase database = SpellDatabase.getInstance(application);
        spellDao = database.spellDao();
        allSpells = spellDao.getAllSpells();
    }

    /* This is the API that Repository will expose to outside models. */
    public void insert(SpellEntity spellEntity) {
        new InsertSpellAsyncTask(spellDao).execute(spellEntity);

    }

    public void update(SpellEntity spellEntity) {
        new UpdateSpellAsyncTask(spellDao).execute(spellEntity);

    }

    public void delete(SpellEntity spellEntity) {
        new DeleteSpellAsyncTask(spellDao).execute(spellEntity);
    }

    public void deleteAllSpells() {
        new DeleteAllSpellAsyncTask(spellDao).execute();
    }

    public LiveData<List<SpellEntity>> getAllSpells() {
        return allSpells;
    }

    private static class InsertSpellAsyncTask extends AsyncTask<SpellEntity, Void, Void> {

        private SpellDao spellDao;

        private InsertSpellAsyncTask(SpellDao spellDao) {
            this.spellDao = spellDao;
        }

        @Override
        protected Void doInBackground(SpellEntity... spellEntities) {
            spellDao.insert(spellEntities[0]);
            return null;
        }
    }

    private static class UpdateSpellAsyncTask extends AsyncTask<SpellEntity, Void, Void> {

        private SpellDao spellDao;

        private UpdateSpellAsyncTask(SpellDao spellDao) {
            this.spellDao = spellDao;
        }

        @Override
        protected Void doInBackground(SpellEntity... spellEntities) {
            spellDao.update(spellEntities[0]);
            return null;
        }
    }

    private static class DeleteSpellAsyncTask extends AsyncTask<SpellEntity, Void, Void> {

        private SpellDao spellDao;

        private DeleteSpellAsyncTask(SpellDao spellDao) {
            this.spellDao = spellDao;
        }

        @Override
        protected Void doInBackground(SpellEntity... spellEntities) {
            spellDao.delete(spellEntities[0]);
            return null;
        }
    }

    private static class DeleteAllSpellAsyncTask extends AsyncTask<Void, Void, Void> {

        private SpellDao spellDao;

        private DeleteAllSpellAsyncTask(SpellDao spellDao) {
            this.spellDao = spellDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            spellDao.deleteAllSpells();
            return null;
        }
    }
}
