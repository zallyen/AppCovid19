package com.example.appcovid19

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.random.Random


class PaisesAdapter(paises: ArrayList<Pais>, contexto: Context): RecyclerView.Adapter<PaisesAdapter.ViewHolder>(){
    var listaPaises:ArrayList<Pais>?=null
    var contexto:Context?=null
    var color:Int = 0
    var androidColors:IntArray?=null

    init {
        this.listaPaises = paises
        this.contexto = contexto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vistaPais:View = LayoutInflater.from(contexto).inflate(
            R.layout.pais_item,
            parent,
            false
        )
        val paisViewHolder = ViewHolder(vistaPais)
        vistaPais.tag = paisViewHolder
        return paisViewHolder
    }

    override fun getItemCount(): Int {
        return listaPaises!!.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombrePais!!.text = listaPaises!![position].nombre
        holder.numeroConfirmados!!.text = "${listaPaises!![position].confirmados}"
        holder.numeroMuertos!!.text = "${listaPaises!![position].muertos}"
        holder.numeroRecuperados!!.text = "${listaPaises!![position].recuperados}"
        Picasso.get().load("https://www.countryflags.io/${listaPaises!![position].codigoPais}/flat/64.png").into(
            holder.bandera
        )
        holder.cartaPais?.setBackgroundColor(androidColors!![Random.nextInt(androidColors!!.size)])
    }




    class ViewHolder(vista: View):RecyclerView.ViewHolder(vista){
        var nombrePais:TextView?=null
        var numeroConfirmados:TextView?=null
        var numeroMuertos:TextView?=null
        var numeroRecuperados:TextView?=null
        var bandera:ImageView?=null
        var cartaPais:CardView?=null

        init {
            nombrePais = vista.findViewById(R.id.tvNombrePais)
            numeroConfirmados = vista.findViewById(R.id.tvConfirmados)
            numeroMuertos = vista.findViewById(R.id.tvMuertos)
            numeroRecuperados = vista.findViewById(R.id.tvRecuperados)
            bandera=vista.findViewById(R.id.ivBandera)
            cartaPais = vista.findViewById(R.id.cvPais)
        }

    }

    fun enviarDatos(androidColors: IntArray){
        this.androidColors = androidColors
        //var color = androidColors[Random.nextInt(androidColors.size)]
        //this.color = color
    }
}