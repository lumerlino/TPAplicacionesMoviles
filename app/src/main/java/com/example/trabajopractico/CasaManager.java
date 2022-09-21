package com.example.trabajopractico;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class CasaManager {
    private static CasaManager instancia;
    Dao<Casa, Integer> dao;

    public CasaManager() {}

    public CasaManager(Context context){
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelperCasa.class);
        try{
            dao = helper.getDao(Casa.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static CasaManager getInstancia(Context context) {
        if (instancia == null) {
            instancia = new CasaManager(context);
        }
        return instancia;
    }

    public Dao<Casa, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<Casa, Integer> dao) {
        this.dao = dao;
    }

    public List<Casa> getCasasFavoritos() throws Exception{
        return dao.queryForAll();
    }

    public void agregarCasaFavorito(Casa casa) throws Exception{
        dao.create(casa);
    }
}
