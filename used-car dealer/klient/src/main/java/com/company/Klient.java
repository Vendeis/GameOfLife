package com.company;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Klient {
    private Socket socket;
    private Scanner scanner;
    private JSONObject input;
    Klient(Socket socket){
        this.socket = socket;
        scanner = new Scanner(System.in);
    }
    void connect() throws IOException {
        int option = displayMenu();

        switch (option){
            case 1:{
                input = dodaj();
                break;
            }
            case 2:{
                input = usun();
                break;
            }
            case 3:{
                input = wyswietl();
                break;
            }
            case 4:{
                input = szukaj();
                break;
            }
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(input.toString());
        writer.newLine();
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String s = reader.readLine();
        JSONObject output = new JSONObject(s);
        if(output.has("dodaj")){
            System.out.println(output.get("dodaj"));
        }
        else if(output.has("usun")){
            System.out.println(output.get("usun"));
        }
        else if(output.has("wyswietl")){
            JSONArray array = output.getJSONArray("wyswietl");
            for(Object o : array){
                System.out.println(o.toString());
            }
        }
        else if(output.has("szukaj")){
            JSONArray array = output.getJSONArray("szukaj");
            for(Object o : array){
                System.out.println(o.toString());
            }
        }
        reader.close();
    socket.close();
    }

    private JSONObject szukaj() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Podaj rok produkcji(wyszukane zostana samochody nowsze od podanego roku), \n" +
                "przebieg(wyszukane zostana samochody o przebiegu mniejszym od podanego), \n" +
                "oraz pojemnosc silnika(zostana wyszukane samochody o pojemnosci wiekszej od podanej)");
        int rokProdukcji = Integer.parseInt(reader.readLine());
        int przebieg = Integer.parseInt(reader.readLine());
        double pojemnosc = Double.parseDouble(reader.readLine());
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        object.put("rok produkcji", rokProdukcji);
        object.put("przebieg", przebieg);
        object.put("pojemnosc", pojemnosc);
        object2.put("szukaj", object.toString());
        return object2;
    }

    private JSONObject wyswietl() {
        JSONObject object = new JSONObject();
        object.put("wyswietl","wyswietl");
        return object;
    }

    private JSONObject usun() {
        System.out.println("Podaj id samochodu, ktory chcesz usunac");
        int id = scanner.nextInt();
        JSONObject object = new JSONObject();
        object.put("usun", id);
        return object;
    }

    private JSONObject dodaj() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Podaj parametry samochodu(id, marka, model, pojemnosc, rok produkcji, przebieg)");
        int id = Integer.parseInt(reader.readLine());
        String marka = reader.readLine();
        String model = reader.readLine();
        double pojemnosc = Double.parseDouble(reader.readLine());
        int rokProdukcji = Integer.parseInt(reader.readLine());
        int przebieg = Integer.parseInt(reader.readLine());
        Car car = new Car(id,marka,model,pojemnosc,rokProdukcji,przebieg);
        JSONObject object = new JSONObject(car);
        JSONObject object2 = new JSONObject();
        object2.put("dodaj",object);
        return object2;
    }

    private int displayMenu() {
        System.out.println("1: dodaj samochod");
        System.out.println("2: usun samochod");
        System.out.println("3: wyswietl wszystkie samochody");
        System.out.println("4: szukaj samochodu");
        return scanner.nextInt();
    }
}
