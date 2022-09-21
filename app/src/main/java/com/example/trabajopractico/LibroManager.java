package com.example.trabajopractico;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class LibroManager {
    private static LibroManager instancia;
    Dao<Libro, Integer> dao;

    public LibroManager() {}

    public LibroManager(Context context){
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelperLibro.class);
        try{
            dao = helper.getDao(Libro.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static LibroManager getInstancia(Context context) {
        if(instancia == null){
            instancia = new LibroManager(context);
        }
        return instancia;
    }

    public Dao<Libro, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<Libro, Integer> dao) {
        this.dao = dao;
    }

    public List<Libro> getLibrosFavoritos() throws Exception{
        return dao.queryForAll();
    }

    public void agregarLibroFavorito(Libro libro) throws Exception{
        dao.create(libro);
    }
}
