package aps_ed;

public class ListaDinamicaDeFiliais implements TADListaFiliais {
    private NodeFiliais inicio, fim;
    private int quantidade;

    public ListaDinamicaDeFiliais() {
        quantidade = 0;
        inicio = fim = null;
    }
    
    @Override
    public boolean isEmpty() {
        return quantidade == 0;
    }

    @Override
    public int size() {
        return quantidade;
    }

    @Override
    public void add(GerenciaServiçosFilial elemento, int indice) throws IndexOutOfBoundsException {
        if (indice >= 0 && indice <= quantidade) {
            NodeFiliais novo = new NodeFiliais(elemento);
            if (indice == 0) {
                novo.proximo = inicio;
                inicio = novo;
                if (quantidade == 0) {
                    fim = novo;
                }
            } else if (indice == quantidade) {
                fim.proximo = novo;
                fim = novo;
            } else {
                NodeFiliais aux = inicio;
                for (int i = 0; i < indice - 1; i++) {
                    aux = aux.proximo;
                }
                novo.proximo = aux.proximo;
                aux.proximo = novo;
            }
            quantidade++;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public GerenciaServiçosFilial remove(int indice) throws IndexOutOfBoundsException {
        if (indice >= 0 && indice < quantidade) {
            NodeFiliais retorno;
            if (indice == 0) {
                retorno = inicio;
                inicio = inicio.proximo;
                retorno.proximo = null;
                if (inicio == null) {
                    fim = null;
                }
            } else {
                NodeFiliais aux = inicio;
                for (int i = 0; i < indice - 1; i++) {
                    aux = aux.proximo;
                }
                retorno = aux.proximo;
                aux.proximo = retorno.proximo; //aux.proximo = aux.proximo.proximo;
                retorno.proximo = null;
                if (aux.proximo == null) {
                    fim = aux;
                }
            }
            quantidade--;
            return retorno.elemento;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public void set(GerenciaServiçosFilial elemento, int indice) throws IndexOutOfBoundsException {
        if (indice >= 0 && indice < quantidade) {
            NodeFiliais aux = inicio;
            for (int i = 0; i < indice; i++) {
                aux = aux.proximo;
            }
            aux.elemento = elemento;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public GerenciaServiçosFilial get(int indice) throws IndexOutOfBoundsException {
        if (indice >= 0 && indice < quantidade) {
            NodeFiliais aux = inicio;
            for (int i = 0; i < indice; i++) {
                aux = aux.proximo;
            }
            return aux.elemento;
        }
        throw new IndexOutOfBoundsException();
    }
    
    public GerenciaServiçosFilial getById(int id) throws IndexOutOfBoundsException {
            NodeFiliais aux = inicio;
            for (int i = 0; i < size(); i++) {
                if(aux.elemento.getId() == id){
                    return aux.elemento;
                }else{
                    aux = aux.proximo;
                }
            }
            
        throw new IndexOutOfBoundsException();
    }
    
    public int getIndiceById(int id) throws IndexOutOfBoundsException {
            NodeFiliais aux = inicio;
            for (int i = 0; i < size(); i++) {
                if(aux.elemento.getId() == id){
                    return i;
                }else{
                    aux = aux.proximo;
                }
            }
            
        throw new IndexOutOfBoundsException();
    }
    
    public int getLastId(){
        return fim.elemento.getId();
    }
}