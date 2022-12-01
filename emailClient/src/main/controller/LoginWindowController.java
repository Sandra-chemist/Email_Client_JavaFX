package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.EmailManager;
import main.controller.services.LoginService;
import main.model.EmailAccount;
import main.view.ViewFactory;

public class LoginWindowController extends BaseController {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField passwordField;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() {
        System.out.println("loginButtonAction!");
        if (fieldsAreaValid()) {
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {

                EmailLoginResult emailLoginResult = loginService.getValue();

                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("Login successful: " + emailAccount);
                        viewFactory.showMainWindow();
                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                }

            });

        }

    }

    private boolean fieldsAreaValid() {
        if (emailAddressField.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        }
        if (passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill password");
            return false;
        }
        return true;

    }

}
