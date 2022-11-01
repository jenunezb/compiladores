package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import javafx.scene.control.TreeItem

class CicloRun(

    var declaracionVariable: DeclaracionVariable,
    var expresionLogica: ExpresionLogica?,
    var incrementoDecremento: IncrementoDecremento,
    var listaSentencias: ArrayList<Sentencia>
) : Ciclo() {
    var centinelaRetorno = false
    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Ciclo run")
        raiz.children.add(declaracionVariable.getArbolVisual())
        if (expresionLogica != null) {
            raiz.children.add(expresionLogica!!.getArbolVisual())
        }

        raiz.children.add(incrementoDecremento.getArbolVisual())
        var raizSentencia = TreeItem<String>("Lista de Sentencias")
        for (p in listaSentencias) {

            raizSentencia.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizSentencia)

        return raiz
    }

    override fun toString(): String {
        return "CicloRun(${declaracionVariable.toString()} $expresionLogica, ${incrementoDecremento.operador.lexema} ${incrementoDecremento.identificador!!.lexema})"
    }

    override fun getJavaCode(): String {
        var codigo =
            "for(" + declaracionVariable.getJavaCode() + expresionLogica!!.getJavaCode() + ";" + incrementoDecremento.getJavaCode()

        codigo = codigo.substring(0, codigo.length - 1)
        codigo += "){"
        for (s in listaSentencias) {

            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo
    }
}