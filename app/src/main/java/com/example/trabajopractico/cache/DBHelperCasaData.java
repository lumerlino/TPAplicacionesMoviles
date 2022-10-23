package com.example.trabajopractico.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.trabajopractico.Casa;
import com.example.trabajopractico.CasaData;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelperCasaData extends OrmLiteSqliteOpenHelper {

    private static final String NOMBRE_BDD = "bdd_casadata";
    private static final int VERSION_BDD = 1;

    public DBHelperCasaData(Context context) {
        super(context, NOMBRE_BDD, null, VERSION_BDD);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, CasaData.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}