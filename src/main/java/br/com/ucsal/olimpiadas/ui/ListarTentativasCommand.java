package br.com.ucsal.olimpiadas.ui;

import br.com.ucsal.olimpiadas.service.TentativaService;

/** OCP — comando do menu para listar tentativas. SRP — faz apenas essa ação. */
public class ListarTentativasCommand implements MenuCommand {

    private final TentativaService tentativaService;

    public ListarTentativasCommand(TentativaService tentativaService) {
        this.tentativaService = tentativaService;
    }

    @Override public String tecla()  { return "5"; }
    @Override public String rotulo() { return "Listar tentativas (resumo)"; }

    @Override
    public void executar() {
        System.out.println("\n--- Tentativas ---");
        for (var t : tentativaService.listarTodos()) {
            System.out.printf("#%d | participante=%d | prova=%d | nota=%d/%d%n",
                    t.getId(), t.getParticipanteId(), t.getProvaId(),
                    tentativaService.calcularNota(t), t.getRespostas().size());
        }
    }
}
