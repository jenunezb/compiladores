package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class VariableInmutable(var tipoDato: Token, var listaIdentificadores: ArrayList<Token>) : DeclaracionVariable() {


    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Declaracion Variable Inmutable")
        raiz.children.add(TreeItem("Tipo de dato: ${tipoDato.lexema}"))

        for (p in listaIdentificadores) {
            raiz.children.add(TreeItem(" Identificador ${p.lexema}"))
        }
        return raiz
    }

    override fun getJavaCode(): String {

        var codigo = "final " + tipoDato.getJavaCode() + " "
        for (s in listaIdentificadores) {
            codigo += s.getJavaCode() + ","
        }

        codigo = codigo.substring(0, codigo.length - 1)
        codigo += ";"

        return codigo
    }

}