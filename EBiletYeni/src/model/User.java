package model;

public class User {
    private int id;
    private String kullaniciAd;
    private String sifre;
    private String mail;

    public User(String kullaniciAd, String sifre, String mail) {
        this.kullaniciAd = kullaniciAd;
        this.sifre = sifre;
        this.mail = mail;
    }

    // Getter ve Setter Metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKullaniciAd() { return kullaniciAd; }
    public void setKullaniciAd(String kullaniciAd) { this.kullaniciAd = kullaniciAd; }

    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
}