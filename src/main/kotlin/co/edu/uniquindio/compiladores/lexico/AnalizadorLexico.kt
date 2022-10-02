package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico(var codigoFuente:String) {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int) = listaTokens.add(Token(lexema,categoria,fila,columna))

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
            if (esOperadorAritmetico()) continue


            if(esIdentificadorTipoEntero()) continue
            if(esIdentificadorTipoCadena()) continue
            if(esIdentificadorTipoBooleano()) continue
            if(esIdentificadorTipoDecimal()) continue
            if(esIdentificadorTipoCaracter()) continue
            if(esOperadorIncrementoDecremento()) continue
            if(esOperadorRelacional()) continue
            if(esFindeSentencia()) continue
            if(esSeparador()) continue
            if(esComentarioBloque()) continue
            if(esComentarioLinea()) continue
            if(esDosPuntos()) continue
            if(esLlaveApertura())continue
            if(esLlaveCierre())continue
            if(esParentesisAbierto())continue
            if(esParentesisCerrado())continue

            if(esPalabraReservadaClase())continue

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

    /**
     * OPERADORES
     */
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
        if (caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/') {
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
    fun esDosPuntos(): Boolean {
        var lexema = ""
        if (caracterActual == '"') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.IDENTIFICADOR_DOS_PUNTOS, filaInicial, columnaInicial
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
    /**
     * PALABRAS RESERVADAS
     */

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