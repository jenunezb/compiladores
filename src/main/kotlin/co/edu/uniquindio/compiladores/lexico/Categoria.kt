package co.edu.uniquindio.compiladores.lexico

    enum class Categoria {
        ENTERO, DESCONOCIDO, DECIMAL, CADENA, CARACTER, OPERADOR_ARITMETICO, OPERADOR_INCREMENTO, OPERADOR_DECREMENTO,IDENTIFICADOR_DOS_PUNTOS,
        OPERADOR_ASIGNACION, OPERADOR_RELACIONAL, OPERADOR_LOGICO, FINDESENTENCIA,
        BOOLEAN, IDENTIFICADOR_TIPO_ENTERO, IDENTIFICADOR_TIPO_DECIMAL, IDENTIFICADOR_TIPO_CARACTER,
        IDENTIFICADOR_TIPO_CADENA, PALABRA_RESERVADA_CLASE, IDENTIFICADOR_TIPO_BOOLEANO, COMENTARIO_BLOQUE, COMENTARIO_LINEA, SEPARADOR,
        LLAVEAPERTURA,LLAVECIERRE,PARENTESISABIERTO,PARENTESISCERRADO
    }