package com.example.kanbanboard.Scene;

import com.example.kanbanboard.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScene {
    public static Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
    public static FXMLLoader setScene(Stage stage,String fileName,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(fileName));
        Parent submitParent = loader.load();
        Scene scene = new Scene(submitParent);
        stage.setTitle(title);
        stage.setScene(scene);
        return loader;
    }
}
