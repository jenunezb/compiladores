package co.edu.uniquindio.compiladores.prueba

class AnalizadorLexico (var codigoFuente: String) {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>();
    var finCodigo = 0.toChar()// representación de null en un char
    var filaActual = 0
    var columnaActual = 0

    fun esEntero():Boolean{

        if (caracterActual.isDigit()){

            var lexema=""
            var filaInicial=filaActual
            var columnaInicial=columnaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while ( caracterActual.isDigit()){
                lexema+=caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.ENTERO, filaInicial,columnaInicial)
            return true
        }
         return false
    }

    //Para agregar cada co.edu.uniquindio.compiladores.prueba.Token ya analizado se realiza esta función
    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) =
        listaTokens.add(
            Token(
                lexema,
                categoria,
                fila,
                columna
            )
        )
// en obtenerSiguienteCaracter verificamos en un inicio si el caracter actual es el último para hacer saber que
//es el fin del código, de lo contrario aumento el caracter actual para seguir analizando el sgte caracter
    fun obtenerSiguienteCaracter() {
        if (posicionActual == codigoFuente.length - 1) {
            caracterActual = finCodigo
        }else{
            if (caracterActual == '\n') {
                filaActual++
                columnaActual = 0
            } else {
                columnaActual++
            }
        }
           posicionActual++
            caracterActual=codigoFuente[posicionActual]

    }
}