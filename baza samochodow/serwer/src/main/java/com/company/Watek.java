package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Watek extends Thread {
    private Socket socket;
    private Connection connection;
    private ArrayList<Car> cars;
    private ArrayList<Car> carFiltered;

    public Watek(Socket socket, Connection connection){
        this.socket = socket;
        this.connection = connection;
        this.cars = new ArrayList<>();
        this.carFiltered = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = reader.readLine();
            JSONObject input = new JSONObject(str);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            JSONObject output = new JSONObject();

            if (input.has("dodaj")) {
                String carString = input.getJSONObject("dodaj").toString();
                ObjectMapper mapper = new ObjectMapper();
                Car car = mapper.readValue(carString,Car.class);
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO Samochody VALUES(" + car.getId() + "," + car.getMarka()
                        + "," + car.getModel() + "," + car.getPojemnosc() + "," + car.getRokProdukcji()
                        + "," + car.getPrzebieg() + ")";
                try {
                    statement.execute(sql);
                    output.put("dodaj","dodano samochod o id " + car.getId());
                    System.out.println("dodano nowy samochod o id " + car.getId());
                }catch (SQLException e){
                    output.put("dodaj","w bazie znajduje się juz samochod o id " + car.getId());
                    System.out.println("w bazie znajduje się juz samochod o id " + car.getId());
                }

                statement.close();
                writer.write(output.toString());
            }
            else if (input.has("usun")) {
                int id = input.getInt("usun");
                Statement statement = connection.createStatement();
                String sql = "DELETE FROM Samochody WHERE ID =" + id;

                try {
                    statement.execute(sql);
                    output.put("usun", "usunieto samochod o id " + id);
                    System.out.println("usunieto samochod o id " + id);
                }
                catch (SQLException e) {
                    output.put("usun", "w bazie nie ma samochodu o id " + id);
                    System.out.println("w bazie nie ma samochodu o id " + id);
                }
                statement.close();
                writer.write(output.toString());
            }
            else if (input.has("wyswietl")) {
                wczytajDane();
                JSONArray array = new JSONArray(cars);
                output.put("wyswietl",array);
                writer.write(output.toString());
                System.out.println("Wyswietlam samochody");
            }
            else if (input.has("szukaj")){
                JSONObject object1 = new JSONObject(input.getString("szukaj"));
                wczytajDane();
                ArrayList<Car> list;
                int rokProdukcji = object1.getInt("rok produkcji");
                int przebieg = object1.getInt("przebieg");
                double pojemnosc = object1.getDouble("pojemnosc");
                list = cars.stream().filter(car -> car.getRokProdukcji()>rokProdukcji)
                        .filter(car -> car.getPrzebieg()<przebieg)
                        .filter(car -> car.getPojemnosc()>pojemnosc)
                        .collect(Collectors.toCollection(ArrayList::new));
                JSONArray array = new JSONArray(list);
                output.put("szukaj",array);
                writer.write(output.toString());
                System.out.println("Znaleziono samochody");

            }
            writer.newLine();
            writer.flush();


        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void wczytajDane() throws SQLException {
        Statement statement = connection.createStatement();
        String s = "SELECT * FROM Samochody";
        ResultSet resultSet = statement.executeQuery(s);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String marka = resultSet.getString(2);
            String model = resultSet.getString(3);
            double pojemnosc = resultSet.getDouble(4);
            int rokProdukcji = resultSet.getInt(5);
            int przebieg = resultSet.getInt(6);
            cars.add(new Car(id,marka,model,pojemnosc,rokProdukcji,przebieg));
        }
    }


}

