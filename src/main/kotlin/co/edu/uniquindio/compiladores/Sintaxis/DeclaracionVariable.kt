package co.edu.uniquindio.compiladores.Sintaxis

import javafx.scene.control.TreeItem

open class DeclaracionVariable():Sentencia() {

    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Declaracion de Variable")
    }

    open override fun toString(): String {
       return ""
    }
    override fun getJavaCode(): String {
        return ""
    }



}