package com.samnoedel.reader.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG_NAME = DatabaseHelper.class.getName();
    private static final String DATABASE_NAME = "reader.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<RssFeed, String> mRssFeedDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RssFeed.class);
        } catch (SQLException ex) {
            Log.e(TAG_NAME, "Can't create database", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, RssFeed.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException ex) {
            Log.e(TAG_NAME, "Can't drop database", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void close() {
        super.close();
        mRssFeedDao = null;
    }

    public Dao<RssFeed, String> getRssFeedDao() throws SQLException {
        if (mRssFeedDao == null) {
            mRssFeedDao = getDao(RssFeed.class);
        }
        return mRssFeedDao;
    }
}
