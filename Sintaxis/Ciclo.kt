package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import javafx.scene.control.TreeItem

open class Ciclo():Sentencia() {

  override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Ciclo")
    }
}