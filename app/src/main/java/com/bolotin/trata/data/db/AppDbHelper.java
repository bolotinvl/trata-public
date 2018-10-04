package com.bolotin.trata.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.bolotin.trata.data.db.dao.AccountDao;
import com.bolotin.trata.data.db.dao.CategoryDao;
import com.bolotin.trata.data.db.dao.TransactionDao;
import com.bolotin.trata.data.db.model.Account;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.di.ApplicationContext;
import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.utils.AppConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Singleton
public class AppDbHelper implements DbHelper {

    private final Context context;

    private TransactionDao transactionDao;

    private CategoryDao categoryDao;

    private AccountDao accountDao;

    private AppDatabase appDatabase;

    @Inject
    AppDbHelper(@ApplicationContext Context context,
                AppDatabase appDatabase) {
        this.context = context;
        this.appDatabase = appDatabase;
        this.transactionDao = appDatabase.transactionDao();
        this.categoryDao = appDatabase.categoryDao();
        this.accountDao = appDatabase.accountDao();
    }

    @Override
    public Completable insertTransaction(final Transaction transaction) {
        return Completable.fromCallable(() -> {
            transactionDao.insertTransaction(transaction);
            return true;
        });
    }

    @Override
    public Completable insertTransactions(List<Transaction> transactions) {
        return Completable.fromCallable(() -> {
            transactionDao.insertTransactions(transactions);
            return true;
        });
    }

    @Override
    public Completable updateTransaction(final Transaction transaction) {
        return Completable.fromCallable(() -> {
            transactionDao.updateTransaction(transaction);
            return true;
        });
    }

    @Override
    public Completable updateTransactions(List<Transaction> transactions) {
        return Completable.fromCallable(() -> {
            transactionDao.updateTransactions(transactions);
            return true;
        });
    }

    @Override
    public Completable deleteTransaction(final Transaction transaction) {
        return Completable.fromCallable(() -> {
            transactionDao.deleteTransaction(transaction);
            return true;
        });
    }

    @Override
    public Flowable<List<Transaction>> getTransactionsByMonth(final String date) {
        return transactionDao.getTransactionsByMonth(date + "%");
    }

    @Override
    public Single<List<Transaction>> getTransactionsByCategory(final String categoryId) {
        return transactionDao.getTransactionsByCategory(categoryId);
    }

    @Override
    public Single<List<Transaction>> getTransactionsByMonthAndCategory(final String date, final String categoryId) {
        return transactionDao.getTransactionsByMonthAndCategory(date + "%", categoryId);
    }

    @Override
    public Flowable<Transaction> getTransactionById(final String id) {
        return transactionDao.getTransactionById(id);
    }

    @Override
    public Single<List<Transaction>> getTransactionsByCategoryAndDate(final String categoryId, final String startDate, final String endDate) {
        return transactionDao.getTransactionsByCategoryAndDate(categoryId, startDate, endDate);
    }

    @Override
    public Flowable<List<String>> getUniqueMonths() {
        return transactionDao.getUniqueMonths();
    }

    @Override
    public Flowable<List<Double>> getTransactionsSum() {
        return transactionDao.getTransactionsSum();
    }

    @Override
    public Single<List<BarChartData>> getBarChartDataGroupedByCategory(final String startDate, final String endDate) {
        return transactionDao.getBarChartDataGroupedByCategory(startDate, endDate);
    }

    @Override
    public Single<List<BarChartData>> getBarChartDataSummary() {
        return transactionDao.getBarChartDataSummary();
    }

    @Override
    public Single<List<BarChartData>> getBarChartDataByCategory(final String categoryId) {
        return transactionDao.getBarChartDataByCategory(categoryId);
    }

    @Override
    public Completable insertCategory(final Category category) {
        return Completable.fromCallable(() -> {
            categoryDao.insertCategory(category);
            return true;
        });
    }

    @Override
    public Completable insertCategories(List<Category> categories) {
        return Completable.fromCallable(() -> {
            categoryDao.insertCategories(categories);
            return true;
        });
    }

    @Override
    public Completable updateCategory(final Category category) {
        return Completable.fromCallable(() -> {
            categoryDao.updateCategory(category);
            return true;
        });
    }

    @Override
    public Completable updateCategories(final List<Category> categories) {
        return Completable.fromCallable(() -> {
            categoryDao.updateCategories(categories);
            return true;
        });
    }

    @Override
    public Completable deleteCategory(final Category category) {
        return Completable.fromCallable(() -> {
            categoryDao.deleteCategory(category);
            return true;
        });
    }

    @Override
    public Flowable<List<Category>> getCategories() {
        return categoryDao.getCategories();
    }

    @Override
    public Single<Integer> getCategoriesCount() {
        return Single.fromCallable(categoryDao::getCategoriesCount);
    }

    @Override
    public Single<List<Category>> getCategoriesExceptOne(final String categoryId) {
        return categoryDao.getCategoriesExceptOne(categoryId);
    }

    @Override
    public Maybe<Category> getDefaultCategory() {
        return categoryDao.getDefaultCategory();
    }

    @Override
    public Flowable<Category> getCategoryById(final String categoryId) {
        return categoryDao.getCategoryById(categoryId);
    }

    @Override
    public Single<String> getCategoryIcon(final String categoryId) {
        return categoryDao.getCategoryIcon(categoryId);
    }

    @Override
    public Single<String> getCategoryName(String categoryId) {
        return categoryDao.getCategoryName(categoryId);
    }

    @Override
    public Completable insertAccount(final Account account) {
        return Completable.fromCallable(() -> {
            accountDao.insertAccount(account);
            return true;
        });
    }

    @Override
    public Completable insertAccounts(List<Account> accounts) {
        return Completable.fromCallable(() -> {
            accountDao.insertAccounts(accounts);
            return true;
        });
    }

    @Override
    public Completable updateAccount(final Account account) {
        return Completable.fromCallable(() -> {
            accountDao.updateAccount(account);
            return true;
        });
    }

    @Override
    public Single<Account> getAccount() {
        return accountDao.getAccount();
    }

    @Override
    public Single<String> getCurrencyCode() {
        return accountDao.getCurrencyCode();
    }

    @Override
    public Single<Boolean> exportDB(Uri uri) {
        return Single.fromCallable(() -> {
            //Close connection to database
            AppDatabase.closeConnection();

            File currentDB = new File(context.getDatabasePath(AppConstants.DB_NAME).getAbsolutePath());
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "w");

            if (currentDB.exists() && parcelFileDescriptor != null) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(parcelFileDescriptor.getFileDescriptor()).getChannel();

                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                parcelFileDescriptor.close();
                MediaScannerConnection.scanFile(context, new String[]{uri.getPath()},
                        new String[]{AppConstants.DB_MIME_TYPE}, null);

                if (!AppDatabase.isInstantiated()) {
                    appDatabase = AppDatabase.getDatabase(context);
                    transactionDao = appDatabase.transactionDao();
                    categoryDao = appDatabase.categoryDao();
                    accountDao = appDatabase.accountDao();
                }
                return true;
            } else {
                throw new Exception();
            }
        });
    }

    @Override
    public Single<Boolean> importDB(Uri uri) {
        return Single.fromCallable(() -> {
            DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(context, "importedDB", uri);
            databaseOpenHelper.copyDB();
            SQLiteDatabase database = databaseOpenHelper.getWritableDatabase();
            try {
                //Checks if there is needed table
                Cursor checkCursor = database.rawQuery("SELECT * FROM categories", null);
                checkCursor.close();

                transactionDao.clearTransactions();
                accountDao.clearAccounts();
                categoryDao.clearCategories();

                Cursor categoryCursor = database.rawQuery("SELECT * FROM categories", null);
                List<Category> categories = new ArrayList<>();
                categoryCursor.moveToFirst();

                while (!categoryCursor.isAfterLast()) {
                    categories.add(new Category(
                            categoryCursor.getString(0),
                            categoryCursor.getString(1),
                            categoryCursor.getString(2),
                            categoryCursor.getInt(3),
                            categoryCursor.getString(4),
                            categoryCursor.getString(5)));
                    categoryCursor.moveToNext();
                }
                categoryDao.insertCategories(categories);
                categoryCursor.close();

                Cursor accountCursor = database.rawQuery("SELECT * FROM accounts", null);
                List<Account> accounts = new ArrayList<>();
                accountCursor.moveToFirst();
                while (!accountCursor.isAfterLast()) {
                    accounts.add(new Account(
                            accountCursor.getString(0),
                            accountCursor.getString(1),
                            accountCursor.getString(2),
                            accountCursor.getString(3)));
                    accountCursor.moveToNext();
                }
                accountDao.insertAccounts(accounts);
                accountCursor.close();

                Cursor transactionCursor = database.rawQuery("SELECT * FROM transactions", null);
                List<Transaction> transactions = new ArrayList<>();
                transactionCursor.moveToFirst();

                if (transactionCursor.getColumnCount() == 10) {
                    while (!transactionCursor.isAfterLast()) {
                        transactions.add(new Transaction(
                                transactionCursor.getString(0),
                                transactionCursor.getString(1),
                                transactionCursor.getString(2),
                                transactionCursor.getDouble(3),
                                transactionCursor.getString(4),
                                transactionCursor.getString(5),
                                transactionCursor.getInt(6) != 0,
                                transactionCursor.getInt(7) != 0,
                                transactionCursor.getString(8),
                                transactionCursor.getString(9),
                                null,
                                null));
                        transactionCursor.moveToNext();
                    }
                } else {
                    while (!transactionCursor.isAfterLast()) {
                        transactions.add(new Transaction(
                                transactionCursor.getString(0),
                                transactionCursor.getString(1),
                                transactionCursor.getString(2),
                                transactionCursor.getDouble(3),
                                transactionCursor.getString(4),
                                transactionCursor.getString(5),
                                transactionCursor.getInt(6) != 0,
                                transactionCursor.getInt(7) != 0,
                                transactionCursor.getString(8),
                                transactionCursor.getString(9),
                                transactionCursor.getDouble(10) == 0 ? null : transactionCursor.getDouble(10),
                                transactionCursor.getDouble(11) == 0 ? null : transactionCursor.getDouble(11)));
                        transactionCursor.moveToNext();
                    }
                }
                transactionDao.insertTransactions(transactions);

                transactionCursor.close();
                database.close();
                return true;
            } finally {
                databaseOpenHelper.close();

                if (database != null) {
                    database.close();
                }
            }

        });
    }

    @Override
    public Single<List<HashMap<String, String>>> getCurrencies() {
        return Single.fromCallable(() -> {
            ArrayList<HashMap<String, String>> currencies = new ArrayList<>();
            DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(context, "currencies.sqlite3");
            databaseOpenHelper.copyDB();

            try (SQLiteDatabase database = databaseOpenHelper.getWritableDatabase();
                 Cursor cursor = database.rawQuery("SELECT * FROM currencies ORDER BY code", null)) {
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    HashMap<String, String> currency = new HashMap<>();
                    String currencyCode = cursor.getString(0);
                    String currencyName = cursor.getString(1);
                    String currencySymbol = cursor.getString(2);

                    currency.put("currencyCode", currencyCode);
                    currency.put("currencyName", currencyName);
                    currency.put("currencySymbol", currencySymbol);
                    currencies.add(currency);

                    cursor.moveToNext();
                }
                databaseOpenHelper.close();
            }
            return currencies;
        });
    }
}