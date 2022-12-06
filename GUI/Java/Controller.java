package com.application;

import com.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;


public class Controller {
    private boolean chooseFile;
    private File selectedFile;
    private String flag;

    @FXML
    private Button choosebutton;

    @FXML
    private TextArea textArea;

    @FXML
    private Label fileLabel;

    @FXML
    void actionButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        fileLabel.setText("File: " + selectedFile.getName().toString());
        chooseFile = true;
        flag = "";
    }

    @FXML
    void actionConvert(ActionEvent event) {
        if(!chooseFile){
            fileLabel.setText("Choose file!");
            return;
        }

        Client clientSend = new Client();
        clientSend.connectSocketServer(9527);

        String flag = "";
        clientSend.sendFile(selectedFile, flag);
        clientSend.clientStop();

        Client clientGet = new Client();
        clientGet.connectSocketServer(9528);
        System.out.println("Please wait");
        clientGet.getFile();

        System.out.println("Done!");
        clientGet.clientStop();
    }


    @FXML
    void actionClear(ActionEvent event) {
        flag = "";
        textArea.setText("");
    }


    ////transform file handlers:
    @FXML
    void actionCompress(ActionEvent event) {
        if(!chooseFile){
            fileLabel.setText("Choose file!");
            return;
        }
        flag += "Cc";
        textArea.setText(textArea.getText() + "Compress + \n");
    }

    @FXML
    void actionDecode(ActionEvent event) {
        if(!chooseFile){
            fileLabel.setText("Choose file!");
            return;
        }
        flag += "Ed";
        textArea.setText(textArea.getText() + "Decode + \n");
    }

    @FXML
    void actionDecompress(ActionEvent event) {
        if(!chooseFile){
            fileLabel.setText("Choose file!");
            return;
        }
        flag += "Cd";
        textArea.setText(textArea.getText() + "Decompress + \n");
    }

    @FXML
    void actionEncode(ActionEvent event) {
        if(!chooseFile){
            fileLabel.setText("Choose file!");
            return;
        }
        flag += "Ee";
        textArea.setText(textArea.getText() + "Encode + \n");
    }

    @FXML
    void actionUnzip(ActionEvent event) {
        if (!chooseFile) {
            fileLabel.setText("Choose file!");
            return;
        }

        if (!getExtension(selectedFile.getName()).equals(".zip") || !flag.contains("Zz")) {
            textArea.setText(textArea.getText() + "File can not be unzipped!!! + \n");
        }else{
            flag += "Zu";
            textArea.setText(textArea.getText() + "Unzip + \n");
        }
    }

    @FXML
    void actionZip(ActionEvent event) {
        if(!chooseFile){
            fileLabel.setText("Choose file!");
            return;
        }
        if(getExtension(selectedFile.getName()).equals(".zip") || flag.contains("Zz")){
            //flag += "Zu";
            textArea.setText(textArea.getText() + "File already zipped!!! + \n");
        }else{
            flag += "Zz";
            textArea.setText(textArea.getText() + "Zip + \n");
        }
    }


    String getExtension(String fileName){
        String extension = null;
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

}