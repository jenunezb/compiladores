package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
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