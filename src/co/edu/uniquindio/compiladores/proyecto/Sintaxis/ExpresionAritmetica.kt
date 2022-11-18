package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
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

    override fun obtenerTipoExpresion(
        tablaSimbolos: TablaSimbolos,
        ambito: Ambito,
        listaErrores: ArrayList<Error>
    ): String {
        if (expAritmetica1 != null && expAritmetica2 != null && valorNumerico == null) {

            var tipo1 = expAritmetica1!!.obtenerTipoExpresion(tablaSimbolos, ambito, listaErrores)
            var tipo2 = expAritmetica2!!.obtenerTipoExpresion(tablaSimbolos, ambito, listaErrores)
            if (tipo1 == Categoria.DECIMAL.name || tipo2 == Categoria.DECIMAL.name) {
                return "Dc"
            } else {
                return "ENTERO"
            }
        } else {
            if (expAritmetica1 != null && expAritmetica2 == null && valorNumerico == null) {
                return obtenerTipoExpresion(tablaSimbolos, ambito, listaErrores)

            } else {
                if (valorNumerico != null && expAritmetica1 != null && expAritmetica2 == null) {

                    var tipo1 = obtenerTipoCampo(valorNumerico, ambito, tablaSimbolos, listaErrores)
                    var tipo2 = expAritmetica1!!.obtenerTipoExpresion(tablaSimbolos, ambito, listaErrores)
                    if (tipo1 == Categoria.DECIMAL.name || tipo2 == Categoria.DECIMAL.name) {
                        return "Dc"
                    } else {
                        return "ENTERO"
                    }

                } else {
                    if (valorNumerico != null) {

                        return obtenerTipoCampo(valorNumerico, ambito, tablaSimbolos, listaErrores)
                    }
                }
            }
        }

        return ""

    }

    fun obtenerTipoCampo(
        valorNumerico: ValorNumerico?,
        ambito: Ambito,
        tablaSimbolos: TablaSimbolos,
        listaErrores: ArrayList<Error>
    ): String {

        if (valorNumerico!!.termino.categoria == Categoria.ENTERO) {
            return "ENTERO"
        } else {
            if (valorNumerico!!.termino.categoria == Categoria.DECIMAL) {
                return "Dc"
            } else {
                var simbolo = tablaSimbolos.buscarSimboloValor(
                    valorNumerico!!.termino.lexema,
                    ambito,
                    valorNumerico!!.termino.fila,
                    valorNumerico!!.termino.columna
                )
                if (simbolo != null) {
                    return simbolo.tipo
                } else {
                    listaErrores.add(
                        Error(
                            "El campo ${valorNumerico!!.termino.lexema} no existe dentro del ámbito  $ambito",
                            valorNumerico!!.termino.fila,
                            valorNumerico!!.termino.columna
                        )
                    )
                }
            }
        }
        return ""
    }


    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {

        if (valorNumerico != null) {
            if (valorNumerico!!.termino.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                var simbolo = tablaSimbolos.buscarSimboloValor(
                    valorNumerico!!.termino.lexema,
                    ambito,
                    valorNumerico!!.termino.fila,
                    valorNumerico!!.termino.columna
                )
                if (simbolo == null) {
                    erroresSemanticos.add(
                        Error(
                            "El campo (${valorNumerico!!.termino.lexema}) no existe dentro del ámbito  ($ambito)",
                            valorNumerico!!.termino.fila,
                            valorNumerico!!.termino.columna
                        )
                    )
                } else {
                    var tipo = simbolo!!.tipo
                    if (tipo == "ENTERO" || tipo == "Dc") {

                    } else {
                        erroresSemanticos.add(
                            Error(
                                "El campo (${valorNumerico!!.termino.lexema}) no es un tipo de dato numerico",
                                valorNumerico!!.termino.fila,
                                valorNumerico!!.termino.columna
                            )
                        )
                    }
                }
            }
        }
        if (expAritmetica1 != null) {
            expAritmetica1!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if (expAritmetica2 != null) {
            expAritmetica2!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
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