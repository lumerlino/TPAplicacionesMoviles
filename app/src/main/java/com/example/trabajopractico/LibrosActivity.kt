package com.example.trabajopractico

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajopractico.LibroAdapter
import android.os.Bundle
import com.example.trabajopractico.R
import com.example.trabajopractico.Libro
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.trabajopractico.LibroActivity
import com.example.trabajopractico.cache.LibroDataManager
import com.example.trabajopractico.cache.LibroPage
import com.example.trabajopractico.cache.LibroPageManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class LibrosActivity : AppCompatActivity() {
    private lateinit var rvLibros: RecyclerView
    private lateinit var adapter: LibroAdapter
    private lateinit var mi_toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libros)
        mi_toolbar = findViewById(R.id.mi_toolbar)
        mi_toolbar.setTitle("Libros")
        mi_toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(mi_toolbar)
        //getSupportActionBar().setTitle("Libros");
        setUpAdapter(1)
    }

    private fun setUpAdapter(pag:Int) {
        rvLibros = findViewById(R.id.rvLibros)
        val libros: MutableList<Libro> = ArrayList()

        val insLibroPageMan = LibroPageManager.getInstancia(this@LibrosActivity)
        val res = insLibroPageMan.getLibroPage(pag)
        val insLibroDataMan = LibroDataManager.getInstancia(this@LibrosActivity)
        if(res == null){
            Log.d("REST", "no cache, intento llamar a la api")
            val api = RetrofitClient.retrofit.create(LibroAPI::class.java)
            val callGetLibro = api.getLibros()
            callGetLibro.enqueue(object : retrofit2.Callback<List<LibroData>>{
                override fun onResponse(call: Call<List<LibroData>>, response : Response<List<LibroData>>){
                    val librosData = response.body()
                    if(librosData != null){
                            Log.d("REST", librosData.toString())
                            val gson = Gson()
                            for(libro in librosData){
                                val newLibroId = libro.url.takeLastWhile{ caracter -> caracter!='/' }.toInt()
                                val libToAdd = Libro(newLibroId,libro.name)
                                libros.add(libToAdd)
                                //guardado en la base para cache:
                                libro.page = 1
                                libro.id = newLibroId
                                libro.authorsString = gson.toJson(libro.authors)
                                libro.charactersString = gson.toJson(libro.characters)
                                libro.povCharactersString = gson.toJson(libro.povCharacters)
                                insLibroDataMan.agregarLibroData(libro)
                            }

                            //guardar pag en base:
                            insLibroPageMan.agregarLibroPage(LibroPage(pag))
                            adapter =
                                LibroAdapter(libros) { libro -> //Toast.makeText(LibrosActivity.this, libro.getNombre(), Toast.LENGTH_SHORT).show();
                                    val libro_activity = Intent(this@LibrosActivity, LibroActivity::class.java)
                                    libro_activity.putExtra("name", libro.nombre)
                                    libro_activity.putExtra("id", libro.id.toString())
                                    startActivity(libro_activity)
                                }
                            rvLibros.setAdapter(adapter)
                    }
                }
                override fun onFailure(call: Call<List<LibroData>>, t : Throwable){
                    Log.e("REST", t.message?:"")
                    Log.e("REST", t.toString()?:"")

                }
            }
            )
        }
        else{
            Log.d("DB", "trayendo libros de la base")
            val librosData = insLibroDataMan.getLibrosData(pag)
            for(libro in librosData){
                val libToAdd = Libro(libro.url.takeLastWhile{ caracter -> caracter!='/' }.toInt(),libro.name)
                libros.add(libToAdd)
            }
            adapter =
                LibroAdapter(libros) { libro -> //Toast.makeText(LibrosActivity.this, libro.getNombre(), Toast.LENGTH_SHORT).show();
                    val libro_activity = Intent(this@LibrosActivity, LibroActivity::class.java)
                    libro_activity.putExtra("name", libro.nombre)
                    libro_activity.putExtra("id", libro.id.toString())
                    startActivity(libro_activity)
                }
            rvLibros.setAdapter(adapter)
        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_volver) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}