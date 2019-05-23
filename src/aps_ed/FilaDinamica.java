package aps_ed;


import java.util.NoSuchElementException;

public class FilaDinamica implements TADFila {

    private Node inicio, fim;
    private int quantidade;

    public FilaDinamica() {
        inicio = fim = null;
        quantidade = 0;
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
    public void enqueue(Serviços elemento) {
        Node novo = new Node(elemento);
        if (!isEmpty()) {
            fim.proximo = novo;
        } else {
            inicio = novo;
        }
        fim = novo;
        quantidade++;
    }

    @Override
    public Serviços dequeue() throws NoSuchElementException {
        if (!isEmpty()) {
            Node aux = inicio;
            inicio = inicio.proximo;
            aux.proximo = null;
            quantidade--;
            if (quantidade == 0) {
                fim = null;
            }
            return aux.elemento;
        }
        throw new NoSuchElementException();
    }

    @Override
    public Serviços front() throws NoSuchElementException {
        if (!isEmpty()) {
            return inicio.elemento;
        }
        throw new NoSuchElementException();
    }
}
