package controllers;

import application.GestionClase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller4 {

    @FXML
    private TextField txtNombre, txtApellidos, txtEdad;
    @FXML
    private TableView<GestionClase> tablaPersonas;
    @FXML
    private TableColumn<GestionClase, String> columnaNombre, columnaApellidos;
    @FXML
    private TableColumn<GestionClase, Integer> columnaEdad;
    @FXML
    private Button btnModificar, btnEliminar;

    private final ObservableList<GestionClase> listaPersonas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columnaApellidos.setCellValueFactory(cellData -> cellData.getValue().apellidosProperty());
        columnaEdad.setCellValueFactory(cellData -> cellData.getValue().edadProperty().asObject());

        tablaPersonas.setItems(listaPersonas);

        // Deshabilitar botones si no hay selección
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);

        tablaPersonas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtNombre.setText(newSelection.getNombre());
                txtApellidos.setText(newSelection.getApellidos());
                txtEdad.setText(String.valueOf(newSelection.getEdad()));

                btnModificar.setDisable(false);
                btnEliminar.setDisable(false);
            } else {
                limpiarCampos();
                btnModificar.setDisable(true);
                btnEliminar.setDisable(true);
            }
        });
    }

    @FXML
    private void agregarPersona() {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String edadStr = txtEdad.getText();

        if (!nombre.isEmpty() && !apellidos.isEmpty() && !edadStr.isEmpty()) {
            try {
                int edad = Integer.parseInt(edadStr);
                listaPersonas.add(new GestionClase(nombre, apellidos, edad));
                limpiarCampos();
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Edad inválida", "Por favor, ingrese un número válido para la edad.");
            }
        } else {
            mostrarAlerta("Error", "Campos vacíos", "Todos los campos deben estar llenos.");
        }
    }

    @FXML
    private void modificarPersona() {
        GestionClase seleccionada = tablaPersonas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                seleccionada.setNombre(txtNombre.getText());
                seleccionada.setApellidos(txtApellidos.getText());
                seleccionada.setEdad(Integer.parseInt(txtEdad.getText()));

                tablaPersonas.refresh();
                limpiarCampos();
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Edad inválida", "Ingrese un número válido para la edad.");
            }
        }
    }

    @FXML
    private void eliminarPersona() {
        GestionClase seleccionada = tablaPersonas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            listaPersonas.remove(seleccionada);
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellidos.clear();
        txtEdad.clear();
    }

    private void mostrarAlerta(String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
