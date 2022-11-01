package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionCadena() : Expresion() {

    var concatenacion: Token? = null
    var expresion: Expresion? = null
    var ideVariable: Token? = null
    var token: Token? = null
    var tokenCadenaSola: Token? = null

    constructor(tokkenCaden: Token?, concatenacion: Token?, expresion: Expresion?) : this() {
        this.concatenacion = concatenacion
        this.expresion = expresion
        this.token = tokkenCaden
    }

    constructor(token: Token?) : this() {
        if (token!!.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            this.ideVariable = token
        } else {
            if (token!!.categoria == Categoria.CADENA) {
                this.tokenCadenaSola = token
            }

        }

    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Expresion Cadena")

        if (token != null && concatenacion != null && expresion != null) {

            raiz.children.add(TreeItem("Cadena: ${token!!.lexema}"))
            raiz.children.add(TreeItem("Operador Concatenacion: ${concatenacion!!.lexema}"))
            raiz.children.add(expresion!!.getArbolVisual())
        } else {
            if (tokenCadenaSola != null && tokenCadenaSola!!.categoria == Categoria.CADENA) {
                raiz.children.add(TreeItem("Cadena: ${tokenCadenaSola!!.lexema}"))
            } else {
                if (ideVariable != null && ideVariable!!.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                    raiz.children.add(TreeItem("Identificador de variable: ${ideVariable!!.lexema}"))
                }
            }
        }
        return raiz
    }

    override fun toString(): String {
        if (token != null && concatenacion != null && expresion != null) {
            return "(${token!!.lexema} ${concatenacion!!.lexema} ${expresion.toString()})"
        }
        if (token != null && token!!.categoria == Categoria.CADENA) {
            return "(${token!!.lexema} )"
        }
        if (ideVariable != null && token!!.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            return "(${ideVariable!!.lexema} )"
        }

        return ""

    }

    override fun getJavaCode(): String {
        var codigo = ""
        if (token != null && expresion != null) {
            codigo += token!!.getJavaCode() + "+" + expresion!!.getJavaCode()
        }
        if (tokenCadenaSola != null) {
            codigo += tokenCadenaSola!!.getJavaCode()
        }
        if (ideVariable != null) {
            codigo += ideVariable!!.getJavaCode()
        }
        return codigo
    }

}