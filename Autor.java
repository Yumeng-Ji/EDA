import java.util.*;
import java.io.*;

public class Autor {
    private String id;
    private String nombre;
    private HashSet<Publicacion> listaPublicaciones;

    public Autor(String pId, String pNombre) {
        this.id = pId;
        this.nombre = pNombre;
        this.listaPublicaciones = new HashSet<Publicacion>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public HashSet<Publicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void addPublicacion(Publicacion publicacion) {
        listaPublicaciones.add(publicacion);
    }

}
