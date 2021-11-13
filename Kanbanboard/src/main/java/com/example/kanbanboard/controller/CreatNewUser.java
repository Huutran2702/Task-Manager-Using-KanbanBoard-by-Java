package com.example.kanbanboard.controller;

import com.example.kanbanboard.Main;
import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.model.*;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.JacksonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class CreatNewUser {
    public UserRepository userRepository;
    @FXML
    public Button enterPassBtn;
    @FXML
    public Button passBtn;
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;
    @FXML
    private TextField newUserAccount;
    @FXML
    private TextField newUserPassword;
    @FXML
    private PasswordField newPassValue;
    @FXML
    private TextField enterPass;
    @FXML
    private PasswordField enterPassValue;
    @FXML
    private TextField newUserPhone;
    @FXML
    private Label alertNewAcc;


    @FXML
    public void add(ActionEvent event) throws IOException {
        String name = firstName.getText() + " " + lastName.getText();
        boolean matches = newUserAccount.getText().matches("[A-Z,a-z0-9]+@+[a-z,0-9]+.+[a-z]");
        if (matches) {
            String  email = newUserAccount.getText();
            String passWord = getPass(newUserPassword, newPassValue);
            String enterPassWord=getPass(enterPass,enterPassValue);
            String phone = newUserPhone.getText();
            String json = FileService.read("package.json");
            userRepository = new UserRepository();
            userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
            if (firstName.getText().equals("")) {
                alertNewAcc.setText("First name invalid");
            } else if (lastName.getText().equals("")) {
                alertNewAcc.setText("Last name invalid");
            } else if (email.equals("")) {
                alertNewAcc.setText("Email invalid");
            } else if (passWord.equals("")) {
                alertNewAcc.setText("Password invalid");
            } else if (enterPassWord.equals("")) {
                alertNewAcc.setText("Enter password invalid");
            } else if (phone.equals("")){
                alertNewAcc.setText("Phone invalid");
            } else if (userRepository.getByEmail(email)!=null){
                alertNewAcc.setText("Account already exists");
            } else {
                if (newUserPassword.getText().equals(enterPass.getText())) {
                    User newUser = new User(name, email, passWord, phone);
                    newUser.setRole(Role.USER);
                    userRepository.add(newUser);
                    FileService.write(userRepository, "package.json");
                    Stage stage = ChangeScene.getStage(event);
                    FXMLLoader loader = ChangeScene.setScene(stage, "login.fxml", "Login!");

                } else {
                    alertNewAcc.setText("Enter password does not match");
                }
            }
        } else {
            alertNewAcc.setText("The registered email is not in the correct format");
        }


    }

    private String getPass(TextField newUserPassword, PasswordField newPassValue) {
        String passWord;
        if (newUserPassword.disableProperty().getValue()) {
            passWord = newUserPassword.getText();
        } else {
            passWord = newPassValue.getText();
        }
        return passWord;
    }

    @FXML
    public void backToLogin(ActionEvent event) throws IOException {
        Stage stage = ChangeScene.getStage(event);
        FXMLLoader loader = ChangeScene.setScene(stage, "login.fxml", "Login!");
    }

    @FXML
    void showEnterPass(ActionEvent event) {
        if (enterPassValue.disableProperty().getValue()) {
            enterPassValue.setText(enterPass.getText());
            enterPass.disableProperty().setValue(true);
            enterPass.setText("");
            enterPassValue.disableProperty().setValue(false);
            enterPassBtn.setText("Show");

        } else {
            enterPass.setText(enterPassValue.getText());
            enterPassValue.disableProperty().setValue(true);
            enterPassValue.setText("");
            enterPass.disableProperty().setValue(false);
            enterPassBtn.setText("Hide");
        }
    }

    @FXML
    void showPass(ActionEvent event) {
        if (newPassValue.disableProperty().getValue()) {
            newPassValue.setText(newUserPassword.getText());
            newUserPassword.disableProperty().setValue(true);
            newUserPassword.setText("");
            newPassValue.disableProperty().setValue(false);
            passBtn.setText("Show");

        } else {
            newUserPassword.setText(newPassValue.getText());
            newPassValue.disableProperty().setValue(true);
            newPassValue.setText("");
            newUserPassword.disableProperty().setValue(false);
            passBtn.setText("Hide");
        }
    }
}
