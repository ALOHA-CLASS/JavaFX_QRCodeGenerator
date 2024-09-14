module com.aloha {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires javafx.swing;

    opens com.aloha to javafx.graphics, javafx.fxml, com.google.zxing;
    exports com.aloha;
}
