package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class Controller implements Initializable {

    Segurado cSegur = new Segurado();
    GeradorDespacho cDesp = new GeradorDespacho();
    TextoRecorrente cTextoRec = new TextoRecorrente();

    public Button botaoInput = new Button();
    public Button botaoGerarDespacho = new Button();
    public TextArea caixaDespacho = new TextArea();
    public Button botaoCopy = new Button();
    public TextArea caixaTR1 = new TextArea();
    public VBox painelTextoRecorrente = new VBox();
    public Button botaoNovoTR = new Button();

    /*
    Ação do botão de adicionar bloco de texto recorrente
     */
    public void acaoBotaoNovoTR() {
        VBox novoBloco = cTextoRec.fazerblocoTR(TextoRecorrente.textoRecorrente.size()+1);
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
            if (codEspecieNB.matches("41|42")) {
                cSegur.setCodEspecieBeneficio(codEspecieNB);
                parseAtributosIniciais();
                parseAtributosR1();
                if (cSegur.isFiliaAteEC()) {
                    parseAtributosR2();
                    parseAtributosR3();
                    parseAtributosFinais();
                    this.botaoGerarDespacho.setDisable(false);
                }
            } else {Popup.popup("Alerta","Arquivo selecionado não é um extrato de " +
                    "aposentadoria por idade ou tempo de contribuição e não é suportado pelo programa");}
        }
    }

    /*
    Constrói o despacho e coloca na caixa de texto principal
     */
    public void acaoGerarDespacho() {
        cDesp = new GeradorDespacho();
        constroiParteInicial();
        int contador = 1;
        while (contador<=cSegur.getR1()+cSegur.getR2()+cSegur.getR3()) {
         constroiParagrafoAnaliseDireito(contador);
            if (contador == cSegur.getR1()+cSegur.getR2()+cSegur.getR3()) {
                contador = 0;
                constroiParagrafoAnaliseDireito(contador);
                break;
            }
         contador++;
        }
        constroiParteFinal();
        cDesp.setStringDespachoCompleto(String.join("\n", cDesp.getDespachoCompleto()));
        caixaDespacho.setText(cDesp.getStringDespachoCompleto());
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

    /*
    Parse de valores para parte nível r1 - regra de apoentadoria programada art. 19 da EC 103/2019
    */
    public void parseAtributosR1() {

        cSegur.setR1(0);

        String regra = cSegur.retornaNomeRegraAnaliseDireito(cSegur.getR1());
        cSegur.addRegraAnaliseDireito(regra);

        String stringDataBase = cSegur.parseDataBase(cSegur.getR1());
        cSegur.addStringDataBase(stringDataBase);

        Date dateDatabase = cSegur.converteDataBaseDate(cSegur.getR1());
        cSegur.addDateDataBase(dateDatabase);

        String idadeExigida = cSegur.parseIdadeExigida(cSegur.getR1());
        cSegur.addIdadeExigida(idadeExigida);

        String carenciaExigida = cSegur.parseCarenciaExigida(cSegur.getR1());
        cSegur.addCarenciaExigida(carenciaExigida);

        String[] tempCompExigido = {"20", "00", "00"};
        cSegur.addTempCompExigido(tempCompExigido);

        String[] idadeEfetiva = cSegur.parseIdadeEfetiva(cSegur.getR1());
        cSegur.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = cSegur.parseCarenciaEfetiva(cSegur.getR1());
        cSegur.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = cSegur.parseTempCompEfetivo(cSegur.getR1());
        cSegur.addTempCompEfetivo(tempCompEfetivo);

        String recDireitoDataBase = cSegur.parseRecDireitoDataBase(cSegur.getR1());
        cSegur.addRecDireitoDataBase(recDireitoDataBase);

    }

    /*
    Parse de valores para parte nível r2 - regra de direito adquirido antes da EC 103/2019
     */
    public void parseAtributosR2() {

        cSegur.setR2(1);

        String regra = cSegur.retornaNomeRegraAnaliseDireito(cSegur.getR2());
        cSegur.addRegraAnaliseDireito(regra);

        String stringDataBase = cSegur.parseDataBase(cSegur.getR2());
        cSegur.addStringDataBase(stringDataBase);

        Date dateDatabase = cSegur.converteDataBaseDate(cSegur.getR2());
        cSegur.addDateDataBase(dateDatabase);

        String idadeExigida = cSegur.parseIdadeExigida(cSegur.getR2());
        cSegur.addIdadeExigida(idadeExigida);

        String carenciaExigida = cSegur.parseCarenciaExigida(cSegur.getR2());
        cSegur.addCarenciaExigida(carenciaExigida);

        String[] tempCompExigido = {"00", "00", "00"};
        cSegur.addTempCompExigido(tempCompExigido);

        String[] idadeEfetiva = cSegur.parseIdadeEfetiva(cSegur.getR2());
        cSegur.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = cSegur.parseCarenciaEfetiva(cSegur.getR2());
        cSegur.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = cSegur.parseTempCompEfetivo(cSegur.getR2());
        cSegur.addTempCompEfetivo(tempCompEfetivo);

        String recDireitoDataBase = cSegur.parseRecDireitoDataBase(cSegur.getR2());
        cSegur.addRecDireitoDataBase(recDireitoDataBase);

    }

    /*
    Parse de valores para parte nível r3 - Regra transitoria do Art.18 da EC 103/2019
     */
    public void parseAtributosR3() {

        cSegur.setR3(2);

         do {
             String regra = cSegur.retornaNomeRegraAnaliseDireito(cSegur.getR3());
             cSegur.addRegraAnaliseDireito(regra);

             String stringDataBase = cSegur.parseDataBase(cSegur.getR3());
             cSegur.addStringDataBase(stringDataBase);

             Date dateDatabase = cSegur.converteDataBaseDate(cSegur.getR3());
             cSegur.addDateDataBase(dateDatabase);

             String idadeExigida = cSegur.parseIdadeExigida(cSegur.getR3());
             cSegur.addIdadeExigida(idadeExigida);

             String carenciaExigida = cSegur.parseCarenciaExigida(cSegur.getR3());
             cSegur.addCarenciaExigida(carenciaExigida);

             String[] tempCompExigido = {"15", "00", "00"};
             cSegur.addTempCompExigido(tempCompExigido);

             String[] idadeEfetiva = cSegur.parseIdadeEfetiva(cSegur.getR3());
             cSegur.addIdadeEfetiva(idadeEfetiva);

             String carenciaEfetiva = cSegur.parseCarenciaEfetiva(cSegur.getR3());
             cSegur.addCarenciaEfetiva(carenciaEfetiva);

             String[] tempCompEfetivo = cSegur.parseTempCompEfetivo(cSegur.getR3());
             cSegur.addTempCompEfetivo(tempCompEfetivo);

             String recDireitoDataBase = cSegur.parseRecDireitoDataBase(cSegur.getR3());
             cSegur.addRecDireitoDataBase(recDireitoDataBase);

             if (cSegur.getDateDataBase(cSegur.getR3()).compareTo(cSegur.getDER()) < 0) {
                 cSegur.setR3(cSegur.getR3() + 1);
             } else {break;}

         } while (cSegur.getDateDataBase(cSegur.getR3() - 1).compareTo(cSegur.getDER())<0);

        cSegur.setR3(cSegur.getR3() - 1);
     }

    public void parseAtributosFinais() {
        String RecDireitoFinal = cSegur.parseDireitoAposFinal();
        cSegur.setRecDireitoFinal(RecDireitoFinal);
    }


    /*
    Constrói a parte inicial do despacho - dados básicos do segurado
     */
    public void constroiParteInicial(){
        cDesp.addDespachoCompleto(cDesp.escreverParte1(cSegur));
    }

    /*
    Constrói parágrafo referente às regras de análise de direito no despacho
     */
    public void constroiParagrafoAnaliseDireito(int index) {
        cDesp.addDespachoCompleto(cDesp.escreverParagrafoAnaliseDireito(cSegur, index));
    }

    /*
    Constrói a parte inicial do despacho - reconhecimento de direito à aposentadoria por qualquer uma das regras
     */
    public void constroiParteFinal() {
        cDesp.addDespachoCompleto(cDesp.escreverParteFinal(cSegur));
    }

    /*
    Inicializador do programa. Executa código antes de abrir a GUI
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
        testa se há um arquivo TextoRecorrente.txt e cria o mesmo em caso negativo
         */
        File tR = new File("TextoRecorrente.txt");
        Path pathTR = Paths.get(tR.getAbsolutePath());
        if (Files.notExists(pathTR)) {
            try {
                    tR.createNewFile();
                    Files.write(pathTR, Collections.singleton(TextoRecorrente.tR1), StandardCharsets.UTF_8);
            } catch (Exception ex) {}
        }
        try {
            Scanner ler = new Scanner(tR);
            ler.useDelimiter(TextoRecorrente.divisor);
            while (ler.hasNext()) {
                TextoRecorrente.textoRecorrente.add(ler.next());
            }
            ler.close();
        } catch (Exception ex) {}
        for (int i = 0; i< TextoRecorrente.textoRecorrente.size(); i++) {
            VBox novoBloco = cTextoRec.fazerblocoTR(i);
            painelTextoRecorrente.getChildren().add(novoBloco);
            novoBloco.setVisible(true);
            botaoNovoTR.toFront();
        }

    }
}


