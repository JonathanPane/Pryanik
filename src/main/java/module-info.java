module com.example.pryanik {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pryanik to javafx.fxml;
    exports com.example.pryanik;
    exports com.example.pryanik.services;
    opens com.example.pryanik.services to javafx.fxml;
    opens com.example.pryanik.controllers to javafx.fxml;
    exports com.example.pryanik.controllers;
}