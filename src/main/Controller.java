package main;

import javafx.fxml.Initializable;
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

    public Button botaoCopy = new Button();
    public VBox painelTextoRecorrente = new VBox();
    public Button botaoNovoTR = new Button();

    public TextArea caixaDespacho = new TextArea();

    /*
    A��o do bot�o de adicionar bloco de texto recorrente
     */
    public void acaoBotaoNovoTR() {
        VBox novoBloco = cTextoRec.fazerblocoTR(TextoRecorrente.textoRecorrente.size(), this);
        painelTextoRecorrente.getChildren().add(novoBloco);
        novoBloco.setVisible(true);
        botaoNovoTR.toFront();
    }

    /*
    Bot�o de importa��o de arquivo faz inicia os seguintes processos quando pressionado:
    - abre uma janela para sele��o do arquivo de extrato do qual ser�o extra�dos os dados
    - faz o parse de todas as vari�veis necess�rias � constu��o do despacho
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
            } else {
                Popups.popup1("Alerta","Arquivo selecionado n�o � um extrato de " +
                    "aposentadoria por idade ou tempo de contribui��o e n�o � suportado pelo programa");}
        }
    }

    /*
    Constr�i o despacho e coloca na caixa de texto principal
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
    Copia a despacho para a �rea de transfer�ncia
     */
    public void acaoCopy() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent pegaTexto = new ClipboardContent();
        pegaTexto.putString(caixaDespacho.getText());
        clipboard.setContent(pegaTexto);
    }

    /*
    Chama m�todo lerExtrato e seta
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
            cSegur.setAtendeNaoAtEC("n�o atende");
        }
    }

    /*
    Parse de valores para parte n�vel r1 - regra de apoentadoria programada art. 19 da EC 103/2019
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
    Parse de valores para parte n�vel r2 - regra de direito adquirido antes da EC 103/2019
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
    Parse de valores para parte n�vel r3 - Regra transitoria do Art.18 da EC 103/2019
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
    Constr�i a parte inicial do despacho - dados b�sicos do segurado
     */
    public void constroiParteInicial(){
        cDesp.addDespachoCompleto(cDesp.escreverParte1(cSegur));
    }

    /*
    Constr�i par�grafo referente �s regras de an�lise de direito no despacho
     */
    public void constroiParagrafoAnaliseDireito(int index) {
        cDesp.addDespachoCompleto(cDesp.escreverParagrafoAnaliseDireito(cSegur, index));
    }

    /*
    Constr�i a parte inicial do despacho - reconhecimento de direito � aposentadoria por qualquer uma das regras
     */
    public void constroiParteFinal() {
        cDesp.addDespachoCompleto(cDesp.escreverParteFinal(cSegur));
    }

    /*
    Inicializador do programa. Executa c�digo antes de abrir a GUI
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cTextoRec.iniciaTR();

        /*
        Cria os blocos de texto recorrente com o conte�do da lista textoRecorrente
         */
        for (int i = 0; i< TextoRecorrente.textoRecorrente.size(); i++) {
            VBox novoBloco = cTextoRec.fazerblocoTR(i, this);
            painelTextoRecorrente.getChildren().add(novoBloco);
            novoBloco.setVisible(true);
            botaoNovoTR.toFront();
        }

    }
}


