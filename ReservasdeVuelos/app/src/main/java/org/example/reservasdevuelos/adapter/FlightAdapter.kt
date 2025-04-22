package org.example.reservasdevuelos.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.example.reservasdevuelos.R
import org.example.reservasdevuelos.model.Flight
import org.example.reservasdevuelos.ui.DetailActivity

class FlightAdapter(
    private val lista: ArrayList<Flight>,
    private val context: Context,
    private val esPantallaFavoritos: Boolean = false

) : RecyclerView.Adapter<FlightAdapter.MyHolder>() {




    private val ciudadImagenMap = mapOf(
        "Madrid" to R.drawable.madrid,
        "Barcelona" to R.drawable.barcelona,
        "París" to R.drawable.paris,
        "Londres" to R.drawable.londres,
        "Marrakech" to R.drawable.marrakech,
        "Roma" to R.drawable.roma,
        "Berlín" to R.drawable.berlin
    )



    interface OnFlightListener {
        fun onFlightSelected(flight: Flight)
        fun onFavoritoClicked(flight: Flight)
        fun onEliminarFavoritoClicked(flight: Flight)
    }


    private val listener: OnFlightListener = context as? OnFlightListener
        ?: throw RuntimeException("$context")





    class MyHolder(item: View) : RecyclerView.ViewHolder(item) {
        val imgOrigin: ImageView = item.findViewById(R.id.imgOrigin)
        val imgDestination: ImageView = item.findViewById(R.id.imgDestination)
        val tvOrigin: TextView = item.findViewById(R.id.tvOrigin)
        val tvDestination: TextView = item.findViewById(R.id.tvDestination)
        val btnFavorito: Button = item.findViewById(R.id.btnFavorito)
        val btnEliminarFavorito: Button = item.findViewById(R.id.btnEliminarFavorito)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_flight, parent, false)
        return MyHolder(view)
    }



    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val flight = lista[position]


        ciudadImagenMap[flight.origen]?.let { holder.imgOrigin.setImageResource(it) }
        ciudadImagenMap[flight.destino]?.let { holder.imgDestination.setImageResource(it) }


        holder.tvOrigin.text = flight.origen
        holder.tvDestination.text = flight.destino



        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("vuelo", flight)
            }
            context.startActivity(intent)
        }


        if (esPantallaFavoritos) {
            holder.btnFavorito.visibility = View.GONE
            holder.btnEliminarFavorito.visibility = View.GONE
        } else {



            holder.btnFavorito.setOnClickListener {
                listener.onFavoritoClicked(flight)
            }


            holder.btnEliminarFavorito.setOnClickListener {
                listener.onEliminarFavoritoClicked(flight)
            }


            if (flight.esFavorito) {
                holder.btnFavorito.visibility = View.GONE
                holder.btnEliminarFavorito.visibility = View.VISIBLE
            } else {
                holder.btnFavorito.visibility = View.VISIBLE
                holder.btnEliminarFavorito.visibility = View.GONE
            }
        }
    }


    override fun getItemCount(): Int {
        return lista.size
    }
}