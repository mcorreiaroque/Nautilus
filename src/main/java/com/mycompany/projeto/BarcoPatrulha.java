package com.mycompany.projeto;

import java.time.LocalDate;
import java.util.List;

public class BarcoPatrulha extends Embarcacao {

    private boolean holofote = false;
    private boolean radar = false;

    public BarcoPatrulha(String nome, String marca, String modelo, LocalDate dataFabrico, List<Motor> motores) {
        super(nome, marca, modelo, dataFabrico);

        if (motores.size() != 1) {
            throw new IllegalArgumentException("Um Barco de Patrulha deve ter exatamente 1 motor.");
        }

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

    @Override
    public String toString() {
        StringBuilder motoresString = new StringBuilder();
        for (Motor motor : getMotores()) {
            motoresString.append(motor.toString()).append(", ");
        }

        StringBuilder marinheirosString = new StringBuilder();
        for (Marinheiro marinheiro : getTripulacao()) {
            marinheirosString.append(marinheiro.getNome()).append(", ");
        }

        if (motoresString.length() > 0) {
            motoresString.setLength(motoresString.length() - 2);
        }
        if (marinheirosString.length() > 0) {
            marinheirosString.setLength(marinheirosString.length() - 2);
        }

        return String.format("Barco de Patrulha [ID: %d, Nome: %s, Marca: %s, Modelo: %s, Data de Fabricação: %s, Em Missão: %b, Zona: %s, Motores: [%s], Tripulação: [%s]]", getId(),getNome(),getMarca(),getModelo(),getDataFabrico(),isEmMissao(),getZona(),motoresString.toString(),marinheirosString.toString());
    }
}
