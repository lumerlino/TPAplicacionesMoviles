package com.example.trabajopractico.cache;

import android.content.Context;

import com.example.trabajopractico.Casa;
import com.example.trabajopractico.CasaData;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

public class CasaDataManager {
    private static CasaDataManager instancia;
    Dao<CasaData, Integer> dao;
    OrmLiteSqliteOpenHelper helper;

    public CasaDataManager() {}

    public CasaDataManager(Context context){
        try{
            OpenHelperManager.setOpenHelperClass(null);
            helper = OpenHelperManager.getHelper(context, DBHelperCasaData.class);
            dao = helper.getDao(CasaData.class);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        OpenHelperManager.releaseHelper();
    }

    public static CasaDataManager getInstancia(Context context) {
        if (instancia == null) {
            instancia = new CasaDataManager(context);
        }
        return instancia;
    }

    public Dao<CasaData, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<CasaData, Integer> dao) {
        this.dao = dao;
    }

    public void agregarCasaData(CasaData casaData) throws Exception{
        dao.create(casaData);
    }
    public CasaData getCasaData(int id) throws Exception{
        return dao.queryForId(id);
    }
    public List<CasaData> getCasasData(int page) throws Exception{
        QueryBuilder<CasaData, Integer> queryBuilder = dao.queryBuilder();
        Where<CasaData, Integer> where = queryBuilder.where();
        SelectArg selectArg = new SelectArg();
        selectArg.setValue(page);
        where.eq("page", selectArg);
        PreparedQuery<CasaData> preparedQuery = queryBuilder.prepare();
        return dao.query(preparedQuery);
    }
}