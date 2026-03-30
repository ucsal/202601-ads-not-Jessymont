package br.com.ucsal.olimpiadas.ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * OCP — o menu funciona por comandos, sem switch fixo.
 * Pra adicionar nova opção, basta criar um MenuCommand e registrar no App.
 *
 * SRP — responsabilidade única: controlar o menu e a interação no console.
 * DIP — recebe os comandos prontos pelo construtor.
 */
public class ConsoleUI {

    private final Map<String, MenuCommand> comandos;

    /**
 * @param comandos lista de comandos do menu (a ordem define como aparece).
 */
    public ConsoleUI(List<MenuCommand> comandos) {
        // Mantém a ordem de inserção para o menu aparecer certinho.
        this.comandos = new LinkedHashMap<>();
        for (var cmd : comandos) {
            this.comandos.put(cmd.tecla(), cmd);
        }
    }

    public void iniciar() {
    try (var in = new java.util.Scanner(System.in)) {
        while (true) {
            System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V1) ===");

            // OCP — o menu é montado automaticamente com base nos comandos registrados.
            for (var cmd : comandos.values()) {
                System.out.printf("%s) %s%n", cmd.tecla(), cmd.rotulo());
            }
            System.out.println("0) Sair");
            System.out.print("> ");

            String entrada = in.nextLine();
            if ("0".equals(entrada)) { 
                System.out.println("tchau"); 
                return; 
            }

            var cmd = comandos.get(entrada);
            if (cmd != null) {
                cmd.executar();
            } else {
                System.out.println("opção inválida");
            }
        }
    } 
}
}
