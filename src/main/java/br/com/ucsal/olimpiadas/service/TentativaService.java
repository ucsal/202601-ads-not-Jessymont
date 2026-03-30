package br.com.ucsal.olimpiadas.service;

import java.util.List;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.model.Resposta;
import br.com.ucsal.olimpiadas.model.Tentativa;
import br.com.ucsal.olimpiadas.repository.Repository;

/**
 * OCP — a regra de cálculo da nota pode ser trocada só mudando a implementação do NotaCalculator no App.
 *
 * SRP — essa classe cuida apenas da lógica de tentativas.
 * DIP — recebe Repository<Tentativa> e NotaCalculator pelo construtor.
 * LSP — funciona com Questao e qualquer subtipo (QuestaoSimples, QuestaoComFen).
 */
public class TentativaService {

    private final Repository<Tentativa> repository;
    private final NotaCalculator notaCalculator;   // OCP: abstração, não classe concreta

    public TentativaService(Repository<Tentativa> repository,
                            NotaCalculator notaCalculator) {
        this.repository     = repository;
        this.notaCalculator = notaCalculator;
    }

    public Tentativa iniciar(long participanteId, long provaId) {
        var t = new Tentativa();
        t.setParticipanteId(participanteId);
        t.setProvaId(provaId);
        return t;
    }

    public void registrarResposta(Tentativa tentativa, Questao questao, char alternativaMarcada) {
        var r = new Resposta();
        r.setQuestaoId(questao.getId());
        r.setAlternativaMarcada(alternativaMarcada);
        r.setCorreta(questao.isRespostaCorreta(alternativaMarcada));
        tentativa.getRespostas().add(r);
    }

    public int finalizar(Tentativa tentativa) {
        repository.salvar(tentativa);
        return notaCalculator.calcular(tentativa);
    }

    public List<Tentativa> listarTodos()         { return repository.listarTodos(); }
    public int calcularNota(Tentativa tentativa) { return notaCalculator.calcular(tentativa); }
}
