package sample;
import java.sql.Date;

public class Rezerwacja {
    private int id;
    private String imie;
    private String nazwisko;
    private int nr_pokoju;
    private Date data_przyjazdu;
    private Date data_wyjazdu;
    private String parking;

    public int getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getNr_pokoju() {
        return nr_pokoju;
    }

    public Date getData_przyjazdu() {
        return data_przyjazdu;
    }

    public Date getData_wyjazdu() {
        return data_wyjazdu;
    }

    public String getParking() {
        return parking;
    }

    public Rezerwacja(int id, String imie, String nazwisko, int nr_pokoju, Date data_przyjazdu, Date data_wyjazdu, String parking) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_pokoju = nr_pokoju;
        this.data_przyjazdu = data_przyjazdu;
        this.data_wyjazdu = data_wyjazdu;
        this.parking = parking;
    }
}
