package com.enes.ilerlemetakipcisi;

/**
 * Created by Enes on 15.12.2017.
 */

public class Birim {
    private int id;
    private String birim;

    public Birim(String birim) {
        this.birim = birim;
    }

    public String getBirim() {
        return birim;
    }

    public void setBirim(String birim) {
        this.birim = birim;
    }

    public int getId() {
        return id;
    }

    // Nesne oluşturulduktan sonra, veritabanındaki id ile eşleştirilimeli.
    public void setId(int id) {
        this.id = id;
    }
}
