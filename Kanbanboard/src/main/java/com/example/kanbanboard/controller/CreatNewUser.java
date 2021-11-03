package com.example.kanbanboard.controller;

import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.Main;
import com.example.kanbanboard.model.*;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.JacksonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class CreatNewUser {
    public UserRepository userRepository;
    @FXML
    private TextField newUserAccount;
    @FXML
    private TextField newUserPassword;
    @FXML
    private TextField newUserEmail;
    @FXML
    private TextField newUserPhone;
    @FXML
    private Label alertNewAcc;
    @FXML
    public void add(ActionEvent event) throws IOException {
        String json = FileService.read("package.json");
        userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
        if (newUserAccount.getText().equals("")||newUserPassword.getText().equals("")||
                newUserEmail.getText().equals("")||newUserPhone.getText().equals("")) {
            alertNewAcc.setText("Thông tin đăng ký không hợp lệ");
        } else {
            if (userRepository.getByAccount(newUserAccount.getText())!=null) {
                alertNewAcc.setText("Tài khoản đã tồn tại");
            } else {
                User newUser = new User(newUserAccount.getText(), newUserPassword.getText(), newUserEmail.getText(), newUserPhone.getText());
                newUser.setRole(Role.USER);
                userRepository.add(newUser);
                FileService.write(userRepository, "package.json");
                Stage stage = ChangeScene.getStage(event);
                FXMLLoader loader = ChangeScene.setScene(stage,"login.fxml");
            }
        }
    }
    @FXML
    public void backToLogin(ActionEvent event) throws IOException {
    Stage stage = ChangeScene.getStage(event);
    FXMLLoader loader = ChangeScene.setScene(stage,"login.fxml");
    }

}
