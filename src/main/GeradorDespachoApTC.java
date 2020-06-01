package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeradorDespachoApTC {

    /*
    Atributos
     */
    private List<String> despachoCompletoTC = new ArrayList<>(); //Texto completo do despacho
    private String stringDespachoCompletoTC; //Texto completo do despacho


    /*
    Getters e Setters
     */


    public String getDespachoCompletoTC(int index) {
        return this.despachoCompletoTC.get(index);
    }

    public List<String> getDespachoCompletoTC() {
        return this.despachoCompletoTC;
    }

    public void addDespachoCompletoTC(String despachoCompletoTC) {
        this.despachoCompletoTC.add(despachoCompletoTC);
    }

    public String getStringDespachoCompletoTC() {
        return stringDespachoCompletoTC;
    }

    public void setStringDespachoCompletoTC(String stringDespachoCompletoTC) {
        this.stringDespachoCompletoTC = stringDespachoCompletoTC;
    }


    /*
    Métodos para chamar parsing para aposentadoria por tempo de contribuição
     */

    /*
    Chama parse de valores para parte nível r1 - regra de aposentadoria programada art. 19 da EC 103/2019
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

        String[] pedagio = segurado.parsePedagio(segurado.getR1());
        segurado.addPedagio(pedagio);

        String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR1());
        segurado.addTempCompExigido(tempCompExigido);

        String[] tempCompPedagio = segurado.somaPedagioTempComp(segurado.getR1());
        segurado.addTempCompPedagio(tempCompPedagio);

        String pontuacaoExigida = segurado.parsePontuacaoExigida(segurado.getR1());
        segurado.addPontuacaoExigida(pontuacaoExigida);

        String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR1());
        segurado.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR1());
        segurado.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR1());
        segurado.addTempCompEfetivo(tempCompEfetivo);

        String[] pontuacaoEfetiva = segurado.parsePontuacaoEfetiva(segurado.getR1());
        segurado.addPontuacaoEfetiva(pontuacaoEfetiva);

        String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR1());
        segurado.addRecDireitoDataBase(recDireitoDataBase);
    }

    /*
    Chama parse de valores para parte nível r4 - regra de direito adquirido TC integral antes da EC 103/2019
     */
    public void parseAtributosR4(Segurado segurado) {

        segurado.setR4(1);

        String regra = segurado.retornaNomeRegraAnaliseDireito(segurado.getR4());
        segurado.addRegraAnaliseDireito(regra);

        String stringDataBase = segurado.parseDataBase(segurado.getR4());
        segurado.addStringDataBase(stringDataBase);

        Date dateDatabase = segurado.converteDataBaseDate(segurado.getR4());
        segurado.addDateDataBase(dateDatabase);

        String idadeExigida = "00 anos";
        segurado.addIdadeExigida(idadeExigida);

        String carenciaExigida = segurado.parseCarenciaExigida(segurado.getR4());
        segurado.addCarenciaExigida(carenciaExigida);

        String[] pedagio = segurado.parsePedagio(segurado.getR4());
        segurado.addPedagio(pedagio);

        String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR4());
        segurado.addTempCompExigido(tempCompExigido);

        String[] tempCompPedagio = segurado.somaPedagioTempComp(segurado.getR4());
        segurado.addTempCompPedagio(tempCompPedagio);

        String pontuacaoExigida = segurado.parsePontuacaoExigida(segurado.getR4());
        segurado.addPontuacaoExigida(pontuacaoExigida);

        String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR4());
        segurado.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR4());
        segurado.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR4());
        segurado.addTempCompEfetivo(tempCompEfetivo);

        String[] pontuacaoEfetiva = segurado.parsePontuacaoEfetiva(segurado.getR4());
        segurado.addPontuacaoEfetiva(pontuacaoEfetiva);

        String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR4());
        segurado.addRecDireitoDataBase(recDireitoDataBase);
    }

    /*
    Chama parse de valores para parte nível r5 - regra de direito adquirido TC proporcional antes da EC 103/2019
     */
    public void parseAtributosR5(Segurado segurado) {

        segurado.setR5(segurado.getR4() + 1);

        String regra = segurado.retornaNomeRegraAnaliseDireito(segurado.getR5());
        segurado.addRegraAnaliseDireito(regra);

        String stringDataBase = segurado.parseDataBase(segurado.getR5());
        segurado.addStringDataBase(stringDataBase);

        Date dateDatabase = segurado.converteDataBaseDate(segurado.getR5());
        segurado.addDateDataBase(dateDatabase);

        String idadeExigida = segurado.parseIdadeExigida(segurado.getR5());
        segurado.addIdadeExigida(idadeExigida);

        String carenciaExigida = segurado.parseCarenciaExigida(segurado.getR5());
        segurado.addCarenciaExigida(carenciaExigida);

        String[] pedagio = segurado.parsePedagio(segurado.getR5());
        segurado.addPedagio(pedagio);

        String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR5());
        segurado.addTempCompExigido(tempCompExigido);

        String pontuacaoExigida = segurado.parsePontuacaoExigida(segurado.getR5());
        segurado.addPontuacaoExigida(pontuacaoExigida);

        String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR5());
        segurado.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR5());
        segurado.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR5());
        segurado.addTempCompEfetivo(tempCompEfetivo);

        String[] tempCompPedagio = segurado.somaPedagioTempComp(segurado.getR5());
        segurado.addTempCompPedagio(tempCompPedagio);

        String[] pontuacaoEfetiva = segurado.parsePontuacaoEfetiva(segurado.getR5());
        segurado.addPontuacaoEfetiva(pontuacaoEfetiva);

        String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR5());
        segurado.addRecDireitoDataBase(recDireitoDataBase);
    }

    /*
    Chama parse de valores para parte nível r6 - regra transitória art. 15 da EC 103/2019
    aposentadoria por tempo de contribuicao com soma de idade e tempo
     */
    public void parseAtributosR6(Segurado segurado) {

        segurado.setR6(segurado.getR5() + 1);

        do {


        String regra = segurado.retornaNomeRegraAnaliseDireito(segurado.getR6());
        segurado.addRegraAnaliseDireito(regra);

        String stringDataBase = segurado.parseDataBase(segurado.getR6());
        segurado.addStringDataBase(stringDataBase);

        Date dateDatabase = segurado.converteDataBaseDate(segurado.getR6());
        segurado.addDateDataBase(dateDatabase);

        String idadeExigida = segurado.parseIdadeExigida(segurado.getR6());
        segurado.addIdadeExigida(idadeExigida);

        String carenciaExigida = segurado.parseCarenciaExigida(segurado.getR6());
        segurado.addCarenciaExigida(carenciaExigida);

        String[] pedagio = segurado.parsePedagio(segurado.getR6());
        segurado.addPedagio(pedagio);

        String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR6());
        segurado.addTempCompExigido(tempCompExigido);

        String pontuacaoExigida = segurado.parsePontuacaoExigida(segurado.getR6());
        segurado.addPontuacaoExigida(pontuacaoExigida);

        String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR6());
        segurado.addIdadeEfetiva(idadeEfetiva);

        String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR6());
        segurado.addCarenciaEfetiva(carenciaEfetiva);

        String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR6());
        segurado.addTempCompEfetivo(tempCompEfetivo);

        String[] tempCompPedagio = segurado.somaPedagioTempComp(segurado.getR6());
        segurado.addTempCompPedagio(tempCompPedagio);

        String[] pontuacaoEfetiva = segurado.parsePontuacaoEfetiva(segurado.getR6());
        segurado.addPontuacaoEfetiva(pontuacaoEfetiva);

        String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR6());
        segurado.addRecDireitoDataBase(recDireitoDataBase);

            if (segurado.getDateDataBase(segurado.getR6()).compareTo(segurado.getDER()) < 0) {
                segurado.setR6(segurado.getR6() + 1);
            } else {break;}

        } while (segurado.getDateDataBase(segurado.getR6() - 1).compareTo(segurado.getDER())<0);
    }

    /*
    Chama parse de valores para parte nível R7 - regra transitória art. 16 da EC 103/2019
    aposentadoria por tempo de contribuicao com idade mínima
     */
    public void parseAtributosR7(Segurado segurado) {

        segurado.setR7(segurado.getR6()+1);

        do {


            String regra = segurado.retornaNomeRegraAnaliseDireito(segurado.getR7());
            segurado.addRegraAnaliseDireito(regra);

            String stringDataBase = segurado.parseDataBase(segurado.getR7());
            segurado.addStringDataBase(stringDataBase);

            Date dateDatabase = segurado.converteDataBaseDate(segurado.getR7());
            segurado.addDateDataBase(dateDatabase);

            String idadeExigida = segurado.parseIdadeExigida(segurado.getR7());
            segurado.addIdadeExigida(idadeExigida);

            String carenciaExigida = segurado.parseCarenciaExigida(segurado.getR7());
            segurado.addCarenciaExigida(carenciaExigida);

            String[] pedagio = segurado.parsePedagio(segurado.getR7());
            segurado.addPedagio(pedagio);

            String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR7());
            segurado.addTempCompExigido(tempCompExigido);

            String pontuacaoExigida = segurado.parsePontuacaoExigida(segurado.getR7());
            segurado.addPontuacaoExigida(pontuacaoExigida);

            String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR7());
            segurado.addIdadeEfetiva(idadeEfetiva);

            String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR7());
            segurado.addCarenciaEfetiva(carenciaEfetiva);

            String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR7());
            segurado.addTempCompEfetivo(tempCompEfetivo);

            String[] tempCompPedagio = segurado.somaPedagioTempComp(segurado.getR7());
            segurado.addTempCompPedagio(tempCompPedagio);

            String[] pontuacaoEfetiva = segurado.parsePontuacaoEfetiva(segurado.getR7());
            segurado.addPontuacaoEfetiva(pontuacaoEfetiva);

            String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR7());
            segurado.addRecDireitoDataBase(recDireitoDataBase);

            if (segurado.getDateDataBase(segurado.getR7()).compareTo(segurado.getDER()) < 0) {
                segurado.setR7(segurado.getR7() + 1);
            } else {break;}

        } while (segurado.getDateDataBase(segurado.getR7() - 1).compareTo(segurado.getDER())<0);
    }

    /*
    Chama parse de valores para parte nível R8 - regra transitória art. 17 da EC 103/2019
    aposentadoria por tempo de contribuicao com pedagio de 50%
     */
    public void parseAtributosR8(Segurado segurado) {

        segurado.setR8(segurado.getR7()+1);

        do {


            String regra = segurado.retornaNomeRegraAnaliseDireito(segurado.getR8());
            segurado.addRegraAnaliseDireito(regra);

            String stringDataBase = segurado.parseDataBase(segurado.getR8());
            segurado.addStringDataBase(stringDataBase);

            Date dateDatabase = segurado.converteDataBaseDate(segurado.getR8());
            segurado.addDateDataBase(dateDatabase);

            String idadeExigida = segurado.parseIdadeExigida(segurado.getR8());
            segurado.addIdadeExigida(idadeExigida);

            String carenciaExigida = segurado.parseCarenciaExigida(segurado.getR8());
            segurado.addCarenciaExigida(carenciaExigida);

            String[] pedagio = segurado.parsePedagio(segurado.getR8());
            segurado.addPedagio(pedagio);

            String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR8());
            segurado.addTempCompExigido(tempCompExigido);

            String pontuacaoExigida = segurado.parsePontuacaoExigida(segurado.getR8());
            segurado.addPontuacaoExigida(pontuacaoExigida);

            String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR8());
            segurado.addIdadeEfetiva(idadeEfetiva);

            String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR8());
            segurado.addCarenciaEfetiva(carenciaEfetiva);

            String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR8());
            segurado.addTempCompEfetivo(tempCompEfetivo);

            String[] tempCompPedagio = segurado.somaPedagioTempComp(segurado.getR8());
            segurado.addTempCompPedagio(tempCompPedagio);

            String[] pontuacaoEfetiva = segurado.parsePontuacaoEfetiva(segurado.getR8());
            segurado.addPontuacaoEfetiva(pontuacaoEfetiva);

            String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR8());
            segurado.addRecDireitoDataBase(recDireitoDataBase);

            if (segurado.getDateDataBase(segurado.getR8()).compareTo(segurado.getDER()) < 0) {
                segurado.setR8(segurado.getR8() + 1);
            } else {break;}

        } while (segurado.getDateDataBase(segurado.getR8() - 1).compareTo(segurado.getDER())<0);
    }

    /*
    Chama parse de valores para parte nível R9 - regra transitória art. 20 da EC 103/2019
    aposentadoria por tempo de contribuicao com pedagio de 100%
     */
    public void parseAtributosR9(Segurado segurado) {

        segurado.setR9(segurado.getR8()+1);

        do {


            String regra = segurado.retornaNomeRegraAnaliseDireito(segurado.getR9());
            segurado.addRegraAnaliseDireito(regra);

            String stringDataBase = segurado.parseDataBase(segurado.getR9());
            segurado.addStringDataBase(stringDataBase);

            Date dateDatabase = segurado.converteDataBaseDate(segurado.getR9());
            segurado.addDateDataBase(dateDatabase);

            String idadeExigida = segurado.parseIdadeExigida(segurado.getR9());
            segurado.addIdadeExigida(idadeExigida);

            String carenciaExigida = segurado.parseCarenciaExigida(segurado.getR9());
            segurado.addCarenciaExigida(carenciaExigida);

            String[] pedagio = segurado.parsePedagio(segurado.getR9());
            segurado.addPedagio(pedagio);

            String[] tempCompExigido = segurado.parseTempCompExigido(segurado.getR9());
            segurado.addTempCompExigido(tempCompExigido);

            String pontuacaoExigida = segurado.parsePontuacaoExigida(segurado.getR9());
            segurado.addPontuacaoExigida(pontuacaoExigida);

            String[] idadeEfetiva = segurado.parseIdadeEfetiva(segurado.getR9());
            segurado.addIdadeEfetiva(idadeEfetiva);

            String carenciaEfetiva = segurado.parseCarenciaEfetiva(segurado.getR9());
            segurado.addCarenciaEfetiva(carenciaEfetiva);

            String[] tempCompEfetivo = segurado.parseTempCompEfetivo(segurado.getR9());
            segurado.addTempCompEfetivo(tempCompEfetivo);

            String[] tempCompPedagio = segurado.somaPedagioTempComp(segurado.getR9());
            segurado.addTempCompPedagio(tempCompPedagio);

            String[] pontuacaoEfetiva = segurado.parsePontuacaoEfetiva(segurado.getR9());
            segurado.addPontuacaoEfetiva(pontuacaoEfetiva);

            String recDireitoDataBase = segurado.parseRecDireitoDataBase(segurado.getR9());
            segurado.addRecDireitoDataBase(recDireitoDataBase);

            if (segurado.getDateDataBase(segurado.getR9()).compareTo(segurado.getDER()) < 0) {
                segurado.setR9(segurado.getR9() + 1);
            } else {break;}

        } while (segurado.getDateDataBase(segurado.getR9() - 1).compareTo(segurado.getDER())<0);
    }


    /*
    Método para escrever em String a parte 1 do despacho - Parte fixa com cabeçalho e dados básicos
     */
    public String escreverParte1(Segurado segur) {
        return
                Config.getOLatual() + "\n\n" +
                        "Nome d" + segur.getArtGenero() + " requerente: " + segur.getNome() + "\n\n" +
                        "Número do requerimento benefício: " + segur.getNB() + "\n\n" +
                        "Trata-se de requerimento de " + segur.getEspecieBeneficio() + "." + "\n\n" +
                        "A data de nascimento d" + segur.getArtGenero() + " requerente é " + segur.getStringDataNasc() +
                        ". A data de entrada do requerimento - DER é " +
                        segur.getStringDER() + ". Portanto, a idade na DER é de " +
                        segur.getIdadeDER()[0] + " anos, " + segur.getIdadeDER()[1] + " meses e " + segur.getIdadeDER()[2] + " dias" + ".\n\n" +
                        "Trata-se de requerente do sexo " + segur.getSexo() + ".\n\n" +
                        "O ingresso no RGPS ocorreu em " + segur.getStringDataFiliaAs() + ", " + segur.getAntesDepoisEC() +
                        " publicação da Emenda Constitucional 103/2019, em 13/11/2019. Assim, " + segur.getAtendeNaoAtEC() +
                        " ao primeiro requisito para análise quanto à concessão por direito adquirido à aposentadoria antes da publicação da " +
                        "Emenda Constitucional 103/2019 e/ou pela regras transitórias dos arts. 15, 16, 17 e 20 da citada emenda." + "\n";

    }

    /*
    Método para escrever os parágrafos de reconhecimento de direito a partir do número de índice fornecido
     */
    public String escreverParagrafoAnaliseDireito(Segurado segurado, int index) {
        return
                this.textoRegraAnaliseDireito(segurado, index) + "considerado até " +
                        segurado.getStringDataBase(index) + ", são necessários, cumulativamente, " +
                        segurado.getCarenciaExigida(index) + " contribuições para fins de carência, " +
                        segurado.getTempCompExigido(index)[0] + " anos, " +
                        segurado.getTempCompExigido(index)[1] + " meses e " +
                        segurado.getTempCompExigido(index)[2] + " dias de tempo de contribuição" +
                        this.testaPedagio(segurado, index) +
                        this.testaIdadeExigida(segurado, index) +
                        this.testaPontuacaoExigida(segurado, index) +
                        "Foram apuradas " +
                        segurado.getCarenciaEfetiva(index) + " contribuições para fins de carência, com " +
                        segurado.getTempCompEfetivo(index)[0] + " anos, " +
                        segurado.getTempCompEfetivo(index)[1] + " meses e " +
                        segurado.getTempCompEfetivo(index)[2] + " dias de tempo de contribuição " +
                        this.testaIdadeEfetiva(segurado, index) +
                        this.testaPontuacaoEfetiva(segurado, index) +
                        " Portanto, " +
                        segurado.getRecDireitoDataBase(index) + " direito à aposentadoria por esta regra até " +
                        segurado.getStringDataBase(index) + "." + "\n";
    }

    /*
    Retorna o texto com o nome da regra de análise do direito formatado para o despacho
     */
    public String textoRegraAnaliseDireito(Segurado segurado, int index) {
        String texto = "";
        if (index == segurado.getR1()) {
            texto = "Quanto à regra de aposentadoria programada art. 19 da Emenda Constitucional 103/2019, ";
        } else if (index == segurado.getR4()) {
            texto = "Quanto à regra de direito adquirido à aposentadoria integral antes da Emenda Constitucional 103/2019, ";
        } else if (index == segurado.getR5()) {
            texto = "Quanto à regra de direito adquirido à aposentadoria proporcional antes da Emenda Constitucional 103/2019, ";
        } else if (index > segurado.getR5() && index <= segurado.getR6()) {
            texto = "Quanto à regra transitória do art. 15 da Emenda Constitucional 103/2019, aposentadoria por tempo de contribuicao com soma de idade e tempo, ";
        } else if (index > segurado.getR6() && index <= segurado.getR7()) {
            texto = "Quanto à regra transitória do art. 16 da Emenda Constitucional 103/2019, aposentadoria por tempo de contribuicao com idade mínima, ";
        } else if (index == segurado.getR8()) {
            texto = "Quanto à regra transitória do art. 17 da Emenda Constitucional 103/2019, aposentadoria por tempo de contribuicao com pedagio de 50%, ";
        } else if (index == segurado.getR9()) {
            texto = "Quanto à regra transitória do art. 20 da Emenda Constitucional 103/2019, aposentadoria por tempo de contribuicao com pedagio de 100%, ";
        }
        return texto;
    }

    /*
    Testa se a regra a ser impressa leva em consideração a idade e retorna texto da idade EXIGIDA
     */
    public String testaIdadeExigida(Segurado segurado, int index) {
        if (index > segurado.getR6() && index <= segurado.getR7() | index == segurado.getR9()) {
            return " e " + segurado.getIdadeExigida(index) + " de idade";
        } else {
            return "";
        }
    }

    /*
    Testa se a regra a ser impressa leva em consideração a idade e retorna texto da idade EFETIVA
     */
    public String testaIdadeEfetiva(Segurado segurado, int index) {
        if (index > segurado.getR6() && index <= segurado.getR7() | index == segurado.getR9()) {
            return
                    " e " + segurado.getIdadeEfetiva(index)[0] + " anos, " +
                            segurado.getIdadeEfetiva(index)[1] + " meses e " +
                            segurado.getIdadeEfetiva(index)[2] + " dias de idade";
        } else { return "";
        }
    }

    /*
    Testa se a regra a ser impressa leva em consideração o pedágio e retorna pedágio e tempo + pedágio
     */
    public String testaPedagio(Segurado segurado, int index) {
        if (index == segurado.getR5() | index > segurado.getR7() && index <= segurado.getR9()) {
            return ", acrescidos de " +
                    segurado.getPedagio(index)[0] + " anos, " +
                    segurado.getPedagio(index)[1] + " meses e " +
                    segurado.getPedagio(index)[2] + " dias de pedágio, totalizando " +
                    segurado.getTempCompPedagio(index)[0] + " anos, " +
                    segurado.getTempCompPedagio(index)[1] + " meses e " +
                    segurado.getTempCompPedagio(index)[2] + " dias de tempo de contribuição exigido";
        }
        else {return "";}
    }

    /*
    Testa se a regra a ser impressa leva em consideração a pontuação e retorna texto da pontuação EXIGIDA
     */
    public String testaPontuacaoExigida(Segurado segurado, int index) {
        if (index > segurado.getR5() && index <= segurado.getR6()) {
            return " e " + segurado.getPontuacaoExigida(index) + " de pontuação correspondente à soma de idade" +
                    " e tempo de contribuição. ";
        } else {
            return ". ";
        }
    }

    /*
    Testa se a regra a ser impressa leva em consideração a pontuação e retorna texto da pontuação EFETIVA
     */
    public String testaPontuacaoEfetiva(Segurado segurado, int index) {
        if (index > segurado.getR5() && index <= segurado.getR6()) {
            return
                    "e pontuação correspondente a " +
                            segurado.getPontuacaoEfetiva(index)[0] + " anos, " +
                            segurado.getPontuacaoEfetiva(index)[1] + " meses e " +
                            segurado.getPontuacaoEfetiva(index)[2] + " dias. ";
        } else { return ". ";
        }
    }

    /*
    Método para escrever a parte final do despacho - reconhecimento de direito à aposentadoria por qualquer uma das regras
     */
    public String escreverParteFinal(Segurado segurado) {

        String tFinalCarencia = "apurada carência suficiente na DER para análise quanto aos demais requisitos para " +
                "concessão de aposentadoria por idade. ";
        String tFinalIdade = "idade suficiente na DER para análise quanto aos demais requisitos para " +
                "concessão de aposentadoria por idade. " + "\n\n";
        String tFinal =
                "Pelo exposto, " + segurado.getRecDireitoFinalTC() +
                " à concessão da aposentadoria por tempo de contribuição." + "\n\n";
        if (segurado.getRecDireitoFinalTC().startsWith("não")) {
            if (segurado.fazPreAnaliseCarencia()) {
                tFinalCarencia = "Foi  " + tFinalCarencia;
                if (segurado.fazPreAnaliseIdade()) {
                    tFinalIdade = "Também tem  " + tFinalIdade;
                } else {
                    tFinalIdade = "Contudo, não " + tFinalIdade;
                }
                tFinalCarencia = tFinalCarencia + tFinalIdade;
            } else {
                tFinalCarencia = "Não foi  " + tFinalCarencia + "\n\n";
            }
            tFinal = tFinal + tFinalCarencia;
        }

        return tFinal;
    }
}
