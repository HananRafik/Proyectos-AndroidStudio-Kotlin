package org.example.reservasdevuelos.model


import java.io.Serializable

class Flight (
    val id: Int,
    val origen: String,
    val destino: String,
    var esFavorito: Boolean = false
): Serializable{
}