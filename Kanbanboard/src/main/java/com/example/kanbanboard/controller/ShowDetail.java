package com.example.kanbanboard.controller;


import com.example.kanbanboard.model.User;


import com.example.kanbanboard.model.Work;
import com.example.kanbanboard.model.Workspace;
import com.example.kanbanboard.repository.DefaultWorkspace;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.service.JacksonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;


public class ShowDetail {
    private User user;
    @FXML
    private GridPane border = new GridPane();
    @FXML
    private Label accName;
    @FXML
    private TextField workspaceName;
    @FXML
    private ComboBox<String> workspaceChoiceBox;
    @FXML
    private TextField newWorkspaceName;
    public void showUser(User user) {
        accName.setText(user.getAccount());
        if (user.getWorkspace() == null) {
            user.setWorkspace(new ArrayList<>(
            ));
            user.getWorkspace().add(0, DefaultWorkspace.setWorkspace());
        }
        setChoiceBox(user);
    }

    private void setChoiceBox(User user) {
        ObservableList<String> workspaces = FXCollections.observableArrayList();
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            workspaces.add(user.getWorkspace().get(i).getName());
        }
        workspaceChoiceBox.setItems(workspaces);
        workspaceChoiceBox.setValue(user.getWorkspace().get(0).getName());

    }

    public void displayWorkList() {
        int indexWorkspace = 0;
        workspaceName.setText(workspaceChoiceBox.getValue());
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(workspaceChoiceBox.getValue())) {
                indexWorkspace = i;
            }
        }
        for (int j = 0; j < user.getWorkspace().get(indexWorkspace).getWork().size(); j++) {
            ObservableList<Work> works = FXCollections.observableArrayList();
            works.addAll(user.getWorkspace().get(indexWorkspace).getWork().get(j).getItems());
            border.addColumn(j, editTable(works, indexWorkspace, j));
            border.setHgap(10);
            border.setVgap(10);
        }
    }

    @FXML
    public void selectWorkspace(ActionEvent event) {
        loadWorkList();
        displayWorkList();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void updateWorkspaceName(ActionEvent event) throws IOException {
        int index = 0;
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(workspaceChoiceBox.getValue())) {
                index = i;
            }
        }
        user.getWorkspace().get(index).setName(workspaceName.getText());
        setChoiceBox(user);
        displayWorkList();
        saveFile();
    }

    private void saveFile() throws IOException {
        FileService.read("package.json");
        String json = FileService.read("package.json");
        UserRepository userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
        userRepository.getByAccount(user.getAccount()).setWorkspace(user.getWorkspace());
        FileService.write(userRepository, "package.json");
    }

    @FXML
    void creatNewWorkspace(ActionEvent event) throws IOException {
        Workspace  newWorkspace=DefaultWorkspace.setWorkspace1();
        newWorkspace.setName(newWorkspaceName.getText());
        user.getWorkspace().add(newWorkspace);
        newWorkspaceName.setText("");
        displayWorkList();
        setChoiceBox(user);
//        FileService.read("package.json");
//        String json = FileService.read("package.json");
//        UserRepository userRepository = new UserRepository();
//        userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
//        userRepository.getByAccount(user.getAccount()).setWorkspace(user.getWorkspace());
//        FileService.write(userRepository, "package.json");
    }
    public <T> Node editTable(ObservableList<T> obj, int index, int j) {
        TableView<T> table = new TableView<>();
        TableColumn<T, String> nameCol = new TableColumn<>();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setText(user.getWorkspace().get(index).getWork().get(j).getName());
        table.getColumns().add(nameCol);
        table.setItems(obj);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }
    public void loadWorkList() {
//        int indexWorkspace = 0;
//        workspaceName.setText(workspaceChoiceBox.getValue());
//        for (int i = 0; i < user.getWorkspace().size(); i++) {
//            if (user.getWorkspace().get(i).getName().equals(workspaceChoiceBox.getValue())) {
//                indexWorkspace = i;
//            }
//        }
        border.getChildren().remove(0,3);

    }
}