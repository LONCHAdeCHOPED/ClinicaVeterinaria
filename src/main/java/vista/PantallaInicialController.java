package vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.App;
import modelo.Paciente;
import java.sql.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PantallaInicialController {

    @FXML private TableView<Paciente> tablaPrincipal; // Pon este fx:id a tu TableView en SceneBuilder
    @FXML private TableColumn<Paciente, String> nombreTable;
    @FXML private TableColumn<Paciente, String> especieTable;
    @FXML private TableColumn<Paciente, Integer> edadTable;
    @FXML private TableColumn<Paciente, String> propietarioTable;


    private ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nombreTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        especieTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().especie()));
        edadTable.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().edad()).asObject());
        propietarioTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().propietario()));

        cargarDatosDeBaseDeDatos();
    }

    @FXML
    public void abrirVentanaCrear() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/crearNuevoPaciente.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Registrar Nuevo Paciente");

            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            stage.setScene(new javafx.scene.Scene(root));

            stage.showAndWait();

            cargarDatosDeBaseDeDatos();

        } catch (java.io.IOException e) {
            System.err.println("❌ No se pudo abrir la ventana de creación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarDatosDeBaseDeDatos() {
        listaPacientes.clear();

        String sql = "SELECT id, nombre, especie, edad, propietario FROM pacientestest";

        try {
            Connection con = App.con;
            if (con == null || con.isClosed()) App.testConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                listaPacientes.add(new Paciente(
                        rs.getInt("id"),             // <--- El ID que se rellena solo en la DB
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getInt("edad"),
                        rs.getString("propietario")
                ));
            }

            tablaPrincipal.setItems(listaPacientes);

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}