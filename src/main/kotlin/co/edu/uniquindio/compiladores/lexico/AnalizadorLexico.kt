package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico(var codigoFuente:String) {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var ListaErrores = ArrayList<Error>();
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int) = listaTokens.add(Token(lexema,categoria,fila,columna))

    fun almacenarReporteErrores(lexema: String,  fila: Int, columna: Int) =
        ListaErrores.add(
            Error(
                lexema,
                fila,
                columna
            )
        )
    fun hacerBT(posicionInicial: Int, filaInicial: Int, columnaInicial: Int) {

        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial
        caracterActual = codigoFuente[posicionActual]
    }

//analizar repite la comprobación de los autómatas
    fun analizar(){
        while (caracterActual!=finCodigo){

            if(caracterActual==' ' || caracterActual == '\t' || caracterActual == '\n'){
                obtenerSiguienteCaracter()
                continue
            }
            if(esDecimal()) continue
            if(esEntero()) continue
            if(esCaracter()) continue
            if(esCadena()) continue
            if(esBoolean()) continue
            if(esOperadorLogico())continue
            if(esOperadorAsignacion()) continue
            if(esOperadorAritmetico()) continue
            if(esOperadorIncrementoDecremento()) continue
            if(esOperadorRelacional()) continue
            if(esIdentificadorTipoEntero()) continue
            if(esIdentificadorTipoCadena()) continue
            if(esIdentificadorTipoBooleano()) continue
            if(esIdentificadorTipoDecimal()) continue
            if(esIdentificadorTipoCaracter()) continue
            if(esFindeSentencia()) continue
            if(esSeparador()) continue
            if(esComentarioBloque()) continue
            if(esComentarioLinea()) continue
            if(esTerminal()) continue
            if(esCaracterEscape()) continue
            if(esPuntoYcoma()) continue
            if(esLlaveApertura())continue
            if(esLlaveCierre())continue
            if(esParentesisAbierto())continue
            if(esParentesisCerrado())continue
            if(esPalabraReservadaClase())continue
            if(esIdentificadorTipoCiclos()) continue
            if(esPalabraReservadaInicioDeClase()) continue
            if(esUnaPalabraReservadaCondicional()) continue
            if(esIdentificadorTipoConstantes()) continue
            if(esPalabraReservadaFinDeClase()) continue
            if(esPalabraReservadaRetorno()) continue
            if(esIdentificadorDeMetodo()) continue
            if(esIdentificadorVariable()) continue
            if(esInicioSentencia()) continue
            if(esFinSentencia()) continue
            if(esPalabraReservadaVacio()) continue
            if(esIdentificadorArreglo()) continue
            if(esIdentificadorLectura()) continue
            if(esPalabraReservadaImpresion()) continue
            if(esPalabraReservadaFuncionMatematica()) continue
            if(esPalabraIdentificadorMatematico()) continue
            if(esPalabraReservadaRomper()) continue
            if(esPalabraReservadaContinuar()) continue

            //ALMACENAR LOS TOKEN

            almacenarToken( ""+caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual )
            obtenerSiguienteCaracter()
        }
    }

    fun esEntero():Boolean{
        var lexema = ""

        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        if (caracterActual.isDigit()){

            lexema+=caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isDigit()){
                lexema+=caracterActual
                obtenerSiguienteCaracter()
            }
                almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaActual)
                return true
        }else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        //RI
            return false
    }

    fun esDecimal(): Boolean {

        if (caracterActual.isDigit()) {

            var lexema = ""

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == ',') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                        if (caracterActual.isDigit()) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            while (caracterActual.isDigit()) {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                            }
                            almacenarToken(
                                lexema,
                                Categoria.DECIMAL, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
        }
        //RI
        return false
    }

    fun esCaracter(): Boolean {
        var lexema = ""
        if (caracterActual == '¿') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '¿') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual != '¿') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual != '¿' && caracterActual != finCodigo) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == '¿') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.CARACTER, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    fun esCadena(): Boolean {
        var lexema = ""
        if (caracterActual == '?') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual != '?' ) {


                while (caracterActual != '?') {

                    if (caracterActual != '?') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                }
                if (caracterActual == '?') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.CADENA, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false

    }


    fun esBoolean(): Boolean {
        var lexema = ""

        if (caracterActual == 'T') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual == 'R') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(
                    lexema,
                    Categoria.BOOLEAN, filaInicial, columnaInicial
                )
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            }else { if (caracterActual == 'F') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'S') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.BOOLEAN, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        }
        //RI
        return false
    }

    fun esOperadorLogico():Boolean{
        var lexema = ""
        if (caracterActual == 'Y' || caracterActual == 'N' || caracterActual == 'O') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            if(caracterActual=='Y'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual=='Y'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                    return true
                }
            }else{
                if(caracterActual=='O'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if(caracterActual=='R'){
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                        return true
                    }
                }else{
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if(caracterActual=='O'){
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if(caracterActual=='T'){
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                            return true
                        }
                    }else{
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        //RI
        return false
    }

    fun esOperadorAritmetico(): Boolean {
        var lexema = ""
        if (caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/'|| caracterActual == '%') {
            System.out.println(caracterActual)
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial
            )
            return true
        }
        return false
    }

    //OPERADORES INCREMENTO,DECREMENTO
    fun esOperadorIncrementoDecremento(): Boolean {
        var lexema = ""
        if (caracterActual == '↑' || caracterActual=='↓'){
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            if (caracterActual == '↑') {

                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(
                    lexema,
                    Categoria.OPERADOR_INCREMENTO, filaInicial, columnaInicial
                )
                return true
            } else if (caracterActual == '↓') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(
                    lexema,
                    Categoria.OPERADOR_DECREMENTO, filaInicial, columnaInicial
                )
                return true
            }
        }
        //RI
        return false
    }

    //Verifica si el token es un Operador_relacional
    fun esOperadorRelacional(): Boolean {
        var lexema = ""
        if (caracterActual == '→' || caracterActual == '←' || caracterActual == '='|| caracterActual == '!'  ) {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            if (caracterActual == '←') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                    )
                    return true
                }
            }else if (caracterActual == '→') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                    )
                    return true
                }
            }else if (caracterActual == '=') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }else if (caracterActual == '!') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }
        //RI
        return false
    }

    //OPERADORES DE ASIGNACION
    fun esOperadorAsignacion(): Boolean {
        var lexema = ""
        if (caracterActual == '=' || caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/'|| caracterActual == '%') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            if (caracterActual == '=') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else  if (caracterActual == '+') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else if (caracterActual == '-') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }else  if (caracterActual == '*') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }else  if (caracterActual == '/') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }else  if (caracterActual == '%') {
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }

        //RI
        return false
    }

    // FIN DE SENTENCIA ;
    fun esFindeSentencia(): Boolean {
        var lexema = ""
        if (caracterActual == '_') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.FINDESENTENCIA, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    // Punto y coma
    fun esPuntoYcoma(): Boolean {
        var lexema = ""
        if (caracterActual == ';') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.IDENTIFICADOR_PUNTO_COMA, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    // SEPARADOR ,
    fun esLlaveApertura(): Boolean {
        var lexema = ""
        if (caracterActual == '{') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.LLAVEAPERTURA, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    fun esLlaveCierre(): Boolean {
        var lexema = ""
        if (caracterActual == '}') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.LLAVECIERRE, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    fun esParentesisAbierto(): Boolean {
        var lexema = ""
        if (caracterActual == '(') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.PARENTESISABIERTO, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    fun esParentesisCerrado(): Boolean {
        var lexema = ""
        if (caracterActual == ')') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.PARENTESISCERRADO, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    fun esSeparador(): Boolean {
        var lexema = ""
        if (caracterActual == '|') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.SEPARADOR, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    //Verifica si el token es un comentario de bloque
    fun esComentarioBloque(): Boolean {
        var lexema = ""
        if (caracterActual == '#') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '#') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                    if (caracterActual != '#') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        while (caracterActual != '#' && caracterActual != finCodigo) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == '#') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.COMENTARIO_BLOQUE, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    fun esComentarioLinea(): Boolean {
        var lexema = ""
        if (caracterActual == '#') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
                if (caracterActual != '#') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual != '#' && caracterActual != finCodigo) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.COMENTARIO_LINEA, filaInicial, columnaInicial
                        )
                        return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
        }
        //RI
        return false
    }

    //Verifica si el token es un termial es ALT + 194
    fun esTerminal(): Boolean {
        var lexema = ""
        if (caracterActual == '┬') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(
                lexema,
                Categoria.TERMINAL, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    fun esCaracterEscape(): Boolean {
        var lexema = ""
        if (caracterActual == '^') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '*' || caracterActual == '?' || caracterActual == ']' || caracterActual == '_') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(
                    lexema,
                    Categoria.CARACTER_ESCAPE, filaInicial, columnaInicial
                )
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    /**
     * IDENTIFICADORES
     */
    fun esIdentificadorTipoEntero(): Boolean {
        var lexema = ""
        if (caracterActual == 'E') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual == 'N') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'T') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'E') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'R') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'O') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(
                                    lexema,
                                    Categoria.IDENTIFICADOR_TIPO_ENTERO, filaInicial, columnaInicial
                                )
                                return true
                            } else {
                                 hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }

                        } else {
                             hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                         hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }

                } else {
                     hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    fun esIdentificadorTipoCadena(): Boolean {
        var lexema = ""
        if (caracterActual == 'C') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual == 'A') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'D') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'E') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'N') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'A') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(
                                    lexema,
                                    Categoria.IDENTIFICADOR_TIPO_CADENA, filaInicial, columnaInicial
                                )
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }

                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }

                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    fun esIdentificadorTipoBooleano(): Boolean {
        var lexema = ""
        if (caracterActual == 'B') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual == 'O') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'L') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'E') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'A') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'N') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(
                                    lexema,
                                    Categoria.IDENTIFICADOR_TIPO_BOOLEANO, filaInicial, columnaInicial
                                )
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }

                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }

                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    fun esIdentificadorTipoDecimal(): Boolean {
        var lexema = ""
        if (caracterActual == 'D') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual == 'E') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'C') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'I') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'M') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'A') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'L') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    almacenarToken(
                                        lexema,
                                        Categoria.IDENTIFICADOR_TIPO_DECIMAL, filaInicial, columnaInicial
                                    )
                                    return true
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }



                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }


                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }

                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    fun esIdentificadorTipoCaracter(): Boolean {
        var lexema = ""
        if (caracterActual == 'C') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual == 'A') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'R') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'A') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'C') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'T') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'E') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    if (caracterActual == 'R') {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()
                                        almacenarToken(
                                            lexema,
                                            Categoria.IDENTIFICADOR_TIPO_CARACTER, filaInicial, columnaInicial
                                        )
                                        return true
                                    } else {
                                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                                        return false
                                    }
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }


                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }

                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }


    //Verifica si el identificador es de tipo constante
    fun esIdentificadorTipoConstantes(): Boolean {
        var lexema = ""
        if (caracterActual == 'V') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'A') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'L') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual.isLetterOrDigit() && caracterActual != finCodigo) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    if (!caracterActual.isLetterOrDigit() || caracterActual != finCodigo) {
                        almacenarToken(
                            lexema,
                            Categoria.IDENTIFICADOR_INMUTABLE, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }

            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    fun esIdentificadorTipoCiclos(): Boolean {
        var lexema = ""
        if (caracterActual == 'r' || caracterActual == 'R') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            if (caracterActual == 'r') {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == 'u') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'n') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.PALABRA_RESERVADA_CICLOS, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {

                if (caracterActual == 'R') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'a') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'm') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.PALABRA_RESERVADA_CICLOS, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }
        //RI
        return false
    }

    //Verifica palabra reservada inicio de clase
    fun esPalabraReservadaInicioDeClase(): Boolean {
        var lexema = ""
        if (caracterActual == 'd') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'r') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'a') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'w') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'e') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'r') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(
                                    lexema,
                                    Categoria.PALABRA_RESERVADA_INICIO_CLASE, filaInicial, columnaInicial
                                )
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Verifica si una palabra reservada condicional
    fun esUnaPalabraReservadaCondicional(): Boolean {
        var lexema = ""
        if (caracterActual == 'S' || caracterActual == 'N') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            if (caracterActual == 'S') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'I') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.PALABRA_RESERVADA_DESCICIONES, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {

                if (caracterActual == 'N') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'O') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.PALABRA_RESERVADA_DESCICIONES, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }
        //RI
        return false
    }

    fun esPalabraReservadaClase(): Boolean {
        var lexema = ""
        if (caracterActual == 'C') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'L') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'A') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'S') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'E') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.PALABRA_RESERVADA_CLASE, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }


                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }

                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }
    //Verifica si es la palabra reservada de fin de clase
    fun esPalabraReservadaFinDeClase(): Boolean {
        var lexema = ""
        if (caracterActual == 'e') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'x') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'i') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 't') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.PALABRA_RESERVADA_FIN_CLASE, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }
    //Verifica si es la palabra reservada de retorno
    fun esPalabraReservadaRetorno(): Boolean {
        var lexema = ""
        if (caracterActual == 'R') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'E') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'T') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'O') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'R') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.PALABRA_RESERVADA_RETORNO, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Verifica si es identificador Método
    fun esIdentificadorDeMetodo(): Boolean {
        var lexema = ""
        if (caracterActual == '¬') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

                        while (isMayuscula(caracterActual)) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        if (caracterActual == '¬') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                                    almacenarToken(
                                        lexema,
                                        Categoria.IDENTIFICADOR_DE_METODOS,
                                        filaInicial,
                                        columnaInicial
                                    )
                                    return true
                } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
        }
        //RI
        return false
    }

    //Verifica si el token es un identificador de variable
    fun esIdentificadorVariable(): Boolean {
        var lexema = ""
        if (caracterActual == 'V') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'A') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'R') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual.isLetterOrDigit() && caracterActual != finCodigo) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    if (!caracterActual.isLetterOrDigit() || caracterActual != finCodigo) {
                        almacenarToken(
                            lexema,
                            Categoria.IDENTIFICADOR_VARIABLE, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }

            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }
    //Indica el caracter que limita por ambos extremos un bloque de agrupacion

    //Indica el caracter que inicia el bloque de sentencia
    fun esInicioSentencia(): Boolean {
        var lexema = ""
        if (caracterActual == '{') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '}') {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            } else {
                almacenarToken(
                    lexema,
                    Categoria.LLAVE_INICIO, filaInicial, columnaInicial
                )
                return true
            }
        }
        return false
    }

    //Indica el caracter que finaliza el bloque de sentencia
    fun esFinSentencia(): Boolean {
        var lexema = ""
        if (caracterActual == '>') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'b') {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            } else {
                almacenarToken(
                    lexema,
                    Categoria.LLAVE_FIN, filaInicial, columnaInicial
                )
                return true
            }
        }
        return false
    }

    //Indica la palabra reservada para el tipo de retorno void
    fun esPalabraReservadaVacio(): Boolean {
        var lexema = ""
        if (caracterActual == 'V') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'A') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'C') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'I') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'O') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.PALABRA_RESERVADA_VACIO, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }

                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Verifica si el token es un identificador de arreglo
    fun esIdentificadorArreglo(): Boolean {
        var lexema = ""
        if (caracterActual == '#') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '>') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'A') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual.isLetterOrDigit() && caracterActual != finCodigo) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    if (!caracterActual.isLetterOrDigit() || caracterActual != finCodigo) {
                        almacenarToken(
                            lexema,
                            Categoria.IDENTIFICADOR_ARREGLO, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Metodos auxiliares
    fun isMayuscula(letra: Char): Boolean {
        if (letra == 'A' || letra == 'B' || letra == 'C' || letra == 'D' ||
            letra == 'E' || letra == 'F' || letra == 'G' || letra == 'H' ||
            letra == 'I' || letra == 'J' || letra == 'K' || letra == 'L' ||
            letra == 'Ñ' || letra == 'N' || letra == 'M' || letra == 'O' ||
            letra == 'P' || letra == 'Q' || letra == 'R' || letra == 'S' ||
            letra == 'T' || letra == 'U' || letra == 'V' || letra == 'W' || letra == 'Z'
        ) {
            return true
        }
        return false
    }

    fun esIdentificadorLectura(): Boolean {
        var lexema = ""
        if (caracterActual == 'L') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'i') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'r') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'e') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'D') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'i') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'a') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    if (caracterActual == 'l') {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()
                                        if (caracterActual == 'o') {
                                            lexema += caracterActual
                                            obtenerSiguienteCaracter()
                                            if (caracterActual == 'g') {
                                                lexema += caracterActual
                                                obtenerSiguienteCaracter()
                                                if (caracterActual == 'u')
                                                    lexema += caracterActual
                                                obtenerSiguienteCaracter()
                                                if (caracterActual == 'e') {
                                                    lexema += caracterActual
                                                    obtenerSiguienteCaracter()
                                                    almacenarToken(
                                                        lexema,
                                                        Categoria.PALABRA_RESERVADA_LEER, filaInicial, columnaInicial
                                                    )
                                                    return true
                                                } else {
                                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                                    return false
                                                }
                                            } else {
                                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                                return false
                                            }
                                        } else {
                                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                                            return false
                                        }

                                    } else {
                                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                                        return false
                                    }
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Verifica si el token es  la palabra reservada para imprimir
    fun esPalabraReservadaImpresion(): Boolean {
        var lexema = ""
        if (caracterActual == 'I') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'M') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'P') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'R') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'I') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'M') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'E') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    almacenarToken(
                                        lexema,
                                        Categoria.PALABRA_RESERVADA_IMPRIMIR, filaInicial, columnaInicial
                                    )
                                    return true
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    fun esPalabraReservadaFuncionMatematica(): Boolean {
        var lexema = ""
        if (caracterActual == 'F') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'n') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '_') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.PALABRA_RESERVADA_FUNCION_MATEMATICA, filaInicial, columnaInicial
                    )
                    return true

                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    fun esPalabraIdentificadorMatematico(): Boolean {
        var lexema = ""
        if (caracterActual == 'A' || caracterActual == 'C' || caracterActual == 'S') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'b' || caracterActual == 'o' || caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 's' || caracterActual == 'i') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'o' || caracterActual == 'i' || caracterActual == 'n') {
                        if (caracterActual == 'n') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.PALABRA_IDENTIFICADOR_MATEMATICO, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'l') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'u') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    almacenarToken(
                                        lexema,
                                        Categoria.PALABRA_IDENTIFICADOR_MATEMATICO, filaInicial, columnaInicial
                                    )
                                    return true
                                }
                            } else {
                                if (caracterActual == 'n') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    if (caracterActual == 'u') {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()
                                        if (caracterActual == 's') {
                                            lexema += caracterActual
                                            obtenerSiguienteCaracter()
                                            almacenarToken(
                                                lexema,
                                                Categoria.PALABRA_IDENTIFICADOR_MATEMATICO, filaInicial, columnaInicial
                                            )
                                            return true
                                        } else {
                                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                                            return false
                                        }
                                    } else {
                                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                                        return false
                                    }
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }
                            }
                        }

                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }


    fun esPalabraReservadaRomper(): Boolean {
        var lexema = ""
        if (caracterActual == 'C') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'a') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 's') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 's') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'e') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'r') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(
                                    lexema,
                                    Categoria.PALABRA_RESERVADA_ROMPER, filaInicial, columnaInicial
                                )
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false

    }


    fun esPalabraReservadaContinuar(): Boolean {
        var lexema = ""
        if (caracterActual == 'R') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 's') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 't') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'e') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'r') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(
                                    lexema,
                                    Categoria.PALABRA_RESERVADA_CONTINUAR, filaInicial, columnaInicial
                                )
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }
    fun obtenerSiguienteCaracter(){

        if(posicionActual==codigoFuente.length-1){
            caracterActual = finCodigo
        } else{
            if(caracterActual == '\n'){
                filaActual++
                columnaActual = 0
            }else{
                columnaActual++
            }

            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }

    }
}