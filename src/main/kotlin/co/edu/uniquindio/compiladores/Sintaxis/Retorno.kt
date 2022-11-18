package co.edu.uniquindio.compiladores.Sintaxis
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

open class Retorno(var nombreVariable:Token) : Sentencia() {

    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem<String>("Retorno ${nombreVariable.lexema}")
    }

    override fun toString(): String {
        return "Retorno(${nombreVariable.lexema})"
    }
    override fun getJavaCode(): String {
        return nombreVariable.getJavaCode()
    }
}