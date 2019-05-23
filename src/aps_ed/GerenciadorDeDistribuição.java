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
public class GerenciadorDeDistribuição {
    
    private ListaDinamica lista;
    private ListaDinamica listaPrioridadeRegular;
    private ListaDinamica listaPrioridadeAlta;
    private ListaDinamica listaPrioridadeMuitoAlta;
    private ListaDinamicaDeFiliais listaFilial = new ListaDinamicaDeFiliais();
    private GerenciaServiçosFilial filial;
    private boolean finalizaDia = false;
    private boolean iniciarDia = true;
    
    public GerenciadorDeDistribuição(ListaDinamica lista){
        this.lista = lista;
    }
    
    public void adicionarFilial(GerenciaServiçosFilial filial){
        listaFilial.add(filial, listaFilial.size());
        System.out.println("Filial adicionada com sucesso!");
    }
    
    public void removerFilial(int id){
        if(listaFilial.isEmpty()){
            System.out.println("Não possuem filiais para serem removidas");
        }else{
            try{
                listaFilial.remove(listaFilial.getIndiceById(id));
                System.out.println("Filial removida com sucesso!");
            }catch(IndexOutOfBoundsException e){
                System.out.println("ID não encontrado");
            }
        }
    }
    
    public void adicionarServiço(Serviços serviço){
        lista.add(serviço, lista.size());
        System.out.println("Serviço adicionado à lista com sucesso!");
    }

    public void aumentarPrioridade(int id){
        if(lista.isEmpty()){
            System.out.println("Não possuem serviços para serem priorizados");
        }else {
            try{
                lista.getById(id).aumentarPrioridade();
                System.out.println("Serviço priorizazdo sucesso!");
            }catch(IndexOutOfBoundsException e){
                System.out.println("ID não encontrado");
            }
        }
    }

    public void diminuirPrioridade(int id){
        if(lista.isEmpty()){
            System.out.println("Não possuem serviços para serem despriorizados");
        }else {
            try{
                lista.getById(id).diminuirPrioridade();
                System.out.println("Serviço despriorizazdo sucesso!");
            }catch(IndexOutOfBoundsException e){
                System.out.println("ID não encontrado");
            }
        }
    }
    
    public void removerServiço(int id){
        if(lista.isEmpty()){
            System.out.println("Não possuem serviços para serem removidos");
        }else {
            try {
                lista.remove(lista.getIndiceById(id));
                System.out.println("Serviço excluído com sucesso!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("ID não encontrado");
            }
        }
    }
    
    public void iniciarDia(){
        if(finalizaDia = false || iniciarDia == false){
            System.out.println("Não foi possivel iniciar o dia, o dia anterior não foi finalizado ainda");
        }else{
            if(listaFilial.isEmpty() || lista.isEmpty()){
                System.out.println("Não foi possivel iniciar o dia, não possuem filiais e(ou) serviços cadastrados");
            }else{
                for(int i = 0; i<listaFilial.size(); i++){
                    listaFilial.get(i).iniciarDia();
                }
                separarPorPrioridade();
                distribuirServiços();
                System.out.println("Serviços do dia separados com sucesso!");
                iniciarDia = false;
                
                for(int i = 0; i<listaFilial.size(); i++){
                    listaFilial.get(i).setDiaIniciado(true);
                }  
            }
        }
    }
    
    public void finalizarDia(){
        finalizaDia = true;
        for(int i = 0; i<listaFilial.size(); i++){
            if(listaFilial.get(i).isDiaFinalizado() == false || listaFilial.get(i).isDiaIniciado() == false){
                finalizaDia = false;
                filial = listaFilial.get(i);
                break;
            }
        }
        
        
        if((finalizaDia == true) && (iniciarDia == false)){
            int id;
            if(!listaPrioridadeRegular.isEmpty()){
                for(int i = 0; i<listaPrioridadeRegular.size(); i++){
                    listaPrioridadeRegular.get(i).aumentarDiasDeEspera();
                    id = listaPrioridadeRegular.get(i).getId();
                    lista.getById(id).aumentarDiasDeEspera();
                    if(lista.getById(id).getDiasEspera() >=5){
                        lista.getById(id).aumentarPrioridade();
                    }
                }
            }
            
            if(!listaPrioridadeAlta.isEmpty()){
                for(int i = 0; i<listaPrioridadeAlta.size(); i++){
                    listaPrioridadeAlta.get(i).aumentarDiasDeEspera();
                    id = listaPrioridadeAlta.get(i).getId();
                    lista.getById(id).aumentarDiasDeEspera();
                    if(lista.getById(id).getDiasEspera() >=10){
                        lista.getById(id).aumentarPrioridade();
                    }
                }
            }
            
            if(!listaPrioridadeMuitoAlta.isEmpty()){
                for(int i = 0; i<listaPrioridadeMuitoAlta.size(); i++){
                    listaPrioridadeMuitoAlta.get(i).aumentarDiasDeEspera();
                    id = listaPrioridadeMuitoAlta.get(i).getId();
                    lista.getById(id).aumentarDiasDeEspera();
                }
            }
            System.out.println("Dia Finalizado com Sucesso!");
            finalizaDia = true;
            iniciarDia = true;
            for(int i = 0; i<listaFilial.size(); i++){
                listaFilial.get(i).setDiaFinalizado(true);
                listaFilial.get(i).setDiaIniciado(false);
            }  
        }else{
            System.out.println("Não foi possível finalizar o dia, verifique se todas as filiais finalizaram o seu expediente!");
        }
    }
    
    public void imprimirDadosFilial(int id){
        if(listaFilial.isEmpty()){
            System.out.println("Não existem filiais cadastradas no momento!");
        }else{
            try{
                listaFilial.get(listaFilial.getIndiceById(id)).imprimirDadosFilial();
                
            }catch(IndexOutOfBoundsException e){
                System.out.println("Filial não encontrada");
            }
        }
    }
    
    public void imprimirDadosTodasFiliais(){
        if(listaFilial.isEmpty()){
            System.out.println("Não existem filiais cadastradas no momento!");
        }else{
            for(int i = 0; i<listaFilial.size(); i++){
                listaFilial.get(i).imprimirDadosFilial();
            }
        }
    }
    
    public void imprimirDadosServiço(int id){
        if(listaFilial.isEmpty()){
            System.out.println("Não existem Serviços cadastrados no momento!");
        }else{
            try{
                lista.get(lista.getIndiceById(id)).imprimirDadosServico();
            }catch(IndexOutOfBoundsException e){
                System.out.println("Serviço não encontrado");
            }
        }
    }
    
    public void imprimirDadosTodosServiços(){
        if(listaFilial.isEmpty()){
            System.out.println("Não existem Serviços cadastrados no momento!");
        }else{
            for(int i = 0; i<lista.size(); i++){
                lista.get(i).imprimirDadosServico();
            }
        }
    }
    
    public void separarPorPrioridade(){
        Serviços aux;
        listaPrioridadeRegular = new ListaDinamica();
        listaPrioridadeAlta = new ListaDinamica();
        listaPrioridadeMuitoAlta = new ListaDinamica();
        for(int i = 0; i<lista.size(); i++){
            if(!(lista.get(i).getStatus().equals("Finalizado com sucesso!") 
                    || lista.get(i).getStatus().contains("Finalizado com falha segue motivo a seguir:\n"))){
                aux = lista.get(i);
                switch(aux.getPrioridade()){
                    case 0:
                        listaPrioridadeRegular.add(aux, listaPrioridadeRegular.size());
                        break;

                    case 1:
                        listaPrioridadeAlta.add(aux, listaPrioridadeAlta.size());
                        break;

                    case 2:
                        listaPrioridadeMuitoAlta.add(aux, listaPrioridadeMuitoAlta.size());
                        break;
                }
            }
        }
    }
    
    public GerenciaServiçosFilial filialComMaiorTempoLivre(){
        for(int i = 0; i<listaFilial.size(); i++){
            if(i == 0){
                filial = listaFilial.get(i);
            }
            if(filial.getTempoLivreParaNovosServiços()<listaFilial.get(i).getTempoLivreParaNovosServiços()){
                filial = listaFilial.get(i);
            }
        }
        return filial;
    }
    
    public GerenciaServiçosFilial filialComMaiorTempoLivreEDisponivel(){
        for(int i = 0; i<listaFilial.size(); i++){
            if(i == 0){
                filial = listaFilial.get(i);
            }
            if((filial.getTempoLivreParaNovosServiços()<listaFilial.get(i).getTempoLivreParaNovosServiços()) && listaFilial.get(i).isLivreParaMaisServiços() == true){
                filial = listaFilial.get(i);
            }
        }
        return filial;
    }
    
    public void distribuirServiços(){
        boolean finaliza = false;
        if(!listaPrioridadeMuitoAlta.isEmpty()){
            while(finaliza == false){
                for(int i = 0; i<listaPrioridadeMuitoAlta.size(); i++){
                    filial = filialComMaiorTempoLivre();
                    if(filial.getTempoLivreParaNovosServiços()>=listaPrioridadeMuitoAlta.get(i).getTempoEstimado()){
                            filial.adicionarServiço(listaPrioridadeMuitoAlta.remove(i));
                            finaliza = false;
                            break;
                    }
                    if(i == listaPrioridadeMuitoAlta.size()-1){
                        finaliza = true;
                    }
                }
                if(listaPrioridadeAlta.size() == 0){
                    finaliza = true;
                }
            }
        }
        finaliza = false;
        if(!listaPrioridadeAlta.isEmpty()){
            while(finaliza == false){
                for(int i = 0; i<listaPrioridadeAlta.size(); i++){
                    filial = filialComMaiorTempoLivre();
                    if(filial.getTempoLivreParaNovosServiços()>=listaPrioridadeAlta.get(i).getTempoEstimado()){
                            filial.adicionarServiço(listaPrioridadeAlta.remove(i));
                            finaliza = false;
                            break;
                    }
                    if(i == listaPrioridadeMuitoAlta.size()-1){
                        finaliza = true;
                    }
                }
                if(listaPrioridadeAlta.size() == 0){
                    finaliza = true;
                }
            }
        }
        
        finaliza = false;
        if(!listaPrioridadeRegular.isEmpty()){
            while(finaliza == false){
                for(int i = 0; i<listaPrioridadeRegular.size(); i++){
                    filial = filialComMaiorTempoLivre();
                    if(filial.getTempoLivreParaNovosServiços()>=listaPrioridadeRegular.get(i).getTempoEstimado()){
                            filial.adicionarServiço(listaPrioridadeRegular.remove(i));
                             finaliza = false;
                            break;
                    }
                    if(i == listaPrioridadeMuitoAlta.size()-1){
                        finaliza = true;
                    }       
                }
                if(listaPrioridadeAlta.size() == 0){
                    finaliza = true;
                }
            }
        }
    }

    public boolean isDiaIniciado() { return iniciarDia; }
    
    public GerenciaServiçosFilial getById(int id){
        return listaFilial.getById(id);
    }
    
    public int getLastId(){
        if(lista.isEmpty()){
            return -1;
        }else{
            return lista.getLastId();
        }
    }
    
    public int getFilalLastId(){
        if(listaFilial.isEmpty()){
            return -1;
        }else{
            return listaFilial.getLastId();
        }
    }
}
