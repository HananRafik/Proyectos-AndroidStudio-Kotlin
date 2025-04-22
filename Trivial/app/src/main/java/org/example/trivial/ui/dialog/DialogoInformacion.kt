package org.example.trivial.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogoInformacion:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val correo = arguments?.getString("correo") ?: "Sin datos"
        val apodo = arguments?.getString("apodo") ?: "Sin datos"
        val vecesJugadas = arguments?.getInt("vecesJugadas") ?: 0



        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Información del usuario")
            .setMessage(
                """
                Correo: $correo
                Nick: $apodo
                Veces Jugadas:  $vecesJugadas
                
            """.trimIndent()
            )
            .setPositiveButton("Aceptar") { _, _ ->

                dismiss()
            }

        return builder.create()

    }

    companion object {
        fun newInstance(

            correo: String,
            apodo: String,
            vecesJugadas: Int

        ): DialogoInformacion {
            val args = Bundle().apply {
                putString("correo", correo)
                putString("apodo", apodo)
                putInt("vecesJugadas", vecesJugadas)



            }
            val fragment = DialogoInformacion()
            fragment.arguments = args
            return fragment
        }
    }

}