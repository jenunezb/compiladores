package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
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
        raiz.children.add(TreeItem("Tipo Retorno: ${tipoRetorno.lexema}"))
        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))

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


    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        tablaSimbolos.guardarSimboloFuncion(
            nombreFuncion.lexema,
            tipoRetorno.lexema,
            obtenerTiposParametros(),
            ambito,
            nombreFuncion.fila,
            nombreFuncion.columna
        )


        var ambito1 = Ambito(ambito.nombre, null, this, ambito)
        for (p in listaParametros) {

            tablaSimbolos.guardarSimboloValor(
                p.nombreParametro.lexema,
                p.tipoDato.lexema,
                true,
                ambito1,
                p.nombreParametro.fila,
                p.nombreParametro.columna
            )
        }
        for (s in listaSentencia) {

            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito1)
        }


    }

    fun analizarSemantica(
        tablaSimbolos: TablaSimbolos,
        listaErrores: ArrayList<Error>,
        ambito: Ambito
    ) {
        var ambito1 = Ambito(ambito.nombre, null, this, ambito)
        var centinela = false;
        var centinela2 = false;
        for (s in listaSentencia) {

            centinela2 = s.analizarRetornoEnsentencias()
            s.analizarSemantica(tablaSimbolos, listaErrores, ambito1)
            if (tipoRetorno.lexema != "VACIO" && (s is Retorno || centinela2)) {
                centinela = true;
            }
        }

        if (!centinela && tipoRetorno.lexema != "VACIO") {
            listaErrores.add(
                Error(
                    "Es necesario especificar un retorno del tipo ${tipoRetorno.lexema}", fila, columna
                )
            )
        }
    }


    fun getJacaCode(): String {

        var codigo = ""
        // Main de Java
        if (nombreFuncion.lexema == "¬INICIO¬") {

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