package modelo;

public record Usuario(int idUsuario, String nombreUsuario, String rol) {

    // Constructor vacío (sin argumentos)
    public Usuario() {
        // Llamamos al constructor principal con valores por defecto
        this(0, "", "" );

    }

}

