package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import javafx.scene.control.TreeItem

open class Expresion {
    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion")
    }

    open fun getJavaCode(): String {
        return ""
    }
}