package com.bolotin.trata.ui.analytics.customDatePicker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.di.component.ActivityComponent;
import com.bolotin.trata.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDatePickerFragment extends BaseFragment {
    
    private static final String IS_FIRST_FRAGMENT = "isFirstFragment";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY_OF_MONTH = "dayOfMonth";

    @BindView(R.id.custom_date_picker)
    DatePicker datePicker;

    @BindView(R.id.custom_date_picker_ok_button)
    TextView okButton;

    CustomDatePickerDialog parentDialog;

    private boolean isOkDisabled;
    private int year;
    private int month;
    private int dayOfMonth;

    public static CustomDatePickerFragment newInstance(boolean isFirstFragment, int year, int month, int dayOfMonth) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_FIRST_FRAGMENT, isFirstFragment);
        bundle.putInt(YEAR, year);
        bundle.putInt(MONTH, month);
        bundle.putInt(DAY_OF_MONTH, dayOfMonth);
        CustomDatePickerFragment fragment = new CustomDatePickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_date_picker_fragment, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            setUnBinder(ButterKnife.bind(this, view));
            setUp(view);
        }

        return view;
    }

    public void updateButtonStatus(boolean state) {
        isOkDisabled = state;
        if (isOkDisabled) {
            okButton.setTextColor(getResources().getColor(R.color.editTransactionTextColor));
        } else {
            okButton.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    protected void setUp(View view) {
        parentDialog = (CustomDatePickerDialog) getParentFragment();
        Bundle bundle = getArguments();
        if (bundle != null) {
            boolean isFirstFragment = bundle.getBoolean(IS_FIRST_FRAGMENT);
            year = bundle.getInt(YEAR);
            month = bundle.getInt(MONTH);
            dayOfMonth = bundle.getInt(DAY_OF_MONTH);

            if (isFirstFragment) {
                okButton.setText(R.string.custom_date_picker_next_button);
            }

            datePicker.init(year, month, dayOfMonth, (datePicker, year, month, dayOfMonth) -> {
                this.year = year;
                this.month = month;
                this.dayOfMonth = dayOfMonth;

                parentDialog.onDateClick(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            });
        }
    }

    @OnClick(R.id.custom_date_picker_cancel_button)
    void onCancelButtonClick() {
        parentDialog.onCancelClick();
    }

    @OnClick(R.id.custom_date_picker_ok_button)
    void onOkButtonClick() {
        if (!isOkDisabled) {
            parentDialog.onOkClick();
        }
    }
}
