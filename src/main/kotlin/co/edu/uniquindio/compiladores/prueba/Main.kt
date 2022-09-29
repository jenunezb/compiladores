package co.edu.uniquindio.compiladores.prueba

import javax.swing.JOptionPane;
import javax . swing . JFrame;
import javax . swing . JLabel;
import javax.swing.JTextArea

fun main() {
    var mensaje = "";
    mensaje=JOptionPane.showInputDialog("ingrese codigo")
    val lexico = AnalizadorLexico(mensaje)
    lexico.analizar()

        var frame = JFrame("Analizador Léxico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var contentPane =frame. getContentPane ();
        var label = JLabel("Hello World!");
        val label1 = JLabel("Ingrese el código: ")
        var texto1 = lexico.listaTokens;
        var resultado = ""

        resultado+=texto1 ;

        val texto = JTextArea(resultado);
    

    contentPane.add(label);
    contentPane.add(label1);
    contentPane.add(texto);


        frame.setSize(750, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }