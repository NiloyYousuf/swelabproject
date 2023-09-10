module com.example.softwareproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jfreechart;
    requires java.sql;
    requires jcommon;


    opens com.example.softwareproject to javafx.fxml;
    exports com.example.softwareproject;
}