package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Matematica(var fnmatematica: Token, var valorNumerico: ValorNumerico) : FuncionMatematica() {

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Tipo de Funcion")
        raiz.children.add(TreeItem<String>("Nombre:  ${fnmatematica.lexema}  "))
        raiz.children.add(valorNumerico.getArbolVisual())
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {


        if (valorNumerico != null) {
            valorNumerico.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }

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

