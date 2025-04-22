package org.example.reservasdevuelos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.example.reservasdevuelos.R
import org.example.reservasdevuelos.databinding.ActivityDetailBinding
import org.example.reservasdevuelos.model.Flight

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val vuelo = intent.getSerializableExtra("vuelo") as? Flight
        if (vuelo == null) {
            finish()
            return
        }


        mostrarDetalles(vuelo)
    }

    private fun mostrarDetalles(vuelo: Flight) {


        val ciudadImagenMap = mapOf(
            "Madrid" to R.drawable.madrid,
            "Barcelona" to R.drawable.barcelona,
            "París" to R.drawable.paris,
            "Londres" to R.drawable.londres,
            "Marrakech" to R.drawable.marrakech,
            "Roma" to R.drawable.roma,
            "Berlín" to R.drawable.berlin
        )

        ciudadImagenMap[vuelo.origen]?.let { binding.imgDetalleOrigen.setImageResource(it) }
        ciudadImagenMap[vuelo.destino]?.let { binding.imgDetalleDestino.setImageResource(it) }


        binding.tvDetalleOrigen.text = vuelo.origen
        binding.tvDetalleDestino.text = vuelo.destino
    }
}