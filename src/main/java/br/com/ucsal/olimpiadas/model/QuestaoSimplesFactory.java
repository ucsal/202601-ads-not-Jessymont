package br.com.ucsal.olimpiadas.model;

/**
 * OCP — Factory para criar QuestaoSimples (questões só com texto, sem tabuleiro).
 * SRP — responsabilidade única: criar QuestaoSimples.
 */
public class QuestaoSimplesFactory implements QuestaoFactory {

    @Override
    public boolean aceita(String fenInicial) {
        return fenInicial == null || fenInicial.isBlank();
    }

    @Override
    public Questao criar(String fenInicial) {
        return new QuestaoSimples();
    }
}
