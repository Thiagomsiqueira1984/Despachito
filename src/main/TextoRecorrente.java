package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TextoRecorrente {

    /*
    Atributos
     */


    public static List<String> textoRecorrente = new ArrayList<>(); //lista com os textos recorrentes
    public static List<String> exigenciaRecorrente = new ArrayList<>(); //lista com as exigências recorrentes

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
            "Foi realizada reafirmação da DER para a data em que completa os requisitos mínimos para a concessão do benefício.";

    public static String tR2 =
            "Não realizada reafirmação da DER pois não seria suficiente para a concessão do benefício.";

    public static String tR3 =
            "Foi computado período de benefício por incapacidade como carência por conta da Ação Civil Pública" +
                    " nº 02162497720174025101/RJ.";

    public static String tR4 =
            "Computados meses de atividade rural como carência por conta da Ação Civil Pública" +
                    " nº 50382611520154047100/RS.";

    public static String tR5 =
            "Foram computados todos os vínculos empregatícios que constam no CNIS, realizadas as devidas alterações, " +
                    "confirmações e inclusões conforme a documentação apresentada." + "\n";

    public static String tR6 =
            "Não foram computados os seguintes vínculos empregatícios:" + "\n" + "- ";

    /*
    Texto inicial para o arquivo de database de exigência recorrente
     */
    public static String eR1 =
            "- RG, CPF, carteiras de trabalho e comprovante de residência;";

    public static String eR2 =
            "- Declaração dos órgãos públicos onde trabalhou informando os períodos de vínculo, cargo, se a filiação " +
                    "foi ao Regime Geral de Previdência (RGPS) ou a Regime Próprio de Previdência (RPPS), se houve " +
                    "aposentadoria no ente e quais os períodos utilizados para aposentadoria, bem como Certidão de " +
                    "Tempo de Contribuição caso seja RPPS;";

    public static String eR3 =
            "- Ficha de registro de empregado acompanhada de declaração do empregador confirmando o vínculo referente à " +
                    "empresa;";

    public static String eR4 =
            "- Autodeclaração de atividade rural (em anexo) devidamente preenchida, acompanhada de documentos de " +
                    "comprovação de atividade rural caso se trate de segurado especial;";

    public static String eR5 =
            "- Declaração dos órgãos públicos onde trabalhou informando os períodos de vínculo, cargo, se a filiação " +
                    "foi ao Regime Geral de Previdência (RGPS) ou a Regime Próprio de Previdência (RPPS), se houve " +
                    "aposentadoria no ente e quais os períodos utilizados para aposentadoria, bem como Certidão de " +
                    "Tempo de Contribuição caso seja RPPS.";

    public static String eR6 =
            "Formulários de comprovação de atividade especial devidamente preenchidos pelas empresas, acompanhados de " +
                    "laudos ambientais contemporâneos aos períodos de atividade especial";

    public static String eR7 =
            "Declaração informando se recebe pensão por morte deixada por cônjuge/companheiro(a) em outro regime de " +
                    "previdência social (declaração em anexo)";


    /*
    Métodos
     */

    /*
    testa se há um arquivo TextoRecorrente.dpch e cria o mesmo em caso negativo
     */
    public static void iniciaTR(String nomeArquivo, List<String> lista) {
        File tRfolder = new File(System.getProperty("user.home"), "Despachito");
        Path pathTRfolder = Paths.get(tRfolder.getAbsolutePath());
        if (Files.notExists(pathTRfolder)) {
            tRfolder.mkdir();
        }
        File tR = new File(tRfolder, nomeArquivo);
        Path pathTR = Paths.get(tR.getAbsolutePath());
        if (Files.notExists(pathTR)) {
            try {
                tR.createNewFile();
                if (nomeArquivo.equals("textoRecorrente.dpch")) {
                    textoRecorrente.add(tR1);
                    textoRecorrente.add(tR2);
                    textoRecorrente.add(tR3);
                    textoRecorrente.add(tR4);
                    textoRecorrente.add(tR5);
                    textoRecorrente.add(tR6);
                } else {
                    exigenciaRecorrente.add(eR1);
                    exigenciaRecorrente.add(eR2);
                    exigenciaRecorrente.add(eR3);
                    exigenciaRecorrente.add(eR4);
                    exigenciaRecorrente.add(eR5);
                    exigenciaRecorrente.add(eR6);
                    exigenciaRecorrente.add(eR7);
                }
                try {
                    FileWriter f = new FileWriter(tR);
                    for (String str: lista) {
                        if (!str.trim().isEmpty()) {
                            f.write(str + divisor);
                        }
                    }
                    f.close();
                } catch (Exception ex) {}
            } catch (Exception ex) {}
        }
        else //Passa o conteúdo do arquivo de TextoRecorrente para a lista textoRecorrente
        try {
            Scanner ler = new Scanner(tR);
            ler.useDelimiter(TextoRecorrente.divisor);
            while (ler.hasNext()) {
                lista.add(ler.next());
            }
            ler.close();
        } catch (Exception ex) {}
    }

    /*
    grupo de controles para texto recorrente
     */
    public VBox fazerblocoTR(int index, Controller cont, VBox vBoxParent, String nomeArquivo, List<String> lista) {
        AtomicBoolean alteraTR = new AtomicBoolean(false);

        VBox blocoTR = new VBox();

        TextArea areaTexto = new TextArea();
        areaTexto.setWrapText(true);
        areaTexto.setEditable(false);
        areaTexto.setPrefHeight(100);
        if (index < lista.size()) {
            areaTexto.setText(lista.get(index));
        } else {
            lista.add(" ");
            areaTexto.setText(lista.get(index));
        }

        /*
        Caixa com os botões
         */
        HBox h = new HBox();
        h.setAlignment(Pos.TOP_RIGHT);

        /*
        Exclui o bloco de texto recorrente e tira texto da lista e do arquivo database de lista
         */
        Button bExcluir = new Button("Excluir");
        bExcluir.setOnAction(e -> {
            lista.remove(index);
            vBoxParent.getChildren().remove(blocoTR);
            try {
                File tRfolder = new File(System.getProperty("user.home"), "Despachito");
                FileWriter f = new FileWriter(new File(tRfolder, nomeArquivo));
                for (String str: lista) {
                    if (!str.trim().isEmpty()) {
                        f.write(str + divisor);
                    }
                }
                f.close();
            } catch (Exception ex) {}
        });

        /*
        Chama popup para editar o texto recorrente, atualizando a lista e o campo correspondente em caso de alteração
         */
        Button bEditar = new Button("Editar");
        bEditar.setOnAction(e -> {
            alteraTR.set(this.popupEditar(index, lista));
            if (alteraTR.get()) {
                areaTexto.setText(lista.get(index));
                try {
                    File tRfolder = new File(System.getProperty("user.home"), "Despachito");
                    FileWriter f = new FileWriter(new File(tRfolder, nomeArquivo));
                    for (String str: lista) {
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

        h.getChildren().addAll(bExcluir, bEditar, bAdicionar);


        blocoTR.getChildren().addAll(h, areaTexto);

        return blocoTR;
    }

    /*
    Popup de edição do texto recorrente
     */
    public boolean popupEditar(int index, List<String> lista){

        AtomicBoolean edicaoTR = new AtomicBoolean(false);

        Stage janelinha = new Stage();

        janelinha.initModality(Modality.APPLICATION_MODAL);
        janelinha.setTitle("Edição de texto recorrente");
        janelinha.setWidth(1000);
        janelinha.getIcons().add(new Image(Main.class.getResourceAsStream("Icone.png")));

        HBox hB = new HBox();
        hB.setAlignment(Pos.TOP_RIGHT);
        hB.setSpacing(15);

        TextArea areaTR = new TextArea();
        areaTR.setWrapText(true);
        areaTR.setPrefHeight(400);
        if (index < lista.size()) {
            areaTR.setText(lista.get(index));
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
            if (index < lista.size()) {
                if (!areaTR.getText().equals(lista.get(index))) {
                    lista.set(index, areaTR.getText());
                    edicaoTR.set(true);
                }
            } else {
                lista.add(areaTR.getText());
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
