package com.example.kanbanboard.controller;


import com.example.kanbanboard.model.User;


import com.example.kanbanboard.model.Work;
import com.example.kanbanboard.model.WorkList;
import com.example.kanbanboard.repository.DefaultWorkspace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;


public class ShowDetail {
    private User user;
    @FXML
    private GridPane border;
    @FXML
    private Label accName;
    @FXML
    private TextField workspaceName;
    @FXML
    private ComboBox<String> workspaceChoiceBox;

    public void showUser(User user) {
        if (user.getWorkspace() == null) {
            user.setWorkspace(new ArrayList<>(
            ));
            user.getWorkspace().add(0,DefaultWorkspace.setWorkspace());
        }
        ObservableList<String> workspaces = FXCollections.observableArrayList();
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            workspaces.add(user.getWorkspace().get(i).getName());
        }
        workspaceChoiceBox.setItems(workspaces);

        accName.setText(user.getAccount());

    }

    @FXML
    public void selectWorkspace(ActionEvent event) {
        int index = 0;
        workspaceName.setText(workspaceChoiceBox.getValue());
        for (int i = 0; i < user.getWorkspace().size() ; i++) {
            if (user.getWorkspace().get(i).getName().equals(workspaceChoiceBox.getValue())){
                index = i;
            }
        }

        for (int j = 0; j < user.getWorkspace().get(index).getWork().size() ; j++) {
            TableView<Work> table = new TableView<>();
            ObservableList<Work> works = FXCollections.observableArrayList();
            works.addAll(user.getWorkspace().get(index).getWork().get(j).getItems());
            TableColumn<Work, String> tableColumn = new TableColumn<>();
            tableColumn.setText(user.getWorkspace().get(index).getWork().get(j).getName());
            tableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            table.getColumns().add(tableColumn);
            table.setItems(works);
            border.addColumn(j,table);

        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}