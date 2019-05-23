package aps_ed;

public interface TADListaFiliais {
    public boolean isEmpty();
    //public boolean isFull();
    public int size();
    public void add(GerenciaServiçosFilial elemento, int indice) throws IndexOutOfBoundsException;
    public GerenciaServiçosFilial remove(int indice) throws IndexOutOfBoundsException;
    public void set(GerenciaServiçosFilial elemento, int indice) throws IndexOutOfBoundsException;
    public GerenciaServiçosFilial get(int indice) throws IndexOutOfBoundsException;
}