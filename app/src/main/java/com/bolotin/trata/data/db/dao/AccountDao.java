package com.bolotin.trata.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bolotin.trata.data.db.model.Account;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface AccountDao {

    @Insert
    void insertAccount(Account account);

    @Insert
    void insertAccounts(List<Account> accounts);

    @Update
    void updateAccount(Account account);

    @Query("SELECT * FROM accounts")
    Single<Account> getAccount();

    @Query("SELECT currency_code FROM accounts")
    Single<String> getCurrencyCode();

    @Query("DELETE FROM accounts")
    void clearAccounts();
}