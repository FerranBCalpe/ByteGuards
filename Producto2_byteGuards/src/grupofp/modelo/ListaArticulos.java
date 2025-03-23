package grupofp.modelo;

import java.util.ArrayList;

public  class ListaArticulos extends Lista<Articulo> {
    private Articulo articulo;

    public ListaArticulos(ArrayList lista){
        super(lista);
    }
    public ListaArticulos(){

    }

    public ListaArticulos(Articulo articulo) {
        this.articulo=articulo;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }


}
