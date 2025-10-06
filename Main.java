//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        System.out.println("=== SISTEMA DE GESTIÓN DE PUBLICACIONES Y AUTORES ===\n");

        // Ejecutar todas las pruebas
        testCargaDatos();
        testBorrarPublicacion();
        testOperacionesCRUD();
        testRelaciones();
        testCasosError();
        testRendimiento();
        testOrdenacion();
        testPersistencia();
        testIntegracionCompleta();

        System.out.println("\n=== TODAS LAS PRUEBAS COMPLETADAS ===");
    }

    public static void testCargaDatos() {
        System.out.println("=== PRUEBAS CARGA DATOS ===");

        // Test 1: Carga archivo existente
        try {
            Repositorio.getRepositorio().cargarAutor("src/Datuak/authors-name-all.txt");
            int numAutores = Repositorio.getRepositorio().tamañoHashAutor();
            System.out.println("✓ Carga autores exitosa - " + numAutores + " autores cargados");
        } catch (IOException e) {
            System.out.println("✗ Error carga autores: " + e.getMessage());
        }

        // Test 2: Carga publicaciones
        try {
            ArrayList<String> titulos = Repositorio.getRepositorio().cargarPublicacion("src/Datuak/publications-titles-all.txt");
            int numPublicaciones = Repositorio.getRepositorio().tamañoHashPublicacion();
            System.out.println("✓ Carga publicaciones exitosa - " + numPublicaciones + " publicaciones cargadas");
        } catch (IOException e) {
            System.out.println("✗ Error carga publicaciones: " + e.getMessage());
        }

        // Test 3: Carga relaciones autores-publicaciones
        try {
            Repositorio.getRepositorio().cargarPublicacionesAutores("src/Datuak/publications-authors-all-final.txt");
            System.out.println("✓ Carga relaciones autores-publicaciones exitosa");
        } catch (IOException e) {
            System.out.println("✗ Error carga relaciones: " + e.getMessage());
        }

        // Test 4: Carga citas
        try {
            Repositorio.getRepositorio().cargarCitas("src/Datuak/publications-citedPubs-all.txt");
            System.out.println("✓ Carga citas exitosa");
        } catch (IOException e) {
            System.out.println("✗ Error carga citas: " + e.getMessage());
        }

        // Test 5: Carga archivo inexistente
        try {
            Repositorio.getRepositorio().cargarAutor("src/Datuak/archivo-inexistente.txt");
            System.out.println("✗ Debió fallar con archivo inexistente");
        } catch (FileNotFoundException e) {
            System.out.println("✓ Correcto: Maneja archivo inexistente");
        } catch (IOException e) {
            System.out.println("✓ Correcto: Maneja error IO");
        }
    }

    public static void testOperacionesCRUD() {
        System.out.println("\n=== PRUEBAS OPERACIONES CRUD ===");

        // Test 1: Insertar publicación nueva
        int sizeBefore = Repositorio.getRepositorio().tamañoHashPublicacion();
        Repositorio.getRepositorio().añadirPublicacion("TEST001", "Título Test Publicación");
        int sizeAfter = Repositorio.getRepositorio().tamañoHashPublicacion();
        Publicacion pub = Repositorio.getRepositorio().buscarPublicacionPorId("TEST001");

        if (pub != null && pub.getTitulo().equals("Título Test Publicación") && sizeAfter == sizeBefore + 1) {
            System.out.println("✓ Inserción publicación correcta");
        } else {
            System.out.println("✗ Error en inserción publicación");
        }

        // Test 2: Insertar publicación con ID duplicado
        sizeBefore = Repositorio.getRepositorio().tamañoHashPublicacion();
        Repositorio.getRepositorio().añadirPublicacion("TEST001", "Título Duplicado");
        sizeAfter = Repositorio.getRepositorio().tamañoHashPublicacion();
        if (sizeBefore == sizeAfter) {
            System.out.println("✓ Correcto: No permite duplicados");
        } else {
            System.out.println("✗ Error: Permitió duplicado");
        }

        // Test 3: Insertar autor nuevo
        sizeBefore = Repositorio.getRepositorio().tamañoHashAutor();
        Repositorio.getRepositorio().añadirAutor("AUT001", "Autor de Prueba");
        sizeAfter = Repositorio.getRepositorio().tamañoHashAutor();
        if (sizeAfter == sizeBefore + 1) {
            System.out.println("✓ Inserción autor correcta");
        } else {
            System.out.println("✗ Error en inserción autor");
        }

        // Test 4: Búsqueda publicación existente
        Publicacion encontrada = Repositorio.getRepositorio().buscarPublicacionPorId("TEST001");
        if (encontrada != null && encontrada.getTitulo().equals("Título Test Publicación")) {
            System.out.println("✓ Búsqueda publicación existente correcta");
        } else {
            System.out.println("✗ Error en búsqueda publicación existente");
        }

        // Test 5: Búsqueda publicación inexistente
        Publicacion inexistente = Repositorio.getRepositorio().buscarPublicacionPorId("NOEXISTE123");
        if (inexistente == null) {
            System.out.println("✓ Correcto: Retorna null para ID inexistente");
        } else {
            System.out.println("✗ Error: Debió retornar null para ID inexistente");
        }

        // Test 6: Eliminar publicación
        String resultadoEliminacion = Repositorio.getRepositorio().borrarPublicacionPorId("TEST001");
        if (resultadoEliminacion.contains("eliminada")) {
            System.out.println("✓ Eliminación publicación correcta");
        } else {
            System.out.println("✗ Error en eliminación publicación");
        }

        // Test 7: Eliminar autor
        String resultadoElimAutor = Repositorio.getRepositorio().borrarAutorPorId("AUT001");
        if (resultadoElimAutor.contains("eliminado")) {
            System.out.println("✓ Eliminación autor correcta");
        } else {
            System.out.println("✗ Error en eliminación autor");
        }
    }

    public static void testRelaciones() {
        System.out.println("\n=== PRUEBAS RELACIONES ===");

        // Configuración de datos de prueba
        Repositorio.getRepositorio().añadirAutor("AUT_REL1", "Autor Relación 1");
        Repositorio.getRepositorio().añadirAutor("AUT_REL2", "Autor Relación 2");
        Repositorio.getRepositorio().añadirPublicacion("PUB_REL1", "Publicación Relación 1");
        Repositorio.getRepositorio().añadirPublicacion("PUB_REL2", "Publicación Relación 2");
        Repositorio.getRepositorio().añadirPublicacion("PUB_REL3", "Publicación Relación 3");

        // Test 1: Relación autor-publicación
        Repositorio.getRepositorio().añadirAutorAPublicacion("AUT_REL1", "PUB_REL1");
        Repositorio.getRepositorio().añadirAutorAPublicacion("AUT_REL2", "PUB_REL1"); // Múltiples autores
        Repositorio.getRepositorio().añadirAutorAPublicacion("AUT_REL1", "PUB_REL2"); // Múltiples publicaciones

        HashSet<Autor> autoresPub1 = Repositorio.getRepositorio().getAutoresDePublicacion("PUB_REL1");
        if (autoresPub1.size() == 2) {
            System.out.println("✓ Relación autor-publicación correcta (múltiples autores)");
        } else {
            System.out.println("✗ Error en relación autor-publicación múltiple");
        }

        // Test 2: Relación publicación-autor
        HashSet<Publicacion> publicacionesAut1 = Repositorio.getRepositorio().getPublicacionesDeAutor("AUT_REL1");
        if (publicacionesAut1.size() == 2) {
            System.out.println("✓ Relación publicación-autor correcta (múltiples publicaciones)");
        } else {
            System.out.println("✗ Error en relación publicación-autor múltiple");
        }

        // Test 3: Relación cita entre publicaciones
        Publicacion origen = Repositorio.getRepositorio().buscarPublicacionPorId("PUB_REL1");
        Publicacion citada1 = Repositorio.getRepositorio().buscarPublicacionPorId("PUB_REL2");
        Publicacion citada2 = Repositorio.getRepositorio().buscarPublicacionPorId("PUB_REL3");

        origen.addCitada(citada1);
        origen.addCitada(citada2);

        HashSet<Publicacion> citas = Repositorio.getRepositorio().getCitasDePublicacion("PUB_REL1");
        if (citas.size() == 2) {
            System.out.println("✓ Relación cita correcta (múltiples citas)");
        } else {
            System.out.println("✗ Error en relación cita múltiple");
        }

        // Test 4: Publicación sin autores
        HashSet<Autor> autoresVacio = Repositorio.getRepositorio().getAutoresDePublicacion("PUB_REL3");
        if (autoresVacio.isEmpty()) {
            System.out.println("✓ Correcto: Publicación sin autores retorna conjunto vacío");
        } else {
            System.out.println("✗ Error: Publicación sin autores debería retornar vacío");
        }

        // Test 5: Publicación sin citas
        HashSet<Publicacion> citasVacio = Repositorio.getRepositorio().getCitasDePublicacion("PUB_REL2");
        if (citasVacio.isEmpty()) {
            System.out.println("✓ Correcto: Publicación sin citas retorna conjunto vacío");
        } else {
            System.out.println("✗ Error: Publicación sin citas debería retornar vacío");
        }
    }

    public static void testCasosError() {
        System.out.println("\n=== PRUEBAS CASOS ERROR ===");

        // Test 1: Añadir autor con ID nulo
        try {
            Repositorio.getRepositorio().añadirAutor(null, "Nombre");
            System.out.println("✗ Debió manejar ID nulo");
        } catch (Exception e) {
            System.out.println("✓ Correcto: Maneja ID nulo");
        }

        // Test 2: Añadir publicación con ID nulo
        try {
            Repositorio.getRepositorio().añadirPublicacion(null, "Título");
            System.out.println("✗ Debió manejar ID nulo en publicación");
        } catch (Exception e) {
            System.out.println("✓ Correcto: Maneja ID nulo en publicación");
        }

        // Test 3: Eliminar publicación inexistente
        String resultado = Repositorio.getRepositorio().borrarPublicacionPorId("NOEXISTE456");
        if (resultado.contains("no existe")) {
            System.out.println("✓ Correcto: Maneja eliminación publicación inexistente");
        } else {
            System.out.println("✗ Error en eliminación publicación inexistente");
        }

        // Test 4: Eliminar autor inexistente
        String resultadoAutor = Repositorio.getRepositorio().borrarAutorPorId("NOEXISTE789");
        if (resultadoAutor.contains("no existe")) {
            System.out.println("✓ Correcto: Maneja eliminación autor inexistente");
        } else {
            System.out.println("✗ Error en eliminación autor inexistente");
        }

        // Test 5: Relación con autor inexistente (debería crearlo)
        int autoresAntes = Repositorio.getRepositorio().tamañoHashAutor();
        Repositorio.getRepositorio().añadirAutorAPublicacion("AUT_INEXISTENTE", "PUB_REL1");
        int autoresDespues = Repositorio.getRepositorio().tamañoHashAutor();
        if (autoresDespues == autoresAntes + 1) {
            System.out.println("✓ Correcto: Crea autor automáticamente en relación");
        } else {
            System.out.println("✗ Error: No creó autor automáticamente");
        }

        // Test 6: Relación con publicación inexistente (debería crearla)
        int pubsAntes = Repositorio.getRepositorio().tamañoHashPublicacion();
        Repositorio.getRepositorio().añadirAutorAPublicacion("AUT_REL1", "PUB_INEXISTENTE");
        int pubsDespues = Repositorio.getRepositorio().tamañoHashPublicacion();
        if (pubsDespues == pubsAntes + 1) {
            System.out.println("✓ Correcto: Crea publicación automáticamente en relación");
        } else {
            System.out.println("✗ Error: No creó publicación automáticamente");
        }
    }

    public static void testRendimiento() {
        System.out.println("\n=== PRUEBAS RENDIMIENTO ===");

        long startTime = System.currentTimeMillis();

        // Test inserción múltiple - O(1)
        for (int i = 0; i < 1000; i++) {
            Repositorio.getRepositorio().añadirPublicacion("PERF_PUB" + i, "Título Performance " + i);
        }
        long insercionTime = System.currentTimeMillis();
        System.out.println("✓ 1000 inserciones: " + (insercionTime - startTime) + "ms");

        // Test búsqueda múltiple - O(1)
        for (int i = 0; i < 1000; i++) {
            Repositorio.getRepositorio().buscarPublicacionPorId("PERF_PUB" + i);
        }
        long busquedaTime = System.currentTimeMillis();
        System.out.println("✓ 1000 búsquedas: " + (busquedaTime - insercionTime) + "ms");

        // Test relaciones múltiples - O(1)
        for (int i = 0; i < 500; i++) {
            Repositorio.getRepositorio().añadirAutorAPublicacion("AUT_REL1", "PERF_PUB" + i);
        }
        long relacionesTime = System.currentTimeMillis();
        System.out.println("✓ 500 relaciones: " + (relacionesTime - busquedaTime) + "ms");

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("✓ Tiempo total 2500 operaciones: " + totalTime + "ms");

        // Limpiar datos de performance
        for (int i = 0; i < 1000; i++) {
            Repositorio.getRepositorio().borrarPublicacionPorId("PERF_PUB" + i);
        }
    }

    public static void testOrdenacion() {
        System.out.println("\n=== PRUEBAS ORDENACIÓN ===");

        // Añadir publicaciones con títulos desordenados
        Repositorio.getRepositorio().añadirPublicacion("ORD3", "Zebra Article");
        Repositorio.getRepositorio().añadirPublicacion("ORD1", "Alpha Study");
        Repositorio.getRepositorio().añadirPublicacion("ORD2", "Beta Research");
        Repositorio.getRepositorio().añadirPublicacion("ORD4", "Alpha Duplicate"); // Test duplicados

        // Test ordenación
        String[] ordenadas = Repositorio.getRepositorio().publicacionesOrdenadas();

        boolean correctamenteOrdenadas = true;
        for (int i = 0; i < ordenadas.length - 1; i++) {
            if (ordenadas[i].compareTo(ordenadas[i + 1]) > 0) {
                correctamenteOrdenadas = false;
                break;
            }
        }

        if (correctamenteOrdenadas) {
            System.out.println("✓ Ordenación alfabética correcta");
            System.out.println("  - Primer elemento: " + ordenadas[0]);
            System.out.println("  - Último elemento: " + ordenadas[ordenadas.length - 1]);
        } else {
            System.out.println("✗ Error en ordenación alfabética");
        }

        // Test impresión ordenada
        String resultadoImpresion = Repositorio.getRepositorio().imprimirPublicacionesOrdenadas(3);
        if (resultadoImpresion.contains("Lista ordenada")) {
            System.out.println("✓ Impresión ordenada correcta");
        } else {
            System.out.println("✗ Error en impresión ordenada");
        }

        // Limpiar datos de ordenación
        Repositorio.getRepositorio().borrarPublicacionPorId("ORD1");
        Repositorio.getRepositorio().borrarPublicacionPorId("ORD2");
        Repositorio.getRepositorio().borrarPublicacionPorId("ORD3");
        Repositorio.getRepositorio().borrarPublicacionPorId("ORD4");
    }

    public static void testPersistencia() {
        System.out.println("\n=== PRUEBAS PERSISTENCIA ===");

        // Crear datos de prueba
        Repositorio.getRepositorio().añadirAutor("PERS_AUT1", "Autor Persistencia");
        Repositorio.getRepositorio().añadirPublicacion("PERS_PUB1", "Publicación Persistencia");
        Repositorio.getRepositorio().añadirAutorAPublicacion("PERS_AUT1", "PERS_PUB1");

        try {
            // Test guardar autores
            Repositorio.getRepositorio().guardarAutores("test_autores_salida.txt");
            System.out.println("✓ Guardado autores exitoso");

            // Test guardar publicaciones
            Repositorio.getRepositorio().guardarPublicaciones("test_publicaciones_salida.txt");
            System.out.println("✓ Guardado publicaciones exitoso");

        } catch (IOException e) {
            System.out.println("✗ Error en persistencia: " + e.getMessage());
        }

        // Limpiar
        Repositorio.getRepositorio().borrarPublicacionPorId("PERS_PUB1");
        Repositorio.getRepositorio().borrarAutorPorId("PERS_AUT1");
    }

    public static void testIntegracionCompleta() {
        System.out.println("\n=== PRUEBA INTEGRACIÓN COMPLETA ===");

        // Simular un flujo completo de uso
        Repositorio repo = Repositorio.getRepositorio();

        // 1. Crear autores y publicaciones
        repo.añadirAutor("INT_AUT1", "María García");
        repo.añadirAutor("INT_AUT2", "Carlos López");
        repo.añadirPublicacion("INT_PUB1", "Machine Learning Avanzado");
        repo.añadirPublicacion("INT_PUB2", "Redes Neuronales Profundas");
        repo.añadirPublicacion("INT_PUB3", "Artículo de Referencia");

        // 2. Establecer relaciones
        repo.añadirAutorAPublicacion("INT_AUT1", "INT_PUB1");
        repo.añadirAutorAPublicacion("INT_AUT2", "INT_PUB1"); // Co-autor
        repo.añadirAutorAPublicacion("INT_AUT1", "INT_PUB2");

        // 3. Establecer citas
        Publicacion pub1 = repo.buscarPublicacionPorId("INT_PUB1");
        Publicacion pub3 = repo.buscarPublicacionPorId("INT_PUB3");
        pub1.addCitada(pub3);

        // 4. Verificar integridad
        HashSet<Autor> autoresPub1 = repo.getAutoresDePublicacion("INT_PUB1");
        HashSet<Publicacion> citasPub1 = repo.getCitasDePublicacion("INT_PUB1");
        HashSet<Publicacion> pubsAut1 = repo.getPublicacionesDeAutor("INT_AUT1");

        boolean integridadOK = (autoresPub1.size() == 2) &&
                (citasPub1.size() == 1) &&
                (pubsAut1.size() == 2);

        if (integridadOK) {
            System.out.println("✓ Integración completa exitosa");
            System.out.println("  - Autores por publicación: " + autoresPub1.size());
            System.out.println("  - Citas por publicación: " + citasPub1.size());
            System.out.println("  - Publicaciones por autor: " + pubsAut1.size());
        } else {
            System.out.println("✗ Error en integración completa");
        }

        // 5. Ordenar y mostrar resultados
        String[] ordenadas = repo.publicacionesOrdenadas();
        System.out.println("  - Publicaciones ordenadas: " + ordenadas.length + " elementos");

        // Limpiar
        repo.borrarPublicacionPorId("INT_PUB1");
        repo.borrarPublicacionPorId("INT_PUB2");
        repo.borrarPublicacionPorId("INT_PUB3");
        repo.borrarAutorPorId("INT_AUT1");
        repo.borrarAutorPorId("INT_AUT2");
    }
    public static void testBorrarPublicacion() {
        System.out.println("\n=== PRUEBAS ESPECÍFICAS BORRAR PUBLICACIÓN ===");

        try {
            // Configuración inicial
            Repositorio repo = Repositorio.getRepositorio();

            // Test 1: Borrar publicación sin relaciones
            System.out.println("\n--- Test 1: Publicación sin relaciones ---");
            repo.añadirPublicacion("BORRAR1", "Publicación a borrar sin relaciones");
            int sizeBefore = repo.tamañoHashPublicacion();

            String resultado1 = repo.borrarPublicacionPorId("BORRAR1");
            int sizeAfter = repo.tamañoHashPublicacion();

            if (resultado1.contains("eliminada") && sizeAfter == sizeBefore - 1) {
                System.out.println("✓ Borrado publicación sin relaciones correcto");
            } else {
                System.out.println("✗ Error borrando publicación sin relaciones");
            }

            // Test 2: Borrar publicación con autores
            System.out.println("\n--- Test 2: Publicación con autores ---");
            repo.añadirAutor("AUT_BORRAR1", "Autor Test Borrado");
            repo.añadirPublicacion("BORRAR2", "Publicación con autores");
            repo.añadirAutorAPublicacion("AUT_BORRAR1", "BORRAR2");

            // Verificar que la relación existe antes de borrar
            HashSet<Autor> autoresAntes = repo.getAutoresDePublicacion("BORRAR2");
            HashSet<Publicacion> pubsAutorAntes = repo.getPublicacionesDeAutor("AUT_BORRAR1");

            System.out.println("  - Autores antes de borrar: " + autoresAntes.size());
            System.out.println("  - Publicaciones del autor antes: " + pubsAutorAntes.size());

            String resultado2 = repo.borrarPublicacionPorId("BORRAR2");

            // Verificar que se limpiaron las relaciones
            HashSet<Publicacion> pubsAutorDespues = repo.getPublicacionesDeAutor("AUT_BORRAR1");
            Publicacion pubBorrada = repo.buscarPublicacionPorId("BORRAR2");

            if (resultado2.contains("eliminada") && pubBorrada == null && pubsAutorDespues.size() == 0) {
                System.out.println("✓ Borrado publicación con autores correcto");
                System.out.println("  - Publicaciones del autor después: " + pubsAutorDespues.size());
            } else {
                System.out.println("✗ Error borrando publicación con autores");
            }

            // Test 3: Borrar publicación que es citada por otras
            System.out.println("\n--- Test 3: Publicación citada por otras ---");
            repo.añadirPublicacion("BORRAR3", "Publicación que será citada");
            repo.añadirPublicacion("BORRAR4", "Publicación que cita");
            repo.añadirPublicacion("BORRAR5", "Otra publicación que cita");

            // Establecer citas
            Publicacion citadora1 = repo.buscarPublicacionPorId("BORRAR4");
            Publicacion citadora2 = repo.buscarPublicacionPorId("BORRAR5");
            Publicacion citada = repo.buscarPublicacionPorId("BORRAR3");

            citadora1.addCitada(citada);
            citadora2.addCitada(citada);

            // Verificar citas antes de borrar
            HashSet<Publicacion> citasCitadora1Antes = repo.getCitasDePublicacion("BORRAR4");
            HashSet<Publicacion> citasCitadora2Antes = repo.getCitasDePublicacion("BORRAR5");

            System.out.println("  - Citas de BORRAR4 antes: " + citasCitadora1Antes.size());
            System.out.println("  - Citas de BORRAR5 antes: " + citasCitadora2Antes.size());

            String resultado3 = repo.borrarPublicacionPorId("BORRAR3");

            // Verificar que se limpiaron las citas
            HashSet<Publicacion> citasCitadora1Despues = repo.getCitasDePublicacion("BORRAR4");
            HashSet<Publicacion> citasCitadora2Despues = repo.getCitasDePublicacion("BORRAR5");

            if (resultado3.contains("eliminada") &&
                    citasCitadora1Despues.size() == 0 &&
                    citasCitadora2Despues.size() == 0) {
                System.out.println("✓ Borrado publicación citada correcto");
                System.out.println("  - Citas de BORRAR4 después: " + citasCitadora1Despues.size());
                System.out.println("  - Citas de BORRAR5 después: " + citasCitadora2Despues.size());
            } else {
                System.out.println("✗ Error borrando publicación citada");
            }

            // Test 4: Borrar publicación que cita a otras
            System.out.println("\n--- Test 4: Publicación que cita a otras ---");
            repo.añadirPublicacion("BORRAR6", "Publicación que cita");
            repo.añadirPublicacion("BORRAR7", "Publicación citada 1");
            repo.añadirPublicacion("BORRAR8", "Publicación citada 2");

            Publicacion citadora = repo.buscarPublicacionPorId("BORRAR6");
            Publicacion citada1 = repo.buscarPublicacionPorId("BORRAR7");
            Publicacion citada2 = repo.buscarPublicacionPorId("BORRAR8");

            citadora.addCitada(citada1);
            citadora.addCitada(citada2);

            // Verificar que las publicaciones citadas existen independientemente
            System.out.println("  - Publicación BORRAR7 existe antes: " + (repo.buscarPublicacionPorId("BORRAR7") != null));
            System.out.println("  - Publicación BORRAR8 existe antes: " + (repo.buscarPublicacionPorId("BORRAR8") != null));

            String resultado4 = repo.borrarPublicacionPorId("BORRAR6");

            // Verificar que las publicaciones citadas siguen existiendo
            boolean citadasSiguenExistiendo = (repo.buscarPublicacionPorId("BORRAR7") != null) &&
                    (repo.buscarPublicacionPorId("BORRAR8") != null);

            if (resultado4.contains("eliminada") && citadasSiguenExistiendo) {
                System.out.println("✓ Borrado publicación que cita correcto");
                System.out.println("  - Publicaciones citadas siguen existiendo: " + citadasSiguenExistiendo);
            } else {
                System.out.println("✗ Error borrando publicación que cita");
            }

            // Test 5: Borrar publicación con relaciones complejas
            System.out.println("\n--- Test 5: Publicación con relaciones complejas ---");
            repo.añadirAutor("AUT_BORRAR2", "Autor Complejo 1");
            repo.añadirAutor("AUT_BORRAR3", "Autor Complejo 2");
            repo.añadirPublicacion("BORRAR9", "Publicación compleja");
            repo.añadirPublicacion("BORRAR10", "Otra publicación");
            repo.añadirPublicacion("BORRAR11", "Publicación que cita");

            // Establecer relaciones complejas
            repo.añadirAutorAPublicacion("AUT_BORRAR2", "BORRAR9");
            repo.añadirAutorAPublicacion("AUT_BORRAR3", "BORRAR9");

            Publicacion pubCompleja = repo.buscarPublicacionPorId("BORRAR9");
            Publicacion otraPub = repo.buscarPublicacionPorId("BORRAR10");
            Publicacion citadoraCompleja = repo.buscarPublicacionPorId("BORRAR11");

            if (pubCompleja != null && otraPub != null && citadoraCompleja != null) {
                pubCompleja.addCitada(otraPub);
                citadoraCompleja.addCitada(pubCompleja);

                // Verificar estado antes de borrar
                HashSet<Autor> autoresComplejosAntes = repo.getAutoresDePublicacion("BORRAR9");
                HashSet<Publicacion> citasDeComplejaAntes = repo.getCitasDePublicacion("BORRAR9");
                HashSet<Publicacion> citasAComplejaAntes = repo.getCitasDePublicacion("BORRAR11");

                System.out.println("  - Autores antes: " + autoresComplejosAntes.size());
                System.out.println("  - Citas de BORRAR9 antes: " + citasDeComplejaAntes.size());
                System.out.println("  - Citas a BORRAR9 antes: " + citasAComplejaAntes.size());

                String resultado5 = repo.borrarPublicacionPorId("BORRAR9");

                // Verificar limpieza completa
                HashSet<Autor> autoresComplejosDespues = repo.getAutoresDePublicacion("BORRAR9");
                HashSet<Publicacion> citasDeComplejaDespues = repo.getCitasDePublicacion("BORRAR9");
                HashSet<Publicacion> citasAComplejaDespues = repo.getCitasDePublicacion("BORRAR11");
                Publicacion pubComplejaDespues = repo.buscarPublicacionPorId("BORRAR9");

                boolean limpiezaCorrecta = (pubComplejaDespues == null) &&
                        (autoresComplejosDespues.size() == 0) &&
                        (citasDeComplejaDespues.size() == 0) &&
                        (citasAComplejaDespues.size() == 0);

                if (resultado5.contains("eliminada") && limpiezaCorrecta) {
                    System.out.println("✓ Borrado publicación compleja correcto");
                    System.out.println("  - Todas las relaciones limpiadas: " + limpiezaCorrecta);
                } else {
                    System.out.println("✗ Error borrando publicación compleja");
                }
            }

            // Test 6: Borrar publicación inexistente
            System.out.println("\n--- Test 6: Publicación inexistente ---");
            String resultado6 = repo.borrarPublicacionPorId("NO_EXISTE_123");
            if (resultado6.contains("no existe")) {
                System.out.println("✓ Manejo correcto de publicación inexistente");
                System.out.println("  - Mensaje: " + resultado6);
            } else {
                System.out.println("✗ Error manejando publicación inexistente");
            }

            // Test 7: Borrar publicación nula
            System.out.println("\n--- Test 7: ID nulo ---");
            try {
                String resultado7 = repo.borrarPublicacionPorId(null);
                System.out.println("  - Resultado con ID nulo: " + resultado7);
            } catch (Exception e) {
                System.out.println("  - Excepción con ID nulo: " + e.getClass().getSimpleName());
            }

            // Limpieza final
            repo.borrarAutorPorId("AUT_BORRAR1");
            repo.borrarAutorPorId("AUT_BORRAR2");
            repo.borrarAutorPorId("AUT_BORRAR3");
            repo.borrarPublicacionPorId("BORRAR4");
            repo.borrarPublicacionPorId("BORRAR5");
            repo.borrarPublicacionPorId("BORRAR7");
            repo.borrarPublicacionPorId("BORRAR8");
            repo.borrarPublicacionPorId("BORRAR10");
            repo.borrarPublicacionPorId("BORRAR11");

            System.out.println("\n=== PRUEBAS BORRAR PUBLICACIÓN COMPLETADAS ===");

        } catch (Exception e) {
            System.out.println("✗ Error inesperado en pruebas de borrado: " + e.getMessage());
            e.printStackTrace();
        }
    }

        long inicio = System.currentTimeMillis();

        //Cargar datos
        Repositorio.getRepositorio().cargarAutor("C:\\Users\\Yu Meng Ji\\IdeaProjects\\EDA\\src\\Datuak\\authors-name-all.txt");
        System.out.println("Los datos se han cargado. Se han cargado " + Repositorio.getRepositorio().tamañoHashAutor() + " autores desde el archivo.");
        System.out.println("");

        Repositorio.getRepositorio().cargarPublicacion("C:\\Users\\Yu Meng Ji\\IdeaProjects\\EDA\\src\\Datuak\\publications-titles-all.txt");
        System.out.println("Los datos se han cargado. Se han cargado " + Repositorio.getRepositorio().tamañoHashPublicacion() + " publicaciones desde el archivo.");
        System.out.println("");

        Repositorio.getRepositorio().cargarPublicacionesAutores("C:\\Users\\Yu Meng Ji\\IdeaProjects\\EDA\\src\\Datuak\\publications-authors-all-final.txt");
        Repositorio.getRepositorio().cargarCitas("C:\\Users\\Yu Meng Ji\\IdeaProjects\\EDA\\src\\Datuak\\publications-citedPubs-all.txt");
        System.out.println("Se han cargado las relaciones de autores y citas.");

        //Buscar una publicación dado su id
        String pubBuscar = "Q34484783";
        System.out.println("Se buscará la publicación: " + pubBuscar);
        Publicacion pub = Repositorio.getRepositorio().buscarPublicacionPorId(pubBuscar);
        if (pub != null) {
            System.out.println("Publicación encontrada: " + pub.getTitulo());
        } else {
            System.out.println("No se encontró la publicación con id " + pubBuscar);
        }
        System.out.println();

        //Insertar una nueva publicacion
        Repositorio.getRepositorio().añadirPublicacion("Q12345678","Vlog de Viaje: Explorando la Costa Vasca");
        System.out.println("La publicación ha sido añadida. Nuevo número de publicaciones: " + Repositorio.getRepositorio().tamañoHashPublicacion());
        System.out.println("");

        //Añadir una cita a una publicacion
        String pubOrigen = "Q12345678";
        Repositorio.getRepositorio().añadirPublicacion("Q87654321","La Costa Vasca");
        Publicacion origen = Repositorio.getRepositorio().buscarPublicacionPorId(pubOrigen);
        Publicacion citada = Repositorio.getRepositorio().buscarPublicacionPorId("Q87654321");
        if (origen != null && citada != null) {
            origen.addCitada(citada);
            System.out.println("La publicación " + pubOrigen + " ahora cita a " + citada.getId() + ".\n");
        } else {
            System.out.println("No se pudo añadir la cita porque alguna publicación no existe.\n");
        }

        //Añadir un nuevo autor
        Repositorio.getRepositorio().añadirAutor("Q13579246", "Ana");
        System.out.println("Autor nuevo añadido: Q13579246 - Ana");
        System.out.println("Número total de autores: " + Repositorio.getRepositorio().tamañoHashAutor() + "\n");

        //Añadir un autor a una publicacion
        Repositorio.getRepositorio().añadirAutorAPublicacion("Q13579246", "Q12345678");
        System.out.println("El autor Q13579246 se ha asociado a la publicación Q12345678.");

        //Dada una publicacion, devolver una lista con las publicaciones que cita
        System.out.println("Publicaciones citadas por Q12345678:");
        HashSet<Publicacion> citas = Repositorio.getRepositorio().getCitasDePublicacion("Q12345678");
        if (citas.isEmpty()) {
            System.out.println(" (No cita a ninguna publicación)");
        } else {
            for (Publicacion p : citas) {
                System.out.println(" - " + p.getId() + " # " + p.getTitulo());
            }
        }
        System.out.println();

        //Dada una publicacion, devolver una lista con sus autores
        System.out.println("Autores de la publicación Q12345678:");
        HashSet<Autor> autoresPub = Repositorio.getRepositorio().getAutoresDePublicacion("Q12345678");
        if (autoresPub.isEmpty()) {
            System.out.println(" (No tiene autores asociados)");
        } else {
            for (Autor a : autoresPub) {
                System.out.println(" - " + a.getId() + " # " + a.getNombre());
            }
        }
        System.out.println();

        //Dado un autor, devolver una lista con sus publicaciones
        System.out.println("Publicaciones del autor Q13579246:");
        HashSet<Publicacion> pubsAutor = Repositorio.getRepositorio().getPublicacionesDeAutor("Q13579246");
        if (pubsAutor.isEmpty()) {
            System.out.println(" (El autor no tiene publicaciones)");
        } else {
            for (Publicacion p : pubsAutor) {
                System.out.println(" - " + p.getId() + " # " + p.getTitulo());
            }
        }
        System.out.println();

        //Borrar un autor
        System.out.println("Borrando autor Q13579246...");
        Autor autorABorrar = Repositorio.getRepositorio().getListaAutores().get("Q13579246");
        if (autorABorrar != null) {
            Repositorio.getRepositorio().borrarAutor(autorABorrar);
            System.out.println("Autor Q13579246 eliminado. Total de autores: " + Repositorio.getRepositorio().tamañoHashAutor());
        } else {
            System.out.println("El autor Q13579246 no existe.");
        }
        System.out.println();

        //Borrar una publicacion
        System.out.println("Borrando publicación Q12345678...");
        Publicacion pubABorrar = Repositorio.getRepositorio().getListaPublicaciones().get("Q12345678");
        if (pubABorrar != null) {
            Repositorio.getRepositorio().borrarPublicacion(pubABorrar);
            System.out.println("Publicación Q12345678 eliminada. Total publicaciones: " + Repositorio.getRepositorio().tamañoHashPublicacion());
        } else {
            System.out.println("La publicación Q12345678 no existe.");
        }
        System.out.println();

        //Ordenar la lista de publicaciones
        ArrayList<String> listaTitulos = new ArrayList<>();
        for (Publicacion p : Repositorio.getRepositorio().getListaPublicaciones().values()) {
            listaTitulos.add(p.getTitulo());
        }
        Repositorio.getRepositorio().publicacionesOrdenadas(listaTitulos);

        //Escribir los datos en archivos
        System.out.println("Finalmente, escribiremos todos los datos en nuevos archivos.");
        Repositorio.getRepositorio().guardarAutores("C:\\Users\\Yu Meng Ji\\IdeaProjects\\EDA\\src\\Datuak\\salida-autores-all.txt");
        Repositorio.getRepositorio().guardarPublicaciones("C:\\Users\\Yu Meng Ji\\IdeaProjects\\EDA\\src\\Datuak\\salida-publicaciones-all.txt");
        System.out.println("Archivos de salida generados correctamente.");

        //Calcular tiempo
        long fin = System.currentTimeMillis();
        double tiempo = (double) ((fin - inicio) / 1000);
        System.out.println("Tiempo total de ejecución: " + tiempo + " segundos");
    }

}
