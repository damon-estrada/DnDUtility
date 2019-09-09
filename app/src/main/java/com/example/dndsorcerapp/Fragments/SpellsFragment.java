package com.example.dndsorcerapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.dndsorcerapp.MyDnDAPI;
import com.example.dndsorcerapp.R;
import com.example.dndsorcerapp.RetrofitClientCreation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SpellsFragment extends Fragment {

    private static final String TAG = SpellsFragment.class.getSimpleName();

    private List<String> spellsToCreate;
    private ViewPager mViewPager;
    private ViewPager bViewPager;
    private FragmentAdapter fStateAdapter;
    private SpellBackAdapter bStateAdapter;

    Retrofit retrofit = RetrofitClientCreation.getRetrofitCreation();

    /* Retrofit handles the lifting here */
    MyDnDAPI myDnDAPI = retrofit.create(MyDnDAPI.class);

    public int layoutNeeded;

    /**
     * On Constructor creation, we will pass what spell we want to call and
     * generate the correct spell. This is solely to populate the arraylist
     * and will not be added to the ViewPager.
     * @param spellsToCreate What spells the user had previously.
     */
    public SpellsFragment(List<String> spellsToCreate) {
        this.spellsToCreate = new ArrayList<>();
        for (int i = 0; i < spellsToCreate.size(); i++)
            this.spellsToCreate.add(spellsToCreate.get(i));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflaterView = inflater.inflate(R.layout.fragment_spell, container, false);
        setHasOptionsMenu(true);

        /* Set toolbar title */
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Spells");

        fStateAdapter = new FragmentAdapter(getFragmentManager());
        bStateAdapter = new SpellBackAdapter(getFragmentManager());
        mViewPager = (ViewPager) inflaterView.findViewById(R.id.frontOfSpell);
        bViewPager = (ViewPager) inflaterView.findViewById(R.id.backOfSpell);

        for (int i = 0; i < spellsToCreate.size(); i++) {
            addToViewPager(i, fStateAdapter);
        }

        mViewPager.setAdapter(fStateAdapter);
        //bViewPager.setAdapter(bStateAdapter);

        /* Getting creative with the view pager transformer */
        //mViewPager.setPageTransformer(false, new DepthPageTransformer());
        mViewPager.setPageTransformer(false, new DepthPageTransformer());
        return inflaterView;
    }

    private void addToViewPager(int fragmentNum, FragmentAdapter fStateAdapter) {

        /* Choose the correct spell card needed for the current spell call */
        chooseSpellCardLayout(spellsToCreate.get(fragmentNum));

        Log.d(TAG, "addToViewPager: LAYOUT NEEDED: " + layoutNeeded);

        SpellFrontFragment tmp = new SpellFrontFragment(R.layout.dnd_spell_card, spellsToCreate.get(fragmentNum));
        //SpellBackFragment back = new SpellBackFragment(R.layout.dnd_spell_card_back);

        fStateAdapter.addFragment(tmp, "Fragment Front " + fragmentNum);
        //bStateAdapter.addFragment(back, "Fragment Back " + fragmentNum);
    }

    public void chooseSpellCardLayout(String spellIndex) {

        Call<Spell> call = myDnDAPI.getSpell(spellIndex);

        call.enqueue(new Callback<Spell>() {

            @Override
            public void onResponse(Call<Spell> call, Response<Spell> response) {

                Spell spell = response.body();

                if ( !(spell.getMaterial() == null) ) {
                    Log.d(TAG, "onResponse: HAS MATERIAL");
                    //layoutId(R.layout.dnd_spell_card);

                } else {
                    Log.d(TAG, "onResponse: DOES NOT HAVE MATERIAL");
                    //layoutId(R.layout.dnd_spell_card);
                }
            }

            @Override
            public void onFailure(Call<Spell> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    /**
     * This Will create the options icon and menu panel in the top right corner.
     * @param menu The menu drop down
     * @param inflater the view inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.spells_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Add or Remove a spell option bar and what happens on selected
     * @param item The option being selected
     * @return true or false on what was chosen.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addSpell:
                Toast.makeText(getContext(), "Add Spell", Toast.LENGTH_SHORT).show();
                SpellFrontFragment tmp = new SpellFrontFragment(R.layout.dnd_spell_card, spellsToCreate.get(0));

                fStateAdapter.addFragment(tmp, "new spell");
                return true;
            case R.id.removeSpell:
                Toast.makeText(getContext(), "Remove Spell", Toast.LENGTH_SHORT).show();
                fStateAdapter.deleteFragment(mViewPager.getCurrentItem());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Provided by google
     */
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}
