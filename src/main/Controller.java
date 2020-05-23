package main;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.Button;


public class Controller {

    Segurado cSegur = new Segurado();
    GeradorDespacho cDesp = new GeradorDespacho();

    public Button botaoInput = new Button();
    public Button botaoGerarDespacho = new Button();
    public Button botaoCopy = new Button();

    /*
    Botão de importação de arquivo faz inicia os seguintes processos quando pressionado:
    - abre uma janela para seleção do arquivo de extrato do qual serão extraídos os dados
    - faz o parse de todas as variáveis necessárias à constução do despacho
     */
    public void acaoInput() {
        cSegur = new Segurado();
        importaArquivo();
        parseAtributosIniciais();
        parseAtributosR1();
        if (cSegur.isFiliaAteEC()) {
        parseAtributosR2();
        parseAtributosR3();
        parseAtributosFinais();
        }
    }

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
        cDesp.setStringDespachoFinal(String.join("\n", cDesp.getDespachoFinal()));
        System.out.println(cDesp.getStringDespachoFinal());
    }

    public void acaoCopy() {
        String pegaTexto = cDesp.getStringDespachoFinal();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection copiarDespacho = new StringSelection(pegaTexto);
        clipboard.setContents(copiarDespacho, null);
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


        String nome = cSegur.parseNome();
        cSegur.setNome(nome);

        String sexo = cSegur.parseSexo();
        cSegur.setSexo(sexo);

        char artGenero = cSegur.parseArtGenero();
        cSegur.setArtGenero(artGenero);

        String nascString = cSegur.parseNascString();
        cSegur.setDataNascAsString(nascString);

        Date nascDate = cSegur.nascToDate();
        cSegur.setDataNasc(nascDate);

        String DERString = cSegur.lerDerString();
        cSegur.setDERasString(DERString);

        Date DERDate = cSegur.DERtoDate();
        cSegur.setDER(DERDate);

        String[] arrayIdadeDER = cSegur.parseIdadeDER();
        cSegur.setIdadeDER(arrayIdadeDER);

        String filiaString = cSegur.parseFiliaString();
        cSegur.setDataFiliaAsString(filiaString);

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

        cSegur.addCarenciaExigida("180");

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

        cSegur.addCarenciaExigida("180");

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

                 cSegur.addCarenciaExigida("180");

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
        cDesp.addDespachoFinal(cDesp.escreverParte1(cSegur));
    }

    /*
    Constrói parágrafo referente às regras de análise de direito no despacho
     */
    public void constroiParagrafoAnaliseDireito(int index) {
        cDesp.addDespachoFinal(cDesp.escreverParagrafoAnaliseDireito(cSegur, index));
    }

    /*
    Constrói a parte inicial do despacho - reconhecimento de direito à aposentadoria por qualquer uma das regras
     */
    public void constroiParteFinal() {
        cDesp.addDespachoFinal(cDesp.escreverParteFinal(cSegur));
    }

    }

