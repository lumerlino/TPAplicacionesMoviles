package com.example.trabajopractico.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.trabajopractico.LibroData;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelperLibroData extends OrmLiteSqliteOpenHelper {

    private static final String NOMBRE_BDD = "bdd_librodata";
    private static final int VERSION_BDD = 1;

    public DBHelperLibroData(Context context) {
        super(context, NOMBRE_BDD, null, VERSION_BDD);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, LibroData.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}