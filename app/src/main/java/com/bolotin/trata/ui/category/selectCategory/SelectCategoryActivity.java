package com.bolotin.trata.ui.category.selectCategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.transaction.TransactionsActivity;
import com.bolotin.trata.ui.category.AbstractCategoryActivity;

import butterknife.OnClick;

public class SelectCategoryActivity extends AbstractCategoryActivity {

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SelectCategoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp(toolbar);
    }

    @Override
    public void onBackPressed() {
        if (wasCategoryDeleted) {
            startActivity(TransactionsActivity.getStartIntent(this));
        }
        super.onBackPressed();
    }

    @OnClick(R.id.category_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @Override
    public void onClicked(View v, int position) {
        if (actionMode != null) {
            toggleSelection(position);
            actionMode.invalidate();
            return;
        }

        String categoryId = adapter.getCategoryId(position);
        setResult(RESULT_OK, new Intent().putExtra("categoryId", categoryId));
        onBackPressed();
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
}
