package modelo;

public record Factura(int idFactura, int fecha, String concepto, double importeBase, double iva, double total, int idPaciente) {
}
