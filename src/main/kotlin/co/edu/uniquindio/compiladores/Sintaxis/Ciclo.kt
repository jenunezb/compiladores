package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import javafx.scene.control.TreeItem

open class Ciclo():Sentencia() {

  override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Ciclo")
    }

}