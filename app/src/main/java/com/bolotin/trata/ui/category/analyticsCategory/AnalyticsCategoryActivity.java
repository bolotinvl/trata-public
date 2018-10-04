package com.bolotin.trata.ui.category.analyticsCategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.analytics.category.CategoryAnalyticsActivity;
import com.bolotin.trata.ui.category.AbstractCategoryActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AnalyticsCategoryActivity extends AbstractCategoryActivity {

    @BindView(R.id.category_edit_button)
    TextView editButton;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AnalyticsCategoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp(toolbar);
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        super.setUp(toolbar);

        editButton.setVisibility(View.GONE);
        adapter.setArrowsVisible();
    }

    @OnClick(R.id.category_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @Override
    public void onClicked(View v, int position) {
        Intent intent = CategoryAnalyticsActivity.getStartIntent(this);
        intent.putExtra("categoryId", adapter.getCategoryId(position));
        startActivity(intent);
    }
}