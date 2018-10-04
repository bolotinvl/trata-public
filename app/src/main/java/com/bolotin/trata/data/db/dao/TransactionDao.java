package com.bolotin.trata.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.data.db.model.Transaction;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TransactionDao {

    @Insert
    void insertTransaction(Transaction transaction);

    @Insert
    void insertTransactions(List<Transaction> transactions);

    @Update
    void updateTransaction(Transaction transaction);

    @Update
    void updateTransactions(List<Transaction> transactions);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions WHERE category_id = :categoryId")
    Single<List<Transaction>> getTransactionsByCategory(String categoryId);

    @Query("SELECT * FROM transactions WHERE date LIKE :date AND active = 1 ORDER BY date")
    Flowable<List<Transaction>> getTransactionsByMonth(String date);

    @Query("SELECT * FROM transactions WHERE id = :id")
    Flowable<Transaction> getTransactionById(String id);

    @Query("SELECT * FROM transactions WHERE category_id = :categoryId AND active = 1 " +
            "AND substr(date, 1, 4)|| substr(date, 6, 2) || substr(date, 9, 2) BETWEEN :startDate AND :endDate")
    Single<List<Transaction>> getTransactionsByCategoryAndDate(String categoryId, String startDate, String endDate);

    @Query("SELECT * FROM transactions WHERE date LIKE :date AND category_id = :categoryId AND active = 1 ORDER BY date")
    Single<List<Transaction>> getTransactionsByMonthAndCategory(String date, String categoryId);

    @Query("SELECT DISTINCT SUBSTR (date, 1, 7) FROM `transactions` WHERE active = 1 ORDER BY date DESC")
    Flowable<List<String>> getUniqueMonths();

    @Query("SELECT SUM(value) FROM transactions WHERE active = 1 GROUP BY SUBSTR (date, 1, 7) ORDER BY date DESC")
    Flowable<List<Double>> getTransactionsSum();

    @Query("DELETE FROM transactions")
    void clearTransactions();

    @Query("SELECT SUM(value) AS sum, category_id as xValue FROM transactions " +
            "WHERE active = 1 AND substr(date, 1, 4) || substr(date, 6, 2) || substr(date, 9, 2) BETWEEN :startDate AND :endDate GROUP BY category_id ORDER BY SUM(value) DESC")
    Single<List<BarChartData>> getBarChartDataGroupedByCategory(String startDate, String endDate);

    @Query("SELECT SUM(value) AS sum, substr(date, 1, 4)|| substr(date, 6, 2) as xValue FROM transactions " +
            "WHERE active = 1 GROUP BY substr(date, 1, 4)|| substr(date, 6, 2) ORDER BY xValue DESC")
    Single<List<BarChartData>> getBarChartDataSummary();

    @Query("SELECT SUM(value) AS sum, substr(date, 1, 4)|| substr(date, 6, 2) as xValue FROM transactions " +
            "WHERE active = 1 AND category_id = :categoryId GROUP BY substr(date, 1, 4)|| substr(date, 6, 2) ORDER BY xValue DESC")
    Single<List<BarChartData>> getBarChartDataByCategory(String categoryId);
}