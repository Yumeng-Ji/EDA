import java.util.*;
import java.io.*;

public class Publicacion {

    private String id;
    private String titulo;
    private HashSet<Autor> listaAutores;
    private HashSet<Publicacion> listaCitadas;

    public Publicacion(String pId, String pTitulo) {
        this.id = pId;
        this.titulo = pTitulo;
        this.listaAutores = new HashSet<Autor>();
        this.listaCitadas = new HashSet<Publicacion>();


    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public HashSet<Autor> getListaAutores() {
        return listaAutores;
    }

    public HashSet<Publicacion> getListaCitadas() {
        return listaCitadas;
    }

    public void addAutor(Autor autor) {
        listaAutores.add(autor);
    }

    public void addCitada(Publicacion publicacion) {
        listaCitadas.add(publicacion);
    }
}