package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.model.*;


/**
 * LSP — os repositórios em memória seguem o contrato de Repository<T> e podem ser usados
 * no lugar de qualquer outra implementação (ex: banco de dados) sem quebrar o sistema.
 *
 * SRP — cada repositório cuida apenas da persistência em memória da sua entidade.
 *
 * DIP — as implementações concretas são criadas apenas na RepositoryFactory, e os services
 * dependem apenas das interfaces.
 */
// ── Participante ─
class InMemoryParticipanteRepository extends BaseInMemoryRepository<Participante> {

    InMemoryParticipanteRepository() {
        super(Participante::getId, Participante::setId);
    }
    // Herda salvar, listarTodos, buscarPorId e existePorId, mantendo o contrato do Repository (LSP).
}

// ── Prova ─
class InMemoryProvaRepository extends BaseInMemoryRepository<Prova> {

    InMemoryProvaRepository() {
        super(Prova::getId, Prova::setId);
    }
    // Herda salvar, listarTodos, buscarPorId e existePorId, mantendo o contrato do Repository (LSP)..
}

// InMemoryQuestaoRepository fica em um arquivo separado (InMemoryQuestaoRepository.java).

// ── Tentativa ─
class InMemoryTentativaRepository extends BaseInMemoryRepository<Tentativa> {

    InMemoryTentativaRepository() {
        super(Tentativa::getId, Tentativa::setId);
    }
    // Herda salvar, listarTodos, buscarPorId e existePorId, mantendo o contrato do Repository (LSP).
}
