package org.example.adivinacin_cartas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.example.adivinacin_cartas.databinding.ActivityMainBinding
import org.example.adivinacin_cartas.ui.activity.SecondActivity

class MainActivity : AppCompatActivity(), OnClickListener{

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        acciones()


    }


  private fun acciones(){
      binding.btnEmpezar.setOnClickListener(this)
  }



    override fun onClick(p0: View?) {

       if (binding.textoNombre.text.isNullOrEmpty()){
           Snackbar.make(binding.root, "Por favor ingresa tu nombre",Snackbar.LENGTH_SHORT).show()
       }else{
        Snackbar.make(binding.root,"Perfecto ${binding.textoNombre.text.toString()} quieres empezar", Snackbar.LENGTH_INDEFINITE)
            .setAction("Ok"){

                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
        }.show()

       }
    }
}