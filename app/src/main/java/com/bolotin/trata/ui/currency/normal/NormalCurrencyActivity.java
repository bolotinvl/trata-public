package com.bolotin.trata.ui.currency.normal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.currency.AbstractCurrencyActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class NormalCurrencyActivity extends AbstractCurrencyActivity {

    @BindView(R.id.currency_title)
    TextView title;

    @BindView(R.id.currency_subtitle)
    TextView subTitle;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, NormalCurrencyActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp(toolbar);
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        super.setUp(toolbar);

        presenter.onViewPrepared();
        title.setVisibility(View.GONE);
        subTitle.setVisibility(View.GONE);
        continueButton.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @OnClick(R.id.currency_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @Override
    public void onClicked(View v, int position) {
        adapter.toggleSelection(position);
        presenter.updateCurrencyCode(adapter.getCurrencyCode());
        presenter.updateCurrencySymbol(adapter.getCurrencySymbol());
        onBackPressed();
    }
}
