package br.com.ucsal.olimpiadas.service;

import java.util.List;
import java.util.Optional;

import br.com.ucsal.olimpiadas.model.Participante;
import br.com.ucsal.olimpiadas.repository.Repository;

/**
 * SRP — essa classe cuida apenas das regras de participante.
 * DIP — recebe o Repository<Participante> pelo construtor.
 * LSP — funciona com qualquer tipo de repositório (memória, banco, etc).
 */
public class ParticipanteService {

    private final Repository<Participante> repository;

    public ParticipanteService(Repository<Participante> repository) {
        this.repository = repository;
    }

    public Participante cadastrar(String nome, String email) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("nome inválido");
        var p = new Participante();
        p.setNome(nome);
        p.setEmail(email);
        repository.salvar(p);
        return p;
    }

    public List<Participante> listarTodos()         { return repository.listarTodos(); }
    public Optional<Participante> buscarPorId(long id) { return repository.buscarPorId(id); }
    public boolean existePorId(long id)             { return repository.existePorId(id); }
}
