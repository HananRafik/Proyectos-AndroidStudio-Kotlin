package org.example.reservasdevuelos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.example.reservasdevuelos.Data.DataSet
import org.example.reservasdevuelos.adapter.FlightAdapter
import org.example.reservasdevuelos.databinding.ActivityMainBinding
import org.example.reservasdevuelos.model.Flight

class MainActivity : AppCompatActivity(), FlightAdapter.OnFlightListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var flightAdapter: FlightAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        configurarSpinners()


        configurarRecyclerView()


        binding.btnAgregar.setOnClickListener {
            agregarVuelo()
        }



        binding.btnVerFavoritos.setOnClickListener {
            val favoritos = DataSet.vuelos.filter { it.esFavorito }
            if (favoritos.isEmpty()) {
                Snackbar.make(binding.root, "No hay vuelos favoritos", Snackbar.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, FavoritesActivity::class.java).apply {
                    putExtra("favoritos", ArrayList(favoritos)) // Pasar la lista como Serializable
                }
                startActivity(intent)
            }
        }
    }


    private fun configurarSpinners() {

        val ciudades = listOf("Madrid", "Barcelona", "París", "Londres", "Marrakech")


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ciudades)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerOrigin.adapter = adapter
        binding.spinnerDestino.adapter = adapter
    }


    private fun configurarRecyclerView() {


        flightAdapter = FlightAdapter(DataSet.vuelos, this)
        binding.listaVuelos.layoutManager = LinearLayoutManager(this)
        binding.listaVuelos.adapter = flightAdapter
    }

    private fun agregarVuelo() {
        val origen = binding.spinnerOrigin.selectedItem.toString()
        val destino = binding.spinnerDestino.selectedItem.toString()


        if (origen.isEmpty() || destino.isEmpty()) {
            Snackbar.make(binding.root, "Selecciona origen y destino", Snackbar.LENGTH_SHORT).show()
            return
        }


        val nuevoVuelo = Flight(
            id = DataSet.vuelos.size + 1,
            origen = origen,
            destino = destino
        )




        DataSet.vuelos.add(nuevoVuelo)


        flightAdapter.notifyDataSetChanged()

        Snackbar.make(binding.root, "Vuelo agregado: $origen - $destino", Snackbar.LENGTH_SHORT).show()
    }

    override fun onFlightSelected(flight: Flight) {


    }

    override fun onFavoritoClicked(flight: Flight) {

        flight.esFavorito = true
        flightAdapter.notifyDataSetChanged()
        Snackbar.make(binding.listaVuelos, "${flight.origen} - ${flight.destino} añadido a favoritos", Snackbar.LENGTH_SHORT).show()
    }

    override fun onEliminarFavoritoClicked(flight: Flight) {

        flight.esFavorito = false
        flightAdapter.notifyDataSetChanged()
        Snackbar.make(binding.listaVuelos, "${flight.origen} - ${flight.destino} eliminado de favoritos", Snackbar.LENGTH_SHORT).show()
    }
}