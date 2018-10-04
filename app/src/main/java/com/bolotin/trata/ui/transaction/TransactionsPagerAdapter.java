package com.bolotin.trata.ui.transaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bolotin.trata.ui.transaction.transactionsFragment.TransactionsFragment;

import java.util.ArrayList;
import java.util.List;

public class TransactionsPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> months;
    private List<TransactionsFragment> fragments;

    public TransactionsPagerAdapter(FragmentManager fragmentManager, List<String> months) {
        super(fragmentManager);
        this.months = months;
        createFragments();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return months.get(position);
    }

    void setMonths(List<String> months) {
        if (this.months.containsAll(months) && months.containsAll(this.months)) {
            return;
        }

        if (this.months.size() > months.size()) {
            List<String> oldMonths = new ArrayList<>(this.months);
            oldMonths.removeAll(months);
            for (String removedMonth : oldMonths) {
                removeFragment(this.months.indexOf(removedMonth));
            }
        }

        if (this.months.size() < months.size()) {
            List<String> newMonths = new ArrayList<>(months);
            newMonths.removeAll(this.months);
            for (String addedMonths : newMonths) {
                addFragment(months.indexOf(addedMonths), addedMonths);
            }
        }

        this.months = months;
        notifyDataSetChanged();
    }

    int getMonthPosition(String month) {
        return months.indexOf(month);
    }

    private void createFragments() {
        fragments = new ArrayList<>();
        for (String month : months) {
            fragments.add(TransactionsFragment.newInstance(month));
        }
    }

    private void removeFragment(int position) {
        if (position <= fragments.size()) {
            fragments.remove(position);
        }
    }

    private void addFragment(int position, String month) {
        fragments.add(position, TransactionsFragment.newInstance(month));
    }
}