package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label lblErrors;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnSignin;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public  LoginController(){
        con = database.conMySqlServerDB();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Funcion encargada del boton login
     private String logIn() {
        String status = "Success";
         String email = txtUsername.getText();
         String password = txtPassword.getText();
        if(email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("ingrese datos validos");
            alert.showAndWait();
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM usuario Where nombre = ? and password = ?;";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("ingrese datos validos");
                    alert.showAndWait();
                    status = "Error";
                }else{
                    status = resultSet.getString("id_rol");
                    userdata.user_id = status;
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }
    @FXML


    public void handleButtonAction(MouseEvent event) {

            //login here
                try {
                    if (logIn().equals("1")) {
                        
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Administrador-View.fxml")));
                        stage.setScene(scene);
                        stage.show();
                    } else if (logIn().equals("2")) {
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Tecnico-View.fxml")));
                        stage.setScene(scene);
                        stage.show();
                    }else if (logIn().equals("3")) {
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Padrino-View.fxml")));
                        stage.setScene(scene);
                        stage.show();
                    }else if (logIn().equals("4")) {
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Infante-View.fxml")));
                        stage.setScene(scene);
                        stage.show();
                    }


                } catch (IOException ex) {

                    System.err.println(ex.getMessage());
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }

    }


}
