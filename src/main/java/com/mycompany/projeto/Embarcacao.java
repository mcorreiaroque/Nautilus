
package com.mycompany.projeto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Embarcacao implements Serializable {

    private final int id;
    private String nome;
    private String marca;
    private String modelo;
    private final LocalDate dataFabrico;
    private Zona zona = Zona.ATRACADO;
    private final Idade idade;
    private static int contadorID = 0;
    private List<Motor> motores = new ArrayList<>();
    private List<Marinheiro> tripulacao = new ArrayList<>();
    private boolean emMissao;

    public Embarcacao(String nome, String marca, String modelo, LocalDate dataFabrico) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da embarcação não pode ser vazio.");
        }
        if (modelo == null || modelo.isEmpty()) {
            throw new IllegalArgumentException("O modelo da embarcação não pode ser vazio.");
        }
        if (marca == null || marca.isEmpty()) {
            throw new IllegalArgumentException("A marca da embarcação não pode ser vazia.");
        }
        this.id = ++contadorID;
        this.nome = nome;
        this.marca = marca;
        this.modelo = modelo;
        this.dataFabrico = dataFabrico;
        this.idade = Idade.calcularIdade(dataFabrico);
        this.emMissao = false;
    }

     // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public LocalDate getDataFabrico() {
        return dataFabrico;
    }

    public Zona getZona() {
        return zona;
    }

    public Idade getIdade() {
        return idade;
    }

    public List<Motor> getMotores() {
        return motores;
    }

    public int getNumeroDeMotores() {
        return motores.size();
    }

    public boolean isEmMissao() {
        return emMissao;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public void setMotores(List<Motor> motores) {
        this.motores = motores;
    }

    public void adicionarMotor(Motor motor) {
        this.motores.add(motor);
    }

    public void removerMotor(Motor motor) {
        this.motores.remove(motor);
    }

    public List<Marinheiro> getTripulacao() {
        return tripulacao;
    }

    public boolean isTripulacaoCompleta() {
        // Exemplo de regra para tripulação completa
        return tripulacao.size() >= 5;  // Exemplo: mínimo de 5 marinheiros
    }

    public void iniciarMissao() {
        this.emMissao = true;
    }

    public void terminarMissao() {
        this.emMissao = false;
    }

    public void adicionarMotores(List<Motor> novosMotores) {
        if (this.motores == null) {
            this.motores = new ArrayList<>();
        }

        // Copiar a lista para evitar modificação direta durante a iteração
        List<Motor> copiaMotores = new ArrayList<>(novosMotores);
        for (Motor motor : copiaMotores) {
            if (!this.motores.contains(motor)) {
                this.motores.add(motor);
            }
        }
    }

    public void adicionarMarinheiro(Marinheiro marinheiro) {
        if(marinheiro.alocado == false && marinheiro.nomeEmbarcacao == null){
            if (!tripulacao.contains(marinheiro)) {
                tripulacao.add(marinheiro);
                marinheiro.alocado = true;
                marinheiro.nomeEmbarcacao = this.getNome();
            } else {
                System.out.println("Marinheiro já está na tripulação.");
            }
        } else {
            System.out.println("Marinheiro já está noutra tripulacao.");
        }
    }

    public void removerMarinheiro(Marinheiro marinheiro) {
        tripulacao.remove(marinheiro);
        marinheiro.alocado = false;
        marinheiro.nomeEmbarcacao = null;
    }
}
