package co.edu.uniquindio.compiladores.proyecto.Semantica


import co.edu.uniquindio.compiladores.proyecto.Sintaxis.Funcion
import co.edu.uniquindio.compiladores.proyecto.Sintaxis.Sentencia

class Ambito(var nombre: String?, var sentencia: Sentencia?, var funcion: Funcion?, var ambito: Ambito?) {

    override fun toString(): String {
        if (nombre != null && sentencia == null && funcion == null) {
            return "('$nombre')"
        }
        if (sentencia == null && nombre != null && funcion != null) {
            return "('$nombre', funcion=${funcion!!.nombreFuncion.lexema},${funcion!!.obtenerTiposParametros()})"
        }
        if (funcion == null && nombre != null && sentencia != null) {
            return "('$nombre',  sentencia=${sentencia.toString()}})"
        }
        if (nombre != null && sentencia != null && funcion != null && ambito == null) {
            return "('$nombre', funcion=${funcion!!.nombreFuncion.lexema} ${funcion!!.obtenerTiposParametros()},  sentencia=${sentencia.toString()})"
        }
        if (nombre != null && sentencia != null && funcion != null && ambito != null) {
            return "('$nombre', funcion=${funcion!!.nombreFuncion.lexema} ${funcion!!.obtenerTiposParametros()},  sentencia=${sentencia.toString()} ambito=${ambito.toString()})"
        }
        return ""
    }

    @Override
    fun equals(nombre1: String?, sentencia1: Sentencia?, funcion1: Funcion?): Boolean {
        if (nombre == nombre1 && sentencia == sentencia1 && funcion == funcion1) {
            return true
        }
        return false
    }
}