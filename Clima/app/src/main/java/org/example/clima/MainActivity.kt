package org.example.clima

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.example.clima.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//Traducir
    private val traduccionesClima = mapOf(
        "clear" to "Despejado",
        "clouds" to "Nublado",
        "rain" to "Lluvia",
        "snow" to "Nieve",
        "thunderstorm" to "Tormenta eléctrica",
        "fog" to "Niebla",
        "drizzle" to "Llovizna"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Buscar la ciudad
        binding.btnBuscar.setOnClickListener {
            val ciudad = binding.etCiudad.text.toString()
            if (ciudad.isNotEmpty()) {
                obtenerDatosClima(ciudad)
            } else {
                binding.textoClima.text = "Por favor, ingresa un nombre de ciudad 🌍"
            }
        }
    }

    //URL de la api se  conecta con OpenWeather usando el nombre de la ciudad
    private fun obtenerDatosClima(city: String) {
        val apiKey = "ce1934ccf7ec186fd51abc851acd1318"
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"


        // usamos Corrutina para trabajar en el hilo
        // porque vamos a conectarnos a internet

        CoroutineScope(Dispatchers.IO).launch {
            try {

                //se conecta a la api usando OkHttpClient() y obtiene la respuesta JSON
                // esto pasa fuera del hilo principal, para que  la app no se congela

                //para hacer la solicitud
                val cliente = OkHttpClient()
                //construimos la solicitud con la url
                val solicitud = Request.Builder().url(url).build()

                //obtenemos la respuesta
                val respuesta = cliente.newCall(solicitud).execute()

                //extraemos los datos de la respuesta en formato json
                val datosJSON = respuesta.body?.string()


                //cambia al hilo principal
                // para que todolo que sacamos de la API se muestre:
                // temperatura, emoji del clima, fondos, etc.

                withContext(Dispatchers.Main) {

                    if (respuesta.isSuccessful && datosJSON != null) {
                        val jsonObject = JSONObject(datosJSON)

                        val main = jsonObject.getJSONObject("main")

                        //array con las condiciones del clima
                        val climaArray = jsonObject.getJSONArray("weather")
                        //clima actual
                        val condicionClima = climaArray.getJSONObject(0).getString("main")
                        //temperatura en grados Celsius
                        val temperatura = main.getDouble("temp")

                        // Obtener la traducción al español
                        val condicionClimaEspanol = traduccionesClima[condicionClima.lowercase()] ?: "Desconocido"

                        val emoji = when (condicionClima.lowercase()) {
                            "clear" -> "☀️"
                            "clouds" -> "☁️"
                            "rain" -> "🌧️"
                            "snow" -> "❄️"
                            "thunderstorm" -> "⛈️"
                            "fog" -> "🌫️"
                            "drizzle" -> "🌧️"
                            else -> "🌈"
                        }

                        val iconRes = when (condicionClima.lowercase()) {
                            "clear" -> R.drawable.soleado
                            "clouds" -> R.drawable.nublado
                            "rain" -> R.drawable.lluvia
                            "snow" -> R.drawable.snowicon
                            "fog" -> R.drawable.fogicon
                            "thunderstorm" -> R.drawable.thunderstorm
                            else -> R.drawable.drizzle
                        }

                        val backgroundRes = when (condicionClima.lowercase()) {
                            "clear" -> R.drawable.sunbackground
                            "clouds" -> R.drawable.dianublado
                            "rain" -> R.drawable.rainbackg
                            "snow" -> R.drawable.snow
                            "fog" -> R.drawable.fog
                            "thunderstorm" -> R.drawable.thunderstormback
                            "drizzle"->R.drawable.drizzleback
                            else -> R.drawable.back
                        }

                        // cambiamos el fondo del layout
                        binding.rootLayout.setBackgroundResource(backgroundRes)

                        // actualizamos los elementos graficos con los datos obtenidos
                        binding.ivIconoClima.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, iconRes))
                        binding.textoClima.text = "$condicionClimaEspanol $emoji"
                        binding.textoTemperatura.text = "$temperatura°"

                        obtenerPronosticoClima(city)

                    } else if (respuesta.code == 404) {
                        binding.textoInfoClima.text = "Ciudad no encontrada❌"
                    } else {
                        binding.textoClima.text = "Error: ${respuesta.code} - ${respuesta.message} 💔"
                    }
                }
            } catch (e: Exception) {
                //regresa al hilo principal y muestra el mensaje
                withContext(Dispatchers.Main) {
                    binding.textoClima.text = "Error al obtener los datos del clima: ${e.message} 🚨"
                }
            }
        }
    }

    private fun obtenerPronosticoClima(ciudad: String) {
        val apiKey = "ce1934ccf7ec186fd51abc851acd1318"
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$ciudad&appid=$apiKey&units=metric"

        binding.textoInfoClima.text = ""
        binding.textoInfoClima.visibility = View.VISIBLE


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cliente = OkHttpClient()
                val solicitud = Request.Builder().url(url).build()

                //ejecutamos la solicitud y obrenemos la respuesta
                val respuesta = cliente.newCall(solicitud).execute()

                //convertimos en un cuerpo de la respuesta en un string json
                val datosJSON = respuesta.body?.string()

                if (respuesta.isSuccessful && datosJSON != null) {
                    val jsonObject = JSONObject(datosJSON)
                    val lista = jsonObject.getJSONArray("list")

                    for (i in 0 until lista.length()) {
                        val dayForecast = lista.getJSONObject(i)
                        val main = dayForecast.getJSONObject("main")
                        val climaArray = dayForecast.getJSONArray("weather")
                        val condicionClima = climaArray.getJSONObject(0).getString("main")
                        val temperatura = main.getDouble("temp")
                        val tiempo = dayForecast.getString("dt_txt")

                        // Obtener la traducción al español
                        val condicionClimaEspanol = traduccionesClima[condicionClima.lowercase()] ?: "Desconocido"



                        // regresamos al hilo principal para actualizar la interfaz
                        withContext(Dispatchers.Main) {

                            val textoActual = binding.textoInfoClima.text.toString()
                            val nuevoTexto = "$textoActual\n$tiempo - $temperatura°, $condicionClimaEspanol"
                            binding.textoInfoClima.text = nuevoTexto.trim()
                        }
                        //pausa de 2 segundo
                        delay(2000L)
                    }
                } else {

                    withContext(Dispatchers.Main) {
                        binding.textoInfoClima.text = "Error al obtener el pronóstico: ${respuesta.message} 💔"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.textoInfoClima.text = "Error al obtener el pronóstico: ${e.message} 🚨"
                }
            }
        }
    }
}
