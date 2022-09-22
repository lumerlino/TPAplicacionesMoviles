package com.example.trabajopractico;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class UsuarioManager {
    private static UsuarioManager instancia;
    Dao<Usuario, Integer> dao;

    public UsuarioManager(){}

    public UsuarioManager (Context context){
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelperUsuario.class);
        try{
            dao = helper.getDao(Usuario.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static UsuarioManager getInstancia(Context context){
        if(instancia == null){
            instancia = new UsuarioManager(context);
        }
        return instancia;
    }

    public Dao<Usuario, Integer> getDao(){
        return dao;
    }

    public void setDao(Dao<Usuario, Integer> dao){
        this.dao = dao;
    }

    public List<Usuario> getUsuarios() throws Exception{
        return dao.queryForAll();
    }

    public void agregarUsuario(Usuario usuario) throws Exception{
        dao.create(usuario);
    }
}
