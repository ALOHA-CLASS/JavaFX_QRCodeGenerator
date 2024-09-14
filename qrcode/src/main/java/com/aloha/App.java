package com.aloha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Main"));
        // CSS
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        // Font
        Font.loadFont(App.class.getResource("/fonts/Roboto-Medium.ttf").toExternalForm(), 16);
        // icon
        Image image = new Image(App.class.getResource("/img/icon.png").toExternalForm());
        stage.getIcons().add(image);
        // title
        stage.setTitle("QR Code Generator - ALOHA CLASS");
        // TRANSPARENT
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void exit() {
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch();
    }

}