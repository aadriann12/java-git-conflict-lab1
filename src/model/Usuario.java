package model;

import java.util.Objects;

public class Usuario {
    private int email;
    private String nombre;

    public Usuario(int email, String nombre) {
        this.email = email;
        this.nombre = nombre;
    }

    public int getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }
}