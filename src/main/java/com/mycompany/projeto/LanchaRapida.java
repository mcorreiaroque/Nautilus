package com.mycompany.projeto;

import java.time.LocalDate;
import java.util.List;

public class LanchaRapida extends Embarcacao {

    private boolean holofote = false;

    public LanchaRapida(String nome, String marca, String modelo, LocalDate dataFabrico, List<Motor> motores) {
        super(nome, marca, modelo, dataFabrico);

        if (motores.size() < 2 || motores.size() > 4) {
            throw new IllegalArgumentException("Uma Lancha Rápida deve ter entre 2 e 4 motores.");
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

    @Override
    public String toString() {
        return String.format("Lancha Rápida [ID: %d, Nome: %s, Marca: %s, Modelo: %s, Data de Fabricação: %s, Zona: %s, Em Missão: %b, Holofote: %b, Motores: %s, Tripulação: %s]",getId(),getNome(),getMarca(),getModelo(),getDataFabrico(),getZona(),isEmMissao(),holofote,getMotores(),getTripulacao());
    }
}
