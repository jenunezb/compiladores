package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

open class ExpresionRelacional(var variableA:Token, var variableB:Token, var idenRel:Token) : Expresion() {

    override fun toString(): String {
        return "Expresión Relacional ${variableA.lexema} ${idenRel.lexema} ${variableB.lexema}"
    }
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem<String>("Expresión Relacioal : ${variableA.lexema} ${variableB.lexema} ${idenRel.lexema}")
    }
}