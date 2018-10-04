package com.bolotin.trata.listeners;

import android.view.View;

public interface OnClickListener {

    void onClicked(View v, int position);

    default void onLongClicked(View v, int position) {
    }
}
