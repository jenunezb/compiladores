package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

open class VariableMutable(var tipoDato: Token, var nombreMutable:Token):Sentencia() {
    override fun toString(): String {
        return "Mutable(tipoDato=$tipoDato, nombreParametros=$nombreMutable)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem<String>("Mutable ${tipoDato.lexema}  ${nombreMutable.lexema}")
    }

    override fun getJavaCode(): String {
        return tipoDato.getJavaCode() + " " + nombreMutable.getJavaCode()
    }
}