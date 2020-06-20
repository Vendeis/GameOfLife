package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Watek extends Thread {
    private Socket socket;
    private Connection connection;
    private ArrayList<Rezerwacja> list;
    private ArrayList<Pracownik> list2;
    public Watek(Socket socket, Connection connection){
        this.socket = socket;
        this.connection = connection;
        list = new ArrayList<>();
        list2 = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = reader.readLine();
            JSONObject input = new JSONObject(s);
            //System.out.println(input.toString());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            JSONObject output = new JSONObject();
            
            if(input.has("wyswietlRezerwacja")){
                output = wyswietl(input);
            }
            else if(input.has("rezerwuj")){
                output = rezerwuj(input);
            }
            else if(input.has("anuluj")){
                output = anuluj(input);
            }
            else if(input.has("wyszukaj")){
                output = wyszukaj(input);
            }
            else if(input.has("wyswietlPracownik")){
                output = wyswietl(input);
            }
            else if(input.has("dodaj")){
                output = dodaj(input);
                
            }
            else if(input.has("usun")){
                output = usun(input);
            }
            writer.write(output.toString());
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject usun(JSONObject input) {
        int id = input.getInt("usun");
        JSONObject object = new JSONObject();

        try {
            Statement statement = connection.createStatement();
            int x = statement.executeUpdate("DELETE FROM pracownicy WHERE ID = " + id);
            if(x<1) {
                object.put("blad",id);
                System.out.println("brak pracownika o id " + id);
            }
            else {
                System.out.println("usunieto pracownika o id " + id);
                object.put("sukces", id);
            }
        }
        catch (SQLException e){e.printStackTrace();}
        return object;
    }

    private JSONObject dodaj(JSONObject input) {
        JSONObject object = new JSONObject(input.getJSONObject("dodaj").toString());
        JSONObject object1 = new JSONObject();
        try {
            pobierzDaneDoListy();
            Statement statement = connection.createStatement();
            String s = "INSERT INTO pracownicy(IMIE,NAZWISKO,STANOWISKO,PENSJA)" +
                    " VALUES('" + object.getString("imie") + "','" +
                    object.getString("nazwisko") + "','" + object.getString("stanowisko")
                    + "',"  +  object.getInt("pensja") + ")";
            statement.executeUpdate(s);
            object1.put("sukces","sukces");
        }
        catch (Exception e) {
            e.printStackTrace();
            object1.put("blad","blad");
        }
        return object1;
    }

    private JSONObject wyszukaj(JSONObject input) {
        int id = input.getInt("wyszukaj");
        JSONObject object = new JSONObject();
        try {
            Statement statement = connection.createStatement();
            int x = statement.executeUpdate("SELECT * FROM rezerwacje WHERE ID = " + id);
            if(x<1) {
                object.put("blad",id);
                System.out.println("brak rezerwacji o id " + id);
            }
            else {
                System.out.println("znaleziono rezerwacje rezerwacje o id " + id);
                pobierzDaneDoListy();

                ArrayList<Rezerwacja> filteredList = list.stream().filter(rezerwacja -> rezerwacja.getId()==id)
                        .collect(Collectors.toCollection(ArrayList::new));

                for(int i=0; i<filteredList.size(); i++){
                    object.put("id",filteredList.get(i).getId());
                    object.put("imie",filteredList.get(i).getImie());
                    object.put("nazwisko",filteredList.get(i).getNazwisko());
                    object.put("nr_pokoju",filteredList.get(i).getNr_pokoju());
                    object.put("data_przyjazdu",filteredList.get(i).getData_przyjazdu());
                    object.put("data_wyjazdu",filteredList.get(i).getData_wyjazdu());
                    object.put("parking",filteredList.get(i).getParking());
                }
            }
        }
        catch (SQLException e){e.printStackTrace();}
        return object;
    }

    private JSONObject anuluj(JSONObject input) {
        int id = input.getInt("anuluj");
        JSONObject object = new JSONObject();

        try {
            Statement statement = connection.createStatement();
            int x = statement.executeUpdate("DELETE FROM rezerwacje WHERE ID = " + id);
            if(x<1) {
                object.put("blad",id);
                System.out.println("brak rezerwacji o id " + id);
            }
            else {
                System.out.println("usunieto rezerwacje o id " + id);
                object.put("sukces", id);
            }
        }
        catch (SQLException e){e.printStackTrace();}
        return object;
    }

    private JSONObject rezerwuj(JSONObject input) {
        JSONObject object = new JSONObject(input.getJSONObject("rezerwuj").toString());
        try {
            pobierzDaneDoListy();
            // sprawdzanie, czy wybrany pokoj jest wolny w okreslonym terminie
            for(Rezerwacja rezerwacja : list){
                if (rezerwacja.getNr_pokoju() == object.getInt("nr_pokoju")){
                    Date przyjazd1,przyjazd2;
                    Date wyjazd1,wyjazd2;
                    przyjazd1 = rezerwacja.getData_przyjazdu();
                    przyjazd2 = Date.valueOf(object.getString("data_przyjazdu"));
                    wyjazd1 = rezerwacja.getData_wyjazdu();
                    wyjazd2 = Date.valueOf(object.getString("data_wyjazdu"));
                    // jesli przyjazd1 po przyjazd2
                    if(przyjazd1.compareTo(przyjazd2)>=0){
                        if (przyjazd1.compareTo(wyjazd2)<=0){
                            System.out.println("pokoj zajety!");
                            return new JSONObject("{blad:wystapil blad}");

                        }
                    }
                    else if(przyjazd2.compareTo(przyjazd1)>=0){
                        if(przyjazd2.compareTo(wyjazd1)<=0){
                            System.out.println("pokoj zajety!");
                            return new JSONObject("{blad:wystapil blad}");
                        }
                    }

                }
            }
            Statement statement = connection.createStatement();
            String s = "INSERT INTO rezerwacje(IMIE,NAZWISKO,NR_POKOJU,DATA_PRZYJAZDU," +
                    "DATA_WYJAZDU,PARKING) VALUES('" + object.getString("imie") + "','" +
                    object.getString("nazwisko") + "','" + object.getInt("nr_pokoju")
                    + "','" + Date.valueOf(object.getString("data_przyjazdu")) + "','" +
                    Date.valueOf(object.getString("data_wyjazdu")) + "','" +
                    object.getString("parking") + "')";
            statement.executeUpdate(s);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("rezerwacja przyjęta");
        JSONObject object1 = new JSONObject();
        long milliseconds = Date.valueOf(object.getString("data_wyjazdu")).getTime() -
                            Date.valueOf(object.getString("data_przyjazdu")).getTime();
        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);

        int cena;
        if(object.getInt("nr_pokoju")>=200){
            cena = 80;
        }
        else
            cena = 50;
        if(object.getString("parking").equals("Y")){
            cena += 10;
        }
        object1.put("nr_pokoju",object.getInt("nr_pokoju"));
        object1.put("czas_pobytu",days);
        object1.put("cena",cena*days);
        return object1;
    }

    private JSONObject wyswietl(JSONObject input)  {
        JSONObject object = new JSONObject();
        try {
            if(input.has("wyswietlRezerwacja")) {
                pobierzDaneDoListy();
                JSONArray array = new JSONArray(list);
                object.put("wyswietl",array);
            }
            else if(input.has("wyswietlPracownik")){
                pobierzDaneDoListy2();
                JSONArray array = new JSONArray(list2);
                object.put("wyswietl",array);
            }

        }
        catch (Exception e){ e.printStackTrace();}

        return object;
    }

    private void pobierzDaneDoListy() throws SQLException{
        Statement statement = connection.createStatement();

        String s = "SELECT * FROM rezerwacje";
        ResultSet resultSet = statement.executeQuery(s);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String imie = resultSet.getString(2);
            String nazwisko = resultSet.getString(3);
            int nr_pokoju = resultSet.getInt(4);
            Date data_przyjazdu = resultSet.getDate(5);
            Date data_wyjazdu = resultSet.getDate(6);
            String parking = resultSet.getString(7);
            list.add(new Rezerwacja(id,imie,nazwisko,nr_pokoju,data_przyjazdu,data_wyjazdu,parking));
        }
    }
    private void pobierzDaneDoListy2() throws SQLException {
        Statement statement = connection.createStatement();
        String s = "SELECT * FROM pracownicy";
        ResultSet resultSet = statement.executeQuery(s);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String imie = resultSet.getString(2);
            String nazwisko = resultSet.getString(3);
            String stanowisko = resultSet.getString(4);
            int pensja = resultSet.getInt(5);
            list2.add(new Pracownik(id,imie,nazwisko,stanowisko,pensja));
        }
    }

}







