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
    Métodos para chamar parsing para aposentadoria por idade
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
    Chama parse de valores para parte nível r2 - regra de direito adquirido antes da EC 103/2019
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
    Chama parse de valores para parte nível r3 - Regra transitoria do Art.18 da EC 103/2019
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
    Método para escrever em String a parte 1 do despacho - Parte fixa com cabeçalho e dados básicos
     */
    public String escreverParte1(Segurado segurado) {
        return
                Config.getOLatual() + "\n\n" +
                        "Nome d" + segurado.getArtGenero() + " requerente: " + segurado.getNome() + "\n\n" +
                        "Número do requerimento benefício: " + segurado.getNB() + "\n\n" +
                        "Trata-se de requerimento de " + segurado.getEspecieBeneficio() + "." + "\n\n" +
                        "A data de nascimento d" + segurado.getArtGenero() + " requerente é " + segurado.getStringDataNasc() +
                        ". A data de entrada do requerimento - DER é " +
                        segurado.getStringDER() + ". A data de início do benefício - DIB, se for reconhecido o direito à concessão, é " +
                        segurado.getStringDIB() + ". Portanto, a idade na DIB é de " +
                        segurado.getIdadeDIB()[0] + " anos, " + segurado.getIdadeDIB()[1] + " meses e " + segurado.getIdadeDIB()[2] + " dias" + ".\n\n" +
                        "Trata-se de requerente do sexo " + segurado.getSexo() + ".\n\n" +
                        "O ingresso no RGPS ocorreu em " + segurado.getStringDataFiliaAs() + ", " + segurado.getAntesDepoisEC() +
                        " publicação da Emenda Constitucional 103/2019, em 13/11/2019. Assim, " + segurado.getAtendeNaoAtEC() +
                        " ao primeiro requisito para análise quanto à concessão por direito adquirido à aposentadoria antes da publicação da " +
                        "Emenda Constitucional 103/2019 e/ou pela regra transitória do art. 18 da citada emenda." + "\n";

    }

    /*
    Método para escrever os parágrafos de reconhecimento de direito a partir do número de índice fornecido
     */
    public String escreverParagrafoAnaliseDireito(Segurado segurado, int index) {
        return
                this.textoRegraAnaliseDireito(segurado, index) + "considerado até " +
                segurado.getStringDataBase(index) + ", são necessários, cumulativamente, " +
                segurado.getIdadeExigida(index) + " de idade, com " +
                segurado.getCarenciaExigida(index) + " contribuições para fins de carência" +
                this.testaTempCompExigido(segurado, index) + "Foram apuradas a idade de " +
                segurado.getIdadeEfetiva(index)[0] + " anos, " +
                segurado.getIdadeEfetiva(index)[1] + " meses e " +
                segurado.getIdadeEfetiva(index)[2] + " dias, com " +
                segurado.getCarenciaEfetiva(index) + " contribuições para fins de carência" +
                this.testaTempCompEfetivo(segurado, index) + "Portanto, " +
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
        } else if (index == segurado.getR2()) {
            texto = "Quanto à regra de direito adquirido antes da Emenda Constitucional 103/2019, ";
        } else {
            texto = "Quanto à regra transitória do art. 18 da Emenda Constitucional 103/2019, ";
        }
        return texto;
    }

    /*
    Testa se a regra a ser impressa leva em consideração o tempo de contribuição e retorna texto do tempo de
     contribuição EXIGIDO
     */
    public String testaTempCompExigido(Segurado segurado, int index) {
        if (index != segurado.getR2()) {
            return
                    " e " + segurado.getTempCompExigido(index)[0] + " anos, " +
                            segurado.getTempCompExigido(index)[1] + " meses e " +
                            segurado.getTempCompExigido(index)[2] + " dias de tempo de contribuição. ";
        } else {
            return ". ";
        }
    }

    /*
    Testa se a regra a ser impressa leva em consideração o tempo de contribuição e retorna texto do tempo de
     contribuição EFETIVO
     */
    public String testaTempCompEfetivo(Segurado segurado, int index) {
        if (index != segurado.getR2()) {
            return
                    " e " + segurado.getTempCompEfetivo(index)[0] + " anos, " +
                            segurado.getTempCompEfetivo(index)[1] + " meses e " +
                            segurado.getTempCompEfetivo(index)[2] + " dias de tempo de contribuição. ";
        } else {
            return ". ";
        }
    }

    /*
    Método para escrever a parte final do despacho - reconhecimento de direito à aposentadoria por qualquer uma das regras
     */
    public String escreverParteFinal(Segurado segurado) {

        String tFinalCarencia = "apurada carência suficiente na DIB para análise quanto aos demais requisitos para " +
                "concessão de aposentadoria por tempo de contribuição. ";
        String tFinalTC = "tempo de contribuição suficiente na DIB para análise quanto aos demais requisitos para " +
                "concessão de aposentadoria por tempo de contribuição. " + "\n\n";
        String tFinal =
                "Pelo exposto, " + segurado.getRecDireitoFinalIdade() +
                " à concessão da aposentadoria por idade." + "\n\n";

        if (segurado.getRecDireitoFinalIdade().startsWith("não")) {
            if (segurado.fazPreAnaliseCarencia()) {
                tFinalCarencia = "Foi  " + tFinalCarencia;
                if (segurado.fazPreAnaliseTC()) {
                    tFinalTC = "Também tem  " + tFinalTC;
                    segurado.setCabeAnaliseDtoOutraEspecie(true);
                } else {
                    tFinalTC = "Contudo, não tem " + tFinalTC;
                }
                tFinalCarencia = tFinalCarencia + tFinalTC;
            } else {
                tFinalCarencia = "Não foi  " + tFinalCarencia + "\n\n";
            }
            tFinal = tFinal + tFinalCarencia;
        }

        return tFinal;
    }

}
