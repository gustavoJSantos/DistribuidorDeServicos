package aps_ed;


import java.util.NoSuchElementException;

public interface TADFila {
    public boolean isEmpty();
    //public boolean isFull();
    public int size();
    public void enqueue(Serviços elemento);
    public Serviços dequeue() throws NoSuchElementException;
    public Serviços front() throws NoSuchElementException;
}
