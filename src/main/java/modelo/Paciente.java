package modelo;

public record Paciente(int id, String nombre, String especie, int edad, String propietario) {
    @Override
    public String toString() {
        return "ID --> " + id + " | Paciente: " + nombre + " | Especie: " + especie + " | Edad: " + edad + " | Propietario: " + propietario;
    }
}
