package br.com.ucsal.olimpiadas.repository;

import java.util.List;
import java.util.Optional;

/**
 * DIP — interface genérica de repositório. Os services dependem dela e não das classes concretas.
 *
 * LSP — qualquer implementação (em memória, JDBC, JPA, etc) pode ser trocada sem quebrar o sistema,
 * desde que siga esse contrato.
 *
 */
public interface Repository<T> {
    void salvar(T entidade);
    List<T> listarTodos();
    Optional<T> buscarPorId(long id);
    boolean existePorId(long id);
}
