package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Asignacion(
    var identificador: Token,
    var apAsig: Token?,
    var expresion: Expresion?
) : Sentencia() {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador, apAsig=$apAsig, expresion=$expresion)"
    }

    var cont = 1;

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Asignacion")
        raiz.children.add(TreeItem("Identificador Variable: ${identificador.lexema}"))
        if (apAsig != null) {
            raiz.children.add(TreeItem("Operador de Asigancion: ${apAsig!!.lexema}"))
        }
        if (expresion != null) {
            var raizExpresion = TreeItem<String>("Expresion")

            raizExpresion.children.add(expresion!!.getArbolVisual())
            raiz.children.add(raizExpresion)
        }

        return raiz
    }


    override fun getJavaCode(): String {

        if (identificador!=null &&expresion != null) {
            return identificador.getJavaCode() + apAsig!!.getJavaCode() + expresion!!.getJavaCode()+";"
        }
        return ""
    }

}