package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import javafx.scene.control.TreeItem

class Decision(
    var expresionLogica: ExpresionLogica?,
    var listaSentencias: ArrayList<Sentencia>?,
    var listaSentenciasNot: ArrayList<Sentencia>?
) : Sentencia() {

    var centinelaRetorno = false
    override fun toString(): String {
        return "Decision(expresionLogica=$expresionLogica, listaSentencias=$listaSentencias, listaSentenciasNot=$listaSentenciasNot)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Decision")


        if (expresionLogica != null) {
            var condicion = TreeItem<String>("Condicion")
            condicion.children.add(expresionLogica!!.getArbolVisual())
            raiz.children.add(condicion)
        }
        var raizYes = TreeItem<String>("Sentencias Verdaderas")
        for (s in listaSentencias!!) {
            raizYes.children.add(s.getArbolVisual())
        }
        raiz.children.add(raizYes)

        if (listaSentenciasNot != null) {
            var raizNot = TreeItem<String>("Sentencias Falsas")
            for (s in listaSentenciasNot!!) {
                raizNot.children.add(s.getArbolVisual())
            }
            raiz.children.add(raizNot)
        }
        return raiz
    }

    override fun getJavaCode(): String {

        var codigo = "if(" + expresionLogica!!.getJavaCode() + "){"
        for (s in listaSentencias!!) {

            codigo += s.getJavaCode()
        }
        codigo += "}"

        if (listaSentenciasNot != null) {
            codigo += "else{"

            for (s in listaSentenciasNot!!) {
                codigo += s.getJavaCode()
            }
            codigo += "}"
        }
        return codigo
    }

}