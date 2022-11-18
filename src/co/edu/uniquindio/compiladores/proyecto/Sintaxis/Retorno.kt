package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
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


    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {
        if (expresion != null) {
            expresion!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
            var tipoEx = expresion!!.obtenerTipoExpresion(tablaSimbolos, ambito, erroresSemanticos)
            if (ambito.funcion!!.tipoRetorno.lexema != tipoEx) {
                erroresSemanticos.add(
                    Error(
                        "El tipo de dato del retorno (${tipoEx}) no coiciden con el tipo de retorno (${ambito.funcion!!.tipoRetorno.lexema}) de la funcion ${ambito.funcion.toString()}",
                        fila,
                        columna
                    )
                )
            }
        }
        if (valorToken != null) {
            if (valorToken!!.categoria == Categoria.IDENTIFICADOR_ARREGLO) {
                var s = tablaSimbolos.buscarSimboloValor(
                    valorToken!!.lexema,
                    ambito,
                    valorToken!!.fila,
                    valorToken!!.columna
                )
                if (s == null) {
                    erroresSemanticos.add(
                        Error(
                            "El arreglo (${valorToken!!.lexema})no existe en el ambito (${ambito})",
                            valorToken!!.fila,
                            valorToken!!.columna
                        )
                    )
                } else {
                    var tipo = s!!.tipo
                    if (ambito.funcion!!.tipoRetorno.lexema != tipo) {
                        erroresSemanticos.add(
                            Error(
                                "El tipo de dato del retorno (${tipo}) no coiciden con el tipo de retorno (${ambito.funcion!!.tipoRetorno.lexema}) de la funcion ${ambito.funcion.toString()}",
                                valorToken!!.fila, valorToken!!.columna
                            )
                        )
                    }
                }

            } else {
                if (valorToken!!.categoria == Categoria.PALABRA_RESERVADA_VACIO) {
                    if (ambito.funcion!!.tipoRetorno.categoria != Categoria.PALABRA_RESERVADA_VACIO) {
                        erroresSemanticos.add(
                            Error(
                                "El tipo de dato del retorno (${valorToken!!.lexema}) no coiciden con el tipo de retorno (${ambito.funcion!!.tipoRetorno.lexema}) de la funcion ${ambito.funcion.toString()}",
                                valorToken!!.fila, valorToken!!.columna
                            )
                        )
                    }
                }
                if (valorToken!!.categoria == Categoria.CARACTER) {
                    if (ambito.funcion!!.tipoRetorno.categoria != Categoria.IDENTIFICADOR_TIPO_CARACTER) {
                        erroresSemanticos.add(
                            Error(
                                "El tipo de dato del retorno (lett) no coiciden con el tipo de retorno (${ambito.funcion!!.tipoRetorno.lexema}) de la funcion ${ambito.funcion.toString()}",
                                valorToken!!.fila, valorToken!!.columna
                            )
                        )
                    }
                }
            }
        }
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