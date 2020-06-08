package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Config {

    /*
    Atributos
     */


    private static File OLfolder = new File(System.getProperty("user.home"), "Despachito"); //pasta do arquivo db listaOL como caminho em String
    private static Path pathOLfolder = Paths.get(OLfolder.getAbsolutePath()); //pasta do arquivo db listaOL como path
    private static File OL = new File(OLfolder, "listaOL.dpch"); //arquivo db listaOL como caminho em String
    private static Path pathOL = Paths.get(OL.getAbsolutePath()); //arquivo db listaOL como path
    private static List<String> listaOL = new ArrayList<>();
    private static String OLpadrao;
    private static String OLatual;
    private static File pathImportaExtrato;


    /*
    Getters e Setters
     */

    public static File getOLfolder() {
        return OLfolder;
    }

    public static void setOLfolder(File OLfolder) {
        Config.OLfolder = OLfolder;
    }

    public static Path getPathOLfolder() {
        return pathOLfolder;
    }

    public static void setPathOLfolder(Path pathOLfolder) {
        Config.pathOLfolder = pathOLfolder;
    }

    public static File getOL() {
        return OL;
    }

    public void setOL(File OL) {
        this.OL = OL;
    }

    public static Path getPathOL() {
        return pathOL;
    }

    public void setPathOL(Path pathOL) {
        this.pathOL = pathOL;
    }

    public static List<String> getListaOL() {
        return listaOL;
    }

    public static String getListaOL(int index) {
        return listaOL.get(index);
    }

    public static void addListaOL(String OL) {
        Config.listaOL.add(OL);
    }

    public static int sizeListaOL() {
        return Config.listaOL.size();
    }

    public static void clearListaOL() {
        Config.listaOL.clear();
    }

    public static void swapListaOL(int indexUm, int indexDois) {
        Collections.swap(listaOL, indexUm, indexDois);
    }

    public static void removeListaOL(int index) {
        Config.listaOL.remove(index);
    }

    public static void setListaOL(int index, String novTextoOL) {
        Config.listaOL.set(index, novTextoOL);
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

        /*
        Parte referente às OL padrão
         */
        Config.criaListaOL();

        Label etiquetaOL = new Label("Órgãos de Lotação - OL:");
        Label etiquetaOLpadrao = new Label();
        etiquetaOLpadrao.setText("OL padrão: " + OLpadrao);

        ToggleGroup grupoOL = new ToggleGroup();
        VBox vBOL = new VBox();
        Config.constroiListaOLConfig(grupoOL, vBOL);
        ScrollPane caixaOL = new ScrollPane();
        caixaOL.setMinViewportHeight(75);
        caixaOL.setContent(vBOL);

        Label msgErro = new Label("É necessário ter pelo menos uma OL cadastrada.");
        msgErro.setTextFill(Color.web("#db0b2f"));
        msgErro.setVisible(false);

        Button novo = new Button("Novo");
        Button editar = new Button("Editar");
        Button tornarPadrao = new Button("Tornar padrão");
        Button excluir = new Button("Excluir");
        HBox botoesOL = new HBox();
        botoesOL.setPadding(new Insets(-30, 0, 0, 0));
        botoesOL.getChildren().addAll(novo, editar, tornarPadrao, excluir);
        botoesOL.setSpacing(5);

        novo.setOnAction(event -> adicionarOL(grupoOL, vBOL));

        editar.setOnAction(event -> editarOL(grupoOL, vBOL, etiquetaOLpadrao));

        tornarPadrao.setOnAction(event -> tornarPadrao(grupoOL, vBOL, etiquetaOLpadrao));

        excluir.setOnAction(event -> excluirOL(grupoOL, vBOL, etiquetaOLpadrao, msgErro));

        /*
        Parte referente ao diretório padrão para importação de arquivos de extrato
         */
        Label dirPadraoImp = new Label("Diretório padrão para importação de arquivos de extrato");
        dirPadraoImp.setPadding(new Insets(10, 0, 0, 0));
        TextField campoDirPadraoImp = new TextField();
        campoDirPadraoImp.setText(Config.pathImportaExtrato.toString());
        campoDirPadraoImp.setEditable(false);
        Button browseButton = new Button("procurar");
        HBox hBDir = new HBox();
        hBDir.getChildren().addAll(campoDirPadraoImp, browseButton);

        browseButton.setOnAction(event ->{
            DirectoryChooser seletorDirPadr = new DirectoryChooser();
            seletorDirPadr.setInitialDirectory(Config.pathImportaExtrato);
            if (seletorDirPadr != null) {
                File pastaSelecionada = seletorDirPadr.showDialog(null);
                Config.pathImportaExtrato = pastaSelecionada;

                File home = new File(System.getProperty("user.home"));
                File dpcFolder = new File(home, "Despachito");
                Path pathDpcFolder = Paths.get(dpcFolder.getAbsolutePath());
                if (Files.notExists(pathDpcFolder)) {
                    dpcFolder.mkdir();
                }
                File dP = new File(dpcFolder, "dirPadrao.dpch");
                Path pathDp = Paths.get(dP.getAbsolutePath());
                if (Files.notExists(pathDp)) {
                    try {
                        dP.createNewFile();
                    } catch (Exception ex) {}
                }
                try {
                    FileWriter f = new FileWriter(dP);
                    f.write(Config.pathImportaExtrato.toString());
                    f.close();
                } catch (Exception ex) {}
                campoDirPadraoImp.setText(Config.pathImportaExtrato.toString());
            }
        });


        Button botaoFechar = new Button("Fechar");
        botaoFechar.setPrefWidth(75);
        botaoFechar.setOnAction(e -> janelaConfig.close());

        VBox layout = new VBox();
        layout.getChildren().addAll(etiquetaOL, etiquetaOLpadrao, caixaOL, msgErro, botoesOL,
                dirPadraoImp, hBDir, botaoFechar);
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
        if (Files.notExists(Config.getPathOLfolder())) {
            OLfolder.mkdir();
        }

        if (Files.notExists(Config.getPathOL())) {
            try {
                Config.getOL().createNewFile();
                Config.addListaOL("23.001.820 - Central Especializada de Alta Performance Aposentadoria por Idade");
                Config.setOLatual(Config.getListaOL(0));
                Config.setOLpadrao(Config.getListaOL(0));
                Config.listaParaArquivoOL(Config.getOL());
            } catch (Exception ex) {}
        }
        else {Config.arquivoParaListaOL(Config.getOL());}
        Config.setOLpadrao(Config.getListaOL(0));
        Config.setOLatual(Config.getListaOL(0));
    }


    /*
    Passa o conteúdo da lista de OL para o arquivo de database listaOL
     */
    public static void listaParaArquivoOL(File arquivoDbOL) {
        try {
            FileWriter f = new FileWriter(arquivoDbOL);
            for (String str : Config.listaOL) {
                if (!str.trim().isEmpty()) {
                    f.write(str + divisor);
                }
            }
            f.close();
        } catch (Exception ex) {}
    }

    /*
    Passa o conteúdo do arquivo database listaOL para a lista listaOL
     */
    public static void arquivoParaListaOL(File arquivoDbOL) {
        try {
            Config.clearListaOL();
            Scanner ler = new Scanner(arquivoDbOL);
            ler.useDelimiter(Config.divisor);
            while (ler.hasNext()) {
                Config.listaOL.add(ler.next());
            }
            ler.close();
        } catch (Exception ex) {
        }
    }

    /*
    Cria a lista de OL na GUI da janela de configurações
     */
    public static void constroiListaOLConfig(ToggleGroup toggleGroup, VBox vB) {
        toggleGroup.getToggles().clear();
        vB.getChildren().clear();
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


    /*
    Adiciona uma OL à listaOL e atualiza o arquivo de database listaOL e GUI
     */
    public static void adicionarOL(ToggleGroup toggleGroup, VBox vBox) {
        String novaOL = Popups.popupAdicOL("Adicionar OL");
        if (!novaOL.trim().isEmpty()) {
            Config.addListaOL(novaOL);
            Config.listaParaArquivoOL(Config.getOL());
            Config.constroiListaOLConfig(toggleGroup, vBox);
            Config.setOLatual(Config.getListaOL(0));
        }
    }

    /*
    Edita uma OL da listaOL e atualiza o arquivo de database listaOL e GUI
     */
    public static void editarOL(ToggleGroup toggleGroup, VBox vBox, Label labelOLpadrao) {
        int OL = (int) toggleGroup.getSelectedToggle().getUserData();
        String novaOL = Popups.popupAdicOL("Adicionar OL");
        if (!novaOL.trim().isEmpty()) {
            Config.setListaOL(OL, novaOL);
            Config.listaParaArquivoOL(Config.getOL());
            Config.constroiListaOLConfig(toggleGroup, vBox);
            Config.setOLatual(Config.getListaOL(0));
            if (OL == 0) {
                labelOLpadrao.setText(Config.getListaOL(0));
                Config.setOLpadrao(Config.getListaOL(0));
            }
        }
    }

    /*
    Passa o item selecionado no toggleGroup para o index 0 da listaOL e atualiza o arquivo de database listaOL e GUI
     */
    public static void tornarPadrao( ToggleGroup toggleGroup, VBox vBox, Label labelOLpadrao) {
        if ((int)toggleGroup.getSelectedToggle().getUserData() != 0) {
            Config.swapListaOL((int)toggleGroup.getSelectedToggle().getUserData(), 0);
            Config.listaParaArquivoOL(Config.getOL());
            labelOLpadrao.setText(Config.getListaOL(0));
            Config.setOLpadrao(Config.getListaOL(0));
            Config.constroiListaOLConfig(toggleGroup, vBox);
        }
    }

    /*
    Exclui uma OL da listaOL e atualiza o arquivo de database listaOL e GUI
     */
    public static void excluirOL(ToggleGroup toggleGroup, VBox vBox, Label labelOLpadrao, Label msgErro) {
        int OL = (int)toggleGroup.getSelectedToggle().getUserData();
        if (Config.sizeListaOL() <= 1) {
            msgErro.setVisible(true);
        } else {
            Config.removeListaOL(OL);
            Config.listaParaArquivoOL(Config.getOL());
            if (OL == 0) {
                Config.setOLpadrao(Config.getListaOL(0));
                labelOLpadrao.setText(Config.getListaOL(0));
            }
            Config.constroiListaOLConfig(toggleGroup, vBox);
            Config.setOLatual(Config.getListaOL(0));
        }
    }

    /*
    Verificação inicial de direitório padão de importação de arquivos
     */
    public static void iniciaDirImporta() {
        File home = new File(System.getProperty("user.home"));
        String homeString = Paths.get(home.getAbsolutePath()).toString();
        File dpcFolder = new File(home, "Despachito");
        Path pathDpcFolder = Paths.get(dpcFolder.getAbsolutePath());
        if (Files.notExists(pathDpcFolder)) {
            dpcFolder.mkdir();
        }
        File dP = new File(dpcFolder, "dirPadrao.dpch");
        Path pathDp = Paths.get(dP.getAbsolutePath());
        if (Files.notExists(pathDp)) {
            try {
                dP.createNewFile();
                try {
                    FileWriter f = new FileWriter(dP);
                    f.write(homeString);
                    f.close();
                } catch (Exception ex) {}
            } catch (Exception ex) {}
            Config.pathImportaExtrato = new File(homeString);
        }
        else //Passa o conteúdo do arquivo de dirPadrao para o diretório padrão de importação
            try {
                String p = new String(Files.readAllBytes(dP.toPath()));
                File fp = new File(p);
                Path pp = Paths.get(fp.getAbsolutePath());
                if (Files.exists(pp)) {
                    Config.pathImportaExtrato = new File(p);
                } else {
                    fp.mkdir();
                }
            } catch (Exception ex) {}
    }

}
