package com.example.appcovid19

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.appcovid19.databinding.ActivityMainBinding
import org.json.JSONException
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var adaptadorPaises:PaisesAdapter?=null
    var listaPaises:ArrayList<Pais>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backgroundColor()

        binding.miRecyclerCovid.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.miRecyclerCovid.adapter = adaptadorPaises

        val queue = Volley.newRequestQueue(this)
        val url = "http://wuhan-coronavirus-api.laeyoung.endpoint.ainize.ai/jhu-edu/latest"

        val peticionDatosCovid = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            for (index in 0..response.length() - 1) {
                try {
                    val paisJson = response.getJSONObject(index)
                    val nombrePais = paisJson.getString("countryregion")
                    var numeroConfirmados = paisJson.getInt("confirmed")
                    val numeroMuertos = paisJson.getInt("deaths")
                    val numeroRecuperados = paisJson.getInt("recovered")
                    val countryCodeJson = paisJson.getJSONObject("countrycode")
                    val codigoPais = countryCodeJson.getString("iso2")
                    //numeroConfirmados = DecimalFormat(numeroConfirmados)
                    //objeto de kotlin
                    val paisIndividual = Pais(
                        nombrePais,
                        numeroConfirmados,
                        numeroMuertos,
                        numeroRecuperados,
                        codigoPais
                    )
                    listaPaises!!.add(paisIndividual)
                } catch (e: JSONException) {
                    Log.wtf("JsonError", e.localizedMessage)
                }
            }

            listaPaises!!.sortByDescending { it.confirmados }
            adaptadorPaises!!.notifyDataSetChanged()
        },
            { error ->
                Log.e("error_volley", error.localizedMessage)
            })

        queue.add(peticionDatosCovid)

    }

    private fun backgroundColor() {
        listaPaises = ArrayList<Pais>()
        adaptadorPaises = PaisesAdapter(listaPaises!!, this)

        var androidColors = resources.getIntArray(R.array.colors)
        //var randomAndroidColor = androidColors[Random.nextInt(androidColors.size)]
        //binding.miRecyclerCovid.setBackgroundColor(randomAndroidColor)
        adaptadorPaises!!.enviarDatos(androidColors)
    }
}