package com.bolotin.trata.ui.category.settingsCategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.category.AbstractCategoryActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsCategoryActivity extends AbstractCategoryActivity {

    @BindView(R.id.category_title)
    TextView title;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SettingsCategoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp(toolbar);
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        super.setUp(toolbar);

        title.setText(R.string.settings_categories);
    }

    @Override
    public void onClicked(View v, int position) {
        if (actionMode != null) {
            toggleSelection(position);
            actionMode.invalidate();
        }
    }

    @Override
    public void onLongClicked(View v, int position) {
        if (actionMode != null) {
            toggleSelection(position);
            actionMode.invalidate();
            return;
        }

        actionMode = startSupportActionMode(this);
        adapter.setActionModeState(true);
        toggleSelection(position);
        actionMode.invalidate();
    }

    @OnClick(R.id.category_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }
}