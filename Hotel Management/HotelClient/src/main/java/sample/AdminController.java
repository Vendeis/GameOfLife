package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    public void usun(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/usunPracownik.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene2 = new Scene(parent);
        Stage window = new Stage();
        window.setScene(scene2);
        window.setResizable(false);
        window.show();
    }

    public void wyswietl(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/wyswietlPracownik.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene2 = new Scene(parent);
        Stage window = new Stage();
        window.setScene(scene2);
        window.setResizable(false);
        window.show();
    }

    public void dodaj(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/dodajPracownik.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene2 = new Scene(parent);
        Stage window = new Stage();
        window.setScene(scene2);
        window.setResizable(false);
        window.show();
    }
}
