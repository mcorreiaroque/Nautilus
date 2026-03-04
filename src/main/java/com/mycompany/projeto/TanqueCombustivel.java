package com.mycompany.projeto;

import java.io.Serializable;

public class TanqueCombustivel implements Serializable {
    
    private final int capacidade;
    private double combustivelAtual;
    private final TipoCombustivel tipoDeCombustivel;
    
    public TanqueCombustivel(int capacidade, TipoCombustivel tipoDeCombustivel) {
        this.capacidade = capacidade;
        this.combustivelAtual = 0;
        this.tipoDeCombustivel = tipoDeCombustivel;
    }
    
    public double getCombustivelAtual() {
        return combustivelAtual;
    }

    public TipoCombustivel getTipoDeCombustivel() {
        return tipoDeCombustivel;
    }
    
    public int getCapacidade(){
        return capacidade;
    }

    public void abastecer() {
        this.combustivelAtual = capacidade;
    }
    
    @Override
    public String toString() {
        return String.format("Capacidade: %d litros, Tipo: %s", capacidade, tipoDeCombustivel);
    }
}
