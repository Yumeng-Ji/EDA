import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class Repositorio {
    private static Repositorio miRepositorio = null;
    private HashMap<String, Autor> listaAutores;
    private HashMap<String, Publicacion> listaPublicaciones;

    private Repositorio() {
        this.listaAutores = new HashMap<String, Autor>();
        this.listaPublicaciones = new HashMap<String, Publicacion>();
    }

    public static Repositorio getRepositorio() {
        if (miRepositorio == null) {
            miRepositorio = new Repositorio();
        }
        return miRepositorio;
    }

    public HashMap<String, Autor> getListaAutores() {
        return listaAutores;
    }

    public HashMap<String, Publicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void cargarAutor(String pFichero) throws IOException, FileNotFoundException {
        Scanner sc = new Scanner(new FileReader(pFichero));
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] lineSplited = line.split("\\s+#\\s+");
            añadirAutor(lineSplited[0], lineSplited[1]);
            System.out.println(lineSplited[0] + " " + lineSplited[1]);
        }
        sc.close();
    }

    public void añadirAutor(String pId, String pNombre) {
        if (!listaAutores.containsKey(pId)) {
            Autor autor = new Autor(pId, pNombre);
            listaAutores.put(pId, autor);
        }
    }

    public int tamañoHash() {
        return this.listaAutores.size();
    }

    public ArrayList<String> cargarPublicacion(String pFichero) throws IOException, FileNotFoundException {
        ArrayList<String> titulos = new ArrayList<>();
        Scanner sc = new Scanner(new FileReader(pFichero));
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] lineSplited = line.split("\\s+#\\s+");
            añadirPublicacion(lineSplited[0], lineSplited[1]);
            titulos.add(lineSplited[1]);
        }
        sc.close();
        return titulos;
    }

    public void añadirPublicacion(String pId, String pTitulo) {
        if (!listaPublicaciones.containsKey(pId)) {
            Publicacion publicacion = new Publicacion(pId, pTitulo);
            listaPublicaciones.put(pId, publicacion);
        }
    }

    public int tamañoHashPublicacion() {
        return this.listaPublicaciones.size();
    }

    private String[] nuevaLista(ArrayList<String> pLista) {
        // Devolver array con los títulos
        return pLista.toArray(new String[0]);
        // coge todas las claves del HashMap listaPublicaciones (IDs de publicaciones),
        // las convierte en un array de String y lo devuelve.
    }

    public String[] publicacionesOrdenadas(ArrayList<String> pLista) {
        //Obtener la lista de publicaciones ordenada alfabéticamente
        String[] lista = this.nuevaLista(pLista); // crea un array con los IDs/títulos de publicaciones
        quickSort(lista, 0, lista.length - 1);
        return lista;
    }

    private void quickSort(String[] lista, int inicio, int fin) {
        if (inicio < fin) { // hay más de un elemento en la lista
            int indiceParticion = particion(lista, inicio, fin);
            // llama al metodo particion() para reorganizar los elementos
            // alrededor de un pivote y devuelve la posición del pivote
            quickSort(lista, inicio, indiceParticion - 1);
            // ordena recursivamente la parte izquierda (menores que el pivote)
            quickSort(lista, indiceParticion + 1, fin);
            // ordena recursivamente la parte derecha (mayores que el pivote)
        }
    }

    private int particion(String[] lista, int inicio, int fin) {
        String pivote = lista[inicio]; // se elige el primer elemento como pivote
        // inicializamos dos punteros, uno a la izquierda y otro a la derecha
        int izq = inicio;
        int der = fin;

        while (izq < der) {
            while (lista[izq].compareTo(pivote) <= 0 && izq < der) {
                izq++;
            }
            while (lista[der].compareTo(pivote) > 0) {
                der--;
            }
            if (izq < der) {
                swap(lista, izq, der);
            }
        }
        // si aún no se cruzaron, intercambia los valores
        lista[inicio] = lista[der];
        lista[der] = pivote;
        return der; // devuelve la posición final del pivote
    }

    private void swap(String[] lista, int izq, int der) {
        // intercambia dos elementos del array usando una variable temporal
        String temp = lista[izq];
        lista[izq] = lista[der];
        lista[der] = temp;
    }

    /*public void imprimirListaOrdenada(){
        String[] ordenada = this.publicacionesOrdenadas();
        for(String titulo: ordenada){
            System.out.println(titulo);
        }
    }*/

    private Publicacion buscarPublicacionPorId(String pId) {
        if (listaPublicaciones.containsKey(pId)) {
            return listaPublicaciones.get(pId);
        }
        return null;
    }

    public void cargarPublicacionesAutores(String pFichero) throws IOException, FileNotFoundException {
        Scanner sc = new Scanner(new FileReader(pFichero));
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] lineSplited = line.split("\\s+#\\s+");
            String idPub = lineSplited[0];
            String idAutor = lineSplited[1];

            añadirAutorAPublicacion(idAutor, idPub);
        }
        sc.close();
    }

    public void añadirAutorAPublicacion(String idAutor, String idPub){
        Autor autor = listaAutores.get(idAutor);
        if (autor == null) {
            autor = new Autor(idAutor, "");
            listaAutores.put(idAutor, autor);
        }
        Publicacion pub = buscarPublicacionPorId(idPub);
        if (pub == null) {
            pub = new Publicacion(idPub, "");
            listaPublicaciones.put(idPub, pub);
        }
        pub.addAutor(autor);
        autor.addPublicacion(pub);
    }

    public void cargarCitas(String pFichero) throws IOException, FileNotFoundException {
        Scanner sc = new Scanner(new FileReader(pFichero));
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] lineSplited = line.split("\\s+#\\s+");
            String idPub = lineSplited[0];
            String idCitada = lineSplited[1];

            Publicacion pub = buscarPublicacionPorId(idPub);
            if (pub == null) {
                pub = new Publicacion(idPub, "");
                listaPublicaciones.put(idPub, pub);
            }

            Publicacion citada = buscarPublicacionPorId(idCitada);
            if(citada == null){
                citada = new Publicacion(idCitada, "");
                listaPublicaciones.put(idCitada, citada);
            }
            pub.addCitada(citada);
        }
        sc.close();
    }

    //Dada una publicación (identificador), devolver una lista con las publicaciones que cita
    public HashSet<Publicacion> getCitasDePublicacion(String idPublicacion){
        Publicacion pub = buscarPublicacionPorId(idPublicacion);
        if(pub == null){
            return new HashSet<>();
        }
        return pub.getListaCitadas();
    }

    //Dada una publicación, devolver una lista con sus autores
    public HashSet<Autor> getAutoresDePublicacion(String idPublicacion){
        Publicacion pub = buscarPublicacionPorId(idPublicacion);
        if(pub == null){  //si no existe la publicación, devolvemos la lista vacía
            return new HashSet<>();
        }
        return pub.getListaAutores();
    }

    //Dado un autor, devolver una lista con sus publicaciones
    public HashSet<Publicacion> getPublicacionesDeAutor(String idAutor){
        Autor autor = listaAutores.get(idAutor);
        if(autor == null){
            return new HashSet<>();
        }
        return autor.getListaPublicaciones();
    }

    public void borrarPublicacion(Publicacion pub){
        if(pub == null){
            return;
        }
        //Borrarla de los autores que la referencian
        for(Autor autor: pub.getListaAutores()){
            autor.getListaPublicaciones().remove(pub);
        }
        //Borrarla de las citas de otras publicaciones
        for(Publicacion cita: listaPublicaciones.values()) {
            cita.getListaCitadas().remove(pub);
        }
        //Borrarla del repositorio
        listaPublicaciones.remove(pub.getId());
    }

    public void borrarAutor(Autor autor){
        if(autor == null){
            return;
        }
        //Borrarlo de las publicaciones en las que aparezca
        for(Publicacion pub: autor.getListaPublicaciones()){
            pub.getListaAutores().remove(autor);
        }
        //Borrarlo del repositorio
        listaAutores.remove(autor.getId());
    }

    public void readFile(String nom) {
        try {
            Scanner entrada = new Scanner(new FileReader(nom));

            String linea;
            int cont = 0;
            while (entrada.hasNext()) {
                linea = entrada.nextLine();
                cont++;
                if ((cont % 10000) == 0) System.out.println("Lineas: " + cont + "\t" + linea);
            }

            entrada.close();
        } // try
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearFichero(String nomF, String[] lineas) {
        // output: se han escrito las líneas en el fichero de nombre nomF
        try {
            PrintWriter writer = new PrintWriter(nomF, "UTF-8");
            for (String s : lineas) {
                writer.println(s);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarPublicaciones(String path) throws IOException{
        FileWriter fw = new FileWriter(path);
        for(Publicacion pub: listaPublicaciones.values()){
            fw.write(pub.getId() + "#" + pub.getTitulo() + "\n");
        }
        fw.close();
    }

    public void guardarAutores(String path) throws IOException{
        FileWriter fw = new FileWriter(path);
        for(Autor autor: listaAutores.values()){
            fw.write(autor.getId() + "#" + autor.getNombre() + "\n");
        }
        fw.close();
    }
}