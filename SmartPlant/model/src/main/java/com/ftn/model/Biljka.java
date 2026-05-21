package com.ftn.model;

public class Biljka {

    private Long id;
    private String naziv;
    private TipBiljke tipBiljke;

    public Biljka() {
    }

    public Biljka(Long id, String naziv, TipBiljke tipBiljke) {
        this.id = id;
        this.naziv = naziv;
        this.tipBiljke = tipBiljke;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public TipBiljke getTipBiljke() {
        return tipBiljke;
    }

    public void setTipBiljke(TipBiljke tipBiljke) {
        this.tipBiljke = tipBiljke;
    }

    @Override
    public String toString() {
        return "Biljka{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", tipBiljke=" + tipBiljke +
                '}';
    }
}