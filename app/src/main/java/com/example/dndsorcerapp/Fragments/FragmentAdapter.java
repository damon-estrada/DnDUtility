package com.example.dndsorcerapp.Fragments;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    /* This list knows how many spells the user has that is relayed
    *  from the database. */
    private List<SpellCardCreation> mFragmentList = new ArrayList<>();

    public void setFragmentList(List<SpellCardCreation> mFragmentList) {
        this.mFragmentList = mFragmentList;
        notifyDataSetChanged();
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    /**
     * This is called after deleteFragment (when notifyDataSetChanged() is called ).
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
