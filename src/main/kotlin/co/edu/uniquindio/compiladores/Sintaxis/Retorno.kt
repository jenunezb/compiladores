package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Retorno() : Sentencia() {


    var valorToken: Token? = null
    var expresion: Expresion? = null

    var fila: Int = 0;
    var columna: Int = 0;


    constructor(valorToken: Token?, fila: Int, columna: Int) : this() {
        this.valorToken = valorToken
        this.fila = fila
        this.columna = columna
    }


    constructor(expresion: Expresion?, fila: Int, columna: Int) : this() {
        this.expresion = expresion
        this.fila = fila
        this.columna = columna
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Retorno")

        if (expresion != null) {
            raiz.children.add(expresion!!.getArbolVisual())
        }

        if (valorToken != null) {
            if (valorToken!!.categoria == Categoria.IDENTIFICADOR_ARREGLO) {
                raiz.children.add(TreeItem("Arreglo: ${valorToken!!.lexema}"))
            }
            if (valorToken!!.categoria == Categoria.PALABRA_RESERVADA_VACIO) {
                raiz.children.add(TreeItem("Vacio: ${valorToken!!.lexema}"))
            }
            if (valorToken!!.categoria == Categoria.CARACTER) {
                raiz.children.add(TreeItem("Caracter: ${valorToken!!.lexema}"))
            }

        }
        return raiz
    }

    override fun getJavaCode(): String {
        if (expresion != null) {
            return "return " + expresion!!.getJavaCode() + ";"
        }
        if (valorToken != null && expresion == null) {
            return "return " + valorToken!!.getJavaCode() + ";"

        }
        return ""
    }


}