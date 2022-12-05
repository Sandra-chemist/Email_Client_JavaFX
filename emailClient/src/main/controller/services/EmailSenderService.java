package main.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.controller.EmailSendingResult;
import main.model.EmailAccount;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSenderService extends Service<EmailSendingResult> {

    private EmailAccount emailAccount;
    private String subject;
    private String recipient;
    private String content;

    public EmailSenderService(EmailAccount emailAccount, String subject, String recipient, String content) {
        this.emailAccount = emailAccount;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
    }

    @Override
    protected Task<EmailSendingResult> createTask() {
        return new Task<EmailSendingResult>() {
            @Override
            protected EmailSendingResult call() {
                try {
                    // create the message
                    MimeMessage mimeMessage = new MimeMessage(emailAccount.getSession());
                    mimeMessage.setFrom(emailAccount.getAddress());
                    mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
                    mimeMessage.setSubject(subject);

                    // set the content:
                    Multipart multipart = new MimeMultipart();
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(content, "text/html");
                    multipart.addBodyPart(messageBodyPart);
                    mimeMessage.setContent(multipart);


                } catch (MessagingException e) {
                    e.printStackTrace();
                    return EmailSendingResult.FAILED_BY_PROVIDER;
                } catch (Exception e) {
                    e.printStackTrace();
                    return EmailSendingResult.FAILED_BY_UNEXPECTED_ERROR;
                }
                return null;
            }
        };
    }
}
