package com.td5;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.input.KeyEvent;

public class GraphicsCalculator extends Application {
    Tokenizer tok;
    int WINDOW_SIZE = 500;
    Label label = new Label();
    Tokenizer tokenizer = new Tokenizer();

    @Override
    public void start(Stage stage) {
        stage.show();
        stage.setTitle("My calculator");
        stage.setWidth(WINDOW_SIZE);
        stage.setHeight(WINDOW_SIZE);

        HBox line2 = createLine(new char[]{'7','8', '9', '+'});
        HBox line3 = createLine(new char[]{'4','5', '6', '-'});
        HBox line4 = createLine(new char[]{'1','2', '3', '*'});
        HBox line5 = createLine(new char[]{'0','.', 'C', '/'});
        HBox line6 = createLine(new char[]{'(',')', '$', '='});

        Scene scene = new Scene(new VBox(
            2, 
            label,
            line2,
            line3,
            line4,
            line5,
            line6
        ));

        scene.setOnKeyTyped(e -> {
            char c = e.getCharacter().charAt(0);
            if (c == '\n')
                return;
            this.update(c); 
        });
        stage.setScene(scene);
    }

    HBox createLine (char[] characters){
        HBox line = new HBox();

        for (char c : characters){
            line.getChildren().addAll(b(c));
        }
        // line.getChildren().addAll(new Button[] {b('1')});
         
        return line;
    }

    Button b(char c){
        Button button =  new Button(""+ c);
        button.setMinSize(WINDOW_SIZE/4, WINDOW_SIZE/7);
        button.setMaxSize(WINDOW_SIZE/4, WINDOW_SIZE/7);
        button.setFont(new Font(20));
        button.setOnAction(e ->  {
            update(c);
        });

        return button;
    }

    void update(char c){
        String thisExpresion = this.label.getText();
        if (c == 'C'){
            tokenizer.resetState();
            this.label.setText("");
        }
        else if (c == '='){
            this.tokenizer.readString(thisExpresion + "=");
            double result = this
                .tokenizer
                .calc
                .getResult();

            this.label.setText(""+result);
        }

        else 
            this.label.setText(thisExpresion + c);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}