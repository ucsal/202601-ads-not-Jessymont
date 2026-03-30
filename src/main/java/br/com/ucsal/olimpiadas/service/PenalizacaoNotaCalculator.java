package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.Tentativa;

/**
 * OCP — estratégia alternativa de nota (com penalização por erro).
 *  Cada acerto vale +1 ponto; cada erro desconta {@code penalidade} pontos.
 *  O resultado é limitado a zero (não pode ser negativo).
 * SRP — responsabilidade única: calcular nota com desconto.
 */
public class PenalizacaoNotaCalculator implements NotaCalculator {

    private final double penalidade;

    /**
     * @param penalidade pontos descontados por cada resposta errada (ex.: 0.25).
     */
    public PenalizacaoNotaCalculator(double penalidade) {
        if (penalidade < 0) throw new IllegalArgumentException("penalidade não pode ser negativa");
        this.penalidade = penalidade;
    }

    @Override
    public int calcular(Tentativa tentativa) {
        long acertos = tentativa.getRespostas().stream().filter(r ->  r.isCorreta()).count();
        long erros   = tentativa.getRespostas().stream().filter(r -> !r.isCorreta()).count();
        double nota  = acertos - (erros * penalidade);
        return (int) Math.max(0, nota);
    }
}
