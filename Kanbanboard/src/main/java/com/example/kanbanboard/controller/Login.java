package com.example.kanbanboard.controller;

import com.example.kanbanboard.FileSevice;
import com.example.kanbanboard.Main;
import com.example.kanbanboard.model.User;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.JacksonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public UserRepository userRepository;

    @FXML
    private TextField accText;
    @FXML
    private TextField passText;
    @FXML
    private Label alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void submit(ActionEvent event) throws Exception {
        String json = FileSevice.read("package.json");
        userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json,User.class);
       User user = userRepository.getByAccount(accText.getText());
            if (user == null) {
                alert.setText("Tai khoan ban nhap khong dung");
            } else {
                if (user.getPassword().equals(passText.getText())) {
                    Stage stage = getStage(event);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("show.fxml"));
                    Parent submitParent = loader.load();
//                    HelloController controller = loader.getController();
//                    controller.showUser(user);
                    Scene scene = new Scene(submitParent);
                    stage.setScene(scene);
                } else {
                    alert.setText("Mat khau khong dung");
                }
            }
    }
    @FXML
    public void create(ActionEvent event) throws Exception {
        Stage stage = getStage(event);
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(Main.class.getResource("create-new-account.fxml"));
        Parent createNewAccountParent = loader1.load();
        Scene scene = new Scene(createNewAccountParent);
        stage.setScene(scene);
    }

    public static Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }


}
