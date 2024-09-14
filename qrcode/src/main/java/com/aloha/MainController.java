package com.aloha;

import java.io.File;
import java.io.FileOutputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private BorderPane container;

    @FXML
    private Button btnGenerate;

    @FXML
    private TextField height;

    @FXML
    private ImageView imgQRCode;

    @FXML
    private TextField url;

    @FXML
    private TextField width;

    // x, y
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        container.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
            container.setCursor(Cursor.CLOSED_HAND);        // 🤚
        });

        container.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) container.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    void exit(ActionEvent event) {
        App.exit();
    }

    /**
     * ⭐⭐⭐⭐⭐
     * @param event
     */
    @FXML
    void generate(ActionEvent event) {
        try {
            // Get URL, Width, Height
            String qrCodeText = url.getText();
            int qrCodeWidth = Integer.parseInt(width.getText()) ;
            int qrCodeHeight = Integer.parseInt(height.getText()) ;

            // File Choose
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save QR Code Image");
            // png image only
            ExtensionFilter filter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add( filter );
            File file = fileChooser.showSaveDialog(new Stage());

            if( file != null ) {
                // Save QR Code Image
                byte[] data = ImageUtils.makeQR(qrCodeText, qrCodeWidth, qrCodeHeight);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();

                // Make QR Code Image to Transparent
                Image fxImage = ImageUtils.toTransparentImage(file);

                // Save Transparent QR Code Image
                ImageUtils.saveImageToFile(fxImage, file);

                // Show ImageView
                Image qrImage = new Image(file.toURI().toString());
                imgQRCode.setImage(qrImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
