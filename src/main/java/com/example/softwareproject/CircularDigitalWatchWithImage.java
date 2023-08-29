package com.example.softwareproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CircularDigitalWatchWithImage extends Application {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private Text clockText = new Text();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 300);

        Circle circle = new Circle(150, Color.TRANSPARENT);
        circle.setStroke(Color.PURPLE);
        circle.setStrokeWidth(5);

        ImageView imageView = new ImageView(new Image("C:\\Users\\yousu\\IdeaProjects\\softwareProject\\src\\main\\resources\\com\\example\\softwareproject\\dinosaur.png")); // Replace with the actual image path

        root.getChildren().addAll(circle, imageView, clockText);
        StackPane.setAlignment(clockText, javafx.geometry.Pos.CENTER);

        primaryStage.setTitle("Circular Digital Watch");
        primaryStage.setScene(scene);
        primaryStage.show();

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateClock();
            }
        }, 0, 1000); // Update every 1 second
    }

    private void updateClock() {
        String currentTime = dateFormat.format(new Date());
        clockText.setText(currentTime);
    }


    public void initialize() {
        clockText.setLayoutX(50);
        clockText.setLayoutY(180);
        clockText.setStyle("-fx-font-size: 600px; -fx-text-fill: white;");
    }
}
