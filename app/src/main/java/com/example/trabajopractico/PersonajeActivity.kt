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
import com.example.trabajopractico.PersonajeManager
import com.example.trabajopractico.Personaje
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajopractico.DetailAdapter.ItemTextAdapter
import com.example.trabajopractico.cache.PersonajeDataManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class PersonajeActivity : AppCompatActivity() {
    private lateinit var mi_toolbar: Toolbar
    private lateinit var tvName: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvCulture: TextView
    private lateinit var tvBorn: TextView
    private lateinit var tvDied: TextView
    private lateinit var tvTitles: TextView
    private lateinit var tvAliases: TextView
    private lateinit var tvFather: TextView
    private lateinit var tvMother: TextView
    private lateinit var tvSpouse: TextView

    private lateinit var rvAllegiances: RecyclerView
    private lateinit var rvBooks: RecyclerView
    private lateinit var tvTvSeries: TextView
    private lateinit var tvPlayedBy: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje)
        var personajeId = 0
        var toolbar_title = "nombre personaje"
        val bundle = intent.extras
        if (bundle != null) {
            toolbar_title = bundle.getString("name").toString()
            personajeId = bundle.getString("id").toString().toInt()
        }
        mi_toolbar = findViewById(R.id.mi_toolbar)
        mi_toolbar.setTitle(toolbar_title)
        mi_toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(mi_toolbar)
        tvName = findViewById(R.id.tvName)
        tvGender = findViewById(R.id.tvGender)
        tvCulture = findViewById(R.id.tvCulture)
        tvBorn = findViewById(R.id.tvBorn)
        tvDied = findViewById(R.id.tvDied)
        tvTitles = findViewById(R.id.tvTitles)
        tvAliases = findViewById(R.id.tvAliases)
        tvFather = findViewById(R.id.tvFather)
        tvMother = findViewById(R.id.tvMother)
        tvSpouse = findViewById(R.id.tvSpouse)
        rvAllegiances = findViewById(R.id.rvAllegiances)
        rvBooks = findViewById(R.id.rvBooks)
        tvTvSeries = findViewById(R.id.tvTvSeries)
        tvPlayedBy = findViewById(R.id.tvPlayedBy)



        val insPersonajeDataMan = PersonajeDataManager.getInstancia(this@PersonajeActivity)
        val res = insPersonajeDataMan.getPersonajeData(personajeId)
        if(res == null){
            Log.d("REST", "No Cache, pido datos a la api")
            val api = RetrofitClient.retrofit.create(PersonajeAPI::class.java)
            val callGetPersonaje = api.getPersonaje(personajeId)
            callGetPersonaje.enqueue(object : retrofit2.Callback<PersonajeData>{
                override fun onResponse(call: Call<PersonajeData>, response : Response<PersonajeData>){
                    val personaje = response.body()
                    if(personaje != null){
                        setDataToView(personaje)
                        val newPersonajeId = personaje.url.takeLastWhile{ caracter -> caracter!='/' }.toInt()
                        val gson = Gson()
                        personaje.id = newPersonajeId
                        personaje.titlesString = gson.toJson(personaje.titles)
                        personaje.aliasesString = gson.toJson(personaje.aliases)
                        personaje.allegiancesString = gson.toJson(personaje.allegiances)
                        personaje.booksString = gson.toJson(personaje.books)
                        personaje.tvSeriesString = gson.toJson(personaje.tvSeries)
                        personaje.playedByString = gson.toJson(personaje.playedBy)
                        insPersonajeDataMan.agregarPersonajeData(personaje)
                    }
                }
                override fun onFailure(call: Call<PersonajeData>, t : Throwable){
                    Log.e("REST", t.message?:"")
                }
            }
            )
        }
        else{
            Log.d("DB", "Cache, muestro datos de la base")
            val gson = Gson()
            res.titles = gson.fromJson(res.titlesString, Array<String>::class.java).toList()
            res.aliases = gson.fromJson(res.aliasesString, Array<String>::class.java).toList()
            res.allegiances = gson.fromJson(res.allegiancesString, Array<String>::class.java).toList()
            res.books = gson.fromJson(res.booksString, Array<String>::class.java).toList()
            res.tvSeries = gson.fromJson(res.tvSeriesString, Array<String>::class.java).toList()
            res.playedBy = gson.fromJson(res.playedByString, Array<String>::class.java).toList()
            setDataToView(res)
        }
    }

    private fun setDataToView(personaje: PersonajeData) {
        tvName.text = personaje.name
        tvGender.text = personaje.gender
        tvCulture.text = personaje.culture
        tvBorn.text = personaje.born
        tvDied.text = personaje.died
        tvTitles.text = convertStringList(personaje.titles)
        tvAliases.text = convertStringList(personaje.aliases)
        loadCharacterForTv(personaje.father,tvFather)
        loadCharacterForTv(personaje.mother,tvMother)
        loadCharacterForTv(personaje.spouse,tvSpouse)
        rvAllegiances.adapter = ItemTextAdapter(this, createListItemTextForAdapter("CASA", personaje.allegiances))
        rvBooks.adapter = ItemTextAdapter(this, createListItemTextForAdapter("LIBRO", personaje.books))
        tvTvSeries.text = convertStringList(personaje.tvSeries)
        tvPlayedBy.text = convertStringList(personaje.playedBy)
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
            val manager = PersonajeManager.getInstancia(this@PersonajeActivity)
            var personaje: Personaje? = null
            val bundle = intent.extras
            if (bundle != null) {
                personaje = Personaje(bundle.getString("id")!!.toInt(), bundle.getString("name"))
            }
            try {
                if (personaje != null) {
                    val personajeGuardado = manager.getPersonajeFavorito(personaje.id)
                    if (personajeGuardado == null) {
                        manager.agregarPersonajeFavorito(personaje)
                        Toast.makeText(
                            this@PersonajeActivity,
                            "Personaje Agregado a Favoritos",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        manager.deletePersonajeFavorito(personaje.id)
                        Toast.makeText(
                            this@PersonajeActivity,
                            "Personaje Eliminado de Favoritos",
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