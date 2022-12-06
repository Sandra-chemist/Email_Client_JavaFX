package emailClient;

import javafx.application.Application;
import javafx.stage.Stage;
import emailClient.view.ViewFactory;
public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        viewFactory.showLoginWindow();
    }
}