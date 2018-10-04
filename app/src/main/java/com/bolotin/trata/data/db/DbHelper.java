package com.bolotin.trata.data.db;

import android.net.Uri;

import com.bolotin.trata.data.db.model.Account;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.barChart.BarChartData;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface DbHelper {

    /*
    Transaction table
     */
    Completable insertTransaction(final Transaction transaction);

    Completable insertTransactions(final List<Transaction> transactions);

    Completable updateTransaction(final Transaction transaction);

    Completable updateTransactions(final List<Transaction> transactions);

    Completable deleteTransaction(final Transaction transaction);

    Flowable<List<Transaction>> getTransactionsByMonth(final String date);

    Single<List<Transaction>> getTransactionsByCategory(final String categoryId);

    Single<List<Transaction>> getTransactionsByMonthAndCategory(final String date, final String categoryId);

    Flowable<Transaction> getTransactionById(final String id);

    Single<List<Transaction>> getTransactionsByCategoryAndDate(final String categoryId, final String startDate, final String endDate);

    Flowable<List<String>> getUniqueMonths();

    Flowable<List<Double>> getTransactionsSum();

    Single<List<BarChartData>> getBarChartDataGroupedByCategory(final String startDate, final String endDate);

    Single<List<BarChartData>> getBarChartDataSummary();

    Single<List<BarChartData>> getBarChartDataByCategory(final String categoryId);

    /*
    Category table
     */
    Completable insertCategory(final Category category);

    Completable insertCategories(final List<Category> categories);

    Completable updateCategory(final Category category);

    Completable updateCategories(final List<Category> categories);

    Completable deleteCategory(final Category category);

    Flowable<List<Category>> getCategories();

    Single<Integer> getCategoriesCount();

    Single<List<Category>> getCategoriesExceptOne(final String categoryId);

    Maybe<Category> getDefaultCategory();

    Flowable<Category> getCategoryById(final String categoryId);

    Single<String> getCategoryIcon(final String categoryId);

    Single<String> getCategoryName(final String categoryId);

    /*
    Account table
     */
    Completable insertAccount(final Account account);

    Completable insertAccounts(final List<Account> accounts);

    Completable updateAccount(final Account account);

    Single<Account> getAccount();

    Single<String> getCurrencyCode();

    /*
    Exporting data
     */
    Single<Boolean> exportDB(Uri uri);

    /*
    Importing data
     */
    Single<Boolean> importDB(Uri uri);

    Single<List<HashMap<String, String>>> getCurrencies();
}
