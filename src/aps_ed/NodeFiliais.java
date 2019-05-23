package aps_ed;

public class NodeFiliais {
    GerenciaServiçosFilial elemento;
    NodeFiliais proximo;

    public NodeFiliais(GerenciaServiçosFilial elemento) {
        this.elemento = elemento;
        proximo = null;
    }
}
