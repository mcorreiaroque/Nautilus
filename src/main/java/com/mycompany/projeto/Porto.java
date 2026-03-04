package com.mycompany.projeto;

import java.time.LocalDate;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Scanner;

public class Porto implements Serializable {

    // Atributos
    private static final long serialVersionUID = 1L;
    private String nome;
    private List<Embarcacao> embarcacoesAtracadas;
    private List<Embarcacao> embarcacoesEmMissao;
    private List<Marinheiro> marinheiros;
    private int totalMissoes;
    private boolean radarLigado;

    // Construtor
    public Porto(String nome) {
        this.nome = nome;
        this.embarcacoesAtracadas = new ArrayList<>();
        this.embarcacoesEmMissao = new ArrayList<>();
        this.marinheiros = new ArrayList<>();
        this.totalMissoes = 0;
        this.radarLigado = false;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Embarcacao> getEmbarcacoesAtracadas() {
        return embarcacoesAtracadas;
    }

    public List<Embarcacao> getEmbarcacoesEmMissao() {
        return embarcacoesEmMissao;
    }

    public List<Marinheiro> getMarinheiros() {
        return marinheiros;
    }

    public int getTotalMissoes() {
        return totalMissoes;
    }

    public boolean isRadarLigado() {
        return radarLigado;
    }

    public void setRadarLigado(boolean radarLigado) {
        this.radarLigado = radarLigado;
    }

    // Metodos
    @Override
    public String toString() {
        return "Porto{" + "nome='" + nome + '\'' + ", embarcacoesAtracadas = " + embarcacoesAtracadas + ", embarcacoesEmMissao = " + embarcacoesEmMissao + ", marinheiros = " + marinheiros + ", totalMissoes = " + totalMissoes + ", radarLigado = " + radarLigado + '}';
    }

    public void registarEmbarcacao(Embarcacao embarcacao) {
        // Verifica se embarcacao ja esta no porto
        if (procurarEmbarcacao(embarcacao.getNome()) != null) {
            throw new IllegalArgumentException("Embarcação " + embarcacao.getNome() + " já estava registada no porto.");
        }

        // Regista embarcacao
        embarcacoesAtracadas.add(embarcacao);
        System.out.println("Embarcação " + embarcacao.getNome() + " foi registada no porto.");
    }

    public void registarMarinheiro(Marinheiro marinheiro) {
        // Verifica se Marinheiro ja esta no porto
        if (procurarMarinheiro(marinheiro.getNome()) != null) {
            throw new IllegalArgumentException("Marinheiro " + marinheiro.getNome() + " já está registrado no porto.");
        }

        // Regista marinheiro
        marinheiros.add(marinheiro);
        System.out.println("Marinheiro " + marinheiro.getNome() + " foi registado no porto.");
    }

    public void ativarRadar() {
        if (!radarLigado) {
            this.radarLigado = true;
            System.out.println("Radar ativado.");
        } else {
            System.out.println("Radar já está ativado.");
        }
    }

    public void desativarRadar() {
        if (radarLigado) {
            this.radarLigado = false;
            System.out.println("Radar desativado.");
        } else {
            System.out.println("Radar já está desativado.");
        }
    }

    public void detectarEmbarcacoes(Scanner scanner) {
        if (!radarLigado) {
            throw new IllegalArgumentException("Radar está desligado. Não é possível detectar embarcações. Ligue o radar.");
        }

        System.out.println("Escolha a ordem para exibir as embarcações:");
        System.out.println("1. Por ID");
        System.out.println("2. Por Marca");
        System.out.println("3. Por Idade");
        int escolhaOrdem = scanner.nextInt();
        scanner.nextLine();

        switch (escolhaOrdem) {
            case 1 -> mostrarEmbarcacoes();
            case 2 -> mostrarEmbarcacoesPorMarca();
            case 3 -> mostrarEmbarcacoesPorIdadeDecrescente();
            default -> {
                System.out.println("Opção inválida! As embarcações serão exibidas por ID.\n");
                mostrarEmbarcacoes();
            }
        }

    }

    public void iniciarMissao(Embarcacao embarcacao) {
        Scanner scanner = new Scanner(System.in);
        if (embarcacao.isEmMissao() == false) {
            switch (embarcacao) {
                case NavioSuporte navioSuporte -> {
                    // Verifica se há entre 4 e 10 tripulantes e se existe um oficial e um sargento
                    boolean tripulacaoAdequada = embarcacao.getTripulacao().size() >= 4 && embarcacao.getTripulacao().size() <= 10
                            && embarcacao.getTripulacao().stream().anyMatch(m -> m.getPatente() == Patente.OFICIAL)
                            && embarcacao.getTripulacao().stream().anyMatch(m -> m.getPatente() == Patente.SARGENTO);
                    
                    // Verifica combustível
                    boolean combustivelSuficiente = embarcacao.getMotores().stream()
                            .allMatch(motor -> motor.getTanque().getCombustivelAtual() >= 0.75 * motor.getTanque().getCapacidade());
                    
                    if (!combustivelSuficiente) {
                        embarcacao.getMotores().forEach(motor -> {
                            if (motor.getTanque().getCombustivelAtual() < 0.75 * motor.getTanque().getCapacidade()) {
                                motor.abastecer();
                            }
                        });
                        System.out.println("Motores abastecidos.");
                    }
                    
                    // Verificar se todos os critérios são atendidos para missão
                    if (tripulacaoAdequada) {
                        embarcacao.iniciarMissao();
                        navioSuporte.ligarHolofote();
                        navioSuporte.ligarRadar();
                        totalMissoes++;
                        embarcacoesAtracadas.remove(embarcacao);
                        embarcacoesEmMissao.add(embarcacao);
                        System.out.println("Navio de Suporte " + embarcacao.getNome() + " vai ser enviado para missão.");
                        try {
                            Thread.sleep(2000);
                            Zona zonaNavioSuporte = embarcacao.getZona();
                            System.out.println("Embarcações na zona " + zonaNavioSuporte + ":");
                            
                            List<Embarcacao> embarcacoesNaMesmaZona = new ArrayList<>();
                            
                            for (Embarcacao e : embarcacoesEmMissao) {
                                if (e.getZona() == zonaNavioSuporte) {
                                    embarcacoesNaMesmaZona.add(e);
                                }
                            }
                            
                            Collections.sort(embarcacoesNaMesmaZona, (Embarcacao emb1, Embarcacao emb2) -> Integer.compare(emb1.getId(), emb2.getId()));
                            
                            for (Embarcacao e : embarcacoesNaMesmaZona) {
                                System.out.println("ID: " + e.getId() + " - Nome: " + e.getNome() + " - Tipo: " + e.getClass().getSimpleName() + " - Zona: " + e.getZona());
                            }
                            
                        } catch (InterruptedException e) {
                            System.out.println("A execução foi interrompida.");
                        }
                    } else {
                        
                        if (!tripulacaoAdequada) {
                            System.out.println("Navio de Suporte " + embarcacao.getNome() + " não tem tripulação adequada (deve ter entre 4 e 10 tripulantes, com ao menos 1 oficial e 1 sargento).");
                        }
                    }

                }
                case LanchaRapida lanchaRapida -> {
                    // Verificar se a Lancha Rápida tem entre 2 e 4 tripulantes e se há pelo menos um sargento
                    boolean tripulacaoAdequada = embarcacao.getTripulacao().size() >= 2 && embarcacao.getTripulacao().size() <= 4
                            && embarcacao.getTripulacao().stream().anyMatch(m -> m.getPatente() == Patente.SARGENTO);
                    
                    boolean combustivelSuficiente = embarcacao.getMotores().stream()
                            .allMatch(motor -> motor.getTanque().getCombustivelAtual() >= 0.75 * motor.getTanque().getCapacidade());
                    
                    if (!combustivelSuficiente) {
                        embarcacao.getMotores().forEach(motor -> {
                            if (motor.getTanque().getCombustivelAtual() < 0.75 * motor.getTanque().getCapacidade()) {
                                motor.abastecer();
                            }
                        });
                        System.out.println("Motores abastecidos.");
                    }
                    
                    // Verificar se todos os critérios são atendidos para missão
                    if (tripulacaoAdequada) {
                        embarcacao.iniciarMissao();
                        totalMissoes++;
                        lanchaRapida.ligarHolofote();
                        embarcacoesAtracadas.remove(embarcacao);
                        embarcacoesEmMissao.add(embarcacao);
                        System.out.println("Lancha Rápida " + embarcacao.getNome() + " vai ser enviada para perseguicao e captura.");
                    } else {
                        if (!tripulacaoAdequada) {
                            System.out.println("Lancha Rápida " + embarcacao.getNome() + " não tem tripulação adequada (deve ter entre 2 e 4 tripulantes e pelo menos 1 sargento).");
                        }
                    }
                    
                }
                case BarcoPatrulha barcoPatrulha -> {
                    // Verificar se o Barco de Patrulha tem entre 2 e 4 tripulantes e se há exatamente 1 oficial
                    boolean tripulacaoAdequada = embarcacao.getTripulacao().size() >= 2 && embarcacao.getTripulacao().size() <= 4
                            && embarcacao.getTripulacao().stream().filter(m -> m.getPatente() == Patente.OFICIAL).count() == 1;
                    
                    boolean combustivelSuficiente = embarcacao.getMotores().stream()
                            .allMatch(motor -> motor.getTanque().getCombustivelAtual() >= 0.75 * motor.getTanque().getCapacidade());
                    
                    if (!combustivelSuficiente) {
                        embarcacao.getMotores().forEach(motor -> {
                            if (motor.getTanque().getCombustivelAtual() < 0.75 * motor.getTanque().getCapacidade()) {
                                motor.abastecer();
                            }
                        });
                        System.out.println("Motores abastecidos.");
                    }
                    
                    // Verificar se todos os critérios são atendidos para missão
                    if (tripulacaoAdequada) {
                        embarcacao.iniciarMissao();
                        totalMissoes++;
                        barcoPatrulha.ligarHolofote();
                        barcoPatrulha.ligarRadar();
                        embarcacoesAtracadas.remove(embarcacao);
                        embarcacoesEmMissao.add(embarcacao);
                        System.out.println("Barco de Patrulha " + embarcacao.getNome() + " vai ser enviado para procura e salvamento.");
                    } else {
                        if (!tripulacaoAdequada) {
                            System.out.println("Barco de Patrulha " + embarcacao.getNome() + " não tem tripulação adequada (deve ter entre 2 e 4 tripulantes e exatamente 1 oficial).");
                        }
                    }
                    
                }
                default -> System.out.println("Tipo de embarcação não reconhecido para missão.");
            }
            if (embarcacao.isEmMissao() == true) {
                {
                    System.out.println("Escolha a zona de destino:");
                }
                System.out.println("1 - NORTE");
                System.out.println("2 - SUL");
                System.out.println("3 - LESTE");
                System.out.println("4 - OESTE");

                int escolhaZona = scanner.nextInt();
                scanner.nextLine();

                try {
                    Zona zonaDestino = Zona.escolha(escolhaZona);
                    System.out.println("Embarcação " + embarcacao.getNome() + " será enviada para a zona " + zonaDestino);

                    embarcacao.setZona(zonaDestino);
                } catch (IllegalArgumentException e) {
                    System.out.println("Opção inválida de zona.");
                }
            }
        } else {
            System.out.println("Embarcação " + embarcacao.getNome() + " já está em missão.");
        }
    }

    public void terminarMissao(Embarcacao embarcacao) {
        if (embarcacao.isEmMissao()) {
            switch (embarcacao) {
                case NavioSuporte navioSuporte -> {
                    navioSuporte.desligarHolofote();
                    navioSuporte.desligarRadar();
                    embarcacoesEmMissao.remove(embarcacao);
                    embarcacoesAtracadas.add(embarcacao);
                }
                case LanchaRapida lanchaRapida -> {
                    lanchaRapida.desligarHolofote();
                    embarcacoesEmMissao.remove(embarcacao);
                    embarcacoesAtracadas.add(embarcacao);
                    terminarMissaoLanchasNaMesmaZona(lanchaRapida);
                }
                case BarcoPatrulha barcoPatrulha -> {
                    barcoPatrulha.desligarHolofote();
                    barcoPatrulha.desligarRadar();
                    embarcacoesEmMissao.remove(embarcacao);
                    embarcacoesAtracadas.add(embarcacao);
                }
                default -> {
                }
            }

            embarcacao.terminarMissao();
            System.out.println("Missão da embarcação " + embarcacao.getNome() + " concluída.");

            embarcacao.setZona(Zona.ATRACADO);
            System.out.println("Embarcação " + embarcacao.getNome() + " agora está atracada.");
        } else {
            System.out.println("Embarcação " + embarcacao.getNome() + " não está em missão.");
        }
    }

    private void terminarMissaoLanchasNaMesmaZona(LanchaRapida lanchaRapida) {
        Zona zonaAtual = lanchaRapida.getZona();
        Set<Embarcacao> lanchasProcessadas = new HashSet<>();

        List<Embarcacao> embarcacoesCopia = new ArrayList<>(embarcacoesEmMissao);

        for (Embarcacao embarcacao : embarcacoesCopia) {
            if (embarcacao instanceof LanchaRapida outraLancha) {
                if (outraLancha.getZona() == zonaAtual && !lanchasProcessadas.contains(outraLancha)) {
                    terminarMissao(outraLancha);
                    lanchasProcessadas.add(outraLancha);
                }
            }
        }
    }

    public void adicionarNavioSuporte(Scanner scanner) {
        try {

            System.out.print("Nome do Navio: ");
            String nome1 = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();

            int capacidade = 0;
            while (capacidade < 1000) {
                System.out.print("Capacidade Carga (mínimo 1000): ");
                String capacidadeInput = scanner.nextLine();
                try {
                    capacidade = Integer.parseInt(capacidadeInput);
                    if (capacidade < 1000) {
                        System.out.println("A capacidade deve ser maior ou igual a 1000.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Valor inválido. Insira um número válido para a capacidade.");
                }
            }
            LocalDate dataFabrico = LocalDate.now();

            Motor motor1 = null;
            Motor motor2 = null;

            while (true) {
                System.out.println("Criação dos motores para o Navio Suporte");

                motor1 = criarMotor(scanner, 1);

                if (motor1.getPotencia() < 25000) {
                    System.out.println("A potência do primeiro motor deve ser igual ou superior a 25000. Tente novamente.");
                    continue;
                }

                motor2 = criarMotor(scanner, 2);

                if (motor2.getPotencia() < 25000) {
                    System.out.println("A potência do segundo motor deve ser igual ou superior a 25000. Tente novamente.");
                    continue;
                }

                break;
            }
            List<Motor> motores = new ArrayList<>();
            motores.add(motor1);
            motores.add(motor2);

            NavioSuporte barco = new NavioSuporte(nome1, marca, modelo, dataFabrico, motores, capacidade);
            registarEmbarcacao(barco);

            System.out.println("Navio de suporte criado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao criar navio de suporte: " + e.getMessage());
        }
    }

    public void adicionarLanchaRapida(Scanner scanner) {
        try {
            System.out.print("Nome da Lancha Rápida: ");
            String nome1 = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();

            LocalDate dataFabrico = LocalDate.now();

            int numMotores;
            do {
                System.out.print("Quantos motores deseja adicionar (2 a 4): ");
                numMotores = Integer.parseInt(scanner.nextLine());
            } while (numMotores < 2 || numMotores > 4);

            List<Motor> motores = new ArrayList<>();
            for (int i = 1; i <= numMotores; i++) {
                Motor motor = criarMotor(scanner, i);
                motores.add(motor);
            }

            LanchaRapida barco = new LanchaRapida(nome1, marca, modelo, dataFabrico, motores);
            registarEmbarcacao(barco);
        } catch (NumberFormatException e) {
            System.out.println("Erro inesperado: " + e.getMessage());

        }
    }
    
    public void adicionarBarcoPatrulha(Scanner scanner) {
        try {
            System.out.print("Nome do Barco de Patrulha: ");
            String nome1 = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();

            LocalDate dataFabrico = LocalDate.now();

            Motor motor1 = criarMotor(scanner, 1);
            List<Motor> motores = new ArrayList<>();
            motores.add(motor1);

            BarcoPatrulha barco = new BarcoPatrulha(nome1, marca, modelo, dataFabrico, motores);
            registarEmbarcacao(barco);

        } catch (Exception e) {
            System.out.println("Erro ao criar barco de patrulha: " + e.getMessage());
        }
    }

    public Embarcacao procurarEmbarcacao(String nome) {
        for (Embarcacao e : embarcacoesAtracadas) {
            if (e.getNome().equals(nome)) { 
                return e;
            }
        }

        for (Embarcacao e : embarcacoesEmMissao) {
            if (e.getNome().equals(nome)) { 
                return e;
            }
        }

        return null;
    }

    public int tipoEmbarcacao(Embarcacao embarcacao) {
        if (embarcacao instanceof NavioSuporte) {
            System.out.println(embarcacao.getNome() + " é um Navio Suporte.");
            return 1;
        } else if (embarcacao instanceof LanchaRapida) {
            System.out.println(embarcacao.getNome() + " é uma Lancha Rápida.");
            return 2;
        } else if (embarcacao instanceof BarcoPatrulha) {
            System.out.println(embarcacao.getNome() + " é um Barco de Patrulha.");
            return 3;
        }
        return 0;
    }

    public void editarEmbarcacao(Scanner scanner) {
        System.out.print("Informe o nome da embarcação a ser editada: ");
        String nomeEmbarcacao = scanner.nextLine();

        Embarcacao embarcacao = procurarEmbarcacao(nomeEmbarcacao); 
        if (embarcacao == null) {
            System.out.println("Embarcacao não encontrada.");
            return;
        }

        if (embarcacao.isEmMissao()) {
            System.out.println("A embarcação está em missão e não pode ser editada.");
            return; 
        }

        boolean sairEdicao = false;
        while (!sairEdicao) {
            System.out.println("\nEscolha o que deseja editar:");
            System.out.println("1 - Editar Nome");
            System.out.println("2 - Editar Modelo");
            System.out.println("3 - Editar Marca");
            System.out.println("4 - Editar Motores");
            System.out.println("5 - Voltar ao menu anterior");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Novo nome da embarcação: ");
                    String novoNome = scanner.nextLine();
                    embarcacao.setNome(novoNome); 
                    System.out.println("Nome da embarcação atualizado com sucesso!");
                }
                case 2 -> {
                    System.out.print("Novo modelo da embarcação: ");
                    String novoModelo = scanner.nextLine();
                    embarcacao.setModelo(novoModelo);  
                    System.out.println("Modelo da embarcação atualizado com sucesso!");
                }
                case 3 -> {
                    System.out.print("Nova marca da embarcação: ");
                    String novaMarca = scanner.nextLine();
                    embarcacao.setMarca(novaMarca);  
                    System.out.println("Marca da embarcação atualizada com sucesso!");
                }
                case 4 -> {
                    if (embarcacao instanceof BarcoPatrulha) {
                        System.out.println("Barco de Patrulha deve ter 1 motor.");
                        Motor motor = criarMotor(scanner, 1); 
                        List<Motor> novosMotores = new ArrayList<>();
                        novosMotores.add(motor);
                        embarcacao.setMotores(novosMotores); 
                        System.out.println("Motor do barco de patrulha atualizado com sucesso!");
                    } else if (embarcacao instanceof LanchaRapida) {
                        System.out.println("Escolha quantos motores a lancha rápida deve ter (2 a 4):");
                        int numMotores = scanner.nextInt();
                        scanner.nextLine();
                        if (numMotores >= 2 && numMotores <= 4) {
                            List<Motor> novosMotores = new ArrayList<>();
                            for (int i = 1; i <= numMotores; i++) {
                                Motor motorLancha = criarMotor(scanner, i); 
                                novosMotores.add(motorLancha);
                            }
                            embarcacao.setMotores(novosMotores);
                            System.out.println("Motores da lancha rápida atualizados com sucesso!");
                        } else {
                            System.out.println("Número de motores inválido para lancha rápida. Deve ser entre 2 e 4.");
                        }
                    } else if (embarcacao instanceof NavioSuporte) {
                        System.out.println("Navio de Suporte deve ter 2 motores.");
                        List<Motor> novosMotores = new ArrayList<>();
                        Motor motor1 = criarMotor(scanner, 1);
                        Motor motor2 = criarMotor(scanner, 2); 
                        novosMotores.add(motor1);
                        novosMotores.add(motor2);
                        embarcacao.setMotores(novosMotores); 
                        System.out.println("Motores do navio de suporte atualizados com sucesso!");
                    } else {
                        System.out.println("Tipo de embarcação desconhecido. Não é possível editar os motores.");
                    }
                }
                case 5 -> sairEdicao = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    public void removerEmbarcacao(Scanner scanner) {
        System.out.print("Informe o nome da embarcação a ser removida: ");
        String nomeEmbarcacao = scanner.nextLine();

        Embarcacao embarcacao = procurarEmbarcacao(nomeEmbarcacao);  
        if (embarcacao != null) {
        } else {
            System.out.println("Embarcacao não encontrada.");
            return;
        }

        if (embarcacao.isEmMissao()) {
            System.out.println("A embarcação está em missão e não pode ser removida.");
            return; 
        }

        System.out.println("Tem certeza que deseja remover a embarcação '" + nomeEmbarcacao + "'? (S/N)");
        String confirmar = scanner.nextLine();

        if (!confirmar.equalsIgnoreCase("S")) {
            System.out.println("Remoção cancelada.");
        } else {
            embarcacoesAtracadas.remove(embarcacao);
            System.out.println("Embarcação '" + nomeEmbarcacao + "' removida com sucesso!");
        }
    }

    private Motor criarMotor(Scanner scanner, int numeroMotor) {
        System.out.println("----- Motor " + numeroMotor + " -----");

        int capacidadeCombustivel;
        while (true) {
            try {
                System.out.print("Capacidade de combustível (em litros): ");
                capacidadeCombustivel = Integer.parseInt(scanner.nextLine());
                if (capacidadeCombustivel <= 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido para a capacidade de combustível (em litros).");
            }
        }

        TipoCombustivel tipoCombustivel = null;
        while (tipoCombustivel == null) {
            System.out.println("Escolha o tipo de combustível para o motor " + numeroMotor + ":");
            System.out.println("1. GASOLINA");
            System.out.println("2. GASOLEO");
            System.out.println("3. ETANOL");

            String tipoCombustivelEscolha = scanner.nextLine();
            tipoCombustivel = escolherTipoCombustivel(tipoCombustivelEscolha);
            if (tipoCombustivel == null) {
                System.out.println("Escolha inválida! Por favor, insira um número válido.");
            }
        }

        int cilindrada;
        while (true) {
            try {
                System.out.print("Cilindrada (em cm³): ");
                cilindrada = Integer.parseInt(scanner.nextLine());
                if (cilindrada <= 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido para a cilindrada (em cm³).");
            }
        }

        int potencia;
        while (true) {
            try {
                System.out.print("Potência (em CV): ");
                potencia = Integer.parseInt(scanner.nextLine());
                if (potencia <= 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido para a potência (em CV).");
            }
        }

        return new Motor(capacidadeCombustivel, tipoCombustivel, cilindrada, potencia);
    }

    private TipoCombustivel escolherTipoCombustivel(String escolha) {
        switch (escolha) {
            case "1" -> {
                return TipoCombustivel.GASOLINA;
            }
            case "2" -> {
                return TipoCombustivel.GASOLEO;
            }
            case "3" -> {
                return TipoCombustivel.ETANOL;
            }
            default -> throw new IllegalArgumentException("Tipo de combustível inválido.");
        }
    }

    public void criarMarinheiro(Scanner scanner) {
        try {
            System.out.print("Nome do Marinheiro: ");
            String nome1 = scanner.nextLine();

            LocalDate nascimento = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            while (nascimento == null) {
                try {
                    System.out.print("Data de nascimento (ano-mês-dia): ");
                    String dataNascimento = scanner.nextLine();
                    nascimento = LocalDate.parse(dataNascimento, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Por favor, insira a data no formato ano-mês-dia (yyyy-MM-dd).");
                }
            }
            System.out.println("Escolha a patente do Marinheiro:");
            System.out.println("1 - SOLDADO");
            System.out.println("2 - SARGENTO");
            System.out.println("3 - OFICIAL");
            int opcaoPatente = scanner.nextInt();
            scanner.nextLine();
            Patente patente;
            switch (opcaoPatente) {
                case 1 -> patente = Patente.PRACA;
                case 2 -> patente = Patente.SARGENTO;
                case 3 -> patente = Patente.OFICIAL;
                default -> {
                    System.out.println("Patente inválida, atribuindo como PRACA.");
                    patente = Patente.PRACA;
                }
            }
            Marinheiro marinheiro = new Marinheiro(nome1, patente, nascimento);
            marinheiros.add(marinheiro);
            System.out.println("Marinheiro criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar marinheiro: " + e.getMessage());
        }
    }

    public Marinheiro procurarMarinheiro(String nome) {
        for (Marinheiro marinheiro : marinheiros) {
            if (marinheiro.getNome().equals(nome)) { 
                return marinheiro;
            }
        }
        System.out.println("Marinheiro não encontrado.");
        return null;
    }

    public void editarMarinheiro(Scanner scanner) {
        System.out.print("Informe o nome do marinheiro a ser editado: ");
        String nomeMarinheiro = scanner.nextLine();

        Marinheiro marinheiro = procurarMarinheiro(nomeMarinheiro); 
        if (marinheiro == null) {
            return;
        }

        boolean sairEdicao = false;
        while (!sairEdicao) {
            System.out.println("\nEscolha o que deseja editar:");
            System.out.println("1 - Editar Nome");
            System.out.println("2 - Editar Patente");
            System.out.println("3 - Voltar ao menu anterior");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Novo nome do marinheiro: ");
                    String novoNome = scanner.nextLine();
                    marinheiro.setNome(novoNome);  
                    System.out.println("Nome do marinheiro atualizado com sucesso!");
                }
                case 2 -> {
                    System.out.println("Escolha a nova patente do Marinheiro:");
                    System.out.println("1 - PRACA");
                    System.out.println("2 - SARGENTO");
                    System.out.println("3 - OFICIAL");
                    int opcaoPatente = scanner.nextInt();
                    scanner.nextLine();

                    Patente novaPatente;
                    switch (opcaoPatente) {
                        case 1 -> novaPatente = Patente.PRACA;
                        case 2 -> novaPatente = Patente.SARGENTO;
                        case 3 -> novaPatente = Patente.OFICIAL;
                        default -> {
                            System.out.println("Patente inválida. Mantendo a patente atual.");
                            return; 
                    }
                    }

                    if (marinheiro.getPatente() == Patente.PRACA && novaPatente == Patente.SARGENTO) {
                        System.out.println("Parabéns pela promoção" + marinheiro.getNome() + "!!");
                    } else if (marinheiro.getPatente() == Patente.SARGENTO && novaPatente == Patente.OFICIAL) {
                        System.out.println("Parabéns pela promoção de SARGENTO para OFICIAL " + marinheiro.getNome() + "!!!");
                    }

                    marinheiro.setPatente(novaPatente);
                    System.out.println("Patente do marinheiro atualizada com sucesso!");
                }
                case 3 -> sairEdicao = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    public void removerMarinheiro(Scanner scanner) {
        System.out.print("Informe o nome do marinheiro a ser removido: ");
        String nomeMarinheiro = scanner.nextLine();
        Marinheiro marinheiro = procurarMarinheiro(nomeMarinheiro);  
        
        if (marinheiro == null) {
            return;
        }
        
        System.out.println("Tem certeza que deseja remover o marinheiro '" + nomeMarinheiro + "'? (S/N)");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            marinheiros.remove(marinheiro); 
            System.out.println("Marinheiro '" + nomeMarinheiro + "' removido com sucesso!");
        } else {
            System.out.println("Remoção cancelada.");
        }
    }

    public void mostrarMarinheiros() {
        if (marinheiros.isEmpty()) {
            System.out.println("Não há marinheiros no porto.");
        } 
        else {
            Collections.sort(marinheiros, Comparator.comparing(Marinheiro::getNome));
            for (Marinheiro marinheiro : marinheiros) {
                System.out.println(marinheiro);
            }
        }
    }

    public void mostrarMarinheirosPorID() {
        if (marinheiros.isEmpty()) {
            System.out.println("Não há marinheiros no porto.");
        } else {
            Collections.sort(marinheiros, Comparator.comparingInt(Marinheiro::getId));
            for (Marinheiro marinheiro : marinheiros) {
                System.out.println(marinheiro);
            }
        }
    }

    public void mostrarMarinheirosPorIdadeDecrescente() {
        if (marinheiros.isEmpty()) {
            System.out.println("Não há marinheiros no porto.");
        } else {
            Collections.sort(marinheiros, (m1, m2) -> Integer.compare(m2.getIdade().getAnos(), m1.getIdade().getAnos()));

            for (Marinheiro marinheiro : marinheiros) {
                System.out.println(marinheiro);
            }
        }
    }

    public void mostrarEmbarcacoes() {
        Collections.sort(embarcacoesAtracadas, (emb1, emb2) -> Integer.compare(emb1.getId(), emb2.getId()));
        Collections.sort(embarcacoesEmMissao, (emb1, emb2) -> Integer.compare(emb1.getId(), emb2.getId()));

        System.out.println("Embarcações Atracadas ordenadas por ID:");
        for (Embarcacao embarcacao : embarcacoesAtracadas) {
            System.out.println(embarcacao); 
        }

        System.out.println("\nEmbarcações em Missão ordenadas por ID:");
        for (Embarcacao embarcacao : embarcacoesEmMissao) {
            System.out.println(embarcacao); 
        }
    }

    public void mostrarEmbarcacoesPorMarca() {
        List<Embarcacao> todasEmbarcacoes = new ArrayList<>();
        todasEmbarcacoes.addAll(embarcacoesAtracadas);
        todasEmbarcacoes.addAll(embarcacoesEmMissao);

        Collections.sort(todasEmbarcacoes, Comparator.comparing(Embarcacao::getMarca));

        System.out.println("Embarcações (ordenadas por marca):");
        for (Embarcacao embarcacao : todasEmbarcacoes) {
            System.out.println(embarcacao);
        }
    }

    public void mostrarEmbarcacoesPorIdadeDecrescente() {
        List<Embarcacao> todasEmbarcacoes = new ArrayList<>();
        todasEmbarcacoes.addAll(embarcacoesAtracadas);
        todasEmbarcacoes.addAll(embarcacoesEmMissao);

        Collections.sort(todasEmbarcacoes, (emb1, emb2) -> {
            Idade idade1 = emb1.getIdade();
            Idade idade2 = emb2.getIdade();
            return Integer.compare(idade2.getAnos(), idade1.getAnos());
        });

        System.out.println("Embarcações (ordenadas por idade - decrescente):");
        for (Embarcacao embarcacao : todasEmbarcacoes) {
            Idade idade = embarcacao.getIdade();
            System.out.println(embarcacao + " - Idade: " + idade);
        }
    }

    // Método para salvar os dados do porto em um ficheiro
    public void salvarDados(String nomeFicheiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFicheiro))) {
            oos.writeObject(this);
            System.out.println("Dados do porto salvos com sucesso no ficheiro: " + nomeFicheiro);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    // Método para RECOVER
    public static Porto carregarDados(String nomeFicheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFicheiro))) {
            Porto portoCarregado = (Porto) ois.readObject();
            System.out.println("Dados do porto carregados com sucesso do ficheiro: " + nomeFicheiro);
            return portoCarregado;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
            return null;
        }
    }

    // Método para Ficheiro txt com embarcações
    public void exportarEmbarcacoes(String nomeFicheiro) {
        try (BufferedWriter BufferedWriter = new BufferedWriter(new FileWriter(nomeFicheiro))) {
            BufferedWriter.write("Lista de Embarcações\n");
            BufferedWriter.write("=====================\n");
            BufferedWriter.write("======Atracadas======\n");
            BufferedWriter.write("=====================\n");
            for (Embarcacao embarcacao : embarcacoesAtracadas) {
                BufferedWriter.write(embarcacao.toString()); 
                BufferedWriter.write("\n---------------------\n");
            }
            BufferedWriter.write("======Em Missao======\n");
            BufferedWriter.write("=====================\n");
            for (Embarcacao embarcacao : embarcacoesEmMissao) {
                BufferedWriter.write(embarcacao.toString()); 
                BufferedWriter.write("\n---------------------\n");
            }

            BufferedWriter.close();
            System.out.println("Lista de embarcações exportada com sucesso para " + nomeFicheiro);
        } catch (IOException e) {
            System.out.println("Erro ao exportar lista de embarcações: " + e.getMessage());
        }
    }

}
