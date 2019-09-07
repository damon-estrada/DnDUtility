package com.example.dndsorcerapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private FragmentAdapter fStateAdapter;

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


        fStateAdapter = new FragmentAdapter(getFragmentManager());
        mViewPager = (ViewPager) inflaterView.findViewById(R.id.container);

        for (int i = 0; i < spellsToCreate.size(); i++) {
            addToViewPager(mViewPager, i, fStateAdapter);
        }

        /* Getting creative with the view pager transformer */
        //mViewPager.setPageTransformer(false, new DepthPageTransformer());
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setTranslationX(-position*page.getWidth());



                if (position<-1){    // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0);

                }
                else if (position<=0){    // [-1,0]
                    page.setAlpha(1);
                    page.setPivotX(0);
                    page.setRotationY(90*Math.abs(position));

                }
                else if (position <=1){    // (0,1]
                    page.setAlpha(1);
                    page.setPivotX(page.getWidth());
                    page.setRotationY(-90*Math.abs(position));

                }else {    // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0);

                }
            }
        });

        return inflaterView;
    }

    private void addToViewPager(ViewPager viewPager, int fragmentNum, FragmentAdapter fStateAdapter) {

        /* Choose the correct spell card needed for the current spell call */
        chooseSpellCardLayout(spellsToCreate.get(fragmentNum));

        Log.d(TAG, "addToViewPager: LAYOUT NEEDED: " + layoutNeeded);

        SpellFragmentHelper tmp = new SpellFragmentHelper(R.layout.dnd_spell_card, spellsToCreate.get(fragmentNum));

        fStateAdapter.addFragment(tmp, "Fragment " + fragmentNum);

        viewPager.setAdapter(fStateAdapter);
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
