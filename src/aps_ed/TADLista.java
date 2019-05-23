package aps_ed;

public interface TADLista {
    public boolean isEmpty();
    //public boolean isFull();
    public int size();
    public void add(Serviços elemento, int indice) throws IndexOutOfBoundsException;
    public Serviços remove(int indice) throws IndexOutOfBoundsException;
    public void set(Serviços elemento, int indice) throws IndexOutOfBoundsException;
    public Serviços get(int indice) throws IndexOutOfBoundsException;
}