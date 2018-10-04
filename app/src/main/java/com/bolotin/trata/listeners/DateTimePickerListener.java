package com.bolotin.trata.listeners;

public interface DateTimePickerListener {

    void onDateSet(int year, int month, int day);

    default void onTimeSet(int hours, int minutes) {
    }
}
