package com.example.trabajopractico

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.trabajopractico.R
import com.example.trabajopractico.LibroManager
import com.example.trabajopractico.Libro
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class LibroActivity : AppCompatActivity() {
    private lateinit var miToolbar: Toolbar
    private lateinit var tvName: TextView
    private lateinit var tvIsbn: TextView
    private lateinit var tvNumberOfPages: TextView
    private lateinit var tvPublisher: TextView
    private lateinit var tvCountry: TextView
    private lateinit var tvMediaType: TextView
    private lateinit var tvReleased: TextView

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
        tvName = findViewById(R.id.tvName)
        tvIsbn = findViewById(R.id.tvIsbn)
        tvNumberOfPages = findViewById(R.id.tvNumberOfPages)
        tvPublisher = findViewById(R.id.tvPublisher)
        tvCountry = findViewById(R.id.tvCountry)
        tvMediaType = findViewById(R.id.tvMediaType)
        tvReleased = findViewById(R.id.tvReleased)

        val api = RetrofitClient.retrofit.create(LibroAPI::class.java)
        val callGetLibro = api.getLibro(2)
        callGetLibro.enqueue(object : retrofit2.Callback<LibroData>{
            override fun onResponse(call: Call<LibroData>, response : Response<LibroData>){
                val libro = response.body()
                if(libro != null){
                    Log.d("REST", libro.toString())
                    setDataToView(libro)
                }
            }
            override fun onFailure(call: Call<LibroData>, t : Throwable){
                Log.e("REST", t.message?:"")
            }
        }
        )
    }

    private fun setDataToView(libro: LibroData) {
        tvName.text = libro.name
        tvIsbn.text = libro.isbn
        tvNumberOfPages.text = libro.numberOfPages.toString()
        tvPublisher.text = libro.publisher
        tvCountry.text = libro.country
        tvMediaType.text = libro.mediaType
        tvReleased.text = libro.released
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