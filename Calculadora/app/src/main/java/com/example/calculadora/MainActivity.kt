package com.example.calculadora

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora.databinding.ActivityMainBinding
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan


class MainActivity : AppCompatActivity() , View.OnClickListener {


    private lateinit var binding: ActivityMainBinding

    private var operador1: Double? = null
    private var operador2: Double? = null
    private var operador: String? = null
    private var textoActual: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)

        binding.btnC.setOnClickListener(this)
        binding.btnResta.setOnClickListener(this)
        binding.btnSuma.setOnClickListener(this)
        binding.btnMulti.setOnClickListener(this)
        binding.btnDivicion.setOnClickListener(this)
        binding.btnPorcentaje.setOnClickListener(this)
        binding.btnIgual.setOnClickListener(this)
        binding.btnCambiarSigno.setOnClickListener(this)
        binding.btnDecimal.setOnClickListener(this)


        binding.btnCoseno?.setOnClickListener(this)
        binding.btnConstante?.setOnClickListener(this)
        binding.btnAbsoluto?.setOnClickListener(this)
        binding.btnPi?.setOnClickListener(this)
        binding.btnLog?.setOnClickListener(this)
        binding.btnTangente?.setOnClickListener(this)
        binding.btnSino?.setOnClickListener(this)
        binding.btnExponencial?.setOnClickListener(this)
        binding.btnElevado?.setOnClickListener(this)
        binding.btnRad?.setOnClickListener(this)
        binding.btnRaizCuadrada?.setOnClickListener(this)
        binding.btnIn?.setOnClickListener(this)
        binding.btnInverso?.setOnClickListener(this)
        binding.btnElevarPotencial?.setOnClickListener(this)
        binding.btnEliminar?.setOnClickListener(this)




    }



    override fun onClick(v: View?) {

        when(v?.id){
            binding.btn0.id -> agregarNumero("0")
            binding.btn1.id -> agregarNumero("1")
            binding.btn2.id -> agregarNumero("2")
            binding.btn3.id -> agregarNumero("3")
            binding.btn4.id -> agregarNumero("4")
            binding.btn5.id -> agregarNumero("5")
            binding.btn6.id -> agregarNumero("6")
            binding.btn7.id -> agregarNumero("7")
            binding.btn8.id -> agregarNumero("8")
            binding.btn9.id -> agregarNumero("9")


            binding.btnSuma.id -> establecerOperador("+")
            binding.btnResta.id -> establecerOperador("-")
            binding.btnMulti.id -> establecerOperador("*")
            binding.btnDivicion.id -> establecerOperador("/")
            binding.btnPorcentaje.id -> calcularPorcentaje()

            binding.btnAbsoluto?.id -> calcularAbsoluto()
            binding.btnConstante?.id -> agregarConstanteE()
            binding.btnCoseno?.id -> agregarCoseno()
            binding.btnInverso?.id -> calcularInverso()
            binding.btnSino?.id -> agregarSeno()
            binding.btnRaizCuadrada?.id -> calcularRaizCuadrada()
            binding.btnTangente?.id -> calcularTangente()
            binding.btnPi?.id -> agregarPi()
            binding.btnExponencial?.id -> calcularExponencial()
            binding.btnRad?.id -> convertirRadian()
            binding.btnIn?.id -> calcularLn()
            binding.btnLog?.id -> calcularLog()
            binding.btnElevado?.id -> elevadoAlCuadrado()
            binding.btnElevarPotencial?.id -> elevarPotencial(2.0)
            binding.btnEliminar?.id -> eliminarCaracter()


            binding.btnIgual.id -> calcularResultado()

            binding.btnDecimal.id -> agregarDecimal()
            binding.btnCambiarSigno.id -> cambiarSigno()

            binding.btnC.id -> limpiar()


        }
    }


    private fun agregarNumero(valor: String){

        textoActual += valor
        binding.pantallaOperaciones.text = textoActual
    }


    private fun agregarDecimal(){

        val ultimoNumero = textoActual.split(" ").last()
        if (!ultimoNumero.contains(".")){
            textoActual += "."
            binding.pantallaOperaciones.text = textoActual
        }
    }






    private fun establecerOperador(op: String){

        operador1 = textoActual.toDoubleOrNull()
        if (operador1 != null) {
            operador = op
            textoActual += " $op "
            binding.pantallaOperaciones.text = textoActual
        } else {
            mostrarError()
        }
    }


    private fun calcularResultado(){
        operador2 = textoActual.split(" ").last().toDoubleOrNull()

        if (operador1 != null && operador2 != null && operador != null) {
            val resultado = when (operador) {
                "+" -> (operador1 ?: 0.0) + (operador2 ?: 0.0)
                "-" -> (operador1 ?: 0.0) - (operador2 ?: 0.0)
                "*" -> (operador1 ?: 0.0) * (operador2 ?: 0.0)
                "/" -> if (operador2 != 0.0) (operador1 ?: 0.0) / (operador2 ?: 0.0) else Double.NaN
                "%" -> (operador1 ?: 0.0) * (operador2 ?: 0.0) / 100
                else -> null
            }

            if (resultado != null) {
                textoActual += " = $resultado"

                binding.pantallaOperaciones.text = textoActual
                operador1 = resultado
            } else {
                mostrarError()
            }
        } else {
            mostrarError()
        }


        operador2 = null
        operador = null
    }




    private fun mostrarResultado(resultado: Double){
        textoActual = resultado.toString()
        binding.pantallaOperaciones.text = textoActual
    }


    private fun mostrarError(){
        binding.pantallaOperaciones.text = "Error"
        textoActual = ""
    }






    private fun cambiarSigno(){
        val numeroActual = textoActual.split(" ").last().toDoubleOrNull() ?: return
        val nuevoNumero = -numeroActual
        textoActual = textoActual.dropLastWhile { it != ' ' }.trimEnd() + " $nuevoNumero"
        binding.pantallaOperaciones.text = textoActual
    }



    private fun calcularPorcentaje(){
        operador1 =  textoActual.toDoubleOrNull()

        if (operador1 != null){

            val resultado = operador1 !! / 100

            textoActual += "% = $resultado"
            binding.pantallaOperaciones.text = textoActual
            operador1 = resultado

        }else{
            mostrarError()
        }
    }




    private fun eliminarCaracter(){
        if (textoActual.isNotEmpty()){
            textoActual = textoActual.dropLast(1)
            binding.pantallaOperaciones.text = textoActual
        }
    }


    private fun calcularRaizCuadrada(){
        val numero = textoActual.toDoubleOrNull()

        if (numero != null && numero >= 0) {
            val resultado = sqrt(numero)
            mostrarResultado(resultado)
        } else {
            mostrarError()
        }
    }


    private fun convertirRadian(){
        val numero = textoActual.toDoubleOrNull()
        if(numero != null){
            val resultado = Math.toRadians(numero)
            mostrarResultado(resultado)
        }else{
            mostrarError()
        }
    }


    private fun calcularTangente(){
        val numero = textoActual.toDoubleOrNull()

        if (numero != null){
            val resultado = tan(Math.toRadians(numero))
            mostrarResultado(resultado)
        }else{
            mostrarError()
        }

    }


    private fun agregarCoseno(){
        val numero = textoActual.toDoubleOrNull() ?: return
        val resultado = cos(Math.toRadians(numero))
        mostrarResultado(resultado)
    }

    private fun agregarSeno(){
        val numero = textoActual.toDoubleOrNull()
        if (numero != null){
            val resultado = sin(Math.toRadians(numero))
            mostrarResultado(resultado)
        }else{
            mostrarError()
        }

    }



    private fun calcularInverso(){
        val numero = textoActual.toDoubleOrNull()

        if (numero != null && numero != 0.0){
            val resultado = 1/ numero
            mostrarResultado(resultado)
        }else{
            mostrarError()
        }
    }

    private fun calcularLog(){
        val numero = textoActual.toDoubleOrNull()
        if (numero!= null && numero >0){
            val resultado = log10(numero)
            mostrarResultado(resultado)
        }else{
            mostrarError()
        }
    }


    private fun calcularLn(){
        val numero = textoActual.toDoubleOrNull()

        if (numero != null && numero>0){
            val resultado = ln(numero)
            mostrarResultado(resultado)

        }else{
            mostrarError()
        }
    }

    private fun elevarPotencial(potencial : Double){
        val numero = textoActual.toDoubleOrNull() ?: return

        val resultado = numero.pow(potencial)
        mostrarResultado(resultado)
    }

    private fun elevadoAlCuadrado(){

        val numero = textoActual.toDoubleOrNull()
        if (numero != null){
            val resultado = numero * numero
            mostrarResultado(resultado)
        }else{
            mostrarError()
        }
    }

    private fun calcularExponencial(){
        val numero = textoActual.toDoubleOrNull()
        if (numero != null){
            val resultado = exp(numero)
            mostrarResultado(resultado)
        }else{
            mostrarError()
        }

    }

    private fun calcularAbsoluto(){
        val numero = textoActual.toDoubleOrNull() ?: return
        val resultado = abs(numero)
        mostrarResultado(resultado)
    }







    private fun agregarPi(){
        textoActual += PI.toString()
        binding.pantallaOperaciones.text = textoActual
    }

    private fun agregarConstanteE(){
        textoActual += E.toString()
        binding.pantallaOperaciones.text = textoActual
    }




    private fun limpiar(){
        textoActual = ""
        operador = null
        operador1 = null
        operador2 = null
        binding.pantallaOperaciones.text = ""
    }
}