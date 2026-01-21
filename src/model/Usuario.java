package model;

import java.util.Objects;

public class Usuario {
    private final String email;
    private final String nombre;

    public Usuario(String email, String nombre) {
        this.email = email == null ? "" : email.trim();
        this.nombre = nombre == null ? "" : nombre.trim();
    }

    public String getEmail() { return email; }
    public String getNombre() { return nombre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(email.toLowerCase(), usuario.email.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email.toLowerCase());
    }

    @Override
    public String toString() {
        return nombre + " <" + email + ">";
    }
}