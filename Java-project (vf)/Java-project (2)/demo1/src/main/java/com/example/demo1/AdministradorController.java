package com.example.demo1;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdministradorController implements Initializable {
    @FXML
    TextField txtNombre,txtCorreo,txtPassword;
    @FXML
    ComboBox<String> txtRol;
    @FXML
    TableView tblData;
    Connection con = null;
    List<Rol> Roles = null;
    List<cartas> Cartas = null;
    private ObservableList<ObservableList> data;

    public AdministradorController(){
        con = database.conMySqlServerDB();
        Roles = new ArrayList<>();
        Cartas = new ArrayList<>();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadRol();
    }

    public void CrearUsuario(){
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            // Crear un objeto Statement para ejecutar consultas SQL
            String query = "INSERT INTO usuario (id_rol, nombre, correo, password) VALUES (?,?, ?, ?);";

            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, buscarrol(txtRol.getValue()));
            preparedStatement.setString(2, txtNombre.getText());
            preparedStatement.setString(3, txtCorreo.getText());
            preparedStatement.setString(4, txtPassword.getText());
            preparedStatement.execute();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuario creado");
            alert.setHeaderText(null);
            alert.setContentText("El ususario se ingreso de manera exitosa");
            alert.showAndWait();
        }catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No pudimos ingresar el ususario valida tus datos");
            alert.showAndWait();
            System.out.println(ex.getMessage());
        }
    }

    private int buscarrol(String value) {
        if(value.equals("Administrador")){
            return 1;
        }
        if(value.equals("Tecnico")){
            return 2;
        }
        if(value.equals("Padrino")){
            return 3;
        }
        if(value.equals("Infante")){
            return 4;
        }
        return 0;
    }

    public void LoadRol(){
        ResultSet resultSet = null;

        try{
            // Crear un objeto Statement para ejecutar consultas SQL
            Statement statement = con.createStatement();

            // Ejecutar la consulta SQL para obtener los roles
            String query = "SELECT * FROM rol;";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Rol rol= new Rol();
                rol.id = resultSet.getInt("id");
                rol.Name = resultSet.getString("nombre");
                Roles.add(rol);
            }
            ObservableList<String> RolesName = FXCollections.observableArrayList();
            for (Rol rol : Roles) {
                RolesName.add(rol.Name);
                System.out.println(rol.Name);
            }
            System.out.println(txtRol.getId());
            txtRol.setItems(RolesName);
        }catch (SQLException ex){

        }

    }

    public  void LoadDataCartas(){
        ResultSet resultSet = null;

        try{
            tblData.getColumns().clear();
            // Crear un objeto Statement para ejecutar consultas SQL
            Statement statement = con.createStatement();

            // Ejecutar la consulta SQL para obtener los roles
            String query = "select * from cartas;";
            resultSet = statement.executeQuery(query);
            fetColumnList(resultSet);
            fetRowList(resultSet);
            while (resultSet.next()) {
                cartas carta= new cartas();
                carta.setId(resultSet.getInt("id"));
                carta.setHash(resultSet.getString("hash"));
                carta.setIdEstado(resultSet.getInt("id_estado"));
                carta.setIdUsuario(resultSet.getInt("id_usuarios"));
                Cartas.add(carta);
            }

        }catch (SQLException ex){

        }
    }
    public  void LoadDataUsuarios(){
        ResultSet resultSet = null;

        try{
            // Crear un objeto Statement para ejecutar consultas SQL
            Statement statement = con.createStatement();
            tblData.getColumns().clear();
            // Ejecutar la consulta SQL para obtener los roles
            String query = "select * from usuario;";
            resultSet = statement.executeQuery(query);
            fetColumnList(resultSet);
            fetRowList(resultSet);
            while (resultSet.next()) {
                cartas carta= new cartas();
                carta.setId(resultSet.getInt("id"));
                carta.setHash(resultSet.getString("hash"));
                carta.setIdEstado(resultSet.getInt("id_estado")) ;
                carta.setIdUsuario(resultSet.getInt("id_usuarios"));
                Cartas.add(carta);
            }

        }catch (SQLException ex){

        }
    }

    private void fetColumnList(ResultSet rs) {

        try {

            //SQL FOR SELECTING ALL OF CUSTOMER
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblData.getColumns().removeAll(col);
                tblData.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");

            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    private void fetRowList(ResultSet rs) {

        data = FXCollections.observableArrayList();
        try {

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            tblData.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void exit(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login-View.fxml")));
        stage.setScene(scene);
        stage.show();
    }

}
