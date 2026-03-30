package br.com.ucsal.olimpiadas.service;

import java.util.List;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.model.QuestaoFactory;
import br.com.ucsal.olimpiadas.repository.InMemoryQuestaoRepository;

/**
 * OCP — QuestaoService não precisa de if/else para decidir o tipo de questão.
 * Ele usa as factories injetadas, então novos tipos podem ser adicionados sem alterar essa classe.
 *
 * SRP — cuida apenas da lógica de questões.
 * DIP — recebe a lista de QuestaoFactory pelo construtor.
 * LSP — o sistema continua trabalhando com Questao, independente do subtipo criado.
 */
public class QuestaoService {

    private final InMemoryQuestaoRepository repository;
    private final List<QuestaoFactory> factories;

    public QuestaoService(InMemoryQuestaoRepository repository,
                          List<QuestaoFactory> factories) {
        this.repository = repository;
        this.factories  = factories;
    }

/**
 * OCP — a factory correta é escolhida automaticamente, sem precisar de if/else.
 */
    public Questao cadastrar(long provaId, String enunciado, String[] alternativas,
                             char alternativaCorreta, String fenInicial) {

        Questao q = factories.stream()
                .filter(f -> f.aceita(fenInicial))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nenhuma factory encontrada para fenInicial: " + fenInicial))
                .criar(fenInicial);

        q.setProvaId(provaId);
        q.setEnunciado(enunciado);
        q.setAlternativas(alternativas);
        q.setAlternativaCorreta(alternativaCorreta);
        repository.salvar(q);
        return q;
    }

    public List<Questao> listarPorProva(long provaId) {
        return repository.listarPorProva(provaId);
    }
}
