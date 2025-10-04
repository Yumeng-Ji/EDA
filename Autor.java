import java.util.*;
import java.io.*;

public class Autor {
    private String id;
    private String nombre;
    private HashSet<String> listaIdPublicaciones;

    public Autor(String pId, String pNombre) {
        this.id = pId;
        this.nombre = pNombre;
        this.listaIdPublicaciones = new HashSet<String>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public HashSet<String> getListaIdPublicaciones() {
        return listaIdPublicaciones;
    }

    public void añadirPublicacion(String pId) {
        this.listaIdPublicaciones.add(pId);
    }
    public void eliminarPublicacion(String pId){
        this.listaIdPublicaciones.remove(pId);
    }

}

