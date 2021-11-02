module com.example.kanbanboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    opens com.example.kanbanboard to javafx.fxml;
    exports com.example.kanbanboard;
    exports com.example.kanbanboard.controller;
    opens com.example.kanbanboard.controller to javafx.fxml;
    exports com.example.kanbanboard.model;
    opens com.example.kanbanboard.model to javafx.fxml;
}