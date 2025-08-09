/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dominio.Usuario;
import java.util.ArrayList;
import utilidades.HashUtil;

/**
 *
 * @author pame
 */
public class AuthService {
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public AuthService() {
        usuarios.add(new Usuario("admin", HashUtil.sha256("1234"), true));
        usuarios.add(new Usuario("user", HashUtil.sha256("1234"), true));
        usuarios.add(new Usuario("cajero", HashUtil.sha256("abcd"), true));
    }

    public boolean login(String username, String password) {
        String hash = HashUtil.sha256(password);
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username) && u.getPasswordHash().equals(hash) && u.isActivo()) {
                return true;
            }
        }
        return false;
    }
} 
