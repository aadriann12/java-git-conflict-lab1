package service;

import model.Libro;
import model.Reserva;
import model.Usuario;
import util.Validador;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {

    private List<Reserva> reservas = new ArrayList<>();

    /**
     * Intenta realizar la reserva de un libro.
     *
     * @param usuario Usuario que realiza la reserva
     * @param libro   Libro a reservar
     * @return true si la reserva se realiza correctamente, false en caso contrario
     */
    public boolean reservarLibro(Usuario usuario, Libro libro) {

        // Validación del usuario
        if (usuario == null) {
            System.out.println("Error: el usuario no puede ser nulo.");
            return false;
        }

        if (!Validador.validarUsuario(usuario)) {
            System.out.println("Error: usuario no válido.");
            return false;
        }

        // Validación del libro
        if (libro == null) {
            System.out.println("Error: el libro no puede ser nulo.");
            return false;
        }

        if (!libro.isDisponible()) {
            System.out.println("Error: el libro ya está reservado.");
            return false;
        }

        // Comprobación de reservas duplicadas
        for (Reserva r : reservas) {
            if (r.getUsuario().equals(usuario) &&
                r.getLibro().equals(libro)) {
                System.out.println("Error: el usuario ya tiene este libro reservado.");
                return false;
            }
        }

        // Realizar la reserva
        libro.setDisponible(false);
        reservas.add(new Reserva(usuario, libro));

        System.out.println("Reserva realizada correctamente.");
        return true;
    }

    public void listarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }

        System.out.println("Listado de reservas:");
        for (Reserva r : reservas) {
            System.out.println("- " + r);
        }
    }

    public static boolean validarUsuario(Usuario usuario) {
        return usuario != null && usuario.getNombre() != null;
    }
}
