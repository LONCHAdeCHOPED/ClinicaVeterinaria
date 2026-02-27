package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public record Citas(int idCita, LocalDate fecha, LocalTime hora, String motivo, String estado, int idPaciente) {
}
