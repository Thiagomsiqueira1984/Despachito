package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {

    /*
    Atributos
     */


    private static List<String> listaOL = new ArrayList<>();
    private static String OLpadrao;
    private static String OLatual;


    /*
    Getters e Setters
     */

    public static String getListaOL(int index) {
        return listaOL.get(index);
    }

    public static void addListaOL(String OL) {
        Config.listaOL.add(OL);
    }

    public static void clearListaOL() {
        Config.listaOL.clear();
    }

    public static String getOLpadrao() {
        return OLpadrao;
    }

    public static void setOLpadrao(String OLpadrao) {
        Config.OLpadrao = OLpadrao;
    }

    public static String getOLatual() {
        return OLatual;
    }

    public static void setOLatual(String OLatual) {
        Config.OLatual = OLatual;
    }

    /*
    Métodos
     */


    /*
    Popup da janela de configurações
     */
    public static void janelaConfig() {

        Stage janelaConfig = new Stage();

        janelaConfig.initModality(Modality.APPLICATION_MODAL);
        janelaConfig.setTitle("Configurações");
        janelaConfig.setMinWidth(800);
        janelaConfig.setMaxWidth(800);

        Config.criaListaOL();

        Label etiquetaOL = new Label();
        etiquetaOL.setText("Órgãos de Lotação - OL:");
        Label etiquetaOLpadrao = new Label();
        etiquetaOLpadrao.setText("OL padrão: " + OLpadrao);

        ToggleGroup grupoOL = new ToggleGroup();
        VBox vBOL = new VBox();
        Config.constoiListaOLConfig(grupoOL, vBOL);
        ScrollPane caixaOL = new ScrollPane();
        caixaOL.setMinViewportHeight(75);
        caixaOL.setContent(vBOL);

        Button novo = new Button("Novo");
        Button editar = new Button("Editar");
        Button tornarPadrao = new Button("Tornar padrão");
        Button excluir = new Button("Excluir");
        HBox botoesOL = new HBox();
        botoesOL.getChildren().addAll(novo, editar, tornarPadrao, excluir);
        botoesOL.setSpacing(5);


        Button botaoFechar = new Button("OK");
        botaoFechar.setPrefWidth(75);
        botaoFechar.setOnAction(e -> janelaConfig.close());

        VBox layout = new VBox();
        layout.getChildren().addAll(etiquetaOL, etiquetaOLpadrao, caixaOL, botoesOL, botaoFechar);
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setSpacing(15);
        layout.setPadding(new Insets(15, 15, 15, 15));

        Scene cena = new Scene(layout);
        janelaConfig.setScene(cena);
        janelaConfig.setResizable(false);
        janelaConfig.showAndWait();

    }

    /*
    Constrói a lista de OL a partir do arquivo de database
     */
    public static void criaListaOL() {
        File OLfolder = new File(System.getProperty("user.home"), "Despachito");
        Path pathOLfolder = Paths.get(OLfolder.getAbsolutePath());
        if (Files.notExists(pathOLfolder)) {
            OLfolder.mkdir();
        }
        File OL = new File(OLfolder, "listaOL.dpch");
        Path pathOL = Paths.get(OL.getAbsolutePath());
        if (Files.notExists(pathOL)) {
            try {
                OL.createNewFile();
                Config.addListaOL("23.001.820 - Central Especializada de Alta Performance Aposentadoria por Idade");
                Config.setOLatual(Config.getListaOL(0));
                Config.setOLpadrao(Config.getListaOL(0));
                FileWriter f = new FileWriter(OL);
                for (String str: Config.listaOL) {
                    if (!str.trim().isEmpty()) {
                        f.write(str + divisor);
                    }
                }
                f.close();

            } catch (Exception ex) {}
        }
        else //Passa o conteúdo do arquivo de listaOL para a lista listaOL
            try {
                Config.clearListaOL();
                Scanner ler = new Scanner(OL);
                ler.useDelimiter(Config.divisor);
                while (ler.hasNext()) {
                    Config.listaOL.add(ler.next());
                }
                ler.close();
            } catch (Exception ex) {}
    }

    /*
    Cria a lista de OL na GUI da janela de configurações
     */
    public static void constoiListaOLConfig(ToggleGroup toggleGroup, VBox vB) {
        int i = 0;
        for (String str: Config.listaOL) {
            RadioButton rb = new RadioButton(str);
            rb.setUserData(i);
            rb.setToggleGroup(toggleGroup);
            vB.getChildren().add(rb);
            i++;
        }
    }

    /*
    divisor para o arquivo de database de OL
     */
    public static String divisor =
            Character.toString((char)248) +
            Character.toString((char)216) +
            Character.toString((char)248);


}
