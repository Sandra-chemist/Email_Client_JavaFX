package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import main.EmailManager;
import main.controller.services.MessageRendererService;
import main.model.EmailMessage;
import main.model.EmailTreeItem;
import main.model.SizeInteger;
import main.view.ViewFactory;
import java.util.Date;
import java.net.URL;
import javafx.scene.control.TableColumn;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TreeView<String> emailsTreeView;
    @FXML
    private TableView<EmailMessage> emailsTableView;

    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    @FXML
    private WebView emailWebView;

    private MessageRendererService messageRendererService;


    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void optionsAction() {
        viewFactory.showOptionsWindow();
    }

    @FXML
    void addAccountAction() {
        viewFactory.showLoginWindow();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpEmailsTreeView();
        setUpEmailsTableView();
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageRendererService();
        setUpMessageSelection();
    }

    private void setUpMessageSelection(){
        emailsTableView.setOnMouseClicked(event -> {
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
            if (emailMessage != null) {
                emailManager.setSelectedMessage(emailMessage);
                if (!emailMessage.isRead()) {
                    emailManager.setRead();
                }
                messageRendererService.setEmailMessage(emailMessage);
                messageRendererService.restart();
            }
        });
    }

    private void setUpMessageRendererService(){
        messageRendererService = new MessageRendererService(emailWebView.getEngine());
    }

    private void setUpBoldRows() {
        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
                return new TableRow<EmailMessage>(){
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty){
                        super.updateItem(item, empty);
                        if(item != null) {
                            if(item.isRead()){
                                setStyle("");
                            } else {
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }
    private void setUpFolderSelection(){
        emailsTreeView.setOnMouseClicked(e -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if(item != null){
                emailManager.setSelectedFolder(item);
                emailsTableView.setItems(item.getEmailMessages());
            }
        });
    }
    private void setUpEmailsTableView(){
        senderCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("sender")));
        subjectCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("subject")));
        recipientCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("recipient")));
        sizeCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, SizeInteger>("size")));
        dateCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, Date>("date")));
    }

    private void setUpEmailsTreeView(){
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }
}
