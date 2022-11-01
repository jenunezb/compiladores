package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ValorLogico(var valorLogico: Token) {
    fun getArbolVisual(): TreeItem<String> {
        return TreeItem<String>("Valor Logico: ${valorLogico.lexema} ")
    }

    override fun toString(): String {
        return "(${valorLogico.lexema})"
    }

    open fun getJavaCode(): String {
        if (valorLogico.lexema == "On") {
            return "true"
        } else {
            return "false"
        }
    }


}