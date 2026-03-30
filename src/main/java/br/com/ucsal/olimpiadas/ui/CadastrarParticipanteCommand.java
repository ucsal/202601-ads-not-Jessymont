package br.com.ucsal.olimpiadas.ui;

import java.util.Scanner;

import br.com.ucsal.olimpiadas.service.ParticipanteService;

/** OCP — comando do menu para cadastrar participante. SRP — faz apenas essa ação. */
public class CadastrarParticipanteCommand implements MenuCommand {

    private final ParticipanteService participanteService;
    private final Scanner in;

    public CadastrarParticipanteCommand(ParticipanteService participanteService, Scanner in) {
        this.participanteService = participanteService;
        this.in = in;
    }

    @Override public String tecla()  { return "1"; }
    @Override public String rotulo() { return "Cadastrar participante"; }

    @Override
    public void executar() {
        System.out.print("Nome: ");
        var nome = in.nextLine();
        System.out.print("Email (opcional): ");
        var email = in.nextLine();
        try {
            var p = participanteService.cadastrar(nome, email);
            System.out.println("Participante cadastrado: " + p.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
