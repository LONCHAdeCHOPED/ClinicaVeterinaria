package vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import modelo.App;
import javafx.scene.control.*;

import java.io.IOException;

public class IniciarSesionController {
    @FXML
    private TextField idUsr;
    @FXML
    private TextField idPasswd;
    @FXML
    private Button idButton;

    @FXML
    public void loginButton() {
        String usuario = idUsr.getText();
        String password = idPasswd.getText();

        if (usuario.isEmpty() || password.isEmpty()){
            mostrarAlerta("Error", "los campos estan vacios");
            return;
        }
        boolean esValido = App.validarUsuario(usuario, password);

        if (esValido){
            System.out.println("Sesión iniciada");
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pantallaInicial.fxml"));
                Parent root = loader.load();

                Stage stagePrincipal = new Stage();
                stagePrincipal.setTitle("Sistema de Gestión - Clínica Veterinaria");
                stagePrincipal.setScene(new Scene(root));
                stagePrincipal.show();

                idButton.getScene().getWindow().hide();

            } catch (IOException e) {
                System.err.println("❌ Error al cargar la pantalla inicial: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Error", "Usuario o contraseña no válidos");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}
