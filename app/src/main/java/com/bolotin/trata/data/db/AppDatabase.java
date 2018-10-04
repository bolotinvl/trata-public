package com.bolotin.trata.data.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bolotin.trata.data.db.dao.AccountDao;
import com.bolotin.trata.data.db.dao.CategoryDao;
import com.bolotin.trata.data.db.dao.TransactionDao;
import com.bolotin.trata.data.db.model.Account;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.utils.AppConstants;
import com.bolotin.trata.utils.DateTypeConverter;

@Database(entities = {Transaction.class, Category.class, Account.class}, version = 2, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppConstants.DB_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE transactions ADD COLUMN `location_latitude` REAL;");
            database.execSQL("ALTER TABLE transactions ADD COLUMN `location_longtitude` REAL;");
        }
    };

    static void closeConnection() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }

    static boolean isInstantiated() {
        return instance != null;
    }

    public abstract TransactionDao transactionDao();

    public abstract CategoryDao categoryDao();

    public abstract AccountDao accountDao();
}
