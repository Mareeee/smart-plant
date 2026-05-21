package com.ftn.service;

import com.ftn.model.TipBiljke;

public class AnalizaRequest {

    private Long biljkaId;
    private String naziv;
    private TipBiljke tipBiljke;
    private double vlaznostZemljista;
    private double temperatura;
    private double svetlost;
    private double nivoRezervoara;

    public AnalizaRequest() {
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
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

    public double getVlaznostZemljista() {
        return vlaznostZemljista;
    }

    public void setVlaznostZemljista(double vlaznostZemljista) {
        this.vlaznostZemljista = vlaznostZemljista;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getSvetlost() {
        return svetlost;
    }

    public void setSvetlost(double svetlost) {
        this.svetlost = svetlost;
    }

    public double getNivoRezervoara() {
        return nivoRezervoara;
    }

    public void setNivoRezervoara(double nivoRezervoara) {
        this.nivoRezervoara = nivoRezervoara;
    }
}