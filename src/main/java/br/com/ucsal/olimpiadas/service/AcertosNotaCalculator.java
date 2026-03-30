package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.Tentativa;

/**
 * OCP — implementação da estratégia de cálculo de nota por acertos.
 * SRP — responsabilidade única: calcular a nota da tentativa.
 */
public class AcertosNotaCalculator implements NotaCalculator {

    @Override
    public int calcular(Tentativa tentativa) {
        int acertos = 0;
        for (var r : tentativa.getRespostas()) {
            if (r.isCorreta()) acertos++;
        }
        return acertos;
    }
}
