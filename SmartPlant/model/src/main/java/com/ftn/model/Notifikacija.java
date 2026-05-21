package com.ftn.model;

public class Notifikacija {

    private Long biljkaId;
    private String poruka;
    private String prioritet;

    public Notifikacija() {
    }

    public Notifikacija(Long biljkaId, String poruka, String prioritet) {
        this.biljkaId = biljkaId;
        this.poruka = poruka;
        this.prioritet = prioritet;
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getPrioritet() {
        return prioritet;
    }

    public void setPrioritet(String prioritet) {
        this.prioritet = prioritet;
    }

    @Override
    public String toString() {
        return "Notifikacija{" +
                "biljkaId=" + biljkaId +
                ", poruka='" + poruka + '\'' +
                ", prioritet='" + prioritet + '\'' +
                '}';
    }
}