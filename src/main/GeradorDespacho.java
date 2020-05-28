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
    Método para escrever em String a parte 1 do despacho - Parte fixa com cabeçalho e dados básicos
     */
    public String escreverParte1(Segurado segur) {
        return
                "23.001.820 ? Central Especializada de Alta Performance Aposentadoria por Idade" + "\n\n" +
                        "Nome d" + segur.getArtGenero() + " requerente: " + segur.getNome() + "\n\n" +
                        "Número do requerimento benefício: " + segur.getNB() + "\n\n" +
                        "Trata-se de requerimento de " + segur.getEspecieBeneficio() + "." + "\n\n" +
                        "A data de nascimento d" + segur.getArtGenero() + " requerente é " + segur.getStringDataNasc() +
                        ". A data de entrada do requerimento ? DER é " +
                        segur.getStringDER() + ". Portanto, a idade na DER é de " +
                        segur.getIdadeDER()[0] + " anos, " + segur.getIdadeDER()[1] + " meses e " + segur.getIdadeDER()[2] + " dias" + ".\n\n" +
                        "Trata-se de requerente do sexo " + segur.getSexo() + ".\n\n" +
                        "O ingresso no RGPS ocorreu em " + segur.getStringDataFiliaAs() + ", " + segur.getAntesDepoisEC() +
                        " publicação da Emenda Constitucional 103/2019, em 13/11/2019. Assim, " + segur.getAtendeNaoAtEC() +
                        " ao primeiro requisito para análise quanto à concessão por direito adquirido à aposentadoria antes da publicação da " +
                        "Emenda Constitucional 103/2019 e/ou pela regra transitória do art. 18 da citada emenda." + "\n\n";

    }

    /*
    Método para escrever os parágrafos de reconhecimento de direito a partir do número de índice fornecido
     */
    public String escreverParagrafoAnaliseDireito(Segurado gSegur, int index) {
        return
                this.textoRegraAnaliseDireito(gSegur, index) + "considerado até " +
                gSegur.getStringDataBase(index) + ", são necessários, cumulativamente, " +
                gSegur.getIdadeExigida(index) + " de idade, com " +
                gSegur.getCarenciaExigida(index) + " contribuições para fins de carência" +
                this.testaTempCompExigido(gSegur, index) + "Foram apuradas a idade de " +
                gSegur.getIdadeEfetiva(index)[0] + " anos, " +
                gSegur.getIdadeEfetiva(index)[1] + " meses e " +
                gSegur.getIdadeEfetiva(index)[2] + " dias, com " +
                gSegur.getCarenciaEfetiva(index) + " contribuições para fins de carência" +
                this.testaTempCompEfetivo(gSegur, index) + "Portanto, " +
                gSegur.getRecDireitoDataBase(index) + " direito à aposentadoria por esta regra até " +
                gSegur.getStringDataBase(index) + "." + "\n";
    }

    /*
    Retorna o texto com o nome da regra de análise do direito formatado para o despacho
     */
    public String textoRegraAnaliseDireito(Segurado gSegur, int index) {
        String texto = "";
        if (index == gSegur.getR1()) {
            texto = "Quanto à regra de aposentadoria programada art. 19 da Emenda Constitucional 103/2019, ";
        } else if (index == gSegur.getR2()) {
            texto = "Quanto à regra de direito adquirido antes da Emenda Constitucional 103/2019, ";
        } else {
            texto = "Quanto à regra transitória, art. 18 da Emenda Constitucional 103/2019, ";
        }
        return texto;
    }

    /*
    Testa se a regra a ser impressa leva em consideração o tempo de contribuição e retorna texto do tempo de contribuição exigido
     */
    public String testaTempCompExigido(Segurado gSegur, int index) {
        if (index != gSegur.getR2()) {
            return
                    " e " + gSegur.getTempCompExigido(index)[0] + " anos, " +
                            gSegur.getTempCompExigido(index)[1] + " meses e " +
                            gSegur.getTempCompExigido(index)[2] + " dias de tempo de contribuição. ";
        } else {
            return ". ";
        }
    }

    /*
    Testa se a regra a ser impressa leva em consideração o tempo de contribuição e retorna texto do tempo de contribuição efetivo
     */
    public String testaTempCompEfetivo(Segurado gSegur, int index) {
        if (index != gSegur.getR2()) {
            return
                    " e " + gSegur.getTempCompEfetivo(index)[0] + " anos, " +
                            gSegur.getTempCompEfetivo(index)[1] + " meses e " +
                            gSegur.getTempCompEfetivo(index)[2] + " dias de tempo de contribuição. ";
        } else {
            return ". ";
        }
    }

    /*
    Método para escrever a parte final do despacho - reconhecimento de direito à aposentadoria por qualquer uma das regras
     */
    public String escreverParteFinal(Segurado gSegur) {
        return "Pelo exposto, " + gSegur.getRecDireitoFinal() + " à concessão da aposentadoria por idade.";
    }

}
