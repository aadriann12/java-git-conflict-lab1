package model;

public class Reserva {
    private final Usuario usuario;
    private final Libro libro;

    public Reserva(Usuario usuario, Libro libro) {
        this.usuario = usuario;
        this.libro = libro;
    }

    public Usuario getUsuario() { return usuario; }
    public Libro getLibro() { return libro; }

    @Override
    public String toString() {
        return usuario.getNombre() + " ha reservado " + libro.getTitulo();
    }
}