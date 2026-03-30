package br.com.ucsal.olimpiadas.repository;

import java.util.List;

import br.com.ucsal.olimpiadas.model.Questao;

/**
 * LSP — esse repositório consegue armazenar qualquer tipo de Questao
 * (QuestaoSimples ou QuestaoComFen). O método listarPorProva filtra apenas pelo
 * provaId e não depende do subtipo da questão.
 *
 * SRP — responsabilidade única: armazenar Questao em memória.
 *
 * DIP — usa a classe base BaseInMemoryRepository e reaproveita a lógica de ID
 * através do super().
 */
public class InMemoryQuestaoRepository extends BaseInMemoryRepository<Questao> {

    public InMemoryQuestaoRepository() {
        super(Questao::getId, Questao::setId);
    }

    /** Método extra específico de Questao, sem alterar o comportamento do repositório base. */
    public List<Questao> listarPorProva(long provaId) {
        return store.stream().filter(q -> q.getProvaId() == provaId).toList();
    }
}
