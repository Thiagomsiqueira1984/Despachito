package main;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TextoRecorrente {

    /*
    Atributos
     */


    public static List<String> textoRecorrente = new ArrayList<>(); //lista com os textos recorrentes

    /*
    divisor para o arquivo de database de texto recorrente
     */
    public static String divisor =
            Character.toString((char)248) +
            Character.toString((char)216) +
            Character.toString((char)248);

    /*
    Texto inicial para o arquivo de database de texto recorrente
     */
    public static String tR1 =
            "Foram computados todos os vínculos empregatícios que constam no CNIS, realizadas as devidas alterações," +
            " confirmações e inclusões conforme a documentação apresentada" +
            divisor +
            "Não foram computados os seguintes vínculos empregatícios:" + "\n" + "-" +
            divisor +
            "Computados meses de atividade rural como carência por conta da Ação Civil Pública" +
                    " nº 50382611520154047100/RS." +
            divisor +
            "Foi computado período de benefício por incapacidade como carência por conta da Ação Civil Pública" +
                    " nº 200971000041034/RS.";

    /*
    Métodos
     */

    /*
    grupo de controles para texto recorrente
     */
    public VBox fazerblocoTR(int index) {
        VBox blocoTR = new VBox();

        HBox h = new HBox();
        h.setAlignment(Pos.TOP_RIGHT);
        Button b1 = new Button("Editar");
        Button b2 = new Button("Adicionar >>");
        h.getChildren().addAll(b1, b2);
        TextArea areaTexto = new TextArea();
        areaTexto.setWrapText(true);
        areaTexto.setEditable(false);
        if (index<=textoRecorrente.size()) {
            areaTexto.setText(textoRecorrente.get(index));
        }
        blocoTR.getChildren().addAll(h, areaTexto);

        return blocoTR;
    }
}
