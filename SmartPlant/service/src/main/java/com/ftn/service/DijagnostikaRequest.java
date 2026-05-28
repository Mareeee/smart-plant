package com.ftn.service;

public class DijagnostikaRequest {

    private Long biljkaId;
    private String simptom;
    private String vizuelniSimptom;

    public DijagnostikaRequest() {
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public String getSimptom() {
        return simptom;
    }

    public void setSimptom(String simptom) {
        this.simptom = simptom;
    }

    public String getVizuelniSimptom() {
        return vizuelniSimptom;
    }

    public void setVizuelniSimptom(String vizuelniSimptom) {
        this.vizuelniSimptom = vizuelniSimptom;
    }

    @Override
    public String toString() {
        return "DijagnostikaRequest{" +
                "biljkaId=" + biljkaId +
                ", simptom='" + simptom + '\'' +
                ", vizuelniSimptom='" + vizuelniSimptom + '\'' +
                '}';
    }
}