package com.example.trabajopractico.cache;

import android.content.Context;

import com.example.trabajopractico.LibroData;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

public class LibroDataManager {
    private static LibroDataManager instancia;
    Dao<LibroData, Integer> dao;
    OrmLiteSqliteOpenHelper helper;

    public LibroDataManager() {}

    public LibroDataManager(Context context){
        try{
            OpenHelperManager.setOpenHelperClass(null);
            helper = OpenHelperManager.getHelper(context, DBHelperLibroData.class);
            dao = helper.getDao(LibroData.class);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        OpenHelperManager.releaseHelper();
    }

    public static LibroDataManager getInstancia(Context context) {
        if (instancia == null) {
            instancia = new LibroDataManager(context);
        }
        return instancia;
    }

    public Dao<LibroData, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<LibroData, Integer> dao) {
        this.dao = dao;
    }

    public void agregarLibroData(LibroData libroData) throws Exception{
        LibroData libro = getLibroData(libroData.getId());
        if(libro==null) dao.create(libroData);
        else{
            if(libro.getPage()==0) dao.update(libroData);
        }
    }
    public LibroData getLibroData(int id) throws Exception{
        return dao.queryForId(id);
    }
    public List<LibroData> getLibrosData(int page) throws Exception{
        QueryBuilder<LibroData, Integer> queryBuilder = dao.queryBuilder();
        Where<LibroData, Integer> where = queryBuilder.where();
        SelectArg selectArg = new SelectArg();
        selectArg.setValue(page);
        where.eq("page", selectArg);
        PreparedQuery<LibroData> preparedQuery = queryBuilder.prepare();
        return dao.query(preparedQuery);
    }
}
