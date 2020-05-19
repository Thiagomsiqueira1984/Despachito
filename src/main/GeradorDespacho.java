package main;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;
import java.text.SimpleDateFormat;


public class GeradorDespacho {

    /*
    Atributos
     */

    private String despachoFinal; //Resultado para return da função gerarDespacho

    /*
    Getters e Setters
     */

    public String getDespachoFinal() {
        return despachoFinal;
    }

    public void setDespachoFinal(String despachoFinal) {
        this.despachoFinal = despachoFinal;
    }

    /*
    Métodos
     */

    public void gerarDespacho() {

        /*
        Constrói um objeto da classe main.Segurado
         */
        Segurado novoSegurado = new Segurado();

        /*
        Chama método lerExtrato e seta em novoSegurado.extrato
         */
        String extrato = novoSegurado.lerExtrato();
        novoSegurado.setExtrato(extrato);

        /*
        Chama método lerNome e seta o nome do segurado em novoSegurado.nome
         */
        String nome = novoSegurado.lerNome();
        novoSegurado.setNome(nome);

        /*
        Chama o método lerSexo e seta o sexo do segurado em novoSegurado.sexo
         */
        String sexo = novoSegurado.lerSexo();
        novoSegurado.setSexo(sexo);

        /*
        Chama o método lerArtGenero e seta o artigo de gênero de acordo com o sexo em novoSegurado.artGenero
         */
        char artGenero = novoSegurado.lerArtGenero();
        novoSegurado.setArtGenero(artGenero);

        /*
        Chama o método lerNascString e seta a data de nascimento como String em novoSegurado.nascString
         */
        String nascString = novoSegurado.lerNascString();
        novoSegurado.setDataNascAsString(nascString);

        /*
        Chama o método nascToDate e seta a data de nascimento como Date em novoSegurado.dataNasc
         */
        Date nascDate = novoSegurado.nascToDate();
        novoSegurado.setDataNasc(nascDate);

        /*
        Chama o método lerDerString e seta a DER como String em novoSegurado.DERasString
         */
        String DERString = novoSegurado.lerDerString();
        novoSegurado.setDERasString(DERString);

        /*
        Chama o método DERtoDate e seta a DER como Date em novoSegurado.DER
         */
        Date DERDate = novoSegurado.DERtoDate();
        novoSegurado.setDER(DERDate);

        /*
        Chama o método lerIdadeDER e seta a idade como array de String em novoSegurado.idadeDER
         */
        String[] arrayIdadeDER = novoSegurado.lerIdadeDER();
        novoSegurado.setIdadeDER(arrayIdadeDER);

        /*
        Chama o método lerFiliaString e seta a data de filiação como String em novoSegurado.dataFiliaString
         */
        String filiaString = novoSegurado.lerFiliaString();
        novoSegurado.setDataFiliaAsString(filiaString);

        /*
        Chama o método FiliaToDate e seta a data de filiação como Date em novoSegurado.dataFilia
         */
        Date filiaDate = novoSegurado.FiliaToDate();
        novoSegurado.setDataFilia(filiaDate);

        /*
        Chama o método testaFiliaAteEC e preenche os campos referentes a este teste:
        - novoSegurado.filiaAteEC
        - novoSegurado.antesDepoisEC
        - novoSegurado.atendeNaoAtEC
         */
        boolean filiaAteEC = novoSegurado.testaFiliaAteEC();
        novoSegurado.setFiliaAteEC(filiaAteEC);
        if (novoSegurado.isFiliaAteEC()) {
            novoSegurado.setAntesDepoisEC("antes da");}
        else{
            novoSegurado.setAntesDepoisEC("depois da");
        }

        if (novoSegurado.isFiliaAteEC()) {
            novoSegurado.setAtendeNaoAtEC("atende");}
        else{
            novoSegurado.setAtendeNaoAtEC("não atende");
        }

        /*
        Inicia a construição do despacho integral e chama o método escreverParte1
         */
        String parte1despacho = this.escreverParte1(novoSegurado);
        this.setDespachoFinal(parte1despacho);

        /*
        A seguir será testado se há necessidade de criar as partes 2 e 3 do despacho.
        Caso positivo, serão setadas variáveis, bem como criadas e impressas as partes do despacho.
        */

        if (novoSegurado.isFiliaAteEC()){

            /*
            Testa se o sexo é masculino ou feminino e seta a idade exigida em 13/11/2019
            em novoSegurado.IdadeExigidaEC
             */
            if (novoSegurado.getSexo().equals("Masculino")) {
                novoSegurado.setIdadeExigidaEC("65");}
            else {
                novoSegurado.setIdadeExigidaEC("60");
            }

            /*
            Chama o método lerIdadeEC e seta idade em 13/11/2019 em novoSegurado.idadeEC
             */
            String[] arrayIdadeEC = novoSegurado.lerIdadeEC();
            novoSegurado.setIdadeCompEC(arrayIdadeEC);


            /*
            Chama o método lerCarenciaEC e seta carencia em 13/11/2019 em novoSegurado.carenciaEC
             */
            String carenciaEC = novoSegurado.lerCarenciaEC();
            novoSegurado.setCarenciaAteEC(carenciaEC);

            /*
            Testa se há direito adquirido à aposentadoria em 13/11/2019 e seta em:
            - novoSegurado.direitoAdquiridoEC
            - novoSegurado.possuiNaoDireitoAdquiridoEC
             */
            String dirAdqEC = novoSegurado.lerDireitoAdquidoEC();
            novoSegurado.setDirAdqEC(dirAdqEC.equals("s"));
            if (novoSegurado.isDirAdqEC()){
                novoSegurado.setPossuiNaoDirAdqEC("possui");}
            else{
                novoSegurado.setPossuiNaoDirAdqEC("não possui");
            }

            /*
            Chama o método escreverParte2 e adiciona parte 2 ao despacho integral
             */
            String parte2despacho = this.escreverParte2(novoSegurado);
            this.setDespachoFinal(this.getDespachoFinal() + "\n\n" + parte2despacho);

            /*

            Passa à parte 3 do despacho, que verifica o direito na DER para homem
            e 31/12 de todos os anos entre 2019 e a DER e também na DER para mulher

             */

            /*
            Seta a data de verificação do direito referente ao art. 18 da EC 103/2019
            para parte 3 do despacho. Na DER, se homem ou em 31/12/2019, se mulher
             */
            Calendar calendar = Calendar.getInstance();
            calendar.set(2019, Calendar.DECEMBER,31);
            if (novoSegurado.getSexo().equals("Masculino")){
                novoSegurado.setDtVerif(novoSegurado.getDER());
            }
            else{novoSegurado.setDtVerif(calendar.getTime());
            }


            /*
            Loop para geração de novos despachos para direito no art. 18 da EC 103/2019 quando necessários (mulher)
             */
            while(novoSegurado.getDtVerif().compareTo(novoSegurado.getDER())<=0) {


                /*
                Seta data de verificação do direito como String formatada
                 */
                SimpleDateFormat formatoDataSimples = new SimpleDateFormat("dd/MM/yyyy");
                novoSegurado.setDtVerifString(formatoDataSimples.format(novoSegurado.getDtVerif()));

                /*
                Chama o método lerIdadeDtVerif e seta idade exigida na data de verificação do direito
                em novoSegurado.idadeDtVerif
                 */
                String idadeDtVerif = novoSegurado.lerIdadeDtVerif();
                novoSegurado.setIdadeExigidaDtVerif(idadeDtVerif);

                String[] arrayIdadeCompDtVerif = novoSegurado.lerIdadeCompDtVerif();
                novoSegurado.setIdadeCompDtVerif(arrayIdadeCompDtVerif);
                //Seta idade exigida na data de verificação do direito


                String carenciaDtVerif = novoSegurado.lerCarenciaDtVerif();
                novoSegurado.setCarenciaDtVerif(carenciaDtVerif);
                //Seta carência na data de verificação do direito

                String[] arraytempoContrDtVerif = novoSegurado.lerTempoContrDtVerif();
                novoSegurado.setTempoContrDtVerif(arraytempoContrDtVerif);
                //Seta tempo de contribuição na data de verificação do direito

                /*
                Testa se há direito adquirido à aposentadoria na data de verificação do direito e seta em:
                - novoSegurado.dirAdqDtVerif
                - novoSegurado.possuiNaodirAdqDtVerif
                 */
                String dirDtVerif = novoSegurado.lerDireitoDtVerif();
                novoSegurado.setDirAdqDtVerif(dirDtVerif.equals("s"));
                if (novoSegurado.isDirAdqDtVerif()) {
                    novoSegurado.setPossuiNaoDirAdqDtVerif("possui");
                } else {
                    novoSegurado.setPossuiNaoDirAdqDtVerif("não possui");
                }

                /**
                 Chama o método escreverParte3 e adiciona parte 3 ao despacho integral
                 */
                String parte3despacho = this.escreverParte3(novoSegurado);
                this.setDespachoFinal(this.getDespachoFinal() + "\n\n" + parte3despacho);

                /*
                sai do loop se a data de verificação do direito for igual à DER
                 */
                if (novoSegurado.getDtVerif().equals(novoSegurado.getDER())){
                    break;
                }

                /*
                Chama o método adicAnoDtVerif e seta na nova data ou na DER caso a nova data seja maior que a DER
                 */
                Date novaDtVerif = novoSegurado.adicAnoDtVerif();
                if (novaDtVerif.compareTo(novoSegurado.getDER()) < 0) {
                    novoSegurado.setDtVerif(novaDtVerif);
                } else {
                    novoSegurado.setDtVerif(novoSegurado.getDER());
                }

            }


        }

        /*
        Passa à construção da parte 3 do despacho
         */

        /*
        Se a data de verificação do direito for diferente da DER, seta igual à DER
         */
        if (novoSegurado.getDtVerif().compareTo(novoSegurado.getDER())!=0){
            novoSegurado.setDtVerif(novoSegurado.getDER());
        }

        /*
        Seta idade exigida para aposentadoria programada
         */
        if (novoSegurado.getSexo().equals("masculino")){
            novoSegurado.setIdadeExigidaAposProg("65");
        }
        else{
            novoSegurado.setIdadeExigidaAposProg("62");
        }

        /*
        Seta idade atingida na data de verificação como DER
         */
        String[] idadeCompDtVerif = novoSegurado.lerIdadeCompDtVerif();
        novoSegurado.setIdadeCompDtVerif(idadeCompDtVerif);

        /*
        Seta carência na data de verificação como DER
         */
        String carenciaDtVerif = novoSegurado.lerCarenciaDtVerif();
        novoSegurado.setCarenciaDtVerif(carenciaDtVerif);

        /*
        Seta Tempo de contribuição na data de verificação como DER
         */
        String[] arraytempoContrDtVerif = novoSegurado.lerTempoContrDtVerif();
        novoSegurado.setTempoContrDtVerif(arraytempoContrDtVerif);

        /*
        Chama o método lerDireitoAposProg e seta se possui ou não direito à aposentadoria programada
         */
        String dirAposProg = novoSegurado.lerDireitoAposProg();
        if (dirAposProg.equals("s")){
            novoSegurado.setPossuiNaoDireitoAposProg("possui");
        }
        else{
            novoSegurado.setPossuiNaoDireitoAposProg("não possui");
        }

        /*
        Chama o método escreverParte4 e adiciona parte 4 ao despacho integral
         */
        String parte4despacho = this.escreverParte4(novoSegurado);
        this.setDespachoFinal(this.getDespachoFinal() + "\n\n" + parte4despacho);

        /*
        Chama o método lerDireitoAposFinal e seta se possui ou não direito à aposentadoria por qualquer uma das regras
         */
        String direitoAposFinal = novoSegurado.lerDireitoAposFinal();
        novoSegurado.setFoiNaoRecDireitoAposFinal(direitoAposFinal);

        /*
        Chama o método escreverParte5 e adiciona parte 5 ao despacho integral
         */
        String parte5despacho = this.escreverParteFinal(novoSegurado);
        this.setDespachoFinal(this.getDespachoFinal() + "\n\n" + parte5despacho);

    }

    /*
    Método para copiar despacho pronto para a clipboard
     */

    public void copiarDespacho () {
        String pegaTexto = this.getDespachoFinal();
        System.out.println(pegaTexto);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection copiarDespacho = new StringSelection(pegaTexto);
        clipboard.setContents(copiarDespacho, null);
    }


    /*
    Método para escrever em String a parte 1 do despacho - Parte fixa com cabeçalho e dados básicos
     */
    public String escreverParte1(Segurado segur){
        return (
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
                        "Emenda Constitucional 103/2019 e/ou pela regra transitória do art. 18 da citada emenda."
        );
    }

    /*
    Método para escrever em String a parte 2 do despacho - Regra do direito adquirido antes de 13/11/2019 EC 103/2019
     */
    public String escreverParte2(Segurado novoSegurado){
        return (
                "Quanto ao direito adquirido à aposentadoria antes da publicação da Emenda Constitucional 103/2019, são necessários, cumulativamente, " +
                        novoSegurado.getIdadeExigidaEC() + " anos de idade e 180 contribuições para fins de carência completos até 13/11/2019. A idade em 13/11/2019 era de "+
                        novoSegurado.getIdadeCompEC()[0] + " anos, " +
                        novoSegurado.getIdadeCompEC()[1] + " meses e " +
                        novoSegurado.getIdadeCompEC()[2] + " dias, e a carência apurada até esta data foi de " +
                        novoSegurado.getCarenciaAteEC() + " contribuições. Portanto, " +
                        novoSegurado.getPossuiNaoDirAdqEC() + " direito adquirido antes da publicação da Emenda Constitucional 103/2019."
        );
    }

    /*
    Método para escrever em String a parte 3 do despacho - Regra transitória do art. 18 da EC 103/2019
      */
    public  String escreverParte3(Segurado novoSegurado){
        return (
                "Quanto ao direito pela regra transitória do art. 18 da Emenda Constitucional 103/2019 após 13/11/2019, considerado até " +
                        novoSegurado.getDtVerifString() + ", são necessários, cumulativamente, " +
                        novoSegurado.getIdadeExigidaDtVerif() +
                        " de idade, com 180 contribuições para fins de carência e 15 anos de tempo de contribuição completos. Foram apuradas a idade de "+
                        novoSegurado.getIdadeCompDtVerif()[0] + " anos, " +
                        novoSegurado.getIdadeCompDtVerif()[1] + " meses e " +
                        novoSegurado.getIdadeCompDtVerif()[2] + " dias, com " +
                        novoSegurado.getCarenciaDtVerif() + " contribuições para fins de carência e " +
                        novoSegurado.getTempoContrDtVerif()[0] + " anos, " +
                        novoSegurado.getTempoContrDtVerif()[1] + " meses e " +
                        novoSegurado.getTempoContrDtVerif()[2] + " dias de tempo de contribuição. Portanto, " +
                        novoSegurado.getPossuiNaoDirAdqDtVerif() +
                        " direito adquirido pela regra transitória do art. 18 da Emenda Constitucional 103/2019 até " +
                        novoSegurado.getDtVerifString() + "."
        );
    }

    /*
    Método para escrever em String a parte 4 do despacho - Regra do art. 19 da EC 103/2019, aposentadoria programada
     */
    public String escreverParte4(Segurado novoSegurado){
        return (
                "Quanto ao direito pela regra da aposentadoria programada, art. 19 da Emenda Constitucional 103/2019, são necessários, cumulativamente, " +
                        novoSegurado.getIdadeExigidaAposProg() +
                        " anos de idade, 180 contribuições para fins de carência e 20 anos de tempo de contribuição. Foram apurados, até a DER, " +
                        novoSegurado.getIdadeCompDtVerif()[0] + " anos," +
                        novoSegurado.getIdadeCompDtVerif()[1] + " meses e " +
                        novoSegurado.getIdadeCompDtVerif()[2] + " dias de idade, " +
                        novoSegurado.getCarenciaDtVerif() +
                        " contribuições para fins de carência e " +
                        novoSegurado.getTempoContrDtVerif()[0] + " anos, " +
                        novoSegurado.getTempoContrDtVerif()[1] + " meses e " +
                        novoSegurado.getTempoContrDtVerif()[2] + " dias de tempo de contribuição. Portanto " +
                        novoSegurado.getPossuiNaoDireitoAposProg() +
                        "direito à aposentadoria programada pela regra do art. 19 da Emenda Constitucional 103/2019."


        );
    }

    /*
    Método para escrever em String a parte final do despacho - conclusão quanto ao direito à aposentadoria por qualquer uma das regras
     */
    public String escreverParteFinal(Segurado segur){
        return (
                "Pelo exposto, " + segur.getFoiNaoRecDireitoAposFinal() +
                        " reconhecido o direito à concessão da aposentadoria por idade.");
    }

}
