package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Funcion(
    var nombreFuncion: Token,
    var tipoRetorno: Token,
    var listaParametros: ArrayList<Parametro>,
    var listaSentencia: ArrayList<Sentencia>, var fila: Int, var columna: Int
) {
    override fun toString(): String {
        return "(${nombreFuncion.lexema}, tipoRetorno=${tipoRetorno.lexema}, ${obtenerTiposParametros()})"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Funcion")
        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))
        raiz.children.add(TreeItem("Tipo Retorno: ${tipoRetorno.lexema}"))

        var raizParametros = TreeItem<String>("Parametro")
        for (p in listaParametros) {
            raizParametros.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizParametros)

        var raizSentencias = TreeItem<String>("Sentencia")
        for (p in listaSentencia) {
            raizSentencias.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizSentencias)

        return raiz
    }

    fun obtenerTiposParametros(): ArrayList<String> {
        var lista = ArrayList<String>()
        for (p in listaParametros) {
            lista.add(p.tipoDato.lexema)
        }
        return lista
    }

    fun getJavaCode(): String {

        var codigo = ""
        // Main de Java
        if (nombreFuncion.lexema == "<<*INICIO*>>") {

            codigo = "public static void main(String[] args){"

        } else {
            codigo = "static " + tipoRetorno.getJavaCode() + " " + nombreFuncion.getJavaCode() + "("

            if (listaParametros.isNotEmpty()) {

                for (p in listaParametros) {
                    codigo += p.getJavaCode() + ","
                }
                codigo = codigo.substring(0, codigo.length - 1)
            }
            codigo += "){"

        }

        for (s in listaSentencia) {
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo

    }


}