package br.com.ucsal.olimpiadas.ui;

import java.util.Scanner;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;

/** OCP — comando do menu para cadastrar questão. SRP — faz apenas essa ação. */
public class CadastrarQuestaoCommand implements MenuCommand {

    private final ProvaService provaService;
    private final QuestaoService questaoService;
    private final Scanner in;

    public CadastrarQuestaoCommand(ProvaService provaService,
                                   QuestaoService questaoService,
                                   Scanner in) {
        this.provaService   = provaService;
        this.questaoService = questaoService;
        this.in = in;
    }

    @Override public String tecla()  { return "3"; }
    @Override public String rotulo() { return "Cadastrar questão (A–E) em uma prova"; }

    @Override
    public void executar() {
        if (provaService.listarTodos().isEmpty()) {
            System.out.println("não há provas cadastradas"); return;
        }

        System.out.println("\nProvas:");
        for (var p : provaService.listarTodos())
            System.out.printf("  %d) %s%n", p.getId(), p.getTitulo());
        System.out.print("Escolha o id da prova: ");
        long provaId;
        try {
            provaId = Long.parseLong(in.nextLine());
            if (!provaService.existePorId(provaId)) { System.out.println("id inválido"); return; }
        } catch (Exception e) { System.out.println("entrada inválida"); return; }

        System.out.println("Enunciado:");
        var enunciado = in.nextLine();

        var alternativas = new String[5];
        for (int i = 0; i < 5; i++) {
            char letra = (char) ('A' + i);
            System.out.print("Alternativa " + letra + ": ");
            alternativas[i] = letra + ") " + in.nextLine();
        }

        System.out.print("Alternativa correta (A–E): ");
        char correta;
        try {
            correta = Questao.normalizar(in.nextLine().trim().charAt(0));
        } catch (Exception e) {
            System.out.println("alternativa inválida"); return;
        }

        var q = questaoService.cadastrar(provaId, enunciado, alternativas, correta, null);
        System.out.println("Questão cadastrada: " + q.getId() + " (na prova " + provaId + ")");
    }
}
