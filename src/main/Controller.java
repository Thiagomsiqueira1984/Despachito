package main;

import java.util.Date;

import javafx.scene.control.Button;


public class Controller {

    Segurado cSegur = new Segurado();
    GeradorDespacho cDesp = new GeradorDespacho();

    public Button botaoInput = new Button();
    public Button botaoGerarDespacho = new Button();

    /*
    Botão de importação de arquivo faz inicia os seguintes processos quando pressionado:
    - abre uma janela para seleção do arquivo de extrato do qual serão extraídos os dados
    - faz o parse de todas as variáveis necessárias à constução do despacho
     */
    public void acaoInput() {
        importaArquivo();
        parseAtributosIniciais();
        parseDadosR1();
        if (cSegur.isFiliaAteEC()) {
        parseDadosR2();

        }
    }

    public void acaoGerarDespacho() {
        constroiParteInicial();
        int contador = 0;
        while (contador<=cSegur.getR1()+cSegur.getR2()+cSegur.getR3()) {
         constroiParagrafoAnaliseDireito(contador);
         contador++;
        }
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
    public void parseDadosR1() {

        cSegur.setR1(0);

        String regra = cSegur.retornaNomeRegraAnaliseDireito(cSegur.getR1());
        cSegur.addRegraAnaliseDireito(regra);

        String stringDataBase = cSegur.parseDataBase(cSegur.getR1());
        cSegur.addStringDataBase(stringDataBase);

        Date dateDatabase = cSegur.converteDataBaseDate(cSegur.getR1());
        cSegur.addDateDataBase(dateDatabase);

        if (cSegur.getSexo().equals("masculino")) {
            cSegur.addIdadeExigida("65 anos");
        } else {
            cSegur.addIdadeExigida("62 anos");
        }

        cSegur.addCarenciaExigida("180");

        String[] tempCompExigido = {"20", "00", "00"};
        cSegur.addTempCompExigido(tempCompExigido);

        String[] idadeEfetiva = cSegur.parseIdade(cSegur.getR1());
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
    public void parseDadosR2() {

        cSegur.setR2(1);

        String regra = cSegur.retornaNomeRegraAnaliseDireito(cSegur.getR2());
        cSegur.addRegraAnaliseDireito(regra);

        String stringDataBase = cSegur.parseDataBase(cSegur.getR2());
        cSegur.addStringDataBase(stringDataBase);

        Date dateDatabase = cSegur.converteDataBaseDate(cSegur.getR2());
        cSegur.addDateDataBase(dateDatabase);

        if (cSegur.getSexo().equals("masculino")) {
            cSegur.addIdadeExigida("65 anos");
        } else {
            cSegur.addIdadeExigida("60 anos");
        }

        cSegur.addCarenciaExigida("180");

        String[] tempCompExigido = {"00", "00", "00"};
        cSegur.addTempCompExigido(tempCompExigido);

        String[] idadeEfetiva = cSegur.parseIdade(cSegur.getR2());
        cSegur.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = cSegur.parseCarenciaEfetiva(cSegur.getR2());
        cSegur.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = cSegur.parseTempCompEfetivo(cSegur.getR2());
        cSegur.addTempCompEfetivo(tempCompEfetivo);

        String recDireitoDataBase = cSegur.parseRecDireitoDataBase(cSegur.getR2());
        cSegur.addRecDireitoDataBase(recDireitoDataBase);

    }

    /*
    Parse de valores para parte nível r2 - regra de direito adquirido antes da EC 103/2019
     */
    public void parseDadosR3() {

        cSegur.setR3(2);

        String regra = cSegur.retornaNomeRegraAnaliseDireito(cSegur.getR3());
        cSegur.addRegraAnaliseDireito(regra);

        String stringDataBase = cSegur.parseDataBase(cSegur.getR3());
        cSegur.addStringDataBase(stringDataBase);

        Date dateDatabase = cSegur.converteDataBaseDate(cSegur.getR3());
        cSegur.addDateDataBase(dateDatabase);

        if (cSegur.getSexo().equals("masculino")) {
            cSegur.addIdadeExigida("65 anos");
        } else {
            cSegur.addIdadeExigida("FAZER MÉTODO DE PARSE DE IDADE EXIGIDA");
        }

        cSegur.addCarenciaExigida("180");

        String[] tempCompExigido = {"15", "00", "00"};
        cSegur.addTempCompExigido(tempCompExigido);

        String[] idadeEfetiva = cSegur.parseIdade(cSegur.getR3());
        cSegur.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = cSegur.parseCarenciaEfetiva(cSegur.getR3());
        cSegur.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = cSegur.parseTempCompEfetivo(cSegur.getR3());
        cSegur.addTempCompEfetivo(tempCompEfetivo);

        String recDireitoDataBase = cSegur.parseRecDireitoDataBase(cSegur.getR3());
        cSegur.addRecDireitoDataBase(recDireitoDataBase);

    }

    /*
    Constrói a parte inicial do despacho - dados básicos do segurado
     */
    public void constroiParteInicial(){
        System.out.println(cDesp.escreverParte1(cSegur));
    }

    /*
    Constrói parágrafo referente à análise de direito no despacho
     */
    public void constroiParagrafoAnaliseDireito(int index) {
        System.out.println(cDesp.escreverParagrafoAnaliseDireito(cSegur, index));
    }

    }

