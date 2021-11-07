package com.example.kanbanboard.controller;

import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.model.Role;
import com.example.kanbanboard.model.User;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.service.JacksonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DisplayUserList {
    private User admin;
private UserRepository userRepository;
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
}
    @FXML
    void addAdmin(ActionEvent event) throws IOException {
        User selected = table.getSelectionModel().getSelectedItem();
        userRepository.getByEmail(selected.getEmail()).setRole(Role.ADMIN);
        FileService.write(userRepository, "package.json");
        show();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Stage stage = ChangeScene.getStage(event);
        ShowDetail controller = ChangeScene.setScene(stage,"show-detail.fxml","Kanban Board").getController();
        controller.setUser(admin);
        controller.showUser(admin);

    }
}
