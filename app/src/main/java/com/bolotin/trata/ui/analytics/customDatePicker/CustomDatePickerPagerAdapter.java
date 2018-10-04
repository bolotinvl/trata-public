package com.bolotin.trata.ui.analytics.customDatePicker;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bolotin.trata.utils.DateTimeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomDatePickerPagerAdapter extends FragmentPagerAdapter {

    private List<CustomDatePickerFragment> fragments;
    private Date startDate;
    private Date endDate;

    CustomDatePickerPagerAdapter(FragmentManager fragmentManager, Date startDate, Date endDate) {
        super(fragmentManager);
        this.startDate = startDate;
        this.endDate = endDate;
        createFragments();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "FROM " + DateTimeConverter.formatDayOfMonth(startDate);
            case 1:
                return "TO " + DateTimeConverter.formatDayOfMonth(endDate);
            default:
                return "FROM " + DateTimeConverter.formatDayOfMonth(startDate);
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    void updateButtonsState() {
        if (endDate.before(startDate)) {
            fragments.get(1).updateButtonStatus(true);
        } else {
            fragments.get(1).updateButtonStatus(false);
        }
        notifyDataSetChanged();
    }

    private void createFragments() {
        fragments = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        fragments.add(CustomDatePickerFragment.newInstance(true,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)));

        calendar.setTime(endDate);
        fragments.add(CustomDatePickerFragment.newInstance(false,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)));
    }
}