package co.edu.uniquindio.compiladores.prueba

fun main(){
    val lexico = AnalizadorLexico("= == ! !="  )
    lexico.analizar()
    println(lexico.listaTokens)
}