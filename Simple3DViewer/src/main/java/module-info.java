module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;
    requires junit;
    requires org.testng;
    requires org.junit.jupiter.api;


    opens com.CG4 to javafx.fxml;
    exports com.CG4;
}