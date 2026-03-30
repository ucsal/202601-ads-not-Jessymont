package br.com.ucsal.olimpiadas.seed;

import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;

/**
 * SRP — essa classe serve apenas para carregar o sistema com dados inicais.
 *
 * DIP — recebe os services pelo construtor, sem criar nada diretamente aqui.
 *
 * LSP — o cadastrar() pode criar QuestaoSimples ou QuestaoComFen dependendo dos dados,
 * e o DataSeeder não precisa saber qual tipo foi criado, porque trabalha com Questao (classe base).
 */
public class DataSeeder {

    private final ProvaService provaService;
    private final QuestaoService questaoService;

    public DataSeeder(ProvaService provaService, QuestaoService questaoService) {
        this.provaService   = provaService;
        this.questaoService = questaoService;
    }

    public void executar() {
        var prova = provaService.cadastrar("Olimpíada 2026 • Nível 1 • Prova A");

        // QuestaoService escolhe o subtipo (ex: QuestaoComFen) sem afetar quem usa Questao (LSP).
        questaoService.cadastrar(
            prova.getId(),
            """
            Questão 1 — Mate em 1.
            É a vez das brancas.
            Encontre o lance que dá mate imediatamente.
            """,
            new String[]{"A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#"},
            'C',
            "6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1"
        );
    }
}
