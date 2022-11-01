package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
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

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {
        expresion!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
    }

    override fun getJavaCode(): String {

        var cadena = "JOptionPane.showMessageDialog( null," + expresion!!.getJavaCode()+");"
        return cadena
    }


}