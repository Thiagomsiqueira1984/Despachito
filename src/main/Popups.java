package main;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.awt.*;
import java.net.URI;
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
        janelinha.getIcons().add(new Image(Main.class.getResourceAsStream("Icone.png")));

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
        janelinha.getIcons().add(new Image(Main.class.getResourceAsStream("Icone.png")));

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
        janelinha.setMaxWidth(1000);
        janelinha.getIcons().add(new Image(Main.class.getResourceAsStream("Icone.png")));

        Label label1 = new Label();
        label1.setText("Despachito versão: 1.8.10");
        label1.setTextFill(Color.valueOf("#137ccd"));
        label1.setStyle("-fx-font-weight: bold");
        Label label2 = new Label();
        label2.setTextFill(Color.valueOf("#137ccd"));
        label2.setStyle("-fx-font-weight: bold");
        label2.setText("Desenvolvedor: Thiago de Morais Siqueira");

        Label labPag = new Label("Mais informações e video tutorial: ");
        labPag.setTextFill(Color.valueOf("#137ccd"));
        labPag.setStyle("-fx-font-weight: bold");

        Hyperlink pagDespachito = new Hyperlink("https://thiagomsiqueira1984.github.io/Pagina-Despachito/");
        pagDespachito.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://thiagomsiqueira1984.github.io/Pagina-Despachito/"));
            } catch (Exception ex) {}
        });

        HBox pag = new HBox();
        pag.getChildren().addAll(labPag, pagDespachito);
        pag.setAlignment(Pos.CENTER);

        Label ajuda = new Label();
        String textoAjuda = "Este programa foi desenvolvido com o intuito de importar arquivos de extrato de tempo de contribuição do programa PRISMA em formato .txt, extrair os dados e gerar automaticamente um despacho de conclusão do processo com base nestes dados.\n" +
                "\n" +
                "Antes de iniciar a utilização, verifique na caixa de seleção acima da área de despacho central se a OL atual correta está selecionada. Se for necessária edição ou inclusão de OL, isto pode ser feito no botão de configurações no canto superior esquerdo. No menu de configurações também é possível configurar a pasta padrão onde o programa irá procurar os arquivos de extrato de tempo de contribuição.\n" +
                "\n" +
                "Há duas maneiras de gerar o arquivo de extrato de tempo de contribuição necessário para geração do despacho:\n" +
                "\n" +
                "A primeira e mais fácil é feita de forma automática pelo programa PrismaPDF ao imprimir um extrato para o formato PDF. Os arquivos txt gerados são salvos na pasta cnilinha onde o programa PrismaPDF foi instalado.\n" +
                "\n" +
                "A segunda maneira, para usuários que não usem o PrismaPDF ou que não tenham os arquivos de extrato gerados automaticamente por outros motivos é utilizando a opção de captura do PRISMA. Para capturar o extrato de tempo contribuição do PRISMA (AccuTerm versão 7) em formato de texto é necessário clicar no menu Tools > Capture, marcar as opções \"capture to Clipboard\" e \"Printer Data\" e clicar em \"Start Capture\", digitar a opção \"11 Extrato T.C.\" dentro do benefício e, após concluída a emissão do extrato, clicar novamente no menu Tools > Capture e clicar em \"End Capture\". Essa operação copia o extrato em formato de texto para a área de transferência. Abrir o bloco de notas do Windows, colar o texto copiado e salvar o arquivo na pasta desejada. Importante: a configuração da impressora no PRISMA não pode estar na opção \"Documento PDF\". Qualquer impressora do tipo escrava deve funcionar (LASERJET, HP-DESKJET, entre outras).\n" +
                "\n" +
                "Para importar um arquivo extrato de tempo de contribuição, clique no botão \"Importar arquivo de extrato\" e selecione o arquivo que deseja importar. Depois clique no botão \"Gerar despacho\".\n" +
                "\n" +
                "O despacho pode ser editado na caixa central de despacho. Também é possível incluir textos recorrentes que constam no painel \"Texto recorrente\" na parte esquerda. Estes textos podem ser editados e também é possível incluir novas caixas com textos personalizados no botão \"+\" abaixo das caixas existentes.\n" +
                "\n" +
                "O programa foi desenvolvido para geração de despachos de aposentadoria por idade urbana e aposentadoria por tempo de contribuição. Não suporta outras espécies de benfício.\n" +
                "\n" +
                "Ao final da geração do despacho de aposentadoria por idade, é feita uma pré análise quanto a existência de requisitos mínimos para análise de aposentadoria por tempo de contribuição (carência e tempo de contribuição). Caso seja constatado que há os requisitos mínimos, será oportunizada a importação de novo arquivo com extrato de aposentadoria por tempo de contribuição, com subsequente geração de despacho com os dados da análise quanto a esta espécie. O mesmo ocorre em análises de aposentadoria por tempo de contribuição, com pré análise dos requisitos para aposentadoria por idade (carência e idade).\n" +
                "\n" +
                "Atenção: o uso desta ferramenta não dispensa o servidor da realização da devida análise do processo, conferência dos dados do despacho com o extrato de tempo de contribuição utilizado e com os dados do processo." +
                "\n";
        ajuda.setText(textoAjuda);
        ajuda.setTextAlignment(TextAlignment.JUSTIFY);
        ajuda.setMaxWidth(950);
        ajuda.setWrapText(true);

        Button botao = new Button("Fechar");
        botao.setPrefWidth(75);
        botao.setOnAction(e -> janelinha.close());

        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setContent(ajuda);

        VBox layout = new VBox();
        layout.getChildren().addAll(label1, label2, pag, sp, botao);
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
        janelinha.getIcons().add(new Image(Main.class.getResourceAsStream("Icone.png")));

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
                novoOL.set(campoCodOL.getText() + " - " + campoNomeOL.getText());
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
