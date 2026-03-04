package com.mycompany.projeto;

import java.io.Serializable;

public class Motor implements Serializable {

    private final TanqueCombustivel tanque;
    private final double cilindrada;
    private final int potencia;

    public Motor(int capacidadeTanque, TipoCombustivel tipoCombustivel, double cilindrada, int potencia) {
        this.tanque = new TanqueCombustivel(capacidadeTanque, tipoCombustivel);
        this.cilindrada = cilindrada;
        this.potencia = potencia;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public int getPotencia() {
        return potencia;
    }

    public TanqueCombustivel getTanque() {
        return tanque;
    }

    public void abastecer() {
        try {
            System.out.println("Combustivel insuficiente num motor para prosseguir com a missão");
            System.out.println("A abastecer com " + tanque.getTipoDeCombustivel() + "...");
            Thread.sleep(6000);
            tanque.abastecer();
        } catch (InterruptedException e) {
            System.out.println("Ocorreu uma interrupção durante o abastecimento: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("Motor [Cilindrada: %.2f, Potência: %d CV, Tanque: (%s)]",
                cilindrada,
                potencia,
                tanque.toString());
    }
}
