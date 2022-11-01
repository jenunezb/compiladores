 import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
 import co.edu.uniquindio.compiladores.Sintaxis.AnalizadorSintactico

 fun main(){
     val lexico = AnalizadorLexico(
         codigoFuente = "¬METODO¬ ENTERO (){ ENTERO VARholarey }"
     )
     lexico.analizar()
     val sintaxis = AnalizadorSintactico(lexico.listaTokens)
     print(sintaxis.esUnidadDeCompilacion())
     print(sintaxis.listaErrores)
 }