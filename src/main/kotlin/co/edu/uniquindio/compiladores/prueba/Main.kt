package co.edu.uniquindio.compiladores.prueba

fun main(){
    val lexico = AnalizadorLexico("## Hola Perras ##"  )
    lexico.analizar()
    println(lexico.listaTokens)
}