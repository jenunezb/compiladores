package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Impresion() : Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem<String>("Imprime")
    }
}