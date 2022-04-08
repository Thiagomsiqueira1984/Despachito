package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.filechooser.FileFilter;
import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage janelaInicial) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        janelaInicial.setTitle("Despachito");
        janelaInicial.setScene(new Scene(root, 1000, 600));
        janelaInicial.setMaximized(true);
        janelaInicial.setMinWidth(800);
        janelaInicial.setMinHeight(600);
        janelaInicial.getIcons().add(new Image(getClass().getResourceAsStream("Icone.png")));
        Font.loadFont(getClass().getResource("Rubik-Black.ttf").toExternalForm(), 40);
        System.setProperty("prism.lcdtext", "false");
        janelaInicial.show();
    }

    public static void main(String[] args) {
        launch(args);
    }




    static class ExtensionFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            return f.getAbsolutePath().toLowerCase().endsWith(".txt");
        }

        @Override
        public String getDescription() {
            return ".txt (arquivo de texto)";
        }
    }
}


