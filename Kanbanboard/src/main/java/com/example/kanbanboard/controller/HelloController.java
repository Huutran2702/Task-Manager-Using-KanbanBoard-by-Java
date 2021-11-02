package com.example.kanbanboard.controller;


import com.example.kanbanboard.Main;
import com.example.kanbanboard.model.User;


import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.JacksonParser;
import com.fasterxml.jackson.core.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TextField accName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("hello-view.fxml"));
        try {
            Parent submitParent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Login controller = loader.getController();

//        UserRepository userRepository = new UserRepository();
//        try {
//            FileReader fileReader = new FileReader("package.json");
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String json = bufferedReader.readLine();
//            User user = JacksonParser.INSTANCE.toObject(json,User.class);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public void showUser(User user) {
        accName.setText(user.getAccount());
    }
}