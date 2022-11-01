package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.Sintaxis.AnalizadorSintactico
import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.lexico.Error
import javafx.collections.FXCollections.observableArrayList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*

class InicioController : Initializable{
    @FXML lateinit var codigoFuente:TextArea

    @FXML    lateinit var tablaTokens: TableView<Token>
    @FXML    lateinit var columnaLexema: TableColumn<Token, String>
    @FXML    lateinit var columnaCategoria: TableColumn<Token, String>
    @FXML    lateinit var columnaFila: TableColumn<Token, Int>
    @FXML    lateinit var columnaColumna: TableColumn<Token, Int>

    @FXML    lateinit var tablaErroresLexicos:TableView<Error>
    @FXML    lateinit var ErroresLexicos: TableColumn<Error, String>
    @FXML    lateinit var FilaErroresLexicos: TableColumn<Error, String>
    @FXML    lateinit var columnaErroresLexicos: TableColumn<Error, String>

    @FXML    lateinit var tablaErroresSintacticos:TableView<Error>
    @FXML    lateinit var ErroresSintacticos: TableColumn<Error, String>
    @FXML    lateinit var FilaErroresSintacticos: TableColumn<Error, String>
    @FXML    lateinit var columnaErroresSintacticos: TableColumn<Error, String>

    @FXML lateinit var arbolVisual:TreeView<String>

    private lateinit var lexico: AnalizadorLexico

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        columnaLexema.cellValueFactory = PropertyValueFactory("lexema")
        columnaCategoria.cellValueFactory = PropertyValueFactory("categoria")
        columnaFila.cellValueFactory = PropertyValueFactory("fila")
        columnaColumna.cellValueFactory = PropertyValueFactory("columna")
    }
    @FXML    fun analizarCodigo( e : ActionEvent){
    if(codigoFuente.text.length>0){
        lexico = AnalizadorLexico(codigoFuente.text)
        lexico.analizar()

        tablaTokens.items = observableArrayList(lexico.listaTokens)
        /**
         * tablaErroresLexicos.items = observableArrayList(lexico.ListaErrores)
         */

        if(lexico.ListaErrores.isEmpty()){
            val sintaxis = AnalizadorSintactico(lexico.listaTokens)
            val uc = sintaxis.esUnidadDeCompilacion()

            if(uc!=null){
                arbolVisual.root = uc.getArbolVisual()
            }
        }else{
            var alerta = Alert(Alert.AlertType.WARNING)
            alerta.headerText = "Mensaje"
            alerta.contentText = "Hay errores léxicos en el código fuente"
        }


    }

    }
}