package com.bolotin.trata.ui.analytics.customDatePicker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bolotin.trata.R;
import com.bolotin.trata.di.component.ActivityComponent;
import com.bolotin.trata.ui.base.BaseDialog;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDatePickerDialog extends BaseDialog implements CustomDatePickerMvpView {

    private static final String TAG = "DeleteCategoryDialog";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";

    @Inject
    CustomDatePickerMvpPresenter<CustomDatePickerMvpView> presenter;

    @BindView(R.id.custom_date_picker_tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.custom_date_picker_viewpager)
    ViewPager viewPager;

    CustomDatePickerPagerAdapter pagerAdapter;
    OnDateChangedListener listener;
    Date startDate;
    Date endDate;

    public static CustomDatePickerDialog newInstance(Date startDate, Date endDate) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(START_DATE, startDate);
        bundle.putSerializable(END_DATE, endDate);
        CustomDatePickerDialog dialog = new CustomDatePickerDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public interface OnDateChangedListener {
        void updateDate(Date startDate, Date endDate);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_date_picker_dialog, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            presenter.onAttach(this);

            setUp(view);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            startDate = (Date) bundle.getSerializable(START_DATE);
            endDate = (Date) bundle.getSerializable(END_DATE);
        }
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pagerAdapter = new CustomDatePickerPagerAdapter(getChildFragmentManager(), startDate, endDate);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnDateChangedListener) context;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public void onCancelClick() {
        dismiss();
    }

    public void onOkClick() {
        if (viewPager.getCurrentItem() == 0) {
            viewPager.setCurrentItem(1);
            return;
        }
        listener.updateDate(pagerAdapter.getStartDate(), pagerAdapter.getEndDate());
        dismiss();
    }

    public void onDateClick(int year, int month, int dayOfMonth) {
        if (viewPager.getCurrentItem() == 0) {
            startDate = presenter.getDate(year, month, dayOfMonth);
            pagerAdapter.setStartDate(startDate);
            pagerAdapter.updateButtonsState();
            viewPager.setCurrentItem(1);

        } else {
            endDate = presenter.getDate(year, month, dayOfMonth);
            pagerAdapter.setEndDate(endDate);
            pagerAdapter.updateButtonsState();
        }
    }
}