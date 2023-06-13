package com.td5;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent;

public class GraphicsCalculator extends Application {
    Tokenizer tok;
    
    @Override
    public void start(Stage stage) {
        stage.show();
        // TODO
        Scene scene = new Scene(new VBox(/* TODO */));
        stage.setScene(scene);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}