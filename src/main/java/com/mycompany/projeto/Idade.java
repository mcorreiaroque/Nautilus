package com.mycompany.projeto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Idade implements Serializable {
    private final int anos;
    private final int meses;

    private Idade(int anos, int meses) {
        this.anos = anos;
        this.meses = meses;
    }

    public static Idade calcularIdade(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        Period periodo = Period.between(dataNascimento, hoje); 
        return new Idade(periodo.getYears(), periodo.getMonths()); 
    }

    public int getAnos() {
        return anos;
    }

    public int getMeses() {
        return meses;
    }

    @Override
    public String toString() {
        return anos + " anos e " + meses + " meses";
    }
}

