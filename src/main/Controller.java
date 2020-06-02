package main;

import javafx.fxml.Initializable;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class Controller implements Initializable {

    Segurado cSegur = new Segurado();
    GeradorDespachoApIdade cDespI = new GeradorDespachoApIdade();
    GeradorDespachoApTC cDespTC = new GeradorDespachoApTC();
    TextoRecorrente cTextoRec = new TextoRecorrente();

    public Button config = new Button();
    public Button info = new Button();

    public Button botaoInput = new Button();
    public Button botaoGerarDespacho = new Button();

    public Button botaoCopy = new Button();
    public VBox painelTextoRecorrente = new VBox();
    public Button botaoNovoTR = new Button();

    public TextArea caixaDespacho = new TextArea();

    /*
    Ação do botão de abrir tela de configurações
     */
    public void abrirConfig() {
        Config.janelaConfig();
    }

    /*
    Ação do botão de abrir tela de configurações
     */
    public void abrirInfo() {
        Popups.popupInfo();
    }

    /*
    Ação do botão de adicionar bloco de texto recorrente
     */
    public void acaoBotaoNovoTR() {
        VBox novoBloco = cTextoRec.fazerblocoTR(TextoRecorrente.textoRecorrente.size(), this);
        painelTextoRecorrente.getChildren().add(novoBloco);
        novoBloco.setVisible(true);
        botaoNovoTR.toFront();
    }

    /*
    Botão de importação de arquivo faz inicia os seguintes processos quando pressionado:
    - abre uma janela para seleção do arquivo de extrato do qual serão extraídos os dados
    - faz o parse de todas as variáveis necessárias à constução do despacho
     */
    public void acaoInput() {
        cSegur = new Segurado();
        importaArquivo();
        if (cSegur.getExtrato()!=null) {
            String codEspecieNB = cSegur.parseCodEspecie();
            if (codEspecieNB.equals("41")) {
                cSegur.setCodEspecieBeneficio(codEspecieNB);
                parseAtributosIniciais();
                cDespI.parseAtributosR1(cSegur);
                if (cSegur.isFiliaAteEC()) {
                    cDespI.parseAtributosR2(cSegur);
                    cDespI.parseAtributosR3(cSegur);
                    parseAtributosFinais();
                    this.botaoGerarDespacho.setDisable(false);
                }
            } else if (codEspecieNB.equals("42")) {
                cSegur.setCodEspecieBeneficio(codEspecieNB);
                parseAtributosIniciais();
                cDespTC.parseAtributosR1(cSegur);
                if (cSegur.isFiliaAteEC()) {
                    cDespTC.parseAtributosR4(cSegur);
                    cDespTC.parseAtributosR5(cSegur);
                    cDespTC.parseAtributosR6(cSegur);
                    cDespTC.parseAtributosR7(cSegur);
                    cDespTC.parseAtributosR8(cSegur);
                    cDespTC.parseAtributosR9(cSegur);
                    parseAtributosFinais();
                    this.botaoGerarDespacho.setDisable(false);
                }
            } else {
                Popups.popupAlerta("Alerta", "Arquivo selecionado não é um extrato de " +
                        "aposentadoria por idade ou tempo de contribuição e não é suportado pelo programa");
            }
        }
    }

    /*
    Constrói o despacho e coloca na caixa de texto principal
    testa a espécie de benefício e constrói o tipo de despacho adequado
     */
    public void acaoGerarDespacho() {

        //todo adaptar a impressao de aposentadoria por tempo de contribuição

        cDespI = new GeradorDespachoApIdade();
        cDespTC = new GeradorDespachoApTC();
        constroiParteInicial();
        int quantParagrafos = cSegur.countRegraAnaliseDireito();
        int contador = 1;
        while (contador < quantParagrafos) {
            constroiParagrafoAnaliseDireito(contador);
            contador++;
        }
        contador = 0;
        constroiParagrafoAnaliseDireito(contador);

        constroiParteFinal();

        if (cSegur.getCodEspecieBeneficio().equals("41")) {
            cDespI.setStringDespachoCompletoIdade(String.join("\n", cDespI.getDespachoCompletoIdade()));
            caixaDespacho.setText(cDespI.getStringDespachoCompletoIdade());
        } else {
            cDespTC.setStringDespachoCompletoTC(String.join("\n", cDespTC.getDespachoCompletoTC()));
            caixaDespacho.setText(cDespTC.getStringDespachoCompletoTC());
        }
        this.botaoCopy.setDisable(false);
    }



    /*
    Copia a despacho para a área de transferência
     */
    public void acaoCopy() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent pegaTexto = new ClipboardContent();
        pegaTexto.putString(caixaDespacho.getText());
        clipboard.setContent(pegaTexto);
    }

    /*
    Chama método lerExtrato e seta
     */
    public void importaArquivo() {
        String extrato = cSegur.parseExtrato();
        cSegur.setExtrato(extrato);
    }

    /*
    Inicia parsing de atributos de dados iniciais do segurado
     */
    public void parseAtributosIniciais() {

        String especieNB = cSegur.transfEspecieBeneficio();
        cSegur.setEspecieBeneficio(especieNB);

        String nome = cSegur.parseNome();
        cSegur.setNome(nome);

        String nb = cSegur.parseNB();
        cSegur.setNB(nb);


        String sexo = cSegur.parseSexo();
        cSegur.setSexo(sexo);

        char artGenero = cSegur.parseArtGenero();
        cSegur.setArtGenero(artGenero);

        String nascString = cSegur.parseNascString();
        cSegur.setStringDataNasc(nascString);

        Date nascDate = cSegur.nascToDate();
        cSegur.setDataNasc(nascDate);

        String DERString = cSegur.lerDerString();
        cSegur.setStringDER(DERString);

        Date DERDate = cSegur.DERtoDate();
        cSegur.setDER(DERDate);

        String[] arrayIdadeDER = cSegur.parseIdadeDER();
        cSegur.setIdadeDER(arrayIdadeDER);

        String filiaString = cSegur.parseFiliaString();
        cSegur.setStringDataFiliaAs(filiaString);

        Date filiaDate = cSegur.FiliaToDate();
        cSegur.setDataFilia(filiaDate);

        boolean filiaAteEC = cSegur.testaFiliaAteEC();
        cSegur.setFiliaAteEC(filiaAteEC);
        if (cSegur.isFiliaAteEC()) {
            cSegur.setAntesDepoisEC("antes da");
        } else {
            cSegur.setAntesDepoisEC("depois da");
        }

        if (cSegur.isFiliaAteEC()) {
            cSegur.setAtendeNaoAtEC("atende");
        } else {
            cSegur.setAtendeNaoAtEC("não atende");
        }
    }





    public void parseAtributosFinais() {
        String RecDireitoFinal = cSegur.parseDireitoAposFinal();
        if (cSegur.getCodEspecieBeneficio().equals("41")) {
            cSegur.setRecDireitoFinalIdade(RecDireitoFinal);
        } else {
            cSegur.setRecDireitoFinalTC(RecDireitoFinal);
        }
    }


    /*
    Constrói a parte inicial do despacho - dados básicos do segurado
     */
    public void constroiParteInicial(){
        if (cSegur.getCodEspecieBeneficio().equals("41")) {
            cDespI.addDespachoCompletoIdade(cDespI.escreverParte1(cSegur));
        } else {
            cDespTC.addDespachoCompletoTC(cDespTC.escreverParte1(cSegur));
        }
    }

    /*
    Constrói parágrafo referente às regras de análise de direito no despacho
     */
    public void constroiParagrafoAnaliseDireito(int index) {
        if (cSegur.getCodEspecieBeneficio().equals("41")) {
            cDespI.addDespachoCompletoIdade(cDespI.escreverParagrafoAnaliseDireito(cSegur, index));
        } else {
            cDespTC.addDespachoCompletoTC(cDespTC.escreverParagrafoAnaliseDireito(cSegur, index));
        }
    }

    /*
    Constrói a parte inicial do despacho - reconhecimento de direito à aposentadoria por qualquer uma das regras
     */
    public void constroiParteFinal() {
        if (cSegur.getCodEspecieBeneficio().equals("41")) {
            cDespI.addDespachoCompletoIdade(cDespI.escreverParteFinal(cSegur));
        } else {
            cDespTC.addDespachoCompletoTC(cDespTC.escreverParteFinal(cSegur));
        }
    }

    /*
    Inicializador do programa. Executa código antes de abrir a GUI
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TextoRecorrente.iniciaTR();
        Config.criaListaOL();

        /*
        Cria os blocos de texto recorrente na GUI com o conteúdo da lista textoRecorrente
         */
        for (int i = 0; i< TextoRecorrente.textoRecorrente.size(); i++) {
            VBox novoBloco = cTextoRec.fazerblocoTR(i, this);
            painelTextoRecorrente.getChildren().add(novoBloco);
            novoBloco.setVisible(true);
            botaoNovoTR.toFront();
        }

    }
}


