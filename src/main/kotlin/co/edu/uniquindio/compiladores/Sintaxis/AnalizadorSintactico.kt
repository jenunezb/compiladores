package co.edu.uniquindio.compiladores.Sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.lexico.Error
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
     * <Funcion>::=IdentificadorMetodo <TipoRetorno> "(" <ListaParametros> ")" <BloqueSentencias>
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

                        }else{
                            reportarError(mensaje = "Falta parentesis cerrado ")
                        }

                    } else {
                        reportarError(mensaje = "Falta parentesis abierto")
                    }

                } else {
                    reportarError(mensaje = "Falta el tipo de retorno o está mal escrito")
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
                if (tokenActual.categoria == Categoria.SEPARADOR) {
                    obtenerSiguienteToken()
                    parametro = esParametro()
                }
                else {
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

        if (tokenActual.categoria == Categoria.LLAVEAPERTURA){

            obtenerSiguienteToken()

            var listaSentencias = esListaSentencias()

            if (tokenActual.categoria == Categoria.LLAVECIERRE) {
                obtenerSiguienteToken()
                return listaSentencias
            } else {
                reportarError(mensaje = "Falta la llave derecha de la función")
            }
        } else {
            reportarError(mensaje = "Falta la llave izquierda de la función")
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
        var inmutable = esVariableInmutable()
        if(inmutable!=null){
            return inmutable
        }
        var mutable = esVariableMutable()
        if(mutable!=null){
            return mutable
        }

        return null
    }

    /**
     * <Decision>::=SI<ExpresionRelacional><BloqueSentencia>[NO<BloqueSentencia>]
     */
    fun esDecision(): Decision? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DECISIONES && (tokenActual.lexema == "SI")) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESISABIERTO) {
                obtenerSiguienteToken()
                val expresion = esExpresionRelacional()
                if (expresion == null) {
                    reportarError(mensaje = "Hay un error en la condición")
                } else{
                    if (tokenActual.categoria == Categoria.PARENTESISCERRADO) {
                        obtenerSiguienteToken()
                        return Decision(expresion)
                }else{
                        reportarError(mensaje = "Falta parentesis cerrrado")
                    }
            }

            }else{
                reportarError(mensaje = "Falta parentesis abierto")
            }
            }
return null
    }

    /**
     * <Ciclo>::=<cicloRam>|<cicloRun>
     */
    fun esCiclo(): Ciclo? {

        var ciclo: Ciclo? = esCicloRam()
        if (ciclo != null) {
            return ciclo
        }
        /**
         * ciclo = esCicloRun()
        if (ciclo != null) {
        return ciclo
        }
         */

        return null
    }

    /**
     * <CicloRam>::=Ram<ExpresionLogica><BloqueSentencia>
     */
    fun esCicloRam(): Ciclo? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_CICLOS && tokenActual.lexema == "Ram") {
            val tipoCiclo = tokenActual.lexema
            obtenerSiguienteToken()
            val expresion = esExpresionLogica()
            if (expresion == null) {
                reportarError(mensaje = "Hay un error en la condición")
                while (!(tokenActual.categoria == Categoria.LLAVE_INICIO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }
            val bloque = esBloqueSentencias()
            if (bloque != null) {
                return CicloRam(expresion, bloque)
            }

        }
        return null
    }

    /**
     * <CicloRum>::=run<varibaleMutable><ExpresionLogica><IncrementoDecremento><BloqueSentencia>
     */
    /**
     * fun esCicloRun(): Ciclo? {
    if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_CICLOS && tokenActual.lexema == "run") {
    obtenerSiguienteToken()
    val tipoCiclo = tokenActual.lexema
    if (tokenActual.categoria == Categoria.AGRUPADOR) {
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
    if (tokenActual.categoria == Categoria.AGRUPADOR) {
    obtenerSiguienteToken()
    } else {
    reportarError(mensaje = "Falta el agrupador final")
    }
    val bloque = esBloqueSentencias()
    if (bloque != null) {
    return CicloRun(declaracionVar!!, expresion, incrementoDecremento, bloque)
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
      */


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
            if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE ||tokenActual.categoria==Categoria.CADENA ){
                obtenerSiguienteToken()
            }else {
                reportarError(mensaje = "La variable no fue asignada")
            }
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
            return Impresion()
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
            if ((tokenActual.categoria == Categoria.OPERADOR_ASIGNACION) && tokenActual.lexema == "∞") {
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
                        while (!(tokenActual.categoria == Categoria.PARENTESISCERRADO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
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
                return Asignacion(identificador, opAsig, null)
            } else {
                val expresion = esExpresion()
                if (expresion != null) {
                    if (tokenActual.categoria == Categoria.TERMINAL) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError(mensaje = "Falta el terminal de la sentencia")
                    }
                    return Asignacion(identificador, opAsig, expresion)
                } else {
                    hacerBT(posicionInicial)
                    return null
                }

            }

        }
        return null
    }

    /**
     * <VariableInmutable>::=<tipoDato><listaIdentificadores>
     */
    fun esVariableInmutable():VariableInmutable? {
        var posicionInicial = posicionActual
        var tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_INMUTABLE) {
                var idenInmutable = tokenActual
                //El parametro esta bien escrito
                obtenerSiguienteToken()
                return VariableInmutable(tipoDato,idenInmutable)
            } else {
                hacerBT(posicionInicial)
                return null
            }
        }
        return null
    }

    /**
     * <VariableMutable>::=<tipoDato><listaIdentificadores>
     */
    fun esVariableMutable():VariableMutable? {
        var tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                var idenMutable = tokenActual
                //El parametro esta bien escrito
                obtenerSiguienteToken()
                return VariableMutable(tipoDato,idenMutable)
            } else {
                reportarError(mensaje = "El identificador de variable no corresponde a un identificador valido")
            }
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
                if (tokenActual.categoria == Categoria.SEPARADOR) {
                    obtenerSiguienteToken()
                    identificador = tokenActual
                } else {
                    if (tokenActual.categoria == Categoria.LLAVE_FIN) {
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
            if (tokenActual.categoria == Categoria.SEPARADOR) {
                obtenerSiguienteToken()
                argumento = esExpresion()
            } else {
                if (tokenActual.categoria == Categoria.LLAVE_FIN) {
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
        if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
            var valor = tokenActual
            obtenerSiguienteToken()
            return ValorLogico(valor)
        }
        return null
    }

    /**
     *<Retorno>::=
     */
    fun esRetorno(): Retorno? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_RETORNO) {
            obtenerSiguienteToken()
            var tipodato=esTipoDato()
            if(tipodato!=null){
                var nombre=tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria==Categoria.TERMINAL){
                    return Retorno(nombre)
                }else{
                    reportarError(mensaje = "Falta el símbolo terminal")
                    return null
                }
            }else{
                reportarError(mensaje = "el dato ingresado no se puede retornar")
                return null
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
            if (tokenActual.categoria == Categoria.SEPARADOR) {
                obtenerSiguienteToken()
                expresion = esExpresion()
                if (expresion == null) {
                    reportarError(mensaje = "Hay un error en la expresion")
                    while (!(tokenActual.categoria == Categoria.IDENTIFICADOR_PUNTO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                        obtenerSiguienteToken()
                    }
                }
            } else {
                if (tokenActual.categoria == Categoria.LLAVE_FIN) {
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
        if (tokenActual.categoria == Categoria.OPERADOR_LOGICO && (tokenActual.lexema == "Y")) {
            val opLogico = tokenActual
            obtenerSiguienteToken()
            var expLo1 = esExpresionLogica()
            if (expLo1 != null) {

                if (tokenActual.categoria == Categoria.OPERADOR_LOGICO && (tokenActual.lexema != "Y")) {
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

        if(tokenActual.categoria==Categoria.DECIMAL || tokenActual.categoria==Categoria.ENTERO){
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.OPERADOR_ARITMETICO){
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.DECIMAL || tokenActual.categoria==Categoria.ENTERO){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria==Categoria.OPERADOR_ARITMETICO){
                        obtenerSiguienteToken()
                        return esExpresionAritmetica()
                    }else if(tokenActual.categoria==Categoria.TERMINAL){
                        return ExpresionAritmetica()
                    }
                }
            }else{
            }
        }
        return null
    }

    /**
     * <ExpresionRelacional>::=":"<ExpresionAritmetica>OperadorRelacional<ExpresionAritmetica>|<valorLogico>
     */
    fun esExpresionRelacional(): ExpresionRelacional? {
            if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
                var tokenA=tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.OPERADOR_RELACIONAL){
                    var tokenRel=tokenActual
                    obtenerSiguienteToken()
                    if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
                        var tokenB=tokenActual
                        obtenerSiguienteToken()
                       return ExpresionRelacional(tokenA,tokenB,tokenRel)
                    }else{
                        reportarError("Debe declarar la variable")
                    }
                }else{
                    reportarError("Debe declarar el operador relacional")
                }

            }else{
                reportarError("Debe declarar la variable")
            }
        return null
    }

    /**
     *<ExpresionCadena>::=cadena["S++"<Expresion>]|IdentificadorVariable
     */
    fun esExpresionCadena(): ExpresionCadena? {

        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.CADENA) {
            val cadena = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && (tokenActual.lexema == "S++")) {
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
            if (tokenActual.categoria == Categoria.PARENTESISABIERTO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el agrupador inicial")
            }
            val valorNumerico = esValorNumerico()
            if (valorNumerico != null) {
                if (tokenActual.categoria == Categoria.PARENTESISCERRADO) {
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