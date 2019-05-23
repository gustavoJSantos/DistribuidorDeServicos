/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps_ed;

/**
 *
 * @author Gustavo de Jesus Santos - RA 21000084
 * 
 */
public class GerenciaServiçosFilial {
    private String nomeFilial;
    private int expediente, expedienteInicial, id, tempoLivreParaNovosServiços;
    private boolean livreParaMaisServiços = true, diaFinalizado = true, diaIniciado = false;
    private ListaDinamica lista;
    private FilaDinamica filaDeServiços = new FilaDinamica();
    private ListaEstatica standBy = new ListaEstatica(8);
    private Serviços serviço;

    public GerenciaServiçosFilial(int id, ListaDinamica lista, String nomeFilial, int expediente, GerenciadorDeDistribuição distribuidor){
        if(distribuidor.isDiaIniciado()) {
            this.id = id;
            this.expediente = this.tempoLivreParaNovosServiços = this.expedienteInicial = expediente;
            this.nomeFilial = nomeFilial;
            this.lista = lista;
            diaFinalizado = true;
            diaIniciado = false;
        }else{
            this.id = id;
            this.expediente = this.tempoLivreParaNovosServiços = this.expedienteInicial = expediente;
            this.nomeFilial = nomeFilial;
            this.lista = lista;
            diaFinalizado = false;
            diaIniciado = true;
        }
    }
    
    public void proximoServiço(){
        if(filaDeServiços.isEmpty()){
            System.out.println("Não há um próximo serviço no momento!");
        }else{
            serviço = filaDeServiços.front();
            if(serviço.getStatus().equals("cancelado")){
                System.out.println("Este serviço foi cancelado pela central!");
                filaDeServiços.dequeue();
            }else{
                serviço.imprimirDadosServico();
            }
        }
    }
    
    public void finalzarDia(){
        if(filaDeServiços.isEmpty() && diaFinalizado == false){
            expediente = 0;
            tempoLivreParaNovosServiços = 0;
            diaFinalizado = true;
            System.out.println("Expediente encerrado com sucesso!");
        }else{
            System.out.println("Não foi possível encerrar o expediente, ainda há serviço para fazer ou o dia ainda não foi inicializado!");
        }
    }
    
    public void permitirMaisServiços(){
        if(livreParaMaisServiços == true){
            System.out.println("Esta filial já está permitindo mais serviços");
        }else{
            livreParaMaisServiços = true;
            System.out.println("A partir de agora a filial poderá receber novos serviços!");
        }
    }
    
    public void recusarMaisServiços(){
        if(livreParaMaisServiços == false){
            System.out.println("Esta filial já está recusando os serviços");
        }else{
            livreParaMaisServiços = false;
            System.out.println("A partir de agora a filial recusará qualquer novo serviço!");
        }
    }
    
    public void finalizaServiço(int tempoRealGasto){
        if(filaDeServiços.isEmpty()){
            System.out.println("Não há nenhum serviço para finalizar!");
        }else{
            int idServiço = filaDeServiços.front().getId();
            serviço = lista.get(lista.getIndiceById(idServiço));
            serviço.setStatus("Finalizado com sucesso!");
            expediente -= tempoRealGasto;
            tempoLivreParaNovosServiços -= serviço.getTempoEstimado();
            tempoLivreParaNovosServiços += tempoRealGasto;
            filaDeServiços.dequeue();
            System.out.println("Ação realizada com sucesso!");
        }
    }
    
    public void finalizaServiçoSemSucesso(int tempoRealGasto, String mensagemErro){
        if(filaDeServiços.isEmpty()){
            System.out.println("Não há nenhum serviço para finalizar!");
        }else{
            int idServiço = filaDeServiços.front().getId();
            serviço = lista.get(lista.getIndiceById(idServiço));
            serviço.setStatus("Finalizado com falha segue motivo a seguir:\n"+mensagemErro);
            expediente -= tempoRealGasto;
            tempoLivreParaNovosServiços -= serviço.getTempoEstimado();
            tempoLivreParaNovosServiços += tempoRealGasto;
            filaDeServiços.dequeue();
            System.out.println("Ação realizada com sucesso!");
        }
    }
    
    public void moverParaOStandBy(String motivo, int refazTempo, int tempoGasto, int novoTempo){
        if(filaDeServiços.isEmpty()){
            System.out.println("Não há nenhum serviço em StandBy!");
        }else if(standBy.isFull()){
            System.out.println("A lista de StandBy está cheia, o pedido deverá ser recusado para ser criado novamente!");
        }else{
            int idServiço = filaDeServiços.front().getId();
            serviço = lista.get(lista.getIndiceById(idServiço));
            serviço.setStatus(motivo);
            serviço.setStandBy(true);
            tempoLivreParaNovosServiços += serviço.getTempoEstimado();
            if(refazTempo == 1){
                serviço.setTempoEstimado(novoTempo);
            }else{
                serviço.setTempoEstimado(serviço.getTempoEstimado()-tempoGasto);
            }
            standBy.add(serviço, standBy.size());
            expediente -= tempoGasto;
            tempoLivreParaNovosServiços -= tempoGasto;
            filaDeServiços.dequeue();
            System.out.println("Ação realizada com sucesso!");
        }
    }
    
    public void moverDoStandBy(int id){
        if(standBy.isEmpty()){
            System.out.println("Não há nenhum serviço em StandBy!");
        }else{
            int cont = 0;
            if(standBy.isEmpty()){
                System.out.println("Não existe nenhum serviço em standBy");
            }else{
                for(int i = 0; i<standBy.size(); i++){
                    if(standBy.get(i).getId() == id){
                        cont++;
                        serviço = standBy.get(i);
                        if(serviço.getTempoEstimado()<tempoLivreParaNovosServiços){
                            adicionarServiço(serviço);
                            standBy.remove(i);
                            System.out.println("Adicionado com sucesso!");
                        }else{
                            System.out.println("Não foi possível adicionar este serviço a fila, o tempo estimado ultrapassa o tempo de expediente, ele será adicionado no dia seguinte!");
                        }
                        serviço.setStandBy(false);
                        //lista.set(serviço, id);
                        break;
                    }
                }
                if(cont == 0){
                    System.out.println("Não existe nenhum serviço com este Id!");
                }
            }
        }
    }
    
    public void imprimirListaStandBy(){
        if(standBy.isEmpty()){
            System.out.println("Não existe nenhum serviço em StandBy!");
        }else{
            for(int i = 0; i<standBy.size(); i++){
                standBy.get(i).imprimirDadosServico();
            }
        }
    }
    
    public void imprimirDadosFilial(){
        System.out.println("===========================");
        System.out.println("ID da filial: "+id);
        System.out.println("Nome da Filial: "+nomeFilial);
        System.out.println("Seu expediente é de "+(expedienteInicial/60)+" Horas");
        System.out.println("No momento ainda faltam aproximadamente "+(float)(expediente/60)+" horas para seu expediente acabar");
        System.out.println("Ainda faltam "+filaDeServiços.size()+" serviços a serem finalizados");
        System.out.println("Existem atualmente "+standBy.size()+" serviços aguardando alguma resposta para serem continuados!");
        if(livreParaMaisServiços == true){
            System.out.println("Esta filial está apta a receber novos serviços!");
            System.out.println("E ainda possui "+(float)tempoLivreParaNovosServiços/60+" horas livres em seu expediente para receber serviços");
        }else{
            System.out.println("Esta filial não deseja receber novos serviços!");
        }
        System.out.println("");
    }
    
    public void adicionarServiçoNoMeioExpediente(Serviços serviço){
        if(serviço.getTempoEstimado()+60>tempoLivreParaNovosServiços){
            System.out.println("Não é possível adicionar este serviço, ele ultrapassa o tempo de expediente da filial com o maior tempo disponível para novos serviços!");
        }else if(livreParaMaisServiços == false){
            System.out.println("Esta filial não permite que mais serviços sejam adicionados hoje!");
        }else if(diaFinalizado == true && diaIniciado == false){
            System.out.println("Esta filial já encerrou seu expediente!");
        }else{
            filaDeServiços.enqueue(serviço);
            if(expediente > serviço.getTempoEstimado()+60){
                tempoLivreParaNovosServiços -= serviço.getTempoEstimado();
            }else{
                tempoLivreParaNovosServiços -= serviço.getTempoEstimado()+60;
            }
            System.out.println("Serviço adicionado com sucesso!");
            System.out.println(filaDeServiços.size());
        }
    }

    public boolean isLivreParaMaisServiços() {
        return livreParaMaisServiços;
    }
    
    public void iniciarDia(){
        if(diaFinalizado = true){
            expediente = tempoLivreParaNovosServiços = 480;
            if(!standBy.isEmpty()){
                for(int i = 0; i<standBy.size(); i++){
                    if(standBy.get(i).isStandBy() == false){
                        tempoLivreParaNovosServiços -= standBy.get(i).getTempoEstimado();
                        filaDeServiços.enqueue(standBy.remove(i));
                    }
                }
            }
            diaFinalizado = false;
            System.out.println("Expediente Da filial iniciada com sucesso!");
        }else{
            System.out.println("Não foi possível iniciar o dia da filial "+nomeFilial+" pois ela ainda não finalizou o dia");
        }
    }
    
    public int getId(){
        return id;
    }

    public boolean isDiaIniciado() {
        return diaIniciado;
    }

    public void setDiaIniciado(boolean diaIniciado) {
        this.diaIniciado = diaIniciado;
    }
    
    public boolean isDiaFinalizado() {
        return diaFinalizado;
    }

    public void setDiaFinalizado(boolean diaFinalizado) {
        this.diaFinalizado = diaFinalizado;
    }

    public int getTempoLivreParaNovosServiços() {
        return tempoLivreParaNovosServiços;
    }
    
    public void adicionarServiço(Serviços serviço){
        filaDeServiços.enqueue(serviço);
        tempoLivreParaNovosServiços -= serviço.getTempoEstimado();
    }
    
    
}
