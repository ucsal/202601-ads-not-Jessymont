package br.com.ucsal.olimpiadas;

import java.util.List;
import java.util.Scanner;

import br.com.ucsal.olimpiadas.model.QuestaoComFenFactory;
import br.com.ucsal.olimpiadas.model.QuestaoSimplesFactory;
import br.com.ucsal.olimpiadas.repository.RepositoryFactory;
import br.com.ucsal.olimpiadas.seed.DataSeeder;
import br.com.ucsal.olimpiadas.service.AcertosNotaCalculator;
import br.com.ucsal.olimpiadas.service.NotaCalculator;
import br.com.ucsal.olimpiadas.service.ParticipanteService;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;
import br.com.ucsal.olimpiadas.service.TentativaService;
import br.com.ucsal.olimpiadas.ui.AplicarProvaCommand;
import br.com.ucsal.olimpiadas.ui.AsciiBoardRenderer;
import br.com.ucsal.olimpiadas.ui.BoardRenderer;
import br.com.ucsal.olimpiadas.ui.CadastrarParticipanteCommand;
import br.com.ucsal.olimpiadas.ui.CadastrarProvaCommand;
import br.com.ucsal.olimpiadas.ui.CadastrarQuestaoCommand;
import br.com.ucsal.olimpiadas.ui.ConsoleUI;
import br.com.ucsal.olimpiadas.ui.ListarTentativasCommand;

/**
 * Versão SOLID com OCP aplicado.
 *
 * Aqui foram adicionadas extensões sem precisar mexer no código existente, como:
 * - estratégias de nota (NotaCalculator)
 * - formas de renderizar tabuleiro (BoardRenderer)
 * - criação de questões (QuestaoFactory)
 * - comandos do menu (MenuCommand)
 *
 * O App é o Composition Root, então é aqui que as implementações são instanciadas.
 */
public class App {

    public static void main(String[] args) {

        var scanner = new Scanner(System.in);

        // ── Repositórios ─
        var participanteRepo = RepositoryFactory.criarParticipanteRepository();
        var provaRepo        = RepositoryFactory.criarProvaRepository();
        var questaoRepo      = RepositoryFactory.criarQuestaoRepository();
        var tentativaRepo    = RepositoryFactory.criarTentativaRepository();

        // OCP — estratégia de nota (dá pra trocar por outra implementação).
        // Troque por new PenalizacaoNotaCalculator(0.25) para habilitar penalização.
        NotaCalculator notaCalculator = new AcertosNotaCalculator();

        // OCP — renderização do tabuleiro (ASCII ou Unicode).
        // Troque por new UnicodeBoardRenderer() para tabuleiro com símbolos Unicode.
        BoardRenderer boardRenderer = new AsciiBoardRenderer();

        // OCP — factories de questão (a ordem importa).
        var questaoFactories = List.of(
            new QuestaoComFenFactory(),   // aceita fenInicial não-nulo
            new QuestaoSimplesFactory()   // fallback: aceita null/blank
        );

        // ── Serviços ─
        var participanteService = new ParticipanteService(participanteRepo);
        var provaService        = new ProvaService(provaRepo);
        var questaoService      = new QuestaoService(questaoRepo, questaoFactories);
        var tentativaService    = new TentativaService(tentativaRepo, notaCalculator);

        // ── Seed ─
        new DataSeeder(provaService, questaoService).executar();

        // OCP — comandos do menu registrados aqui. Pra adicionar outro, é só incluir na lista.
        var comandos = List.of(
            new CadastrarParticipanteCommand(participanteService, scanner),
            new CadastrarProvaCommand(provaService, scanner),
            new CadastrarQuestaoCommand(provaService, questaoService, scanner),
            new AplicarProvaCommand(participanteService, provaService,
                                    questaoService, tentativaService,
                                    boardRenderer, scanner),
            new ListarTentativasCommand(tentativaService)
        );

        // ── UI ──
        new ConsoleUI(comandos).iniciar();
    }
}
