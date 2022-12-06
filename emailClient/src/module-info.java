module emailClient {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;
    requires java.desktop;

    opens emailClient;
    opens emailClient.view;
    opens emailClient.controller;
    opens emailClient.model;
}