package main;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeradorDespachoApIdade {

    /*
    Atributos
     */
    private List<String> despachoCompletoIdade = new ArrayList<>(); //Texto completo do despacho
    private String stringDespachoCompletoIdade; //Texto completo do despacho


    /*
    Getters e Setters
     */


    public String getDespachoCompletoIdade(int index) {
        return this.despachoCompletoIdade.get(index);
    }

    public List<String> getDespachoCompletoIdade() {
        return this.despachoCompletoIdade;
    }

    public void addDespachoCompletoIdade(String despachoCompletoIdade) {
        this.despachoCompletoIdade.add(despachoCompletoIdade);
    }

    public String getStringDespachoCompletoIdade() {
        return stringDespachoCompletoIdade;
    }

    public void setStringDespachoCompletoIdade(String stringDespachoCompletoIdade) {
        this.stringDespachoCompletoIdade = stringDespachoCompletoIdade;
    }


    /*
    M�todos para chamar parsing para aposentadoria por idade
     */


    /*
    Chama parse de valores para parte n�vel r1 - regra de aposentadoria programada art. 19 da EC 103/2019
    */
    public void parseAtributosR1(Segurado segurado) {

        segurado.setR1(0);

        String regra = segurado.retornaNomeRegraAnaliseDireito(segurado.getR1());
        segurado.addRegraAnaliseDireito(regra);

        String stringDataBase = segurado.parseDataBase(segurado.getR1());
        segurado.addStringDataBase(stringDataBase);

        Date dateDatabase = segurado.converteDataBaseDate(segurado.getR1());
        segurado.addDateDataBase(dateDatabase);

        String idadeExigida = segurado.parseIdadeExigida(segurado.getR1());
        segurado.addIdadeExigida(idadeExigida);

        String carenciaExigida = segurado.parseCarenciaExigida(segurado.getR1());
        segurado.addCarenciaExigida(carenciaExigida);

        String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR1());
        segurado.addTempCompExigido(tempCompExigido);

        String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR1());
        segurado.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR1());
        segurado.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR1());
        segurado.addTempCompEfetivo(tempCompEfetivo);

        String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR1());
        segurado.addRecDireitoDataBase(recDireitoDataBase);
    }

    /*
    Chama parse de valores para parte n�vel r2 - regra de direito adquirido antes da EC 103/2019
     */
    public void parseAtributosR2(Segurado segurado) {

        segurado.setR2(1);

        String regra = segurado.retornaNomeRegraAnaliseDireito(segurado.getR2());
        segurado.addRegraAnaliseDireito(regra);

        String stringDataBase = segurado.parseDataBase(segurado.getR2());
        segurado.addStringDataBase(stringDataBase);

        Date dateDatabase = segurado.converteDataBaseDate(segurado.getR2());
        segurado.addDateDataBase(dateDatabase);

        String idadeExigida = segurado.parseIdadeExigida(segurado.getR2());
        segurado.addIdadeExigida(idadeExigida);

        String carenciaExigida = segurado.parseCarenciaExigida(segurado.getR2());
        segurado.addCarenciaExigida(carenciaExigida);

        String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR2());
        segurado.addTempCompExigido(tempCompExigido);

        String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR2());
        segurado.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR2());
        segurado.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR2());
        segurado.addTempCompEfetivo(tempCompEfetivo);

        String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR2());
        segurado.addRecDireitoDataBase(recDireitoDataBase);

    }

    /*
    Chama parse de valores para parte n�vel r3 - Regra transitoria do Art.18 da EC 103/2019
     */
    public void parseAtributosR3(Segurado segurado) {

        segurado.setR3(2);

        do {
            String regra = segurado.retornaNomeRegraAnaliseDireito(segurado.getR3());
            segurado.addRegraAnaliseDireito(regra);

            String stringDataBase = segurado.parseDataBase(segurado.getR3());
            segurado.addStringDataBase(stringDataBase);

            Date dateDatabase = segurado.converteDataBaseDate(segurado.getR3());
            segurado.addDateDataBase(dateDatabase);

            String idadeExigida = segurado.parseIdadeExigida(segurado.getR3());
            segurado.addIdadeExigida(idadeExigida);

            String carenciaExigida = segurado.parseCarenciaExigida(segurado.getR3());
            segurado.addCarenciaExigida(carenciaExigida);

            String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR3());
            segurado.addTempCompExigido(tempCompExigido);

            String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR3());
            segurado.addIdadeEfetiva(idadeEfetiva);

            String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR3());
            segurado.addCarenciaEfetiva(carenciaEfetiva);

            String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR3());
            segurado.addTempCompEfetivo(tempCompEfetivo);

            String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR3());
            segurado.addRecDireitoDataBase(recDireitoDataBase);

            if (segurado.getDateDataBase(segurado.getR3()).compareTo(segurado.getDIB()) < 0) {
                segurado.setR3(segurado.getR3() + 1);
            } else {break;}

        } while (segurado.getDateDataBase(segurado.getR3() - 1).compareTo(segurado.getDIB())<0);

        segurado.setR3(segurado.getR3() - 1);
    }

    /*
    M�todo para escrever em String a parte 1 do despacho - Parte fixa com cabe�alho e dados b�sicos
     */
    public String escreverParte1(Segurado segurado) {
        return
                Config.getOLatual() + "\n\n" +
                        "Nome d" + segurado.getArtGenero() + " requerente: " + segurado.getNome() + "\n\n" +
                        "N�mero do requerimento benef�cio: " + segurado.getNB() + "\n\n" +
                        "Trata-se de requerimento de " + segurado.getEspecieBeneficio() + "." + "\n\n" +
                        "A data de nascimento d" + segurado.getArtGenero() + " requerente � " + segurado.getStringDataNasc() +
                        ". A data de entrada do requerimento - DER � " +
                        segurado.getStringDER() + ". A data de in�cio do benef�cio - DIB, se for reconhecido o direito � concess�o, � " +
                        segurado.getStringDIB() + ". Portanto, a idade na DIB � de " +
                        segurado.getIdadeDIB()[0] + " anos, " + segurado.getIdadeDIB()[1] + " meses e " + segurado.getIdadeDIB()[2] + " dias" + ".\n\n" +
                        "Trata-se de requerente do sexo " + segurado.getSexo() + ".\n\n" +
                        "O ingresso no RGPS ocorreu em " + segurado.getStringDataFiliaAs() + ", " + segurado.getAntesDepoisEC() +
                        " publica��o da Emenda Constitucional 103/2019, em 13/11/2019. Assim, " + segurado.getAtendeNaoAtEC() +
                        " ao primeiro requisito para an�lise quanto � concess�o por direito adquirido � aposentadoria antes da publica��o da " +
                        "Emenda Constitucional 103/2019 e/ou pela regra transit�ria do art. 18 da citada emenda." + "\n";

    }

    /*
    M�todo para escrever os par�grafos de reconhecimento de direito a partir do n�mero de �ndice fornecido
     */
    public String escreverParagrafoAnaliseDireito(Segurado segurado, int index) {
        return
                this.textoRegraAnaliseDireito(segurado, index) + "considerado at� " +
                segurado.getStringDataBase(index) + ", s�o necess�rios, cumulativamente, " +
                segurado.getIdadeExigida(index) + " de idade, com " +
                segurado.getCarenciaExigida(index) + " contribui��es para fins de car�ncia" +
                this.testaTempCompExigido(segurado, index) + "Foram apuradas a idade de " +
                segurado.getIdadeEfetiva(index)[0] + " anos, " +
                segurado.getIdadeEfetiva(index)[1] + " meses e " +
                segurado.getIdadeEfetiva(index)[2] + " dias, com " +
                segurado.getCarenciaEfetiva(index) + " contribui��es para fins de car�ncia" +
                this.testaTempCompEfetivo(segurado, index) + "Portanto, " +
                segurado.getRecDireitoDataBase(index) + " direito � aposentadoria por esta regra at� " +
                segurado.getStringDataBase(index) + "." + "\n";
    }

    /*
    Retorna o texto com o nome da regra de an�lise do direito formatado para o despacho
     */
    public String textoRegraAnaliseDireito(Segurado segurado, int index) {
        String texto = "";
        if (index == segurado.getR1()) {
            texto = "Quanto � regra de aposentadoria programada art. 19 da Emenda Constitucional 103/2019, ";
        } else if (index == segurado.getR2()) {
            texto = "Quanto � regra de direito adquirido antes da Emenda Constitucional 103/2019, ";
        } else {
            texto = "Quanto � regra transit�ria do art. 18 da Emenda Constitucional 103/2019, ";
        }
        return texto;
    }

    /*
    Testa se a regra a ser impressa leva em considera��o o tempo de contribui��o e retorna texto do tempo de
     contribui��o EXIGIDO
     */
    public String testaTempCompExigido(Segurado segurado, int index) {
        if (index != segurado.getR2()) {
            return
                    " e " + segurado.getTempCompExigido(index)[0] + " anos, " +
                            segurado.getTempCompExigido(index)[1] + " meses e " +
                            segurado.getTempCompExigido(index)[2] + " dias de tempo de contribui��o. ";
        } else {
            return ". ";
        }
    }

    /*
    Testa se a regra a ser impressa leva em considera��o o tempo de contribui��o e retorna texto do tempo de
     contribui��o EFETIVO
     */
    public String testaTempCompEfetivo(Segurado segurado, int index) {
        if (index != segurado.getR2()) {
            return
                    " e " + segurado.getTempCompEfetivo(index)[0] + " anos, " +
                            segurado.getTempCompEfetivo(index)[1] + " meses e " +
                            segurado.getTempCompEfetivo(index)[2] + " dias de tempo de contribui��o. ";
        } else {
            return ". ";
        }
    }

    /*
    M�todo para escrever a parte final do despacho - reconhecimento de direito � aposentadoria por qualquer uma das regras
     */
    public String escreverParteFinal(Segurado segurado) {

        String tFinalCarencia = "apurada car�ncia suficiente na DIB para an�lise quanto aos demais requisitos para " +
                "concess�o de aposentadoria por tempo de contribui��o. ";
        String tFinalTC = "tempo de contribui��o suficiente na DIB para an�lise quanto aos demais requisitos para " +
                "concess�o de aposentadoria por tempo de contribui��o. " + "\n\n";
        String tFinal =
                "Pelo exposto, " + segurado.getRecDireitoFinalIdade() +
                " � concess�o da aposentadoria por idade." + "\n\n";

        if (segurado.getRecDireitoFinalIdade().startsWith("n�o")) {
            if (segurado.fazPreAnaliseCarencia()) {
                tFinalCarencia = "Foi  " + tFinalCarencia;
                if (segurado.fazPreAnaliseTC()) {
                    tFinalTC = "Tamb�m tem  " + tFinalTC;
                    segurado.setCabeAnaliseDtoOutraEspecie(true);
                } else {
                    tFinalTC = "Contudo, n�o tem " + tFinalTC;
                }
                tFinalCarencia = tFinalCarencia + tFinalTC;
            } else {
                tFinalCarencia = "N�o foi  " + tFinalCarencia + "\n\n";
            }
            tFinal = tFinal + tFinalCarencia;
        }

        return tFinal;
    }

}
