package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class InvocacionFuncion(var identificadorMetodo: Token, var listaArgumentos: ArrayList<Expresion>) : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Invocacion de Funcion")
        raiz.children.add(TreeItem("Nombre de Metodo: ${identificadorMetodo.lexema}"))

        if (!listaArgumentos.isEmpty()) {


            var raizArgumentos = TreeItem<String>("Argumentos")
            for (p in listaArgumentos) {
                raizArgumentos.children.add(p.getArbolVisual())
            }
            raiz.children.add(raizArgumentos)

        } else {
            raiz.children.add(TreeItem("Sin Argumentos"))
        }
        return raiz
    }


    fun obtenerTiposArgumentos(
        tablaSimbolos: TablaSimbolos,
        ambito: Ambito,
        erroresSemanticos: ArrayList<Error>
    ): ArrayList<String> {
        var listaArg = ArrayList<String>()
        for (a in listaArgumentos) {
            listaArg.add(a.obtenerTipoExpresion(tablaSimbolos, ambito, erroresSemanticos))
        }
        return listaArg
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {
        var listaTipoArgs = obtenerTiposArgumentos(tablaSimbolos, ambito, erroresSemanticos)
        var sim = tablaSimbolos.buscarSimboloFuncion(identificadorMetodo.lexema, listaTipoArgs)
        if (sim == null) {
            erroresSemanticos.add(
                Error(
                    "La funcion ${identificadorMetodo.lexema} $listaTipoArgs no existe",
                    identificadorMetodo.fila,
                    identificadorMetodo.columna
                )
            )
        }
    }

    override fun toString(): String {
        return "InvocacionFuncion(identificadorMetodo=$identificadorMetodo, listaArgumentos=$listaArgumentos)"
    }


    override fun getJavaCode(): String {

        var codigo = identificadorMetodo.getJavaCode() + "("

        for (s in listaArgumentos) {

            codigo += s.getJavaCode() + ","
        }

        codigo = codigo.substring(0, codigo.length - 1)
        codigo += ");"
        return codigo
    }

}