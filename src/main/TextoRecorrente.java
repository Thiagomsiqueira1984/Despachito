package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TextoRecorrente {

    /*
    Atributos
     */

    public static List<String> textoRecorrente = new ArrayList<>();

    public static String divisor =
            Character.toString((char)248) +
            Character.toString((char)216) +
            Character.toString((char)248);
    public static String tR1 =
            "Foram computados todos os vínculos empregatícios que constam no CNIS, realizadas as devidas alterações," +
            " confirmações e inclusões conforme a documentação apresentada" +
            divisor +
            "Não foram computados os seguintes vínculos empregatícios:" + "\n" + "-" +
            divisor +
            "Computados meses de atividade rural como carência por conta da Ação Civil Pública" +
                    " nº 50382611520154047100." +
            divisor +
            "Foi computado período de benefício por incapacidade como carência por conta da Ação Civil Pública" +
                    " nº 200971000041034.";
}
