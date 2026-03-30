package br.com.ucsal.olimpiadas.ui;

import java.util.Scanner;

import br.com.ucsal.olimpiadas.service.ProvaService;

/** OCP — comando do menu para cadastrar prova. SRP — faz apenas essa ação. */
public class CadastrarProvaCommand implements MenuCommand {

    private final ProvaService provaService;
    private final Scanner in;

    public CadastrarProvaCommand(ProvaService provaService, Scanner in) {
        this.provaService = provaService;
        this.in = in;
    }

    @Override public String tecla()  { return "2"; }
    @Override public String rotulo() { return "Cadastrar prova"; }

    @Override
    public void executar() {
        System.out.print("Título da prova: ");
        var titulo = in.nextLine();
        try {
            var prova = provaService.cadastrar(titulo);
            System.out.println("Prova criada: " + prova.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
