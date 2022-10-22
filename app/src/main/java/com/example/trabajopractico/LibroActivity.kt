package com.example.trabajopractico

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.trabajopractico.R
import com.example.trabajopractico.LibroManager
import com.example.trabajopractico.Libro
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.lang.Exception

class LibroActivity : AppCompatActivity() {
    private lateinit var miToolbar: Toolbar
    private lateinit var tvNombre: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)
        var toolbar_title = "nombre libro"
        val bundle = intent.extras
        if (bundle != null) {
            toolbar_title = bundle.getString("name").toString()
        }
        miToolbar = findViewById(R.id.mi_toolbar)
        miToolbar.setTitle(toolbar_title)
        miToolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(miToolbar)
        tvNombre = findViewById(R.id.tvNombre)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorito, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_volver) {
            finish()
        }
        if (item.itemId == R.id.item_favorito) {
            val manager = LibroManager.getInstancia(this@LibroActivity)
            var libro: Libro? = null
            val bundle = intent.extras
            if (bundle != null) {
                libro = Libro(bundle.getString("id")!!.toInt(), bundle.getString("name"))
            }
            try {
                if (libro != null) {
                    val libroGuardado = manager.getLibroFavorito(libro.id)
                    if (libroGuardado == null) {
                        manager.agregarLibroFavorito(libro)
                        Toast.makeText(
                            this@LibroActivity,
                            "Libro Agregado a Favoritos",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        manager.deleteLibroFavorito(libro.id)
                        Toast.makeText(
                            this@LibroActivity,
                            "Libro Eliminado de Favoritos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}