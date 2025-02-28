package controllers;

import application.GestionClase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerClase {

    @FXML
    private TextField txtNombre, txtApellidos, txtEdad;
    @FXML
    private Button btnGuardar, btnSalir;

    private ObservableList<GestionClase> listaPersonas;

    public void setListaPersonas(ObservableList<GestionClase> lista) {
        this.listaPersonas = lista;
    }

    @FXML
    private void guardarPersona() {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String edadStr = txtEdad.getText();

        if (!nombre.isEmpty() && !apellidos.isEmpty() && !edadStr.isEmpty()) {
            try {
                int edad = Integer.parseInt(edadStr);
                listaPersonas.add(new GestionClase(nombre, apellidos, edad));

                cerrarVentana();
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Edad inválida", "Por favor, ingrese un número válido.");
            }
        } else {
            mostrarAlerta("Error", "Campos vacíos", "Todos los campos deben estar llenos.");
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
