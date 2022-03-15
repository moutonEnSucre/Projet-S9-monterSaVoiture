module com.sma.taquinsma {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sma.taquinsma to javafx.fxml;
    exports com.sma.taquinsma;
}