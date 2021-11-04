package com.example.kanbanboard.controller;


import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.model.User;


import com.example.kanbanboard.model.Work;
import com.example.kanbanboard.model.WorkList;
import com.example.kanbanboard.model.Workspace;
import com.example.kanbanboard.repository.DefaultWorkspace;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.service.JacksonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class ShowDetail {
    private User user;
    @FXML
    private GridPane border;
    @FXML
    private Label accName;
    @FXML
    private TextField workspaceName;
    @FXML
    private ComboBox<String> comboBoxWorkspace;
    @FXML
    private TextField newWorkspaceName;
    @FXML
    private TextField newWorkListName;
    @FXML
    private ComboBox<String> comboBoxWorkList;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void showUser(User user) {
        accName.setText(user.getAccount());
        if (user.getWorkspace() == null) {
            user.setWorkspace(new ArrayList<>(
            ));
            user.getWorkspace().add(0, DefaultWorkspace.setWorkspace());
        }
        setComboBoxWorkspace(user);
        setComboBoxWorkList();
    }

    private void setComboBoxWorkspace(User user) {
        ObservableList<String> workspaces = FXCollections.observableArrayList();
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            workspaces.add(user.getWorkspace().get(i).getName());
        }
        comboBoxWorkspace.setItems(workspaces);
        comboBoxWorkspace.setValue(user.getWorkspace().get(0).getName());

    }

    public void displayWorkList() {
        int indexWorkspace = 0;
        workspaceName.setText(comboBoxWorkspace.getValue());
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(comboBoxWorkspace.getValue())) {
                indexWorkspace = i;
            }
        }
        for (int j = 0; j < user.getWorkspace().get(indexWorkspace).getWork().size(); j++) {
            ObservableList<Work> works = FXCollections.observableArrayList();
            works.addAll(user.getWorkspace().get(indexWorkspace).getWork().get(j).getItems());
            border.addColumn(j, editTable(works, indexWorkspace, j));
            border.add(editButton(), j, 1);
            border.setHgap(10);
            border.setVgap(10);

        }
        GridPane addWork = addNewWork();
        border.add(addWork, 0, 2);
    }
// Phương thức làm việc với Workspace
    @FXML
    public void updateWorkspaceName(ActionEvent event) throws IOException {
        boolean isExist = false;
        int index = 0;
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(workspaceName.getText())) {
                index = i;
                isExist = true;
                break;
            }
        }
        if (workspaceName.getText().equals("")) {
            alertWorkspace("Tên không gian làm việc không hợp lệ");
        } else if (isExist) {
            alertWorkspace("Tên không gian làm việc đã tồn tại");
        } else {
            user.getWorkspace().get(index).setName(workspaceName.getText());
            loadWorkList();
            setComboBoxWorkspace(user);
            saveFile();
        }
        workspaceName.setText(comboBoxWorkspace.getValue());
    }

    @FXML
    public void selectWorkspace(ActionEvent event) {
        if (border.getChildren().size() > 0) {
            loadWorkList();
        }
        displayWorkList();
    }

    @FXML
    void creatNewWorkspace(ActionEvent event) throws IOException {
        boolean isExist = false;
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(newWorkspaceName.getText())) {
                isExist = true;
                break;
            }
        }
        if (newWorkspaceName.getText().equals("")) {
            alertWorkspace("Tên không gian làm việc không hợp lệ");
        } else if (isExist) {
            alertWorkspace("Tên không gian làm việc đã tồn tại");
        } else {
            Workspace newWorkspace = DefaultWorkspace.setWorkspace1();
            newWorkspace.setName(newWorkspaceName.getText());
            newWorkspace.setWork(DefaultWorkspace.setWorkspace1().getWork());
            user.getWorkspace().add(newWorkspace);
            newWorkspaceName.setText("");
            displayWorkList();
            setComboBoxWorkspace(user);
            saveFile();
        }
    }

    @FXML
    void deleteWorkspace(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn muốn xóa không gian làm việc này");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int indexWorkspace = 0;
            workspaceName.setText(comboBoxWorkspace.getValue());
            for (int i = 0; i < user.getWorkspace().size(); i++) {
                if (user.getWorkspace().get(i).getName().equals(comboBoxWorkspace.getValue())) {
                    indexWorkspace = i;
                }
            }
            user.getWorkspace().remove(indexWorkspace);
            displayWorkList();
            setComboBoxWorkspace(user);
            saveFile();
        }
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        FileService.writeAccountLogout(user, "save.json");
        Stage stage = ChangeScene.getStage(event);
        FXMLLoader loader = ChangeScene.setScene(stage, "login.fxml");
    }
    private void alertWorkspace(String message) {
        Alert alertInfor = new Alert(Alert.AlertType.INFORMATION);
        alertInfor.setHeaderText(null);
        alertInfor.setContentText(message);
        alertInfor.showAndWait();
    }

//    Load lại bảng WorkList
    public void loadWorkList() {
        border.getChildren().remove(0, border.getChildren().size());
    }
//    Tạo GridPane để thêm công việc vào WorkList
    private GridPane addNewWork() {
        GridPane addWork = new GridPane();
        Button add = new Button("Add");
        add.setPrefWidth(40);
        TextField newWork = new TextField();
        addWork.add(add, 1, 0);
        addWork.add(newWork, 0, 0);
        addWork.setVgap(10);
        addWork.setHgap(10);
        addWork.setPadding(new Insets(50, 10, 10, 10));
        return addWork;
    }
//    Thao tác với WorkList
    public void setComboBoxWorkList() {
        ObservableList<String> list = FXCollections.observableArrayList();
        int indexWorkspace = 0;
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(comboBoxWorkspace.getValue())) {
                indexWorkspace = i;
                break;
            }
        }
        for (int i = 0; i < user.getWorkspace().get(indexWorkspace).getWork().size(); i++) {
            list.add(user.getWorkspace().get(indexWorkspace).getWork().get(i).getName());
        }
        comboBoxWorkList.setItems(list);
    }
    @FXML
    void createNewWorkList(ActionEvent event) throws IOException {
        String name = newWorkListName.getText();
        int indexWorkspace = 0;
        boolean isExist = false;
        workspaceName.setText(comboBoxWorkspace.getValue());
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(comboBoxWorkspace.getValue())) {
                indexWorkspace = i;
            }
        }
        for (int i = 0; i < user.getWorkspace().get(indexWorkspace).getWork().size()  ; i++) {
            if (user.getWorkspace().get(indexWorkspace).getWork().get(i).getName().equals(name)) {
               isExist = true;
            }
        }

        if (name.equals("")) {
            alertWorkspace("Tên WorkList không hợp lệ");
        } else if (isExist) {
            alertWorkspace("Tên WorkList đã tồn tại");
        } else {
            user.getWorkspace().get(indexWorkspace).getWork().add(new WorkList(name,0));
            loadWorkList();
            displayWorkList();
            saveFile();
            setComboBoxWorkList();
        }
    }
    @FXML
    void deleteList(ActionEvent event) throws IOException {
        int indexWorkspace = 0;
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(comboBoxWorkspace.getValue())) {
                indexWorkspace = i;
                break;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn muốn xóa không gian làm việc này");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK) {
            String name = comboBoxWorkList.getValue();
            for (int i = 0; i < user.getWorkspace().get(indexWorkspace).getWork().size(); i++) {
                if (user.getWorkspace().get(indexWorkspace).getWork().get(i).getName().equals(name)) {
                    user.getWorkspace().get(indexWorkspace).getWork().remove(i);
                    break;
                }
                loadWorkList();
                setComboBoxWorkList();
                displayWorkList();
                saveFile();
            }
        }
    }
//    Thêm bảng WorkList
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
//    Thêm GridPane edit công việc
    public GridPane editButton() {
        GridPane edit = new GridPane();
        Button update = new Button("Update");
        Button delete = new Button("Delete");
        Button next = new Button("Next");
        edit.add(update, 0, 0);
        edit.add(delete, 1, 0);
        edit.add(next, 2, 0);
        edit.setHgap(10);
        edit.setVgap(10);
        return edit;
    }
//    Lưu dữ liệu user về file
    private void saveFile() throws IOException {
        FileService.read("package.json");
        String json = FileService.read("package.json");
        UserRepository userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
        userRepository.getByAccount(user.getAccount()).setWorkspace(user.getWorkspace());
        FileService.write(userRepository, "package.json");
    }
}