package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional() : Expresion() {
    var expAritmetica1: ExpresionAritmetica? = null
    var expAritmetica2: ExpresionAritmetica? = null
    var operador: Token? = null
    var valorLogico: ValorLogico? = null

    var expresion: Expresion? = null
    var fila = 0;
    var columna = 0;


    //Constructores Secundarios
    constructor(
        expAritmetica1: ExpresionAritmetica?,
        operador: Token?,
        expAritmetica2: ExpresionAritmetica?,
        fila: Int,
        columna: Int
    ) : this() {
        this.expAritmetica1 = expAritmetica1
        this.operador = operador
        this.expAritmetica2 = expAritmetica2
        this.fila = fila
        this.columna = columna
    }

    constructor(valorLogico: ValorLogico?) : this() {
        this.valorLogico = valorLogico
    }


    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Expresion Relacional")

        if (expAritmetica1 != null && operador != null && expAritmetica2 != null) {

            raiz.children.add(expAritmetica1!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador Relacional: ${operador!!.lexema}"))
            raiz.children.add(expAritmetica2!!.getArbolVisual())
        }
        if (valorLogico != null) {
            raiz.children.add(valorLogico!!.getArbolVisual())
        }
        if (expresion != null) {
            raiz.children.add(expresion!!.getArbolVisual())
        }

        return raiz
    }

    override fun toString(): String {

        if (expAritmetica1 != null && operador != null && expAritmetica2 != null) {
            return "${expAritmetica1.toString()} ${operador!!.lexema} ${expAritmetica2.toString()}"
        }
        if (valorLogico != null) {
            return " ${valorLogico.toString()}"
        }
        return ""
    }
    override fun getJavaCode(): String {

        if (expAritmetica1 != null && expAritmetica2 != null) {

            return expAritmetica1!!.getJavaCode() + operador!!.getJavaCode() + expAritmetica2!!.getJavaCode()

        } else {
            return valorLogico!!.getJavaCode()
        }
    }
}