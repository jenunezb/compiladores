package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

open class ExpresionAritmetica() : Expresion() {

    override fun toString(): String {
        return "Expresión aritmética"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Expresión Aritmética")

        raiz.children.add(TreeItem("Identificador de variable"))
        return raiz
    }

}