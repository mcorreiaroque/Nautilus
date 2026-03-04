package com.mycompany.projeto;

public enum Zona {
    NORTE, SUL, LESTE, OESTE, ATRACADO;
    
    public static Zona escolha(int i) {
        switch (i) {
            case 1: return NORTE;
            case 2: return SUL;
            case 3: return LESTE;
            case 4: return OESTE;
            default: throw new IllegalArgumentException("Zona inválida");
        }
    }
}
