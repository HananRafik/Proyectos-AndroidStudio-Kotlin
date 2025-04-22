package org.example.reservasdevuelos.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.example.reservasdevuelos.Data.DataSet
import org.example.reservasdevuelos.databinding.ActivityRegistrarBinding
import org.example.reservasdevuelos.model.User

class RegistrarActivity : AppCompatActivity(){

    private lateinit var binding: ActivityRegistrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profil = arrayOf("admin", "usuario")
        val adapterProfil = ArrayAdapter(this, android.R.layout.simple_spinner_item, profil)
        binding.spinnerProfil.adapter = adapterProfil



        binding.btnRegistar.setOnClickListener {

            val nombre = binding.editNombre.text.toString()
            val correo = binding.editCorreo.text.toString()
            val pass = binding.editPass.text.toString()
            val profil = binding.spinnerProfil.selectedItem.toString()
            val edad = binding.editEdad.text.toString().toIntOrNull()?:0

            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty() || edad == 0){
                Snackbar.make(binding.root, "Rellena los datos necesarios", Snackbar.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            if (DataSet.usuarios.any { it.correo == correo}){
                Snackbar.make(binding.root, "El correo existe", Snackbar.LENGTH_SHORT).show()

            }else {


                    DataSet.usuarios.add(User(nombre,correo,pass,profil,edad) )
                    Snackbar.make(binding.root,"Usuario registrado correctamente",Snackbar.LENGTH_SHORT).show()

                finish()
            }
        }


    }
}