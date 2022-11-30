package main.controller.services;

import main.EmailManager;
import main.model.EmailAccount;

public class LoginService {

    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }
    
    public void login(){

    }
}
