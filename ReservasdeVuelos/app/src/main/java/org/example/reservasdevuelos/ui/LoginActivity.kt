package org.example.reservasdevuelos.ui

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.example.reservasdevuelos.Data.DataSet
import org.example.reservasdevuelos.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            val correo = binding.editCorreo.text.toString()
            val contrasenia = binding.editPass.text.toString()

            val usuario = DataSet.usuarios.find { it.correo == correo && it.contrasena == contrasenia }

            if(usuario != null){
                    startActivity(Intent(this, MainActivity::class.java))


            }else{
                Snackbar.make(binding.root, "Datos incorrectos ", Snackbar.LENGTH_SHORT).show()
            }
        }


        binding.btnRegistrar.setOnClickListener {
            startActivity(Intent(this,RegistrarActivity::class.java))

        }

    }
}