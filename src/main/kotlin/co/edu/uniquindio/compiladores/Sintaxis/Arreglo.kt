package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import kotlin.collections.ArrayList


open class Arreglo(var tipodato: Token, var identificador: Token, var listaDatos: ArrayList<Expresion>?) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Arreglo")

        raiz.children.add(TreeItem("Tipo: ${tipodato!!.lexema}"))
        raiz.children.add(TreeItem("Identificador: ${identificador!!.lexema}"))

        if (listaDatos != null) {
            for (s in listaDatos!!) {
                raiz.children.add(s.getArbolVisual())
            }
        }
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        tablaSimbolos.guardarSimboloValor(
            identificador.lexema,
            tipodato.lexema,
            true,
            ambito,
            identificador.fila,
            identificador.columna
        )
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {

        for (e in listaDatos!!) {
            e!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            var tipo = e.obtenerTipoExpresion(tablaSimbolos, ambito, listaErrores)
            if (tipo != tipodato.lexema) {
                listaErrores.add(
                    Error(
                        "El tipo de dato de la expresion($tipo) no coincide con el tipo de dato del arreglo(${tipodato.lexema})",
                        identificador.fila,
                        identificador.columna
                    )
                )
            }
        }
    }


    /**
     * int[] arreglo = {1,2,3,4}
     */

    override fun getJavaCode(): String {
        var codigo = tipodato.getJavaCode() + "[]" + identificador.getJavaCode() + "={"

        if (listaDatos != null) {
            for (e in listaDatos!!) {
                codigo += e.getJavaCode() + ", "
            }
            codigo = codigo.substring(0, codigo.length - 1)
            codigo += "}"
        }
        codigo += ";"
        return codigo
    }
}