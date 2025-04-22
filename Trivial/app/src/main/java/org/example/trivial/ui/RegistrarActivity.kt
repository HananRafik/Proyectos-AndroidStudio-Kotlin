package org.example.trivial.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.example.trivial.data.DataSet
import org.example.trivial.databinding.ActivityRegistrarBinding
import org.example.trivial.model.Usuario

class RegistrarActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)

        setContentView(binding.root)




        binding.btnAcceder.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegistrar.setOnClickListener {

            val correo = binding.edtCorreo.text.toString()
            val pass = binding.edtPass.text.toString()
            val apodo = binding.edtApodo.text.toString()

          //  val usuario = DataSet.usuarios.find { it.correo == correo || it.apodo == apodo}

            if (correo.isEmpty() || pass.isEmpty() || apodo.isEmpty()){
                Snackbar.make(binding.root, "Rellena todos los campos", Snackbar.LENGTH_SHORT).show()



            }else{

                if (DataSet.usuarios.any { it.correo == correo || it.nick == apodo}){
                    Snackbar.make(binding.root, "El correo o el apodo  ya existe", Snackbar.LENGTH_SHORT).show()


                }else{

                    if (pass.length<6){
                        Snackbar.make(binding.root, "La contraseña deberá contar con al menos 6 caracteres ", Snackbar.LENGTH_SHORT).show()

                    }else{

                        Snackbar.make(binding.root, "Registrado correctamente",Snackbar.LENGTH_SHORT).show()


                        val nuevoUsuario = Usuario(
                            nick = apodo,
                            correo = correo,
                            pass = pass


                        )
                        DataSet.usuarios.add(nuevoUsuario)

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)

                    }
                }
            }

            binding.edtApodo.text.clear()
            binding.edtPass.text.clear()
            binding.edtCorreo.text.clear()


        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }
}