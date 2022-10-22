package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import javafx.scene.control.TreeItem

class Parametro(var nombreParametro: Token, var tipoDato: Token) {
    override fun toString(): String {
        return "Parametro(tipoDato=$tipoDato, nombreParametros=$nombreParametro)"
    }

    fun getArbolVisual(): TreeItem<String> {
        return TreeItem<String>("${tipoDato.lexema}  ${nombreParametro.lexema}")
    }

    fun getJavaCode(): String {
        return tipoDato.getJavaCode() + " " + nombreParametro.getJavaCode()
    }

}