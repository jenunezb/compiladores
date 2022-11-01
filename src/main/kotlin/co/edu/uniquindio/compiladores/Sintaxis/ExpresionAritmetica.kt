package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

open class ExpresionAritmetica() : Expresion() {

    var expAritmetica1: ExpresionAritmetica? = null
    var expAritmetica2: ExpresionAritmetica? = null
    var operador: Token? = null
    var valorNumerico: ValorNumerico? = null

    //Constructores Secundarios
    constructor(expArimetica1: ExpresionAritmetica?, operador: Token?, expAritmetica2: ExpresionAritmetica?) : this() {
        this.expAritmetica1 = expArimetica1
        this.operador = operador
        this.expAritmetica2 = expAritmetica2

    }

    constructor(expArimetica1: ExpresionAritmetica?) : this() {
        this.expAritmetica1 = expArimetica1
    }

    constructor(valorNumerico: ValorNumerico?, operador: Token?, expAritmetica2: ExpresionAritmetica?) : this() {
        this.valorNumerico = valorNumerico
        this.operador = operador
        this.expAritmetica2 = expAritmetica2

    }

    constructor(valorNumerico: ValorNumerico?) : this() {
        this.valorNumerico = valorNumerico
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Expresion Aritmetica")

        if (expAritmetica1 != null && operador != null && expAritmetica2 != null) {

            raiz.children.add(expAritmetica1!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador Aritmetico: ${operador!!.lexema}"))
            raiz.children.add(expAritmetica2!!.getArbolVisual())
        } else {
            if (expAritmetica1 != null) {
                raiz.children.add(expAritmetica1!!.getArbolVisual())
            } else {
                if (valorNumerico != null && operador != null && expAritmetica2 != null) {
                    raiz.children.add(valorNumerico!!.getArbolVisual())
                    raiz.children.add(TreeItem("Operador Aritmetico: ${operador!!.lexema}"))
                    raiz.children.add(expAritmetica2!!.getArbolVisual())
                } else {
                    if (valorNumerico != null) {
                        raiz.children.add(valorNumerico!!.getArbolVisual())
                    }
                }
            }
        }

        return raiz
    }

    override fun toString(): String {

        if (expAritmetica1 != null && operador != null && expAritmetica2 != null) {
            return "${expAritmetica1.toString()} ${operador!!.lexema} ${expAritmetica2.toString()}"
        }
        if (expAritmetica1 != null) {
            return "${expAritmetica1.toString()} "
        }
        if (valorNumerico != null && operador != null && expAritmetica2 != null) {
            return "${valorNumerico.toString()} ${operador!!.lexema} ${expAritmetica2.toString()} "
        }
        if (valorNumerico != null) {
            return "${valorNumerico.toString()}"
        }
        return ""
    }

    override fun getJavaCode(): String {
        if (expAritmetica1 != null && operador != null && expAritmetica2 != null) {
            return "(" + expAritmetica1!!.getJavaCode() + ")" + operador!!.getJavaCode() + expAritmetica2!!.getJavaCode()
        }
        if (expAritmetica1 != null) {
            return "(" + expAritmetica1!!.getJavaCode() + ")"
        }
        if (valorNumerico != null && operador != null && expAritmetica2 != null) {
            return valorNumerico!!.getJavaCode() + " " + operador!!.getJavaCode() + " " + expAritmetica2!!.getJavaCode()

        }
        if (valorNumerico != null) {
            return valorNumerico!!.getJavaCode()

        }
        return ""
    }


}