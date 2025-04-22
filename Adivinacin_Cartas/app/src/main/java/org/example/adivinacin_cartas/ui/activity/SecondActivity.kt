package org.example.adivinacin_cartas.ui.activity

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.example.adivinacin_cartas.R
import org.example.adivinacin_cartas.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(),  CompoundButton.OnCheckedChangeListener{

    private lateinit var binding: ActivitySecondBinding
    private lateinit var listaImagenes:ArrayList<Int>


    private  var numeroActual = 0
    private var numeroFuturo = 0
    private var contador= 0

    private var primeraImagen = true





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        acciones()
        instancias()

    }

    private fun instancias(){
        listaImagenes = arrayListOf(
            R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4,
            R.drawable.c5,
            R.drawable.c6,
            R.drawable.c7,
            R.drawable.c8,
            R.drawable.c9,
            R.drawable.c10,
            R.drawable.c11,
            R.drawable.c12,
            R.drawable.c13
        )

        if (primeraImagen) {
            actualizarFondo(R.drawable.cf)
        } else{
            numeroActual = listaImagenes.random()
            actualizarFondo(numeroActual)
        }
    }



    private fun acciones(){
        binding.btnUp.setOnCheckedChangeListener(this)
        binding.btnDown.setOnCheckedChangeListener(this)

    }


    private fun actualizarFondo(imagen: Int){
        binding.main.setBackgroundResource(imagen)
    }


    private  fun juagar(up : Boolean){

        if (primeraImagen){
            primeraImagen= false
        }

        numeroFuturo = listaImagenes.random()


       when{
           numeroActual == numeroFuturo ->{
               contador++
           }

           up ->{
               if (numeroActual< numeroFuturo){
                   contador++
                   mostrarSnackbar("Adivinaste la carta Puntos: $contador")

               }else{
                   contador--
                   verificarPuntuacion()
               }
           }
           !up -> {
               if (numeroActual > numeroFuturo){
                   contador++
                   mostrarSnackbar("Adivinaste la carta Puntos: $contador")
               }else{
                   contador--
                   verificarPuntuacion()

               }
           }


       }

        numeroActual = numeroFuturo
        actualizarFondo(numeroActual)


    }

    private fun mostrarSnackbar(mensaje: String){
        Snackbar.make(binding.root,mensaje,Snackbar.LENGTH_INDEFINITE)
            .setAction("Cerrar"){
                finish()
            }.show()
    }


    private fun verificarPuntuacion(){
        if (contador<= 0){
            Snackbar.make(binding.root,"Has perdido", Snackbar.LENGTH_INDEFINITE)
                .setAction("Volver a jugar"){
                    finish()
                }.show()
        }else{
            mostrarSnackbar("Incorreto  Puntos:$contador")
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        when(buttonView?.id){
            binding.btnUp.id->{
                juagar(up = true)


            }binding.btnDown.id->{

                juagar(up = false)

            }

        }
    }


}