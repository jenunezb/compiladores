package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class IncrementoDecremento(var identificador: Token?, var operador: Token) : Sentencia() {
    override fun toString(): String {
        return "Incremento(identificador=$identificador, operador=$operador)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Incremento o Decremento")

        if (operador.categoria == Categoria.OPERADOR_INCREMENTO) {
            var raizIncremento = TreeItem<String>("Incremento")

            if (identificador != null) {
                raizIncremento.children.add(TreeItem<String>("${operador.lexema}  ${identificador!!.lexema}"))
            }

            raiz.children.add(raizIncremento)
        }
        if (operador.categoria == Categoria.OPERADOR_DECREMENTO) {
            var raizDecremento = TreeItem<String>("Decremento")
            if (identificador != null) {
                raizDecremento.children.add(TreeItem<String>("${operador.lexema}  ${identificador!!.lexema}"))
            }

            raiz.children.add(raizDecremento)
        }
        return raiz
    }

    override fun getJavaCode(): String {
        var cadena = ""
        if (operador.lexema == "[+]") {
            cadena = "++" + identificador!!.getJavaCode()
        }
        if (operador.lexema == "[-]") {
            cadena = "--" + identificador!!.getJavaCode()
        }
        cadena += ";"

        return cadena
    }

}