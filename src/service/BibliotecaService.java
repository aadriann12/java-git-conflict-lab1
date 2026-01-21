package service;

import model.Libro;
import model.Prestamo;
import model.Reserva;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {

    private final List<Libro> catalogo = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();
    private final List<Prestamo> prestamos = new ArrayList<>();

    // ===== Catálogo =====

    public void agregarLibro(Libro libro) {
        if (libro == null || libro.getIsbn().isEmpty() || libro.getTitulo().isEmpty()) return;
        if (buscarPorIsbn(libro.getIsbn()) != null) return; // evita duplicados
        catalogo.add(libro);
    }

    public boolean eliminarLibro(String isbn) {
        Libro l = buscarPorIsbn(isbn);
        if (l == null) return false;
        // Si está prestado o reservado, podrías decidir bloquear eliminación:
        // aquí lo permito solo si está disponible.
        if (!l.isDisponible()) return false;
        return catalogo.remove(l);
    }

    public Libro buscarPorIsbn(String isbn) {
        if (isbn == null) return null;
        String key = isbn.trim();
        for (Libro l : catalogo) {
            if (l.getIsbn().equalsIgnoreCase(key)) return l;
        }
        return null;
    }

    public List<Libro> buscarPorTitulo(String texto) {
        List<Libro> res = new ArrayList<>();
        if (texto == null) return res;
        String t = texto.trim().toLowerCase();
        for (Libro l : catalogo) {
            if (l.getTitulo().toLowerCase().contains(t)) res.add(l);
        }
        return res;
    }

    public List<Libro> listarCatalogo() {
        return new ArrayList<>(catalogo);
    }

    // ===== Reservas =====

    public boolean reservarLibro(Usuario usuario, Libro libro) {
        if (!validarUsuario(usuario)) {
            System.out.println("Error: usuario no válido.");
            return false;
        }
        if (libro == null) {
            System.out.println("Error: libro nulo.");
            return false;
        }

        // Importante: reservar siempre el libro REAL del catálogo por ISBN
        Libro real = buscarPorIsbn(libro.getIsbn());
        if (real == null) {
            System.out.println("Error: ISBN no existe en catálogo.");
            return false;
        }

        if (!real.isDisponible()) {
            System.out.println("Error: el libro no está disponible.");
            return false;
        }

        // Duplicado: mismo usuario y mismo libro
        for (Reserva r : reservas) {
            if (r.getUsuario().equals(usuario) && r.getLibro().equals(real)) {
                System.out.println("Error: ya reservado por este usuario.");
                return false;
            }
        }

        real.incrementarReservas();
        reservas.add(new Reserva(usuario, real));
        System.out.println("Reserva realizada correctamente");
        return true;
    }

    public void listarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        System.out.println("Listado de reservas:");
        for (Reserva r : reservas) System.out.println("- " + r);
    }

    // ===== Préstamos =====

    public boolean prestarLibro(Usuario usuario, String isbn) {
        if (!validarUsuario(usuario)) return false;

        Libro l = buscarPorIsbn(isbn);
        if (l == null) return false;

        if (!l.isDisponible()) return false;

        l.setPrestado(true);
        prestamos.add(new Prestamo(usuario, l));
        return true;
    }

    public boolean devolverLibro(String isbn) {
        Libro l = buscarPorIsbn(isbn);
        if (l == null) return false;
        if (!l.isPrestado()) return false;

        l.setPrestado(false);
        // elimina el préstamo asociado a ese libro (simple)
        prestamos.removeIf(p -> p.getLibro().equals(l));
        return true;
    }

    // ===== Validación =====

    private boolean validarUsuario(Usuario u) {
        return u != null
                && u.getEmail() != null && !u.getEmail().trim().isEmpty()
                && u.getNombre() != null && !u.getNombre().trim().isEmpty();
    }
}