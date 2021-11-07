package com.example.kanbanboard.controller;

import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.model.LoginStatus;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.model.User;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.JacksonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class Login implements Initializable {
    public UserRepository userRepository;
    public javafx.scene.control.PasswordField passValue;
    @FXML
    private Button displayBtn;
    @FXML
    private TextField accText;
    @FXML
    private TextField passText;
    @FXML
    private CheckBox login_status;

    @FXML
    private Label alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String oldUser = FileService.read("save.json");
            User user = JacksonParser.INSTANCE.toObject(oldUser,User.class);
            if(user.getLoginStatus().equals(LoginStatus.UNLOGOUT)) {
                accText.setText(user.getEmail());
                passValue.setText(user.getPassword());
            } else {
                accText.setText(user.getEmail());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void submit(ActionEvent event) throws Exception {
        String json = FileService.read("package.json");
        userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json,User.class);
       User user = userRepository.getByEmail(accText.getText());
            if (user == null) {
                alert.setText("Tài khoản bạn nhập không đúng");
            } else {
                if (user.getPassword().equals(passValue.getText())) {
                    Stage stage = ChangeScene.getStage(event);
                    ShowDetail controller = ChangeScene.setScene(stage,"show-detail.fxml","KanbanBoard").getController();
                    controller.setUser(user);
                    controller.showUser(user);
                    if (login_status.isSelected()) {
                        user.setLoginStatus(LoginStatus.UNLOGOUT);
                    } else {
                        user.setLoginStatus(LoginStatus.LOGOUT);
                    }
                    FileService.writeAccountLogout(user, "save.json");
                } else {
                    alert.setText("Mật khẩu bạn nhập không đúng");
                }
            }
    }
    @FXML
    public void create(ActionEvent event) throws Exception {
        Stage stage = ChangeScene.getStage(event);
        FXMLLoader loader = ChangeScene.setScene(stage,"create-new-user.fxml","Create New User");
    }

    @FXML
    void showPass(ActionEvent event) {
        if (passValue.disableProperty().getValue()) {
            passValue.setText(passText.getText());
            passText.disableProperty().setValue(true);
            passText.setText("");
            passValue.disableProperty().setValue(false);
            displayBtn.setText("Show");

        } else {
            passText.setText(passValue.getText());
            passValue.disableProperty().setValue(true);
            passText.disableProperty().setValue(false);
            displayBtn.setText("Hide");
        }
    }


}
