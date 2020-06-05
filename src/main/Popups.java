package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.control.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Popups {

    /*
    Popup de erro com apenas um botão ok pra fechar
     */
    public static void popupAlerta(String titulo, String mensagem){

        Stage janelinha = new Stage();

        janelinha.initModality(Modality.APPLICATION_MODAL);
        janelinha.setTitle(titulo);
        janelinha.setMinWidth(250);

        Label label = new Label();
        label.setText(mensagem);
        Button botao = new Button("OK");
        botao.setPrefWidth(75);
        botao.setOnAction(e -> janelinha.close());

        VBox layout = new VBox();
        layout.getChildren().addAll(label, botao);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(15);
        layout.setPadding(new Insets(10,5,15,5));

        Scene cena = new Scene(layout);
        janelinha.setScene(cena);
        janelinha.setResizable(false);
        janelinha.showAndWait();

    }

    /*
Popup de ok retorna true e cancela retorna false
 */
    public static boolean popupOkCancela(String titulo, String mensagem){
        AtomicBoolean retorno = new AtomicBoolean(false);

        Stage janelinha = new Stage();

        janelinha.initModality(Modality.APPLICATION_MODAL);
        janelinha.setTitle(titulo);
        janelinha.setWidth(800);

        Label label = new Label();
        label.setText(mensagem);
        label.setMaxWidth(750);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);

        Button botaoCancela = new Button("Cancela");
        botaoCancela.setPrefWidth(75);
        botaoCancela.setOnAction(e -> janelinha.close());

        Button botaoOk = new Button("Ok");
        botaoOk.setPrefWidth(75);
        botaoOk.setOnAction(event -> {
                retorno.set(true);
                janelinha.close();
        });

        HBox hB = new HBox();
        hB.getChildren().addAll(botaoCancela, botaoOk);
        hB.setAlignment(Pos.CENTER);

        VBox layout = new VBox();
        layout.getChildren().addAll(label, hB);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(15);
        layout.setPadding(new Insets(10,5,15,5));

        Scene cena = new Scene(layout);
        janelinha.setScene(cena);
        janelinha.setResizable(false);
        janelinha.showAndWait();

        return retorno.get();
    }

    /*
    Abre a janela de informações
     */
    public static void popupInfo(){

        Stage janelinha = new Stage();

        janelinha.initModality(Modality.APPLICATION_MODAL);
        janelinha.setTitle("Informações");
        janelinha.setMinWidth(250);

        Label label1 = new Label();
        label1.setText("Versão: 1.7.1");
        Label label2 = new Label();
        label2.setText("Desenvolvedor: Thiago de Morais Siqueira");
        Button botao = new Button("Fechar");
        botao.setPrefWidth(75);
        botao.setOnAction(e -> janelinha.close());

        VBox layout = new VBox();
        layout.getChildren().addAll(label1, label2, botao);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(5,5,15,5));

        Scene cena = new Scene(layout);
        janelinha.setScene(cena);
        janelinha.setResizable(false);
        janelinha.showAndWait();

    }

    /*
    Abre a popup de adição ou edição de OL
     */
    public static String popupAdicOL(String titulo){

        AtomicReference<String> novoOL = new AtomicReference<>(" ");

        Stage janelinha = new Stage();

        janelinha.initModality(Modality.APPLICATION_MODAL);
        janelinha.setTitle(titulo);
        janelinha.setMinWidth(400);

        Label labelCodOL = new Label();
        labelCodOL.setText("Código da OL");
        TextField campoCodOL = new TextField();
        campoCodOL.setPromptText("00.000.00");
        campoCodOL.setPrefWidth(100);
        VBox paneCodOL = new VBox();
        paneCodOL.getChildren().addAll(labelCodOL, campoCodOL);

        Label labelNomeOL = new Label();
        labelNomeOL.setText("Nome da OL");
        TextField campoNomeOL = new TextField();
        campoNomeOL.setPromptText("Agência da Previdência Social Cidade");
        campoNomeOL.setPrefWidth(600);
        VBox paneNomeOL = new VBox();
        paneNomeOL.getChildren().addAll(labelNomeOL, campoNomeOL);
        HBox hb1 = new HBox();
        hb1.getChildren().addAll(paneCodOL, paneNomeOL);
        hb1.setSpacing(15);

        Label msgErro = new Label("Campos código do OL e nome do OL devem ser preenchidos");
        msgErro.setTextFill(Color.web("#db0b2f"));
        msgErro.setVisible(false);


        Button botaoOk = new Button("Ok");
        botaoOk.setPrefWidth(75);
        botaoOk.setOnAction(event -> {
            if (campoCodOL.getText().isEmpty() | campoNomeOL.getText().isEmpty()) {
                msgErro.setVisible(true);
            }
            else {
                novoOL.set(campoCodOL.getText() + campoNomeOL.getText());
                janelinha.close();
            }
        });
        Button botaoCancelar = new Button("Cancelar");
        botaoCancelar.setPrefWidth(75);
        botaoCancelar.setOnAction(e -> janelinha.close());

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(botaoCancelar, botaoOk);
        hb2.setSpacing(15);
        hb2.setAlignment(Pos.CENTER);


        VBox layout = new VBox();
        layout.getChildren().addAll(hb1, msgErro, hb2);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(5,10,15,10));

        Scene cena = new Scene(layout);
        janelinha.setScene(cena);
        janelinha.setResizable(false);
        janelinha.showAndWait();

        return novoOL.get();
    }
}
