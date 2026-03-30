package br.com.ucsal.olimpiadas.model;

/**
 * OCP — interface de fábrica para criar Questao.
 * O cadastro é passado para as factories.
 * Para adicionar um novo tipo de questão, basta criar uma nova Factory e registrar no App.
 * Nenhum código existente precisa ser modificado.
 *
 *        Implementações fornecidas (em QuestaoFactories.java):
 * A QuestaoSimplesFactory cria QuestaoSimples
 * A QuestaoComFenFactory cria QuestaoComFen quando fenInicial != null
 */
public interface QuestaoFactory {
    /**
     * Indica se esta factory pode criar uma Questao com os parâmetros dados.
     *
     * @param fenInicial pode ser null para questões sem tabuleiro.
     */
    boolean aceita(String fenInicial);

    /** Cria e retorna a instância de Questao adequada. */
    Questao criar(String fenInicial);
}
