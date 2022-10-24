package com.example.trabajopractico.cache;

import android.content.Context;

import com.example.trabajopractico.PersonajeData;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

public class PersonajeDataManager {
    private static PersonajeDataManager instancia;
    Dao<PersonajeData, Integer> dao;
    OrmLiteSqliteOpenHelper helper;

    public PersonajeDataManager() {}

    public PersonajeDataManager(Context context){
        try{
            OpenHelperManager.setOpenHelperClass(null);
            helper = OpenHelperManager.getHelper(context, DBHelperPersonajeData.class);
            dao = helper.getDao(PersonajeData.class);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        OpenHelperManager.releaseHelper();
    }

    public static PersonajeDataManager getInstancia(Context context) {
        if (instancia == null) {
            instancia = new PersonajeDataManager(context);
        }
        return instancia;
    }

    public Dao<PersonajeData, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<PersonajeData, Integer> dao) {
        this.dao = dao;
    }

    public void agregarPersonajeData(PersonajeData personajeData) throws Exception{
        PersonajeData personaje = getPersonajeData(personajeData.getId());
        if(personaje==null) dao.create(personajeData);
    }
    public PersonajeData getPersonajeData(int id) throws Exception{
        return dao.queryForId(id);
    }
    public List<PersonajeData> getPersonajesData(int page) throws Exception{
        QueryBuilder<PersonajeData, Integer> queryBuilder = dao.queryBuilder();
        Where<PersonajeData, Integer> where = queryBuilder.where();
        SelectArg selectArg = new SelectArg();
        selectArg.setValue(page);
        where.eq("page", selectArg);
        PreparedQuery<PersonajeData> preparedQuery = queryBuilder.prepare();
        return dao.query(preparedQuery);
    }
}
