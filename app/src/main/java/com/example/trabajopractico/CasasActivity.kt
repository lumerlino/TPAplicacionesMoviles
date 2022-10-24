package com.example.trabajopractico

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajopractico.cache.CasaDataManager
import com.example.trabajopractico.cache.CasaPage
import com.example.trabajopractico.cache.CasaPageManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response


class CasasActivity : AppCompatActivity() {
    private lateinit var rvCasas: RecyclerView
    private lateinit var adapter: CasaAdapter
    private lateinit var mi_toolbar: Toolbar
    private lateinit var btnNext: ImageButton
    private lateinit var btnPrevious: ImageButton
    private lateinit var tvPag: TextView
    private var page: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_casas)
        mi_toolbar = findViewById(R.id.mi_toolbar)
        mi_toolbar.setTitle("Casas")
        mi_toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(mi_toolbar)
        //getSupportActionBar().setTitle("Casas");


        btnNext = findViewById(R.id.btnNext)
        btnPrevious = findViewById(R.id.btnPrevious)
        tvPag = findViewById(R.id.tvPag)

        setUpAdapter(page)

        btnNext.setOnClickListener(View.OnClickListener {
            if(page<9) {
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
        rvCasas = findViewById(R.id.rvCasas)
        val casas: MutableList<Casa> = ArrayList()

        val insCasaPageMan = CasaPageManager.getInstancia(this@CasasActivity)
        val res = insCasaPageMan.getCasaPage(pag)
        val insCasaDataMan = CasaDataManager.getInstancia(this@CasasActivity)
        if(res == null){
            Log.d("REST", "no cache, intento llamar a la api")
            val api = RetrofitClient.retrofit.create(CasaAPI::class.java)
            val callGetCasa = api.getCasas(pag,50)
            callGetCasa.enqueue(object : retrofit2.Callback<List<CasaData>>{
                override fun onResponse(call: Call<List<CasaData>>, response : Response<List<CasaData>>){
                    val casasData = response.body()
                    if(casasData != null){
                        if(casasData.isEmpty()){
                            page -=1
                        }
                        else{
                            Log.d("REST", casasData.toString())
                            val gson = Gson()
                            for(casa in casasData){
                                val newCasaId = casa.url.takeLastWhile{ caracter -> caracter!='/' }.toInt()
                                val libToAdd = Casa(newCasaId,casa.name)
                                casas.add(libToAdd)
                                //guardado en la base para cache:
                                casa.page = pag
                                casa.id = newCasaId
                                casa.titlesString = gson.toJson(casa.titles)
                                casa.seatsString = gson.toJson(casa.seats)
                                casa.ancestralWeaponsString = gson.toJson(casa.ancestralWeapons)
                                casa.swornMembersString = gson.toJson(casa.swornMembers)
                                casa.cadetBranchesString = gson.toJson(casa.cadetBranches)
                                insCasaDataMan.agregarCasaData(casa)
                            }

                            //guardar pag en base:
                            insCasaPageMan.agregarCasaPage(CasaPage(pag))
                            adapter =
                                CasaAdapter(casas) { casa -> //Toast.makeText(CasasActivity.this, casa.getNombre(), Toast.LENGTH_SHORT).show();
                                    val casa_activity = Intent(this@CasasActivity, CasaActivity::class.java)
                                    casa_activity.putExtra("name", casa.nombre)
                                    casa_activity.putExtra("id", casa.id.toString())
                                    startActivity(casa_activity)
                                }
                            rvCasas.setAdapter(adapter)
                            tvPag.text = "Página: $page"
                        }

                    }
                }
                override fun onFailure(call: Call<List<CasaData>>, t : Throwable){
                    Log.e("REST", t.message?:"")
                    Log.e("REST", t.toString()?:"")

                }
            }
            )
        }
        else{
            Log.d("DB", "trayendo casas de la base")
            val casasData = insCasaDataMan.getCasasData(pag)
            for(casa in casasData){
                val libToAdd = Casa(casa.url.takeLastWhile{ caracter -> caracter!='/' }.toInt(),casa.name)
                casas.add(libToAdd)
            }
            adapter =
                CasaAdapter(casas) { casa -> //Toast.makeText(CasasActivity.this, casa.getNombre(), Toast.LENGTH_SHORT).show();
                    val casa_activity = Intent(this@CasasActivity, CasaActivity::class.java)
                    casa_activity.putExtra("name", casa.nombre)
                    casa_activity.putExtra("id", casa.id.toString())
                    startActivity(casa_activity)
                }
            rvCasas.setAdapter(adapter)
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