//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, FileNotFoundException {
       /* Repositorio.getRepositorio().cargarAutor("C:\\Users\\Yu Meng Ji\\IdeaProjects\\EDA\\src\\Datuak\\authors-name-all.txt");
        int tamanio =Repositorio.getRepositorio().tamañoHash();
        System.out.println(tamanio); */

        ArrayList<String> listaTitulos = Repositorio.getRepositorio().cargarPublicacion("C:\\Users\\Yu Meng Ji\\IdeaProjects\\EDA\\src\\Datuak\\publications-titles-all.txt");
        int tamanio1 = Repositorio.getRepositorio().tamañoHashPublicacion();
        System.out.println(tamanio1);
        //Repositorio.getRepositorio().imprimirListaOrdenada();

        String[] ordenadas = Repositorio.getRepositorio().publicacionesOrdenadas(listaTitulos);
        for (String titulo : ordenadas) {
            System.out.println(titulo);
        }

        System.out.println("Fin");

    }
}


