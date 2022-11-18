package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import javafx.scene.control.TreeItem

class UnidadDeCompilacion(var listaFunciones: ArrayList<Funcion>) {

    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Unidad de Compilacion")

        for (f in listaFunciones) {
            raiz.children.add(f.getArbolVisual())
        }
        return raiz
    }

    fun getJavaCode(): String {

    var codigo = " import javax.swing.JOptionPane; public class Principal{"

    for (f in listaFunciones) {
    codigo += f.getJavaCode()
    }
    codigo += "}"
    return codigo
    }



}