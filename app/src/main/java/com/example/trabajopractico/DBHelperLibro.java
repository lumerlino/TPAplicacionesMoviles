package com.example.trabajopractico;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelperLibro extends OrmLiteSqliteOpenHelper {

    private static final String NOMBRE_BDD = "bdd_favLibros";
    private static final int VERSION_BDD = 1;

    public DBHelperLibro(Context context) {
        super(context, NOMBRE_BDD, null, VERSION_BDD);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Libro.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
