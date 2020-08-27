package main;

import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class Segurado {

    /*

    Atributos da parte de dados iniciais do segurado

     */

    private String extrato; //Armazena o texto do arquivo de extrato na íntegra
    private String nome; //Nome do segurado
    private String NB; //Número do benefício
    private String codEspecieBeneficio; //Código da espécie de benefício
    private String especieBeneficio; //Nome da espécie de benefício
    private String sexo; //Sexo do segurado
    private char artGenero; //Artigo de gênero do segurado
    private Date dataNasc; //Data de nascimento do segurado
    private String stringDataNasc; //Data de nascimento do segurado formatada e como String
    private Date DER; //Data de entrada do requerimento
    private String stringDER;//Data de entrada do requerimento formatada e como String
    private Date DIB; //Data de início do benefício
    private String stringDIB;//Data de entrada do requerimento formatada e como String
    private String[] idadeDIB; //Idade do segurado na DIB
    private Date dataFilia; //Data de filiação ao RGPS
    private String stringDataFiliaAs; //Data de filiação ao RGPS formatada e como String
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

    private int r1 = -1; //Número do índice referente à regra de aposentadoria programada art. 19 da EC 103/2019. Sempre = 0
    private int r2 = -1; //Número do índice referente à regra de direito adquirido à aposentadoria por idade antes da EC 103/2019 (idade)
    private int r3 = -1; //Número do índice referente à regra transitória art. 18 da EC 103/2019 (idade)
    private int r4 = -1; //Número do índice referente à regra de direito adquirido à aposentadoria integral por tempo de contribuição antes da EC 103/2019 (TC)
    private int r5 = -1; //Número do índice referente à regra de direito adquirido à aposentadoria proporcional por tempo de contribuição antes da EC 103/2019 (TC)
    private int r6 = -1; //Número do índice referente à regra transitória art. 15 da EC 103/2019 (TC)
    private int r7 = -1; //Número do índice referente à regra transitória art. 16 da EC 103/2019 (TC)
    private int r8 = -1; //Número do índice referente à regra transitória art. 17 da EC 103/2019 (TC)
    private int r9 = -1; //Número do índice referente à regra transitória art. 20 da EC 103/2019 (TC)

    private List<String> regraAnaliseDireito = new ArrayList<>(); //Lista com o nome das regras para análise do direito em formato String

    private List<String> stringDataBase = new ArrayList<>(); //Lista com data base para análise do direito em formato String
    private List<Date> dateDataBase = new ArrayList<>(); //Lista com data base para análise do direito em formato Date

    private List<String> idadeExigida = new ArrayList<>(); //Lista contendo as idades exigidas guardadas em String
    private List<String> carenciaExigida = new ArrayList<>(); //Lista contendo as carências exigidas guardadas em String
    private List<String[]> pedagio = new ArrayList<>(); //Lista de pedágio de tempo de contribuição com [0] = anos, [1] = meses e [2] = dias
    private List<String[]> tempCompExigido = new ArrayList<>(); //Lista contendo os tempos de contribuição exigidos guardados em array de String com [0] = anos, [1] = meses e [2] = dias
    private List<String[]> tempCompPedagio = new ArrayList<>(); //Lista contendo os tempos de contribuição exigidos com pedagio guardados em array de String com [0] = anos, [1] = meses e [2] = dias
    private List<String> pontuacaoExigida = new ArrayList<>(); //Lista de pontuação exigida (idade + tempo de contribuição)

    private List<String[]> idadeEfetiva = new ArrayList<>(); //Lista contendo as idades efetivas guardadas em array de String com [0] = anos, [1] = meses e [2] = dias
    private List<String> carenciaEfetiva = new ArrayList<>(); //Lista contendo as carências efetivas guardadas em String
    private List<String[]> tempCompEfetivo = new ArrayList<>(); //Lista contendo os tempos de contribuição efetivos guardados em array de String com [0] = anos, [1] = meses e [2] = dias
    private List<String[]> pontuacaoEfetiva = new ArrayList<>(); //Lista de pontuação efetiva (idade + tempo de contribuição)  guardadas em array de String com [0] = anos, [1] = meses e [2] = dias

    private List<String> recDireitoDataBase = new ArrayList<>(); //Lista com a afirmação quanto ao reconhecimento do direito  guardados como String com "foi reconhecido o direito" ou "não foi reconhecido o direito"

    /*
    Afirmação quanto ao reconhecumento do direito a aposentadoria por idade
    por qualquer uma das regras com String "foi reconhecido o direito" ou "não foi reconhecido o direito"
     */
    private String recDireitoFinalIdade;
    /*
    Afirmação quanto ao reconhecumento do direito a aposentadoria por tempo de contribuição
    por qualquer uma das regras com String "foi reconhecido o direito" ou "não foi reconhecido o direito"
     */
    private String recDireitoFinalTC;

    private Boolean cabeAnaliseDtoOutraEspecie = false;

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

    public String getNB() {
        return NB;
    }

    public void setNB(String NB) {
        this.NB = NB;
    }

    public String getCodEspecieBeneficio() {
        return codEspecieBeneficio;
    }

    public void setCodEspecieBeneficio(String codEspecieBeneficio) {
        this.codEspecieBeneficio = codEspecieBeneficio;
    }

    public String getEspecieBeneficio() {
        return especieBeneficio;
    }

    public void setEspecieBeneficio(String especieBeneficio) {
        this.especieBeneficio = especieBeneficio;
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

    public String getStringDataNasc() {
        return stringDataNasc;
    }

    public void setStringDataNasc(String stringDataNasc) {
        this.stringDataNasc = stringDataNasc;
    }

    public Date getDER() {
        return DER;
    }

    public void setDER(Date DER) {
        this.DER = DER;
    }

    public String getStringDER() {
        return stringDER;
    }

    public void setStringDER(String stringDER) {
        this.stringDER = stringDER;
    }

    public Date getDIB() {
        return DIB;
    }

    public void setDIB(Date DIB) {
        this.DIB = DIB;
    }

    public String getStringDIB() {
        return stringDIB;
    }

    public void setStringDIB(String stringDIB) {
        this.stringDIB = stringDIB;
    }

    public String[] getIdadeDIB() {
        return idadeDIB;
    }

    public void setIdadeDIB(String[] idadeDIB) {
        this.idadeDIB = idadeDIB;
    }

    public Date getDataFilia() {
        return dataFilia;
    }

    public void setDataFilia(Date dataFilia) {
        this.dataFilia = dataFilia;
    }

    public String getStringDataFiliaAs() {
        return stringDataFiliaAs;
    }

    public void setStringDataFiliaAs(String stringDataFiliaAs) {
        this.stringDataFiliaAs = stringDataFiliaAs;
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

    public int getR4() {
        return r4;
    }

    public void setR4(int r4) {
        this.r4 = r4;
    }

    public int getR5() {
        return r5;
    }

    public void setR5(int r5) {
        this.r5 = r5;
    }

    public int getR6() {
        return r6;
    }

    public void setR6(int r6) {
        this.r6 = r6;
    }

    public int getR7() {
        return r7;
    }

    public void setR7(int r7) {
        this.r7 = r7;
    }

    public int getR8() {
        return r8;
    }

    public void setR8(int r8) {
        this.r8 = r8;
    }

    public int getR9() {
        return r9;
    }

    public void setR9(int r9) {
        this.r9 = r9;
    }

    public int countRegraAnaliseDireito() {
        return this.regraAnaliseDireito.size();
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

    public String[] getPedagio(int index) {
        return this.pedagio.get(index);
    }

    public void addPedagio(String[] pedagio) {
        this.pedagio.add(pedagio);
    }

    public String[] getTempCompExigido(int index) {
        return this.tempCompExigido.get(index);
    }

    public void addTempCompExigido(String[] tempCompExigido) {
        this.tempCompExigido.add(tempCompExigido);
    }

    public String[] getTempCompPedagio(int index) {
        return this.tempCompPedagio.get(index);
    }

    public void addTempCompPedagio(String[] tempCompPedagio) {
        this.tempCompPedagio.add(tempCompPedagio);
    }

    public String getPontuacaoExigida(int index) {
        return this.pontuacaoExigida.get(index);
    }

    public void addPontuacaoExigida(String pontuacaoExigida) {
        this.pontuacaoExigida.add(pontuacaoExigida);
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
        return this.tempCompEfetivo.get(index);
    }

    public void addTempCompEfetivo(String[] tempCompEfetivo) {
        this.tempCompEfetivo.add(tempCompEfetivo);
    }

    public String[] getPontuacaoEfetiva(int index) {
        return this.pontuacaoEfetiva.get(index);
    }

    public void addPontuacaoEfetiva(String[] pontuacaoEfetiva) {
        this.pontuacaoEfetiva.add(pontuacaoEfetiva);
    }

    public String getRecDireitoDataBase(int index) {
        return this.recDireitoDataBase.get(index);
    }

    public void addRecDireitoDataBase(String recDireitoDataBase) {
        this.recDireitoDataBase.add(recDireitoDataBase);
    }

    public String getRecDireitoFinalIdade() {
        return recDireitoFinalIdade;
    }

    public void setRecDireitoFinalIdade(String recDireitoFinalIdade) {
        this.recDireitoFinalIdade = recDireitoFinalIdade;
    }

    public String getRecDireitoFinalTC() {
        return recDireitoFinalTC;
    }

    public void setRecDireitoFinalTC(String recDireitoFinalTC) {
        this.recDireitoFinalTC = recDireitoFinalTC;
    }

    public Boolean getCabeAnaliseDtoOutraEspecie() {
        return cabeAnaliseDtoOutraEspecie;
    }

    public void setCabeAnaliseDtoOutraEspecie(Boolean cabeAnaliseDtoOutraEspecie) {
        this.cabeAnaliseDtoOutraEspecie = cabeAnaliseDtoOutraEspecie;
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
                if (selectedFile != null) {
                    extrato = new String(Files.readAllBytes(selectedFile.toPath()));
                }
                else
                    extrato = null;
            }
            catch (Exception ex){}
        return extrato;
    }

    /*
    Parse do nome do segurado
     */
    public String parseNome() {
        String nome = this.getExtrato();
        nome = nome.split("SEGURADO\\S+: ")[1];
        nome = nome.split("DATA NASC")[0].trim();
        return nome;
    }

    /*
    Parse do número de benefício
     */
    public String parseNB() {
        String nb = this.getExtrato();
        nb = nb.split("NB\\S+: ")[1];
        nb = nb.split("\\s+")[0].trim();
        return nb;
    }

    /*
    Parse do código da espécie de benefício
     */
    public String parseCodEspecie() {
        String codEspecie = this.getExtrato();
        codEspecie = codEspecie.split("ESPECIE\\S+: ")[1];
        codEspecie = codEspecie.split("\\s+")[0].trim();
        return codEspecie;
    }

    /*
    Transforma código da espécie de benefício em nome da espécie
     */
    public String transfEspecieBeneficio() {
        String nomeEspecie = "";
        if (this.getCodEspecieBeneficio().equals("41")) {
            nomeEspecie = "aposentadoria por idade";
        } else if (this.getCodEspecieBeneficio().equals("42")) {
            nomeEspecie = "aposentadoria por tempo de contribuição";
        }
        return nomeEspecie;
    }

    /*
    Parse do sexo do segurado
     */
    public String parseSexo() {
        String sexo = this.getExtrato();
        sexo = sexo.split("SEXO\\S+: ")[1];
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
            nascDate = new SimpleDateFormat("dd/MM/yyyy").parse(this.getStringDataNasc());
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
            DERdate = new SimpleDateFormat("dd/MM/yyyy").parse(this.getStringDER());
        } catch (Exception ex){}
        return DERdate;
    }

    /*
    Parse da DIB como String
     */
    public String lerDIBString(){
        String DIBString = this.getExtrato();
        DIBString = DIBString.split("DIB\\S+: ")[1];
        DIBString = DIBString.split("\n")[0].trim();
        return DIBString;
    }

    /*
    Converte a DIB para date
     */
    public Date DIBtoDate(){
        Calendar aDate = Calendar.getInstance();
        aDate.set(2019, 11, 31);
        Date DIBdate = aDate.getTime();
        try{
            DIBdate = new SimpleDateFormat("dd/MM/yyyy").parse(this.getStringDIB());
        } catch (Exception ex){}
        return DIBdate;
    }

    /*
    Parse da idade na DIB e conversão para um array de String
     */
    public String[] parseIdadeDIB(){
        String achaIdadeDIB = this.getExtrato();
        achaIdadeDIB = achaIdadeDIB.split("Analise do direito em " + this.getStringDIB())[2];
        achaIdadeDIB = achaIdadeDIB.split("Idade\\s+: ")[1];
        achaIdadeDIB = achaIdadeDIB.split("Soma")[0].trim();
        String[] arrayIdadeDIB;
        arrayIdadeDIB = achaIdadeDIB.split(", ");
        arrayIdadeDIB[0] = arrayIdadeDIB[0].split("a")[0];
        arrayIdadeDIB[1] = arrayIdadeDIB[1].split("m")[0];
        arrayIdadeDIB[2] = arrayIdadeDIB[2].split("d")[0];
        return arrayIdadeDIB;
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
            FiliaDate = new SimpleDateFormat("dd/MM/yyyy").parse(this.getStringDataFiliaAs());
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
        else if (index == this.r4) {
            regra = "Direito a aposentadoria por tempo de contribuicao integral antes da Emenda Constitucional 103/2019";
        }
        else if (index == this.r5) {
            regra = "Direito a aposentadoria por tempo de contribuicao proporcional antes da Emenda Constitucional 103/2019";
        }
        else if (index == this.r6) {
            regra = "Regra transitoria do Art.15 - Aposentadoria por tempo de contribuicao com soma de idade e tempo";
        }
        else if (index == this.r7) {
            regra = "Regra transitoria do Art.16 - Tempo de contribuicao com idade minima";
        }
        else if (index == this.r8) {
            regra = "Regra transitoria do Art.17 - Tempo de contribuicao com pedagio de 50%";
        }
        else if (index == this.r9) {
            regra = "Regra transitoria do Art.20 - Tempo de contribuicao com pedagio de 100%";
        }
        return regra;
    }



    /*
    Parse da data base de verificação de direito
     */
    public String parseDataBase(int index) {

        String data = this.getExtrato();

    if (index == this.r1) {
            data = this.getStringDIB();

        } else if (index == this.r2 | index == this.getR4() | index == this.getR5()) {
            data = "13/11/2019";

        } else { //Regras de direito que podem gerar mais de um parágrafo com datas diferentes no loop de do while
            int limite = this.limite(index);
            data = data.split(this.getRegraAnaliseDireito(index),limite)[limite-1];
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
        } else if (index == this.getR3()|index == this.getR5()|index == this.getR7()|index == this.getR9()) {
            idadeExigida = this.getExtrato();
            idadeExigida = idadeExigida.split(this.getRegraAnaliseDireito(index),this.limite(index))[this.limite(index)-1];
            idadeExigida = idadeExigida.split("Analise do direito em " + this.getStringDataBase(index))[1];
            idadeExigida = idadeExigida.split("Idade exigida: ")[1];
            idadeExigida = idadeExigida.split("\\n")[0].trim();

        }
        return idadeExigida;
    }

    /*
    Parse carência exigida na data base de análise do direito
     */
    public String parseCarenciaExigida(int index) {
        String carenciaExigida = this.getExtrato();
        if (index == this.getR1()) {
            carenciaExigida = "180";
        } else {
            carenciaExigida = carenciaExigida.split(this.getRegraAnaliseDireito(index), 2)[1];
            carenciaExigida = carenciaExigida.split("Analise do direito em " + this.getStringDataBase(index))[1];
            carenciaExigida = carenciaExigida.split("Requisito\\s+:\\s+Carencia igual")[1];
            carenciaExigida = carenciaExigida.split("Exigido\\s")[1];
            carenciaExigida = carenciaExigida.split("\\s")[0].trim();
        }
        return carenciaExigida;
    }

    /*
    Parse de pedágio de tempo de contribuição
     */
    public String[] parsePedagio(int index) {
        String[] arrayPedagio = new String[]{"00", "00", "00"};
        if (index == this.getR5()) {
            String pedagio = this.getExtrato();
            pedagio = pedagio.split(this.getRegraAnaliseDireito(index), 2)[1];
            pedagio = pedagio.split("Analise do direito em " + this.getStringDataBase(index))[1];
            pedagio = pedagio.split("Tempo de pedagio\\s+: ")[1];
            pedagio = pedagio.split("\\n")[0].trim();
            arrayPedagio = pedagio.split(", ");
            arrayPedagio[0] = arrayPedagio[0].split("a")[0];
            arrayPedagio[1] = arrayPedagio[1].split("m")[0];
            arrayPedagio[2] = arrayPedagio[2].split("d")[0];
        }
        return arrayPedagio;
    }

    /*
    Parse de tempo de contribuição exigido na data base de análise do direito
     */
    public String[] parseTempCompExigido(int index) {
        String[] tempCompExigido = new String[]{"00", "00", "00"};
        if (index == this.getR1()) {
            if (this.getSexo().equals("masculino")) {
                tempCompExigido = new String[]{"20", "00", "00"};
            } else {
                tempCompExigido = new String[]{"15", "00", "00"};
            }
        }

        else if (index == this.getR3()) {
            tempCompExigido = new String[]{"15", "00", "00"};
        }

        else if (index == this.getR5()) {
            if (this.getSexo().equals("masculino")) {
                tempCompExigido = new String[]{"30", "00", "00"};
            } else {
                tempCompExigido = new String[]{"25", "00", "00"};
            }
        }

        else if (index == this.getR4() | index == this.getR6() | index == this.getR7()) {
            if (this.getSexo().equals("masculino")) {
                tempCompExigido = new String[]{"35", "00", "00"};
            } else {
                tempCompExigido = new String[]{"30", "00", "00"};
            }
        }

        else if (index == this.getR8() | index == this.getR9()) {
            String tempoExigido = this.getExtrato();
            tempoExigido = tempoExigido.split(this.getRegraAnaliseDireito(index), 2)[1];
            tempoExigido = tempoExigido.split("Tempo exigido:\\s")[1];
            tempoExigido = tempoExigido.split("\\n")[0].trim();

            String anos = tempoExigido.split("\\s anos")[0];
            String meses = "00";
            String dias = "00";

            if (tempoExigido.indexOf("meses") != -1) {
                meses = tempoExigido.split("\\s meses")[0];
                meses = meses.substring(meses.length() - 2);
            }

            if (tempoExigido.indexOf("dias") != -1) {
                dias = tempoExigido.split("\\s dias")[0];
                dias = dias.substring(dias.length() - 2);
            }

            tempCompExigido = new String[]{anos, meses, dias};
        }
        return tempCompExigido;
    }

    /*
    Soma pedágio ao tempo de contribuição exigido
     */
    public String[] somaPedagioTempComp(int index) {
        String[] PedagioTempComp = new String[3];
        String[] tempComp = this.getTempCompExigido(index);
        int[] tcpInt;
        String[] pedagio = this.getPedagio(index);
        int[] ped;
        List<String> l = new ArrayList<>();
        if (index == this.getR5()) {
            tcpInt = Stream.of(tempComp).mapToInt(Integer::parseInt).toArray();
            ped = Stream.of(pedagio).mapToInt(Integer::parseInt).toArray();
            for(int i = 0; i < tcpInt.length; i++){
                if ((tcpInt[i] + ped[i]) < 10) {
                    l.add("0" + Integer.toString(tcpInt[i] + ped[i]));
                } else {
                    l.add(Integer.toString(tcpInt[i] + ped[i]));
                }
            }
            PedagioTempComp = l.toArray(PedagioTempComp);
        }
        else {PedagioTempComp = tempComp;}
        return PedagioTempComp;
    }

    /*
    Parse de pontuação exigida
     */
    public String parsePontuacaoExigida(int index) {
        String PontuacaoExigida = "00 anos";
        if (index == this.getR6()) {

            int limite = 0; //Limite de divisões do split da regra de direito
            int pedaco = 0; //Pedaço do split que será usado para o parse
            limite = 1+(this.getR6() - this.getR5());
            pedaco = limite - 1;

            PontuacaoExigida = this.getExtrato();
            PontuacaoExigida = PontuacaoExigida.split(this.getRegraAnaliseDireito(index), limite)[pedaco];
            PontuacaoExigida = PontuacaoExigida.split("Analise do direito em " + this.getStringDataBase(index))[1];
            PontuacaoExigida = PontuacaoExigida.split("Pontuacao exigida: ")[1];
            PontuacaoExigida = PontuacaoExigida.split("\\n")[0].trim();
        }
        return PontuacaoExigida;
    }

    /*
    Parse da idade efetiva na data base de análise do direito
     */
    public String[] parseIdadeEfetiva(int index){
        String IdadeEfetiva = this.getExtrato();
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
        carenciaEfetiva = carenciaEfetiva.split("Analise do direito em " + this.getStringDataBase(index))[1];
        carenciaEfetiva = carenciaEfetiva.split("Quantidade de carencia\\s+: ")[1];
        carenciaEfetiva = carenciaEfetiva.split("\\n")[0].trim();
        return carenciaEfetiva;
    }

    /*
    Parse do tempo de contribuição efetivo na data base de análise do direito
     */
    public String[] parseTempCompEfetivo(int index) {
        String tempCompEfetivo = this.getExtrato();
        if (this.getCodEspecieBeneficio().equals("42") && index==this.getR1()) {
            tempCompEfetivo = tempCompEfetivo.split("Regra transitoria do Art.15", 2)[1];
            tempCompEfetivo = tempCompEfetivo.split("Analise do direito em " + this.getStringDataBase(index))[1];
            tempCompEfetivo = tempCompEfetivo.split("Tempo de contribuicao\\s+: ")[1];
            tempCompEfetivo = tempCompEfetivo.split("\\n")[0].trim();
        }
        else {
            tempCompEfetivo = tempCompEfetivo.split(this.getRegraAnaliseDireito(index), 2)[1];
            tempCompEfetivo = tempCompEfetivo.split("Analise do direito em " + this.getStringDataBase(index))[1];
            if (index == this.getR5()){
                tempCompEfetivo = tempCompEfetivo.split("Tempo de contribuicao \\(bruto\\)\\s+: ")[1];
                tempCompEfetivo = tempCompEfetivo.split("\\n")[0].trim();
            }
            else {
                tempCompEfetivo = tempCompEfetivo.split("Tempo de contribuicao\\s+: ")[1];
                tempCompEfetivo = tempCompEfetivo.split("\\n")[0].trim();
            }

        }
        String[] arrayTempCompEfetivo;
        arrayTempCompEfetivo = tempCompEfetivo.split(", ");
        arrayTempCompEfetivo[0] = arrayTempCompEfetivo[0].split("a")[0];
        arrayTempCompEfetivo[1] = arrayTempCompEfetivo[1].split("m")[0];
        arrayTempCompEfetivo[2] = arrayTempCompEfetivo[2].split("d")[0];
        return arrayTempCompEfetivo;
    }

    /*
    Parse de pontuação efetiva
     */
    public String[] parsePontuacaoEfetiva(int index) {
        String[] arrayPontuacaoEfetiva = {"00", "00", "00"};
        if (index == this.getR6()) {
            String pef = this.getExtrato();
            pef = pef.split(this.getRegraAnaliseDireito(index), index)[index-1];
            pef = pef.split("Analise do direito em " + this.getStringDataBase(index))[1];
            pef = pef.split("Soma Idade e TC\\s+: ")[1];
            pef = pef.split("Possui direito nesta data")[0].trim();
            arrayPontuacaoEfetiva = pef.split(", ");
            arrayPontuacaoEfetiva[0] = arrayPontuacaoEfetiva[0].split("a")[0];
            arrayPontuacaoEfetiva[1] = arrayPontuacaoEfetiva[1].split("m")[0];
            arrayPontuacaoEfetiva[2] = arrayPontuacaoEfetiva[2].split("d")[0];
        }
        return arrayPontuacaoEfetiva;
    }

    /*
    Parse de reconhecimento de direito na data base de análise do direito
     */
    public String parseRecDireitoDataBase(int index){
        String recDireitoDataBase = "";
        if (index == this.getR1()) {
            int id = Integer.parseInt(this.getIdadeEfetiva(index)[0]);
            int car = Integer.parseInt(this.getCarenciaEfetiva(index));
            int tc = Integer.parseInt(this.getTempCompEfetivo(index)[0]);
            if (this.getSexo().equals("masculino")) {
                if (id >= 65 && car >= 180 && tc >= 20) {
                    recDireitoDataBase = "s";
                    if (this.getCodEspecieBeneficio().equals("42")) {
                        this.setCabeAnaliseDtoOutraEspecie(true);
                    }
                }
            } else {
                if (id >= 62 && car >= 180 && tc >= 15) {
                    recDireitoDataBase = "s";
                    if (this.getCodEspecieBeneficio().equals("42")) {
                        this.setCabeAnaliseDtoOutraEspecie(true);
                    }
                }
            }
        }
        else {
            recDireitoDataBase = this.getExtrato();
            recDireitoDataBase = recDireitoDataBase.split(this.getRegraAnaliseDireito(index), 2)[1];
            recDireitoDataBase = recDireitoDataBase.split("Analise do direito em " + this.getStringDataBase(index))[1];
            recDireitoDataBase = recDireitoDataBase.split("Possui direito nesta data\\s+: ")[1];
            recDireitoDataBase = recDireitoDataBase.split("[^sn]")[0];
        }
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

    /*
    Faz análise preliminar de carência para possível direito a aposentadoria por idade ou tempo de contribuição
     */
    public boolean fazPreAnaliseCarencia() {
        int carencia = Integer.parseInt(this.getCarenciaEfetiva(0));
        return (carencia >= 180);
    }

    /*
    Faz análise preliminar de tempo de contribuição para possível direito a aposentadoria por tempo de contribuição
     */
    public boolean fazPreAnaliseTC() {
        boolean preAnaliseTC = false;
        if (this.getSexo().equals("masculino")) {
            if (Integer.parseInt(this.getTempCompEfetivo(this.getR1())[0]) >= 35) {
                preAnaliseTC = true;
            }
        } else if (this.getSexo().equals("feminino")) {
            if (Integer.parseInt(this.getTempCompEfetivo(this.getR1())[0]) >= 30) {
                preAnaliseTC = true;
            }
        }
        return preAnaliseTC;
    }

    /*
    Faz análise preliminar de idade para possível direito a aposentadoria por idade
     */
    public boolean fazPreAnaliseIdade() {
        boolean preAnaliseIdade = false;
        if (this.getSexo().equals("masculino")) {
            if (Integer.parseInt(this.getIdadeEfetiva(this.getR1())[0]) >= 65) {
                preAnaliseIdade = true;
            }
        } else if (this.getSexo().equals("feminino")) {
            if (Integer.parseInt(this.getIdadeEfetiva(this.getR1())[0]) >= 60) {
                preAnaliseIdade = true;
            }
        }
        return preAnaliseIdade;
    }

    /*
    Fornece limite utilização em split de parsing de atributos com possibilidade de multiplos parágrafos
    retorna o limite de divisões a serem feitas
     */
    public int limite(int index) {

        int limite = 0; //Limite de divisões do split da regra de direito

        if (index == this.getR3()) {
            limite = 1+(this.getR3() - this.getR2());

        }
        else if (index == this.getR5()) {
            limite = 1+(this.getR5() - this.getR4());

        }
        else if (index == this.getR6()) {
            limite = 1+(this.getR6() - this.getR5());

        }
        else if (index == this.getR7()) {
            limite = 1+(this.getR7() - this.getR6());

        }
        else if (index == this.getR8()) {
            limite = 1+(this.getR8() - this.getR7());

        }
        else if (index == this.getR9()) {
            limite = 1+(this.getR9() - this.getR8());

        }

        return limite;
    }
}
