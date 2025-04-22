package org.example.trivial.ui.dialog


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class DialogoListaPreguntas (
    private val opciones: Array<String>,
    private val pregunta: String,
    private val respuestaCorrecta: String
): DialogFragment() {

    private lateinit var listener: OnDialogoMultipleListener
    private lateinit var listaRespuestas: ArrayList<Int>



    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnDialogoMultipleListener
        listaRespuestas = ArrayList()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())

        builder.setTitle(pregunta)

        builder.setSingleChoiceItems(opciones, -1) { _, i ->
            listaRespuestas.clear()
            listaRespuestas.add(i)
        }

        builder.setNeutralButton("Cancelar") { _, _ ->
            dismiss()
        }
        builder.setPositiveButton("Aceptar") { _, _ ->


            listener.onOpcionMultpleSelected(listaRespuestas,respuestaCorrecta, opciones)
        }

        return builder.create()
    }



    interface OnDialogoMultipleListener {
        fun onOpcionMultpleSelected(respuestas: ArrayList<Int>, repuestaCorrecta:String, opciones: Array<String>)
    }


}