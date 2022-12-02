module emailClient {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;

    opens main;
    opens main.view;
    opens main.controller;
    opens main.model;
}