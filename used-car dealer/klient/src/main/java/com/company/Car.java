package com.company;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "marka", "model", "pojemnosc", "rokProdukcji", "przebieg" })
public class Car {
    private int id;
    private String marka;
    private String model;
    private double pojemnosc;
    private int rokProdukcji;
    private int przebieg;

    public Car(int id, String marka, String model, double pojemnosc, int rokProdukcji, int przebieg) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.pojemnosc = pojemnosc;
        this.rokProdukcji = rokProdukcji;
        this.przebieg = przebieg;
    }

    public int getId() {
        return id;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public double getPojemnosc() {
        return pojemnosc;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public int getPrzebieg() {
        return przebieg;
    }
}
