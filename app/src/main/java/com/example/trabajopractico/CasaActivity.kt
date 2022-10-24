package com.example.trabajopractico

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajopractico.DetailAdapter.ItemTextAdapter
import com.example.trabajopractico.cache.CasaDataManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class CasaActivity : AppCompatActivity() {
    private lateinit var mi_toolbar: Toolbar
    private lateinit var tvName: TextView
    private lateinit var tvRegion: TextView
    private lateinit var tvCoatOfArms: TextView
    private lateinit var tvWords: TextView
    private lateinit var tvTitles: TextView
    private lateinit var tvSeats: TextView
    private lateinit var tvCurrentLord: TextView
    private lateinit var tvHeir: TextView
    private lateinit var tvOverlord: TextView
    private lateinit var tvFounded: TextView
    private lateinit var tvFounder: TextView
    private lateinit var tvDiedOut: TextView
    private lateinit var tvAncestralWeapons: TextView

    private lateinit var rvSwornMembers: RecyclerView
    private lateinit var rvCadetBranches: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_casa)
        var casaId = 0
        var toolbar_title = "nombre casa"
        val bundle = intent.extras
        if (bundle != null) {
            toolbar_title = bundle.getString("name").toString()
            casaId = bundle.getString("id").toString().toInt()
        }
        mi_toolbar = findViewById(R.id.mi_toolbar)
        mi_toolbar.setTitle(toolbar_title)
        mi_toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(mi_toolbar)
        tvName = findViewById(R.id.tvName)
        tvRegion = findViewById(R.id.tvRegion)
        tvCoatOfArms = findViewById(R.id.tvCoatOfArms)
        tvWords = findViewById(R.id.tvWords)
        tvTitles = findViewById(R.id.tvTitles)
        tvSeats = findViewById(R.id.tvSeats)
        tvCurrentLord = findViewById(R.id.tvCurrentLord)
        tvHeir = findViewById(R.id.tvHeir)
        tvOverlord = findViewById(R.id.tvOverlord)
        tvFounded = findViewById(R.id.tvFounded)
        tvFounder = findViewById(R.id.tvFounder)
        tvDiedOut = findViewById(R.id.tvDiedOut)
        tvAncestralWeapons = findViewById(R.id.tvAncestralWeapons)
        rvSwornMembers = findViewById(R.id.rvSwornMembers)
        rvCadetBranches = findViewById(R.id.rvCadetBranches)

        val insCasaDataMan = CasaDataManager.getInstancia(this@CasaActivity)
        val res = insCasaDataMan.getCasaData(casaId)
        if(res == null){
            Log.d("REST", "No Cache, pido datos a la api")
            val api = RetrofitClient.retrofit.create(CasaAPI::class.java)
            val callGetCasa = api.getCasa(casaId)
            callGetCasa.enqueue(object : retrofit2.Callback<CasaData>{
                override fun onResponse(call: Call<CasaData>, response : Response<CasaData>){
                    val casa = response.body()
                    if(casa != null){
                        setDataToView(casa)
                        val newCasaId = casa.url.takeLastWhile{ caracter -> caracter!='/' }.toInt()
                        val gson = Gson()
                        casa.id = newCasaId
                        casa.titlesString = gson.toJson(casa.titles)
                        casa.seatsString = gson.toJson(casa.seats)
                        casa.ancestralWeaponsString = gson.toJson(casa.ancestralWeapons)
                        casa.swornMembersString = gson.toJson(casa.swornMembers)
                        casa.cadetBranchesString = gson.toJson(casa.cadetBranches)
                        insCasaDataMan.agregarCasaData(casa)
                    }
                }
                override fun onFailure(call: Call<CasaData>, t : Throwable){
                    Log.e("REST", t.message?:"")
                }
            }
            )
        }
        else{
            Log.d("DB", "Cache, muestro datos de la base")
            val gson = Gson()
            res.titles = gson.fromJson(res.titlesString, Array<String>::class.java).toList()
            res.seats = gson.fromJson(res.seatsString, Array<String>::class.java).toList()
            res.ancestralWeapons = gson.fromJson(res.ancestralWeaponsString, Array<String>::class.java).toList()
            res.swornMembers = gson.fromJson(res.swornMembersString, Array<String>::class.java).toList()
            res.cadetBranches = gson.fromJson(res.cadetBranchesString, Array<String>::class.java).toList()
            setDataToView(res)
        }
    }

    private fun setDataToView(casa: CasaData) {
        tvName.text = casa.name
        tvRegion.text = casa.region
        tvCoatOfArms.text = casa.coatOfArms
        tvWords.text = casa.words
        tvTitles.text = convertStringList(casa.titles)
        tvSeats.text = convertStringList(casa.seats)
        loadCharacterForTv(casa.currentLord,tvCurrentLord)
        loadCharacterForTv(casa.heir,tvHeir)
        loadHouseForTv(casa.overlord, tvOverlord)
        tvFounded.text = casa.founded
        loadCharacterForTv(casa.founder, tvFounder)
        tvDiedOut.text = casa.diedOut
        tvAncestralWeapons.text = convertStringList(casa.ancestralWeapons)

        rvSwornMembers.adapter = ItemTextAdapter(this, createListItemTextForAdapter("PERSONAJE", casa.swornMembers))
        rvCadetBranches.adapter = ItemTextAdapter(this, createListItemTextForAdapter("CASA", casa.cadetBranches))
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
            val manager = CasaManager.getInstancia(this@CasaActivity)
            var casa: Casa? = null
            val bundle = intent.extras
            if (bundle != null) {
                casa = Casa(bundle.getString("id")!!.toInt(), bundle.getString("name"))
            }
            try {
                if (casa != null) {
                    val casaGuardado = manager.getCasaFavorito(casa.id)
                    if (casaGuardado == null) {
                        manager.agregarCasaFavorito(casa)
                        this@CasaActivity.mensajeCorto("Casa Agregada a Favoritos")
                    } else {
                        manager.deleteCasaFavorito(casa.id)
                        Toast.makeText(
                            this@CasaActivity,
                            "Casa Eliminada de Favoritos",
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