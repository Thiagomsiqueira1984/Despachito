package main;


import java.util.ArrayList;
import java.util.List;

public class GeradorDespacho {

    /*
    Atributos
     */
    private List<String> despachoCompleto = new ArrayList<>(); //Texto completo do despacho
    private String stringDespachoCompleto; //Texto completo do despacho




    /*
    Getters e Setters
     */

    public String getDespachoCompleto(int index) {
        return this.despachoCompleto.get(index);
    }

    public List<String> getDespachoCompleto() {
        return this.despachoCompleto;
    }

    public void addDespachoCompleto(String despachoCompleto) {
        this.despachoCompleto.add(despachoCompleto);
    }

    public String getStringDespachoCompleto() {
        return stringDespachoCompleto;
    }

    public void setStringDespachoCompleto(String stringDespachoCompleto) {
        this.stringDespachoCompleto = stringDespachoCompleto;
    }



    /*
    M�todo para escrever em String a parte 1 do despacho - Parte fixa com cabe�alho e dados b�sicos
     */
    public String escreverParte1(Segurado segur) {
        return
                "23.001.820 ? Central Especializada de Alta Performance Aposentadoria por Idade" + "\n\n" +
                        "Nome d" + segur.getArtGenero() + " requerente: " + segur.getNome() + "\n\n" +
                        "N�mero do requerimento benef�cio: " + segur.getNB() + "\n\n" +
                        "Trata-se de requerimento de " + segur.getEspecieBeneficio() + "." + "\n\n" +
                        "A data de nascimento d" + segur.getArtGenero() + " requerente � " + segur.getStringDataNasc() +
                        ". A data de entrada do requerimento ? DER � " +
                        segur.getStringDER() + ". Portanto, a idade na DER � de " +
                        segur.getIdadeDER()[0] + " anos, " + segur.getIdadeDER()[1] + " meses e " + segur.getIdadeDER()[2] + " dias" + ".\n\n" +
                        "Trata-se de requerente do sexo " + segur.getSexo() + ".\n\n" +
                        "O ingresso no RGPS ocorreu em " + segur.getStringDataFiliaAs() + ", " + segur.getAntesDepoisEC() +
                        " publica��o da Emenda Constitucional 103/2019, em 13/11/2019. Assim, " + segur.getAtendeNaoAtEC() +
                        " ao primeiro requisito para an�lise quanto � concess�o por direito adquirido � aposentadoria antes da publica��o da " +
                        "Emenda Constitucional 103/2019 e/ou pela regra transit�ria do art. 18 da citada emenda." + "\n\n";

    }

    /*
    M�todo para escrever os par�grafos de reconhecimento de direito a partir do n�mero de �ndice fornecido
     */
    public String escreverParagrafoAnaliseDireito(Segurado gSegur, int index) {
        return
                this.textoRegraAnaliseDireito(gSegur, index) + "considerado at� " +
                gSegur.getStringDataBase(index) + ", s�o necess�rios, cumulativamente, " +
                gSegur.getIdadeExigida(index) + " de idade, com " +
                gSegur.getCarenciaExigida(index) + " contribui��es para fins de car�ncia" +
                this.testaTempCompExigido(gSegur, index) + "Foram apuradas a idade de " +
                gSegur.getIdadeEfetiva(index)[0] + " anos, " +
                gSegur.getIdadeEfetiva(index)[1] + " meses e " +
                gSegur.getIdadeEfetiva(index)[2] + " dias, com " +
                gSegur.getCarenciaEfetiva(index) + " contribui��es para fins de car�ncia" +
                this.testaTempCompEfetivo(gSegur, index) + "Portanto, " +
                gSegur.getRecDireitoDataBase(index) + " direito � aposentadoria por esta regra at� " +
                gSegur.getStringDataBase(index) + "." + "\n";
    }

    /*
    Retorna o texto com o nome da regra de an�lise do direito formatado para o despacho
     */
    public String textoRegraAnaliseDireito(Segurado gSegur, int index) {
        String texto = "";
        if (index == gSegur.getR1()) {
            texto = "Quanto � regra de aposentadoria programada art. 19 da Emenda Constitucional 103/2019, ";
        } else if (index == gSegur.getR2()) {
            texto = "Quanto � regra de direito adquirido antes da Emenda Constitucional 103/2019, ";
        } else {
            texto = "Quanto � regra transit�ria, art. 18 da Emenda Constitucional 103/2019, ";
        }
        return texto;
    }

    /*
    Testa se a regra a ser impressa leva em considera��o o tempo de contribui��o e retorna texto do tempo de contribui��o exigido
     */
    public String testaTempCompExigido(Segurado gSegur, int index) {
        if (index != gSegur.getR2()) {
            return
                    " e " + gSegur.getTempCompExigido(index)[0] + " anos, " +
                            gSegur.getTempCompExigido(index)[1] + " meses e " +
                            gSegur.getTempCompExigido(index)[2] + " dias de tempo de contribui��o. ";
        } else {
            return ". ";
        }
    }

    /*
    Testa se a regra a ser impressa leva em considera��o o tempo de contribui��o e retorna texto do tempo de contribui��o efetivo
     */
    public String testaTempCompEfetivo(Segurado gSegur, int index) {
        if (index != gSegur.getR2()) {
            return
                    " e " + gSegur.getTempCompEfetivo(index)[0] + " anos, " +
                            gSegur.getTempCompEfetivo(index)[1] + " meses e " +
                            gSegur.getTempCompEfetivo(index)[2] + " dias de tempo de contribui��o. ";
        } else {
            return ". ";
        }
    }

    /*
    M�todo para escrever a parte final do despacho - reconhecimento de direito � aposentadoria por qualquer uma das regras
     */
    public String escreverParteFinal(Segurado gSegur) {
        return "Pelo exposto, " + gSegur.getRecDireitoFinal() + " � concess�o da aposentadoria por idade.";
    }

}
