package co.edu.uniquindio.compiladores.lexico

import co.edu.uniquindio.compiladores.lexico.Categoria

class Token(var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int) {
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }

    fun getJavaCode(): String {

        if (categoria == Categoria.IDENTIFICADOR_TIPO_DECIMAL) {
            return "double"
        }
        if (categoria == Categoria.IDENTIFICADOR_TIPO_CADENA) {

            return "String"
        }
        if (categoria == Categoria.IDENTIFICADOR_TIPO_ENTERO) {
            return "int"
        }
        if (categoria == Categoria.IDENTIFICADOR_TIPO_CARACTER) {
            return "char"
        }
        if (categoria == Categoria.IDENTIFICADOR_TIPO_BOOLEANO) {
            return "boolean"
        }

        if (categoria == Categoria.OPERADOR_RELACIONAL) {

            if (lexema == "|∞") {
                return "!="

            }
            if (lexema == "∞") {
                return "="

            }
            if (lexema == "∞∞") {
                return "=="

            }
            if (lexema == "+") {
                return ">"

            }

            if (lexema == "-") {
                return "<"

            }
            if (lexema == "+∞") {
                return ">="

            }
            if (lexema == "-∞") {
                return "<="

            }
        }
        if (categoria == Categoria.CADENA) {

            return lexema.replace("%", "\"")
        }
        if (categoria == Categoria.CARACTER) {

            return lexema.replace("/", "\'")
        }

        if (categoria == Categoria.OPERADOR_ARITMETICO) {

            if (lexema == "S++") {
                return "+"

            }
            if (lexema == "R--") {
                return "-"

            }
            if (lexema == "mod%") {
                return "%"

            }
            if (lexema == "m#") {
                return "*"
            }
            if (lexema == "d//") {
                return "/"
            }
        }
        if (categoria == Categoria.OPERADOR_ASIGNACION) {

            if (lexema == "S++∞") {
                return "+="
            }
            if (lexema == "R--∞") {
                return "-="
            }
            if (lexema == "m#∞") {
                return "*="
            }
            if (lexema == "mod%∞") {
                return "%="
            }
            if (lexema == "d//∞") {
                return "/="
            }
            if (lexema == "∞") {
                return "="
            }
        }

        if (categoria == Categoria.OPERADOR_LOGICO) {
            if (lexema == "YY") {
                return "&&"
            }
            if (lexema == "O") {
                return "||"
            }
        }


        if (categoria == Categoria.ENTERO) {
            var cod = lexema
            var cod1 = ""
            for (s in cod) {
                if (s.isDigit()) {
                    cod1 += s
                }
            }
            return cod1
        }
        if (categoria == Categoria.DECIMAL) {
            var cod = lexema
            var cod1 = cod.replace(",", ".")
            var cod2 = ""
            for (s in cod1) {
                if (s.isDigit() || s == '.') {
                    cod2 += s
                }
            }
            return cod2
        }

        if (categoria == Categoria.BOOLEAN) {
            if (lexema == "On") {
                return "true"
            }
            if (lexema == "Off") {
                return "false"
            }
        }

        return lexema
    }


}
