package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
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