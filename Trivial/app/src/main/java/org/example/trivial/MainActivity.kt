package org.example.trivial

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.example.trivial.databinding.ActivityMainBinding
import org.example.trivial.model.Pregunta
import org.example.trivial.model.Usuario
import org.example.trivial.ui.LoginActivity
import org.example.trivial.ui.dialog.DialogoInformacion
import org.example.trivial.ui.dialog.DialogoListaPreguntas
import org.example.trivial.ui.dialog.DialogoPreguntasFallidas

class MainActivity : AppCompatActivity(), DialogoListaPreguntas.OnDialogoMultipleListener, DialogoPreguntasFallidas.OnPreguntaFallidaListener {
    private lateinit var binding: ActivityMainBinding


    private var preguntas: List<Pregunta> = listOf()
    private var indicePreguntaActual = 0
    private var aciertos = 0
    private var fallos = 0
    private var preguntasFallidas: ArrayList<Pregunta> = ArrayList()
    private lateinit var usuario : Usuario

    private var vecesJugadas: Int  =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        usuario = intent.getSerializableExtra("usuario") as Usuario



        binding.edtApodo.text = usuario.nick


        binding.btnJugar.setOnClickListener {
            val numeroPreguntas = binding.editNumero.text.toString().toIntOrNull()

            if (numeroPreguntas != null && numeroPreguntas in 1..100) {
                vecesJugadas ++
                cargarPreguntas(numeroPreguntas)
            } else {
                Snackbar.make(binding.root, "Ingresa un número válido desde el 1 al 100", Snackbar.LENGTH_SHORT).show()
            }

            binding.editNumero.text.clear()
        }

        binding.nFallos.setOnClickListener{
            if (preguntasFallidas.isNotEmpty()) {
                val dialogo = DialogoPreguntasFallidas(preguntasFallidas)
                dialogo.show(supportFragmentManager, "DialogoFallos")
            } else {
                Snackbar.make(binding.root, "No hay preguntas fallidas", Snackbar.LENGTH_SHORT).show()
            }
        }


    }

    private fun cargarPreguntas(numeroPreguntas: Int) {
        val urlConsulta = "https://opentdb.com/api.php?amount=$numeroPreguntas"
        val gson: Gson = Gson()
        val peticion = JsonObjectRequest(urlConsulta,
            { response ->
                try {
                    val results = response.getJSONArray("results")
                    preguntas = (0 until results.length()).map {
                        val question = results.getJSONObject(it)
                        gson.fromJson(question.toString(), Pregunta::class.java)
                    }
                    mostrarSiguientePregunta()
                } catch (e: Exception) {
                    Log.e("Error", "Error JSON: ${e.message}")
                    Snackbar.make(binding.root, "Error al procesar las preguntas", Snackbar.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("Error", "Error en la solicitud: ${error.message}")
                Snackbar.make(binding.root, "Error al cargar preguntas desde el json", Snackbar.LENGTH_SHORT).show()
            })

        Volley.newRequestQueue(this).add(peticion)
    }



    private fun mostrarSiguientePregunta() {
        if (indicePreguntaActual < preguntas.size) {
            val pregunta = preguntas[indicePreguntaActual]
            val opciones = pregunta.combinaRespuestas
            mostrarDialogo(pregunta.question ?: "Pregunta sin texto", opciones.toTypedArray(), pregunta.correctAnswer ?: "")
        } else {
            Snackbar.make(binding.root, "Juego terminado. Aciertos: $aciertos, Fallos: $fallos", Snackbar.LENGTH_INDEFINITE)
                .setAction("Jugar de nuevo") {
                    reiniciarJuego()
                }.show()
        }
    }



    private fun mostrarDialogo(pregunta: String, opciones: Array<String>, respuestaCorrecta: String) {
        val dialogo = DialogoListaPreguntas(opciones, pregunta, respuestaCorrecta)
        dialogo.show(supportFragmentManager, null)
    }



    override fun onOpcionMultpleSelected(respuestas: ArrayList<Int>, respuestaCorrecta: String, opciones: Array<String>) {
        val preguntaActual = preguntas[indicePreguntaActual]
        val respuestasSeleccionadas = respuestas.joinToString { opciones[it] }

        if (respuestasSeleccionadas == preguntaActual.correctAnswer) {
            aciertos++
            binding.nAciertos.text = aciertos.toString()
        } else {
            fallos++
            preguntasFallidas.add(preguntaActual)
            binding.nFallos.text = fallos.toString()
        }

        indicePreguntaActual++
        mostrarSiguientePregunta()
    }









    override fun onPreguntaFallidaRespondida(indice: Int, respuesta: String, esCorrecta: Boolean) {
        if (esCorrecta) {
            aciertos++
            fallos--
            preguntasFallidas.removeAt(indice)
            binding.nAciertos.text = aciertos.toString()
            binding.nFallos.text = fallos.toString()

        }
    }

    private fun reiniciarJuego() {
        indicePreguntaActual = 0

        binding.nAciertos.text = aciertos.toString()
        binding.nFallos.text = fallos.toString()
        preguntas = listOf()

    }
    override fun onVolverAJugar() {
        reiniciarJuego()
    }






    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cerrarSesion -> {

                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("apodo",usuario.nick)
                intent.putExtra("aciertos", aciertos)
                intent.putExtra("fallos", fallos)
                startActivity(intent)
                true
            }

            R.id.informacion -> {

                val informacionDialog = DialogoInformacion.newInstance(
                    correo = usuario.correo,
                    apodo = usuario.nick,
                    vecesJugadas = vecesJugadas
                )
                informacionDialog.show(supportFragmentManager, "DialogoInformacion")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}