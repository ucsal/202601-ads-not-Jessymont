package br.com.ucsal.olimpiadas.service;

import java.util.List;

import br.com.ucsal.olimpiadas.model.Prova;
import br.com.ucsal.olimpiadas.repository.Repository;

/**
 * SRP — cuida apenas das regras de prova.
 * DIP — recebe Repository<Prova> pelo construtor.
 * LSP — pode usar qualquer implementação de repositório sem mudar o código.
 */
public class ProvaService {

    private final Repository<Prova> repository;

    public ProvaService(Repository<Prova> repository) {
        this.repository = repository;
    }

    public Prova cadastrar(String titulo) {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("título inválido");
        var prova = new Prova();
        prova.setTitulo(titulo);
        repository.salvar(prova);
        return prova;
    }

    public List<Prova> listarTodos()        { return repository.listarTodos(); }
    public boolean existePorId(long id)     { return repository.existePorId(id); }
}
