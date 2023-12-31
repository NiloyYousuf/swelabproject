package com.example.softwareproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmployeeAttendanceApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("attendance.fxml"));
        primaryStage.setTitle("Employee Attendance System");
        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
