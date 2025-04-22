package org.example.trivial.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import org.example.trivial.ui.LoginActivity

class RecordDialogo: DialogFragment() {

    lateinit var listener: OnDialogoListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as LoginActivity

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        val nombre = arguments?.getString("usuario") ?: "Inicia la sesión"
        val aciertos = arguments?.getInt("aciertos") ?: 0
        val fallos = arguments?.getInt("fallos") ?: 0

        builder.setView(view)
            .setTitle("Datos del usuario")
            .setMessage("Usuario: $nombre\n Aciertos: $aciertos\n Fallos: $fallos")
            .setPositiveButton("Vuelve a Acceder") { _, _ ->

                if(nombre == "Inicia la sesión"){
                    val loginActivity = requireActivity() as LoginActivity
                   Snackbar.make(loginActivity.findViewById(android.R.id.content), "Por favor, inicia sesión o regístrate si no tienes una cuenta ", Snackbar.LENGTH_SHORT).show()


                }else{
                    requireActivity().finish()
                }

            }
            .setNeutralButton("Cancelar"){_,_ ->}

        return builder.create()
    }

    companion object {
        fun newInstance(usuario: String, aciertos: Int, fallos: Int): RecordDialogo {
            val bundle = Bundle().apply {

                putString("usuario", usuario)
                putInt("aciertos", aciertos)
                putInt("fallos", fallos)
            }

            val fragment = RecordDialogo()
            fragment.arguments = bundle
            return fragment
        }
    }

    interface OnDialogoListener{
        fun onDialogoSelected(respuesta: String, respuestaNum: Int)
        fun onDialogoSelectedAll()
    }

}