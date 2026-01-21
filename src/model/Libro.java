package model;

import java.util.Objects;

public class Libro {
    private String isbn;
    private String titulo;
    private boolean disponible;

    public Libro(String isbn, String titulo) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.disponible = true;
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(isbn, libro.isbn);
    }

    @Override
    public String toString() {
        return "[" + isbn + "] " + titulo + " - " + (disponible ? "Disponible" : "Prestado/Reservado");
    }
}