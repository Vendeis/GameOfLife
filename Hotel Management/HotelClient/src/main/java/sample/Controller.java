package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML void rezerwuj() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/rezerwacja.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene2 = new Scene(parent);
        Stage window = new Stage();
        window.setScene(scene2);
        window.setResizable(false);
        window.show();
    }
    @FXML void wyswietl() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/wyswietl.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene2 = new Scene(parent);
        Stage window = new Stage();
        window.setScene(scene2);
        window.setResizable(false);
        window.show();
    }
    @FXML void anuluj() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/anuluj.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene2 = new Scene(parent);
        Stage window = new Stage();
        window.setScene(scene2);
        window.setResizable(false);
        window.show();
    }
    @FXML void wyszukaj() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/wyszukaj.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene2 = new Scene(parent);
        Stage window = new Stage();
        window.setScene(scene2);
        window.setResizable(false);
        window.show();
    }
}
