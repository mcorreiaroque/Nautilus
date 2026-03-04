package com.mycompany.projeto;

import java.util.*;

public class Projeto {

    private Porto porto;

    public static void main(String[] args) {
        Projeto projeto = new Projeto();
        projeto.menu();
    }

    public Projeto() {
        porto = new Porto("Porto Principal");
    }

    public void menu() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("=========== NAUTILUS =============");
            System.out.println("==================================");
            System.out.println("SISTEMA DE GESTAO DE FROTA NAUTICA");
            System.out.println("==================================");
            System.out.println("|| 1 - Modo de Manutenção");
            System.out.println("|| 2 - Modo de Utilização");
            System.out.println("|| 3 - Sair");
            System.out.println("|| 4 - RECOVER");
            System.out.print("|| Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            scanner.close();
            
            switch (opcao) {
                case 1 -> menuManutencao();
                case 2 -> menuUtilizacao();
                case 3 -> {
                    System.out.println("Saindo do sistema...");
                    return;
                }
                case 4 -> {
                    System.out.print("Digite o nome do ficheiro para recuperar os dados: ");
                    String recuperarFicheiro = scanner.nextLine();
                    Porto dadosRecuperados = Porto.carregarDados(recuperarFicheiro);
                    if (dadosRecuperados != null) {
                        porto = dadosRecuperados;
                        System.out.println("Dados recuperados com sucesso");
                    } else {
                        System.out.println("Falha ao recuperar dados. Verifique o ficheiro.");
                    }
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public void menuManutencao() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=========== NAUTILUS =============");
            System.out.println("SISTEMA DE GESTAO DE FROTA NAUTICA");
            System.out.println("==================================");
            System.out.println("======= Modo de Manutenção =======");
            System.out.println("==================================");
            System.out.println("|| 1 - Gerir Embarcações");
            System.out.println("|| 2 - Gerir Marinheiros");
            System.out.println("|| 3 - Sair do Modo de Manutenção");
            System.out.printf("|| R: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1 -> menuGerirEmbarcacoes(scanner);
                case 2 -> menuGerirMarinheiros(scanner);
                case 3 -> {
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    public void menuGerirEmbarcacoes(Scanner scanner) {
        while (true) {
            System.out.println("\n=========== NAUTILUS =============");
            System.out.println("SISTEMA DE GESTAO DE FROTA NAUTICA");
            System.out.println("==================================");
            System.out.println("======= Gerir Embarcações ========");
            System.out.println("==================================");
            System.out.println("|| 1 - Adicionar Embarcação");
            System.out.println("|| 2 - Editar Embarcação");
            System.out.println("|| 3 - Remover Embarcação");
            System.out.println("|| 4 - Voltar ao Menu Principal");
            System.out.print("|| R: ");
            int escolhaEmbarcacao = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaEmbarcacao) {
                case 1 -> {
                    System.out.println("\nEscolha o tipo de embarcação para adicionar");
                    System.out.println("1 - Navio");
                    System.out.println("2 - Lancha Rápida");
                    System.out.println("3 - Barco de Patrulha");
                    System.out.print("R: ");
                    int tipoEmbarcacao = scanner.nextInt();
                    scanner.nextLine();

                    switch (tipoEmbarcacao) {
                        case 1 -> porto.adicionarNavioSuporte(scanner);
                        case 2 -> porto.adicionarLanchaRapida(scanner);
                        case 3 -> porto.adicionarBarcoPatrulha(scanner);
                        default -> System.out.println("Tipo de embarcação inválido.");
                    }
                }

                case 2 -> porto.editarEmbarcacao(scanner);
                case 3 -> porto.removerEmbarcacao(scanner);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    public void menuGerirMarinheiros(Scanner scanner) {
        while (true) {
            System.out.println("\n=========== NAUTILUS =============");
            System.out.println("SISTEMA DE GESTAO DE FROTA NAUTICA");
            System.out.println("==================================");
            System.out.println("======= Gerir Marinheiros ========");
            System.out.println("==================================");
            System.out.println("|| 1 - Adicionar Marinheiro");
            System.out.println("|| 2 - Editar Marinheiro");
            System.out.println("|| 3 - Remover Marinheiro");
            System.out.println("|| 4 - Voltar ao Menu Principal");
            System.out.print("|| R: ");
            int escolhaMarinheiro = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaMarinheiro) {
                case 1 -> porto.criarMarinheiro(scanner);
                case 2 -> porto.editarMarinheiro(scanner);
                case 3 -> porto.removerMarinheiro(scanner);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    public void menuUtilizacao() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n============ NAUTILUS ============");
            System.out.println("SISTEMA DE GESTAO DE FROTA NAUTICA");
            System.out.println("==================================");
            System.out.println("======= Modo de Utilização =======");
            System.out.println("==================================");
            System.out.println("1 - Gerir Missões");
            System.out.println("2 - Detectar Embarcações");
            System.out.println("3 - Ativar/Desativar Radar");
            System.out.println("4 - Gerir Marinheiros");
            System.out.println("5 - Total Missões");
            System.out.println("6 - Exportar Embarcações");
            System.out.println("7 - Gravar Estado");
            System.out.println("8 - Sair");
            System.out.print("R: ");

            int escolha = scanner.nextInt();
            scanner.nextLine(); 

            switch (escolha) {
                case 1 -> {
                    System.out.println("1 - Iniciar Missão");
                    System.out.println("2 - Terminar Missão");
                    System.out.println("3 - Sair");
                    System.out.print("Escolha uma opção: ");
                    int subEscolha = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (subEscolha) {
                        case 1 -> {
                            System.out.print("Nome da embarcação para enviar: ");
                            String nome = scanner.nextLine();
                            Embarcacao emb = porto.procurarEmbarcacao(nome); 
                            if (emb != null) {
                                porto.iniciarMissao(emb);
                            } else {
                                System.out.println("Embarcação não encontrada.");
                            }
                        }
                        
                        case 2 -> {
                            System.out.print("Nome da embarcação para atracar: ");
                            String atracar = scanner.nextLine();
                            Embarcacao embAtracar = porto.procurarEmbarcacao(atracar);
                            if (embAtracar != null) {
                                porto.terminarMissao(embAtracar);
                            } else {
                                System.out.println("Embarcação não encontrada.");
                            }
                        }
                        case 3 -> {
                            return;
                        }
                        default -> System.out.println("Opção inválida.");
                    }
                }

                case 2 -> {
                    if (porto.isRadarLigado()) {
                        porto.detectarEmbarcacoes(scanner);
                    } else {
                        System.out.println("O radar não está ligado. Por favor, ative o radar primeiro.");
                    }
                }

                case 3 -> {
                    System.out.println("1 - Ativar Radar");
                    System.out.println("2 - Desativar Radar");
                    System.out.println("3 - Sair");
                    int radarEscolha = scanner.nextInt();
                    scanner.nextLine(); 
                    switch (radarEscolha) {
                        case 1 -> porto.ativarRadar();
                        case 2 -> porto.desativarRadar();
                        case 3 -> {
                            return;
                        }
                        default -> System.out.println("Opção inválida.");
                    }
                }


                case 4 -> {
                    System.out.println("1 - Alocar Marinheiro");
                    System.out.println("2 - Desalocar Marinheiro");
                    System.out.println("3 - Exibir Marinheiros");
                    System.out.println("4 - Sair");
                    System.out.print("Escolha uma opção: ");
                    int escolhaMarinheiro = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (escolhaMarinheiro) {
                        case 1 -> {
                            System.out.print("Nome da embarcação para alocar: ");
                            String nomeEmbarcacao = scanner.nextLine();
                            Embarcacao embarcacaoAlocar = porto.procurarEmbarcacao(nomeEmbarcacao);
                            if (embarcacaoAlocar != null) {
                                System.out.print("Nome do marinheiro: ");
                                String nomeMarinheiro = scanner.nextLine();
                                Marinheiro marinheiro = porto.procurarMarinheiro(nomeMarinheiro);
                                if (marinheiro != null) {
                                    embarcacaoAlocar.adicionarMarinheiro(marinheiro);
                                    System.out.println("Marinheiro " + marinheiro.getNome() + " alocado na embarcação " + embarcacaoAlocar.getNome());
                                } else {
                                    System.out.println("Marinheiro não encontrado.");
                                }
                            } else {
                                System.out.println("Embarcação não encontrada.");
                            }
                        }
                        
                        case 2 -> {
                            System.out.print("Nome do marinheiro para desalocar: ");
                            String nomeMarinheiroDesalocar = scanner.nextLine();
                            Marinheiro marinheiroDesalocar = porto.procurarMarinheiro(nomeMarinheiroDesalocar);
                            if (marinheiroDesalocar != null) {
                                if (marinheiroDesalocar.nomeEmbarcacao != null) {
                                    Embarcacao embarcacao = porto.procurarEmbarcacao(marinheiroDesalocar.getNomeEmbarcacao());
                                    if (embarcacao != null) {
                                        embarcacao.removerMarinheiro(marinheiroDesalocar);
                                        System.out.println("Marinheiro " + marinheiroDesalocar.getNome() + " foi desalocado da embarcação " + embarcacao.getNome());
                                    } else {
                                        System.out.println("Embarcação não encontrada.");
                                    }
                                } else {
                                    System.out.println("O marinheiro " + marinheiroDesalocar.getNome() + " não está alocado em nenhuma embarcação.");
                                }
                            } else {
                            }
                        }
                        
                        case 3 -> {
                            System.out.println("Exibir marinheiros por:");
                            System.out.println("1 - Nome");
                            System.out.println("2 - ID");
                            System.out.println("3 - Idade");
                            System.out.println("4 - Sair");
                            System.out.print("Escolha uma opção: ");
                            int escolhaExibir = scanner.nextInt();
                            scanner.nextLine();

                            switch (escolhaExibir) {
                                case 1 -> porto.mostrarMarinheiros();
                                case 2 -> porto.mostrarMarinheirosPorID();
                                case 3 -> porto.mostrarMarinheirosPorIdadeDecrescente();
                                case 4 -> {
                                }
                                default -> System.out.println("Opção inválida.");
                            }
                        }
                        
                        case 4 -> {
                            return;
                        }
                        default -> System.out.println("Opção inválida.");
                    }
                }


                case 5 -> System.out.println("Total Missões Porto: " + porto.getTotalMissoes());

                case 6 -> {
                    System.out.print("Digite o nome do ficheiro para exportar a lista de embarcações: ");
                    String nomeFicheiroExportar = scanner.nextLine();
                    porto.exportarEmbarcacoes(nomeFicheiroExportar);
                }

                case 7 -> {
                    System.out.print("Digite o nome do ficheiro para gravar os dados: ");
                    String ficheiroGravar = scanner.nextLine();
                    porto.salvarDados(ficheiroGravar);
                    System.out.println("Estado gravado com sucesso em " + ficheiroGravar);
                }

                case 8 -> {
                    return;
                }

                default -> System.out.println("Opção inválida.");
            }
        }
    }

}
