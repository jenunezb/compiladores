package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Asignacion(
    var identificador: Token,
    var apAsig: Token?,
    var expresion: Expresion?,
    var invocacion: InvocacionFuncion?
) : Sentencia() {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador, apAsig=$apAsig, expresion=$expresion, invocacion=$invocacion)"
    }

    var cont = 1;

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Asignacion")
        raiz.children.add(TreeItem("Identificador Variable: ${identificador.lexema}"))
        if (apAsig != null) {
            raiz.children.add(TreeItem("Operador de Asigancion: ${apAsig!!.lexema}"))
        }
        if (invocacion != null) {
            raiz.children.add(invocacion!!.getArbolVisual())
        }
        if (expresion != null) {
            var raizExpresion = TreeItem<String>("Expresion")

            raizExpresion.children.add(expresion!!.getArbolVisual())
            raiz.children.add(raizExpresion)
        }

        return raiz
    }


    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        var s =
            tablaSimbolos.buscarSimboloValor(identificador.lexema, ambito, identificador.fila, identificador.columna)
        if (s == null) {

            listaErrores.add(
                Error(
                    "El campo ${identificador.lexema} no existe dentro del ambito $ambito", identificador.fila,
                    identificador.columna
                )
            )
        } else {

            var tipo = s.tipo
            if (!s.modificable) {
                if (cont > 1) {
                    listaErrores.add(
                        Error(
                            "La variable (${identificador.lexema}) es inmutable y su uso excedio el limite",
                            identificador.fila,
                            identificador.columna
                        )
                    )
                }
            }
            if (expresion != null) {
                expresion!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
                var tipoExp = expresion!!.obtenerTipoExpresion(tablaSimbolos, ambito, listaErrores)
                if (tipoExp != tipo) {
                    listaErrores.add(
                        Error(
                            "El tipo de dato de la expresion($tipoExp) no coincide con el tipo de dato del campo(${identificador.lexema})  que es $tipo",
                            identificador.fila,
                            identificador.columna
                        )
                    )
                }
            } else {
                if (invocacion != null) {
                    invocacion!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
                    var s2 = tablaSimbolos.buscarSimboloFuncion(
                        invocacion!!.identificadorMetodo.lexema,
                        invocacion!!.obtenerTiposArgumentos(tablaSimbolos, ambito, listaErrores)
                    )
                    if (s2 != null) {
                        if (s2.tipoRetorno != s.tipo) {
                            listaErrores.add(
                                Error(
                                    "El tipo de retorno (${s2.tipoRetorno}) de  la funcion (${invocacion!!.identificadorMetodo.lexema}) no coincide con el tipo de dato del campo(${s.nombre})  que es ($tipo)",
                                    identificador.fila,
                                    identificador.columna
                                )
                            )
                        }
                    }
                }
            }
        }

    }

    override fun getJavaCode(): String {

        if (identificador!=null &&expresion != null) {
            return identificador.getJavaCode() + "=" + expresion!!.getJavaCode()+";"
        }
        if (identificador!=null &&invocacion != null) {
            return identificador.getJavaCode() +  "=" + invocacion!!.getJavaCode()+";"
        }

        return ""

    }


}