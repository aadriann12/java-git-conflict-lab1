package util;

import model.Usuario;

public class Validador {

    public static boolean validarUsuario(Usuario usuario) {
        return usuario != null && usuario.getNombre() != null;
    }
}