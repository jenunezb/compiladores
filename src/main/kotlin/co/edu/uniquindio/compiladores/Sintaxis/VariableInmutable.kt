package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class VariableInmutable(var tipoDato: Token, var nombreInmutable:Token):Sentencia() {
    override fun toString(): String {
        return "Inmutable(tipoDato=$tipoDato, nombreParametros=$nombreInmutable)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem<String>("Inmutable ${tipoDato.lexema}  ${nombreInmutable.lexema}")
    }

    override fun getJavaCode(): String {
        return tipoDato.getJavaCode() + " " + nombreInmutable.getJavaCode()
    }
    }
