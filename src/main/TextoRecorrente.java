package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
            " confirmações e inclusões conforme a documentação apresentada." +
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
    public VBox fazerblocoTR(int index, Controller cont) {
        AtomicBoolean alteraTR = new AtomicBoolean(false);

        VBox blocoTR = new VBox();

        TextArea areaTexto = new TextArea();
        areaTexto.setWrapText(true);
        areaTexto.setEditable(false);
        if (index < textoRecorrente.size()) {
            areaTexto.setText(textoRecorrente.get(index));
        } else {
            textoRecorrente.add(" ");
            areaTexto.setText(textoRecorrente.get(index));
        }

        /*
        Caixa com os botões
         */
        HBox h = new HBox();
        h.setAlignment(Pos.TOP_RIGHT);

        /*
        Chama popupu para editar o texto recorrente, atualizando a lista e o campo correspondente em caso de alteração
         */
        Button bEditar = new Button("Editar");
        bEditar.setOnAction(e -> {
            alteraTR.set(this.popupEditar(index));
            if (alteraTR.get()) {
                areaTexto.setText(TextoRecorrente.textoRecorrente.get(index));
                try {
                    File tRfolder = new File(System.getProperty("user.home"), "Despachito");
                    FileWriter f = new FileWriter(new File(tRfolder, "TextoRecorrente.dpch"));
                    for (String str: TextoRecorrente.textoRecorrente) {
                        if (!str.trim().isEmpty()) {
                            f.write(str + divisor);
                        }
                    }
                    f.close();
                } catch (Exception ex) {}
            }
        });
        Button bAdicionar = new Button("Adicionar >>");
        bAdicionar.setOnAction(e -> {
                    if (!areaTexto.getText().trim().isEmpty()) {
                        int caret = cont.caixaDespacho.getCaretPosition();
                        cont.caixaDespacho.insertText(caret, areaTexto.getText());
                    }
                });

        h.getChildren().addAll(bEditar, bAdicionar);


        blocoTR.getChildren().addAll(h, areaTexto);

        return blocoTR;
    }

    /*
    Popup de edição do texto recorrente
     */
    public boolean popupEditar(int index){

        AtomicBoolean edicaoTR = new AtomicBoolean(false);

        Stage janelinha = new Stage();

        janelinha.initModality(Modality.APPLICATION_MODAL);
        janelinha.setTitle("Edição de texto recorrente");
        janelinha.setWidth(1000);

        HBox hB = new HBox();
        hB.setAlignment(Pos.TOP_RIGHT);
        hB.setSpacing(15);

        TextArea areaTR = new TextArea();
        areaTR.setWrapText(true);
        areaTR.setPrefHeight(400);
        if (index < TextoRecorrente.textoRecorrente.size()) {
            areaTR.setText(TextoRecorrente.textoRecorrente.get(index));
        }

        Button botaoCancelar = new Button("Cancelar");
        botaoCancelar.setPrefWidth(100);
        botaoCancelar.setOnAction(e -> janelinha.close());

        /*
        Salva as edições feitas, se alguma, na lista de texto recorrente, substituindo ou adicionando
         */
        Button botaoSalvar = new Button("Salvar");
        botaoSalvar.setPrefWidth(100);
        botaoSalvar.setOnAction(e -> {
            if (index < TextoRecorrente.textoRecorrente.size()) {
                if (!areaTR.getText().equals(TextoRecorrente.textoRecorrente.get(index))) {
                    TextoRecorrente.textoRecorrente.set(index, areaTR.getText());
                    edicaoTR.set(true);
                }
            } else {
                TextoRecorrente.textoRecorrente.add(areaTR.getText());
                edicaoTR.set(true);
            }
            janelinha.close();
        });



        hB.getChildren().addAll(botaoCancelar, botaoSalvar);
        VBox vB = new VBox(hB, areaTR);
        vB.getChildren().addAll();
        vB.setAlignment(Pos.CENTER);
        vB.setSpacing(15);
        vB.setPadding(new Insets(10,5,15,5));

        Scene cena = new Scene(vB);
        janelinha.setScene(cena);
        janelinha.setResizable(false);
        janelinha.showAndWait();

        return edicaoTR.get();
    }
}
