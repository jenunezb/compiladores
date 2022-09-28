package co.edu.uniquindio.compiladores.prueba

fun main(){
    val lexico = AnalizadorLexico("# Hola MUNDO # ##Hola MUNDO##"  )
    lexico.analizar()
    println(lexico.listaTokens)
}