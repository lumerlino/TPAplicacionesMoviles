package com.example.trabajopractico.cache;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class LibroPageManager {
    private static LibroPageManager instancia;
    Dao<LibroPage, Integer> dao;
    OrmLiteSqliteOpenHelper helper;

    public LibroPageManager() {}

    public LibroPageManager(Context context){
        try{
            OpenHelperManager.setOpenHelperClass(null);
            helper = OpenHelperManager.getHelper(context, DBHelperLibroPage.class);
            dao = helper.getDao(LibroPage.class);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        OpenHelperManager.releaseHelper();
    }

    public static LibroPageManager getInstancia(Context context) {
        if (instancia == null) {
            instancia = new LibroPageManager(context);
        }
        return instancia;
    }

    public Dao<LibroPage, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<LibroPage, Integer> dao) {
        this.dao = dao;
    }

    public void agregarLibroPage(LibroPage libroPage) throws Exception{
        dao.create(libroPage);
    }
    public LibroPage getLibroPage(int id) throws Exception{
        return dao.queryForId(id);
    }

}
