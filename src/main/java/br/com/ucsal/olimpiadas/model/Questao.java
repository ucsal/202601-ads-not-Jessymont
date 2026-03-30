package br.com.ucsal.olimpiadas.model;

import java.util.Arrays;

/**
 * SRP — essa classe representa apenas os dados e regras de uma questão.
 *
 * LSP — é a classe base. Qualquer lugar que usa Questao funciona igual com
 * QuestaoSimples ou QuestaoComFen, sem quebrar o sistema.
 */
public class Questao {
    private long id;
    private long provaId;
    private String enunciado;
    private String[] alternativas = new String[5];
    private char alternativaCorreta;

    public long getId()                          { 
        return id;

     }
    public void setId(long id)                   {
        this.id = id; 
    
    }

    public long getProvaId()                     { 
        return provaId;
    
    }
    public void setProvaId(long provaId)         {
         this.provaId = provaId; 
        
        }

    public String getEnunciado()                 { 
        return enunciado;
    
    }
    public void setEnunciado(String enunciado)   { 
        this.enunciado = enunciado; 
    
    }

    public String[] getAlternativas()            { 
        return alternativas;
    
    }
    public void setAlternativas(String[] alts) {
        if (alts == null || alts.length != 5)
            throw new IllegalArgumentException("A questão deve possuir exatamente 5 alternativas.");
        this.alternativas = Arrays.copyOf(alts, 5);
    }

    public char getAlternativaCorreta()          {
         return alternativaCorreta; 
        
        }
    public void setAlternativaCorreta(char c)    {
         this.alternativaCorreta = normalizar(c);
        
        }

    public boolean isRespostaCorreta(char marcada) {
        return normalizar(marcada) == alternativaCorreta;
    }

    /**
     * LSP — pode ser sobrescrito pelas subclasses para retornar um FEN.
     * Aqui retorna null quando não existe tabuleiro.
     */
    public String getFenInicial() { return null; }

    public static char normalizar(char c) {
        char up = Character.toUpperCase(c);
        if (up < 'A' || up > 'E')
            throw new IllegalArgumentException("Alternativa deve estar entre A e E.");
        return up;
    }
}
