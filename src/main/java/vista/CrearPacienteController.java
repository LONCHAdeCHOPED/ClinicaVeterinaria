package vista;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.App;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrearPacienteController {

    @FXML private TextField nombreText;
    @FXML private TextField especieText;
    @FXML private TextField edadText;
    @FXML private TextField propietarioText;

    @FXML
    public void guardarPaciente() {
        String sql = "INSERT INTO pacientestest (nombre, especie, edad, propietario) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = App.con.prepareStatement(sql)) {
            ps.setString(1, nombreText.getText());
            ps.setString(2, especieText.getText());
            ps.setInt(3, Integer.parseInt(edadText.getText()));
            ps.setString(4, propietarioText.getText());

            ps.executeUpdate();
            System.out.println("✅ Paciente guardado");

            // Cerrar la ventana tras guardar
            Stage stage = (Stage) nombreText.getScene().getWindow();
            stage.close();

        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
}