 import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
 import co.edu.uniquindio.compiladores.Sintaxis.AnalizadorSintactico

 fun main(){
     val lexico = AnalizadorLexico(

         codigoFuente = "SI VARa → VARb"
     )
     lexico.analizar()
     val sintaxis = AnalizadorSintactico(lexico.listaTokens)
     println(sintaxis.esDecision())
     print(sintaxis.listaErrores)
 }