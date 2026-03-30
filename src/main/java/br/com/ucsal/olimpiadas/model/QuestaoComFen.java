package br.com.ucsal.olimpiadas.model;

    /**
     * LSP — QuestaoComFen é só uma Questao normal, mas com um tabuleiro FEN.
     * SRP — responsabilidade única: guardar a questão + o FEN.
     */
public class QuestaoComFen extends Questao {

    private String fenInicial;

    public QuestaoComFen(String fenInicial) {
        if (fenInicial == null || fenInicial.isBlank())
            throw new IllegalArgumentException("FEN não pode ser vazio.");
        this.fenInicial = fenInicial;
    }

    /**
     * LSP — sobrescreve getFenInicial() apenas para retornar o FEN da questão.
     */
    @Override
    public String getFenInicial() { 
        return fenInicial; 
    
    }

    public void setFenInicial(String fenInicial) {
         this.fenInicial = fenInicial; 
        
        }
}
