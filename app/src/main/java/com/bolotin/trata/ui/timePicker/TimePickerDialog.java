package com.bolotin.trata.ui.timePicker;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import com.bolotin.trata.R;
import com.bolotin.trata.listeners.DateTimePickerListener;
import com.bolotin.trata.ui.base.BaseDialog;

public class TimePickerDialog extends BaseDialog implements android.app.TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "TimePickerDialog";

    private android.app.TimePickerDialog timePickerDialog;
    private DateTimePickerListener dateTimePickerListener;

    public static TimePickerDialog newInstance() {
        return new TimePickerDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setUp(getView());
        return timePickerDialog;
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int hours = bundle.getInt("hours");
            int minutes = bundle.getInt("minutes");
            timePickerDialog = new android.app.TimePickerDialog(getActivity(),
                    R.style.DateTimePicker,
                    this,
                    hours,
                    minutes,
                    DateFormat.is24HourFormat(getActivity()));
        }
        dateTimePickerListener = (DateTimePickerListener) getActivity();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        dateTimePickerListener.onTimeSet(hourOfDay, minute);

    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }
}