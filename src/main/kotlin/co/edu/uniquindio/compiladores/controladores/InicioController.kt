package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.collections.FXCollections.observableArrayList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.fxml.Initializable
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*

class InicioController : Initializable{
    @FXML lateinit var codigoFuente:TextArea

    @FXML
    lateinit var tablaTokens: TableView<Token>
    @FXML
    lateinit var columnaLexema: TableColumn<Token, String>
    @FXML
    lateinit var columnaCategoria: TableColumn<Token, String>
    @FXML
    lateinit var columnaFila: TableColumn<Token, Int>
    @FXML
    lateinit var columnaColumna: TableColumn<Token, Int>

    private lateinit var lexico: AnalizadorLexico

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        columnaLexema.cellValueFactory = PropertyValueFactory("lexema")
        columnaCategoria.cellValueFactory = PropertyValueFactory("categoria")
        columnaFila.cellValueFactory = PropertyValueFactory("fila")
        columnaColumna.cellValueFactory = PropertyValueFactory("columna")
    }
    @FXML
    fun analizarCodigo( e : ActionEvent){
    if(codigoFuente.text.length>0){
        lexico = AnalizadorLexico(codigoFuente.text)

        lexico.analizar()

        tablaTokens.items = observableArrayList(lexico.listaTokens)
        print(lexico.listaTokens.get(0).categoria)

    }

    }
}