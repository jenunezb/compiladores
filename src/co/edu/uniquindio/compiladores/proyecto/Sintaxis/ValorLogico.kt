package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
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