package com.example.trabajopractico

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.trabajopractico.DetailAdapter.ItemText
import com.example.trabajopractico.cache.CasaDataManager
import com.example.trabajopractico.cache.PersonajeDataManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

fun Context.mensajeCorto(texto: String){
    Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
}

fun convertStringList(list : List<String>): String {
    var result = ""
    for(item in list){
        result += item
        if(list.last()!=item){
            result += "\n"
        }
    }
    return result
}

fun Context.loadCharacterForTv(url : String, tv : TextView){
    if(!url.isEmpty()){
        fun asignFunctionOnText(id: Int, name: String) {
            tv.setOnClickListener(View.OnClickListener {
                Log.i("TODO", "Se apretó el botón Personaje")
                val personaje_activity = Intent(this, PersonajeActivity::class.java)
                personaje_activity.putExtra("name", name)
                personaje_activity.putExtra("id", id.toString())
                startActivity(personaje_activity)
            })
        }

        val personajeId = url.takeLastWhile{ caracter -> caracter!='/' }.toInt()
        val insPersonajeDataMan = PersonajeDataManager.getInstancia(this)
        val res = insPersonajeDataMan.getPersonajeData(personajeId)
        if(res == null) {
            Log.d("REST", "No Cache, pido personaje a la api")
            val api = RetrofitClient.retrofit.create(PersonajeAPI::class.java)
            val callGetPersonaje = api.getPersonaje(personajeId)
            callGetPersonaje.enqueue(object : retrofit2.Callback<PersonajeData> {
                override fun onResponse(call: Call<PersonajeData>, response: Response<PersonajeData>) {
                    val personaje = response.body()
                    if (personaje != null) {
                        val newPersonajeId =
                            personaje.url.takeLastWhile { caracter -> caracter != '/' }.toInt()
                        val gson = Gson()
                        personaje.id = newPersonajeId
                        tv.text = personaje.name
                        //personaje.titlesString = gson.toJson(personaje.titles)
                        //personaje.seatsString = gson.toJson(personaje.seats)
                        insPersonajeDataMan.agregarPersonajeData(personaje)
                        asignFunctionOnText(personaje.id,personaje.name)
                    }
                }

                override fun onFailure(call: Call<PersonajeData>, t: Throwable) {
                    Log.e("REST", t.message ?: "")
                }
            }
            )
        }
        else{
            Log.d("BD", "Cache, pido personaje a la bdd")
            tv.text = res.name
            asignFunctionOnText(res.url.takeLastWhile { caracter -> caracter != '/' }.toInt(),res.name)
        }
    }else{
        tv.text = "-"
    }
}
fun Context.loadHouseForTv(url : String, tv : TextView){
    if(!url.isEmpty()){
        fun asignFunctionOnText(id: Int, name: String) {
            tv.setOnClickListener(View.OnClickListener {
                Log.i("TODO", "Se apretó el botón Casa")
                val casa_activity = Intent(this, CasaActivity::class.java)
                casa_activity.putExtra("name", name)
                casa_activity.putExtra("id", id.toString())
                startActivity(casa_activity)
            })
        }

        val casaId = url.takeLastWhile{ caracter -> caracter!='/' }.toInt()
        val insCasaDataMan = CasaDataManager.getInstancia(this)
        val res = insCasaDataMan.getCasaData(casaId)
        if(res == null) {
            Log.d("REST", "No Cache, pido casa a la api")
            val api = RetrofitClient.retrofit.create(CasaAPI::class.java)
            val callGetCasa = api.getCasa(casaId)
            callGetCasa.enqueue(object : retrofit2.Callback<CasaData> {
                override fun onResponse(call: Call<CasaData>, response: Response<CasaData>) {
                    val casa = response.body()
                    if (casa != null) {
                        val newCasaId =
                            casa.url.takeLastWhile { caracter -> caracter != '/' }.toInt()
                        val gson = Gson()
                        casa.id = newCasaId
                        tv.text = casa.name
                        casa.titlesString = gson.toJson(casa.titles)
                        casa.seatsString = gson.toJson(casa.seats)
                        casa.ancestralWeaponsString = gson.toJson(casa.ancestralWeapons)
                        casa.swornMembersString = gson.toJson(casa.swornMembers)
                        casa.cadetBranchesString = gson.toJson(casa.cadetBranches)
                        insCasaDataMan.agregarCasaData(casa)
                        asignFunctionOnText(casa.id,casa.name)
                    }
                }

                override fun onFailure(call: Call<CasaData>, t: Throwable) {
                    Log.e("REST", t.message ?: "")
                }
            }
            )
        }
        else{
            Log.d("BD", "Cache, pido casa a la bdd")
            tv.text = res.name
            asignFunctionOnText(res.url.takeLastWhile { caracter -> caracter != '/' }.toInt(),res.name)
        }
    }else{
        tv.text = "-"
    }
}

fun createListItemTextForAdapter(type:String, urls:List<String>) : List<ItemText>{
    val res = ArrayList<ItemText>()
    for(url in urls){
        val item = ItemText()
        item.type = type
        item.url = url
        res.add(item)
    }
    return res
}
