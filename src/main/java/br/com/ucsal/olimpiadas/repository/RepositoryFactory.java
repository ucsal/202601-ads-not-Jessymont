package br.com.ucsal.olimpiadas.repository;

/**
 * DIP — aqui é o único lugar onde os repositórios concretos são criados.
 * Os services recebem apenas Repository<T> e não precisam saber qual implementação está sendo usada.
 *
 * LSP — no futuro, dá pra trocar o repositório em memória por outro (ex: JDBC) sem precisar mudar
 * a lógica dos services, desde que continue seguindo o contrato de Repository<T>.
 */
public class RepositoryFactory {

    public static Repository<br.com.ucsal.olimpiadas.model.Participante> criarParticipanteRepository() {
        return new InMemoryParticipanteRepository();
    }

    public static Repository<br.com.ucsal.olimpiadas.model.Prova> criarProvaRepository() {
        return new InMemoryProvaRepository();
    }

    public static InMemoryQuestaoRepository criarQuestaoRepository() {
    // Retorna o tipo concreto para permitir usar listarPorProva.
    // Quem só precisa de Repository<Questao> continua usando a abstração (LSP).
        return new InMemoryQuestaoRepository();
    }

    public static Repository<br.com.ucsal.olimpiadas.model.Tentativa> criarTentativaRepository() {
        return new InMemoryTentativaRepository();
    }
}
