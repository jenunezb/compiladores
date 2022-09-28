package co.edu.uniquindio.compiladores.prueba

fun main(){
    val lexico = AnalizadorLexico(": (a+8/-15 n"   )
    lexico.analizar()
    println(lexico.listaTokens)
}