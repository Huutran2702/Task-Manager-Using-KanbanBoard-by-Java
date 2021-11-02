package com.example.kanbanboard.controller;

import com.example.kanbanboard.FileSevice;
import com.example.kanbanboard.Main;
import com.example.kanbanboard.model.Role;
import com.example.kanbanboard.model.User;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.JacksonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private void add(ActionEvent event) throws IOException {
        User newUser = new User(newUserAccount.getText(), newUserPassword.getText(), newUserEmail.getText(), newUserPhone.getText());
        newUser.setRole(Role.USER);
        Stage stage = Login.getStage(event);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("login.fxml"));
        Parent submitParent = loader.load();
        String json = FileSevice.read("package.json");
        userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
        userRepository.add(newUser);
        FileSevice.write(userRepository, "package.json");
        Scene scene = new Scene(submitParent);
        stage.setScene(scene);
    }

}
