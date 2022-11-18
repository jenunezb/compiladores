package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
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

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {
        if (identificador != null) {
            var simbolo = tablaSimbolos.buscarSimboloValor(
                identificador!!.lexema,
                ambito,
                identificador!!.fila,
                identificador!!.columna
            )
            if (simbolo == null) {
                erroresSemanticos.add(
                    Error(
                        "El campo (${identificador!!.lexema}) no existe dentro del ámbito  ($ambito)",
                        identificador!!.fila,
                        identificador!!.columna
                    )
                )
            } else {
                var tipo = simbolo!!.tipo
                if (tipo == "ENTERO" || tipo == "Dc") {

                } else {
                    erroresSemanticos.add(
                        Error(
                            "El campo (${identificador!!.lexema}) no  es un tipo de dato numerico",
                            identificador!!.fila,
                            identificador!!.columna
                        )
                    )
                }
            }
        }
    }

    override fun getJavaCode(): String {
        var cadena = ""
        if (operador.lexema == "[+]") {
            cadena =  identificador!!.getJavaCode()+"++"
        }
        if (operador.lexema == "[-]") {
            cadena = identificador!!.getJavaCode()+"--"
        }
        cadena += ";"

        return cadena
    }

}