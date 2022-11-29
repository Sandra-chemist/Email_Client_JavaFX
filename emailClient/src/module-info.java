module emailClient {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    opens com.EmailClient;
    opens com.EmailClient.view;
    opens com.EmailClient.controller;
}