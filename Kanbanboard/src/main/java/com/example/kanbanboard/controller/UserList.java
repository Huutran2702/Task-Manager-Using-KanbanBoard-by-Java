package com.example.kanbanboard.controller;

import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.model.Role;
import com.example.kanbanboard.model.User;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.service.JacksonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class UserList {
    private User admin;
private UserRepository userRepository;
    @FXML
    private ComboBox<Role> comboBox_role ;
    @FXML
    private TableColumn<User, String> email_col;

    @FXML
    private TableColumn<User, String> name_col;

    @FXML
    private TableColumn<User, String> phone_col;

    @FXML
    private TableColumn<User, String> role_col;

    @FXML
    private TableView<User> table;

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

public void show() {
    ObservableList<User> users = FXCollections.observableArrayList();
    try {
        FileService.read("package.json");
    } catch (IOException e) {
        e.printStackTrace();
    }
    String json = null;
    try {
        json = FileService.read("package.json");
    } catch (IOException e) {
        e.printStackTrace();
    }
    userRepository = new UserRepository();
    userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
    for (User user: userRepository.userList
    ) {
        users.add(user);
    }
    name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
    email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
    phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
    role_col.setCellValueFactory(new PropertyValueFactory<>("role"));
    table.setItems(users);
    setComboBoxRole();

}

    private void setComboBoxRole() {
        ObservableList<Role> roles = FXCollections.observableArrayList();
        roles.addAll(Role.USER,Role.ADMIN);
        comboBox_role.setItems(roles);
    }

    @FXML
    void selectRole(ActionEvent event) throws IOException {
        int count = 0;
        for (User user: userRepository.userList) {
            if (user.getRole().equals(Role.ADMIN)) {
                count+= 1;
            }
        }

        User selected = table.getSelectionModel().getSelectedItem();
        if (selected.getRole().equals(Role.USER)) {
            userRepository.getByEmail(selected.getEmail()).setRole(comboBox_role.getValue());
            FileService.write(userRepository, "package.json");
            comboBox_role.setPromptText("Set Role");
            show();
        } else if (selected.getRole().equals(Role.ADMIN)) {
            if (count>1) {
                userRepository.getByEmail(selected.getEmail()).setRole(comboBox_role.getValue());
                FileService.write(userRepository, "package.json");
                comboBox_role.setPromptText("Set Role");
                show();
            } else {
                Alert alertInfor = new Alert(Alert.AlertType.INFORMATION);
                alertInfor.setHeaderText(null);
                alertInfor.setContentText("Must have at least 1 admin");
                alertInfor.showAndWait();
            }
        }

    }
    @FXML
    void back(ActionEvent event) throws IOException {
        Stage stage = ChangeScene.getStage(event);
        stage.setX(80);
        stage.setY(60);
        KanbanBoard controller = ChangeScene.setScene(stage,"show-detail.fxml","Kanban Board").getController();
        controller.setUser(admin);
        controller.showUser(admin);

    }
}
