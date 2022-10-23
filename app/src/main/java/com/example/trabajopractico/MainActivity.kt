package com.example.trabajopractico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trabajopractico.R
import android.content.Intent
import com.example.trabajopractico.PersonajesActivity
import com.example.trabajopractico.CasasActivity
import com.example.trabajopractico.LibrosActivity
import com.example.trabajopractico.Constantes
import android.widget.Toast
import com.example.trabajopractico.PersonajesFavoritosActivity
import com.example.trabajopractico.LibrosFavoritosActivity
import com.example.trabajopractico.CasasFavoritosActivity
import com.example.trabajopractico.LoginActivity
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var btnPersonajes: Button
    private lateinit var btnCasas: Button
    private lateinit var btnLibros: Button
    private lateinit var mi_toolbar: Toolbar

    private lateinit var btnAPIYES : Button
    private lateinit var ivImagenPicasso : ImageView
    private lateinit var tvAnswer : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saludarUsuario()

        mi_toolbar = findViewById(R.id.mi_toolbar)
        mi_toolbar.setTitle("Home")
        mi_toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(mi_toolbar)

        btnPersonajes = findViewById(R.id.btnPersonajes)
        btnCasas = findViewById(R.id.btnCasas)
        btnLibros = findViewById(R.id.btnLibros)

        btnAPIYES = findViewById(R.id.btnAPIYES)
        ivImagenPicasso = findViewById(R.id.ivImagenPicasso)
        tvAnswer = findViewById(R.id.tvAnswer)

        btnPersonajes.setOnClickListener(View.OnClickListener {
            Log.i("TODO", "Se apretó el botón Personajes")
            val personajes_activity = Intent(this@MainActivity, PersonajesActivity::class.java)
            startActivity(personajes_activity)
        })
        btnCasas.setOnClickListener(View.OnClickListener {
            Log.i("TODO", "Se apretó el botón Casas")
            val casas_activity = Intent(this@MainActivity, CasasActivity::class.java)
            startActivity(casas_activity)
        })
        btnLibros.setOnClickListener(View.OnClickListener {
            Log.i("TODO", "Se apretó el botón Libros")
            val libros_activity = Intent(this@MainActivity, LibrosActivity::class.java)
            startActivity(libros_activity)
        })


        btnAPIYES.setOnClickListener(View.OnClickListener {
            Log.i("TODO", "Se apretó el botón API YES NO")
            val api = RetrofitClientYESNO.retrofit.create(YESNOAPI::class.java)
            val callGetYESNO = api.getYESNO()
            callGetYESNO.enqueue(object : retrofit2.Callback<YESNOData>{
                override fun onResponse(call: Call<YESNOData>, response : Response<YESNOData>){
                    val YESNO = response.body()
                    if(YESNO != null){
                        Log.d("REST", YESNO.toString())
                        tvAnswer.text = YESNO.answer
                        Log.d("answer:",YESNO.answer)
                        val path = YESNO.image
                        Log.d("image:", YESNO.image)
                        //Picasso.get().load(path).into(ivImagenPicasso)
                        Glide.with(getApplicationContext()).load(path).into(ivImagenPicasso);
                    }else{
                        Log.i("TODO", response.headers().toString())
                    }
                }
                override fun onFailure(call: Call<YESNOData>, t : Throwable){
                    Log.e("REST", t.message?:"")
                }
            }
            )
        })


    }

    private fun saludarUsuario() {
        val bundle = intent.extras
        if (bundle != null) {
            val usuario = bundle.getString(Constantes.USUARIO)
            Toast.makeText(this, "Bienvenido/a $usuario", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_nav, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuPersonajesFavoritos -> {
                val personajes_activity =
                    Intent(this@MainActivity, PersonajesFavoritosActivity::class.java)
                startActivity(personajes_activity)
            }
            R.id.menuLibrosFavoritos -> {
                val libros_activity = Intent(this@MainActivity, LibrosFavoritosActivity::class.java)
                startActivity(libros_activity)
            }
            R.id.menuCasasFavoritas -> {
                val casas_activity = Intent(this@MainActivity, CasasFavoritosActivity::class.java)
                startActivity(casas_activity)
            }
            R.id.menuSalir -> {
                val login_activity = Intent(this@MainActivity, LoginActivity::class.java)
                val prefs = applicationContext.getSharedPreferences(
                    Constantes.SP_CREDENCIALES,
                    MODE_PRIVATE
                )
                val editor = prefs.edit()
                editor.remove(Constantes.USUARIO)
                editor.remove(Constantes.PASSWORD)
                editor.apply()
                startActivity(login_activity)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}