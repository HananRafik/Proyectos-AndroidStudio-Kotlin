package org.example.trivial.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.example.trivial.MainActivity
import org.example.trivial.R
import org.example.trivial.data.DataSet
import org.example.trivial.databinding.ActivityLoginBinding
import org.example.trivial.model.Usuario
import org.example.trivial.ui.dialog.RecordDialogo


class LoginActivity: AppCompatActivity(), RecordDialogo.OnDialogoListener {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)



        binding.btnRegistrar.setOnClickListener {
        val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }


        binding.btnAcceder.setOnClickListener {
            val correo = binding.edtCorreo.text.toString()
            val pass = binding.edtPass.text.toString()
            val usuario = DataSet.usuarios.find { it.correo == correo && it.pass == pass }

            if (usuario != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            } else {
                Snackbar.make(binding.root, "Datos incorrectos", Snackbar.LENGTH_SHORT).show()
            }

            binding.edtPass.text.clear()
            binding.edtCorreo.text.clear()
        }

        binding.imgRecord.setOnClickListener {

            val apodo = intent.getStringExtra("apodo")
            val aciertos = intent.getIntExtra("aciertos", 0)
            val fallos = intent.getIntExtra("fallos", 0)

            val recordDialog = RecordDialogo.newInstance(apodo ?: "Inicia la sesión", aciertos, fallos)
            recordDialog.show(supportFragmentManager, "RecordDialogo")
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }


    override fun onDialogoSelected(respuesta: String, respuestaNum: Int) {

    }

    override fun onDialogoSelectedAll() {

    }


}