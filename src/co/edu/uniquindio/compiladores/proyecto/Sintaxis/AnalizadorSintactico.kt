package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import kotlin.collections.ArrayList

class AnalizadorSintactico(var listaTokens: ArrayList<Token>) {

    var i = 0
    var posicionActual = 0
    var tokenActual = listaTokens[posicionActual]
    var listaErrores = ArrayList<Error>()

    fun obtenerSiguienteToken() {
        posicionActual++;
        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        } else {
            tokenActual = Token("", Categoria.FIN_CODIGO, tokenActual.fila, tokenActual.columna)
        }
    }

    fun reportarError(mensaje: String) {
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))
    }


    fun hacerBT(posicionInicial: Int) {

        posicionActual = posicionInicial
        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        }
    }


    /**
     * <UnidadDeCompilacio>::=<listaFunciones>
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val listaFunciones: ArrayList<Funcion> = esListaFunciones()
        return if (listaFunciones.size > 0) {
            UnidadDeCompilacion(listaFunciones)
        } else null
    }


    /**\
     * <listafunciones.::=<Funcion>[<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion> {

        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()

        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Funcion>::=IdentificadorMetodo":"[<listaParametros>]":"<TipoRetorno><BloqueSentencias>
     */
    fun esFuncion(): Funcion? {

        if (tokenActual.categoria == Categoria.IDENTIFICADOR_DE_METODOS) {
            var nombreFuncion = tokenActual
            obtenerSiguienteToken()
            var tipoRetorno = esTipoRetorno()
            if (tipoRetorno != null) {
                obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESISABIERTO) {
                obtenerSiguienteToken()
                var listaParametros = esListaParametros()
                if (tokenActual.categoria == Categoria.PARENTESISCERRADO) {
                    obtenerSiguienteToken()

                        var bloqueSentencia = esBloqueSentencias()
                        if (bloqueSentencia != null) {
                            //La funcion esta bien escrita

                            return Funcion(
                                nombreFuncion,
                                tipoRetorno,
                                listaParametros,
                                bloqueSentencia,
                                tokenActual.fila,
                                tokenActual.columna
                            )

                        } else {
                            reportarError(mensaje = "El bloque de sentencias esta vacio")
                        }
                    } else {
                        reportarError(mensaje = "Falta el agrupador")
                    }
                } else {
                    reportarError(mensaje = "Falta el agrupador")
                }

            } else {
                reportarError(mensaje = "El tipo de retorno es obligatorio")
            }
        }

        return null
    }


    /**
     * <TipoRetorno>::= Ent|Dc|txt|lett|Asert|Vide
     */
    fun esTipoRetorno(): Token? {

        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_ENTERO) {
            return tokenActual
        }
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_DECIMAL) {
            return tokenActual
        }
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_CADENA) {
            return tokenActual
        }
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_CARACTER) {
            return tokenActual
        }
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_BOOLEANO) {
            return tokenActual
        }
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_VACIO) {
            return tokenActual
        }
        return null


    }

    /**
     * <listaParametros>::=<parametro>["/"<listaParametros>]|<listaParametrosErroneos>
     *
     */
    fun esListaParametros(): ArrayList<Parametro> {
        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()

        if (parametro != null) {
            while (parametro != null) {
                listaParametros.add(parametro)
                if (tokenActual.categoria == Categoria.SEPARADORES) {
                    obtenerSiguienteToken()
                    parametro = esParametro()
                } else {
                    if (tokenActual.categoria != Categoria.PARENTESISCERRADO) {
                        reportarError(mensaje = "Falta un separador en la lista de parametros")
                    }
                    break
                }
            }
        } else {
            var msj = esListaParametrosErroneos()
            if (msj != null) {
                reportarError(msj)
            }
        }
        return listaParametros
    }

    /**
     * <listaParametrosErroneos>::=<listaIdentificadores>
     *
     */
    fun esListaParametrosErroneos(): String? {
        var posicionInicial = posicionActual
        val lista = esListaIdentificadoresVariables()
        if (lista != null) {
            return "Falta los tipos de datos"
        } else {
            return null
        }
    }


    /**
     * <Parametro>::=<TipoDato>IdentificadorVariable
     */
    fun esParametro(): Parametro? {
        var tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                var nombreParametro = tokenActual
                //El parametro esta bien escrito
                obtenerSiguienteToken()
                return Parametro(nombreParametro, tipoDato)
            } else {
                reportarError(mensaje = "El identificador de variable no corresponde a un identificador valido")
            }
        }
        return null
    }

    /**
     * <TipoDato>::=Ent|Dc|txt|lett|Asert
     */
    fun esTipoDato(): Token? {

        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_ENTERO) {
            return tokenActual
        }
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_DECIMAL) {
            return tokenActual
        }
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_CADENA) {
            return tokenActual
        }
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_CARACTER) {
            return tokenActual
        }
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_TIPO_BOOLEANO) {
            return tokenActual
        }
        return null
    }

    /**
     * <BloqueSentencia>::="{"[<listaSentencias>]"}"
     */
    fun esBloqueSentencias(): ArrayList<Sentencia>? {

        if (tokenActual.categoria == Categoria.INICIO_SENTENCIA) {

            obtenerSiguienteToken()

            var listaSentencias = esListaSentencias()

            if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                obtenerSiguienteToken()
                return listaSentencias
            } else {
                reportarError(mensaje = "El fin de sentencia es obligatorio")
            }
        } else {
            reportarError(mensaje = "El inicio de sentencia es obligatorio")
        }
        return null
    }

    /**
     * <listaSentencias>::=<Sentencia>[<listaSentencia>]
     */
    fun esListaSentencias(): ArrayList<Sentencia> {


        var listaSentencias = ArrayList<Sentencia>()
        var sentencia = esSentencia()
        while (sentencia != null) {
            listaSentencias.add(sentencia)
            sentencia = esSentencia()
        }
        return listaSentencias
    }

    /**
     * <Sentencia>::=<Decision>|<Ciclo>|<Impresion>|<Lectura>|<Asignacion>|<DeclaracionVariable>|<Retorno>|<InvocacionFuncion>|<Arreglo>|<Incremento>
     *               |<FuncionMatematica>|<SentenciaRuptura>
     */
    fun esSentencia(): Sentencia? {


        var sentencia: Sentencia? = esDecision()
        if (sentencia != null) {
            return sentencia
        }
        sentencia = esCiclo()
        if (sentencia != null) {
            return sentencia
        }

        sentencia = esImpresion()
        if (sentencia != null) {
            return sentencia
        }
        sentencia = esLectura()
        if (sentencia != null) {
            return sentencia
        }
        sentencia = esAsignacion()
        if (sentencia != null) {
            return sentencia
        }
        sentencia = esDeclaracionVariable()
        if (sentencia != null) {
            return sentencia
        }
        sentencia = esRetorno()
        if (sentencia != null) {
            return sentencia
        }
        sentencia = esInvocacionFuncion()
        if (sentencia != null) {
            return sentencia
        }
        sentencia = esArreglo()
        if (sentencia != null) {
            return sentencia
        }
        sentencia = esIncrementoDecremento()
        if (sentencia != null) {
            return sentencia
        }

        sentencia = esFuncionMatematica()
        if (sentencia != null) {
            return sentencia
        }

        sentencia = esSentenciaRuptura()
        if (sentencia != null) {
            return sentencia
        }


        return null
    }

    /**
     * <Decision>::=Yes<ExpresionLogica><BloqueSentencia>[Not<BloqueSentencia>]
     */
    fun esDecision(): Decision? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DESCICIONES && (tokenActual.lexema == "Yes")) {
            obtenerSiguienteToken()
            val expresion = esExpresionLogica()
            if (expresion == null) {
                reportarError(mensaje = "Hay un error en la condición")
                while (!(tokenActual.categoria == Categoria.INICIO_SENTENCIA) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }
            val bloqueYes = esBloqueSentencias()

            if (bloqueYes != null) {
                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DESCICIONES && (tokenActual.lexema == "Not")) {
                    obtenerSiguienteToken()
                    val bloqueNot = esBloqueSentencias()

                    if (bloqueNot != null) {

                        return Decision(expresion, bloqueYes, bloqueNot)
                    } else {
                        reportarError(mensaje = "Falta bloque de sentencias Falsas")
                    }
                } else {

                    return Decision(expresion, bloqueYes, null)
                }
            } else {
                reportarError(mensaje = "El bloque de sentencias verdaderas es obligatorio")
            }

        }
        return null
    }

    /**
     * <Ciclo>::=<cicloRam>|<cicloRun>
     */
    fun esCiclo(): Ciclo? {

        var ciclo: Ciclo? = esCicloWhile()
        if (ciclo != null) {
            return ciclo
        }
        ciclo = esCicloFor()
        if (ciclo != null) {
            return ciclo
        }

        return null
    }

    /**
     * <CicloRam>::=Ram<ExpresionLogica><BloqueSentencia>
     */
    fun esCicloWhile(): Ciclo? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_CICLOS && tokenActual.lexema == "MIENTRAS") {
            val tipoCiclo = tokenActual.lexema
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.PARENTESISABIERTO){
                obtenerSiguienteToken()
                val expresion = esExpresionLogica()
                if (expresion == null) {
                    reportarError(mensaje = "Hay un error en la condición")
                    while (!(tokenActual.categoria == Categoria.PARENTESISABIERTO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                        obtenerSiguienteToken()
                    }
                }

                if(tokenActual.categoria==Categoria.PARENTESISCERRADO){
                    obtenerSiguienteToken()
                    val bloque = esBloqueSentencias()
                    if (bloque != null) {
                        return CicloWhile(expresion, bloque)
                    }
                }else{
                    reportarError(mensaje = "Falta el parentesis cerrado")
                }

            }else{
                reportarError(mensaje = "Falta el parentesis abierto")
            }
        }
        return null
    }

    /**
     * <CicloRum>::=run<varibaleMutable><ExpresionLogica><IncrementoDecremento><BloqueSentencia>
     */
    fun esCicloFor(): Ciclo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_CICLOS && tokenActual.lexema == "ITR") {
            obtenerSiguienteToken()
            val tipoCiclo = tokenActual.lexema
            if (tokenActual.categoria == Categoria.PARENTESISABIERTO) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "No se encontro el agrupador")
            }
            val declaracionVar = esDeclaracionVariable()
            if (declaracionVar is VariableMutable && declaracionVar != null) {
                val expresion = esExpresionLogica()
                if (expresion == null) {
                    reportarError(mensaje = "Hay un error en la condición")
                    while (!(tokenActual.categoria == Categoria.OPERADOR_INCREMENTO || tokenActual.categoria == Categoria.OPERADOR_DECREMENTO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                        obtenerSiguienteToken()
                    }
                }
                val incrementoDecremento = esIncrementoDecremento()
                if (incrementoDecremento != null) {
                    if (tokenActual.categoria == Categoria.PARENTESISCERRADO) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError(mensaje = "Falta el agrupador final")
                    }
                    val bloque = esBloqueSentencias()
                    if (bloque != null) {
                        return CicloFor(declaracionVar!!, expresion, incrementoDecremento, bloque)
                    }

                } else {
                    reportarError(mensaje = "Hay un error en el incrmento o decremento")
                }

            } else {
                reportarError(mensaje = "Declaración invalida del la variable")
            }

        }

        return null
    }


    /**
     * <Impresion>::=PalabraReservadaImprimir ":"<ListaAgumentos":"
     */
    fun esImpresion(): Impresion? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_IMPRIMIR) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESISABIERTO) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta el agrupador inicial")
            }

            val expresion = esExpresion()

            if (tokenActual.categoria == Categoria.PARENTESISCERRADO) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta el agrupador Final")
            }
            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta simbolo terminal")
            }
            return Impresion(expresion)


        }
        return null
    }

    /**
     * <Impresion>::=PalabraReservadaLeer":"<ExpresionCadena>":"
     */
    fun esLectura(): Lectura? {

        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            var variable = tokenActual
            obtenerSiguienteToken()
            if ((tokenActual.categoria == Categoria.OPERADOR_ASIGNACION) && tokenActual.lexema == "→") {
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_LEER) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.PARENTESISABIERTO) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError(mensaje = "Falta el agrupador inicial")
                    }
                    val expCadena = esExpresionCadena()
                    if (expCadena == null) {
                        reportarError(mensaje = "Hay un error en la condición")
                        while (!(tokenActual.categoria == Categoria.PARENTESISCERRADO) && tokenActual.categoria != Categoria.LLAVECIERRE) {
                            obtenerSiguienteToken()
                        }
                    }
                    if (tokenActual.categoria == Categoria.PARENTESISCERRADO) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError(mensaje = "Falta el agrupador final")
                    }
                    if (tokenActual.categoria == Categoria.TERMINAL) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError(mensaje = "Falta el terminal")
                    }
                    return Lectura(variable, expCadena)

                } else {
                    hacerBT(posicionInicial)
                    return null
                }
            } else {
                hacerBT(posicionInicial)
                return null
            }


        }

        return null
    }

    /**
     * <Asignacion>::=IdentificadorVariable OperadorAsignacion<Expresion>"$"|IdentificadorVariable OperadorAsignacion<InvocacionFuncion>
     */

    fun esAsignacion(): Asignacion? {
        var posicionInicial = posicionActual
        var opAsig: Token? = null
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION) {
                opAsig = tokenActual
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta operador de asignación")
            }
            val invocacion = esInvocacionFuncion()
            if (invocacion != null) {
                return Asignacion(identificador, opAsig, null, invocacion)
            } else {
                val expresion = esExpresion()
                if (expresion != null) {
                    if (tokenActual.categoria == Categoria.TERMINAL) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError(mensaje = "Falta el terminal de la sentencia")
                    }
                    return Asignacion(identificador, opAsig, expresion, null)
                } else {
                    hacerBT(posicionInicial)
                    return null
                }


            }

        }
        return null
    }


    /**
     * <DeclaracionVariable>::=<VariableMutable>|<VariableInmutable>
     */
    fun esDeclaracionVariable(): DeclaracionVariable? {
        var posicionInicial = posicionActual
        var tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            var declaVar: DeclaracionVariable? = esVariableInmutable(tipoDato)
            if (declaVar != null) {
                return declaVar
            } else {
                declaVar = esVariableMutable(tipoDato)
                if (declaVar != null) {
                    return declaVar
                } else {
                    hacerBT(posicionInicial)
                    return null
                }
            }
        }
        return null
    }

    /**
     * <VariableInmutable>::=<tipoDato>:CONS<listaIdentificadores>
     */
    fun esVariableInmutable(tipoDato: Token): DeclaracionVariable? {

        if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE_CONSTANTE) {
            obtenerSiguienteToken()
            val listaIdentificadores = esListaIdentificadoresVariables()
            if (listaIdentificadores != null) {

                if (tokenActual.categoria == Categoria.TERMINAL) {
                    obtenerSiguienteToken()
                } else {
                    reportarError(mensaje = "Falta el simbolo terminal")
                }
                return VariableInmutable(tipoDato, listaIdentificadores)
            } else {
                reportarError(mensaje = "Especificar como minino un identificador de variable")
            }
        }
        return null
    }

    /**
     * <VariableMutable>::=<tipoDato><listaIdentificadores>
     */
    fun esVariableMutable(tipoDato: Token): DeclaracionVariable? {

        var posicionInicial = posicionActual
        val listaIdentificadores = esListaIdentificadoresVariables()
        if (listaIdentificadores != null) {
            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta el simbolo terminal")
            }
            return VariableMutable(tipoDato, listaIdentificadores)
        } else {
            hacerBT(posicionInicial)
            return null
        }
        return null
    }

    /**
     * <listaIdentificadores.::=IdentificadorVariable["/"<listaIdentificadores>]
     */
    fun esListaIdentificadoresVariables(): ArrayList<Token>? {
        var posicionInicial = posicionActual
        var listaIdentificadores = ArrayList<Token>()
        var identificador = tokenActual
        if (identificador.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            while (identificador.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                listaIdentificadores.add(identificador)
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.SEPARADORES) {
                    obtenerSiguienteToken()
                    identificador = tokenActual
                } else {
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        reportarError(mensaje = "Falta el terminal $")
                    }
                    break
                }
            }
        } else {
            hacerBT(posicionInicial)
            return null
        }

        return listaIdentificadores
    }

    /**
     * <listaIArgumentos>::=IdentificadorVariable["/"<listaArgumentos>]
     */
    fun esListaArgumentos(): ArrayList<Expresion> {
        var listaArgumentos = ArrayList<Expresion>()
        var argumento = esExpresion()
        while (argumento != null) {
            listaArgumentos.add(argumento)
            print(tokenActual.lexema)
            if (tokenActual.categoria == Categoria.SEPARADORES) {
                obtenerSiguienteToken()
                argumento = esExpresion()
            } else {
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    reportarError(mensaje = "Falta el terminal")
                }
                break
            }
        }
        return listaArgumentos
    }

    /**
     * <Argumento>::=<ValorNumerico>|<valorLogico>|cadena
     */
    fun esArgumento(): Argumento? {

        val valorNumerico = esValorNumerico()
        if (valorNumerico != null) {
            return Argumento(valorNumerico)
        }
        val valorLogico = esValorLogico()
        if (valorLogico != null) {
            return Argumento(valorLogico)
        }
        if (tokenActual.categoria == Categoria.CADENA) {
            val cadena = tokenActual
            obtenerSiguienteToken()
            return Argumento(cadena)
        }
        if (tokenActual.categoria == Categoria.CARACTER) {
            var caracter = tokenActual
            obtenerSiguienteToken()
            return Argumento(caracter)
        }
        return null
    }


    /**
     * <valorLogico>::=On|Off
     */
    fun esValorLogico(): ValorLogico? {
        if (tokenActual.categoria == Categoria.BOOLEAN) {
            var valor = tokenActual
            obtenerSiguienteToken()
            return ValorLogico(valor)
        }
        return null
    }

    /**
     *<Retorno>::=palResRetorno IdentArreglo|palResRetorno<valorNumerico>|palResRetorno<Expresion>|palResRetorno void|palResRetorno caracter
     */
    fun esRetorno(): Retorno? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_RETORNO) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_ARREGLO || tokenActual.categoria == Categoria.CARACTER || tokenActual.categoria == Categoria.PALABRA_RESERVADA_VACIO) {
                val token = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.TERMINAL) {
                    obtenerSiguienteToken()
                } else {
                    reportarError(mensaje = "Falta el terminal")
                }
                return Retorno(token, token.fila, token.columna)
            } else {
                var fila = tokenActual.fila
                var colu = tokenActual.columna
                val expresion = esExpresion()
                if (expresion == null) {
                    reportarError(mensaje = "Hay un error en la expresion")
                    while (!(tokenActual.categoria == Categoria.TERMINAL) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                        obtenerSiguienteToken()
                    }
                }
                if (tokenActual.categoria == Categoria.TERMINAL) {
                    obtenerSiguienteToken()

                } else {
                    reportarError(mensaje = "Falta el terminal")
                }
                return Retorno(expresion, fila, colu)
            }
        }
        return null
    }

    /**
     * <InvocacionFuncio>::=IdentificadorMetodo":"<ListaArgumentos>":" "$"
     */
    fun esInvocacionFuncion(): InvocacionFuncion? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_DE_METODOS) {
            var identMetodo = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.PARENTESISABIERTO) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta el agrupador inicial")
            }
            var listaArgumentos = esListaArgumentos()

            if (tokenActual.categoria == Categoria.PARENTESISCERRADO) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta un agrupador final")
            }
            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta el simbolo terminal")
            }

            return InvocacionFuncion(identMetodo, listaArgumentos)

        }
        return null
    }

    /**
     * <Arreglo>::=<TipoDato> indentificadorArreglo"~"<listaDatos>"~""$"
     */
    fun esArreglo(): Sentencia? {
        var posicionInicial = posicionActual
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_ARREGLO) {
                val ident = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR_PUNTO) {
                    obtenerSiguienteToken()
                } else {
                    reportarError(mensaje = "Falta  agrupador inicial de arreglo")
                }
                val listaDatos = esListaDatos()
                if (listaDatos != null) {
                    if (tokenActual.categoria == Categoria.IDENTIFICADOR_PUNTO) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError(mensaje = "Falta  agrupador final de arreglo")
                    }
                    if (tokenActual.categoria == Categoria.TERMINAL) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError(mensaje = "Falta  el terminal")
                    }
                    return Arreglo(tipoDato, ident, listaDatos)

                } else {
                    reportarError(mensaje = "Los datos son obligatorios")
                }
            } else {
                hacerBT(posicionInicial)
                return null
            }
        }
        return null
    }


    /**
     * <ListaDatos>::=<Expresion>["/"<listaDatos>]
     */
    fun esListaDatos(): ArrayList<Expresion>? {
        var listaDatos = ArrayList<Expresion>()
        var expresion = esExpresion()
        while (expresion != null) {
            listaDatos.add(expresion)
            if (tokenActual.categoria == Categoria.SEPARADORES) {
                obtenerSiguienteToken()
                expresion = esExpresion()
                if (expresion == null) {
                    reportarError(mensaje = "Hay un error en la expresion")
                    while (!(tokenActual.categoria == Categoria.IDENTIFICADOR_PUNTO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                        obtenerSiguienteToken()
                    }
                }
            } else {
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    reportarError(mensaje = "Falta el terminal")
                }
                break
            }
        }
        return listaDatos
    }

    /**
     * <Expresion>::=<ExpresionAritmetica>|<ExpresionRelacional>|<ExpresionLogica>|<ExpresionCadena>
     */
    fun esExpresion(): Expresion? {
        var expresion: Expresion? = esExpresionLogica()
        if (expresion != null) {
            return expresion
        }
        expresion = esExpresionRelacional()
        if (expresion != null) {
            return expresion
        }
        expresion = esExpresionAritmetica()
        if (expresion != null) {
            return expresion
        }
        expresion = esExpresionCadena()
        if (expresion != null) {
            return expresion
        }
        return null
    }

    /**
     *<ExpresionLogica>::=<ExpresionLogica>opLBin<ExpresionLogica>|opLNeg<ExpresionLogica>|<ExpresionRelacional>
     *
     * es esquivalente a:
     *
     *
     * <ExpresionLogica>::=opLNeg<ExpresionLogica>[opLBin<ExpresionLogica>]|<ExpresionRelaciona>[opLBin<ExpresionLogica>]
     */
    fun esExpresionLogica(): ExpresionLogica? {

        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.OPERADOR_LOGICO && (tokenActual.lexema == "|")) {
            val opLogico = tokenActual
            obtenerSiguienteToken()
            var expLo1 = esExpresionLogica()
            if (expLo1 != null) {

                if (tokenActual.categoria == Categoria.OPERADOR_LOGICO && (tokenActual.lexema != "|")) {
                    val opBin = tokenActual
                    obtenerSiguienteToken()
                    val expLo2 = esExpresionLogica()
                    return ExpresionLogica(opLogico, expLo1, opBin, expLo2)
                } else {
                    return ExpresionLogica(opLogico, expLo1)
                }
            }
        } else {
            val expRela = esExpresionRelacional()
            if (expRela != null) {
                if (tokenActual.categoria == Categoria.OPERADOR_LOGICO && (tokenActual.lexema != "|")) {
                    val opBin2 = tokenActual
                    obtenerSiguienteToken()
                    val expLo3 = esExpresionLogica()
                    if (expLo3 != null) {
                        return ExpresionLogica(expRela, opBin2, expLo3)
                    } else {
                        reportarError("Falta la otra expresion")
                    }
                } else {
                    return ExpresionLogica(expRela)
                }
            } else {
                hacerBT(posicionInicial)
                return null
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <ExpresionAritmetica>::=":"<ExpresionAritmetica>":"[operadorAritmetico<ExpresionAritmetica>]|ValorNumerico
     *
     * Es Equivalente a:
     *
     * <ExpresionAritmetica>::=":"<ExpresionAritmetica>":"|":"<ExpresionAritmetica>":"operadorArtimetico <ExpresionAritmetica>|
     *                          <valorNumerico>|<valorNumerico>operadorAritmetico<ExpresionAritmetica>
     *
     */
    fun esExpresionAritmetica(): ExpresionAritmetica? {

        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.AGRUPADOR) {
            obtenerSiguienteToken()
            val expA1 = esExpresionAritmetica()

            if (expA1 != null) {

                if (tokenActual.categoria == Categoria.AGRUPADOR) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {
                        var oPa = tokenActual
                        obtenerSiguienteToken()

                        val exPA2 = esExpresionAritmetica()
                        if (exPA2 != null) {

                            return ExpresionAritmetica(expA1, oPa, exPA2)
                        }
                    } else {
                        return ExpresionAritmetica(expA1)
                    }
                } else {
                    reportarError(mensaje = "Falta el agrupador final")
                }
            } else {
                reportarError(mensaje = "Expresión Aritmetica no especificada")
            }
        } else {

            var valorNum = esValorNumerico()
            if (valorNum != null) {
                if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {
                    var oPa = tokenActual
                    obtenerSiguienteToken()
                    val exPA = esExpresionAritmetica()
                    if (exPA != null) {

                        return ExpresionAritmetica(valorNum, oPa, exPA)
                    }
                } else {
                    return ExpresionAritmetica(valorNum)
                }
            } else {

                hacerBT(posicionInicial)
                return null
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     * <ExpresionRelacional>::=":"<ExpresionAritmetica>OperadorRelacional<ExpresionAritmetica>|<valorLogico>
     */
    fun esExpresionRelacional(): ExpresionRelacional? {
        var posicionInicial = posicionActual

        val expreArit1 = esExpresionAritmetica()
        if (expreArit1 != null) {
            if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL) {
                val operador = tokenActual
                obtenerSiguienteToken()
                val expreArit2 = esExpresionAritmetica()
                if (expreArit2 != null) {

                    return ExpresionRelacional(expreArit1, operador, expreArit2, tokenActual.fila, tokenActual.columna)
                } else {
                    reportarError(mensaje = "La ota expresión aritmetica es obligatoria")
                }
            } else {
                hacerBT(posicionInicial)
                return null
            }
        } else {
            var valorlogico = esValorLogico()
            if (valorlogico != null) {
                return ExpresionRelacional(valorlogico)
            } else {

                hacerBT(posicionInicial)
                return null
            }
        }
        hacerBT(posicionInicial)
        return null
    }

    /**
     *<ExpresionCadena>::=cadena["↔"<Expresion>]|IdentificadorVariable
     */
    fun esExpresionCadena(): ExpresionCadena? {

        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.CADENA) {
            val cadena = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.CONCATENAR) {
                val concatenaion: Token = tokenActual
                obtenerSiguienteToken()
                val expresion = esExpresion()

                if (expresion != null) {
                    return ExpresionCadena(cadena, concatenaion, expresion)

                } else {
                    reportarError(mensaje = "La expresión a concatenar es obligatoria")
                }

            } else {
                return ExpresionCadena(cadena)
            }
        } else {
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                val idenVariable: Token = tokenActual
                obtenerSiguienteToken()
                return ExpresionCadena(idenVariable)
            } else {
                hacerBT(posicionInicial)
                return null
            }
        }
        hacerBT(posicionInicial)
        return null
    }


    /**
     * <valorNumerico>::=[<signo>]entero|[<signo>]decimal|[<signo>]identificadorVariable
     */
    fun esValorNumerico(): ValorNumerico? {

        var signo: Token? = null
        if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && (tokenActual.lexema == "S++" || tokenActual.lexema == "R--")) {
            signo = tokenActual
            obtenerSiguienteToken()

        }
        if (tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            val valNumerico = tokenActual
            obtenerSiguienteToken()
            return ValorNumerico(signo, valNumerico)
        }
        return null
    }


    /**
     * <Incremento>::= operadorIncremento IndentificadorVariable | IndentificadorVariable IndentificadorVariable
     */
    fun esIncrementoDecremento(): IncrementoDecremento? {
        var iden: Token? = null
        if (tokenActual.categoria == Categoria.OPERADOR_INCREMENTO || tokenActual.categoria == Categoria.OPERADOR_DECREMENTO) {
            var operador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                iden = tokenActual
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta el identificador de variable")
            }
            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta el símbolo terminal $")
            }

            return IncrementoDecremento(iden, operador)

        }
        return null
    }

    /**
     * <FuncionMatematica>::=palabraReservadaFuncionMatematica<FnsMatematicas>$
     */
    fun esFuncionMatematica(): FuncionMatematica? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_FUNCION_MATEMATICA) {
            obtenerSiguienteToken()

            val matematica = esMatemtica()
            if (matematica != null) {
                if (tokenActual.categoria == Categoria.TERMINAL) {
                    obtenerSiguienteToken()
                } else {
                    reportarError(mensaje = "Falta el simbolo terminal")
                }
                return FuncionMatematica(matematica)
            } else {
                reportarError(mensaje = "Especificar la funcion Matemática")
            }

        } else {

            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                var variable = tokenActual
                obtenerSiguienteToken()
                if ((tokenActual.categoria == Categoria.OPERADOR_ASIGNACION)) {
                    var opR = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_FUNCION_MATEMATICA) {
                        obtenerSiguienteToken()

                        val matematica = esMatemtica()
                        if (matematica != null) {
                            if (tokenActual.categoria == Categoria.TERMINAL) {
                                obtenerSiguienteToken()
                            } else {
                                reportarError(mensaje = "Falta el simbolo terminal")
                            }
                            return FuncionMatematica(variable, opR, matematica)
                        } else {
                            reportarError(mensaje = "Especificar la funcion Matemática")
                        }

                    } else {
                        hacerBT(posicionInicial)
                        return null
                    }

                } else {
                    hacerBT(posicionInicial)
                    return null
                }
            }
        }
        return null
    }


    /**
     * <Matematica>::=PalabraIdentificadorMatematico":"<ValorNumerico>":"
     */
    fun esMatemtica(): Matematica? {
        if (tokenActual.categoria == Categoria.PALABRA_IDENTIFICADOR_MATEMATICO) {
            val fnMatematica = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.AGRUPADOR) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el agrupador inicial")
            }
            val valorNumerico = esValorNumerico()
            if (valorNumerico != null) {
                if (tokenActual.categoria == Categoria.AGRUPADOR) {
                    obtenerSiguienteToken()
                } else {
                    reportarError("Falta el agrupador final")
                }
                return Matematica(fnMatematica, valorNumerico)

            } else {
                reportarError(mensaje = "Especificar el valor numerico a evaluar")
            }

        }
        return null
    }

    /**
     * <SentenciaRuptura>::=<DeclaracionRuptura>|<DeclaracionContinuar>
     */
    fun esSentenciaRuptura(): Sentencia? {

        var sentencia: Sentencia? = esDeclaracionRuptura()
        if (sentencia != null) {
            return sentencia
        }
        sentencia = esDeclaracionContinuar()
        if (sentencia != null) {
            return sentencia
        }
        return null
    }

    /**
     * <DeclaracionRuptura>::=PalabraReservadaRomper"$"
     */
    fun esDeclaracionRuptura(): Sentencia? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_ROMPER) {
            val sentencia = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta el simbolo terminal")
            }
            return DeclaracionRuptura(sentencia)
        }
        return null
    }

    /**
     * <DeclaracionRuptura>::=PalabraReservadaContinuar"$"
     */
    fun esDeclaracionContinuar(): Sentencia? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_CONTINUAR) {
            val sentencia = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError(mensaje = "Falta el simbolo terminal")
            }
            return DeclaracionContinuar(sentencia)
        }
        return null
    }
}