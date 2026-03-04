package com.mycompany.projeto;

import java.io.Serializable;
import java.time.LocalDate;

public class Marinheiro implements Serializable {

    private final int id;
    private String nome;
    private Patente patente;
    private final LocalDate nascimento;
    private final Idade idade;
    private static int contadorID = 0;
    public boolean alocado;
    public String nomeEmbarcacao;
    
    public Marinheiro(String nome, Patente patente, LocalDate data) {
        this.id = ++contadorID;
        
        
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome do marinheiro não pode ser vazio.");
        }
        this.nome = nome;
        this.nascimento = data;
        if(Idade.calcularIdade(data).getAnos() >= 100){
            throw new IllegalArgumentException("Marinheiro já incapacitado, tem idade para dormir.");
        }
        this.idade = Idade.calcularIdade(data);
        
        if (patente == null || !isPatenteValida(patente)) {
            throw new IllegalArgumentException("A patente fornecida não é válida: " + patente);
        }
        if( patente == Patente.OFICIAL && idade.getAnos() < 35){
            throw new IllegalArgumentException("Não pode haver OFICIAIS com menos de 35 anos, idade: " + idade);
        }
        this.patente = patente;
        this.nomeEmbarcacao = null;
        this.alocado = false;
    }

    // Get's e Set's
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Patente getPatente() {
        return patente;
    }

    public void setPatente(Patente patente) {
        this.patente = patente;
    }
    
    public LocalDate getNascimento() {
        return nascimento;
    }

    public Idade getIdade() {
        return idade;
    }

    public String getNomeEmbarcacao() {
        return nomeEmbarcacao;
    }

    public boolean getAlocado() {
        return alocado;
    }
    
    // Metodos
    private boolean isPatenteValida(Patente patente) {
        for (Patente p : Patente.values()) {
            if (p == patente) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Nome: " + nome + ", ID: " + id + ", Idade: " + idade + ", Patente: " + patente + ", Alocado em: " + nomeEmbarcacao;
    }
    
}
