package br.com.ucsal.olimpiadas.ui;

/**
 * OCP — outra forma de renderizar o tabuleiro (Unicode), sem mexer no resto do sistema.
 * SRP — responsabilidade única: renderizar o tabuleiro em Unicode.
 */
public class UnicodeBoardRenderer implements BoardRenderer {

    private static final java.util.Map<Character, String> PECAS = java.util.Map.ofEntries(
        java.util.Map.entry('K', "♔"), java.util.Map.entry('Q', "♕"),
        java.util.Map.entry('R', "♖"), java.util.Map.entry('B', "♗"),
        java.util.Map.entry('N', "♘"), java.util.Map.entry('P', "♙"),
        java.util.Map.entry('k', "♚"), java.util.Map.entry('q', "♛"),
        java.util.Map.entry('r', "♜"), java.util.Map.entry('b', "♝"),
        java.util.Map.entry('n', "♞"), java.util.Map.entry('p', "♟")
    );

    @Override
    public void renderizar(String fen) {
        if (fen == null || fen.isBlank()) return;

        String[] ranks = fen.split(" ")[0].split("/");

        System.out.println();
        System.out.println("    a  b  c  d  e  f  g  h");
        System.out.println("   ┌──┬──┬──┬──┬──┬──┬──┬──┐");

        for (int r = 0; r < 8; r++) {
            System.out.print((8 - r) + " │");
            for (char c : ranks[r].toCharArray()) {
                if (Character.isDigit(c)) {
                    for (int i = 0; i < (c - '0'); i++) System.out.print("  │");
                } else {
                    System.out.print(PECAS.getOrDefault(c, "?") + " │");
                }
            }
            System.out.println(" " + (8 - r));
            if (r < 7) System.out.println("   ├──┼──┼──┼──┼──┼──┼──┼──┤");
        }

        System.out.println("   └──┴──┴──┴──┴──┴──┴──┴──┘");
        System.out.println("    a  b  c  d  e  f  g  h");
        System.out.println();
    }
}
