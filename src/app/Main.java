
package app;

import model.Libro;
import model.Usuario;
import service.BibliotecaService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BibliotecaService biblioteca = new BibliotecaService();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Gestión de Biblioteca ===");

        while (true) {
            mostrarMenu();
            String op = sc.nextLine().trim();

            switch (op) {
                case "1": // Agregar libro
                    Libro nuevo = leerLibro(sc);
                    biblioteca.agregarLibro(nuevo);
                    System.out.println("✔ Libro agregado.");
                    break;

                case "2": // Eliminar libro
                    System.out.print("ISBN a eliminar: ");
                    String isbnEliminar = sc.nextLine().trim();
                    boolean eliminado = biblioteca.eliminarLibro(isbnEliminar);
                    System.out.println(eliminado ? "✔ Eliminado" : "✖ No encontrado");
                    break;

                case "3": // Buscar por ISBN
                    System.out.print("ISBN a buscar: ");
                    String isbnBuscar = sc.nextLine().trim();
                    Libro encontrado = biblioteca.buscarPorIsbn(isbnBuscar);
                    System.out.println(encontrado != null ? encontrado : "✖ No encontrado");
                    break;

                case "4": // Buscar por título
                    System.out.print("Texto del título: ");
                    String texto = sc.nextLine().trim();
                    List<Libro> resultados = biblioteca.buscarPorTitulo(texto);
                    if (resultados == null || resultados.isEmpty()) {
                        System.out.println("✖ Sin coincidencias");
                    } else {
                        resultados.forEach(System.out::println);
                    }
                    break;

                case "5": // Listar catálogo
                    List<Libro> catalogo = biblioteca.listarCatalogo();
                    if (catalogo == null || catalogo.isEmpty()) {
                        System.out.println("✖ Catálogo vacío");
                    } else {
                        catalogo.forEach(System.out::println);
                    }
                    break;

                case "6": // Reservar libro
                    Usuario usuarioRes = leerUsuario(sc);
                    Libro libroRes = leerLibroBasico(sc);
                    biblioteca.reservarLibro(usuarioRes, libroRes);
                    System.out.println("✔ Reserva registrada (si procede).");
                    break;

                case "7": // Listar reservas
                    biblioteca.listarReservas();
                    break;

                case "8": // Prestar libro
                    Usuario usuarioPrest = leerUsuario(sc);
                    System.out.print("ISBN a prestar: ");
                    String isbnPrest = sc.nextLine().trim();
                    boolean prestado = biblioteca.prestarLibro(usuarioPrest, isbnPrest);
                    System.out.println(prestado ? "✔ Prestado" : "✖ No disponible o no encontrado");
                    break;

                case "9": // Devolver libro
                    System.out.print("ISBN a devolver: ");
                    String isbnDev = sc.nextLine().trim();
                    boolean devuelto = biblioteca.devolverLibro(isbnDev);
                    System.out.println(devuelto ? "✔ Devuelto" : "✖ No encontrado o no estaba prestado");
                    break;

                case "0":
                    System.out.println("¡Hasta luego!");
                    sc.close();
                    return;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\nMenú:");
        System.out.println("1) Agregar libro");
        System.out.println("2) Eliminar libro");
        System.out.println("3) Buscar por ISBN");
        System.out.println("4) Buscar por título");
        System.out.println("5) Listar catálogo");
        System.out.println("6) Reservar libro");
        System.out.println("7) Listar reservas");
        System.out.println("8) Prestar libro");
        System.out.println("9) Devolver libro");
        System.out.println("0) Salir");
        System.out.print("Elige una opción: ");
    }

    // Helpers para lectura de datos
    private static Libro leerLibro(Scanner sc) {
        System.out.print("ISBN: ");
        String isbn = sc.nextLine().trim();
        System.out.print("Título: ");
        String titulo = sc.nextLine().trim();
        System.out.print("Autor (opcional): ");
        String autor = sc.nextLine().trim();
        return new Libro(isbn, titulo); // Ajusta si tu constructor incluye autor/año
    }

    private static Libro leerLibroBasico(Scanner sc) {
        System.out.print("ISBN: ");
        String isbn = sc.nextLine().trim();
        System.out.print("Título: ");
        String titulo = sc.nextLine().trim();
        return new Libro(isbn, titulo);
    }

    private static Usuario leerUsuario(Scanner sc) {
        System.out.print("ID usuario: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Se usará 0.");
            id = 0;
        }
        System.out.print("Nombre del usuario: ");
        String nombre = sc.nextLine().trim();
        return new Usuario(id, nombre);
    }
}
