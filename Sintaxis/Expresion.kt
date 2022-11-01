package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Expresion {
    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion")
    }

    open fun obtenerTipoExpresion(
        tablaSimbolos: TablaSimbolos,
        ambito: Ambito,
        listaErrores: ArrayList<Error>
    ): String {
        return ""
    }

    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {}

    open fun getJavaCode(): String {
        return ""
    }
}