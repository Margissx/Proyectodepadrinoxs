package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InfanteController implements Initializable {
    @FXML
    private TextField txtDirPath;

    public void exit(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login-View.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void ShowDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Busca La carta");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("Word", "*.docx")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        String fileName ;
        if (selectedFile != null) {
            fileName= selectedFile.getPath();
            txtDirPath.setText(fileName);
            CreateFolderIfNotExist();

        } else {
            System.out.println("Ningún archivo seleccionado.");
        }

    }

    public void CopyFile(){
        Path currentPath = Paths.get("");
        Path destinationFile = Paths.get(currentPath.toAbsolutePath()+"/Cartas");
        System.out.println("cp '"+txtDirPath.getText()+ "' "+destinationFile);
        if(executeCommand("cp '"+txtDirPath.getText()+ "' "+destinationFile) >0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Carta No enviada");
            alert.setHeaderText(null);
            alert.setContentText("No pudimos enviar la carta al tecnico, porfavor verifica la carta");
            alert.showAndWait();
        }else{
            guardarregistrocarta();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Carta enviada");
            alert.setHeaderText(null);
            alert.setContentText("fue enviada al tecnio");
            alert.showAndWait();
        }

    }

    public static  boolean CreateFolderIfNotExist(){
        String folderPath = "./Cartas";
        Path path = Paths.get(folderPath);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                return true;
            }
        } catch (IOException e) {
        }
        return false;
    }
    public static int executeCommand(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", command);

            Process process = processBuilder.start();

            // Leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Esperar a que termine el proceso
            int exitCode = process.waitFor();
            System.out.println("El comando ha terminado. Código de salida: " + exitCode);
            return exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }
        return 1;
    }

    public void guardarregistrocarta(){
        Connection con = database.conMySqlServerDB();
        String sql = "insert into  cartas(id_estado, id_usuarios, hash) values (2,?,?);";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userdata.user_id);
            preparedStatement.setString(2, txtDirPath.getText());
            preparedStatement.execute();


        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
