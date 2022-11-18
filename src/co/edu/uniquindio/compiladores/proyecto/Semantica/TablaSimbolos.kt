package co.edu.uniquindio.compiladores.proyecto.Semantica

import co.edu.uniquindio.compiladores.proyecto.Lexico.Error

class TablaSimbolos(var listaErroresSemanticos: ArrayList<Error>) {


    var listaSimbolos: ArrayList<Simbolo> = ArrayList()

    /**
     *Permite almacenar un simbolo que representa una contante,variable,parametro,arreglo
     */
    fun guardarSimboloValor(
        nombre: String,
        tipo: String,
        modificable: Boolean,
        ambito: Ambito,
        fila: Int,
        columna: Int
    ) {

        var simbolo = buscarSimboloValor(nombre, ambito, fila, columna)
        if (simbolo == null) {
            listaSimbolos.add(Simbolo(nombre, tipo, modificable, ambito, fila, columna))
        } else {
            listaErroresSemanticos.add(Error("El campo $nombre ya existe en el ámbito $ambito", fila, columna))
        }
    }

    /**
     * permite almacenar un simbolo que representa una funcion-metodo
     */
    fun guardarSimboloFuncion(
        nombre: String,
        tipoRetorno: String,
        tipoParametros: ArrayList<String>,
        ambito: Ambito,
        fila: Int,
        columna: Int
    ) {
        val s = buscarSimboloFuncion(nombre, tipoParametros)
        if (s == null) {
            listaSimbolos.add(Simbolo(nombre, tipoRetorno, tipoParametros, ambito))
        } else {
            listaErroresSemanticos.add(Error("La funcion $nombre ya existe en el ámbito $ambito", fila, columna))
        }
    }

    /**
     * Permite buscar un volor dentro de la tabla de simbolos
     */
    fun buscarSimboloValor(nombre: String, ambito: Ambito, fila: Int, columna: Int): Simbolo? {
        for (s in listaSimbolos) {
            if (s.nombre == nombre && ambito.equals(s.ambito!!.nombre, s.ambito!!.sentencia, s.ambito!!.funcion)) {
                if (s.fila <= fila) {
                    return s
                } else {
                    listaErroresSemanticos.add(
                        Error(
                            "El campo $nombre esta declarado despues de su uso ",
                            fila,
                            columna
                        )
                    )
                }
            } else {
                if (s.nombre == nombre && ambito.ambito!!.equals(
                        s.ambito!!.nombre,
                        s.ambito!!.sentencia,
                        s.ambito!!.funcion
                    )
                ) {
                    if (s.fila <= fila) {
                        return s
                    } else {
                        listaErroresSemanticos.add(
                            Error(
                                "El campo $nombre esta declarado despues de su uso ",
                                fila,
                                columna
                            )
                        )
                    }
                }
            }
        }
        return null
    }

    /**
     * permite buscar una funcion dentro de la tabla de simbolos
     */
    fun buscarSimboloFuncion(nombre: String, tipoParametros: ArrayList<String>): Simbolo? {
        for (s in listaSimbolos) {
            if (tipoParametros != null) {
                if (s.nombre == nombre && s.tipoParametros == tipoParametros) {
                    return s
                }
            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }
}