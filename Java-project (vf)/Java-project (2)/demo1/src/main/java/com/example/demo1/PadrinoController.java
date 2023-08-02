package com.example.demo1;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadErrorException;
import com.dropbox.core.v2.files.FileMetadata;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PadrinoController implements Initializable {
    @FXML
    TableView tblData;
    String  carta = "";
    Connection con = null;
    List<cartas> Cartas = null;
    private ObservableList<ObservableList> data;
    private static final String ACCESS_TOKEN = "sl.BjXoIRD_MUbIH-LXkcKV7D13nPIo8LXDhb82UGWEAtp0bBZkwV4QTiaZbZTzrYZrUzr8ITsd5hOULnxVwfqwU3_WcG1EtLgHC8u5a47_1o7YhjouQISS9pSJIPSvWxxFsG9QYIBNwuqqZnaE33U6jgE";
    public PadrinoController(){
        con = database.conMySqlServerDB();
        Cartas = new ArrayList<>();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadDataCartas();
        tblData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ObservableList<String> c = ( ObservableList<String>) newValue;
                carta = c.toArray()[3].toString();
            }
        });
    }
    public void descargarcarta() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Proceso");
        alert.setHeaderText(null);
        alert.setContentText("Se esta descargando la carta");
        alert.showAndWait();
        CreateFolderIfNotExist();
        if(carta.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor seleccione antes la carta a leer");
            alert.showAndWait();
            return;
        }
        String delimitador = "\\\\";
        Path currentPath = Paths.get("");
        Path destinationFile = Paths.get(currentPath.toAbsolutePath()+"/CartasDescargadas");
        downloadFile("/"+ carta.split(delimitador)[carta.split(delimitador).length-1],destinationFile.toString());
        UpdateStadoCarta();
        OpenFile();
    }

    public void UpdateStadoCarta(){
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            // Crear un objeto Statement para ejecutar consultas SQL
            String query = "UPDATE cartas SET id_estado = 3 WHERE hash=?;";

            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, carta);
            preparedStatement.execute();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Carta Leida");
            alert.setHeaderText(null);
            alert.setContentText("La carta fue actualzada");
            alert.showAndWait();

        }catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No pudimos actualizar la informacion de la carta");
            alert.showAndWait();
            System.out.println(ex.getMessage());
        }
    }
    public static  boolean CreateFolderIfNotExist(){
        String folderPath = "./CartasDescargadas";
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
    public  void downloadFile(String dropboxFilePath, String localFilePath) throws Exception {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("Padrino").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        try (OutputStream out = new FileOutputStream(localFilePath+"/"+dropboxFilePath)) {
            FileMetadata metadata = client.files().downloadBuilder(dropboxFilePath)
                    .download(out);
        } catch (DownloadErrorException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public  void LoadDataCartas(){
        ResultSet resultSet = null;

        try{
            tblData.getColumns().clear();
            // Crear un objeto Statement para ejecutar consultas SQL
            Statement statement = con.createStatement();

            // Ejecutar la consulta SQL para obtener los roles
            String query = "select * from cartas where id_estado = 1;";
            resultSet = statement.executeQuery(query);
            fetColumnList(resultSet);
            fetRowList(resultSet);


        }catch (SQLException ex){

        }
    }

    public void OpenFile(){
        String delimitador = "\\\\";
        Path currentPath = Paths.get("");
        File file = Paths.get(currentPath.toAbsolutePath()+"/CartasDescargadas"+"/"+ carta.split(delimitador)[carta.split(delimitador).length-1]).toFile();
        try {
            java.awt.Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
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
