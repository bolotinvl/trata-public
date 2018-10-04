package com.bolotin.trata.ui.datePicker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.DatePicker;

import com.bolotin.trata.R;
import com.bolotin.trata.listeners.DateTimePickerListener;
import com.bolotin.trata.ui.base.BaseDialog;

public class DatePickerDialog extends BaseDialog implements android.app.DatePickerDialog.OnDateSetListener {

    private static final String TAG = "DatePickerDialog";

    protected android.app.DatePickerDialog datePickerDialog;
    private DateTimePickerListener dateTimePickerListener;
    private int year;
    private int month;
    private int dayOfMonth;

    public static DatePickerDialog newInstance() {
        return new DatePickerDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setUp(getView());
        return datePickerDialog;
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        if (bundle != null && getActivity() != null) {
            int year = bundle.getInt("year");
            int month = bundle.getInt("month");
            int dayOfMonth = bundle.getInt("dayOfMonth");
            datePickerDialog = new android.app.DatePickerDialog(getActivity(),
                    R.style.DateTimePicker,
                    this,
                    year,
                    month,
                    dayOfMonth);
            String negative = bundle.getString("negative");
            String positive = bundle.getString("positive");
            if (negative != null && positive != null) {
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negative, datePickerDialog);
                datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, positive, datePickerDialog);
            }
        }
        dateTimePickerListener = (DateTimePickerListener) getActivity();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateTimePickerListener.onDateSet(year, month, dayOfMonth);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }
}