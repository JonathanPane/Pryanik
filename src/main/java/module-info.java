module com.example.pryanik {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.pryanik to javafx.fxml;
    exports com.example.pryanik;
    exports com.example.pryanik.services;
    opens com.example.pryanik.services to javafx.fxml;
    opens com.example.pryanik.controllers to javafx.fxml;
    exports com.example.pryanik.controllers;
    exports com.example.pryanik.project.library;
    opens com.example.pryanik.project.library to javafx.fxml;
    exports com.example.pryanik.DTO;
}