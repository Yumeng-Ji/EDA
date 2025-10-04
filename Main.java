//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, FileNotFoundException {
       /* Repositorio.getRepositorio().cargarAutor("C:\\Users\\Yu Meng Ji\\IdeaProjects\\EDA\\src\\Datuak\\authors-name-all.txt");
       ArrayList<String> titulos = new ArrayList<>();
            titulos.add("Ade");
            titulos.add("Acbd");
            titulos.add("Bbcd");
            titulos.add("Cbcd");
            titulos.add("Dcd");
            System.out.println("Original titles:");
            for (int i = 0; i < titulos.size(); i++) {
                System.out.println((i+1) + ". " + titulos.get(i));
            }
            String[] ordenas = miRep.publicacionesOrdenadas(titulos);
            System.out.println("\nSorted titles:");
            for (int i = 0; i < ordenas.length; i++) {
                System.out.println((i+1) + ". " + ordenas[i]);
            }
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


