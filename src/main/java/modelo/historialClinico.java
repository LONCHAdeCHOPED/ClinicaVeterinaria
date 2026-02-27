package modelo;

public record historialClinico(int idHistorial, int fecha, String sintomas, String diagnostico, String tratamiento, String observiaciones, int idPaciente) {
}
