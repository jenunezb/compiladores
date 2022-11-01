package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class DeclaracionRuptura(var token: Token) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem<String>("Declaracion Romper: ${token.lexema} ")

    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {
        if (ambito.sentencia is Ciclo) {
        } else {
            erroresSemanticos.add(
                Error(
                    "La declaracion de ruptura: ${token.lexema} solo es permitida al interior de una estructura repetitiva, ",
                    token.fila,
                    token.columna
                )
            )
        }
    }

    override fun getJavaCode(): String {
        return "break;"
    }

}