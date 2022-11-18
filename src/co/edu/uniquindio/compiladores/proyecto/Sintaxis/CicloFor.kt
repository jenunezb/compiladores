package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class CicloFor(

    var declaracionVariable: DeclaracionVariable,
    var expresionLogica: ExpresionLogica?,
    var incrementoDecremento: IncrementoDecremento,
    var listaSentencias: ArrayList<Sentencia>
) : Ciclo() {
    var centinelaRetorno = false
    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Ciclo for")
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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var ambito1 = Ambito(ambito.nombre, this, ambito.funcion, ambito)
        if (declaracionVariable != null) {
            declaracionVariable.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito1)
        }
        for (s in listaSentencias) {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito1)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {
        var ambito1 = Ambito(ambito.nombre, this, ambito.funcion, ambito)
        if (expresionLogica != null) {
            expresionLogica!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }

        if (incrementoDecremento != null) {
            incrementoDecremento.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        for (s in listaSentencias) {

            s.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito1)
        }
    }

    override fun analizarRetornoEnsentencias(): Boolean {
        return centinelaRetorno
    }

    override fun toString(): String {
        return "CicloFor(${declaracionVariable.toString()} $expresionLogica, ${incrementoDecremento.operador.lexema} ${incrementoDecremento.identificador!!.lexema})"
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