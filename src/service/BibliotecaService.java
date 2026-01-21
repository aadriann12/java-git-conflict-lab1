package service;

import model.*;
import util.Validador;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BibliotecaService {
    private List<Libro> catalogo = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    public void agregarLibro(Libro libro) { catalogo.add(libro); }

    public boolean eliminarLibro(String isbn) {
        return catalogo.removeIf(l -> l.getIsbn().equals(isbn));
    }

    public Libro buscarPorIsbn(String isbn) {
        return catalogo.stream().filter(l -> l.getIsbn().equals(isbn)).findFirst().orElse(null);
    }

    public List<Libro> buscarPorTitulo(String texto) {
        return catalogo.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(texto.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Libro> listarCatalogo() { return new ArrayList<>(catalogo); }

    public boolean reservarLibro(Usuario usuario, Libro libroBasico) {
        Libro libro = buscarPorIsbn(libroBasico.getIsbn());
        if (usuario == null || libro == null || !libro.isDisponible()) return false;
        
        libro.setDisponible(false);
        reservas.add(new Reserva(usuario, libro));
        return true;
    }

    public boolean prestarLibro(Usuario usuario, String isbn) {
        Libro libro = buscarPorIsbn(isbn);
        if (libro != null && libro.isDisponible()) {
            libro.setDisponible(false);
            return true;
        }
        return false;
    }

    public boolean devolverLibro(String isbn) {
        Libro libro = buscarPorIsbn(isbn);
        if (libro != null && !libro.isDisponible()) {
            libro.setDisponible(true);
            reservas.removeIf(r -> r.getLibro().getIsbn().equals(isbn));
            return true;
        }
        return false;
    }

    public void listarReservas() {
        if (reservas.isEmpty()) System.out.println("Sin reservas.");
        else reservas.forEach(System.out::println);
    }
}