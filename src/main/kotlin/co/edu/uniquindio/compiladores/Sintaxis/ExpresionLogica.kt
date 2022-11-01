package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.lexico.Error

class ExpresionLogica() : Expresion() {
    var expLogica1: ExpresionLogica? = null
    var expLogica2: ExpresionLogica? = null
    var expLogica3: ExpresionLogica? = null
    var operadorBinario: Token? = null
    var operadorUnario: Token? = null
    var expRelacional: ExpresionRelacional? = null


    //Constructores Secundarios
    constructor(
        operadorUnario: Token?,
        expLogica1: ExpresionLogica?,
        operadorBinario: Token?,
        expLogica2: ExpresionLogica?
    ) : this() {
        this.operadorUnario = operadorUnario
        this.expLogica1 = expLogica1
        this.operadorBinario = operadorBinario
        this.expLogica2 = expLogica2
    }

    constructor(operadorUnario: Token?, expLogica1: ExpresionLogica?) : this() {
        this.operadorUnario = operadorUnario
        this.expLogica1 = expLogica1
    }


    constructor(expRelacional: ExpresionRelacional?, operadorBinario: Token?, expLogica3: ExpresionLogica?) : this() {
        this.expRelacional = expRelacional
        this.operadorBinario = operadorBinario
        this.expLogica3 = expLogica3
    }

    constructor(expRelacional: ExpresionRelacional?) : this() {
        this.expRelacional = expRelacional
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Expresion Logica")

        if (operadorUnario != null && expLogica1 != null && operadorBinario != null && expLogica2 != null) {
            raiz.children.add(TreeItem("Operador Unario: ${operadorUnario!!.lexema}"))
            raiz.children.add(expLogica1!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador Binario: ${operadorBinario!!.lexema}"))
            raiz.children.add(expLogica3!!.getArbolVisual())
        } else {
            if (operadorUnario != null && expLogica1 != null) {
                raiz.children.add(TreeItem("Operador Unario: ${operadorUnario!!.lexema}"))
                raiz.children.add(expLogica1!!.getArbolVisual())
            } else {
                if (expRelacional != null && operadorBinario != null && expLogica3 != null) {

                    raiz.children.add(expRelacional!!.getArbolVisual())
                    raiz.children.add(TreeItem("Operador Binario: ${operadorBinario!!.lexema}"))
                    raiz.children.add(expLogica3!!.getArbolVisual())
                } else {
                    if (expRelacional != null) {
                        raiz.children.add(expRelacional!!.getArbolVisual())
                    }
                }
            }
        }
        return raiz
    }

    override fun toString(): String {
        if (operadorUnario != null && expLogica1 != null && operadorBinario != null && expLogica2 != null) {
            return " ${operadorUnario!!.lexema} ${expLogica1.toString()} ${operadorBinario!!.lexema} ${expLogica2.toString()} "
        }
        if (operadorUnario != null && expLogica1 != null) {
            return "${operadorUnario!!.lexema} ${expLogica1.toString()}  "
        }
        if (expRelacional != null && operadorBinario != null && expLogica3 != null) {
            return " ${expRelacional.toString()} ${operadorBinario!!.lexema} ${expLogica3.toString()} "
        }
        if (expRelacional != null) {
            return " ${expRelacional.toString()} "
        }
        return ""
    }

    override fun getJavaCode(): String {

        if (expLogica1 != null && expLogica2 != null) {

            return operadorUnario!!.getJavaCode() + " " + expLogica1!!.getJavaCode() + " " + operadorBinario!!.getJavaCode() + expLogica2!!.getJavaCode()

        }
        if (expLogica1 != null) {

            return operadorUnario!!.getJavaCode() + expLogica1!!.getJavaCode()
        }
        if (expRelacional != null && expLogica3 != null) {

            return expRelacional!!.getJavaCode() + operadorBinario!!.getJavaCode() + expLogica3!!.getJavaCode()
        }
        if (expRelacional != null) {
            return expRelacional!!.getJavaCode()
        }
        return ""
    }
}