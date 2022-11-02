 import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
 import co.edu.uniquindio.compiladores.Sintaxis.AnalizadorSintactico

 fun main(){
     val lexico = AnalizadorLexico(

         codigoFuente = "¬METODO¬ DECIMAL (DECIMAL VARh | ENTERO VARi ) {\n" +
                 "\n" +
                 "DECIMAL VALh}"
     )
     lexico.analizar()
     val sintaxis = AnalizadorSintactico(lexico.listaTokens)
     println(sintaxis.esUnidadDeCompilacion())
     print(sintaxis.listaErrores)
 }