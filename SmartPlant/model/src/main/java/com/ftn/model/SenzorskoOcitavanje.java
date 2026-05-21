package com.ftn.model;

public class SenzorskoOcitavanje {

    private Long biljkaId;
    private double vlaznostZemljista;
    private double temperatura;
    private double svetlost;
    private double nivoRezervoara;

    public SenzorskoOcitavanje() {
    }

    public SenzorskoOcitavanje(Long biljkaId, double vlaznostZemljista, double temperatura, double svetlost,
            double nivoRezervoara) {
        this.biljkaId = biljkaId;
        this.vlaznostZemljista = vlaznostZemljista;
        this.temperatura = temperatura;
        this.svetlost = svetlost;
        this.nivoRezervoara = nivoRezervoara;
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
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

    @Override
    public String toString() {
        return "SenzorskoOcitavanje{" +
                "biljkaId=" + biljkaId +
                ", vlaznostZemljista=" + vlaznostZemljista +
                ", temperatura=" + temperatura +
                ", svetlost=" + svetlost +
                ", nivoRezervoara=" + nivoRezervoara +
                '}';
    }
}