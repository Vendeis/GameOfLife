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


public class WyswietlRezerwacjaController {
    @FXML
    TableView<Rezerwacja> tabela;
    @FXML
    TableColumn<Rezerwacja,Integer> id;
    @FXML
    TableColumn<Rezerwacja,String> imie;
    @FXML
    TableColumn<Rezerwacja,String> nazwisko;
    @FXML
    TableColumn<Rezerwacja,Integer> nr_pokoju;
    @FXML
    TableColumn<Rezerwacja,Date> data_przyjazdu;
    @FXML
    TableColumn<Rezerwacja,Date> data_wyjazdu;
    @FXML
    TableColumn<Rezerwacja,String> parking;

    private ObservableList<Rezerwacja> list = FXCollections.observableArrayList();
    public void initialize() throws IOException{
        JSONObject json = new JSONObject("{wyswietlRezerwacja:wyswietlRezerwacja}");
        ServerOperation operation = new ServerOperation(json);
        JSONObject output = operation.getAnswerFromServer();
        JSONArray array = output.getJSONArray("wyswietl");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        nr_pokoju.setCellValueFactory(new PropertyValueFactory<>("nr_pokoju"));
        data_przyjazdu.setCellValueFactory(new PropertyValueFactory<>("data_przyjazdu"));
        data_wyjazdu.setCellValueFactory(new PropertyValueFactory<>("data_wyjazdu"));
        parking.setCellValueFactory(new PropertyValueFactory<>("parking"));

        for(int i = 0; i<array.length(); i++){
            JSONObject rezerwacja = (JSONObject) array.get(i);
            int id = rezerwacja.getInt("id");
            String imie = rezerwacja.getString("imie");
            String nazwisko = rezerwacja.getString("nazwisko");
            int nr_pokoju = rezerwacja.getInt("nr_pokoju");
            Date data_przyjazdu = Date.valueOf(rezerwacja.getString("data_przyjazdu"));
            Date data_wyjazdu = Date.valueOf(rezerwacja.getString("data_wyjazdu"));
            String parking = rezerwacja.getString("parking");
            list.add(new Rezerwacja(id,imie,nazwisko,nr_pokoju,data_przyjazdu,data_wyjazdu,parking));
        }
        tabela.setItems(list);
    }
}
