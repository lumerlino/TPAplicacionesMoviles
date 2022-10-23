package com.example.trabajopractico.cache;

import android.content.Context;

import com.example.trabajopractico.Casa;
import com.example.trabajopractico.DBHelperCasa;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class CasaPageManager {
    private static CasaPageManager instancia;
    Dao<CasaPage, Integer> dao;
    OrmLiteSqliteOpenHelper helper;

    public CasaPageManager() {}

    public CasaPageManager(Context context){
        try{
            OpenHelperManager.setOpenHelperClass(null);
            helper = OpenHelperManager.getHelper(context, DBHelperCasaPage.class);
            dao = helper.getDao(CasaPage.class);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        OpenHelperManager.releaseHelper();
    }

    public static CasaPageManager getInstancia(Context context) {
        if (instancia == null) {
            instancia = new CasaPageManager(context);
        }
        return instancia;
    }

    public Dao<CasaPage, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<CasaPage, Integer> dao) {
        this.dao = dao;
    }

    public void agregarCasaPage(CasaPage casaPage) throws Exception{
        dao.create(casaPage);
    }
    public CasaPage getCasaPage(int id) throws Exception{
        return dao.queryForId(id);
    }

}
