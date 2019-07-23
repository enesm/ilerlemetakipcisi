package com.enes.ilerlemetakipcisi;

/**
 * Created by Enes on 15.12.2017.
 */

public class Kayit {
    private int id;
    private String isim;
    private int deger;
    private int birim_id;
    private int degisim_miktari;
    private int limit;
    private int renklendirme;

    public Kayit(String isim, int deger, int birim_id, int degisim_miktari, int limit, int renklendirme) {
        this.isim = isim;
        this.deger = deger;
        this.birim_id = birim_id;
        this.degisim_miktari = degisim_miktari;
        this.limit = limit;
        this.renklendirme = renklendirme;
    }

    public String getIsim() {
        return isim;
    }

    public int getRenklendirme() {
        return renklendirme;
    }

    public void setRenklendirme(int renklendirme) {
        this.renklendirme = renklendirme;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public int getDeger() {
        return deger;
    }

    public void setDeger(int deger) {
        this.deger = deger;
    }

    public int getBirim_id() {
        return birim_id;
    }

    public void setBirim_id(int birim_id) {
        this.birim_id = birim_id;
    }

    public int getDegisim_miktari() {
        return degisim_miktari;
    }

    public void setDegisim_miktari(int degisim_miktari) {
        this.degisim_miktari = degisim_miktari;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getId() {
        return id;
    }

    // Nesne oluşturulduktan sonra, veritabanındaki id ile eşleştirilimeli.
    public void setId(int id) {
        this.id = id;
    }
}
