package org.example.reservasdevuelos.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.example.reservasdevuelos.adapter.FlightAdapter
import org.example.reservasdevuelos.databinding.ActivityFavoritesBinding
import org.example.reservasdevuelos.model.Flight

class FavoritesActivity:AppCompatActivity (), FlightAdapter.OnFlightListener{
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var flightAdapter: FlightAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val favoritos = intent.getSerializableExtra("favoritos") as? ArrayList<Flight>
        if (favoritos == null || favoritos.isEmpty()) {
            Snackbar.make(binding.root, "No hay vuelos favoritos", Snackbar.LENGTH_SHORT).show()
            finish()


        } else {

            Log.d("FavoritesActivity", "Favoritos: $favoritos")
            configurarRecyclerView(favoritos)
        }

    }

    private fun configurarRecyclerView(favoritos: List<Flight>) {
        flightAdapter = FlightAdapter(favoritos.toMutableList() as ArrayList<Flight>, this, true)
        binding.listaFavoritos.layoutManager = LinearLayoutManager(this)
        binding.listaFavoritos.adapter = flightAdapter
    }


    override fun onFlightSelected(flight: Flight) {

    }


    override fun onFavoritoClicked(flight: Flight) {

    }


    override fun onEliminarFavoritoClicked(flight: Flight) {

    }

}