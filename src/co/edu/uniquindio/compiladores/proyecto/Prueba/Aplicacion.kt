package co.edu.uniquindio.compiladores.proyecto.Prueba

import co.edu.uniquindio.compiladores.proyecto.Lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.Sintaxis.AnalizadorSintactico

fun main() {
    val lexico = AnalizadorLexico(
        codigoFuente = "<<*SUMAR*>> :  : Dc <  \n" +
                "\n" +
                "#>V4 S++∞ n5 $\n" +
                "\n" +
                ">"
    )
    lexico.analizar()
    val sintaxis = AnalizadorSintactico(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print(sintaxis.listaErrores)
}
