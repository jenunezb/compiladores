package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class CicloWhile(var expresionLogica: ExpresionLogica?, var listaSentencias: ArrayList<Sentencia>) : Ciclo() {

    var centinelaRetorno = false

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Ciclo While")

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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var ambito1 = Ambito(ambito.nombre, this, ambito.funcion, ambito)
        for (s in listaSentencias) {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito1)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {
        var ambito1 = Ambito(ambito.nombre, this, ambito.funcion, ambito)
        if (expresionLogica != null) {
            expresionLogica!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        for (s in listaSentencias) {
            s.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito1)
            if (s is Retorno) {
                centinelaRetorno = true;
            }
        }
    }

    override fun analizarRetornoEnsentencias(): Boolean {
        return centinelaRetorno
    }

    override fun toString(): String {
        return "CicloWhile($expresionLogica)"
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