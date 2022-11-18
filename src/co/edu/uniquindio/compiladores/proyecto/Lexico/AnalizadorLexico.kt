package co.edu.uniquindio.compiladores.proyecto.Lexico

class AnalizadorLexico(var codigoFuente: String) {

    var caracterActual = codigoFuente[0]

    var posicionActual = 0
    var listaTokens = ArrayList<Token>();
    var listaReportesErrores = ArrayList<Token>();
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0


    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) =
        listaTokens.add(
            Token(
                lexema,
                categoria,
                fila,
                columna
            )
        )

    fun almacenarReporteErrores(lexema: String, categoria: Categoria, fila: Int, columna: Int) =
        listaReportesErrores.add(
            Token(
                lexema,
                categoria,
                fila,
                columna
            )
        )

    fun analizar() {
        while (caracterActual != finCodigo) {

            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSiguienteCaracter()
                continue
            }

            if (esEntero()) continue
            if (esDecimal()) continue
            if (esCadena()) continue
            if (esCaracter()) continue
            if (esBoolean()) continue
            if (esOperadorAritmetico()) continue
            if (esOperadorIncrementoDecremento()) continue
            if (esOperadorRelacional()) continue
            if (esOperadorLogico()) continue
            if (esOperadorAsignacion()) continue
            if (esSeparador()) continue
            if (esComentarioBloque()) continue
            if (esComentarioLinea()) continue
            if (esTerminal()) continue
            if (esCaracterEscape()) continue
            if (esIdentificadorTipoEntero()) continue
            if (esPalabraReservadaClase()) continue
            if (esIdentificadorTipoBooleano()) continue
            if (esIdentificadroTipoDecimal()) continue
            if (esIdentificadorTipoCadena()) continue
            if (esIdentificadorTipoCaracter()) continue
            if (esIdentificadorTipoConstantes()) continue
            if (esIdentificadorTipoCiclos()) continue
            if (esPalabraReservadaInicioDeClase()) continue
            if (esUnaPalabraReservadaCondicional()) continue
            if (esPalabraReservadaFinDeClase()) continue
            if (esPalabraReservadaRetorno()) continue
            if (esIdentificadorDeMetodo()) continue
            if (esIdentificadorVariable()) continue
            if (esIdentificadorArreglo()) continue
            if (esInicioSentencia()) continue
            if (esFinSentencia()) continue
            if (esAgrupador()) continue
            if (esIdentificadoPunto()) continue
            if (esIdentificadorDosPuntos()) continue
            if(esLlaveApertura())continue
            if(esLlaveCierre())continue
            if(esParentesisAbierto())continue
            if(esParentesisCerrado())continue
            if (esPalabraReservadaVacio()) continue
            if (esIdentificadorLectura()) continue
            if (esPalabraReservadaImpresion()) continue
            if (esPalabraReservadaFuncionMatematica()) continue
            if (esPalabraIdentificadorMatematico()) continue
            if (esPalabraReservadaRomper()) continue
            if (esPalabraReservadaContinuar()) continue
            if (esConcatenar()) continue



            almacenarToken(
                "" + caracterActual,
                Categoria.NO_RECONOCIDO, filaActual, columnaActual
            )
            obtenerSiguienteCaracter()

        }

    }

    fun hacerBT(posicionInicial: Int, filaInicial: Int, columnaInicial: Int) {

        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial
        caracterActual = codigoFuente[posicionActual]
    }


    /**
     * DE AQUI EN ADELANTE---TIPOS DE DATOS
     */
    //Verifica si el token es un entero
    fun esEntero(): Boolean {
        var lexema = ""
        if (caracterActual.isDigit()) {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                almacenarToken(
                    lexema,
                    Categoria.ENTERO, filaInicial, columnaInicial
                )
                return true
        }
        //RI
        return false

    }


    //Verifica si el token es un numero decimal
    fun esDecimal(): Boolean {
        var lexema = ""
        if (caracterActual == 'N') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual.isDigit() || caracterActual == 'b') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == ',') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'd') {
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

    //Verifica si el token es una cadena
    fun esCadena(): Boolean {
        var lexema = ""
        if (caracterActual == '#') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += '"'
            obtenerSiguienteCaracter()

            if (caracterActual != '#' || caracterActual == '^') {
                while (caracterActual != '#' || caracterActual == '^') {

                    if (caracterActual != '#') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    } else {
                        if (caracterActual == '^') {
                            obtenerSiguienteCaracter()
                            if (caracterActual == '*' || caracterActual == '?' || caracterActual == ']' || caracterActual == '_') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }
                }
                if (caracterActual == '#') {
                    lexema += '"'
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

    //Verifica si el token es un caracter
    fun esCaracter(): Boolean {
        var lexema = ""
        if (caracterActual == '/') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual != '/') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '/') {
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
        }
        //RI
        return false
    }

    //Verifica si el token es un dato booleano
    fun esBoolean(): Boolean {
        var lexema = ""
        if (caracterActual == 'O') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'n') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(
                    lexema,
                    Categoria.BOOLEAN, filaInicial, columnaInicial
                )
                return true
            } else {
                if (caracterActual == 'f') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'f') {
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
     * DE AQUI EN ADELANTE---OPERADORES
     */

    //OPERADORES ARITMETICOS
    fun esConcatenar(): Boolean {
        var lexema = ""
        if (caracterActual == '↔') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.CONCATENAR, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }
    fun esOperadorAritmetico(): Boolean {
        var lexema = ""
        if (caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/'|| caracterActual == '%') {
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
        if (caracterActual == '[') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '+' || caracterActual == '-') {

                lexema += caracterActual
                if (caracterActual == '+') {
                    obtenerSiguienteCaracter()
                    if (caracterActual == ']') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.OPERADOR_INCREMENTO, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else if (caracterActual == '-') {
                    obtenerSiguienteCaracter()
                    if (caracterActual == ']') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.OPERADOR_DECREMENTO, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Verifica si el token es un Operador_relacional
    fun esOperadorRelacional(): Boolean {
        var lexema = ""
        if (caracterActual == '=' || caracterActual == '>' || caracterActual == '<' || caracterActual == '!') {

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
                        Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                if (caracterActual == '>' || caracterActual == '<') {
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
                        if (caracterActual == '>') {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        } else {
                            almacenarToken(
                                lexema,
                                Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                            )
                            return true
                        }
                    }
                } else {
                    if (caracterActual == '!') {
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

                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        //RI
        return false
    }

    //Verifica si el token es un Operador_Logico
    fun esOperadorLogico(): Boolean {
        var lexema = ""
        if (caracterActual == 'Y' || caracterActual == 'O' || caracterActual == '|') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            if (caracterActual == 'O' || caracterActual == '|') {
                obtenerSiguienteCaracter()
                if (caracterActual != '∞') {
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {

                obtenerSiguienteCaracter()
                if (caracterActual == 'Y') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial
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
        if (caracterActual == 'S' || caracterActual == 'R' || caracterActual == 'm' || caracterActual == 'd' || caracterActual == '→') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            if (caracterActual == '→') {
                obtenerSiguienteCaracter()
                if (caracterActual != '→') {
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

            obtenerSiguienteCaracter()

            if (caracterActual == '+' || caracterActual == '-' || caracterActual == '#' || caracterActual == '/' || caracterActual == 'o') {
                lexema += caracterActual

                if (caracterActual == '#' || caracterActual == 'o') {

                    if (caracterActual == '#') {
                        obtenerSiguienteCaracter()
                        if (caracterActual == '∞') {
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
                    } else {
                        obtenerSiguienteCaracter()
                        lexema += caracterActual
                        if (caracterActual == 'd') {
                            obtenerSiguienteCaracter()
                            lexema += caracterActual
                            if (caracterActual == '%') {
                                obtenerSiguienteCaracter()
                                if (caracterActual == '∞') {
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
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }
                } else {
                    obtenerSiguienteCaracter()
                    if (caracterActual == '+' || caracterActual == '-' || caracterActual == '/') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == '∞') {
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
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }


    //Verifica si el token es un separador
    fun esSeparador(): Boolean {
        var lexema = ""
        if (caracterActual == ',') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.SEPARADORES, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    //Verifica si el token es un termianl
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

    //Verifica si el token es un termianl
    fun esIdentificadoPunto(): Boolean {
        var lexema = ""
        if (caracterActual == '.') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.IDENTIFICADOR_PUNTO, filaInicial, columnaInicial
            )
            return true
        }
        //RI
        return false
    }

    //Verifica si el token es un termianl
    fun esIdentificadorDosPuntos(): Boolean {
        var lexema = ""
        if (caracterActual == ';') {

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
    //Verifica si el token es un comentario de bloque
    fun esComentarioBloque(): Boolean {
        var lexema = ""
        if (caracterActual == '.') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '.') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '.') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual != '.') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        while (caracterActual != '.' && caracterActual != finCodigo) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == '.') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == '.') {
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

    //Verifica si el token es un comentario de linea
    fun esComentarioLinea(): Boolean {
        var lexema = ""
        if (caracterActual == '-') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '>') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual != 'V') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual != '.') {
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
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Verifica si el token es un caracter de escape
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
    //Metodo que identifica el tipo de dato entero

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

    //Verifica si es un identificador de clase
    fun esPalabraReservadaClase(): Boolean {
        var lexema = ""
        if (caracterActual == 'r') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'o') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'm') {
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
        }
        //RI
        return false
    }

    //Metodo que identifica el tipo de dato booleano

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

    //Verifica si es un identificador de tipo Decimal
    fun esIdentificadroTipoDecimal(): Boolean {
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


    //Metodo que identifica el tipo de dato Cadena

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
            }} else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }} else {
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

    //Verifica si el identificador es de un Caracter
    fun esIdentificadorTipoCaracter(): Boolean {
        var lexema = ""
        if (caracterActual == 'l') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 't') {
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
        }
        //RI
        return false
    }

    //Verifica si el identificador es de tipo constante
    fun esIdentificadorTipoConstantes(): Boolean {
        var lexema = ""
        if (caracterActual == ':') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'C') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'O') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'N') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'S') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.IDENTIFICADOR_VARIABLE_CONSTANTE,
                                filaInicial,
                                columnaInicial
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

    //Verifica si el token es un caracter de ciclo
    fun esIdentificadorTipoCiclos(): Boolean {
        var lexema = ""
        if (caracterActual == 'M' || caracterActual == 'I') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            if (caracterActual == 'I') {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == 'T') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'R') {
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

                if (caracterActual == 'M') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'I') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'E') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'N') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'T') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    if (caracterActual == 'R') {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()
                                        if (caracterActual == 'A') {
                                            lexema += caracterActual
                                            obtenerSiguienteCaracter()
                                            if (caracterActual == 'S') {
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
        if (caracterActual == 'Y' || caracterActual == 'N') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            if (caracterActual == 'Y') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'e') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 's') {
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
            } else {

                if (caracterActual == 'N') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'o') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 't') {
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
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
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
                            if (caracterActual == 'N') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                        if (caracterActual == 'O') {
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

    //Verifica si el token es una cadena
    fun esIdentificadorDeMetodo(): Boolean {
        var lexema = ""
        if (caracterActual == '¬') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

                    if (isMayuscula(caracterActual)) {
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

    //Indica el caracter que limita por ambos extremos un boque de agrupacion
    fun esAgrupador(): Boolean {
        var lexema = ""
        if (caracterActual == ':') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'C') {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            } else {
                almacenarToken(
                    lexema,
                    Categoria.AGRUPADOR, filaInicial, columnaInicial
                )
                return true
            }
        }
        return false
    }

    //Indica el caracter que inicia el bloque de sentencia
    fun esInicioSentencia(): Boolean {
        var lexema = ""
        if (caracterActual == '{') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '>') {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            } else {
                almacenarToken(
                    lexema,
                    Categoria.INICIO_SENTENCIA, filaInicial, columnaInicial
                )
                return true
            }
        }
        return false
    }

    //Indica el caracter que finaliza el bloque de sentencia
    fun esFinSentencia(): Boolean {
        var lexema = ""
        if (caracterActual == '}') {
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
                    Categoria.FIN_SENTENCIA, filaInicial, columnaInicial
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
        if (caracterActual == 'A') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'R') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'R') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'E') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'G') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'L') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                if (caracterActual == 'O') {
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

    //El unico metodo que aumenta la posicion, actualiza el caracter actual y verifica que no haya desbordamiento.
    fun obtenerSiguienteCaracter() {
        if (posicionActual == codigoFuente.length - 1) {
            caracterActual = finCodigo
        } else {
            if (caracterActual == '\n') {
                filaActual++
                columnaActual = 0
            } else {
                columnaActual++
            }
            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
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

            if (caracterActual == 'E') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'E') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'R')
                        lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken( lexema, Categoria.PALABRA_RESERVADA_LEER, filaInicial, columnaInicial)
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
}