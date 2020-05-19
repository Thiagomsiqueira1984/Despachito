package main;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Segurado {

    /*

    Atributos

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
    private String idadeExigidaEC; //Idade exigida até 13/11/2019
    private String[] idadeCompEC; //Idade do segurado en 13/11/2019
    private String carenciaAteEC; //Carência exigida até 13/11/2019
    private boolean dirAdqEC; //Testa se há direito adquirido em 13/11/2019
    private String possuiNaoDirAdqEC; //Informa se possui direito adquirido em 13/11/2019 como String
    private Date dtVerif; //Data variável para verificação do direito (art. 18 EC 103/2019) em Date
    private String dtVerifString; //Data variável para verificação do direito (art. 18 EC 103/2019) em String
    private String idadeExigidaDtVerif; //Idade exigina na data de verificação do direito (art. 18 EC 103/2019)
    private String[] idadeCompDtVerif; //Idade atingida até a data de verificação do direito (art. 18 EC 103/2019)
    private String carenciaDtVerif; //Carência na data de verificação do direito (art. 18 EC 103/2019)
    private String[] tempoContrDtVerif; //Na data de verificação do direito (art. 18 EC 103/2019)
    private boolean dirAdqDtVerif; //Testa se há direito adquirido na data de verificação do direito (art. 18 EC 103/2019)
    private String possuiNaoDirAdqDtVerif; //Informa se possui direito adquirido na data de verificação do direito (art. 18 EC 103/2019)
    private String idadeExigidaAposProg; //Idade exigida para concessão de aposentadoria programada (art. 19 da EC 103/2019)
    private String possuiNaoDireitoAposProg; //Informa se possui direito adquirido à concessão de aposentadoria programada (art. 19 da EC 103/2019)
    private String foiNaoRecDireitoAposFinal; //Informa se foi reconhecido direito adquirido à concessão de aposentadoria em qualquer das regras

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
        return this.sexo.toLowerCase();
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

    public String getIdadeExigidaEC() {
        return idadeExigidaEC;
    }

    public void setIdadeExigidaEC(String idadeExigidaEC) {
        this.idadeExigidaEC = idadeExigidaEC;
    }

    public String[] getIdadeCompEC() {
        return idadeCompEC;
    }

    public void setIdadeCompEC(String[] idadeCompEC) {
        this.idadeCompEC = idadeCompEC;
    }

    public String getCarenciaAteEC() {
        return carenciaAteEC;
    }

    public void setCarenciaAteEC(String carenciaExigidaAteEC) {
        this.carenciaAteEC = carenciaExigidaAteEC;
    }

    public boolean isDirAdqEC() {
        return dirAdqEC;
    }

    public void setDirAdqEC(boolean dirAdqEC) {
        this.dirAdqEC = dirAdqEC;
    }

    public String getPossuiNaoDirAdqEC() {
        return possuiNaoDirAdqEC;
    }

    public void setPossuiNaoDirAdqEC(String possuiNaoDirAdqEC) {
        this.possuiNaoDirAdqEC = possuiNaoDirAdqEC;
    }

    public Date getDtVerif() {
        return dtVerif;
    }

    public void setDtVerif(Date dtVerif) {
        this.dtVerif = dtVerif;
    }

    public String getDtVerifString() {
        return dtVerifString;
    }

    public void setDtVerifString(String dtVerifString) {
        this.dtVerifString = dtVerifString;
    }

    public String getIdadeExigidaDtVerif() {
        return idadeExigidaDtVerif;
    }

    public void setIdadeExigidaDtVerif(String idadeExigidaDtVerif) {
        this.idadeExigidaDtVerif = idadeExigidaDtVerif;
    }

    public String[] getIdadeCompDtVerif() {
        return idadeCompDtVerif;
    }

    public void setIdadeCompDtVerif(String[] idadeCompDtVerif) {
        this.idadeCompDtVerif = idadeCompDtVerif;
    }

    public String getCarenciaDtVerif() {
        return carenciaDtVerif;
    }

    public void setCarenciaDtVerif(String carenciaDtVerif) {
        this.carenciaDtVerif = carenciaDtVerif;
    }

    public String[] getTempoContrDtVerif() {
        return tempoContrDtVerif;
    }

    public void setTempoContrDtVerif(String[] tempoContrDtVerif) {
        this.tempoContrDtVerif = tempoContrDtVerif;
    }

    public boolean isDirAdqDtVerif() {
        return dirAdqDtVerif;
    }

    public void setDirAdqDtVerif(boolean dirAdqDtVerif) {
        this.dirAdqDtVerif = dirAdqDtVerif;
    }

    public String getPossuiNaoDirAdqDtVerif() {
        return possuiNaoDirAdqDtVerif;
    }

    public void setPossuiNaoDirAdqDtVerif(String possuiNaoDirAdqDtVerif) {
        this.possuiNaoDirAdqDtVerif = possuiNaoDirAdqDtVerif;
    }

    public String getIdadeExigidaAposProg() {
        return idadeExigidaAposProg;
    }

    public void setIdadeExigidaAposProg(String idadeExigidaAposProg) {
        this.idadeExigidaAposProg = idadeExigidaAposProg;
    }

    public String getPossuiNaoDireitoAposProg() {
        return possuiNaoDireitoAposProg;
    }

    public void setPossuiNaoDireitoAposProg(String possuiNaoDireitoAposProg) {
        this.possuiNaoDireitoAposProg = possuiNaoDireitoAposProg;
    }

    public String getFoiNaoRecDireitoAposFinal() {
        return foiNaoRecDireitoAposFinal;
    }

    public void setFoiNaoRecDireitoAposFinal(String foiNaoRecDireitoAposFinal) {
        this.foiNaoRecDireitoAposFinal = foiNaoRecDireitoAposFinal;
    }

    /*

    Métodos

     */

    /*
    Parse do arquivo Extrato.txt inteiro como String
     */
    public String lerExtrato(){
        String extrato = "";
        JFileChooser seletorArquivo = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        seletorArquivo.setFileFilter(new Main.ExtensionFilter());
        seletorArquivo.setCurrentDirectory(new File("C:\\CNISLINHA"));
        int valorRetorno = seletorArquivo.showOpenDialog(null);
        if (valorRetorno == JFileChooser.APPROVE_OPTION){
            File selectedFile = seletorArquivo.getSelectedFile();
            try {
                extrato = new String(Files.readAllBytes(selectedFile.toPath()));
            }
            catch (Exception ex){}
        }
        return extrato;
    }

    /*
    Parse do nome do segurado a partir da String novoSegurado.lerExtrato
     */
    public String lerNome() {
        String nome = this.getExtrato();
        nome = nome.split("SEGURADO....: ")[1];
        nome = nome.split("DATA NASC")[0].trim();
        return nome;
    }

    /*
    Parse do sexo do segurado a partir da String novoSegurado.lerExtrato a partir da String novoSegurado.lerExtrato
     */
    public String lerSexo() {
        String sexo = this.getExtrato();
        sexo = sexo.split("SEXO....: ")[1];
        sexo = sexo.split("RAMO ATIV...:")[0].trim();
        return sexo;
    }

    /*
    Parse do artigo de gênero do segurado a partir da String novoSegurado.lerExtrato
     */
    public char lerArtGenero () {
        char artGenero;
        if(this.getSexo().equals("feminino")){
            artGenero = 'a';
        }
        else {
            artGenero = 'o';
        }
        return artGenero;
    }

    /*
    Parse da data de nascimento como String a partir da String novoSegurado.lerExtrato
     */
    public String lerNascString(){
        String nascString = this.getExtrato();
        nascString = nascString.split("NASC...: ")[1];
        nascString = nascString.split("DAT........:")[0].trim();
        return nascString;
    }

    /*
    Converte a data de nascimento para date a partir da variável dataNascAsString em uma instância de main.Segurado
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
    Parse da DER como String a partir da String novoSegurado.lerExtrato
     */
    public String lerDerString(){
        String DERString = this.getExtrato();
        DERString = DERString.split("DER.........: ")[1];
        DERString = DERString.split("DIB........:")[0].trim();
        return DERString;
    }

    /*
    Converte a DER para date a partir da variável DERasString em uma instância de main.Segurado
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
    Parse da idade e conversão para um array de String a partir da String novoSegurado.lerExtrato
     */
    public String[] lerIdadeDER(){
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
    Parse da data de filiação como String a partir da String novoSegurado.lerExtrato
     */
    public String lerFiliaString(){
        String filiaString = this.getExtrato();
        filiaString = filiaString.split("PERIODOS DE QUALIDADE DE SEGURADO\\S+: ")[1];
        filiaString = filiaString.split(" a")[0].trim();
        return filiaString;
    }

    /*
    Converte a data de filiação para date a partir da variável filiaString em uma instância de main.Segurado
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
    Parse da idade em 13/11/2019 e conversão em um Array com anos, meses e dias a partir da String novoSegurado.lerExtrato
     */
    public String[] lerIdadeEC(){
        String achaIdadeEC = this.getExtrato();
        achaIdadeEC = achaIdadeEC.split("Analise do direito em 13/11/2019")[1];
        achaIdadeEC = achaIdadeEC.split("Idade\\s+: ")[1];
        achaIdadeEC = achaIdadeEC.split("Soma")[0].trim();
        String[] arrayIdadeEC;
        arrayIdadeEC = achaIdadeEC.split(", ");
        arrayIdadeEC[0] = arrayIdadeEC[0].split("a")[0];
        arrayIdadeEC[1] = arrayIdadeEC[1].split("m")[0];
        arrayIdadeEC[2] = arrayIdadeEC[2].split("d")[0];
        return arrayIdadeEC;
    }

    /*
    Parse da carência em 13/11/2019 a partir da String novoSegurado.lerExtrato
     */
    public String lerCarenciaEC (){
        String CarenciaEC = this.getExtrato();
        CarenciaEC = CarenciaEC.split("Analise do direito em 13/11/2019")[1];
        CarenciaEC = CarenciaEC.split("Quantidade de carencia\\s+: ")[1];
        CarenciaEC = CarenciaEC.split("Idade")[0].trim();
        return CarenciaEC;
    }

    /*
    Parse direito adquirido em 13/11/2019 a partir da String novoSegurado.lerExtrato
     */
    public String lerDireitoAdquidoEC (){
        String direitoAdqEC = this.getExtrato();
        direitoAdqEC = direitoAdqEC.split("Direito a aposentadoria por idade antes da Emenda Constitucional 103/2019")[1];
        direitoAdqEC = direitoAdqEC.split("Possui direito nesta regra: ")[1];
        direitoAdqEC = direitoAdqEC.split("[^sn]")[0];
        return direitoAdqEC;
    }

    /*
    Parse da idade na data de veirificação do direito para art. 18 da EC 103/2019 a partir da String novoSegurado.lerExtrato
     */
    public String lerIdadeDtVerif(){
        String idadeDtVerif = this.getExtrato();
        idadeDtVerif = idadeDtVerif.split("Analise do direito em " +
                this.getDtVerifString())[1];
        idadeDtVerif = idadeDtVerif.split("Idade exigida: ")[1];
        idadeDtVerif = idadeDtVerif.split("\n")[0].trim();
        return idadeDtVerif;
    }

    /*
    Parse da idade na data de veirificação do direito para art. 18 da EC 103/2019
    a partir da String novoSegurado.lerExtrato e conversão em um Array com anos, meses e dias
     */
    public String[] lerIdadeCompDtVerif(){
        String IdadeCompDtVerif = this.getExtrato();
        IdadeCompDtVerif = IdadeCompDtVerif.split("Analise do direito em " +
                this.getDtVerifString())[1];
        IdadeCompDtVerif = IdadeCompDtVerif.split("Idade\\s+: ")[1];
        IdadeCompDtVerif = IdadeCompDtVerif.split("Soma")[0].trim();
        String[] arrayIdadeCompDtVerif = IdadeCompDtVerif.split(", ");
        arrayIdadeCompDtVerif[0] = arrayIdadeCompDtVerif[0].split("a")[0];
        arrayIdadeCompDtVerif[1] = arrayIdadeCompDtVerif[1].split("m")[0];
        arrayIdadeCompDtVerif[2] = arrayIdadeCompDtVerif[2].split("d")[0];
        return arrayIdadeCompDtVerif;
    }

    /*
    Parse da carência na data de veirificação do direito para art. 18 da EC 103/2019
    a partir da String novoSegurado.lerExtrato
     */
    public String lerCarenciaDtVerif(){
        String carenciaDtVerif = this.getExtrato();
        carenciaDtVerif = carenciaDtVerif.split("Analise do direito em " +
                this.getDtVerifString())[1];
        carenciaDtVerif = carenciaDtVerif.split("carencia\\s+: ")[1];
        carenciaDtVerif = carenciaDtVerif.split("Idade")[0].trim();
        return carenciaDtVerif;
    }

    /*
    Parse do tempo de contribuição na data de veirificação do direito para art. 18 da EC 103/2019
    a partir da String novoSegurado.lerExtrato
     */
    public String[] lerTempoContrDtVerif(){
        String tempoContrDtVerif = this.getExtrato();
        tempoContrDtVerif = tempoContrDtVerif.split("Analise do direito em " +
                this.getDtVerifString())[1];
        tempoContrDtVerif = tempoContrDtVerif.split("Total de tempo considerado : ")[1];
        tempoContrDtVerif = tempoContrDtVerif.split("\n")[0].trim();
        String[] arraytempoContrDtVerif = tempoContrDtVerif.split(", ");
        arraytempoContrDtVerif[0] = arraytempoContrDtVerif[0].split("a")[0];
        arraytempoContrDtVerif[1] = arraytempoContrDtVerif[1].split("m")[0];
        arraytempoContrDtVerif[2] = arraytempoContrDtVerif[2].split("d")[0];
        return arraytempoContrDtVerif;
    }

    /*
    Parse de direito na data de verificação do direito
    a partir da String novoSegurado.lerExtrato
     */
    public String lerDireitoDtVerif(){
        String dirDtVerif = this.getExtrato();
        dirDtVerif = dirDtVerif.split("Analise do direito em " +
                this.getDtVerifString())[1];
        dirDtVerif = dirDtVerif.split("Possui direito nesta data\\s+: ")[1];
        dirDtVerif = dirDtVerif.split("[^sn]")[0];
        return dirDtVerif;
    }


    /*
    Adiciona 1 ano à data de verificação do direito
     */
    public Date adicAnoDtVerif(){
        Calendar dtVerifCalendar = Calendar.getInstance();
        dtVerifCalendar.setTime(this.getDtVerif());
        dtVerifCalendar.add(Calendar.YEAR, 1);
        Date novaDtVerif = dtVerifCalendar.getTime();
        return novaDtVerif;
    }

    /*
    Parse de direito à aposentadoria programada
    a partir da String novoSegurado.lerExtrato
     */
    public String lerDireitoAposProg(){
        String dirAposProg = this.getExtrato();
        dirAposProg = dirAposProg.split("Regra geral do Art.19")[1];
        dirAposProg = dirAposProg.split("Possui direito nesta regra: ")[1];
        dirAposProg = dirAposProg.split("[^sn]")[0];
        return dirAposProg;
    }

    /*
    Parse de direito à aposentadoria por qualquer uma das regras
     */
    public String lerDireitoAposFinal(){
        String direitoAposFinal = this.getExtrato();
        direitoAposFinal = direitoAposFinal.split("Aposentadoria por idade convencional")[1];
        direitoAposFinal = direitoAposFinal.split("Possui direito neste perfil: ")[1];
        direitoAposFinal = direitoAposFinal.split("[^sn]")[0];
        if (direitoAposFinal.equals("s")){
            direitoAposFinal = "foi";
        }
        else direitoAposFinal = "não foi";
        return direitoAposFinal;
    }


}
