package com.example.trabajopractico

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajopractico.PersonajeAdapter
import android.os.Bundle
import com.example.trabajopractico.R
import com.example.trabajopractico.Personaje
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.trabajopractico.PersonajeActivity
import com.example.trabajopractico.cache.PersonajeDataManager
import com.example.trabajopractico.cache.PersonajePage
import com.example.trabajopractico.cache.PersonajePageManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class PersonajesActivity : AppCompatActivity() {
    private lateinit var mi_toolbar: Toolbar
    private lateinit var rvPersonajes: RecyclerView
    private lateinit var adapter: PersonajeAdapter
    private lateinit var btnNext: ImageButton
    private lateinit var btnPrevious: ImageButton
    private lateinit var tvPag: TextView
    private var page: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personajes)
        mi_toolbar = findViewById(R.id.mi_toolbar)
        mi_toolbar.setTitle("Personajes")
        mi_toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(mi_toolbar)

        btnNext = findViewById(R.id.btnNext)
        btnPrevious = findViewById(R.id.btnPrevious)
        tvPag = findViewById(R.id.tvPag)

        setUpAdapter(page)

        btnNext.setOnClickListener(View.OnClickListener {
            if(page<50) {
                page += 1
                Log.d("REST", "PAGINA NUEVA A PEDIR = $page")
                setUpAdapter(page)
            }
        })
        btnPrevious.setOnClickListener(View.OnClickListener {
            if(page!=1) {
                page -= 1
                Log.d("REST", "PAGINA NUEVA A PEDIR = $page")
                setUpAdapter(page)
            }
        })
    }

    private fun setUpAdapter(pag:Int) {
        rvPersonajes = findViewById(R.id.rvPersonajes)
        val personajes: MutableList<Personaje> = ArrayList()

        val insPersonajePageMan = PersonajePageManager.getInstancia(this@PersonajesActivity)
        val res = insPersonajePageMan.getPersonajePage(pag)
        val insPersonajeDataMan = PersonajeDataManager.getInstancia(this@PersonajesActivity)
        if(res == null){
            Log.d("REST", "no cache, intento llamar a la api")
            val api = RetrofitClient.retrofit.create(PersonajeAPI::class.java)
            val callGetPersonaje = api.getPersonajes(pag,50)
            callGetPersonaje.enqueue(object : retrofit2.Callback<List<PersonajeData>>{
                override fun onResponse(call: Call<List<PersonajeData>>, response : Response<List<PersonajeData>>){
                    val personajesData = response.body()
                    if(personajesData != null){
                        if(personajesData.isEmpty()){
                            page -=1
                        }
                        else{
                            Log.d("REST", personajesData.toString())
                            val gson = Gson()
                            for(personaje in personajesData){
                                val newPersonajeId = personaje.url.takeLastWhile{ caracter -> caracter!='/' }.toInt()
                                var newPersonajeName = personaje.name
                                if(newPersonajeName.isEmpty()) newPersonajeName = "ID: $newPersonajeId"
                                val libToAdd = Personaje(newPersonajeId,newPersonajeName)
                                personajes.add(libToAdd)
                                //guardado en la base para cache:
                                personaje.page = pag
                                personaje.id = newPersonajeId
                                personaje.titlesString = gson.toJson(personaje.titles)
                                personaje.aliasesString = gson.toJson(personaje.aliases)
                                personaje.allegiancesString = gson.toJson(personaje.allegiances)
                                personaje.booksString = gson.toJson(personaje.books)
                                personaje.tvSeriesString = gson.toJson(personaje.tvSeries)
                                personaje.playedByString = gson.toJson(personaje.playedBy)
                                insPersonajeDataMan.agregarPersonajeData(personaje)
                            }

                            //guardar pag en base:
                            insPersonajePageMan.agregarPersonajePage(PersonajePage(pag))
                            adapter =
                                PersonajeAdapter(personajes) { personaje -> //Toast.makeText(PersonajesActivity.this, personaje.getNombre(), Toast.LENGTH_SHORT).show();
                                    val personaje_activity = Intent(this@PersonajesActivity, PersonajeActivity::class.java)
                                    personaje_activity.putExtra("name", personaje.nombreCompleto)
                                    personaje_activity.putExtra("id", personaje.id.toString())
                                    startActivity(personaje_activity)
                                }
                            rvPersonajes.setAdapter(adapter)
                            tvPag.text = "Página: $page"
                        }

                    }
                }
                override fun onFailure(call: Call<List<PersonajeData>>, t : Throwable){
                    Log.e("REST", t.message?:"")
                    Log.e("REST", t.toString()?:"")

                }
            }
            )
        }
        else{
            Log.d("DB", "trayendo personajes de la base")
            val personajesData = insPersonajeDataMan.getPersonajesData(pag)
            for(personaje in personajesData){
                val newPersonajeId = personaje.url.takeLastWhile{ caracter -> caracter!='/' }.toInt()
                var newPersonajeName = personaje.name
                if(newPersonajeName.isEmpty()) newPersonajeName = "ID: $newPersonajeId"
                val libToAdd = Personaje(newPersonajeId,newPersonajeName)
                personajes.add(libToAdd)
            }
            adapter =
                PersonajeAdapter(personajes) { personaje -> //Toast.makeText(PersonajesActivity.this, personaje.getNombre(), Toast.LENGTH_SHORT).show();
                    val personaje_activity = Intent(this@PersonajesActivity, PersonajeActivity::class.java)
                    personaje_activity.putExtra("name", personaje.nombreCompleto)
                    personaje_activity.putExtra("id", personaje.id.toString())
                    startActivity(personaje_activity)
                }
            rvPersonajes.setAdapter(adapter)
            tvPag.text = "Página: $page"
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