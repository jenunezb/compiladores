package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class UnidadDeCompilacion(var listaFunciones: ArrayList<Funcion>) {
    override fun toString(): String {
        return "UnidadDeCompilacion"
    }

    var ambito: Ambito? = null
    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Unidad de Compilacion")

        for (f in listaFunciones) {

            raiz.children.add(f.getArbolVisual())
        }
        return raiz
    }


    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>) {
        for (f in listaFunciones) {
            ambito = Ambito("Unidad de Compilacion", null, null, null)
            f.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito!!)
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>) {

        for (f in listaFunciones) {
            ambito = Ambito("Unidad de Compilacion", null, null, null)
            f.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito!!)
        }
    }


    fun getJavaCode(): String {

        var codigo = " import javax.swing.JOptionPane; public class Principal{"

        for (f in listaFunciones) {
            codigo += f.getJacaCode()
        }

        codigo += "}"
        return codigo
    }


}