package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ValorNumerico(var signo: Token?, var termino: Token) : ExpresionAritmetica() {


    override fun getArbolVisual(): TreeItem<String> {

        if (signo == null) {
            return TreeItem<String>("termino: ${termino.lexema}")
        } else {
            return TreeItem<String>("Signo: ${signo!!.lexema} termino:  ${termino.lexema} ")
        }
    }

    override fun toString(): String {

        if (signo != null) {
            return "(${signo!!.lexema} ${termino.lexema})"
        } else {
            return "( ${termino.lexema})"
        }
    }

    override fun getJavaCode(): String {

        if (signo != null) {

            return signo!!.getJavaCode() + " " + termino.getJavaCode()

        } else {
            return termino.getJavaCode();
        }
    }
}