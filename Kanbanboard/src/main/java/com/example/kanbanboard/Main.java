package com.example.kanbanboard;

import com.example.kanbanboard.Scene.ChangeScene;
import com.example.kanbanboard.controller.ShowDetail;
import com.example.kanbanboard.model.*;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.FileService;
import com.example.kanbanboard.service.JacksonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 260);
            stage.setTitle("Login!");
            stage.setScene(scene);
            stage.show();

    }

    public static void main(String[] args) {
        launch();

    }
}