/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author pame
 */
public class Usuario {
     private String username;
    private String passwordHash;
    private boolean activo;

    public Usuario(String username, String passwordHash, boolean activo) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.activo = activo;
    }

    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isActivo() { return activo; }
}

