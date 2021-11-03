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
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;


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
        accName.setText(user.getAccount());
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
//            TableColumn<Work,Button> update = new TableColumn<>();
//            update.setText("Edit");
//            update.setCellValueFactory(new PropertyValueFactory<>(""));
            table.setItems(works);
            table.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
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