package org.example.reservasdevuelos.Data

import org.example.reservasdevuelos.model.Flight
import org.example.reservasdevuelos.model.User

class DataSet {

    companion object{
        val usuarios: ArrayList<User> = arrayListOf(
            User("Hanan", "hanan@","123","admin",26),
            User("Lara", "lara@","123","user",30),
            User("Sara", "sara@","123","user",20),
            User("Adam", "adam@","123","admin",35),


        )

        val vuelos: ArrayList<Flight> = arrayListOf(
            Flight(1, "Madrid", "Barcelona"),
            Flight(2, "Madrid", "Marrakech"),
            Flight(3, "París", "Londres"),
            Flight(4, "Roma", "Berlín"),
        )
     }



}