package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        for (i in listaIdentificadores) {
            tablaSimbolos.guardarSimboloValor(i.lexema, tipoDato.lexema, true, ambito, i.fila, i.columna)
        }
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