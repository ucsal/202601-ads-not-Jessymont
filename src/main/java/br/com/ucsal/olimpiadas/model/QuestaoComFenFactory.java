package br.com.ucsal.olimpiadas.model;

/**
 * OCP — a Factory facilita adicionar/criar QuestaoComFen sem precisar mexer no resto do sistema.
 * SRP — responsabilidade única: criar instâncias de QuestaoComFen.
 */
public class QuestaoComFenFactory implements QuestaoFactory {

    @Override
    public boolean aceita(String fenInicial) {
        return fenInicial != null && !fenInicial.isBlank();
    }

    @Override
    public Questao criar(String fenInicial) {
        return new QuestaoComFen(fenInicial);
    }
}
