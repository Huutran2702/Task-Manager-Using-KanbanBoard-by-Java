package com.example.kanbanboard.controller;


import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.model.*;


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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class KanbanBoard {
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
    private TextField email_share;
    @FXML
    private TextField newWorkName;
    @FXML
    private ComboBox<String> comboBoxWorkList;
    @FXML
    private Hyperlink share_workspace;
    @FXML
    private Button share_btn;
    @FXML
    private Button create_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private Button add_work_btn;
    @FXML
    private Hyperlink create_new_list;
    @FXML
    private Hyperlink delete_list;
    @FXML
    private Hyperlink add_new_work;
    @FXML
    private Hyperlink display_user_list;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @FXML
    void showInformation(ActionEvent event) throws IOException {
        Stage stage = ChangeScene.getStage(event);
        stage.setX(500);
        stage.setY(80);
        InformationUser controller = ChangeScene.setScene(stage,"show-information-user.fxml","User Information").getController();
        controller.setUser(user);
        controller.show(user);
    }

    public void showUser(User user) {
        accName.setText(user.getName());
        if (user.getWorkspace() == null) {
            user.setWorkspace(new ArrayList<>(
            ));
            user.getWorkspace().add(0, DefaultWorkspace.setWorkspace());
        }
        if (user.getRole().equals(Role.ADMIN)) {
            setDisable(display_user_list,false);
            setOpacity(display_user_list,1);
        }
        setComboBoxWorkspace(user);
        setComboBoxWorkList();
    }
    @FXML
    void displayUserList(ActionEvent event) throws IOException {
        Stage stage = ChangeScene.getStage(event);
        stage.setX(400);
        stage.setY(80);
        System.out.println(stage.getX());
        System.out.println(stage.getY());

        UserList controller = ChangeScene.setScene(stage,"list-user.fxml","User List").getController();
        controller.setAdmin(user);
        controller.show();

    }
    private void setComboBoxWorkspace(User user) {
        ObservableList<String> workspaces = FXCollections.observableArrayList();
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            workspaces.add(user.getWorkspace().get(i).getName());
        }
        comboBoxWorkspace.setItems(workspaces);
        comboBoxWorkspace.setValue(user.getWorkspace().get(getIndexWorkspace()).getName());

    }

    public int getIndexWorkspace() {
        int indexWorkspace = 0;
        workspaceName.setText(comboBoxWorkspace.getValue());
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(comboBoxWorkspace.getValue())) {
                indexWorkspace = i;
            }
        }
        return indexWorkspace;
    }

    public void displayWorkList() {
        for (int j = 0; j < user.getWorkspace().get(getIndexWorkspace()).getWork().size(); j++) {
            ObservableList<Work> works = FXCollections.observableArrayList();
            works.addAll(user.getWorkspace().get(getIndexWorkspace()).getWork().get(j).getItems());
            TableView<Work> table = editTable(works, getIndexWorkspace(), j);
            border.addColumn(j, table);
//            border.getColumnConstraints().get(j).setMinWidth(150);
            border.add(editButton(j, table), j, 1);
            border.setHgap(10);
            border.setVgap(10);
            border.setPadding(new Insets(30, 0, 0, 0));

        }
    }

    // Phương thức làm việc với Workspace
    @FXML
    public void updateWorkspaceName(ActionEvent event) throws IOException {
        String newWorkspaceName = workspaceName.getText();
        System.out.println(newWorkspaceName);
        boolean isExist = false;
        for (int i = 0; i < user.getWorkspace().size(); i++) {
            if (user.getWorkspace().get(i).getName().equals(workspaceName.getText())) {
                isExist = true;
                break;
            }
        }
        if (workspaceName.getText().equals("")) {
            alertWorkspace("Invalid workspace name");
        } else if (isExist) {
            alertWorkspace("Workspace name already exist");
        } else {
            user.getWorkspace().get(getIndexWorkspace()).setName(newWorkspaceName);
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
            alertWorkspace("Invalid workspace name");
        } else if (isExist) {
            alertWorkspace("Workspace name already exist");
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
        alert.setContentText("You want to delete this workspace?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (user.getWorkspace().size() == 1) {
                user.getWorkspace().add(DefaultWorkspace.setWorkspace());
            }
            user.getWorkspace().remove(getIndexWorkspace());
            displayWorkList();
            setComboBoxWorkspace(user);
            saveFile();
        }
    }

    @FXML
    void shareWorkspaceToOtherUser(ActionEvent event) throws IOException {
        FileService.read("package.json");
        String json = FileService.read("package.json");
        UserRepository userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
        if (email_share.getText().equals("")) {
            alertWorkspace("Email does not exist");
        } else if (userRepository.getByEmail(email_share.getText())==null) {
            alertWorkspace("Email does not exist");
        } else {
            userRepository.getByEmail(email_share.getText()).getWorkspace().add(user.getWorkspace().get(getIndexWorkspace()));
            FileService.write(userRepository, "package.json");

        }
        setDisable(share_workspace,false);
        setOpacity(share_workspace,1);
        share_workspace.underlineProperty().setValue(false);
        setDisable(email_share,true);
        setOpacity(email_share,0);
        setDisable(share_btn,true);
        setOpacity(share_btn,0);
        email_share.setText("");
    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        user.setLoginStatus(LoginStatus.LOGOUT);
        FileService.writeAccountLogout(user, "save.json");
        Stage stage = ChangeScene.getStage(event);
        FXMLLoader loader = ChangeScene.setScene(stage, "login.fxml", "Login!");
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

    //    Thêm công việc vào WorkList
    @FXML
    private void addNewWork() {
        if (newWorkName.getText().equals("")) {
            alertWorkspace("Invalid work name");
        } else {
            user.getWorkspace().get(getIndexWorkspace()).getWork().get(0).getItems().add(new Work(0, newWorkName.getText()));
            newWorkName.setText("");
            loadWorkList();
            displayWorkList();
            try {
                saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setDisable(add_new_work,false);
        setOpacity(add_new_work,1);
        add_new_work.underlineProperty().setValue(false);
        setDisable(newWorkName,true);
        setOpacity(newWorkName,0);
        setDisable(add_work_btn,true);
        setOpacity(add_work_btn,0);
    }

    //    Thao tác với WorkList
    public void setComboBoxWorkList() {
        ObservableList<String> list = FXCollections.observableArrayList();

        for (int i = 0; i < user.getWorkspace().get(getIndexWorkspace()).getWork().size(); i++) {
            list.add(user.getWorkspace().get(getIndexWorkspace()).getWork().get(i).getName());
        }
        comboBoxWorkList.setItems(list);
    }

    @FXML
    void createNewWorkList(ActionEvent event) throws IOException {
        String name = newWorkListName.getText();
        boolean isExist = false;
        workspaceName.setText(comboBoxWorkspace.getValue());

        for (int i = 0; i < user.getWorkspace().get(getIndexWorkspace()).getWork().size(); i++) {
            if (user.getWorkspace().get(getIndexWorkspace()).getWork().get(i).getName().equals(name)) {
                isExist = true;
            }
        }

        if (name.equals("")) {
            alertWorkspace("Invalid work list name");
        } else if (isExist) {
            alertWorkspace("Work list name already exist");
        } else {
            if (user.getWorkspace().get(getIndexWorkspace()).getWork().size()<5) {
                WorkList newWorkList = new WorkList(name, 0);
                newWorkList.getItems().add(new Work(0, "Công việc mới"));
                user.getWorkspace().get(getIndexWorkspace()).getWork().add(newWorkList);
                loadWorkList();
                displayWorkList();
                saveFile();
                setComboBoxWorkList();
            } else {
                alertWorkspace("The number of work lists should not be more than 5");
            }
        }
        newWorkListName.setText("");
        setDisable(create_new_list,false);
        setOpacity(create_new_list,1);
        create_new_list.underlineProperty().setValue(false);
        setDisable(newWorkListName,true);
        setOpacity(newWorkListName,0);
        setDisable(create_btn,true);
        setOpacity(create_btn,0);
    }

    @FXML
    void deleteList(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("You want to delete this work list?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String name = comboBoxWorkList.getValue();
            for (int i = 0; i < user.getWorkspace().get(getIndexWorkspace()).getWork().size(); i++) {
                if (user.getWorkspace().get(getIndexWorkspace()).getWork().get(i).getName().equals(name)) {
                    user.getWorkspace().get(getIndexWorkspace()).getWork().remove(i);
                }
                loadWorkList();
                setComboBoxWorkList();
                displayWorkList();
                saveFile();
            }
        }
        comboBoxWorkList.setValue(user.getWorkspace().get(getIndexWorkspace()).getWork().get(0).getName());
        setDisable(delete_list,false);
        setOpacity(delete_list,1);
        delete_list.underlineProperty().setValue(false);
        setDisable(comboBoxWorkList,true);
        setOpacity(comboBoxWorkList,0);
        setDisable(delete_btn,true);
        setOpacity(delete_btn,0);
    }


    //    Thêm bảng WorkList
    public TableView<Work> editTable(ObservableList<Work> obj, int index, int j) {
        TableView<Work> table = new TableView<>();
        table.setEditable(true);
        TableColumn<Work, String> nameCol = new TableColumn<>();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
//            user.getWorkspace().get(getIndexWorkspace()).getWork().get(index).getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            loadWorkList();
            displayWorkList();
            try {
                saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        nameCol.setText(user.getWorkspace().get(index).getWork().get(j).getName());
        boolean editable = nameCol.isEditable();
        System.out.println(editable);
        table.getColumns().add(nameCol);
        table.setItems(obj);
        table.setMinWidth(150);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    //    Thêm GridPane edit công việc
    public GridPane editButton(int index, TableView<Work> node) {
        GridPane edit = new GridPane();
        Button delete = new Button("Delete");
        delete.setOnAction(event -> {
            int work = node.getSelectionModel().getSelectedIndex();
            user.getWorkspace().get(getIndexWorkspace()).getWork().get(index).getItems().remove(work);
            loadWorkList();
            displayWorkList();
            try {
                saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        edit.add(delete, 0, 0);
        if (index < user.getWorkspace().get(getIndexWorkspace()).getWork().size() - 1) {
            Button next = new Button("Next");
            next.setOnAction(event -> {
                int selectedIndex = node.getSelectionModel().getSelectedIndex();
                if (index < user.getWorkspace().get(getIndexWorkspace()).getWork().size()) {
                    Work work = user.getWorkspace().get(getIndexWorkspace()).getWork().get(index).getItems().remove(selectedIndex);
                    user.getWorkspace().get(getIndexWorkspace()).getWork().get(index + 1).getItems().add(work);
                    loadWorkList();
                    displayWorkList();
                    try {
                        saveFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            edit.add(next, 1, 0);
        }
        edit.setHgap(10);
        edit.setVgap(10);
        edit.setPadding(new Insets(0, 0, 0, 20));
        return edit;
    }

    //    Lưu dữ liệu user về file
    private void saveFile() throws IOException {
        FileService.read("package.json");
        String json = FileService.read("package.json");
        UserRepository userRepository = new UserRepository();
        userRepository.userList = JacksonParser.INSTANCE.toList(json, User.class);
        userRepository.getByEmail(user.getEmail()).setWorkspace(user.getWorkspace());
        FileService.write(userRepository, "package.json");
    }

    //    Thao tác hyperLink
    private void setOpacity(Node node, int a) {
        node.opacityProperty().setValue(a);
    }
    private void setDisable(Node node,boolean a) {
        node.disableProperty().setValue(a);
    }
    @FXML
    void addNewWorkLink(ActionEvent event) {
        setDisable(add_new_work,true);
        setOpacity(add_new_work,0);
        setDisable(newWorkName,false);
        setOpacity(newWorkName,1);
        setDisable(add_work_btn,false);
        setOpacity(add_work_btn,1);
    }



    @FXML
    void deleteListLink(ActionEvent event) {
        setDisable(delete_list,true);
        setOpacity(delete_list,0);
        setDisable(comboBoxWorkList,false);
        setOpacity(comboBoxWorkList,1);
        setDisable(delete_btn,false);
        setOpacity(delete_btn,1);

    }
    @FXML
    void createNewList(ActionEvent event) {
        setDisable(create_new_list,true);
        setOpacity(create_new_list,0);
        setDisable(newWorkListName,false);
        setOpacity(newWorkListName,1);
        setDisable(create_btn,false);
        setOpacity(create_btn,1);
    }
    @FXML
    void shareWorkspace(ActionEvent event) {
        setDisable(share_workspace,true);
        setOpacity(share_workspace,0);
        setDisable(email_share,false);
        setOpacity(email_share,1);
        setDisable(share_btn,false);
        setOpacity(share_btn,1);

    }


}