package com.bolotin.trata.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseOpenHelper extends SQLiteOpenHelper{

    private String dbName;
    private static String DB_PATH = "";
    private static final int DB_VERSION = 2;
    private Uri uri;
    private final Context context;
    private SQLiteDatabase database;

    DatabaseOpenHelper(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.dbName = dbName;
        this.context = context;
    }

    DatabaseOpenHelper(Context context, String dbName, Uri uri) {
        this(context, dbName);
        this.uri = uri;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    void copyDB() {
        copyDatabase();
        getReadableDatabase();
    }

    private boolean checkDatabase(String dbPath, String dbName) {
        File dbFile = new File(dbPath + dbName);
        return dbFile.exists();
    }

    private void copyDatabase() {
           getReadableDatabase();
           close();
           try {
               if (uri != null) {
                   copyDBFileFromUri(uri);
               } else {
                   copyDBFile();
               }
           } catch (IOException e) {
               e.getMessage();
           }
    }

    private void copyDBFile() throws IOException {
        try(InputStream inputStream = context.getAssets().open(dbName);
            OutputStream outputStream = new FileOutputStream(DB_PATH + dbName)) {
            copy(inputStream, outputStream);
        }
    }

    private void copyDBFileFromUri(Uri uri) throws IOException {
        try(InputStream inputStream = context.getContentResolver().openInputStream(uri);
            OutputStream outputStream = new FileOutputStream(DB_PATH + dbName)) {
            copy(inputStream, outputStream);
        }
    }

    private void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
    }
}
