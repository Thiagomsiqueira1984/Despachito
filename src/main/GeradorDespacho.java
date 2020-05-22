package main;


public class GeradorDespacho {

    /*
    Atributos
     */

    /*
    Getters e Setters
     */

    /*
    Método para escrever em String a parte 1 do despacho - Parte fixa com cabeçalho e dados básicos
     */
    public String escreverParte1(Segurado segur){
        return
            "23.001.820 – Central Especializada de Alta Performance Aposentadoria por Idade\n" +
                "\n" +
                "Nome d"+segur.getArtGenero() + " requerente: " + segur.getNome() + "\n" +
                "\n" +
                "Trata-se de requerimento de aposentadoria por idade.\n" +
                "\n" +
                "A data de nascimento d" + segur.getArtGenero() + " requerente é " + segur.getDataNascAsString() +
                ". A data de entrada do requerimento – DER é " +
                segur.getDERasString() + ". Portanto, a idade na DER é de " +
                segur.getIdadeDER()[0] + " anos, "+segur.getIdadeDER()[1] + " meses e "+segur.getIdadeDER()[2] + " dias"+".\n" +
                "\n" +
                "Trata-se de requerente do sexo "+segur.getSexo()+".\n" +
                "\n" +
                "O ingresso no RGPS ocorreu em " + segur.getDataFiliaAsString() + ", " + segur.getAntesDepoisEC() +
                " publicação da Emenda Constitucional 103/2019, em 13/11/2019. Assim, "+segur.getAtendeNaoAtEC() +
                " ao primeiro requisito para análise quanto à concessão por direito adquirido à aposentadoria antes da publicação da " +
                "Emenda Constitucional 103/2019 e/ou pela regra transitória do art. 18 da citada emenda." + "\n";

    }

    /*
    Método para escrever em String a parte 2 do despacho - Regra do direito adquirido antes de 13/11/2019 EC 103/2019
     */
    public String escreverR2(Segurado gSegur, int index){
        return
            "Quanto ao direito adquirido à aposentadoria antes da publicação da Emenda Constitucional 103/2019, são necessários, cumulativamente, " +
                gSegur.getIdadeExigida(index) + " anos de idade e 180 contribuições para fins de carência completos até 13/11/2019. A idade em 13/11/2019 era de "+
                gSegur.getIdadeEfetiva(index)[0] + " anos, " +
                gSegur.getIdadeEfetiva(index)[1] + " meses e " +
                gSegur.getIdadeEfetiva(index)[2] + " dias, e a carência apurada até esta data foi de " +
                gSegur.getCarenciaEfetiva(index) + " contribuições. Portanto, " +
                gSegur.getRecDireitoDataBase(index) + " adquirido antes da publicação da Emenda Constitucional 103/2019.";
    }

    /*
    Método para escrever os parágrafos de reconhecimento de direito a partir do número de índice fornecido
      */
    public  String escreverParagrafoAnaliseDireito(Segurado gSegur, int index){
        return
            this.textoRegraAnaliseDireito(gSegur, index) + "considerado até " +
                gSegur.getStringDataBase(index) + ", são necessários, cumulativamente, " +
                gSegur.getIdadeExigida(index) + " de idade, com " +
                gSegur.getCarenciaEfetiva(index)+ " contribuições para fins de carência" +
                this.testaTempCompExigido(gSegur, index) + "Foram apuradas a idade de "+
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
        }else if (index == gSegur.getR3()) {
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
        } else { return ". "; }
    }

    /*
    Testa se a regra a ser impressa leva em consideração o tempo de contribuição e retorna texto do tempo de contribuição efetivo
     */
    public String testaTempCompEfetivo(Segurado gSegur, int index) {
        if (index != gSegur.getR2()) {
            return
                    "e " + gSegur.getTempCompEfetivo(index)[0] + " anos, " +
                            gSegur.getTempCompEfetivo(index)[1] + " meses e " +
                            gSegur.getTempCompEfetivo(index)[2] + " dias de tempo de contribuição. ";
        } else { return ". "; }
    }

}
