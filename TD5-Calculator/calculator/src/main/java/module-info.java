module com.td5 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.td5 to javafx.fxml;
    exports com.td5;
}
