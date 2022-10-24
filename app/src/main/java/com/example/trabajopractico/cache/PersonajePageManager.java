package com.example.trabajopractico.cache;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class PersonajePageManager {
    private static PersonajePageManager instancia;
    Dao<PersonajePage, Integer> dao;
    OrmLiteSqliteOpenHelper helper;

    public PersonajePageManager() {}

    public PersonajePageManager(Context context){
        try{
            OpenHelperManager.setOpenHelperClass(null);
            helper = OpenHelperManager.getHelper(context, DBHelperPersonajePage.class);
            dao = helper.getDao(PersonajePage.class);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        OpenHelperManager.releaseHelper();
    }

    public static PersonajePageManager getInstancia(Context context) {
        if (instancia == null) {
            instancia = new PersonajePageManager(context);
        }
        return instancia;
    }

    public Dao<PersonajePage, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<PersonajePage, Integer> dao) {
        this.dao = dao;
    }

    public void agregarPersonajePage(PersonajePage personajePage) throws Exception{
        dao.create(personajePage);
    }
    public PersonajePage getPersonajePage(int id) throws Exception{
        return dao.queryForId(id);
    }

}