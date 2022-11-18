package co.edu.uniquindio.compiladores.proyecto.Controladores

import co.edu.uniquindio.compiladores.proyecto.Lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.Lexico.Error
import co.edu.uniquindio.compiladores.proyecto.Lexico.Token
import co.edu.uniquindio.compiladores.proyecto.Semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.proyecto.Sintaxis.AnalizadorSintactico
import javafx.collections.FXCollections.observableArrayList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*
import co.edu.uniquindio.compiladores.proyecto.Sintaxis.UnidadDeCompilacion
import java.io.File

class InicioController : Initializable {

    @FXML
    lateinit var codigoFuente: TextArea
    @FXML
    lateinit var tablaTokens: TableView<Token>

    @FXML
    lateinit var TablaErroresLexicos: TableView<Token>
    @FXML
    lateinit var ErroresLexicos: TableColumn<Token, String>
    @FXML
    lateinit var FilaErroresLexicos: TableColumn<Token, String>
    @FXML
    lateinit var columnaErroresLexicos: TableColumn<Token, String>


    @FXML
    lateinit var tablaErroresSintacticos: TableView<Error>
    @FXML
    lateinit var errorsintactico: TableColumn<Token, String>
    @FXML
    lateinit var filasintactica: TableColumn<Token, String>
    @FXML
    lateinit var columnasintactica: TableColumn<Token, String>

    @FXML
    lateinit var tablaErroresSemanticos: TableView<Error>

    @FXML
    lateinit var errorSemantico: TableColumn<Token, String>
    @FXML
    lateinit var filaSemantica: TableColumn<Token, String>
    @FXML
    lateinit var columnaSemantica: TableColumn<Token, String>


    @FXML
    lateinit var colLexema: TableColumn<Token, String>
    @FXML
    lateinit var colCategoria: TableColumn<Token, String>
    @FXML
    lateinit var colFila: TableColumn<Token, Int>
    @FXML
    lateinit var colColumna: TableColumn<Token, Int>

    private var UC: UnidadDeCompilacion? = null

    private lateinit var lexico: AnalizadorLexico
    private lateinit var sintaxis: AnalizadorSintactico
    private lateinit var semantico: AnalizadorSemantico


    @FXML
    lateinit var arbolVisual: TreeView<String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        colLexema.cellValueFactory = PropertyValueFactory("lexema")
        colCategoria.cellValueFactory = PropertyValueFactory("categoria")
        colFila.cellValueFactory = PropertyValueFactory("fila")
        colColumna.cellValueFactory = PropertyValueFactory("columna")

        ErroresLexicos.cellValueFactory = PropertyValueFactory("Error")
        FilaErroresLexicos.cellValueFactory = PropertyValueFactory("Fila")
        columnaErroresLexicos.cellValueFactory = PropertyValueFactory("columna")


        errorsintactico.cellValueFactory = PropertyValueFactory("Error")
        filasintactica.cellValueFactory = PropertyValueFactory("Fila")
        columnasintactica.cellValueFactory = PropertyValueFactory("columna")

        errorSemantico.cellValueFactory = PropertyValueFactory("Error")
        filaSemantica.cellValueFactory = PropertyValueFactory("Fila")
        columnaSemantica.cellValueFactory = PropertyValueFactory("columna")


    }


    @FXML
    fun analizarCodigo(e: ActionEvent) {

        if (codigoFuente.text.length > 0) {
            lexico = AnalizadorLexico(codigoFuente.text)

            lexico.analizar()

            tablaTokens.items = observableArrayList(lexico.listaTokens)
            TablaErroresLexicos.items = observableArrayList(lexico.listaReportesErrores)


            if (lexico.listaReportesErrores.isEmpty()) {
                sintaxis = AnalizadorSintactico(lexico.listaTokens)
                UC = sintaxis.esUnidadDeCompilacion()
                tablaErroresSintacticos.items = observableArrayList(sintaxis.listaErrores)
                if (UC != null) {

                    arbolVisual.root = UC!!.getArbolVisual()
                    semantico = AnalizadorSemantico(UC!!)
                    semantico.llenarTablaSimbolos()

                    semantico.analizarSemantica()
                    print(semantico.tablaSimbolos)
                    tablaErroresSemanticos.items = observableArrayList(semantico.erroresSemanticos)

                }

            } else {
                val alerta = Alert(Alert.AlertType.WARNING)
                alerta.headerText = "Mensaje"
                alerta.contentText = "Hay errores lexicos en el codigo fuente"
            }


        }

    }

    @FXML
    fun traducirCodigo(e: ActionEvent) {

        if (lexico.listaReportesErrores.isEmpty() && sintaxis.listaErrores.isEmpty() && semantico.erroresSemanticos.isEmpty()) {
            val codigo = UC!!.getJavaCode()
            File("src/Principal.java").writeText(codigo)
            try {
                val runtime = Runtime.getRuntime().exec("javac src/Principal.java")
                runtime.waitFor()
                Runtime.getRuntime().exec("java Principal", null, File("src"))
            } catch (ea: Exception) {
                ea.printStackTrace()
            }


               print("\n")
               println(UC!!.getJavaCode())



        } else {
            var alert = Alert(Alert.AlertType.ERROR)
            alert.headerText = null
            alert.contentText = "No se puede traducir por que existen errores en el codigo fuente"
            alert.show()
        }


    }


}