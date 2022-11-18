package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.Ambito
import co.edu.uniquindio.compiladores.proyecto.Semantica.Simbolo
import co.edu.uniquindio.compiladores.proyecto.Semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Lectura(var variable: Token, var expresionCadena: ExpresionCadena?) : Sentencia() {
    override fun toString(): String {
        return "Lectura(expresionCadena=$expresionCadena)"
    }

    var s: Simbolo? = null
    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Lectura de datos")

        if (expresionCadena != null && variable != null) {
            raiz.children.add(TreeItem("Identificador de variable: ${variable!!.lexema}"))
            raiz.children.add(expresionCadena!!.getArbolVisual())
        }

        return raiz
    }


    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: Ambito) {

        s = tablaSimbolos.buscarSimboloValor(variable.lexema, ambito, variable.fila, variable.columna)
        if (s == null) {

            erroresSemanticos.add(
                Error(
                    "El campo (${variable!!.lexema}) no existe dentro del ámbito  ($ambito)",
                    variable!!.fila,
                    variable!!.columna
                )
            )
        } else {

            if (expresionCadena != null) {
                expresionCadena!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
            }
        }
    }

    override fun getJavaCode(): String {
        return when (s!!.tipo) {
            "ENTERO" -> {
                variable.getJavaCode() + "= Integer.parseInt(JOptionPane.showInputDialog (null,\" Escribir  el numero entero: \") );"
            }
            "CADENA" -> {
                variable.getJavaCode() + "= JOptionPane.showInputDialog (null,\" Escribir Cadena: \");"
            }
            "Dc" -> {
                variable.getJavaCode() + "= Double.parseDouble(JOptionPane.showInputDialog (null,\" Escribir: \") );"
            }
            "Asert" -> {
                variable.getJavaCode() + "= Boolean.parseBoolean(JOptionPane.showInputDialog (null,\" Escribir: \") );"
            }
            else -> {
                variable.getJavaCode() + "= JOptionPane.showInputDialog (null,\" Escribir: \");"
            }
        }
    }

}