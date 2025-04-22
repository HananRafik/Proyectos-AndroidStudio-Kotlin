package org.example.trivial.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pregunta(

val type: String,
val difficulty: String,
val category: String,
val question: String?,
@SerializedName("correct_answer") val correctAnswer: String?,
@SerializedName("incorrect_answers") val incorrectAnswers: List<String>?

):Serializable {

    val combinaRespuestas: List<String> get()= (incorrectAnswers.orEmpty() + (correctAnswer ?: "")).shuffled()
}