package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import javafx.scene.control.TreeItem

open class Sentencia {

    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Sentencia")
    }

    override fun toString(): String {
        return "Sentencia()"
    }

    open fun getJavaCode(): String {
        return ""
    }

}