package br.com.ucsal.olimpiadas.ui;

/**
 * OCP — interface que representa uma opção do menu.
 * Cada opção é uma classe separada, então dá pra criar novos comandos sem mexer na ConsoleUI.
 */
 /** Implementações fornecidas (em MenuCommands.java):
 * CadastrarParticipanteCommand
 * CadastrarProvaCommand
 * CadastrarQuestaoCommand
 * AplicarProvaCommand
 * ListarTentativasCommand
 */
public interface MenuCommand {
    /** Tecla usada para ativar o comando (ex: "1", "2"). */
    String tecla();

    /** Texto que aparece no menu. */
    String rotulo();

    /** Executa a ação do comando. */
    void executar();
}
