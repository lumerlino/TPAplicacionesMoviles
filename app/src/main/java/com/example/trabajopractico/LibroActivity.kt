package com.example.trabajopractico

import android.content.Intent
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
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajopractico.DetailAdapter.ItemTextAdapter
import com.example.trabajopractico.cache.LibroDataManager
import com.google.gson.Gson
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
    private lateinit var tvAuthors: TextView
    private lateinit var rvCharacters: RecyclerView
    private lateinit var rvPovCharacters: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)
        var libroId = 0
        var toolbar_title = "nombre libro"
        val bundle = intent.extras
        if (bundle != null) {
            toolbar_title = bundle.getString("name").toString()
            libroId = bundle.getString("id").toString().toInt()
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
        tvAuthors = findViewById(R.id.tvAuthors)
        rvPovCharacters = findViewById(R.id.rvPovCharacters)
        rvCharacters = findViewById(R.id.rvCharacters)

        val insLibroDataMan = LibroDataManager.getInstancia(this@LibroActivity)
        val res = insLibroDataMan.getLibroData(libroId)
        if(res == null){
            Log.d("REST", "No Cache, pido datos a la api")
            val api = RetrofitClient.retrofit.create(LibroAPI::class.java)
            val callGetLibro = api.getLibro(libroId)
            callGetLibro.enqueue(object : retrofit2.Callback<LibroData>{
                override fun onResponse(call: Call<LibroData>, response : Response<LibroData>){
                    val libro = response.body()
                    if(libro != null){
                        setDataToView(libro)
                        val newLibroId = libro.url.takeLastWhile{ caracter -> caracter!='/' }.toInt()
                        val gson = Gson()
                        libro.id = newLibroId
                        libro.authorsString = gson.toJson(libro.authors)
                        libro.charactersString = gson.toJson(libro.characters)
                        libro.povCharactersString = gson.toJson(libro.povCharacters)
                        insLibroDataMan.agregarLibroData(libro)
                    }
                }
                override fun onFailure(call: Call<LibroData>, t : Throwable){
                    Log.e("REST", t.message?:"")
                }
            }
            )
        }
        else{
            Log.d("DB", "Cache, muestro datos de la base")
            val gson = Gson()
            res.authors = gson.fromJson(res.authorsString, Array<String>::class.java).toList()
            res.characters = gson.fromJson(res.charactersString, Array<String>::class.java).toList()
            res.povCharacters = gson.fromJson(res.povCharactersString, Array<String>::class.java).toList()
            setDataToView(res)
        }
    }

    private fun setDataToView(libro: LibroData) {
        tvName.text = libro.name.ifEmpty { "-" }
        tvIsbn.text = libro.isbn.ifEmpty { "-" }
        tvNumberOfPages.text = libro.numberOfPages.toString()
        tvPublisher.text = libro.publisher.ifEmpty { "-" }
        tvCountry.text = libro.country.ifEmpty { "-" }
        tvMediaType.text = libro.mediaType.ifEmpty { "-" }
        tvReleased.text = libro.released.ifEmpty { "-" }
        tvAuthors.text = convertStringList(libro.authors)
        rvPovCharacters.adapter = ItemTextAdapter(this, createListItemTextForAdapter("PERSONAJE", libro.povCharacters))
        rvCharacters.adapter = ItemTextAdapter(this, createListItemTextForAdapter("PERSONAJE", libro.characters))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorito, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_home) {
            val newIntent = Intent(this, MainActivity::class.java)
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(newIntent)
        }
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