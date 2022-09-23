package com.example.trabajopractico;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class PersonajeManager {
    private static PersonajeManager instancia;
    Dao<Personaje, Integer> dao;

    public PersonajeManager() {}

    public PersonajeManager(Context context){
        try{
            OpenHelperManager.setOpenHelperClass(null);
            OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelperPersonaje.class);
            dao = helper.getDao(Personaje.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
        OpenHelperManager.releaseHelper();
    }

    public static PersonajeManager getInstancia(Context context){
        if(instancia == null){
            instancia = new PersonajeManager(context);
        }
        return instancia;
    }

    public Dao<Personaje, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<Personaje, Integer> dao) {
        this.dao = dao;
    }

    public List<Personaje> getPersonajesFavoritos() throws Exception{
        return dao.queryForAll();
    }
    public Personaje getPersonajeFavorito(int id) throws Exception{
        return dao.queryForId(id);
    }
    public int deletePersonajeFavorito(int id) throws Exception{
        return dao.deleteById(id);
    }

    public void agregarPersonajeFavorito(Personaje personaje) throws Exception{
        dao.create(personaje);
    }
}
