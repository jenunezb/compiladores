package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
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

    override fun obtenerTipoExpresion(
        tablaSimbolos: TablaSimbolos,
        ambito: Ambito,
        listaErrores: ArrayList<Error>
    ): String {
        return "Asert"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {
        if (expAritmetica1 != null && expAritmetica2 != null && operador != null) {
            expAritmetica1!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
            expAritmetica2!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)

            var tipoExp1 = expAritmetica1!!.obtenerTipoExpresion(tablaSimbolos, ambito, erroresSemanticos)
            var tipoExp2 = expAritmetica2!!.obtenerTipoExpresion(tablaSimbolos, ambito, erroresSemanticos)

            if ((tipoExp1 == "ENTERO" || tipoExp2 == "Dc") && (tipoExp1 == "ENTERO" || tipoExp2 == "Dc")) {

            } else {
                erroresSemanticos.add(
                    Error(
                        "La expresion no corresponde a una expresion Logica valida ",
                        fila, columna
                    )
                )
            }
        }
    }

    /**
     *  5<v
     */
    override fun getJavaCode(): String {

        if (expAritmetica1 != null && expAritmetica2 != null) {

            return expAritmetica1!!.getJavaCode() + operador!!.getJavaCode() + expAritmetica2!!.getJavaCode()

        } else {
            return valorLogico!!.getJavaCode()
        }
    }
}