package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.control.*;

public class Popup {

    public static void popup(String titulo, String mensagem){

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
        janelinha.showAndWait();

    }
}
