module com.example.rubankgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rubankgui to javafx.fxml;
    exports com.example.rubankgui;
}