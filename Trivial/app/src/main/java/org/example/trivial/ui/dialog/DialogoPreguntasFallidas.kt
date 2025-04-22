package org.example.trivial.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import org.example.trivial.model.Pregunta

class DialogoPreguntasFallidas(
    private val preguntasFallidas: ArrayList<Pregunta>,
) : DialogFragment() {

    private lateinit var listener: OnPreguntaFallidaListener

    interface OnPreguntaFallidaListener {
        fun onPreguntaFallidaRespondida(indice: Int, respuesta: String, esCorrecta: Boolean)
        fun onVolverAJugar()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnPreguntaFallidaListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val preguntasTexto = preguntasFallidas.map { it.question ?: "Pregunta sin texto" }

        builder.setTitle("Preguntas fallidas")
        builder.setItems(preguntasTexto.toTypedArray()) { _, which ->
            mostrarDialogoPregunta(which)
        }


        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }


        builder.setNeutralButton("Volver a jugar") { _, _ ->
            listener.onVolverAJugar()
        }

        return builder.create()
    }

    private fun mostrarDialogoPregunta(indice: Int) {
        val pregunta = preguntasFallidas[indice]
        val opciones = pregunta.combinaRespuestas
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(pregunta.question ?: "Pregunta sin texto")
        builder.setSingleChoiceItems(opciones.toTypedArray(), -1) { dialog, which ->
            val respuestaSeleccionada = opciones[which]
            val esCorrecta = respuestaSeleccionada == pregunta.correctAnswer
            listener.onPreguntaFallidaRespondida(indice, respuestaSeleccionada, esCorrecta)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}