package com.example.kanbanboard.controller;

import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.Main;
import com.example.kanbanboard.model.User;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.JacksonParser;
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

import java.net.URL;

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
        String json = FileService.read("package.json");
        userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json,User.class);
       User user = userRepository.getByAccount(accText.getText());
            if (user == null) {
                alert.setText("Tài khoản bạn nhập không đúng");
            } else {
                if (user.getPassword().equals(passText.getText())) {
                    Stage stage = ChangeScene.getStage(event);
//                    FXMLLoader loader = new FXMLLoader();
//                    loader.setLocation(Main.class.getResource("show-detail.fxml"));
//                    Parent submitParent = loader.load();
//                    Scene scene = new Scene(submitParent);
//                    stage.setScene(scene);
//                    ShowDetail controller = loader.getController();
                    ShowDetail controller = ChangeScene.setScene(stage,"show-detail.fxml").getController();
                    controller.setUser(user);
                    controller.showUser(user);

                } else {
                    alert.setText("Mật khẩu bạn nhập không đúng");
                }
            }
    }
    @FXML
    public void create(ActionEvent event) throws Exception {
        Stage stage = ChangeScene.getStage(event);
        FXMLLoader loader = ChangeScene.setScene(stage,"create-new-account.fxml");
    }




}
