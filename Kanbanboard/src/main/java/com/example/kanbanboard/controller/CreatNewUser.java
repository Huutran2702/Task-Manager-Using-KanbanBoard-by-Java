package com.example.kanbanboard.controller;

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
        System.out.println(matches);
        if (matches) {
            String  email = newUserAccount.getText();
            String passWord = getPass(newUserPassword, newPassValue);
            String enterPassWord=getPass(enterPass,enterPassValue);
            String phone = newUserPhone.getText();
            String json = FileService.read("package.json");
            userRepository = new UserRepository();
            userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
            if (firstName.getText().equals("") || lastName.getText().equals("") || email.equals("") ||
                    passWord.equals("") || enterPassWord.equals("") || phone.equals("")) {
                alertNewAcc.setText("Thông tin đăng ký không hợp lệ");
            } else {
                if (userRepository.getByEmail(email) != null) {
                    alertNewAcc.setText("Tài khoản đã tồn tại");
                } else {
                    if (newUserPassword.getText().equals(enterPass.getText())) {
                        User newUser = new User(name, email, passWord, phone);
                        newUser.setRole(Role.USER);
                        userRepository.add(newUser);
                        FileService.write(userRepository, "package.json");
                        Stage stage = ChangeScene.getStage(event);
                        FXMLLoader loader = ChangeScene.setScene(stage, "login.fxml", "Login!");
                    } else {
                        alertNewAcc.setText("Mật khẩu không giống nhau");
                    }
                }
            }
        } else {
            alertNewAcc.setText("Email đăng ký không đúng định dạng");
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
