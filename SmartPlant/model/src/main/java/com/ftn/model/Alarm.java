package com.ftn.model;

public class Alarm {

    private Long biljkaId;
    private Boolean niskaVlaga;
    private Boolean visokaVlaga;
    private Boolean niskaTemperatura;
    private Boolean niskaSvetlost;
    private Boolean prazanRezervoar;

    public Alarm() {
    }

    public Alarm(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public Boolean getNiskaVlaga() {
        return niskaVlaga;
    }

    public void setNiskaVlaga(Boolean niskaVlaga) {
        this.niskaVlaga = niskaVlaga;
    }

    public Boolean getVisokaVlaga() {
        return visokaVlaga;
    }

    public void setVisokaVlaga(Boolean visokaVlaga) {
        this.visokaVlaga = visokaVlaga;
    }

    public Boolean getNiskaTemperatura() {
        return niskaTemperatura;
    }

    public void setNiskaTemperatura(Boolean niskaTemperatura) {
        this.niskaTemperatura = niskaTemperatura;
    }

    public Boolean getNiskaSvetlost() {
        return niskaSvetlost;
    }

    public void setNiskaSvetlost(Boolean niskaSvetlost) {
        this.niskaSvetlost = niskaSvetlost;
    }

    public Boolean getPrazanRezervoar() {
        return prazanRezervoar;
    }

    public void setPrazanRezervoar(Boolean prazanRezervoar) {
        this.prazanRezervoar = prazanRezervoar;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "biljkaId=" + biljkaId +
                ", niskaVlaga=" + niskaVlaga +
                ", visokaVlaga=" + visokaVlaga +
                ", niskaTemperatura=" + niskaTemperatura +
                ", niskaSvetlost=" + niskaSvetlost +
                ", prazanRezervoar=" + prazanRezervoar +
                '}';
    }
}