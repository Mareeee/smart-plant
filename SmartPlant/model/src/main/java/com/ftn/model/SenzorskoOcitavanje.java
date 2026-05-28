package com.ftn.model;

public class SenzorskoOcitavanje {

    private Long biljkaId;
    private double vlaznostZemljista;
    private double vlaznostVazduha;
    private double temperatura;
    private double svetlost;
    private double nivoRezervoara;
    private long timestampMs;

    public SenzorskoOcitavanje() {
    }

    public SenzorskoOcitavanje(Long biljkaId, double vlaznostZemljista, double vlaznostVazduha,
            double temperatura, double svetlost, double nivoRezervoara) {
        this.biljkaId = biljkaId;
        this.vlaznostZemljista = vlaznostZemljista;
        this.vlaznostVazduha = vlaznostVazduha;
        this.temperatura = temperatura;
        this.svetlost = svetlost;
        this.nivoRezervoara = nivoRezervoara;
        this.timestampMs = 0L;
    }

    public SenzorskoOcitavanje(Long biljkaId, double vlaznostZemljista, double vlaznostVazduha,
            double temperatura, double svetlost, double nivoRezervoara, long timestampMs) {
        this.biljkaId = biljkaId;
        this.vlaznostZemljista = vlaznostZemljista;
        this.vlaznostVazduha = vlaznostVazduha;
        this.temperatura = temperatura;
        this.svetlost = svetlost;
        this.nivoRezervoara = nivoRezervoara;
        this.timestampMs = timestampMs;
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

    public double getVlaznostVazduha() {
        return vlaznostVazduha;
    }

    public void setVlaznostVazduha(double vlaznostVazduha) {
        this.vlaznostVazduha = vlaznostVazduha;
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

    public long getTimestampMs() {
        return timestampMs;
    }

    public void setTimestampMs(long timestampMs) {
        this.timestampMs = timestampMs;
    }

    @Override
    public String toString() {
        return "SenzorskoOcitavanje{" +
                "biljkaId=" + biljkaId +
                ", vlaznostZemljista=" + vlaznostZemljista +
                ", vlaznostVazduha=" + vlaznostVazduha +
                ", temperatura=" + temperatura +
                ", svetlost=" + svetlost +
                ", nivoRezervoara=" + nivoRezervoara +
                ", timestampMs=" + timestampMs +
                '}';
    }
}