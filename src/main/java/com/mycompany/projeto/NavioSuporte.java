package com.mycompany.projeto;

import java.time.LocalDate;
import java.util.List;

public class NavioSuporte extends Embarcacao {

    private boolean holofote = false;
    private boolean radar = false;
    private final boolean botesSalvaVidas = true;
    private final int capacidadeCarga;
    private final int numeroCamas = 10;

    public NavioSuporte(String nome, String marca, String modelo, LocalDate dataFabrico, List<Motor> motores, int capacidadeCarga) {
        super(nome, marca, modelo, dataFabrico);

        if (motores == null || motores.size() != 2) {
            throw new IllegalArgumentException("Um Navio de Suporte deve ter exatamente 2 motores. Total fornecido: " + (motores == null ? 0 : motores.size()));
        }

        if (capacidadeCarga < 1000) {
            throw new IllegalArgumentException("A capacidade de carga deve ser no mínimo 1000. Valor atual: " + capacidadeCarga);
        }

        this.capacidadeCarga = capacidadeCarga;
        adicionarMotores(motores);

    }

    public void ligarHolofote() {
        if (!this.holofote) {
            this.holofote = true;
            System.out.println("Holofote ligado.");
        } else {
            System.out.println("O holofote já está ligado.");
        }
    }

    public void desligarHolofote() {
        if (this.holofote) {
            this.holofote = false;
            System.out.println("Holofote desligado.");
        } else {
            System.out.println("O holofote já está desligado.");
        }
    }

    public void ligarRadar() {
        if (!this.radar) {
            this.radar = true;
            System.out.println("Radar ligado.");
        } else {
            System.out.println("O radar já está ligado.");
        }
    }

    public void desligarRadar() {
        if (this.radar) {
            this.radar = false;
            System.out.println("Radar desligado.");
        } else {
            System.out.println("O radar já está desligado.");
        }
    }

    public boolean hasBotesSalvaVidas() {
        return botesSalvaVidas;
    }

    public int getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public int getNumeroCamas() {
        return numeroCamas;
    }

    @Override
    public String toString() {
        return String.format("Navio de Suporte [ID: %d, Nome: %s, Marca: %s, Modelo: %s, Data de Fabricação: %s, Zona: %s, Capacidade de Carga: %d, Número de Camas: %d, Em Missão: %b, Motores: %s, Tripulação: %s]",getId(),getNome(),getMarca(),getModelo(),getDataFabrico(),getZona(),capacidadeCarga,numeroCamas,isEmMissao(),getMotores(),getTripulacao());
    }
}
