package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var ambito1 = Ambito(ambito.nombre, this, ambito.funcion, ambito)
        for (s in listaSentencias!!) {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito1)
            if (s is Retorno) {
                centinelaRetorno = true;
            }
        }
        if (listaSentenciasNot != null) {
            for (s in listaSentenciasNot!!) {
                s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito1)
                if (s is Retorno) {
                    centinelaRetorno = true;
                }
            }
        }

    }

    override fun analizarRetornoEnsentencias(): Boolean {
        return centinelaRetorno
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {
        var ambito1 = Ambito(ambito.nombre, this, ambito.funcion, ambito)
        if (expresionLogica != null) {
            expresionLogica!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }

        for (s in listaSentencias!!) {
            s.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito1)
        }
        if (listaSentenciasNot != null) {
            for (s in listaSentenciasNot!!) {
                s.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito1)
            }
        }
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