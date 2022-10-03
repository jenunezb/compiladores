package co.edu.uniquindio.compiladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import javax.swing.JOptionPane;
import javax.swing.JTextArea

fun main() {
    var mensaje = "";
    mensaje=JOptionPane.showInputDialog("ingrese codigo")
    val lexico = AnalizadorLexico(mensaje)
    lexico.analizar()

        var frame = javax.swing.JFrame("Analizador Léxico");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        var contentPane =frame. getContentPane ();
        val label1 = javax.swing.JLabel("Ingrese el código: ")
        var texto1 = ""

        for(tok in lexico.listaTokens){
            texto1 += tok.toString()+"\n"
        }

        val texto = JTextArea(texto1);


    contentPane.add(label1);
    contentPane.add(texto);


        frame.setSize(750, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }