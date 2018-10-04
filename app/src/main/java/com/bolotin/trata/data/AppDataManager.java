package com.bolotin.trata.data;

import android.content.Context;
import android.net.Uri;

import com.bolotin.trata.data.db.DbHelper;
import com.bolotin.trata.data.db.model.Account;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.data.prefs.PreferencesHelper;
import com.bolotin.trata.di.ApplicationContext;
import com.bolotin.trata.ui.barChart.BarChartData;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class AppDataManager implements DataManager {

    private final Context context;
    private final DbHelper dbHelper;
    private final PreferencesHelper preferencesHelper;

    @Inject
    AppDataManager(@ApplicationContext Context context,
                   DbHelper dbHelper,
                   PreferencesHelper preferencesHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public Completable insertTransaction(Transaction transaction) {
        return dbHelper.insertTransaction(transaction);
    }

    @Override
    public Completable insertTransactions(List<Transaction> transactions) {
        return dbHelper.insertTransactions(transactions);
    }

    @Override
    public Completable updateTransaction(Transaction transaction) {
        return dbHelper.updateTransaction(transaction);
    }

    @Override
    public Completable updateTransactions(List<Transaction> transactions) {
        return dbHelper.updateTransactions(transactions);
    }

    @Override
    public Completable deleteTransaction(Transaction transaction) {
        return dbHelper.deleteTransaction(transaction);
    }

    @Override
    public Flowable<List<Transaction>> getTransactionsByMonth(String date) {
        return dbHelper.getTransactionsByMonth(date);
    }

    @Override
    public Single<List<Transaction>> getTransactionsByCategory(String categoryId) {
        return dbHelper.getTransactionsByCategory(categoryId);
    }

    @Override
    public Single<List<Transaction>> getTransactionsByMonthAndCategory(String date, String categoryId) {
        return dbHelper.getTransactionsByMonthAndCategory(date, categoryId);
    }

    @Override
    public Flowable<Transaction> getTransactionById(String id) {
        return dbHelper.getTransactionById(id);
    }

    @Override
    public Single<List<Transaction>> getTransactionsByCategoryAndDate(String categoryId, String startDate, String endDate) {
        return dbHelper.getTransactionsByCategoryAndDate(categoryId, startDate, endDate);
    }

    @Override
    public Flowable<List<String>> getUniqueMonths() {
        return dbHelper.getUniqueMonths();
    }

    @Override
    public Flowable<List<Double>> getTransactionsSum() {
        return dbHelper.getTransactionsSum();
    }

    @Override
    public Single<List<BarChartData>> getBarChartDataGroupedByCategory(String startDate, String endDate) {
        return dbHelper.getBarChartDataGroupedByCategory(startDate, endDate);
    }

    @Override
    public Single<List<BarChartData>> getBarChartDataSummary() {
        return dbHelper.getBarChartDataSummary();
    }

    @Override
    public Single<List<BarChartData>> getBarChartDataByCategory(String categoryId) {
        return dbHelper.getBarChartDataByCategory(categoryId);
    }

    @Override
    public Completable insertCategory(Category category) {
        return dbHelper.insertCategory(category);
    }

    @Override
    public Completable insertCategories(List<Category> categories) {
        return dbHelper.insertCategories(categories);
    }

    @Override
    public Completable updateCategory(Category category) {
        return dbHelper.updateCategory(category);
    }

    @Override
    public Completable updateCategories(final List<Category> categories) {
        return dbHelper.updateCategories(categories);
    }

    @Override
    public Completable deleteCategory(Category category) {
        return dbHelper.deleteCategory(category);
    }

    @Override
    public Flowable<List<Category>> getCategories() {
        return dbHelper.getCategories();
    }

    @Override
    public Single<Integer> getCategoriesCount() {
        return dbHelper.getCategoriesCount();
    }

    @Override
    public Single<List<Category>> getCategoriesExceptOne(String categoryId) {
        return dbHelper.getCategoriesExceptOne(categoryId);
    }

    @Override
    public Maybe<Category> getDefaultCategory() {
        return dbHelper.getDefaultCategory();
    }

    @Override
    public Flowable<Category> getCategoryById(String categoryId) {
        return dbHelper.getCategoryById(categoryId);
    }

    @Override
    public Single<String> getCategoryIcon(String categoryId) {
        return dbHelper.getCategoryIcon(categoryId);
    }

    @Override
    public Single<String> getCategoryName(String categoryId) {
        return dbHelper.getCategoryName(categoryId);
    }

    @Override
    public Completable insertAccount(Account account) {
        return dbHelper.insertAccount(account);
    }

    @Override
    public Completable insertAccounts(List<Account> accounts) {
        return dbHelper.insertAccounts(accounts);
    }

    @Override
    public Completable updateAccount(Account account) {
        return dbHelper.updateAccount(account);
    }

    @Override
    public Single<Account> getAccount() {
        return dbHelper.getAccount();
    }

    @Override
    public Single<String> getCurrencyCode() {
        return dbHelper.getCurrencyCode();
    }

    @Override
    public Single<Boolean> exportDB(Uri uri) {
        return dbHelper.exportDB(uri);
    }

    @Override
    public Single<Boolean> importDB(Uri uri) {
        return dbHelper.importDB(uri);
    }

    @Override
    public Single<List<HashMap<String, String>>> getCurrencies() {
        return dbHelper.getCurrencies();
    }

    @Override
    public Boolean getFirstRun() {
        return preferencesHelper.getFirstRun();
    }

    @Override
    public void setFirstRun(Boolean firstRun) {
        preferencesHelper.setFirstRun(firstRun);
    }

    @Override
    public String getMode() {
        return preferencesHelper.getMode();
    }

    @Override
    public void setMode(String mode) {
        preferencesHelper.setMode(mode);
    }

    @Override
    public String getTheme() {
        return preferencesHelper.getTheme();
    }

    @Override
    public void setTheme(String theme) {
        preferencesHelper.setTheme(theme);
    }

    @Override
    public String getCurrencySymbol() {
        return preferencesHelper.getCurrencySymbol();
    }

    @Override
    public void setCurrencySymbol(String currencySymbol) {
        preferencesHelper.setCurrencySymbol(currencySymbol);
    }

    @Override
    public Integer getTabPosition() {
        return preferencesHelper.getTabPosition();
    }

    @Override
    public void setTabPosition(Integer tabPosition) {
        preferencesHelper.setTabPosition(tabPosition);
    }

    @Override
    public Integer getScrollPosition() {
        return preferencesHelper.getScrollPosition();
    }

    @Override
    public void setScrollPosition(Integer scrollPosition) {
        preferencesHelper.setScrollPosition(scrollPosition);
    }

    @Override
    public Double getLatitude() {
        return preferencesHelper.getLatitude();
    }

    @Override
    public void setLatitude(Double latitude) {
        preferencesHelper.setLatitude(latitude);
    }

    @Override
    public Double getLongitude() {
        return preferencesHelper.getLongitude();
    }

    @Override
    public void setLongitude(Double longitude) {
        preferencesHelper.setLongitude(longitude);
    }
}
