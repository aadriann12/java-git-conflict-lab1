package model;

import java.util.Objects;

public class Libro {
    private final String isbn;
    private final String titulo;

    // Nuevo modelo: reservas activas
    private int reservasActivas = 0;

    // Para préstamos (opciones 8 y 9)
    private boolean prestado = false;

    public Libro(String isbn, String titulo) {
        this.isbn = isbn == null ? "" : isbn.trim();
        this.titulo = titulo == null ? "" : titulo.trim();
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }

    public boolean isDisponible() {
        return reservasActivas == 0 && !prestado;
    }

    public void incrementarReservas() { reservasActivas++; }
    public void decrementarReservas() { if (reservasActivas > 0) reservasActivas--; }

    // Mantengo este método por compatibilidad con código antiguo,
    // pero lo hago coherente con reservasActivas.
    public void setDisponible(boolean disponible) {
        reservasActivas = disponible ? 0 : Math.max(reservasActivas, 1);
    }

    public boolean isPrestado() { return prestado; }
    public void setPrestado(boolean prestado) { this.prestado = prestado; }

    @Override
    public String toString() {
        String estado = isDisponible() ? "Disponible" : (prestado ? "Prestado" : "Reservado");
        return titulo + " (" + isbn + ") - " + estado + " [reservasActivas=" + reservasActivas + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return Objects.equals(isbn, libro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}