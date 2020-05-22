module org.cfcdoom {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.cfcdoom to javafx.fxml;
    exports org.cfcdoom;
}