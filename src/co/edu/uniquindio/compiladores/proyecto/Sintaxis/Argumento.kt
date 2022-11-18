package co.edu.uniquindio.compiladores.proyecto.Sintaxis

import co.edu.uniquindio.compiladores.proyecto.Lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import javafx.scene.control.TreeItem

class Argumento(){
    var valorLogico:ValorLogico?=null
    var valorNumerico:ValorNumerico?=null
    var otroValor:Token?=null


    constructor(valorLogico: ValorLogico?):this(){
    this.valorLogico=valorLogico

    }
    constructor(valorNumerico: ValorNumerico?):this(){
        this.valorNumerico=valorNumerico
    }
    constructor(otroValor: Token?):this(){
        this.otroValor=otroValor
    }

   fun getArbolVisual(): TreeItem<String> {

       if (valorLogico != null) {
           return valorLogico!!.getArbolVisual()
       } else {
           if (valorNumerico!=null){
               return valorNumerico!!.getArbolVisual()
           }else{
               if (otroValor!!.categoria== Categoria.CADENA){
                   return TreeItem<String>("Cadena: ${otroValor!!.lexema} ")
               }else{
                   return TreeItem<String>("Caracter: ${otroValor!!.lexema} ")
               }
           }
       }
   }
}