package com.example.dndsorcerapp.DatabaseTools;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SpellEntity.class}, version = 1)
public abstract class SpellDatabase extends RoomDatabase {

    private static SpellDatabase instance;

    /* To access Dao, Room will take care of this.*/
    public abstract SpellDao spellDao();

    public static synchronized SpellDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SpellDatabase.class, "spell_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitDbAsyncTask(instance).execute();
        }
    };

    private static class InitDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private SpellDao spellDao;

        private InitDbAsyncTask(SpellDatabase spellDatabase) {
            spellDao = spellDatabase.spellDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /* Initial card creation in db */
            spellDao.insert(new SpellEntity(
                    1, "slug", "name", "Hello! Thank you for downloading my app!" +
                    "Please email me if you have any more ideas for the app and future improvements." +
                    "Delete this temporarily spell card and start adding some spells!",
                    "","page number", "range value", "components",
                    "materials", "ritual", "duration", "true/false",
                    "casting time", "spell level", "spell level int",
                    "school","dnd class", "archetype", "circles",
                    "doc slug", "doc title", "doc url"
            ));
            return null;
        }
    }
}
