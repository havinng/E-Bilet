package model;

public class Sefer {
    
    private int id;
    private String kalkis;
    private double fiyat;
    private int koltukSayisi;

    public Sefer(int id, String kalkis, double fiyat, int koltukSayisi) {
        this.id = id;
        this.kalkis = kalkis;
        this.fiyat = fiyat;
        this.koltukSayisi = koltukSayisi;
    }

    public int getId() { return id; }
    public String getKalkis() { return kalkis; }
    public double getFiyat() { return fiyat; }
    public int getKoltukSayisi() { return koltukSayisi; }
}