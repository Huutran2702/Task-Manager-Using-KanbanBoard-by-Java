package com.example.kanbanboard;

import com.example.kanbanboard.model.User;
import com.example.kanbanboard.model.Work;
import com.example.kanbanboard.model.WorkList;
import com.example.kanbanboard.model.Workspace;
import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.JacksonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        UserRepository userRepository = new UserRepository();
//        Work readBook = new Work("Read Book",0);
//        WorkList workList = new WorkList("To do List", 0);
//        workList.getItems().add(readBook);
//        Workspace workspace = new Workspace("Khong gian chinh");
//        workspace.getWork().add(workList);
//        User user = new User("ngochuu","mytolove","huu@gmail.com","0392835054");
//        user.getWorkspace().add(workspace);
//        userRepository.add(user);
//        FileSevice.write(userRepository,"package.json");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();

    }
}