package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import javafx.scene.control.TreeItem

open class FuncionMatematica() : Sentencia() {

    var matematica: Matematica? = null
    var variable: Token? = null
    var operador: Token? = null

    constructor(matematica: Matematica?) : this() {
        this.matematica = matematica!!
    }

    constructor(variable: Token, operador: Token, matematica: Matematica?) : this() {
        this.variable = variable
        this.operador = operador
        this.matematica = matematica!!
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Funciones especiales Matematicas")
        if (matematica != null && variable == null && operador == null) {
            raiz.children.add(matematica!!.getArbolVisual())
        } else {
            if (matematica != null && variable != null && operador != null) {
                raiz.children.add(TreeItem("variable: ${variable!!.lexema}"))
                raiz.children.add(TreeItem("Operador Asignacion: ${operador!!.lexema}"))
                raiz.children.add(matematica!!.getArbolVisual())
            }
        }
        return raiz
    }

    override fun getJavaCode(): String {

        if (matematica != null && variable == null && operador == null) {
            return matematica!!.getJavaCode()
        } else {
            if (matematica != null && variable != null && operador != null) {
                var codigo = ""
                codigo += variable!!.getJavaCode()
                codigo += operador!!.getJavaCode()
                codigo += matematica!!.getJavaCode()
                return codigo
            }

        }
        return ""

    }


}