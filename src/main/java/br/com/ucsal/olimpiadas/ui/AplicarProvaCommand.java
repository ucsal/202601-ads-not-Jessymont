package br.com.ucsal.olimpiadas.ui;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.service.*;
import java.util.Scanner;

/**
 * OCP — Comando de menu para aplicar uma prova.
 *        Depende de BoardRenderer (abstração) — troca de visual sem modificação.
 * SRP — ação única: conduzir o fluxo de aplicação de prova.
 */
public class AplicarProvaCommand implements MenuCommand {

    private final ParticipanteService participanteService;
    private final ProvaService provaService;
    private final QuestaoService questaoService;
    private final TentativaService tentativaService;
    private final BoardRenderer boardRenderer;
    private final Scanner in;

    public AplicarProvaCommand(ParticipanteService participanteService,
                               ProvaService provaService,
                               QuestaoService questaoService,
                               TentativaService tentativaService,
                               BoardRenderer boardRenderer,
                               Scanner in) {
        this.participanteService = participanteService;
        this.provaService        = provaService;
        this.questaoService      = questaoService;
        this.tentativaService    = tentativaService;
        this.boardRenderer       = boardRenderer;
        this.in = in;
    }

    @Override public String tecla()  { return "4"; }
    @Override public String rotulo() { return "Aplicar prova (selecionar participante + prova)"; }

    @Override
    public void executar() {
        if (participanteService.listarTodos().isEmpty()) {
            System.out.println("cadastre participantes primeiro"); return;
        }
        if (provaService.listarTodos().isEmpty()) {
            System.out.println("cadastre provas primeiro"); return;
        }

        System.out.println("\nParticipantes:");
        for (var p : participanteService.listarTodos())
            System.out.printf("  %d) %s%n", p.getId(), p.getNome());
        System.out.print("Escolha o id do participante: ");
        long participanteId;
        try {
            participanteId = Long.parseLong(in.nextLine());
            if (!participanteService.existePorId(participanteId)) { System.out.println("id inválido"); return; }
        } catch (Exception e) { System.out.println("entrada inválida"); return; }

        System.out.println("\nProvas:");
        for (var p : provaService.listarTodos())
            System.out.printf("  %d) %s%n", p.getId(), p.getTitulo());
        System.out.print("Escolha o id da prova: ");
        long provaId;
        try {
            provaId = Long.parseLong(in.nextLine());
            if (!provaService.existePorId(provaId)) { System.out.println("id inválido"); return; }
        } catch (Exception e) { System.out.println("entrada inválida"); return; }

        var questoes = questaoService.listarPorProva(provaId);
        if (questoes.isEmpty()) {
            System.out.println("esta prova não possui questões cadastradas"); return;
        }

        var tentativa = tentativaService.iniciar(participanteId, provaId);
        System.out.println("\n--- Início da Prova ---");

        for (var q : questoes) {
            System.out.println("\nQuestão #" + q.getId());
            System.out.println(q.getEnunciado());

            String fen = q.getFenInicial();
            if (fen != null) {
                System.out.println("Posição inicial:");
                boardRenderer.renderizar(fen); // OCP: abstração injetada
            }

            for (var alt : q.getAlternativas()) System.out.println(alt);

            System.out.print("Sua resposta (A–E): ");
            char marcada;
            try {
                marcada = Questao.normalizar(in.nextLine().trim().charAt(0));
            } catch (Exception e) {
                System.out.println("resposta inválida (marcando como errada)");
                marcada = 'X';
            }

            tentativaService.registrarResposta(tentativa, q, marcada);
        }

        int nota = tentativaService.finalizar(tentativa);
        System.out.println("\n--- Fim da Prova ---");
        System.out.println("Nota (acertos): " + nota + " / " + tentativa.getRespostas().size());
    }
}
