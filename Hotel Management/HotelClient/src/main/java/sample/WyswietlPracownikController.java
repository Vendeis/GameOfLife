package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class WyswietlPracownikController {
    @FXML
    TableView<Pracownik> tabela;
    @FXML
    TableColumn<Pracownik,Integer> id;
    @FXML
    TableColumn<Pracownik,String> imie;
    @FXML
    TableColumn<Pracownik,String> nazwisko;
    @FXML
    TableColumn<Pracownik,String> stanowisko;
    @FXML
    TableColumn<Rezerwacja,Integer> pensja;


    private ObservableList<Pracownik> list = FXCollections.observableArrayList();
    public void initialize() throws IOException{
        JSONObject json = new JSONObject("{wyswietlPracownik:wyswietlPracownik}");
        ServerOperation operation = new ServerOperation(json);
        JSONObject output = operation.getAnswerFromServer();
        JSONArray array = output.getJSONArray("wyswietl");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        stanowisko.setCellValueFactory(new PropertyValueFactory<>("stanowisko"));
        pensja.setCellValueFactory(new PropertyValueFactory<>("pensja"));


        for(int i = 0; i<array.length(); i++){
            JSONObject pracownik = (JSONObject) array.get(i);
            int id = pracownik.getInt("id");
            String imie = pracownik.getString("imie");
            String nazwisko = pracownik.getString("nazwisko");
            String stanowisko = pracownik.getString("stanowisko");
            int pensja = pracownik.getInt("pensja");
            list.add(new Pracownik(id,imie,nazwisko,stanowisko,pensja));
        }
        tabela.setItems(list);
    }
}
