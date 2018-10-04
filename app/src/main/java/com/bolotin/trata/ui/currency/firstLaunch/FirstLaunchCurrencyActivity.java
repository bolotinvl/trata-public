package com.bolotin.trata.ui.currency.firstLaunch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.transaction.TransactionsActivity;
import com.bolotin.trata.ui.currency.AbstractCurrencyActivity;

import butterknife.OnClick;

public class FirstLaunchCurrencyActivity extends AbstractCurrencyActivity {

    public static Intent getStartIntent(Context context) {
        return new Intent(context, FirstLaunchCurrencyActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp(toolbar);
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        super.setUp(toolbar);

        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void onClicked(View v, int position) {
        adapter.toggleSelection(position);
    }

    @OnClick(R.id.continue_button)
    void onContinueButtonClick() {
        presenter.initializeCategories();
        presenter.disableFirstRun();
        presenter.updateCurrencySymbol(adapter.getCurrencySymbol());
        presenter.updateCurrencyCode(adapter.getCurrencyCode());
        startActivity(TransactionsActivity.getStartIntent(this));
    }
}
