package br.com.ucsal.olimpiadas.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
/**
 * LSP — essa é uma classe base abstrata que implementa o contrato do Repository<T>.
 * Os repositórios em memória (Participante, Prova, Questao, etc) herdam essa classe
 * e continuam seguindo o mesmo comportamento esperado, sem quebrar o sistema.
 *
 * Ela já entrega as operações principais como salvar, listar e buscar por ID.
 * Assim, qualquer código que use Repository<T> funciona do mesmo jeito, independente
 * de qual repositório concreto está sendo usado.
 *
 * SRP — responsabilidade única: cuidar do armazenamento genérico em memória e do controle
 * de IDs sequenciais.
 *
 * DIP — a classe não depende diretamente de nenhuma entidade específica, porque ela recebe
 * no construtor uma função que permite extrair/definir o ID do objeto.
 */
public abstract class BaseInMemoryRepository<T> implements Repository<T> {

    protected final List<T> store = new ArrayList<>();
    private long proximoId = 1;

    /** Função usada para pegar o ID da entidade (passada pelo subtipo). */
    private final Function<T, Long> getId;
    /** Função usada para definir o ID da entidade (passada pelo subtipo). */
    private final java.util.function.BiConsumer<T, Long> setId;

    protected BaseInMemoryRepository(Function<T, Long> getId,
                                     java.util.function.BiConsumer<T, Long> setId) {
        this.getId = getId;
        this.setId = setId;
    }

     /**
     * LSP — método padrão do repositório: gera o ID e salva no armazenamento.
     * As subclasses não devem alterar esse comportamento, para não quebrar o contrato do Repository.
     */
    @Override
    public final void salvar(T entidade) {
        setId.accept(entidade, proximoId++);
        store.add(entidade);
    }

    @Override
    public List<T> listarTodos() {
        return List.copyOf(store);
    }

    @Override
    public Optional<T> buscarPorId(long id) {
        return store.stream().filter(e -> getId.apply(e) == id).findFirst();
    }

    @Override
    public boolean existePorId(long id) {
        return store.stream().anyMatch(e -> getId.apply(e) == id);
    }
}
