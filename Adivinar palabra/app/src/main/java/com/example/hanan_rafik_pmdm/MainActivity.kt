package com.example.hanan_rafik_pmdm

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hanan_rafik_pmdm.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val listaPlalabras = listOf("array","clase", "login", "objeto", "input", "debug", "patch", "query", "stack", "cache")
    private lateinit var palabras:String
    private  var intentos= 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnEmpezar.setOnClickListener {
        empezarJuego()
        }


        binding.btnValidar.setOnClickListener {
            validarIntentos()
        }

    }


    private fun empezarJuego(){
        val nombre = binding.editnombre.text.toString()

        if (nombre.isEmpty()){
            Snackbar.make(binding.root, "Por favor completa los datos", Snackbar.LENGTH_SHORT).show()
            return
        }

        palabras = listaPlalabras.random()
        intentos = 5
        binding.contador.text =  intentos.toString()
        clearEditText()


    }


    private fun validarIntentos(){
        val letra1 = binding.primeraLetra.text.toString()
        val letra2 = binding.segundaLetra.text.toString()
        val letra3 = binding.terceraLetra.text.toString()
        val letra4 = binding.cuartaLetra.text.toString()
        val letra5 = binding.quintaLetra.text.toString()

        if (letra1.isEmpty() || letra2.isEmpty() || letra3.isEmpty() || letra4.isEmpty() || letra5.isEmpty()){
            Snackbar.make(binding.root, "Introduce todas las letras", Snackbar.LENGTH_SHORT).show()
            return
        }


        val intento = letra1 + letra2+ letra3+ letra4 + letra5
        if (intento == palabras){

            binding.primeraLetra.setBackgroundColor(Color.GREEN)
            binding.segundaLetra.setBackgroundColor(Color.GREEN)
            binding.terceraLetra.setBackgroundColor(Color.GREEN)
            binding.cuartaLetra.setBackgroundColor(Color.GREEN)
            binding.quintaLetra.setBackgroundColor(Color.GREEN)


            Snackbar.make(binding.root, "Enhorabuena ${binding.editnombre.text.toString()}, has  acertado la palabra ", Snackbar.LENGTH_SHORT).show()
            return
        }
        verificarLetras(intento)
        intentos --
        binding.contador.text = intentos.toString()

        if (intentos == 0 ){
            Snackbar.make(binding.root, "Se han acabado los intentos. la palabra es : $palabras", Snackbar.LENGTH_LONG).show()
        }

        clearEditText()
    }


    private fun verificarLetras(intento : String){

        val letraSecreta = palabras.toCharArray()
        val letrasIntento = intento.toCharArray()
        val letraUsada = BooleanArray(letraSecreta.size){false}

        for (i in letrasIntento.indices){
            val editText = when (i){
                0 -> binding.primeraLetra
                1 -> binding.segundaLetra
                2 -> binding.terceraLetra
                3 -> binding.cuartaLetra
                4 -> binding.quintaLetra

                else -> null
            }

            if (editText!= null){
                if (letrasIntento[i] == letraSecreta[i]){
                    editText.setBackgroundColor(Color.GREEN)
                    letraUsada[i] =true
                }else{
                    editText.setBackgroundColor(Color.RED)
                }

            }
        }

        for (i in letrasIntento.indices) {
            val editText = when (i) {
                0 -> binding.primeraLetra
                1 -> binding.segundaLetra
                2 -> binding.terceraLetra
                3 -> binding.cuartaLetra
                4 -> binding.quintaLetra
                else -> null
            }

            if (editText != null && (editText.background as ColorDrawable).color != Color.GREEN) {

                for (j in letraSecreta.indices) {
                    if (letrasIntento[i] == letraSecreta[j] && !letraUsada[j]) {
                        editText.setBackgroundColor(Color.YELLOW)
                        letraUsada[j] = true
                        break
                    }
                }
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("palabras", palabras)
        outState.putInt("intentos", intentos)

    }



    private fun clearEditText(){

        binding.primeraLetra.text.clear()
        binding.segundaLetra.text.clear()
        binding.terceraLetra.text.clear()
        binding.cuartaLetra.text.clear()
        binding.quintaLetra.text.clear()




    }



}