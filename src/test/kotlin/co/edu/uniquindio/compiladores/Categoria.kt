package co.edu.uniquindio.compiladores

enum class Categoria {
    ENTERO, DECIMAL, CADENA, CARACTER, OPERADOR_ARITMETICO, OPERADOR_INCREMENTO, OPERADOR_DECREMENTO,
    OPERADOR_ASIGNACION, OPERADOR_RELACIONAL, OPERADOR_LOGICO, NO_RECONOCIDO,
    BOOLEAN, IDENTIFICADOR_TIPO_ENTERO, IDENTIFICADOR_TIPO_DECIMAL, IDENTIFICADOR_TIPO_CARACTER,
    IDENTIFICADOR_TIPO_CADENA, PALABRA_RESERVADA_CLASE,PALABRA_RESERVADA_VACIO, PALABRA_RESERVADA_RETORNO, IDENTIFICADOR_VARIABLE,
    IDENTIFICADOR_VARIABLE_CONSTANTE, IDENTIFICADOR_TIPO_BOOLEANO, PALABRA_RESERVADA_CICLOS,
    PALABRA_RESERVADA_DESCICIONES, COMENTARIO_BLOQUE, COMENTARIO_LINEA,
    TERMINAL, SEPARADORES, CARACTER_ESCAPE, IDENTIFICADOR_DE_METODOS, IDENTIFICADOR_PUNTO, IDENTIFICADOR_DOS_PUNTOS,
    PALABRA_RESERVADA_INICIO_CLASE, PALABRA_RESERVADA_FIN_CLASE, AGRUPADOR, INICIO_SENTENCIA, FIN_SENTENCIA,IDENTIFICADOR_ARREGLO,PALABRA_RESERVADA_LEER,PALABRA_RESERVADA_IMPRIMIR,
    PALABRA_RESERVADA_FUNCION_MATEMATICA,PALABRA_IDENTIFICADOR_MATEMATICO,PALABRA_RESERVADA_ROMPER,PALABRA_RESERVADA_CONTINUAR,FIN_CODIGO
}