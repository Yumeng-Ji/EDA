import java.util.*;
import java.io.*;

public class Publicacion {

    private String id;
	private String titulo;
	private HashSet<String> listaIdAutores;
	private HashSet<String> listaIdCitadas;
	
	public Publicacion(String pId,String pTitulo) {
		this.id=pId;
		this.titulo=pTitulo;
		this.listaIdAutores= new HashSet<String>();
		this.listaIdCitadas=new HashSet<String>();
		
     
	}
    public String getId() {
        return this.id;
    }
    public String getTitulo() {
        return this.titulo;
    }
    public HashSet<String> getIdAutores() {return this.listaIdAutores;}
    public HashSet<String> getIdCitas() {
        return this.listaIdCitadas;
    }

    public void añadirAutor(String pAutorId) {
        listaIdAutores.add(pAutorId);
    }

    public void añadirCita(String pCitaId) {
        listaIdCitadas.add(pCitaId);
    }

    public void eliminarAutor(String pAutorId) {
        listaIdAutores.remove(pAutorId);
    }

    public void eliminarCita(String pCitaId) {
        listaIdCitadas.remove(pCitaId);
    }
}


