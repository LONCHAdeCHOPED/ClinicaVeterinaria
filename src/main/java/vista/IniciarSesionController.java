package vista;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import modelo.App;
import javafx.scene.control.*;

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
            idButton.getScene().getWindow().hide();
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
