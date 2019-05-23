/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps_ed;

/**
 *
 * @author Gustavo Santos RA: 21000084
 */
public class Serviços {
    
    private int tempoEstimado, prioridade, diasEspera, id;
    private String nomeServico, status;
    private boolean standBy;
    
    public Serviços(int id, String nomeServico, int tempoEstimado, int prioridade){
        this.id = id;
        this.nomeServico = nomeServico;
        this.tempoEstimado = tempoEstimado;
        this.prioridade = prioridade;
        diasEspera = 0;
        standBy = false;
        status = "Serviço criado";
    }
    
    public int getDiasEspera() {
        return diasEspera;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public boolean isStandBy() {
        return standBy;
    }

    public void setStandBy(boolean standBy) {
        this.standBy = standBy;
    }
    
    public int getId(){
        return id;
    }
    
    public void setTempoEstimado(int tempoEstimado){
        this.tempoEstimado = tempoEstimado;
    }
    
    public int getTempoEstimado(){
        return tempoEstimado;
    }
    
    public void aumentarDiasDeEspera(){
        diasEspera++;
    }
    
    public void aumentarPrioridade(){
        if(prioridade == 2){
            System.out.println("O serviço é de maior prioridade possível");
        }else{
            prioridade++;
        }
    }
    
    public void diminuirPrioridade(){
        if(prioridade == 0){
            System.out.println("O serviço já está na menor prioridade possível");
        }else{
            prioridade--;
        }
    }
    
    public String prioridade(int prioridade){
        switch (prioridade) {
            case 0:
                return "Prioridade regular";
            case 1:
                return "Prioridade alta";
            default:
                return "Prioridade muito alta";
        }
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return status;
    }
    
    public void imprimirDadosServico(){
        System.out.println("===========================");
        System.out.println("Ordem de serviço: "+id);
        System.out.println("Especificação do serviço: "+nomeServico);
        System.out.println(prioridade(prioridade));
        System.out.println("Status: "+status);
        System.out.println("");
    }
    
}
