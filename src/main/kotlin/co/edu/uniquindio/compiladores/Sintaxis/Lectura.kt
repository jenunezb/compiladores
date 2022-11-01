package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Lectura(var variable: Token, var expresionCadena: ExpresionCadena?) : Sentencia() {
    override fun toString(): String {
        return "Lectura(expresionCadena=$expresionCadena)"
    }
    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Lectura de datos")

        if (expresionCadena != null && variable != null) {
            raiz.children.add(TreeItem("Identificador de variable: ${variable!!.lexema}"))
            raiz.children.add(expresionCadena!!.getArbolVisual())
        }

        return raiz
    }
}