package co.edu.uniquindio.compiladores.proyecto.Semantica

class Simbolo() {
    var nombre: String = ""
    var tipo: String =""
    var modificable:Boolean=false
    var fila:Int = 0
    var columna:Int= 0
    var ambito: Ambito? = null
    var tipoParametros: ArrayList<String>? = null
    var tipoRetorno: String = ""

    /**
     * Constructor para la creacion de un simbolo de tipo valor
     */
    constructor(nombre: String, tipo: String,modificable:Boolean ,ambito: Ambito, fila:Int, columna:Int) :this() {
        this.nombre = nombre
        this.tipo = tipo
        this.modificable=modificable
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
    }

    /**
     * Constructor para la creacion de un simbolo de tipo metodo
     */
    constructor(nombre: String, tipoRetorno: String, tipoParametros: ArrayList<String>,ambito:Ambito):this(){
        this.nombre = nombre
        this.tipo = tipoRetorno
        this.tipoParametros = tipoParametros
        this.ambito=ambito
        this.tipoRetorno=tipoRetorno
    }

    override fun toString(): String {
        if (tipoParametros==null){
            return "Simbolo(nombre='$nombre', tipo='$tipo', modificable=$modificable, fila=$fila, columna=$columna, ambito=$ambito)"
        }else{
            return "Simbolo(nombre='$nombre', tipo='$tipo', modificable=$modificable, fila=$fila, columna=$columna, ambito=$ambito,tipoParametros=$tipoParametros)"
        }

    }



}