package br.com.ucsal.olimpiadas.ui;

/**
 * OCP — implementação padrão do BoardRenderer em ASCII.
 * SRP — responsabilidade única: renderizar o tabuleiro FEN no console.
 */
public class AsciiBoardRenderer implements BoardRenderer {

    @Override
    public void renderizar(String fen) {
        if (fen == null || fen.isBlank()) return;

        String parteTabuleiro = fen.split(" ")[0];
        String[] ranks = parteTabuleiro.split("/");

        System.out.println();
        System.out.println("    a b c d e f g h");
        System.out.println("   -----------------");

        for (int r = 0; r < 8; r++) {
            String rank = ranks[r];
            System.out.print((8 - r) + " | ");

            for (char c : rank.toCharArray()) {
                if (Character.isDigit(c)) {
                    int vazios = c - '0';
                    for (int i = 0; i < vazios; i++) System.out.print(". ");
                } else {
                    System.out.print(c + " ");
                }
            }
            System.out.println("| " + (8 - r));
        }

        System.out.println("   -----------------");
        System.out.println("    a b c d e f g h");
        System.out.println();
    }
}
