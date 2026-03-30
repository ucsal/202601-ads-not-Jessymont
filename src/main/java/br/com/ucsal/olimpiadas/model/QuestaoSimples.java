package br.com.ucsal.olimpiadas.model;

/**
 *  * LSP — QuestaoSimples é uma Questao normal (sem tabuleiro), sem mudar o comportamento esperado.
 *Herda todo o comportamento de Questao sem alterar contratos.
 *O getFenInicial() herda a implementação base (retorna null).
 *O isRespostaCorreta() é idêntico ao da superclasse.
 *
 * SRP — responsabilidade única: qrepresentar uma questão apenas textual.
 */
public class QuestaoSimples extends Questao {
    // Herda tudo de Questao sem modificar contratos.
    // getFenInicial() retorna null — sem tabuleiro.
}
