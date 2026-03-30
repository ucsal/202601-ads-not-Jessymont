package br.com.ucsal.olimpiadas.ui;

/**
 * OCP — interface para renderizar tabuleiro.
 * Se quiser mudar o estilo (ASCII, Unicode, etc), é só criar outra implementação.
 */
public interface BoardRenderer {
   /**
 * Renderiza um tabuleiro a partir de uma string FEN.
 * @param fen posição do tabuleiro em formato FEN.
 */
    void renderizar(String fen);
}
