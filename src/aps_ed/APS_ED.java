/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps_ed;

import java.util.Scanner;

/**
 *
 * @author Gustavo de Jesus Santos - RA 21000084
 *          
 */
public class APS_ED {

    public static void main(String[] args) {
        
        int cont = 0, cont2 = 0;
        int expediente;
        int id;
        int tempoEstimado;
        int prioridade = -1;
        int tempoGasto;
        String nome;
        String mensagem;
        
        Scanner scanner = new Scanner(System.in);
        
        GerenciaServiçosFilial filial;
        Serviços serviço;
        
        ListaDinamica lista = new ListaDinamica();
        GerenciadorDeDistribuição gerenciador = new GerenciadorDeDistribuição(lista);
        
        System.out.println("Sistema inicializado com sucesso!");
        
        while(cont != 3){
            System.out.println("Informe qual a visão do sistema desejada:\n1 - Distribuidora\n2 - Filial\n3 - Sair");
            cont = scanner.nextInt();
            switch(cont){
                case 1:
                    while(cont2 != 14){
                        System.out.println("\nInforme a opção desejada");
                        System.out.println("1 - Adicionar Filial");
                        System.out.println("2 - Remover Filial");
                        System.out.println("3 - Buscar dados de uma filial específica");
                        System.out.println("4 - Buscar dados de todas as filiais");
                        System.out.println("5 - Adicionar Serviço");
                        System.out.println("6 - Adicionar serviços no meio do expediente");
                        System.out.println("7 - Aumentar prioridade do serviço");
                        System.out.println("8 - Diminuir prioridade do serviço");
                        System.out.println("9 - Remover Serviço");
                        System.out.println("10 - Buscar dados de um serviço específico");
                        System.out.println("11 - Buscar dados de todos os serviços");
                        System.out.println("12 - Iniciar dia de expedientes");
                        System.out.println("13 - Finalizar dia de expediente");
                        System.out.println("14 - Voltar");
                        System.out.println("15 - Finalizar o programa");
                        
                        cont2 = scanner.nextInt();
                        
                        switch(cont2){
                            case 1:
                                System.out.println("Informe o nome da filial:");
                                nome = scanner.next();
                                System.out.println("Agora informe em minutos o tempo de expediente da filial:");
                                expediente = scanner.nextInt();
                                filial = new GerenciaServiçosFilial(gerenciador.getFilalLastId()+1, lista, nome, expediente, gerenciador);
                                gerenciador.adicionarFilial(filial);
                                break;
                                
                            case 2:
                                System.out.println("Informe o id da filial a ser removida:");
                                id = scanner.nextInt();
                                gerenciador.removerFilial(id);
                                break;
                                
                            case 3:
                                System.out.println("Informe o id da filial a procurada:");
                                id = scanner.nextInt();
                                gerenciador.imprimirDadosFilial(id);
                                break;
                                
                            case 4:
                                System.out.println("Estas são todas as filiais cadastradas atualmente:");
                                gerenciador.imprimirDadosTodasFiliais();
                                break;
                                
                            case 5:
                                System.out.println("Informe o nome do serviço:");
                                nome = scanner.next();
                                System.out.println("Agora informe em minutos o tempo estimado para finalização do serviço:");
                                tempoEstimado = scanner.nextInt();
                                while(prioridade != 0 && prioridade != 1){
                                    System.out.println("Informe a prioridade do serviço:\n0 - Normal\n1 - Alta: ");
                                    prioridade = scanner.nextInt();
                                    switch(prioridade){
                                        case 0:
                                        case 1:
                                            break;
                                            
                                        default :
                                            System.out.println("Prioridade inválida!");
                                    }
                                }
                                serviço = new Serviços(gerenciador.getLastId()+1, nome, tempoEstimado, prioridade);
                                gerenciador.adicionarServiço(serviço);
                                prioridade = -1;
                                break;
                                
                            case 6:
                                System.out.println("Informe o nome do serviço:");
                                nome = scanner.next();
                                System.out.println("Agora informe em minutos o tempo estimado para finalização do serviço:");
                                tempoEstimado = scanner.nextInt();
                                serviço = new Serviços(gerenciador.getLastId()+1, nome, tempoEstimado, 2);
                                gerenciador.adicionarServiço(serviço);
                                gerenciador.filialComMaiorTempoLivreEDisponivel().adicionarServiçoNoMeioExpediente(serviço);
                                break;

                            case 7:
                                System.out.println("Informe o id do serviço a ser priorizado");
                                id = scanner.nextInt();
                                gerenciador.aumentarPrioridade(id);
                                break;

                            case 8:
                                System.out.println("Informe o id do serviço a ser priorizado");
                                id = scanner.nextInt();
                                gerenciador.diminuirPrioridade(id);
                                break;

                            case 9:
                                System.out.println("Informe o id do serviço a ser removida:");
                                id = scanner.nextInt();
                                gerenciador.removerServiço(id);
                                break;
                                
                            case 10:
                                System.out.println("Informe o id do serviço procurado:");
                                id = scanner.nextInt();
                                gerenciador.imprimirDadosServiço(id);
                                break;
                                
                            case 11:
                                System.out.println("Estas são todos os serviços cadastradas atualmente:");
                                gerenciador.imprimirDadosTodosServiços();
                                break;
                                
                            case 12:
                                gerenciador.iniciarDia();
                                break;
                                
                            case 13:
                                gerenciador.finalizarDia();
                                break;
                                
                            case 14:
                                break;
                                
                            case 15:
                                cont2 = 14;
                                cont = 3;
                                System.out.println("Sistema finalizado, agradecemos a preferência!");
                                break;
                                
                            default:
                                System.out.println("Opção inválida!");
                                break;  
                        }
                    }
                    cont2 = 0;
                    break;
                    
                case 2:
                    System.out.println("Informe o id da filial desejada: ");
                    id = scanner.nextInt();
                    try{
                        filial = gerenciador.getById(id);
                        while(cont2 != 12){
                            System.out.println("\nInforme a opção desejada");
                            System.out.println("1 - Verificar qual é o Serviço atual");
                            System.out.println("2 - Permitir mais serviços até o final do expediente");
                            System.out.println("3 - Recusar mais serviços até o final do expediente");
                            System.out.println("4 - Finalizar Serviço com sucesso");
                            System.out.println("5 - Finalizar Serviço sem sucesso");
                            System.out.println("6 - Mover serviço para StandBy");
                            System.out.println("7 - Mover serviço do StandBy para a fila do dia");
                            System.out.println("8 - Listar serviços de StandBy");
                            System.out.println("9 - Listar informações desta filial");
                            System.out.println("10 - Finalizar dia de expediente");
                            System.out.println("11 - Alterar a filial escolhida");
                            System.out.println("12 - Voltar");
                            System.out.println("13 - Finalizar o programa");

                            cont2 = scanner.nextInt();

                            switch(cont2){
                                case 1:
                                    filial.proximoServiço();
                                    break;

                                case 2:
                                    filial.permitirMaisServiços();
                                    break;

                                case 3:
                                    filial.recusarMaisServiços();
                                    break;

                                case 4:
                                    System.out.println("Informe qual foi o tempo real gasto");
                                    tempoGasto = scanner.nextInt();
                                    filial.finalizaServiço(tempoGasto);
                                    break;

                                case 5:
                                    System.out.println("Informe qual foi o tempo real gasto");
                                    tempoGasto = scanner.nextInt();
                                    System.out.println("Agora informe o problema encontrado: ");
                                    mensagem = scanner.next();
                                    filial.finalizaServiçoSemSucesso(tempoGasto, mensagem);
                                    break;

                                case 6:
                                    int refazer = 3;
                                    int novoTempoGasto = 0;
                                    System.out.println("Informe o motivo:");
                                    mensagem = scanner.next();
                                    while(refazer != 1 && refazer != 2){
                                        System.out.println("O tempo estimado deste serviço precisa ser refeito?\n1 - Sim\n2 - Não");
                                        refazer = scanner.nextInt();
                                        switch(refazer){
                                            case 1:
                                                System.out.println("Insira o novo tempo estimado para este serviço:");
                                                novoTempoGasto = scanner.nextInt();
                                                break;
                                            case 2:
                                                break;

                                            default :
                                                System.out.println("Opção inválida!");
                                        }
                                    }
                                    System.out.println("Insira o tempo gasto para avaliar e mudança do serviço:");
                                    tempoGasto = scanner.nextInt();
                                    filial.moverParaOStandBy(mensagem, refazer, tempoGasto, novoTempoGasto);
                                    break;

                                case 7:
                                    System.out.println("Informe o ID do serviço que sairá do StandBy:");
                                    id = scanner.nextInt();
                                    filial.moverDoStandBy(id);
                                    break;

                                case 8:
                                    filial.imprimirListaStandBy();
                                    break;

                                case 9:
                                    filial.imprimirDadosFilial();
                                    break;

                                case 10:
                                    filial.finalzarDia();
                                    break;

                                case 11:
                                    cont2 = 12;
                                    cont = 2;
                                    break;

                                case 12:
                                    break;

                                case 13:
                                    cont2 = 12;
                                    cont = 3;
                                    System.out.println("Sistema finalizado, agradecemos a preferência!");
                                    break;

                                default:
                                    System.out.println("Opção inválida!");
                                    break;   
                            }
                        }
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("ID não encontrado");
                    }
                    cont2 = 0;
                    cont = 0;
                    break;
                    
                case 3:
                    System.out.println("Sistema finalizado, agradecemos a preferência!");
                    break;
                    
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
    
}