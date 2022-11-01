package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import javafx.scene.control.TreeItem

class Impresion(var expresion: Expresion?) : Sentencia() {

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Impresion")
        if (expresion != null) {
            raiz.children.add(expresion!!.getArbolVisual())
            return raiz
        }



        return raiz
    }

    override fun getJavaCode(): String {

        var cadena = "JOptionPane.showMessageDialog( null," + expresion!!.getJavaCode()+");"
        return cadena
    }


}