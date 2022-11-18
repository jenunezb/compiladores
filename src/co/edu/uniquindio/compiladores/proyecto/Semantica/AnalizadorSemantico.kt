package co.edu.uniquindio.compiladores.proyecto.Semantica

import co.edu.uniquindio.compiladores.proyecto.Sintaxis.UnidadDeCompilacion
import co.edu.uniquindio.compiladores.proyecto.Lexico.Error

class AnalizadorSemantico(var unidadDeCompilacion: UnidadDeCompilacion) {

    var erroresSemanticos: ArrayList<Error> = ArrayList()
    var tablaSimbolos: TablaSimbolos = TablaSimbolos(erroresSemanticos)

    fun llenarTablaSimbolos() {
        unidadDeCompilacion.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos)
    }

    fun analizarSemantica() {
        unidadDeCompilacion.analizarSemantica(tablaSimbolos, erroresSemanticos)
    }
}