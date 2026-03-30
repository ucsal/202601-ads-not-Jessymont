package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.Tentativa;

/**
 * OCP — interface para cálculo de nota.
 * Se eu quiser mudar a regra de pontuação (ex.: com bônus, penalidade
 * por resposta errada, peso por dificuldade), basta criar outra implementação.
 */
public interface NotaCalculator {
    int calcular(Tentativa tentativa);
}
