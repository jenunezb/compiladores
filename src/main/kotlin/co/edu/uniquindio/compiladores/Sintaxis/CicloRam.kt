package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import javafx.scene.control.TreeItem

class CicloRam(var expresionLogica: ExpresionLogica?, var listaSentencias: ArrayList<Sentencia>) : Ciclo() {

    var centinelaRetorno = false

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Ciclo Ram")

        if (expresionLogica != null) {
            raiz.children.add(expresionLogica!!.getArbolVisual())
        }

        var raizSentencia = TreeItem<String>("Lista de Sentencias")
        for (p in listaSentencias) {

            raizSentencia.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizSentencia)
        return raiz
    }

    override fun toString(): String {
        return "CicloRam($expresionLogica)"
    }

    override fun getJavaCode(): String {
        var codigo = "while(" + expresionLogica!!.getJavaCode() + "){"
        for (s in listaSentencias) {
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo
    }
}