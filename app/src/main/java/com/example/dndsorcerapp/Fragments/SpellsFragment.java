package com.example.dndsorcerapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.dndsorcerapp.Network_Calls.AddSpell;
import com.example.dndsorcerapp.R;
import com.example.dndsorcerapp.DatabaseTools.SpellEntity;
import com.example.dndsorcerapp.DatabaseTools.SpellViewModel;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * SpellFragment Model Object
 *
 * <P>This object keeps track of all the routines for adding/removing spells
 * to the users view.
 *
 * @author Damon Estrada
 * @version 1.0
 * @since   2019-09-07
 */
public class SpellsFragment extends Fragment {

    private static final String TAG = SpellsFragment.class.getSimpleName();
    public static final int ADD_SPELL_REQUEST = 1;
    private ViewPager mViewPager;
    private FragmentAdapter fStateAdapter;
    private List<SpellCardCreation> spellFragments;
    private PageIndicatorView pageIndicatorView;

    /* Here is where I can access the database of spells */
    private SpellViewModel spellViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflaterView = inflater.inflate(R.layout.fragment_spell, container, false);
        setHasOptionsMenu(true);

        /* Set toolbar title */
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Spells");

        /* The system knows when to create a new instance of the ViewModel.
            Android will destroy this model when the activity is not in use anymore. */
        fStateAdapter = new FragmentAdapter(getFragmentManager());
        mViewPager = (ViewPager) inflaterView.findViewById(R.id.frontOfSpell);
        spellFragments = new ArrayList<>();
        pageIndicatorView = (PageIndicatorView) inflaterView.findViewById(R.id.pageIndicatorView);

        /* Observe what the database has */
        spellViewModel = ViewModelProviders.of(this).get(SpellViewModel.class);
        spellViewModel.getAllSpells().observe(this, new Observer<List<SpellEntity>>() {

            /**
             * This will be triggered every time the live data changes. Will update the spells.
             * @param spellEntities
             */
            @Override
            public void onChanged(List<SpellEntity> spellEntities) {

                /* Clear the fragment so we don't have stragglers from last pull. */
                spellFragments.clear();

                /* So whenever the user deletes or adds a spell, the list should update on
                *  what spells are still active.
                *  Add the spells when the user goes to the spells category.
                */
                for (int i = 0; i < spellViewModel.getAllSpells().getValue().size(); i++) {
                    Log.d(TAG, "onChanged: SPELLS IN DATA BASE: " + spellViewModel
                            .getAllSpells().getValue().size());
                    SpellCardCreation newSpell = new SpellCardCreation(R.layout.dnd_spell_card,
                            spellViewModel.getAllSpells().getValue().get(i));

                    spellFragments.add(newSpell);
                }

                fStateAdapter.setFragmentList(spellFragments);
            }
        });

        mViewPager.setAdapter(fStateAdapter);

        /* Getting creative with the view pager transformer */
        mViewPager.setPageTransformer(false, new DepthPageTransformer());

        /* Page Indicator View animation. */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
                pageIndicatorView.setAnimationType(AnimationType.WORM);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return inflaterView;
    }

    /**
     * This is called when we return from the AddSpell.java activity. We process the object passed
     * and then create a new or several spells which are then added to the adapter and tracked.
     * @param requestCode coming from the correct activity (AddSpell.java)
     * @param resultCode if our resulting object passed was successful.
     * @param data the actual object passed.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: Inside");

        /* This corresponds to the addSpell.java spells that will be created when adding spell(s) */
        if (requestCode == ADD_SPELL_REQUEST && resultCode == RESULT_OK) {

            ArrayList<SpellEntity> spells = data.getParcelableArrayListExtra("spells");

            for (int i = 0; i < spells.size(); i++) {
                Log.d(TAG, "onActivityResult: Spell details: " + spells.get(i).getDescHigherLvl());
                SpellEntity newSpell = new SpellEntity(
                        spells.get(i).getPriority(), spells.get(i).getSlug(), spells.get(i).getName(),
                        spells.get(i).getDesc(), spells.get(i).getDescHigherLvl(), spells.get(i).getPage(),
                        spells.get(i).getRange(), spells.get(i).getComponents(), spells.get(i).getMaterial(),
                        spells.get(i).getDescHigherLvl(), spells.get(i).getDuration(),
                        spells.get(i).getConcentration(), spells.get(i).getCastingTime(),
                        spells.get(i).getLevel() + " " + spells.get(i).getSchool(),
                        spells.get(i).getLevelInt(), spells.get(i).getSchool(), spells.get(i).getDndClass(),
                        spells.get(i).getArchetype(), spells.get(i).getCircles(),
                        spells.get(i).getDocument__slug(), spells.get(i).getDocument__title(),
                        spells.get(i).getDocument__license_url());

                spellViewModel.insert(newSpell);
            }


            Toast.makeText(getContext(), "Spell Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Spell Added Error", Toast.LENGTH_SHORT).show();
        }
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
     * Add or Remove a spell option bar and what happens on selected (removed from db).
     * @param item The option being selected
     * @return true or false on what was chosen.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addSpell:

                Intent intent = new Intent(getContext(), AddSpell.class);
                startActivityForResult(intent, ADD_SPELL_REQUEST);

                return true;
            case R.id.removeSpell:
                int spellLocationInAdapter = mViewPager.getCurrentItem();

                /* Remove spell from db */
                if (spellViewModel.getAllSpells().getValue().size() > 0) {
                    spellViewModel.delete(spellViewModel.getAllSpells().
                            getValue().
                            get(spellLocationInAdapter));


                    Toast.makeText(getContext(), "Spell Removed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "The beginning of love is a horror of emptiness." +
                            " -Robert Bly", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.removeAllSpells:

                /* Remove all spells from db */
                if (spellViewModel.getAllSpells().getValue().size() > 0) {
                    for (int i = 0; i < spellViewModel.getAllSpells().getValue().size(); i++) {
                        spellViewModel.delete(spellViewModel.getAllSpells().getValue().get(i));
                    }

                    Toast.makeText(getContext(), "All Spells Removed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "The beginning of love is a horror of emptiness." +
                            " -Robert Bly", Toast.LENGTH_SHORT).show();
                }
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