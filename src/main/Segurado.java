package main;

import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class Segurado {

    /*

    Atributos da parte de dados iniciais do segurado

     */

    private String extrato; //Armazena o texto do arquivo de extrato na íntegra
    private String nome; //Nome do segurado
    private String sexo; //Sexo do segurado
    private char artGenero; //Artigo de gênero do segurado
    private Date dataNasc; //Data de nascimento do segurado
    private String dataNascAsString; //Data de nascimento do segurado formatada e como String
    private Date DER; //Data de entrada do requerimento
    private String DERasString;//Data de entrada do requerimento formatada e como String
    private String[] idadeDER; //Idade do segurado na DER
    private Date dataFilia; //Data de filiação ao RGPS
    private String dataFiliaAsString; //Data de filiação ao RGPS formatada e como String
    private boolean filiaAteEC; //main.Segurado filiado até 13/11/2019?
    private String antesDepoisEC; //Informa se a filiação ocorreu antes ou depois de 13/11/2019 em String
    private String atendeNaoAtEC; //Informa se atende requisito de filiação até 13/11/2019 em String

    /*

    Atributos da parte de análise de direito:
    -> os atributos desta parte estão em listas, sendo que cada nível da lista (r1 a r3) corresponde a uma regra de
        análise de direito;
    -> o nível r3 pode ter mais de um parágrafo, no caso de segurada do sexo feminino, caso em que será utilizado um
        contador para buscar as informações;

     */

    private int r1; //Número do índice referente à regra de aposentadoria programada art. 19 da EC 103/2019. Sempre = 0
    private int r2; //Número do índice referente à regra de direito adquirido antes da EC 103/2019
    private int r3; //Número do índice referente à regra transitória art. 18 da EC 103/2019

    private List<String> regraAnaliseDireito = new ArrayList<>(); //Lista com o nome das regras para análise do direito em formato String

    private List<String> stringDataBase = new ArrayList<>(); //Lista com data base para análise do direito em formato String
    private List<Date> dateDataBase = new ArrayList<>(); //Lista com data base para análise do direito em formato Date

    private List<String> idadeExigida = new ArrayList<>(); //Lista contendo as idades exigidas na data base guardadas em String
    private List<String> carenciaExigida = new ArrayList<>(); //Lista contendo as carências exigidas na data base guardadas em String
    private List<String[]> TempCompExigido = new ArrayList<>(); //Lista contendo os tempos de contribuição exigidos na data base guardados em array de String com [0] = anos, [1] = meses e [2] = dias

    private List<String[]> idadeEfetiva = new ArrayList<>(); //Lista contendo as idades efetivas na data base. As idades são guardadas em array de String com [0] = anos, [1] = meses e [2] = dias
    private List<String> carenciaEfetiva = new ArrayList<>(); //Lista contendo as carências efetivas na data base. As carências são guardadas em String
    private List<String[]> TempCompEfetivo = new ArrayList<>(); //Lista contendo os tempos de contribuição efetivos na data base guardados em array de String com [0] = anos, [1] = meses e [2] = dias

    private  List<String> recDireitoDataBase = new ArrayList<>(); //Lista com a afirmação quanto ao reconhecimento do direito na data base guardados como String com "foi reconhecido o direito" ou "não foi reconhecido o direito"

    private  String recDireitoFinal; //Afirmação quanto ao reconhecumento do direito na por qualquer uma das regras como String com "foi reconhecido o direito" ou "não foi reconhecido o direito"

    /*

    Getters e Setters

     */



    public String getExtrato() {
        return extrato;
    }

    public void setExtrato(String extrato) {
        this.extrato = extrato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public char getArtGenero() {
        return artGenero;
    }

    public void setArtGenero(char artGenero) {
        this.artGenero = artGenero;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getDataNascAsString() {
        return dataNascAsString;
    }

    public void setDataNascAsString(String dataNascAsString) {
        this.dataNascAsString = dataNascAsString;
    }

    public Date getDER() {
        return DER;
    }

    public void setDER(Date DER) {
        this.DER = DER;
    }

    public String getDERasString() {
        return DERasString;
    }

    public void setDERasString(String DERasString) {
        this.DERasString = DERasString;
    }

    public String[] getIdadeDER() {
        return idadeDER;
    }

    public void setIdadeDER(String[] idadeDER) {
        this.idadeDER = idadeDER;
    }

    public Date getDataFilia() {
        return dataFilia;
    }

    public void setDataFilia(Date dataFilia) {
        this.dataFilia = dataFilia;
    }

    public String getDataFiliaAsString() {
        return dataFiliaAsString;
    }

    public void setDataFiliaAsString(String dataFiliaAsString) {
        this.dataFiliaAsString = dataFiliaAsString;
    }

    public boolean isFiliaAteEC() {
        return filiaAteEC;
    }

    public void setFiliaAteEC(boolean filiaAteEC) {
        this.filiaAteEC = filiaAteEC;
    }

    public String getAntesDepoisEC() {
        return antesDepoisEC;
    }

    public void setAntesDepoisEC(String antesDepoisEC) {
        this.antesDepoisEC = antesDepoisEC;
    }

    public String getAtendeNaoAtEC() {
        return atendeNaoAtEC;
    }

    public void setAtendeNaoAtEC(String atendeNaoAtEC) {
        this.atendeNaoAtEC = atendeNaoAtEC;
    }



    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public int getR3() {
        return r3;
    }

    public void setR3(int r3) {
        this.r3 = r3;
    }

    public String getRegraAnaliseDireito(int index) {
        return this.regraAnaliseDireito.get(index);
    }

    public void addRegraAnaliseDireito(String regraAnaliseDireito) {
        this.regraAnaliseDireito.add(regraAnaliseDireito);
    }

    public String getStringDataBase(int index) {
        return this.stringDataBase.get(index);
    }

    public void addStringDataBase(String stringDataBase) {
        this.stringDataBase.add(stringDataBase);
    }

    public Date getDateDataBase(int index) {
        return this.dateDataBase.get(index);
    }

    public void addDateDataBase(Date dateDataBase) {
        this.dateDataBase.add(dateDataBase);
    }

    public String getIdadeExigida(int index) {
        return this.idadeExigida.get(index);
    }

    public void addIdadeExigida(String idadeExigida){
        this.idadeExigida.add(idadeExigida);
    }

    public String getCarenciaExigida(int index) {
        return this.carenciaExigida.get(index);
    }

    public void addCarenciaExigida(String carenciaExigida) {
        this.carenciaExigida.add(carenciaExigida);
    }

    public String[] getTempCompExigido(int index) {
        return this.TempCompExigido.get(index);
    }

    public void addTempCompExigido(String[] tempCompExigido) {
        this.TempCompExigido.add(tempCompExigido);
    }

    public String[] getIdadeEfetiva(int index) {
        return this.idadeEfetiva.get(index);
    }

    public void addIdadeEfetiva(String[] idadeEfetiva) {
        this.idadeEfetiva.add(idadeEfetiva);
    }

    public String getCarenciaEfetiva(int index) {
        return this.carenciaEfetiva.get(index);
    }

    public void addCarenciaEfetiva(String carenciaEfetiva) {
        this.carenciaEfetiva.add(carenciaEfetiva);
    }

    public String[] getTempCompEfetivo(int index) {
        return this.TempCompEfetivo.get(index);
    }

    public void addTempCompEfetivo(String[] tempCompEfetivo) {
        this.TempCompEfetivo.add(tempCompEfetivo);
    }

    public String getRecDireitoDataBase(int index) {
        return this.recDireitoDataBase.get(index);
    }

    public void addRecDireitoDataBase(String recDireitoDataBase) {
        this.recDireitoDataBase.add(recDireitoDataBase);
    }

    public String getRecDireitoFinal() {
        return recDireitoFinal;
    }

    public void setRecDireitoFinal(String recDireitoFinal) {
        this.recDireitoFinal = recDireitoFinal;
    }

    /*

    Métodos de parse

     */

    /*
    Parse do arquivo Extrato.txt inteiro como String
     */
    public String parseExtrato(){
        String extrato = "";
        FileChooser seletorArquivo = new FileChooser();
        seletorArquivo.setInitialDirectory(new File("C:\\CNISLINHA"));
        seletorArquivo.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Arquivos de texto", "*.txt"));

        File selectedFile = seletorArquivo.showOpenDialog(null);
            try {
                extrato = new String(Files.readAllBytes(selectedFile.toPath()));
            }
            catch (Exception ex){}
        return extrato;
    }

    /*
    Parse do nome do segurado a partir da String novoSegurado.lerExtrato
     */
    public String parseNome() {
        String nome = this.getExtrato();
        nome = nome.split("SEGURADO....: ")[1];
        nome = nome.split("DATA NASC")[0].trim();
        return nome;
    }

    /*
    Parse do sexo do segurado
     */
    public String parseSexo() {
        String sexo = this.getExtrato();
        sexo = sexo.split("SEXO....: ")[1];
        sexo = sexo.split("RAMO ATIV...:")[0].trim().toLowerCase();
        return sexo;
    }

    /*
    Parse do artigo de gênero do segurado
     */
    public char parseArtGenero() {
        char artGenero;
        if(this.getSexo().equals("masculino")){
            artGenero = 'o';
        }
        else {
            artGenero = 'a';
        }
        return artGenero;
    }

    /*
    Parse da data de nascimento do segurado
     */
    public String parseNascString(){
        String nascString = this.getExtrato();
        nascString = nascString.split("NASC...: ")[1];
        nascString = nascString.split("DAT........:")[0].trim();
        return nascString;
    }

    /*
    Converte a data de nascimento para Date
     */
    public Date nascToDate(){
        Calendar aDate = Calendar.getInstance();
        aDate.set(2019, 11, 31);
        Date nascDate = aDate.getTime();
        try{
            nascDate = new SimpleDateFormat("dd/MM/yyyy").parse(this.getDataNascAsString());
        } catch (Exception ex){}
        return nascDate;
    }

    /*
    Parse da DER como String
     */
    public String lerDerString(){
        String DERString = this.getExtrato();
        DERString = DERString.split("DER.........: ")[1];
        DERString = DERString.split("DIB........:")[0].trim();
        return DERString;
    }

    /*
    Converte a DER para date
     */
    public Date DERtoDate(){
        Calendar aDate = Calendar.getInstance();
        aDate.set(2019, 11, 31);
        Date DERdate = aDate.getTime();
        try{
            DERdate = new SimpleDateFormat("dd/MM/yyyy").parse(this.getDERasString());
        } catch (Exception ex){}
        return DERdate;
    }

    /*
    Parse da idade e conversão para um array de String
     */
    public String[] parseIdadeDER(){
        String achaIdadeDER = this.getExtrato();
        achaIdadeDER = achaIdadeDER.split("Aposentadoria Programada")[2];
        achaIdadeDER = achaIdadeDER.split("Idade\\s+: ")[1];
        achaIdadeDER = achaIdadeDER.split("Soma")[0].trim();
        String[] arrayIdadeDER;
        arrayIdadeDER = achaIdadeDER.split(", ");
        arrayIdadeDER[0] = arrayIdadeDER[0].split("a")[0];
        arrayIdadeDER[1] = arrayIdadeDER[1].split("m")[0];
        arrayIdadeDER[2] = arrayIdadeDER[2].split("d")[0];
        return arrayIdadeDER;
    }

    /*
    Parse da data de filiação como String
     */
    public String parseFiliaString(){
        String filiaString = this.getExtrato();
        filiaString = filiaString.split("PERIODOS DE QUALIDADE DE SEGURADO\\S+: ")[1];
        filiaString = filiaString.split(" a")[0].trim();
        return filiaString;
    }

    /*
    Converte a data de filiação para Date
     */
    public Date FiliaToDate(){
        Calendar aDate = Calendar.getInstance();
        aDate.set(2019, 11, 31);
        Date FiliaDate = aDate.getTime();
        try{
            FiliaDate = new SimpleDateFormat("dd/MM/yyyy").parse(this.getDataFiliaAsString());
        } catch (Exception ex){}
        return FiliaDate;
    }

    /*
    Testa se a filiação ocorreu até 13/11/2019
     */
    public boolean testaFiliaAteEC(){
        Calendar aDate = Calendar.getInstance();
        aDate.set(2019, 10, 13);
        Date dataEC = aDate.getTime();
        boolean filiaAteEC = true;
        try{
            filiaAteEC = this.getDataFilia().compareTo(dataEC)<=0;
        }
        catch (Exception ex){}
        return filiaAteEC;
    }

    /*

    Métodos de parsing de atributos da parte de análise do direito do segurado

     */

    /*
    Atribuição do nome da regra de análise de direito
     */
    public String retornaNomeRegraAnaliseDireito(int index){
        String regra = "";
        if (index == this.r1) {
            regra = "Regra geral do Art.19 - Aposentadoria Programada";
        } else if (index == this.r2) {
            regra = "Direito a aposentadoria por idade antes da Emenda Constitucional 103/2019";
        } else if (index == this.r3) {
            regra = "Regra transitoria do Art.18 - Aposentadoria por idade";
        }
        return regra;
    }

    /*
    Parse da data base de verificação de direito
     */
    public String parseDataBase(int index) {
    String data = this.getExtrato();
        if (index == this.r1) {
            data = data.split(this.getRegraAnaliseDireito(index),2)[1];
            data = data.split("Analise do direito em ")[1];
            data = data.split("\\s")[0].trim();
        } else if (index == this.r2) {
            data = "13/11/2019";
        } else if (index == this.r3) {
            data = data.split(this.getRegraAnaliseDireito(index),0+this.r3)[this.getR3()-1];
            data = data.split("Analise do direito em ")[1];
            data = data.split("\\s")[0].trim();
        }
        return data;
    }

    /*
    Converte data base para Date
     */
    public Date converteDataBaseDate(int index) {
        int[] d = Arrays.stream(this.getStringDataBase(index).split("/")).mapToInt(Integer::parseInt).toArray();
        Calendar aDate = Calendar.getInstance();
        aDate.set(d[2], (d[1]-1), d[0]);
        return aDate.getTime();
    }

    /*
    Parse idade exigida na data base de análise do direito
     */
    public String parseIdadeExigida(int index){
        String idadeExigida = "";
        if (index == this.getR1()) {
            if (this.getSexo().equals("masculino")) {
                idadeExigida = "65 anos";
            } else {
                idadeExigida = "62 anos";
            }
        }
        else if(index == this.getR2()){
            if (this.getSexo().equals("masculino")) {
                idadeExigida = "65 anos";
            } else {
                idadeExigida = "60 anos";
            }
        } else if (index == this.getR3()) {
            idadeExigida = this.getExtrato();
            idadeExigida = idadeExigida.split(this.getRegraAnaliseDireito(index),this.r3)[this.r3-1];
            idadeExigida = idadeExigida.split("Analise do direito em " + this.getStringDataBase(index))[1];
            idadeExigida = idadeExigida.split("Idade exigida: ")[1];
            idadeExigida = idadeExigida.split("\\n")[0].trim();

        }
        return idadeExigida;
    }


    /*
    Parse da idade efetiva na data base
     */
    public String[] parseIdadeEfetiva(int index){
        String IdadeEfetiva = this.getExtrato();
        IdadeEfetiva = IdadeEfetiva.split(this.getRegraAnaliseDireito(index),2)[1];
        IdadeEfetiva = IdadeEfetiva.split("Analise do direito em " + this.getStringDataBase(index))[1];
        IdadeEfetiva = IdadeEfetiva.split("Idade\\s+: ")[1];
        IdadeEfetiva = IdadeEfetiva.split("Soma")[0].trim();
        String[] arrayIdadeEfetiva;
        arrayIdadeEfetiva = IdadeEfetiva.split(", ");
        arrayIdadeEfetiva[0] = arrayIdadeEfetiva[0].split("a")[0];
        arrayIdadeEfetiva[1] = arrayIdadeEfetiva[1].split("m")[0];
        arrayIdadeEfetiva[2] = arrayIdadeEfetiva[2].split("d")[0];
        return arrayIdadeEfetiva;
    }

    /*
    Parse da carência efetiva na data base de análise do direito
     */
    public String parseCarenciaEfetiva(int index){
        String carenciaEfetiva = this.getExtrato();
        carenciaEfetiva = carenciaEfetiva.split(this.getRegraAnaliseDireito(index),2)[1];
        carenciaEfetiva = carenciaEfetiva.split("Analise do direito em " + this.getStringDataBase(index))[1];
        carenciaEfetiva = carenciaEfetiva.split("Quantidade de carencia\\s+: ")[1];
        carenciaEfetiva = carenciaEfetiva.split("Idade")[0].trim();
        return carenciaEfetiva;
    }

    /*
    Parse do tempo de contribuição efetiva na data base de análise do direito
     */
    public String[] parseTempCompEfetivo(int index) {
        String tempCompEfetivo = this.getExtrato();
        tempCompEfetivo = tempCompEfetivo.split(this.getRegraAnaliseDireito(index), 2)[1];
        tempCompEfetivo = tempCompEfetivo.split("Analise do direito em " + this.getStringDataBase(index))[1];
        tempCompEfetivo = tempCompEfetivo.split("Total de tempo considerado\\s+: ")[1];
        tempCompEfetivo = tempCompEfetivo.split("Quantidade de carencia")[0].trim();
        String[] arrayTempCompEfetivo;
        arrayTempCompEfetivo = tempCompEfetivo.split(", ");
        arrayTempCompEfetivo[0] = arrayTempCompEfetivo[0].split("a")[0];
        arrayTempCompEfetivo[1] = arrayTempCompEfetivo[1].split("m")[0];
        arrayTempCompEfetivo[2] = arrayTempCompEfetivo[2].split("d")[0];
        return arrayTempCompEfetivo;
    }

    /*
    Parse de reconhecimento de direito na data base de análise do direito
     */
    public String parseRecDireitoDataBase(int index){
        String recDireitoDataBase = this.getExtrato();
        recDireitoDataBase = recDireitoDataBase.split(this.getRegraAnaliseDireito(index),2)[1];
        recDireitoDataBase = recDireitoDataBase.split("Analise do direito em " + this.getStringDataBase(index))[1];
        recDireitoDataBase = recDireitoDataBase.split("Possui direito nesta data\\s+: ")[1];
        recDireitoDataBase = recDireitoDataBase.split("[^sn]")[0];
        if (recDireitoDataBase.equals("s")) {
            recDireitoDataBase = "foi reconhecido";
        } else {
            recDireitoDataBase = "não foi reconhecido";
        }
        return recDireitoDataBase;
    }

    /*
    Parse de direito à aposentadoria por qualquer uma das regras
     */
    public String parseDireitoAposFinal(){
        String direitoAposFinal = this.getExtrato();
        direitoAposFinal = direitoAposFinal.split("ANALISE DO DIREITO", 2)[1];
        direitoAposFinal = direitoAposFinal.split("Possui direito neste perfil: ")[1];
        direitoAposFinal = direitoAposFinal.split("[^sn]")[0];
        if (direitoAposFinal.equals("s")){
            direitoAposFinal = "foi reconhecido o direito";
        }
        else direitoAposFinal = "não foi reconhecido o direito";
        return direitoAposFinal;
    }

}
