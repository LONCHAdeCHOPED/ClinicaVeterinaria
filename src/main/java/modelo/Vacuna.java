package modelo;

import java.time.LocalDate;

public record Vacuna(int idVacuna, String nombre, LocalDate fecha, LocalDate fechaProxima, Paciente paciente) {
}
