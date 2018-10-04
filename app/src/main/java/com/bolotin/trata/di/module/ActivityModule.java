package com.bolotin.trata.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.bolotin.trata.di.ActivityContext;
import com.bolotin.trata.ui.analytics.CommonAnalyticsAdapter;
import com.bolotin.trata.ui.analytics.basic.BasicAnalyticsMvpPresenter;
import com.bolotin.trata.ui.analytics.basic.BasicAnalyticsMvpView;
import com.bolotin.trata.ui.analytics.basic.BasicAnalyticsPresenter;
import com.bolotin.trata.ui.analytics.category.CategoryAnalyticsMvpPresenter;
import com.bolotin.trata.ui.analytics.category.CategoryAnalyticsMvpView;
import com.bolotin.trata.ui.analytics.category.CategoryAnalyticsPresenter;
import com.bolotin.trata.ui.analytics.customDatePicker.CustomDatePickerMvpPresenter;
import com.bolotin.trata.ui.analytics.customDatePicker.CustomDatePickerMvpView;
import com.bolotin.trata.ui.analytics.customDatePicker.CustomDatePickerPresenter;
import com.bolotin.trata.ui.analytics.details.AbstractAnalyticsDetailsMvpPresenter;
import com.bolotin.trata.ui.analytics.details.AbstractAnalyticsDetailsMvpView;
import com.bolotin.trata.ui.analytics.details.AbstractAnalyticsDetailsPresenter;
import com.bolotin.trata.ui.analytics.details.basic.BasicAnalyticsDetailsMvpPresenter;
import com.bolotin.trata.ui.analytics.details.basic.BasicAnalyticsDetailsMvpView;
import com.bolotin.trata.ui.analytics.details.basic.BasicAnalyticsDetailsPresenter;
import com.bolotin.trata.ui.analytics.details.category.CategoryAnalyticsDetailsMvpPresenter;
import com.bolotin.trata.ui.analytics.details.category.CategoryAnalyticsDetailsMvpView;
import com.bolotin.trata.ui.analytics.details.category.CategoryAnalyticsDetailsPresenter;
import com.bolotin.trata.ui.analytics.details.summary.SummaryAnalyticsDetailsMvpPresenter;
import com.bolotin.trata.ui.analytics.details.summary.SummaryAnalyticsDetailsMvpView;
import com.bolotin.trata.ui.analytics.details.summary.SummaryAnalyticsDetailsPresenter;
import com.bolotin.trata.ui.analytics.summary.SummaryAnalyticsMvpPresenter;
import com.bolotin.trata.ui.analytics.summary.SummaryAnalyticsMvpView;
import com.bolotin.trata.ui.analytics.summary.SummaryAnalyticsPresenter;
import com.bolotin.trata.ui.appearance.AppearanceMvpPresenter;
import com.bolotin.trata.ui.appearance.AppearanceMvpView;
import com.bolotin.trata.ui.appearance.AppearancePresenter;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.ui.base.MvpPresenter;
import com.bolotin.trata.ui.base.MvpView;
import com.bolotin.trata.ui.category.CategoryAdapter;
import com.bolotin.trata.ui.category.CategoryMvpPresenter;
import com.bolotin.trata.ui.category.CategoryMvpView;
import com.bolotin.trata.ui.category.CategoryPresenter;
import com.bolotin.trata.ui.category.deleteDialog.DeleteCategoryAdapter;
import com.bolotin.trata.ui.category.deleteDialog.DeleteCategoryMvpPresenter;
import com.bolotin.trata.ui.category.deleteDialog.DeleteCategoryMvpView;
import com.bolotin.trata.ui.category.deleteDialog.DeleteCategoryPresenter;
import com.bolotin.trata.ui.category.dragAndDrop.ItemTouchHelperCallback;
import com.bolotin.trata.ui.changeCategory.ChangeCategoryAdapter;
import com.bolotin.trata.ui.changeCategory.ChangeCategoryMvpPresenter;
import com.bolotin.trata.ui.changeCategory.ChangeCategoryMvpView;
import com.bolotin.trata.ui.changeCategory.ChangeCategoryPresenter;
import com.bolotin.trata.ui.currency.CurrencyAdapter;
import com.bolotin.trata.ui.currency.CurrencyMvpPresenter;
import com.bolotin.trata.ui.currency.CurrencyMvpView;
import com.bolotin.trata.ui.currency.CurrencyPresenter;
import com.bolotin.trata.ui.editTransaction.EditTransactionMvpPresenter;
import com.bolotin.trata.ui.editTransaction.EditTransactionMvpView;
import com.bolotin.trata.ui.editTransaction.EditTransactionPresenter;
import com.bolotin.trata.ui.settings.SettingsMvpPresenter;
import com.bolotin.trata.ui.settings.SettingsMvpView;
import com.bolotin.trata.ui.settings.SettingsPresenter;
import com.bolotin.trata.ui.transaction.TransactionsMvpPresenter;
import com.bolotin.trata.ui.transaction.TransactionsMvpView;
import com.bolotin.trata.ui.transaction.TransactionsPagerAdapter;
import com.bolotin.trata.ui.transaction.TransactionsPresenter;
import com.bolotin.trata.ui.transaction.editDialog.EditDialogMvpPresenter;
import com.bolotin.trata.ui.transaction.editDialog.EditDialogMvpView;
import com.bolotin.trata.ui.transaction.editDialog.EditDialogPresenter;
import com.bolotin.trata.ui.transaction.transactionsFragment.TransactionsAdapter;
import com.bolotin.trata.ui.transaction.transactionsFragment.TransactionsFragmentMvpPresenter;
import com.bolotin.trata.ui.transaction.transactionsFragment.TransactionsFragmentMvpView;
import com.bolotin.trata.ui.transaction.transactionsFragment.TransactionsFragmentPresenter;
import com.bolotin.trata.utils.ScreenUtils;
import com.bolotin.trata.utils.rx.AppSchedulerProvider;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    private CategoryAdapter categoryAdapter;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    MvpPresenter<MvpView> provideBasePresenter(
            BasePresenter<MvpView> presenter) {
        return presenter;
    }

    @Provides
    TransactionsMvpPresenter<TransactionsMvpView> provideTransactionsPresenter(
            TransactionsPresenter<TransactionsMvpView> presenter) {
        return presenter;
    }

    @Provides
    TransactionsFragmentMvpPresenter<TransactionsFragmentMvpView> provideTransactionsFragmentPresenter(
            TransactionsFragmentPresenter<TransactionsFragmentMvpView> presenter) {
        return presenter;
    }

    @Provides
    EditDialogMvpPresenter<EditDialogMvpView> provideEditDialogPresenter(
            EditDialogPresenter<EditDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    EditTransactionMvpPresenter<EditTransactionMvpView> provideEditTransactionPresenter(
            EditTransactionPresenter<EditTransactionMvpView> presenter) {
        return presenter;
    }

    @Provides
    CategoryMvpPresenter<CategoryMvpView> provideCategoryPresenter(
            CategoryPresenter<CategoryMvpView> presenter) {
        return presenter;
    }

    @Provides
    ChangeCategoryMvpPresenter<ChangeCategoryMvpView> provideAddCategoryPresenter(
            ChangeCategoryPresenter<ChangeCategoryMvpView> presenter) {
        return presenter;
    }

    @Provides
    DeleteCategoryMvpPresenter<DeleteCategoryMvpView> provideDeleteCategoryPresenter(
            DeleteCategoryPresenter<DeleteCategoryMvpView> presenter) {
        return presenter;
    }

    @Provides
    SettingsMvpPresenter<SettingsMvpView> provideSettingsPresenter(
            SettingsPresenter<SettingsMvpView> presenter) {
        return presenter;
    }

    @Provides
    CurrencyMvpPresenter<CurrencyMvpView> provideCurrencyPresenter(
            CurrencyPresenter<CurrencyMvpView> presenter) {
        return presenter;
    }

    @Provides
    AppearanceMvpPresenter<AppearanceMvpView> provideAppearancePresenter(
            AppearancePresenter<AppearanceMvpView> presenter) {
        return presenter;
    }

    @Provides
    BasicAnalyticsMvpPresenter<BasicAnalyticsMvpView> provideBasicAnalyticsPresenter(
            BasicAnalyticsPresenter<BasicAnalyticsMvpView> presenter) {
        return presenter;
    }

    @Provides
    CustomDatePickerMvpPresenter<CustomDatePickerMvpView> provideCustomDatePickerPresenter(
            CustomDatePickerPresenter<CustomDatePickerMvpView> presenter) {
        return presenter;
    }

    @Provides
    CategoryAnalyticsMvpPresenter<CategoryAnalyticsMvpView> provideCategoryAnalyticsPresenter(
            CategoryAnalyticsPresenter<CategoryAnalyticsMvpView> presenter) {
        return presenter;
    }

    @Provides
    SummaryAnalyticsMvpPresenter<SummaryAnalyticsMvpView> provideSummaryAnalyticsPresenter(
            SummaryAnalyticsPresenter<SummaryAnalyticsMvpView> presenter) {
        return presenter;
    }

    @Provides
    AbstractAnalyticsDetailsMvpPresenter<AbstractAnalyticsDetailsMvpView> provideAbstractAnalyticsDetailsPresenter(
            AbstractAnalyticsDetailsPresenter<AbstractAnalyticsDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    BasicAnalyticsDetailsMvpPresenter<BasicAnalyticsDetailsMvpView> provideBasicAnalyticsDetailsPresenter(
            BasicAnalyticsDetailsPresenter<BasicAnalyticsDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    CategoryAnalyticsDetailsMvpPresenter<CategoryAnalyticsDetailsMvpView> provideCategoryAnalyticsDetailsPresenter(
            CategoryAnalyticsDetailsPresenter<CategoryAnalyticsDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    SummaryAnalyticsDetailsMvpPresenter<SummaryAnalyticsDetailsMvpView> provideSummaryAnalyticsDetailsPresenter(
            SummaryAnalyticsDetailsPresenter<SummaryAnalyticsDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    TransactionsAdapter provideTransactionsAdapter() {
        return new TransactionsAdapter(new ArrayList<>());
    }

    @Provides
    CategoryAdapter provideCategoryAdapter() {
        this.categoryAdapter = new CategoryAdapter(new ArrayList<>());
        return categoryAdapter;
    }

    @Provides
    DeleteCategoryAdapter provideDeleteCategoryAdapter() {
        return new DeleteCategoryAdapter(new ArrayList<>());
    }

    @Provides
    ChangeCategoryAdapter provideAddCategoryAdapter() {
        return new ChangeCategoryAdapter(new ArrayList<>(), provideContext());
    }

    @Provides
    CurrencyAdapter provideCurrencyAdapter() {
        return new CurrencyAdapter(new ArrayList<>());
    }

    @Provides
    CommonAnalyticsAdapter provideCommonAnalyticsAdapter() {
        return new CommonAnalyticsAdapter(new ArrayList<>());
    }

    @Provides
    TransactionsPagerAdapter provideTransactionPagerAdapter() {
        return new TransactionsPagerAdapter(activity.getSupportFragmentManager(), new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    GridLayoutManager provideGridLayoutManager(AppCompatActivity activity) {
        float dpWidth = ScreenUtils.getScreenWidth(activity) / ScreenUtils.getScreenDensity(activity);
        return new GridLayoutManager(activity, Float.valueOf(dpWidth / 56).intValue());
    }

    @Provides
    ItemTouchHelper provideItemTouchHelper(ItemTouchHelperCallback itemTouchHelperCallback) {
        return new ItemTouchHelper(itemTouchHelperCallback);
    }

    @Provides
    ItemTouchHelperCallback provideItemTouchHelperCallback() {
        return new ItemTouchHelperCallback(categoryAdapter);
    }
}