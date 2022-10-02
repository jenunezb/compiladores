package co.edu.uniquindio.compiladores

import co.edu.uniquindio.compiladores.lexico.Categoria

class Token (var lexema: String, var categoria: Categoria, var fila: Int, var columna: Int) {
    override fun toString(): String {
        return "co.edu.uniquindio.compiladores.prueba.Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }
}