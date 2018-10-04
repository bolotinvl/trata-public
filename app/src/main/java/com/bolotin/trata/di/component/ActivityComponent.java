package com.bolotin.trata.di.component;

import com.bolotin.trata.di.PerActivity;
import com.bolotin.trata.di.module.ActivityModule;
import com.bolotin.trata.ui.analytics.basic.BasicAnalyticsActivity;
import com.bolotin.trata.ui.analytics.category.CategoryAnalyticsActivity;
import com.bolotin.trata.ui.analytics.customDatePicker.CustomDatePickerDialog;
import com.bolotin.trata.ui.analytics.details.AbstractAnalyticsDetailsActivity;
import com.bolotin.trata.ui.analytics.details.basic.BasicAnalyticsDetailsActivity;
import com.bolotin.trata.ui.analytics.details.category.CategoryAnalyticsDetailsActivity;
import com.bolotin.trata.ui.analytics.details.summary.SummaryAnalyticsDetailsActivity;
import com.bolotin.trata.ui.analytics.summary.SummaryAnalyticsActivity;
import com.bolotin.trata.ui.appearance.AppearanceActivity;
import com.bolotin.trata.ui.base.BaseActivity;
import com.bolotin.trata.ui.category.AbstractCategoryActivity;
import com.bolotin.trata.ui.category.deleteDialog.DeleteCategoryDialog;
import com.bolotin.trata.ui.changeCategory.ChangeCategoryActivity;
import com.bolotin.trata.ui.currency.AbstractCurrencyActivity;
import com.bolotin.trata.ui.editTransaction.EditTransactionActivity;
import com.bolotin.trata.ui.settings.SettingsActivity;
import com.bolotin.trata.ui.transaction.TransactionsActivity;
import com.bolotin.trata.ui.transaction.editDialog.EditDialog;
import com.bolotin.trata.ui.transaction.transactionsFragment.TransactionsFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity activity);

    void inject(TransactionsActivity activity);

    void inject(TransactionsFragment fragment);

    void inject(EditDialog dialog);

    void inject(EditTransactionActivity activity);

    void inject(AbstractCategoryActivity activity);

    void inject(ChangeCategoryActivity activity);

    void inject (DeleteCategoryDialog dialog);

    void inject(SettingsActivity activity);

    void inject(AbstractCurrencyActivity activity);

    void inject(AppearanceActivity activity);

    void inject(BasicAnalyticsActivity activity);

    void inject(CustomDatePickerDialog dialog);

    void inject(CategoryAnalyticsActivity activity);

    void inject(SummaryAnalyticsActivity activity);

    void inject(AbstractAnalyticsDetailsActivity activity);

    void inject(BasicAnalyticsDetailsActivity activity);

    void inject(CategoryAnalyticsDetailsActivity activity);

    void inject(SummaryAnalyticsDetailsActivity activity);
}