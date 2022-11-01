package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Matematica(var fnmatematica: Token, var valorNumerico: ValorNumerico) : FuncionMatematica() {

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Tipo de Funcion")
        raiz.children.add(TreeItem<String>("Nombre:  ${fnmatematica.lexema}  "))
        raiz.children.add(valorNumerico.getArbolVisual())
        return raiz
    }


    override fun getJavaCode(): String {

        var cadena = ""
        if (fnmatematica.lexema == "Sein") {
            cadena = "Math.sin(" + valorNumerico.getJavaCode() + ");"

        }
        if (fnmatematica.lexema == "Cosinus") {
            cadena = "Math.cos(" + valorNumerico.getJavaCode() + ");"
        }
        if (fnmatematica.lexema == "Absolu") {
            cadena = "Math.abs(" + valorNumerico.getJavaCode() + ");"
        }

        return cadena
    }
}

