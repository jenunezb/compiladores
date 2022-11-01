package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class VariableMutable(var tipoDato: Token, var listaIdentificadores: ArrayList<Token>) : DeclaracionVariable() {

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Declaracion Variable Mutable ")
        raiz.children.add(TreeItem("Tipo de dato: ${tipoDato.lexema}"))

        for (p in listaIdentificadores) {
            raiz.children.add(TreeItem(" Identificador ${p.lexema}"))
        }
        return raiz
    }

    fun obtenerNombresIdent(): ArrayList<String> {

        var nombres = ArrayList<String>()
        if (listaIdentificadores != null) {
            for (l in listaIdentificadores) {
                nombres.add(l.lexema)
            }
        }
        return nombres
    }

    override fun toString(): String {
        return "${tipoDato.lexema},${obtenerNombresIdent()}"
    }

    override fun getJavaCode(): String {

        var codigo = tipoDato.getJavaCode() + " "
        for (s in listaIdentificadores) {
            codigo += s.getJavaCode() + ", "
        }

        codigo = codigo.substring(0, codigo.length - 2)
        codigo += ";"

        return codigo
    }

}