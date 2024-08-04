package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String contrasena;

    public Usuario(int id, String nombre, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }
    
    public boolean autenticaUsuario(String contrasenaIngresada) {
        return contrasena.equals(contrasenaIngresada);
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
}
