package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Decision(var expresionRelacional: ExpresionRelacional) : Sentencia() {

    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("decisión \n $expresionRelacional")
    }

    override fun toString(): String {
        return "Decision()"
    }
}