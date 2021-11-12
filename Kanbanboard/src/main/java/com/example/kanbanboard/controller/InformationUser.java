package com.example.kanbanboard.controller;

import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.model.User;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.service.JacksonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InformationUser {
    private User user;

    @FXML
    private Button btn_change_name;

    @FXML
    private Button btn_change_password;

    @FXML
    private Button btn_change_phone;

    @FXML
    private Button btn_update_name;

    @FXML
    private Button btn_update_password;

    @FXML
    private Button btn_update_phone;

    @FXML
    private Label label_enterpassword;

    @FXML
    private Label textfield_alert;

    @FXML
    private TextField textfield_enterpassword;

    @FXML
    private TextField textfield_name;

    @FXML
    private TextField textfield_password;

    @FXML
    private TextField textfield_phone;

    @FXML
    private Label user_email;

    @FXML
    private Label user_name;

    @FXML
    private Label user_password;

    @FXML
    private Label user_phone;

    public void show(User user) {
        user_email.setText(user.getEmail());
        user_name.setText(user.getName());
        user_phone.setText(user.getPhone());
        user_password.setText(user.getPassword());
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Stage stage = ChangeScene.getStage(event);
        stage.setX(80);
        stage.setY(60);
        KanbanBoard controller = ChangeScene.setScene(stage, "show-detail.fxml", "Kanban Board").getController();
        controller.setUser(user);
        controller.showUser(user);

    }

    @FXML
    void changeName(ActionEvent event) {
        setDisable(btn_change_name, true);
        setDisable(user_name, true);
        setDisable(textfield_name, false);
        setDisable(btn_update_name, false);
        textfield_name.setText(user_name.getText());
    }

    @FXML
    void changePassword(ActionEvent event) {
        setDisable(btn_change_password, true);
        setDisable(user_password, true);
        setDisable(textfield_password, false);
        setDisable(textfield_enterpassword,false);
        setDisable(label_enterpassword,false);
        setDisable(btn_update_password, false);
        textfield_password.setText(user_password.getText());
    }

    @FXML
    void changePhone(ActionEvent event) {
        setDisable(btn_change_phone, true);
        setDisable(user_phone, true);
        setDisable(textfield_phone, false);
        setDisable(btn_update_phone, false);
        textfield_phone.setText(user_phone.getText());
    }

    @FXML
    void updateName(ActionEvent event) throws IOException {
        String name = textfield_name.getText();
        if (name.equals("")) {
            textfield_alert.setText("Name invalid");
        } else {
            user.setName(name);
            setDisable(btn_change_name, false);
            setDisable(user_name, false);
            setDisable(textfield_name, true);
            setDisable(btn_update_name, true);
            saveFile();
            show(user);
            textfield_alert.setText("");
        }
    }

    @FXML
    void updatePassword(ActionEvent event) throws IOException {
        String password = textfield_password.getText();
        if (textfield_enterpassword.getText().equals("")) {
            textfield_alert.setText("Enter password invalid");
        } else if (textfield_password.getText().equals("")){
            textfield_alert.setText("Password invalid");
        } else if (textfield_password.getText().equals(textfield_enterpassword.getText())) {
            user.setPassword(password);
            setDisable(btn_change_password, false);
            setDisable(user_password, false);
            setDisable(textfield_password, true);
            setDisable(textfield_enterpassword,true);
            setDisable(label_enterpassword,true);
            setDisable(btn_update_password, true);
            saveFile();
            show(user);
            textfield_alert.setText("");
        } else {
            textfield_alert.setText("Enter password incorrect");
        }
    }

    @FXML
    void updatePhone(ActionEvent event) throws IOException {
        String phone = textfield_phone.getText();
        if (phone.equals("")) {
            textfield_alert.setText("Phone invalid");
        } else {
            user.setPhone(phone);
            setDisable(btn_change_phone, false);
            setDisable(user_phone, false);
            setDisable(textfield_phone, true);
            setDisable(btn_update_phone, true);
            saveFile();
            show(user);
            textfield_alert.setText("");
        }
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDisable(Node node, boolean b) {
        node.disableProperty().setValue(b);
        if (b) {
            node.setOpacity(0);
        } else {
            node.setOpacity(1);
        }
    }

    private void saveFile() throws IOException {
        FileService.read("package.json");
        String json = FileService.read("package.json");
        UserRepository userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
        for (User oldUser : userRepository.userList) {
            if (oldUser.getEmail().equals(user.getEmail())) {
                User.transferFields(oldUser, user);
                break;
            }
        }
        FileService.write(userRepository, "package.json");
    }
}
